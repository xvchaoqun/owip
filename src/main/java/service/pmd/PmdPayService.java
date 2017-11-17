package service.pmd;

import controller.global.OpException;
import domain.pmd.PmdBranch;
import domain.pmd.PmdMember;
import domain.pmd.PmdMemberExample;
import domain.pmd.PmdMemberPay;
import domain.pmd.PmdMemberPayExample;
import domain.pmd.PmdMemberPayView;
import domain.pmd.PmdMonth;
import domain.pmd.PmdNotifyLog;
import domain.pmd.PmdParty;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.party.PartyService;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;
import sys.utils.MD5Util;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lm on 2017/11/7.
 */
@Service
public class PmdPayService extends BaseMapper {

    @Autowired
    private PmdMemberService pmdMemberService;
    @Autowired
    private PmdMemberPayService pmdMemberPayService;
    @Autowired
    private PmdBranchService pmdBranchService;
    @Autowired
    private PmdMonthService pmdMonthService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private PmdPartyService pmdPartyService;
    @Autowired
    private PmdPartyAdminService pmdPartyAdminService;
    @Autowired
    private PmdBranchAdminService pmdBranchAdminService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;


    private Logger logger = LoggerFactory.getLogger(getClass());

    public final static String return_url = "http://zzbgz.bnu.edu.cn/pmd/pay/returnPage";
    public final static String notify_url = "http://zzbgz.bnu.edu.cn/pmd/pay/notifyPage";
    //public final static String xmpch = PropertiesUtils.getString("pay.id");
    //public final static String key = PropertiesUtils.getString("pay.key");

    // 测试
    public final static String xmpch = "004-2014050001";
    public final static String key = "umz4aea6g97skeect0jtxigvjkrimd0o";

    // 订单号起始值
    public final static int orderNoOffset = 10240000;

    // 新建订单号，缴费时的月份(yyyyMM) + 是否补缴(0,1) + (党员快照ID + 订单号起始值)
    public String createOrderNo(int pmdMemberId, PmdMonth currentPmdMonth, boolean isDelay) {

        int _orderNo = pmdMemberId + orderNoOffset;
        if (_orderNo > 99999999) {
            throw new OpException("缴费失败，原因：订单号错误。");
        }

        return DateUtils.formatDate(currentPmdMonth.getPayMonth(), "yyyyMM") + (isDelay ? "1" : "0") + _orderNo;
    }

    // 构建支付表单参数
    public PayFormBean createPayFormBean(int pmdMemberId) {

        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(pmdMemberId);
        if (pmdMember == null || pmdMember.getHasPay()) {
            //logger.error("");
            throw new OpException("操作有误，请稍后再试。");
        }
        // 缴费月份校验，要求当前缴费月份是开启状态
        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth == null) {
            throw new OpException("缴费失败，原因：未到缴费时间。");
        }

        boolean isCurrentMonth = (pmdMember.getMonthId().intValue() == currentPmdMonth.getId());
        if (isCurrentMonth && pmdMember.getIsDelay()) {
            // 当月已经设定为延迟缴费，不允许在线缴费
            throw new OpException("缴费失败，原因：已经设定为延迟缴费。");
        }
        if (!isCurrentMonth && !pmdMember.getIsDelay()) {

            throw new OpException("补缴失败，原因：状态异常。");
        }

        String orderDate = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");

        // 重复确认一下当前是否允许缴费
        if (pmdMember.getPayStatus() == 0) {
            throw new OpException("缴费失败，原因：当前不允许缴费。");
        }

        // 使用真实的缴费月份当订单号的日期部分，在处理支付通知时，使用该月份为支付月份
        String orderNo = createOrderNo(pmdMemberId, currentPmdMonth, pmdMember.getIsDelay());
        String amount = pmdMember.getDuePay().toString();
        String md5Str = "orderDate=" + orderDate +
                "&orderNo=" + orderNo +
                "&amount=" + amount +
                "&xmpch=" + xmpch +
                "&return_url=" + return_url +
                "&notify_url=" + notify_url + key;
        String sign = MD5Util.md5Hex(md5Str, "utf-8");

        PayFormBean bean = new PayFormBean();
        bean.setOrderDate(orderDate);
        bean.setOrderNo(orderNo);
        bean.setXmpch(xmpch);
        bean.setAmount(amount);
        bean.setReturn_url(return_url);
        bean.setNotify_url(notify_url);
        bean.setSign(sign);

