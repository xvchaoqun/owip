package service.pmd;

import com.google.gson.Gson;
import controller.global.OpException;
import domain.pmd.*;
import domain.sys.SysUserView;
import jixiantech.api.pay.OrderCloseResult;
import jixiantech.api.pay.OrderFormBean;
import jixiantech.api.pay.OrderQueryResult;
import jixiantech.api.pay.PayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.PmdConstants;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.*;

@Service
public class PmdOrderService extends PmdBaseMapper {
    
    @Autowired
    private PmdMemberService pmdMemberService;
    @Autowired
    private PmdMemberPayService pmdMemberPayService;
    @Autowired
    private PmdMonthService pmdMonthService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;
    
    private Logger logger = LoggerFactory.getLogger(getClass());

    // 新建订单号，缴费时的月份(yyyyMM) + 是否延迟缴费(0,1) + 缴费方式(1,2)
    // + 缴费数量（1：单一缴费） + (党员快照ID%999999，7位，预留一位0备用) + [重复生成的订单数量，2位，要求<=99]
    private String createOrderNo(int pmdMemberId, PmdMonth currentPmdMonth,
                                 boolean isDelay, byte payWay) {
        
        // 根据订单生成次数，给订单号末尾加上后缀
        String suffix = "";
        {
            PmdOrderExample example = new PmdOrderExample();
            example.createCriteria().andMemberIdEqualTo(pmdMemberId);
            long count = pmdOrderMapper.countByExample(example);
            if (count > 0) {
                // 同一条缴费记录，系统只能处理99及以下的订单生成次数
                Assert.isTrue(count < 100, "超过最大允许创建订单次数");
                suffix = String.format("%02d", count);
            }
        }
        
        // 7位订单号, 预留一位0备用（要求每个月的缴费人数不超过999999人）
        String _orderNo = String.format("%07d", pmdMemberId % 999999);
        
        return DateUtils.formatDate(currentPmdMonth.getPayMonth(), "yyyyMM")
                + (isDelay ? "1" : "0")
                + payWay
                + "1" // 1：单一缴费
                + _orderNo + suffix /*+ "_test"*/;
    }
    
    // 构建支付表单参数
    private PmdOrder confirmOrder(String oldOrderNo, int pmdMemberId, boolean isSelfPay, String orderType) {
        
        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(pmdMemberId);
        if (pmdMember == null || pmdMember.getHasPay()) {
            //logger.error("");
            throw new OpException("缴费记录不存在或已经支付，请刷新页面再试。");
        }
        // 缴费月份校验，要求当前缴费月份是开启状态
        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth == null) {
            throw new OpException("未到缴费时间。");
        }
        
        boolean isCurrentMonth = (pmdMember.getMonthId().intValue() == currentPmdMonth.getId());
        if (isCurrentMonth && pmdMember.getIsDelay()) {
            // 当月已经设定为延迟缴费，不允许在线缴费
            throw new OpException("已经设定为延迟缴费，当前不允许缴费。");
        }
        if (!isCurrentMonth && !pmdMember.getIsDelay()) {
            
            throw new OpException("缴费状态错误，请联系管理员。");
        }
        
        // 重复确认一下当前是否允许缴费
        if (pmdMember.getPayStatus() == 0) {
            throw new OpException("缴费失败，当前不允许缴费。");
        }
        // 重复验证是否有成功的缴费记录
        {
            PmdOrderExample example = new PmdOrderExample();
            example.createCriteria().andMemberIdEqualTo(pmdMemberId).andIsSuccessEqualTo(true);
            if (pmdOrderMapper.countByExample(example) > 0) {
                throw new OpException("重复缴费，请联系管理员处理。");
            }
        }
        
        String payer = null;
        String payername = null;
        if (isSelfPay) {
            // 本人缴费
            SysUserView uv = pmdMember.getUser();
            payer = uv.getCode();
            payername = uv.getRealname();
        } else {
            // 代缴
            Integer currentUserId = ShiroHelper.getCurrentUserId();
            if (currentUserId == null) {
                logger.error("代缴错误，currentUserId = null. 缴费账号{}", pmdMember.getUser().getRealname());
                throw new OpException("操作失败，请您重新登录系统后再试。");
            }
            SysUserView uv = sysUserService.findById(currentUserId);
            if (uv == null) {
                logger.error("代缴错误，currentUserId={} but uv = null. 缴费账号{}",
                        currentUserId, pmdMember.getUser().getRealname());
                throw new OpException("操作失败，请您重新登录系统后再试。");
            }
            payer = uv.getCode();
            payername = uv.getRealname();
        }
        
