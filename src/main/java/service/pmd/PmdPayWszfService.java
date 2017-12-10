package service.pmd;

import controller.global.OpException;
import domain.pmd.PmdMember;
import domain.pmd.PmdMemberExample;
import domain.pmd.PmdMemberPay;
import domain.pmd.PmdMemberPayExample;
import domain.pmd.PmdMemberPayView;
import domain.pmd.PmdMonth;
import domain.pmd.PmdNotifyWszfLog;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;
import sys.utils.MD5Util;
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class PmdPayWszfService extends BaseMapper {

    @Autowired
    private PmdMemberService pmdMemberService;
    @Autowired
    private PmdMemberPayService pmdMemberPayService;
    @Autowired
    private PmdMonthService pmdMonthService;
    @Autowired
    private PmdPayService pmdPayService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;


    private Logger logger = LoggerFactory.getLogger(getClass());

    public final static String callback_url = "http://zzbgz.bnu.edu.cn/pmd/pay/callback/wszf";
    public final static String xmpch = PropertiesUtils.getString("pay.wszf.id");
    public final static String key = PropertiesUtils.getString("pay.wszf.key");

    // 构建支付表单参数
    public PayFormWszfBean createPayFormBean(int pmdMemberId) {

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

        // 重复确认一下当前是否允许缴费
        if (pmdMember.getPayStatus() == 0) {
            throw new OpException("缴费失败，原因：当前不允许缴费。");
        }

        // 使用真实的缴费月份当订单号的日期部分，在处理支付通知时，使用该月份为支付月份
        String orderNo = pmdPayService.createOrderNo(pmdMemberId, currentPmdMonth,
                pmdMember.getIsDelay(), SystemConstants.PMD_PAY_WAY_WSZF);
        String orderDate = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        String amount = pmdMember.getDuePay().toString();
        String md5Str = "orderDate=" + orderDate +
                "&orderNo=" + orderNo +
                "&amount=" + amount +
                "&xmpch=" + xmpch +
                "&return_url=" + callback_url +
                "&notify_url=" + callback_url + key;
        String sign = MD5Util.md5Hex(md5Str, "utf-8");

        PayFormWszfBean bean = new PayFormWszfBean();
        bean.setOrderDate(orderDate);
        bean.setOrderNo(orderNo);
        bean.setXmpch(xmpch);
        bean.setAmount(amount);
        bean.setReturn_url(callback_url);
        bean.setNotify_url(callback_url);
        bean.setSign(sign);

        return bean;
    }

    // 无论如何，都要保存服务器支付通知
    public void savePayNotifyBean(PayNotifyWszfBean bean) {

        boolean verifySign = StringUtils.equals(bean.getSign(), verifySign(bean));

        PmdNotifyWszfLog record = new PmdNotifyWszfLog();
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

        pmdNotifyWszfLogMapper.insertSelective(record);
    }

    // 处理返回结果
    @Transactional
    public void callback(PayNotifyWszfBean bean) {

        logger.info("bean.toString()=" + bean.toString());
        processPayNotifyBean(bean);
    }

  /*  // 处理服务器后台结果通知
    @Transactional
    public boolean notifyPage(PayNotifyWszfBean bean) {

        logger.info("bean.toString()=" + bean.toString());
        processPayNotifyBean(bean);
        return true;
    }
*/
    // （服务器通知）签名校验
    public static String verifySign(PayNotifyWszfBean bean) {
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
    public void processPayNotifyBean(PayNotifyWszfBean bean) {

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
    public PayFormWszfBean payConfirm(int monthId) {

        int userId = ShiroHelper.getCurrentUserId();
        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth == null) {
            throw new OpException("操作失败，请稍后再试。");
        }

        PmdMember pmdMember = pmdMemberService.get(monthId, userId);
        PayFormWszfBean payFormBean = createPayFormBean(pmdMember.getId());

        PmdMemberPay record = new PmdMemberPay();
        record.setMemberId(pmdMember.getId());
        record.setOrderNo(payFormBean.getOrderNo());
        if (pmdMemberPayMapper.updateByPrimaryKeySelective(record) == 0) {

            logger.error("确认缴费时，对应的党员账单不存在...%s, %s", monthId, userId);
            throw new OpException("缴费异常，请稍后再试。");
        }

        return payFormBean;
    }
}