        return bean;
    }

    // 无论如何，都要保存服务器支付通知
    public void savePayNotifyBean(PayNotifyBean bean) {

        boolean verifySign = StringUtils.equals(bean.getSign(), verifySign(bean));

        PmdNotifyLog record = new PmdNotifyLog();
        try {
            record.setOrderDate(DateUtils.parseDate(StringUtils.trim(bean.getOrderDate()), "yyyyMMddHHmmss"));
            record.setOrderNo(StringUtils.trim(bean.getOrderNo()));
            record.setAmount(new BigDecimal(StringUtils.trim(bean.getAmount())));
            record.setJylsh(StringUtils.trim(bean.getJylsh()));
            record.setTranStat(Byte.valueOf(StringUtils.trim(bean.getTranStat())));
            record.setReturnType(Byte.valueOf(StringUtils.trim(bean.getReturn_type())));
            record.setSign(StringUtils.trim(bean.getSign()));
            record.setVerifySign(verifySign);
            record.setRetTime(new Date());
            record.setIp(ContextHelper.getRealIp());
        } catch (Exception ex) {
            logger.error("支付通知异常", ex);
        }

        pmdNotifyLogMapper.insertSelective(record);
    }

    // 处理页面返回结果
    @Transactional
    public void returnPage(PayNotifyBean bean) {

        logger.info("bean.toString()=" + bean.toString());
        processPayNotifyBean(bean);
    }

    // 处理服务器后台结果通知
    @Transactional
    public boolean notifyPage(PayNotifyBean bean) {

        logger.info("bean.toString()=" + bean.toString());
        processPayNotifyBean(bean);
        return true;
    }

    // （服务器通知）签名校验
    public static String verifySign(PayNotifyBean bean) {
        //orderDate=20140319163131&orderNo=1403190004&amount=10.00&jylsh=1303190000001&tranStat=1&return_type=1
        String md5Str = "orderDate=" + bean.getOrderDate() +
                "&orderNo=" + bean.getOrderNo() +
                "&amount=" + bean.getAmount() +
                "&jylsh=" + bean.getJylsh() +
                "&tranStat=" + bean.getTranStat() +
                "&return_type=" + bean.getReturn_type() + key;

        return MD5Util.md5Hex(md5Str, "utf-8");
    }

    // 处理服务器支付结果通知
    @Transactional
    public void processPayNotifyBean(PayNotifyBean bean) {

        try {

            boolean verifySign = StringUtils.equals(bean.getSign(), verifySign(bean));
            // 签名校验成功 且 确认交易成功
            if (verifySign && StringUtils.equals(bean.getTranStat(), "1")) {

                String orderNo = bean.getOrderNo();
                // 党员账单
                PmdMemberPayView pmdMemberPayView = pmdMemberPayService.get(orderNo);
                int payStatus;
                if (pmdMemberPayView == null) {
                    logger.error("[党费收缴]处理支付通知失败，订单号不存在，订单号：" + orderNo);
                } else if (pmdMemberPayView.getHasPay()) {
                    logger.warn("[党费收缴]处理支付通知重复，订单号：" + orderNo);
                } else if ((payStatus = pmdMemberPayView.getPayStatus()) == 0) {
                    logger.warn("[党费收缴]处理支付通知异常，当前不允许缴费，订单号：" + orderNo);
                } else {

                    Integer payMonthId = null;
                    // 根据订单号的前6位，找到订单生成时的缴费月份（防止延时反馈跨月的情况出现）
                    PmdMonth payMonth = pmdMonthService.getMonth(DateUtils.parseDate(orderNo.substring(0, 6), "yyyyMM"));
                    if (payMonth == null) {
                        logger.error("[党费收缴]处理支付结果异常，缴费月份不存在，订单号：" + orderNo);
                    } else {

                        // 用户缴费了，但是支付成功的通知在支部管理员设为延迟缴费之后
                        if (pmdMemberPayView.getMonthId().intValue() == payMonth.getId()
                                && pmdMemberPayView.getIsDelay()) {
                            logger.error("[党费收缴]已经设定为延迟缴费，但是当月收到了缴费成功的通知，订单号：" + orderNo);
                        }

                        // 生成订单时所在月份
                        payMonthId = payMonth.getId();
                        // 此笔账单是否补缴
                        boolean isDelay = StringUtils.equals(orderNo.substring(6, 7), "1");
                        if (isDelay && payStatus != 2) {
                            logger.error("[党费收缴]处理支付结果异常，缴费类型有误，订单号：" + orderNo);
                        } else {
                            int memberId = pmdMemberPayView.getMemberId();
                            if (!isDelay) {

                                // 缴费时，需更新快照
                                PmdMember record = new PmdMember();
                                record.setId(memberId);
                                record.setHasPay(true);
                                record.setRealPay(new BigDecimal(bean.getAmount()));
                                record.setIsOnlinePay(true);
                                record.setPayTime(new Date());

                                PmdMemberExample example = new PmdMemberExample();
                                example.createCriteria().andIdEqualTo(memberId)
                                        .andHasPayEqualTo(false);
                                if(pmdMemberMapper.updateByExampleSelective(record, example)==0){
                                    throw new OpException("更新快照失败");
                                }
                            }
                            PmdMemberPay record = new PmdMemberPay();
                            record.setMemberId(memberId);
                            record.setHasPay(true);
                            record.setRealPay(new BigDecimal(bean.getAmount()));
                            record.setIsOnlinePay(true);
                            record.setPayMonthId(payMonthId);
                            // 把党员生成订单时所在党委、支部，设置为缴费的单位
                            int userId = pmdMemberPayView.getUserId();
                            PmdMember pmdMember = pmdMemberService.get(payMonthId, userId);
                            int partyId = pmdMember.getPartyId();
                            Integer branchId = pmdMember.getBranchId();
                            record.setChargePartyId(partyId);
                            record.setChargeBranchId(branchId);
                            record.setPayTime(new Date());

                            PmdMemberPayExample example = new PmdMemberPayExample();
                            example.createCriteria().andMemberIdEqualTo(memberId)
                                    .andHasPayEqualTo(false);
                            if(pmdMemberPayMapper.updateByExampleSelective(record, example)==0){
                                throw new OpException("更新账单失败");
                            }

                            sysApprovalLogService.add(pmdMember.getId(), userId,
                                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
                                    SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                                    "线上缴费成功", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
                        }
                    }
                }
            }
        } catch (Exception ex) {

            HttpServletRequest request = ContextHelper.getRequest();
            logger.error(String.format("保存支付通知失败，报文内容：%s, IP:%s",
                    JSONUtils.toString(request.getParameterMap(), false), ContextHelper.getRealIp()), ex);
            // 抛出异常，回滚数据库
            throw ex;
        }
    }

    // 跳转页面前的支付确认，生成支付订单号
    @Transactional
    public PayFormBean payConfirm(int monthId) {

        int userId = ShiroHelper.getCurrentUserId();
        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth == null) {
            throw new OpException("操作失败，请稍后再试。");
        }

        PmdMember pmdMember = pmdMemberService.get(monthId, userId);
        PayFormBean payFormBean = createPayFormBean(pmdMember.getId());

        PmdMemberPay record = new PmdMemberPay();
        record.setMemberId(pmdMember.getId());
        record.setOrderNo(payFormBean.getOrderNo());
        if (pmdMemberPayMapper.updateByPrimaryKeySelective(record) == 0) {

            logger.error("确认缴费时，对应的党员账单不存在...%s, %s", monthId, userId);
            throw new OpException("缴费异常，请稍后再试。");
        }

        return payFormBean;
    }

    // 现金支付
    @Transactional
    public void payCash(int memberId) {

        checkAdmin(memberId);

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        int currentMonthId = currentPmdMonth.getId();
        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(memberId);

        int payStatus = pmdMember.getPayStatus();
        if(payStatus==0){
            throw new OpException("缴费失败，请稍后再试。");
        }
        if(pmdMember.getDuePay()==null){
            throw new OpException("请先设置缴纳额度。");
        }

        BigDecimal realPay = pmdMember.getDuePay();
        if(payStatus==1){

            // 缴费时，需更新快照
            PmdMember record = new PmdMember();
            record.setId(memberId);
            record.setHasPay(true);
            record.setRealPay(realPay);
            record.setIsOnlinePay(false);
            record.setPayTime(new Date());
            record.setChargeUserId(ShiroHelper.getCurrentUserId());

            PmdMemberExample example = new PmdMemberExample();
            example.createCriteria().andIdEqualTo(memberId)
                    .andHasPayEqualTo(false);
            if(pmdMemberMapper.updateByExampleSelective(record, example)==0){
                throw new OpException("更新快照失败");
            }
        }

        PmdMemberPay record = new PmdMemberPay();
        record.setMemberId(memberId);
        record.setHasPay(true);
        record.setRealPay(realPay);
        record.setIsOnlinePay(false);
        record.setPayMonthId(currentMonthId);

        // 当前所在的单位快照
        PmdMember _pmdMember = pmdMemberService.get(currentMonthId, pmdMember.getUserId());
        // 把当前所在党委、支部，设置为缴费的单位
        int partyId = _pmdMember.getPartyId();
        Integer branchId = _pmdMember.getBranchId();

        record.setChargePartyId(partyId);
        record.setChargeBranchId(branchId);
        record.setPayTime(new Date());
        record.setChargeUserId(ShiroHelper.getCurrentUserId());

        PmdMemberPayExample example = new PmdMemberPayExample();
        example.createCriteria().andMemberIdEqualTo(memberId)
                .andHasPayEqualTo(false);
        if(pmdMemberPayMapper.updateByExampleSelective(record, example)==0){
            throw new OpException("更新账单失败");
        }

        sysApprovalLogService.add(memberId, pmdMember.getUserId(), SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                "现金支付", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }

    // 延迟缴费
    @Transactional
    public void delay(int pmdMemberId, String delayReason) {

        checkAdmin(pmdMemberId);

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(pmdMemberId);
        // 只有当前缴费月份才允许操作
        if (currentPmdMonth == null || currentPmdMonth.getId().intValue() != pmdMember.getMonthId()) {
            throw new OpException("操作失败，请稍后再试。");
        }

        if(pmdMember.getDuePay()==null){
            throw new OpException("请先设置缴纳额度。");
        }

        PmdMember record = new PmdMember();
        record.setIsDelay(true);
        record.setDelayReason(delayReason);

        PmdMemberExample example = new PmdMemberExample();
        example.createCriteria().andIdEqualTo(pmdMemberId)
                .andHasPayEqualTo(false)
                .andIsDelayEqualTo(false);

        pmdMemberMapper.updateByExampleSelective(record, example);

        sysApprovalLogService.add(pmdMemberId, pmdMember.getUserId(), SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                "延迟缴费", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }

    // 取消延迟缴费
    @Transactional
    public void unDelay(int pmdMemberId) {

        checkAdmin(pmdMemberId);

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(pmdMemberId);
        // 只有当前缴费月份才允许操作
        if (currentPmdMonth == null || currentPmdMonth.getId().intValue() != pmdMember.getMonthId()) {
            throw new OpException("操作失败，请稍后再试。");
        }

        commonMapper.excuteSql("update pmd_member set is_delay=0, delay_reason=null " +
                "where has_pay=0 and is_delay=1 and id=" + pmdMemberId);

        sysApprovalLogService.add(pmdMemberId, pmdMember.getUserId(), SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                "取消延迟缴费", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }

    // 检测支部或直属党支部的操作权限
    public PmdMember checkAdmin(int pmdMemberId) {

        int userId = ShiroHelper.getCurrentUserId();

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        int currentMonthId = currentPmdMonth.getId();

        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(pmdMemberId);
        // 当前所在的单位快照
        PmdMember _pmdMember = pmdMemberService.get(currentMonthId, pmdMember.getUserId());

        // 检测党支部或直属党支部是否已经报送了
        Integer partyId = _pmdMember.getPartyId();
        Integer branchId = _pmdMember.getBranchId();

        if (partyService.isDirectBranch(partyId)) {

            List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(userId);
            Set<Integer> adminPartyIdSet = new HashSet<>();
            adminPartyIdSet.addAll(adminPartyIds);
            if (!adminPartyIdSet.contains(partyId)) {
                throw new UnauthorizedException();
            }

            PmdParty pmdParty = pmdPartyService.get(currentMonthId, partyId);
            if (pmdParty == null || pmdParty.getHasReport()) {
                throw new OpException("数据已经报送，不允许操作。");
            }
        } else {
            List<Integer> adminBranchIds = pmdBranchAdminService.getAdminBranchIds(userId);
            Set<Integer> adminBranchIdSet = new HashSet<>();
            adminBranchIdSet.addAll(adminBranchIds);
            if (!adminBranchIdSet.contains(branchId)) {
                throw new UnauthorizedException();
            }

            PmdBranch pmdBranch = pmdBranchService.get(currentMonthId, partyId, branchId);
            if (pmdBranch == null || pmdBranch.getHasReport()) {
                throw new OpException("数据已经报送，不允许操作。");
            }
        }
        return pmdMember;
    }
}