        String amt = pmdMember.getDuePay().toString();
        
        PmdOrder newOrder = new PmdOrder();
        newOrder.setPayer(payer);
        newOrder.setPayername(payername);
        newOrder.setAmt(amt);

        boolean mustMakeNewOrder = false;
        // 如果订单已存在（最近一次的）且与本次支付信息不相同，则生成新的订单
        //PmdMemberPay pmdMemberPay = pmdMemberPayMapper.selectByPrimaryKey(pmdMemberId);
        //String oldOrderNo = pmdMemberPay.getOrderNo();
        PmdOrder oldOrder = null;
        if (StringUtils.isNotBlank(oldOrderNo)) {
            oldOrder = pmdOrderMapper.selectByPrimaryKey(oldOrderNo);
        }
        if (oldOrder != null) {

            OrderFormBean orderFormBean = PayUtils.createOrderFormBean(payer, amt, oldOrderNo, orderType);
            Map<String, Object> paramMap = orderFormBean.getParamMap();
            Gson gson = new Gson();
            Map<String, Object> oldParams = gson.fromJson(oldOrder.getParams(), Map.class);
            if (oldOrder.getIsClosed() // 订单关闭
                    || !FormUtils.paramMapEquals(paramMap, oldParams)) {
                
                logger.info("原订单({})已关闭或信息变更，生成新订单号", oldOrderNo);
                mustMakeNewOrder = true;
            }
        } else {
            logger.info("原订单{}信息不存在，生成订单号", StringUtils.isBlank(oldOrderNo) ? "" : "(" + oldOrderNo + ")");
            mustMakeNewOrder = true;
        }
        
        if (mustMakeNewOrder) {
            // 使用真实的缴费月份当订单号的日期部分，在处理支付通知时，使用该月份为支付月份
            String orderNo = createOrderNo(pmdMemberId, currentPmdMonth,
                    pmdMember.getIsDelay(), PmdConstants.PMD_PAY_WAY_CAMPUSCARD);
            newOrder.setSn(orderNo);

            OrderFormBean orderFormBean = PayUtils.createOrderFormBean(payer, amt, orderNo, orderType);
            Map<String, Object> paramMap = orderFormBean.getParamMap();
            newOrder.setParams(JSONUtils.toString(paramMap, false));
            // 签名
            newOrder.setSign(orderFormBean.getSign());
            
            int userId = ShiroHelper.getCurrentUserId();
            
            newOrder.setPayMonth(DateUtils.formatDate(currentPmdMonth.getPayMonth(), "yyyyMM"));
            newOrder.setMemberId(pmdMemberId);
            newOrder.setUserId(userId);
            newOrder.setIsSuccess(false);
            newOrder.setIsClosed(false);
            newOrder.setCreateTime(new Date());
            newOrder.setIp(ContextHelper.getRealIp());
            
            pmdOrderMapper.insertSelective(newOrder);
            
            sysApprovalLogService.add(pmdMemberId, userId,
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_NOT_SELF,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                    "支付已确认，即将跳转支付页面", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    "订单号：" + orderNo);
            
            return newOrder;
        }
        
