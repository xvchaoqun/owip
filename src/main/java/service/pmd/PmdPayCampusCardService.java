package service.pmd;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import controller.global.OpException;
import domain.pmd.PmdMember;
import domain.pmd.PmdMemberExample;
import domain.pmd.PmdMemberPay;
import domain.pmd.PmdMemberPayExample;
import domain.pmd.PmdMemberPayView;
import domain.pmd.PmdMonth;
import domain.pmd.PmdNotifyCampuscard;
import domain.pmd.PmdOrderCampuscard;
import domain.pmd.PmdOrderCampuscardExample;
import domain.sys.SysUserView;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;
import sys.utils.MD5Util;
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PmdPayCampusCardService extends BaseMapper {

    @Autowired
    private PmdMemberService pmdMemberService;
    @Autowired
    private PmdMemberPayService pmdMemberPayService;
    @Autowired
    private PmdMonthService pmdMonthService;
    @Autowired
    private PmdPayService pmdPayService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;


    private Logger logger = LoggerFactory.getLogger(getClass());

    public final static String paycode = PropertiesUtils.getString("pay.campuscard.paycode");
    public final static String keys = PropertiesUtils.getString("pay.campuscard.keys");

    public final static String queryUrl = PropertiesUtils.getString("pay.campuscard.query.url");

    public final static String closeTradeUrl = PropertiesUtils.getString("pay.campuscard.closeTrade.url");
    public final static String closeTradeAppid = PropertiesUtils.getString("pay.campuscard.closeTrade.appid");
    public final static String closeTradeSalt = PropertiesUtils.getString("pay.campuscard.closeTrade.salt");

    // 订单号起始值
    private final static int orderNoOffset = 10240000;

    // 新建订单号，缴费时的月份(yyyyMM) + 是否补缴(0,1) + (党员快照ID + 订单号起始值)
    private String createOrderNo(int pmdMemberId, PmdMonth currentPmdMonth,
                                 boolean isDelay, byte payWay) {

        int _orderNo = pmdMemberId + orderNoOffset;
        if (_orderNo > 99999999) {
            throw new OpException("缴费失败，原因：订单号错误。");
        }

        // 根据订单生成次数，给订单号末尾加上后缀
        String suffix = "";
        {
            PmdOrderCampuscardExample exmaple = new PmdOrderCampuscardExample();
            exmaple.createCriteria().andMemberIdEqualTo(pmdMemberId);
            long count = pmdOrderCampuscardMapper.countByExample(exmaple);
            if (count > 0) {
                // 系统只能处理99及以下的订单生成次数
                Assert.isTrue(count < 100, "超过最大允许创建订单次数");
                suffix = String.format("%02d", count);
            }
        }

        return DateUtils.formatDate(currentPmdMonth.getPayMonth(), "yyyyMM")
                + (isDelay ? "1" : "0")
                + payWay
                + _orderNo + suffix;
    }

    // 构建支付表单参数
    private PmdOrderCampuscard confirmOrder(String oldOrderNo, int pmdMemberId, boolean isSelfPay) {

        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(pmdMemberId);
        if (pmdMember == null || pmdMember.getHasPay()) {
            //logger.error("");
            throw new OpException("记录不存在或已经支付，请刷新页面再试。");
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

            throw new OpException("缴费状态异常，请联系管理员。");
        }

        // 重复确认一下当前是否允许缴费
        if (pmdMember.getPayStatus() == 0) {
            throw new OpException("缴费失败，当前不允许缴费。");
        }
        // 重复验证是否有成功的缴费记录
        {
            PmdOrderCampuscardExample example = new PmdOrderCampuscardExample();
            example.createCriteria().andMemberIdEqualTo(pmdMemberId).andIsSuccessEqualTo(true);
            if (pmdOrderCampuscardMapper.countByExample(example) > 0) {
                throw new OpException("重复缴费，请联系管理员处理。");
            }
        }

        String payer = null;
        String payername = null;
        String snoIdName = null;
        if (isSelfPay) {
            // 本人缴费
            SysUserView uv = pmdMember.getUser();
            payer = uv.getCode();
            payername = uv.getRealname();
            snoIdName = uv.getCode();
        } else {
            // 代缴
            SysUserView uv = ShiroHelper.getCurrentUser();
            payer = uv.getCode();
            payername = uv.getRealname();
            snoIdName = uv.getCode();
        }

        PmdOrderCampuscard newOrder = new PmdOrderCampuscard();
        newOrder.setPaycode(paycode);
        newOrder.setPayer(payer);
        // 缴费人账号类型，1：学工号，2：服务平台账号，3：校园卡号，4：身份证号
        newOrder.setPayertype("1");
        newOrder.setPayername(payername);
        newOrder.setAmt(pmdMember.getDuePay().toString());
        newOrder.setMacc("");
        newOrder.setCommnet("");
        newOrder.setSnoIdName(snoIdName);

        boolean orderIsNotExist = false;
        // 如果订单已存在（最近一次的）且与本次支付信息不相同，则生成新的订单
        //PmdMemberPay pmdMemberPay = pmdMemberPayMapper.selectByPrimaryKey(pmdMemberId);
        //String oldOrderNo = pmdMemberPay.getOrderNo();
        PmdOrderCampuscard oldOrder = null;
        if (StringUtils.isNotBlank(oldOrderNo)) {
            oldOrder = pmdOrderCampuscardMapper.selectByPrimaryKey(oldOrderNo);
        }
        if (oldOrder!=null) {
            if (!StringUtils.equals(newOrder.getPaycode(), oldOrder.getPaycode())
                    || !StringUtils.equals(newOrder.getPayer(), oldOrder.getPayer())
                    || !StringUtils.equals(newOrder.getPayertype(), oldOrder.getPayertype())
                    || !StringUtils.equals(newOrder.getPayername(), oldOrder.getPayername())
                    || !StringUtils.equals(newOrder.getAmt(), oldOrder.getAmt())
                    || !StringUtils.equals(newOrder.getMacc(), oldOrder.getMacc())
                    || !StringUtils.equals(newOrder.getCommnet(), oldOrder.getCommnet())
                    || !StringUtils.equals(newOrder.getSnoIdName(), oldOrder.getSnoIdName())) {

                logger.info("订单信息变更，生成新订单号");
                orderIsNotExist = true;
            }
        } else {
            logger.info("订单信息不存在，生成订单号");
            orderIsNotExist = true;
        }

        if (orderIsNotExist) {
            // 使用真实的缴费月份当订单号的日期部分，在处理支付通知时，使用该月份为支付月份
            String orderNo = createOrderNo(pmdMemberId, currentPmdMonth,
                    pmdMember.getIsDelay(), SystemConstants.PMD_PAY_WAY_CAMPUSCARD);
            newOrder.setSn(orderNo);
            String md5Str = keys + paycode + newOrder.getSn() +
                    newOrder.getAmt() + newOrder.getPayer() +
                    newOrder.getPayername() + StringUtils.reverse(keys);
            // 全大写
            String sign = MD5Util.md5Hex(md5Str, "utf-8").toUpperCase();
            newOrder.setSign(sign);

            int userId = ShiroHelper.getCurrentUserId();

            newOrder.setMemberId(pmdMemberId);
            newOrder.setUserId(userId);
            newOrder.setIsSuccess(false);
            newOrder.setIsClosed(false);
            newOrder.setCreateTime(new Date());
            newOrder.setIp(ContextHelper.getRealIp());

            pmdOrderCampuscardMapper.insertSelective(newOrder);

            sysApprovalLogService.add(pmdMemberId, userId,
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_NOT_SELF,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                    "支付已确认，即将跳转支付页面", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    "订单号：" + orderNo);

            return newOrder;
        }

        return pmdOrderCampuscardMapper.selectByPrimaryKey(oldOrderNo);
    }

    // 无论如何，都要保存服务器支付通知
    public void savePayNotifyBean(PayNotifyCampusCardBean bean) {

        PmdNotifyCampuscard record = new PmdNotifyCampuscard();
        try {
            record.setPaycode(bean.getPaycode());
            record.setPayitem(bean.getPayitem());
            record.setPayer(bean.getPayer());
            record.setPayertype(bean.getPayertype());
            record.setSn(bean.getSn());
            record.setAmt(bean.getAmt());
            record.setPaid(bean.getPaid());
            record.setPaidtime(bean.getPaidtime());
            record.setSign(StringUtils.trim(bean.getSign()));
            record.setVerifySign(verifySign(bean));
            record.setRetTime(new Date());
            record.setIp(ContextHelper.getRealIp());
        } catch (Exception ex) {
            logger.error("支付通知异常", ex);
        }

        pmdNotifyCampuscardMapper.insertSelective(record);
    }

    // 处理服务器后台结果通知
    @Transactional
    public boolean notify(PayNotifyCampusCardBean bean) {

        processPayCallbackBean(bean);
        return true;
    }

    public static String signMd5Str(PayNotifyCampusCardBean bean) {

        String md5Str = keys + bean.getPaycode() + bean.getSn() +
                bean.getAmt() + bean.getPayer() +
                bean.getPaid() + bean.getPaidtime() + StringUtils.reverse(keys);

        return md5Str;
    }

    // （服务器通知）签名校验
    public boolean verifySign(PayNotifyCampusCardBean bean) {

        String md5Str = signMd5Str(bean);
        String verifySign = MD5Util.md5Hex(md5Str, "utf-8");

        boolean ret = StringUtils.equalsIgnoreCase(bean.getSign(), verifySign);
        if (!ret) {
            logger.warn("签名校验失败，{}, md5Str={}, verifySign={}", bean.toString(), md5Str, verifySign);
        }

        return ret;
    }

    // 处理服务器支付结果通知
    @Transactional
    public void processPayCallbackBean(PayNotifyCampusCardBean bean) {

        try {
            // 签名校验成功 且 确认交易成功
            if (verifySign(bean) && StringUtils.equals(bean.getPaid(), "true")) {

                String orderNo = bean.getSn();
                PmdOrderCampuscard pmdOrder = pmdOrderCampuscardMapper.selectByPrimaryKey(orderNo);
                if (pmdOrder == null) {
                    logger.error("[党费收缴]处理支付通知失败，订单号不存在，订单号：{}", orderNo);
                } else if (pmdOrder.getIsSuccess()) {
                    logger.warn("[党费收缴]处理支付通知重复，订单号：{}", orderNo);
                } else {

                    {
                        // 更新订单状态为成功支付
                        PmdOrderCampuscard record = new PmdOrderCampuscard();
                        record.setSn(orderNo);
                        record.setIsSuccess(true);
                        pmdOrderCampuscardMapper.updateByPrimaryKeySelective(record);
                    }

                    int memberId = pmdOrder.getMemberId();
                    // 更新党员账单
                    PmdMemberPayView pmdMemberPayView = pmdMemberPayService.get(memberId);
                    if (pmdMemberPayView == null) {

                        logger.error("[党费收缴]处理支付通知失败，缴费记录不存在，订单号：{}", orderNo);
                        return;
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
                    String code = StringUtils.trimToNull(bean.getPayer());
                    if (code != null) {
                        SysUserView uv = sysUserService.findByCode(code);
                        if (uv != null && uv.getUserId().intValue() != pmdMemberPayView.getUserId()) {
                            chargeUserId = uv.getUserId();
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
                    } else {

                        int payMonthId = payMonth.getId();
                        // 用户缴费了，但是支付成功的通知在支部管理员设为延迟缴费之后
                        if (pmdMemberPayView.getMonthId().intValue() == payMonthId
                                && pmdMemberPayView.getIsDelay()) {
                            logger.error("[党费收缴]已经设定为延迟缴费，但是当月收到了缴费成功的通知，订单号：{}", orderNo);
                        }

                        // 此笔账单是否补缴
                        boolean isDelay = StringUtils.equals(orderNo.substring(6, 7), "1");
                        if (isDelay && payStatus != 2) {
                            logger.error("[党费收缴]处理支付结果异常，补缴状态异常，订单号：{}", orderNo);
                        } else {

                            if (!isDelay) {
                                // 缴费时，需更新快照
                                PmdMember record = new PmdMember();
                                record.setId(memberId);
                                record.setHasPay(true);
                                record.setRealPay(new BigDecimal(bean.getAmt()));
                                record.setIsSelfPay(isSelfPay);
                                record.setPayTime(new Date());
                                record.setChargeUserId(chargeUserId);

                                PmdMemberExample example = new PmdMemberExample();
                                example.createCriteria().andIdEqualTo(memberId)
                                        .andHasPayEqualTo(false);
                                if (pmdMemberMapper.updateByExampleSelective(record, example) == 0) {
                                    throw new OpException("更新快照失败");
                                }
                            }
                            PmdMemberPay record = new PmdMemberPay();
                            record.setMemberId(memberId);
                            record.setHasPay(true);
                            record.setRealPay(new BigDecimal(bean.getAmt()));
                            record.setIsSelfPay(isSelfPay);
                            record.setChargeUserId(chargeUserId);
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
                            if (pmdMemberPayMapper.updateByExampleSelective(record, example) == 0) {
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
    public PmdOrderCampuscard payConfirm(int pmdMemberId, boolean isSelfPay) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth == null) {
            throw new OpException("未到缴费时间。");
        }

        int userId = ShiroHelper.getCurrentUserId();
        SysUserView uv = sysUserService.findById(userId);
        if (uv.getSource() == SystemConstants.USER_SOURCE_ADMIN
                || uv.getSource() == SystemConstants.USER_SOURCE_REG) {
            throw new OpException("您的账号是系统注册账号，不能使用校园卡支付。");
        }

        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(pmdMemberId);
        if (pmdMember == null) {
            throw new OpException("缴费记录不存在。");
        }

        PmdMemberPay pmdMemberPay = pmdMemberPayMapper.selectByPrimaryKey(pmdMemberId);
        String oldOrderNo = pmdMemberPay.getOrderNo();

        // 确认订单信息
        PmdOrderCampuscard pmdOrder = confirmOrder(oldOrderNo, pmdMemberId, isSelfPay);

        PmdMemberPay record = new PmdMemberPay();
        record.setMemberId(pmdMemberId);
        record.setOrderNo(pmdOrder.getSn());
        record.setOrderUserId(userId);

        if (pmdMemberPayMapper.updateByPrimaryKeySelective(record) == 0) {

            logger.error("确认缴费时，对应的党员账单不存在...%s, %s", pmdMemberId, userId);
            throw new OpException("缴费异常，请稍后再试。");
        }

        // 如果新生成的订单号和原订单号不一致，则关闭原订单号
        if(StringUtils.isNotBlank(oldOrderNo) && !StringUtils.equals(oldOrderNo, pmdOrder.getSn())){
            try {
                closeTrade(oldOrderNo);
            }catch (Exception ex){
                logger.error("关闭订单"+oldOrderNo+"异常", ex);
            }
        }

        // 检查一下生成的所有订单（不包括当前生成的订单），是否已经有支付成功的记录，如有则不允许跳转
        {
            PmdOrderCampuscardExample example = new PmdOrderCampuscardExample();
            example.createCriteria().andMemberIdEqualTo(pmdMemberId).andSnNotEqualTo(pmdOrder.getSn());
            List<PmdOrderCampuscard> pmdOrderCampuscards = pmdOrderCampuscardMapper.selectByExample(example);

            String sn = null;
            String queryRet = null;
            try {
                for (PmdOrderCampuscard pmdOrderCampuscard : pmdOrderCampuscards) {

                    sn = pmdOrderCampuscard.getSn();
                    queryRet = query(sn, pmdOrderCampuscard.getPayer());

                    if(StringUtils.isNotBlank(queryRet)) { // 支付系统查询订单号不存在的话，接口返回的内容为空

                        Gson gson = new Gson();
                        JsonObject jsonObject = gson.fromJson(queryRet, JsonObject.class);
                        JsonElement paid = jsonObject.get("paid");
                        JsonElement payer = jsonObject.get("payer");

                        if (paid.getAsBoolean()) {

                            String code = payer.getAsString();
                            SysUserView payUser = sysUserService.findByCode(code);

                            logger.warn("当前缴费记录已由{}(工号：{})支付成功，支付订单号{}，请不要重复支付。",
                                    payUser!=null?payUser.getRealname():"",code, sn);
                            throw new OpException("当前缴费记录已由{}(工号：{})支付成功，支付订单号{}，请不要重复支付。",
                                    payUser!=null?payUser.getRealname():"",code, sn);
                        }
                    }
                }
            }catch (IOException ex){

                logger.error(String.format("校园卡支付平台异常, sn=%s, ret=%s", sn, queryRet), ex);
                throw new OpException("校园卡支付平台异常，请稍后再试。");
            }
        }

        return pmdOrder;
    }

    private void closeTrade(String sn) throws IOException {

        String _signStr = String.format("appId=%s&orderNo=%s&salt=%s",
                closeTradeAppid, sn, closeTradeSalt);
        String sign = MD5Util.md5Hex(_signStr, "utf-8");

        List<BasicNameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("appId", closeTradeAppid));
        urlParameters.add(new BasicNameValuePair("orderNo", sn));
        urlParameters.add(new BasicNameValuePair("sign", sign));
        HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(closeTradeUrl);
        httppost.setEntity(postParams);
        CloseableHttpResponse res = httpclient.execute(httppost);

        if(res.getStatusLine().getStatusCode()== HttpStatus.SC_OK){

            String ret = EntityUtils.toString(res.getEntity());
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(ret, JsonObject.class);
            JsonElement code = jsonObject.get("code");
            if(code!=null &&
                    (StringUtils.equals(code.getAsString(), "0000")
                            || StringUtils.contains(code.getAsString(), "2005"))){

                PmdOrderCampuscard record = new PmdOrderCampuscard();
                record.setSn(sn);
                record.setIsClosed(true);
                pmdOrderCampuscardMapper.updateByPrimaryKeySelective(record);

                logger.info("关闭订单{}成功，接口响应内容：{}", sn, ret);
            }else{
                logger.warn("关闭订单{}失败，接口响应内容：{}", sn, ret);
            }
        }
        /*
            0000：更新成功
            2004：订单不存在
            2002：该交易已成功，请确认
            2005：该交易已关闭，请确认
            3001：该交易正在处理中，请等待...（只针对于一卡通支付的情况）
            9995：数据库异常，更新失败
        */
    }

    // 查询订单结果
    public String query(String sn, String payer) throws IOException {

        String payertype = "1";
        String sign = MD5Util.md5Hex(paycode+payertype+payer+sn, "utf-8");

        List<BasicNameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("paycode", paycode));
        urlParameters.add(new BasicNameValuePair("sn", sn));
        urlParameters.add(new BasicNameValuePair("payertype", payertype));
        urlParameters.add(new BasicNameValuePair("payer", payer));
        urlParameters.add(new BasicNameValuePair("sign", sign));
        HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(queryUrl);
        httppost.setEntity(postParams);
        CloseableHttpResponse res = httpclient.execute(httppost);

        return EntityUtils.toString(res.getEntity());
    }

    // 根据查询订单返回结果，判断是否支付成功
    public boolean hasPaid(String queryRet) {

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(queryRet, JsonObject.class);
        JsonElement paid = jsonObject.get("paid");

        return paid.getAsBoolean();
    }
}