        return pmdOrderMapper.selectByPrimaryKey(oldOrderNo);
    }
    
    
    // 无论如何，都要保存服务器支付通知
    @Transactional
    public void savePayNotify(HttpServletRequest request) {
        
        PmdNotify record = new PmdNotify();
        try {
            
            record.setSn(request.getParameter("thirdorderid"));
            record.setAmt(request.getParameter("actulamt"));
            record.setParams(JSONUtils.toString(request.getParameterMap(), false));
            record.setIsSuccess(StringUtils.equals(request.getParameter("state"), "1"));
            
            record.setVerifySign(verifyNotifySign(request));
            record.setRetTime(new Date());
            record.setIp(ContextHelper.getRealIp());
        } catch (Exception ex) {
            logger.error("支付通知错误", ex);
        }
        
        pmdNotifyMapper.insertSelective(record);
    }

    // 计算服务器通知签名
    public String notifySign(HttpServletRequest request){

        String tranamt = request.getParameter("tranamt");
        String orderid = request.getParameter("orderid");
        String account = request.getParameter("account");
        String sno = request.getParameter("sno");
        String toaccount = request.getParameter("toaccount");
        String thirdsystem = request.getParameter("thirdsystem");
        String thirdorderid = request.getParameter("thirdorderid");
        String state = request.getParameter("state");
        String orderdesc = request.getParameter("orderdesc");
        String praram1 = request.getParameter("praram1");

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("tranamt", tranamt);
        paramMap.put("orderid", orderid);
        paramMap.put("account", account);
        paramMap.put("sno", sno);
        paramMap.put("toaccount", toaccount);
        paramMap.put("thirdsystem", thirdsystem);
        paramMap.put("thirdorderid", thirdorderid);
        paramMap.put("state", state);
        paramMap.put("orderdesc", orderdesc);
        paramMap.put("praram1", praram1);

        return PayUtils.sign(paramMap);
    }

    // （服务器通知）签名校验
    public boolean verifyNotifySign(HttpServletRequest request){

        String sign = request.getParameter("sign");
        if(StringUtils.isBlank(sign)) return false;

        String verifySign = notifySign(request);

        boolean ret = false;
        try {
            ret = StringUtils.equalsIgnoreCase(sign, verifySign);
            if(!ret) ret = StringUtils.equalsIgnoreCase(URLDecoder.decode(sign, "UTF-8"), verifySign);
        } catch (UnsupportedEncodingException e) {
            logger.error("异常", e);
        }
        if (!ret) {
            logger.warn("签名校验失败，{}, verifySign={}, sign={}",
                    JSONUtils.toString(request.getParameterMap(), false), verifySign, sign);
        }
        
        return ret;
    }
    
    // 跳转页面前的支付确认，生成支付订单号
    @Transactional
    public PmdOrder payConfirm(int pmdMemberId, boolean isSelfPay, String orderType) {
        
        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth == null) {
            throw new OpException("未到缴费时间。");
        }
        
        int userId = ShiroHelper.getCurrentUserId();
        SysUserView uv = sysUserService.findById(userId);
        if (!uv.isCasUser()) {
            throw new OpException("您的账号是系统注册账号，不能使用校园卡支付。");
        }
        
        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(pmdMemberId);
        if (pmdMember == null) {
            throw new OpException("缴费记录不存在。");
        }
        
        PmdMemberPay pmdMemberPay = pmdMemberPayMapper.selectByPrimaryKey(pmdMemberId);
        String oldOrderNo = pmdMemberPay.getOrderNo();
        
        // 确认订单信息
        PmdOrder pmdOrder = confirmOrder(oldOrderNo, pmdMemberId, isSelfPay, orderType);
        
        PmdMemberPay record = new PmdMemberPay();
        record.setMemberId(pmdMemberId);
        record.setOrderNo(pmdOrder.getSn());
        record.setOrderUserId(userId);
        
        if (pmdMemberPayMapper.updateByPrimaryKeySelective(record) == 0) {
            
            logger.error("确认缴费时，对应的党员账单不存在...%s, %s", pmdMemberId, userId);
            throw new OpException("缴费请求有误，请稍后再试。");
        }
        
        // 如果新生成的订单号和原订单号不一致，则关闭原订单号
        if (StringUtils.isNotBlank(oldOrderNo) && !StringUtils.equals(oldOrderNo, pmdOrder.getSn())) {
            try {
                closeTrade(oldOrderNo);
            } catch (IOException e) {
                throw new OpException("关闭原订单{0}错误，请稍后再试。{1}", oldOrderNo, e.getMessage());
            }
        }

        if(!springProps.devMode) { // 测试状态不检查订单支付状态
            checkPayStatus(pmdMemberId, pmdOrder.getSn());
        }
        
        return pmdOrder;
    }
    
    // 确认支付状态，如果是单个缴费，则在生成订单之后检查； 如果是批量缴费，则在生成订单之前检查，且currentSn=null
    private void checkPayStatus(int pmdMemberId, String currentSn) {
        
        // 检查一下是否有批量代缴的记录，如果存在且成功支付，则不允许支付, 如果没有成功支付，则关闭订单
        {
            List<String> toClosedSnList = new ArrayList<>();
            List<PmdOrder> pmdOrders = iPmdMapper.notClosedBatchOrder(pmdMemberId);
            for (PmdOrder pmdOrder : pmdOrders) {
                String sn = pmdOrder.getSn();
                if (pmdOrder.getIsSuccess()) {
                    throw new OpException("当前缴费记录已批量缴费成功，缴费订单号：{0}, 请勿重复支付。", sn);
                } else {
                    toClosedSnList.add(sn);
                }
            }
            for (String sn : toClosedSnList) {
                try {
                    closeTrade(sn);
                } catch (IOException e) {
                    throw new OpException("关闭原订单{0}错误，请稍后再试。{1}", sn, e.getMessage());
                }
            }
        }
        
        // 检查一下生成的所有订单，是否已经有支付成功的记录，如有则不允许跳转
        {
            PmdOrderExample example = new PmdOrderExample();
            PmdOrderExample.Criteria criteria = example.createCriteria().andMemberIdEqualTo(pmdMemberId);
            if (currentSn != null) {
                // 针对单个缴费，不包括当前生成的订单
                criteria.andSnNotEqualTo(currentSn);
            }
            List<PmdOrder> pmdOrders = pmdOrderMapper.selectByExample(example);
            
            String sn = null;
            OrderQueryResult queryRet = null;
            try {
                for (PmdOrder pmdOrder : pmdOrders) {
                    
                    sn = pmdOrder.getSn();
                    queryRet = query(sn);

                    /*if(StringUtils.isBlank(queryRet.getStatus())){
                        throw new OpException(queryRet.getException());
                    }*/

                    if (StringUtils.isNotBlank(queryRet.getStatus())) { // 支付系统查询订单号不存在的话，接口返回的内容为空
                        
                        if (queryRet.isHasPay()) {
                            
                            String payer = queryRet.getPayer();

                            logger.warn("当前缴费记录已由{}支付成功，支付订单号{}，请不要重复支付。",
                                    payer, sn);
                            throw new OpException("当前缴费记录已由{0}支付成功，支付订单号{1}，请不要重复支付。",
                                    payer, sn);
                        }
                    }
                }
            } catch (Exception ex) {
                
                logger.error(String.format("支付平台错误, sn=%s, ret=%s", sn, JSONUtils.toString(queryRet,false)), ex);
                throw new OpException("支付平台错误，请稍后再试。");
            }
        }
    }
    
    
    // 新建批量代缴订单号，缴费时的月份(yyyyMM) + 是否延迟缴费(0,1) + 缴费方式(1,2)
    // + 缴费数量（2：批量代缴） + (userId，7位，预留一位0备用，要求<999999) + (每月生成的订单数量，3位，要求<=999)
    private String createBatchOrderNo(int userId, PmdMonth currentPmdMonth,
                                      boolean isDelay, byte payWay) {
        
        // 根据订单生成次数，给订单号末尾加上后缀
        String suffix = "";
        {
            String payMonth = DateUtils.formatDate(currentPmdMonth.getPayMonth(), "yyyyMM");
            PmdOrderExample example = new PmdOrderExample();
            example.createCriteria().andPayMonthEqualTo(payMonth)
                    .andUserIdEqualTo(userId).andIsBatchEqualTo(true);
            long count = pmdOrderMapper.countByExample(example) + 1;
            
            // 同一条缴费记录，系统只能处理999及以下的订单生成次数
            Assert.isTrue(count < 1000, "超过最大允许创建订单次数");
            suffix = String.format("%03d", count);
        }
        
        if (userId > 999999) {
            throw new OpException("订单号创建失败。");
        }
        
        // 7位订单号, 预留一位0备用（要求每个月的缴费人数不超过999999人）
        String _orderNo = String.format("%07d", userId);
        
        return DateUtils.formatDate(currentPmdMonth.getPayMonth(), "yyyyMM")
                + (isDelay ? "1" : "0")
                + payWay
                + "2" // 2：批量代缴
                + _orderNo + suffix/* + "_test"*/;
    }
    
    // 批量缴费跳转页面前的支付确认， 确保每个缴费记录
    @Transactional
    public PmdOrder batchPayConfirm(boolean isDelay, Integer[] pmdMemberIds) {
        
        // 缴费月份校验，要求当前缴费月份是开启状态
        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth == null) {
            throw new OpException("未到缴费时间。");
        }
        
        Integer currentUserId = ShiroHelper.getCurrentUserId();
        if (currentUserId == null) {
            logger.error("批量缴费异常，currentUserId = null.");
            throw new OpException("操作失败，请您重新登录系统后再试。");
        }
        SysUserView uv = sysUserService.findById(currentUserId);
        if (uv == null) {
            logger.error("批量缴费异常，currentUserId={} but uv = null.", currentUserId);
            throw new OpException("操作失败，请您重新登录系统后再试。");
        }
        
        if (!uv.isCasUser()) {
            throw new OpException("您的账号是系统注册账号，不能使用校园卡支付。");
        }
        
        String currentPayMonth = DateUtils.formatDate(currentPmdMonth.getPayMonth(), "yyyyMM");
        {
            // 关闭之前的订单
            PmdOrderExample example = new PmdOrderExample();
            example.createCriteria().andPayMonthEqualTo(currentPayMonth)
                    .andUserIdEqualTo(currentUserId).andIsBatchEqualTo(true)
                    .andIsSuccessEqualTo(false).andIsClosedEqualTo(false);
            List<PmdOrder> pmdOrders = pmdOrderMapper.selectByExample(example);
            for (PmdOrder pmdOrder : pmdOrders) {
                
                String sn = pmdOrder.getSn();
                try {
                    closeTrade(sn);
                } catch (IOException e) {
                    throw new OpException("关闭原订单{0}异常，请稍后再试。{1}", sn, e.getMessage());
                }
            }
        }
        
        if (pmdMemberIds == null || pmdMemberIds.length == 0) {
            throw new OpException("没有选择缴费记录。");
        }
        
        String orderNo = createBatchOrderNo(currentUserId, currentPmdMonth, isDelay, PmdConstants.PMD_PAY_WAY_CAMPUSCARD);
        
        BigDecimal duePay = BigDecimal.ZERO;
        for (Integer pmdMemberId : pmdMemberIds) {
            
            PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(pmdMemberId);
            if (pmdMember == null) {
                //logger.error("");
                throw new OpException("缴费记录不存在，请刷新页面再试。");
            }
            if (pmdMember.getHasPay()) {
                //logger.error("");
                throw new OpException("{0}缴费记录已经支付。", pmdMember.getUser().getRealname());
            }
            
            if (pmdMember.getIsDelay() != isDelay) {
                throw new OpException("{0}缴费参数错误。", pmdMember.getUser().getRealname());
            }
            
            boolean isCurrentMonth = (pmdMember.getMonthId().intValue() == currentPmdMonth.getId());
            if (isCurrentMonth && pmdMember.getIsDelay()) {
                // 当月已经设定为延迟缴费，不允许在线缴费
                throw new OpException("{0}已经设定为延迟缴费，当前不允许缴费。", pmdMember.getUser().getRealname());
            }
            if (!isCurrentMonth && !pmdMember.getIsDelay()) {
                
                throw new OpException("{0}缴费状态异常，请联系管理员。", pmdMember.getUser().getRealname());
            }
            
            // 重复确认一下当前是否允许缴费
            if (pmdMember.getPayStatus() == 0) {
                throw new OpException("{0}缴费失败，当前不允许缴费。", pmdMember.getUser().getRealname());
            }
            // 重复验证是否有成功的缴费记录
            {
                PmdOrderExample example = new PmdOrderExample();
                example.createCriteria().andMemberIdEqualTo(pmdMemberId).andIsSuccessEqualTo(true);
                if (pmdOrderMapper.countByExample(example) > 0) {
                    throw new OpException("{0}重复缴费，请联系管理员处理。", pmdMember.getUser().getRealname());
                }
            }
            
            PmdMemberPay record = new PmdMemberPay();
            record.setMemberId(pmdMemberId);
            record.setOrderNo(orderNo);
            record.setOrderUserId(currentUserId);
            
            if (pmdMemberPayMapper.updateByPrimaryKeySelective(record) == 0) {
                
                logger.error("确认缴费时，对应的党员账单不存在...%s, %s", pmdMemberId, currentUserId);
                throw new OpException("缴费异常，请稍后再试。");
            }
            
            duePay = duePay.add(pmdMember.getDuePay());
            
            //TO DO: 有重复检测出现
            checkPayStatus(pmdMemberId, null);
            
            PmdOrderItem _pmdOrderItem = new PmdOrderItem();
            _pmdOrderItem.setSn(orderNo);
            _pmdOrderItem.setMemberId(pmdMemberId);
            _pmdOrderItem.setDuePay(pmdMember.getDuePay());
            pmdOrderItemMapper.insertSelective(_pmdOrderItem);
            
            sysApprovalLogService.add(pmdMemberId, currentUserId,
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_NOT_SELF,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                    "支付已确认，即将跳转支付页面(批量缴费)", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    "订单号：" + orderNo);
        }
        
        if (duePay == null || duePay.compareTo(BigDecimal.ZERO) <= 0) {
            
            throw new OpException("缴费金额有误。");
        }
        
        String payer = uv.getCode();
        String payername = uv.getRealname();
        String amt = duePay.toString();
        PmdOrder newOrder = new PmdOrder();
        newOrder.setPayer(payer);
        newOrder.setPayername(payername);
        newOrder.setAmt(amt);

        OrderFormBean orderFormBean = PayUtils.createOrderFormBean(payer, amt, orderNo, PayUtils.orderType_PC);
        newOrder.setParams(JSONUtils.toString(orderFormBean.getParamMap(), false));
        newOrder.setSn(orderNo);
        newOrder.setSign(orderFormBean.getSign());
        
        newOrder.setPayMonth(currentPayMonth);
        newOrder.setIsBatch(true);
        newOrder.setUserId(currentUserId);
        newOrder.setIsSuccess(false);
        newOrder.setIsClosed(false);
        newOrder.setCreateTime(new Date());
        newOrder.setIp(ContextHelper.getRealIp());
        
        pmdOrderMapper.insertSelective(newOrder);
        
        return newOrder;
    }
    
    
    // 处理服务器后台结果通知
    @Transactional
    public boolean notify(HttpServletRequest request) {
        
        savePayNotify(request);
        
        processCallback(request);
        return true;
    }
    
    // 处理服务器支付结果通知
    @Transactional
    public void processCallback(HttpServletRequest request) {
        
        String orderNo = request.getParameter("thirdorderid");
        String payerCode = request.getParameter("sno");
        String amt = request.getParameter("actulamt"); // 单位 分
        String state = request.getParameter("state");

        BigDecimal realPay = new BigDecimal(amt).divide(BigDecimal.valueOf(100));
        try {
            // 签名校验成功 且 确认交易成功
            boolean verifyNotifySign = verifyNotifySign(request);
            if ( verifyNotifySign && StringUtils.equals(state, "1")) {
                
                PmdOrder pmdOrder = pmdOrderMapper.selectByPrimaryKey(orderNo);
                if (pmdOrder == null) {
                    logger.error("[党费收缴]处理支付通知失败，订单号不存在，订单号：{}", orderNo);
                } else if (pmdOrder.getIsSuccess()) {
                    logger.debug("[党费收缴]处理支付通知重复，订单号：{}", orderNo);
                } else {
                    
                    // 更新订单状态为成功支付
                    PmdOrder record = new PmdOrder();
                    record.setSn(orderNo);
                    record.setIsSuccess(true);
                    pmdOrderMapper.updateByPrimaryKeySelective(record);
                    
                    boolean isBatch = StringUtils.equals(orderNo.substring(8, 9), "2");
                    if (isBatch) {
                        processBatchOrder(orderNo, payerCode, realPay);
                    } else {
                        processSingleOrder(pmdOrder.getMemberId(), orderNo,
                                payerCode, realPay);
                    }
                }
            }else{
                logger.warn("[党费收缴]处理支付通知，订单号交易失败，订单号：{}，校验签名结果：{}, state：{}",
                        orderNo, verifyNotifySign, state);
            }
        } catch (Exception ex) {
            
            logger.error(String.format("保存支付通知失败，报文内容：%s, IP:%s",
                    JSONUtils.toString(request.getParameterMap(), false), ContextHelper.getRealIp()), ex);
            // 抛出异常，回滚数据库
            throw ex;
        }
    }
    
    // 单个缴费通知处理
    private void processSingleOrder(int memberId, String orderNo, String payerCode, BigDecimal realPay) {
        
        // 更新党员账单
        PmdMemberPayView pmdMemberPayView = pmdMemberPayService.get(memberId);
        if (pmdMemberPayView == null) {
            
            logger.error("[党费收缴]处理支付通知失败，缴费记录不存在，订单号：{}", orderNo);
            throw new RuntimeException("支付接口通知异常。");
        }
        
        Boolean hasPay = pmdMemberPayView.getHasPay();
        int payStatus = pmdMemberPayView.getPayStatus();
        
        if (BooleanUtils.isTrue(hasPay)) {
            
            // 如果重复通知，则不再更新相关缴费记录。（此时可能原因：重复缴费）
            logger.warn("[党费收缴]处理支付通知重复，订单号：{}, 原订单号：{}",
                    orderNo, pmdMemberPayView.getOrderNo());
            return;
        }
        
        boolean isSelfPay = true;
        Integer chargeUserId = null; // 代缴人
        // 读取实际缴费人
        String code = StringUtils.trimToNull(payerCode);
        if (code != null) {
            SysUserView uv = sysUserService.findByCode(code);
            if (uv != null && uv.getId().intValue() != pmdMemberPayView.getUserId()) {
                chargeUserId = uv.getId();
                isSelfPay = false;
                if (chargeUserId == null) {
                    logger.warn("[党费收缴]处理支付通知异常，代缴人读取失败，订单号：{}", orderNo);
                }
            }
        }
        
        // 根据订单号的前6位，找到订单生成时的缴费月份（防止延时反馈跨月的情况出现）
        PmdMonth payMonth = pmdMonthService.getMonth(DateUtils.parseDate(orderNo.substring(0, 6), "yyyyMM"));
        if (payMonth == null) {
            logger.error("[党费收缴]处理支付结果异常，缴费月份不存在，订单号：{}", orderNo);
            throw new RuntimeException("支付接口通知异常。");
        } else {
            
            int needPayMonthId = payMonth.getId();
            PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
            if (currentPmdMonth == null) { // 再次确定当月还未结算
                logger.error("[党费收缴]缴费已关闭，但是收到了缴费成功的通知，订单号：{}", orderNo);
                //return ; // 因为数据已报送，所以不允许更新??? 但是不更新的话，用户看不到成功的结果
                throw new RuntimeException("支付接口通知异常。");
            }
            
            // 此笔账单是否补缴
            boolean isDelay = StringUtils.equals(orderNo.substring(6, 7), "1");
            if (isDelay && payStatus != 2) { // isDelay=false时，可能也是补缴，因为当月生成了订单号之后，没有缴费，但是补缴时订单号不会变更。
                // 按理不会发生此情况？
                logger.error("[党费收缴]处理支付结果异常，补缴状态异常，订单号：{}", orderNo);
                throw new RuntimeException("支付接口通知异常。");
            } else {
                
                if (payStatus == 0) {
                    logger.error("[党费收缴]处理支付结果异常，订单不允许缴费(当月已关闭缴费或当月已设置为延迟缴费)，订单号：{}", orderNo);
                    throw new RuntimeException("支付接口通知异常。");
                }
                
                // 收到支付通知时，要求订单的缴费月份必须是当前系统设定的缴费月份，否则不允许更新。（注：订单号是由当时的缴费月份生成的）
                if (needPayMonthId != currentPmdMonth.getId()
                        && BooleanUtils.isFalse(pmdMemberPayView.getIsDelay())) {
                    logger.error("[党费收缴]处理支付结果异常，缴费月份和当前缴费月份不同，不允许缴费，订单号：{}", orderNo);
                    //return;  // 为了本人页面正常显示支付成功，这里需要放行
                }
                
                if (!isDelay && payStatus != 0 && needPayMonthId == currentPmdMonth.getId()) {
                    // 当月正常缴费时（非补缴），才需更新快照
                    PmdMember record = new PmdMember();
                    record.setId(memberId);
                    record.setHasPay(true);
                    record.setRealPay(realPay);
                    record.setIsSelfPay(isSelfPay);
                    record.setPayTime(new Date());
                    record.setChargeUserId(chargeUserId);
                    
                    PmdMemberExample example = new PmdMemberExample();
                    example.createCriteria().andIdEqualTo(memberId)
                            .andHasPayEqualTo(false)
                            .andIsDelayEqualTo(isDelay); // 确保延迟状态一致，防止管理员改变了状态
                    /* 支付通知时，以下情况不允许更新快照：
                            1、原订单是正常缴费，缴费当月未收到支付通知，接着在当月管理员它设置为了延迟或强制延迟报送了，
                            在后面的月份又收到了通知（其实前面的payStatus已判断了）
                            2、原订单是补缴，补缴当月未收到支付通知，在后面的月份又收到了通知（前面已判断，但未阻止）
                    */
                    if (pmdMemberMapper.updateByExampleSelective(record, example) == 0) {
                        
                        logger.error("[党费收缴]处理支付结果异常，更新快照失败，订单号：{}", orderNo);
                        throw new RuntimeException("支付接口通知异常。");
                    }
                }
                
                PmdMemberPay record = new PmdMemberPay();
                record.setMemberId(memberId);
                record.setHasPay(true);
                record.setRealPay(realPay);
                record.setIsSelfPay(isSelfPay);
                record.setChargeUserId(chargeUserId);
                record.setPayMonthId(currentPmdMonth.getId());
                // 把党员生成订单时所在党委、支部，设置为缴费的单位
                int userId = pmdMemberPayView.getUserId();
                PmdMember pmdMember = pmdMemberService.get(needPayMonthId, userId);
                int partyId = pmdMember.getPartyId();
                Integer branchId = pmdMember.getBranchId();
                record.setChargePartyId(partyId);
                record.setChargeBranchId(branchId);
                record.setPayTime(new Date());
                
                PmdMemberPayExample example = new PmdMemberPayExample();
                example.createCriteria().andMemberIdEqualTo(memberId)
                        .andHasPayEqualTo(false);
                if (pmdMemberPayMapper.updateByExampleSelective(record, example) == 0) {
                    
                    logger.error("[党费收缴]处理支付结果异常，更新账单失败，订单号：{}", orderNo);
                    throw new OpException("更新账单失败");
                }
                
                sysApprovalLogService.add(pmdMember.getId(), userId,
                        SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
                        SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                        "线上缴费成功通知", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, orderNo);
            }
        }
    }
    
    // 处理批量缴费结果通知
    private void processBatchOrder(String orderNo, String payerCode, BigDecimal realPay) {
        
        PmdOrderItemExample example = new PmdOrderItemExample();
        example.createCriteria().andSnEqualTo(orderNo.trim());
        List<PmdOrderItem> pmdOrderItems = pmdOrderItemMapper.selectByExample(example);
        
        BigDecimal duePay = BigDecimal.ZERO;
        for (PmdOrderItem pmdOrderItem : pmdOrderItems) {
            
            duePay = duePay.add(pmdOrderItem.getDuePay());
            processSingleOrder(pmdOrderItem.getMemberId(), orderNo, payerCode, pmdOrderItem.getDuePay());
        }
        if (duePay.compareTo(realPay) != 0) {
            logger.error("批量缴费通知异常，总额校验失败。订单号：{}, 通知总额：{}，应交总额：{}",
                    orderNo, realPay, duePay);
            throw new RuntimeException("支付接口通知异常。");
        }
    }
    
    public class CloseTradeRet {
        public boolean success;
        public String ret;
    }
    
    /*
        0000：更新成功
        2004：订单不存在
        2002：该交易已成功，请确认
        2005：该交易已关闭，请确认
        3001：该交易正在处理中，请等待...（只针对于一卡通支付的情况）
        9995：数据库异常，更新失败
    */
    @Transactional
    public CloseTradeRet closeTrade(String sn) throws IOException {

        if(springProps.devMode) {
            // test
            PmdOrder record = new PmdOrder();
            record.setSn(sn);
            record.setIsClosed(true);
            pmdOrderMapper.updateByPrimaryKeySelective(record);
            return null;
        }

        CloseTradeRet closeTradeRet = new CloseTradeRet();
        closeTradeRet.success = false;

        OrderCloseResult result = PayUtils.closeOrder(sn);
        closeTradeRet.ret = result.getRet();
        if(result.isSuccess()){
            PmdOrder record = new PmdOrder();
            record.setSn(sn);
            record.setIsClosed(true);
            pmdOrderMapper.updateByPrimaryKeySelective(record);

            logger.info("关闭订单{}成功，接口响应内容：{}", sn, closeTradeRet.ret);
            closeTradeRet.success = true;
        }

        return closeTradeRet;
    }
    
    // 查询订单结果
    public OrderQueryResult query(String sn){

        return PayUtils.orderQuery(sn);
    }
}
