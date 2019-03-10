package service.pmd;

import controller.global.OpException;
import domain.pmd.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.PmdConstants;
import sys.constants.SystemConstants;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class PmdOrderWszfService extends PmdBaseMapper {

    @Autowired
    private PmdMemberService pmdMemberService;
    @Autowired
    private PmdMemberPayService pmdMemberPayService;
    @Autowired
    private PmdMonthService pmdMonthService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;


    private Logger logger = LoggerFactory.getLogger(getClass());

    public final static String callback_url = "http://zzgz.xxx.edu.cn/pmd/pay/callback/wszf";
    public final static String xmpch = PropertiesUtils.getString("pay.wszf.id");
    public final static String key = PropertiesUtils.getString("pay.wszf.key");

    // 订单号起始值
    private final static int orderNoOffset = 10240000;

    // 新建订单号，缴费时的月份(yyyyMM) + 是否补缴(0,1) + (党员快照ID + 订单号起始值)
    private String createOrderNo(int pmdMemberId, PmdMonth currentPmdMonth,
                                boolean isDelay, byte payWay) {

        int _orderNo = pmdMemberId + orderNoOffset;
        if (_orderNo > 99999999) {
            throw new OpException("缴费失败，原因：订单号错误。");
        }

        return DateUtils.formatDate(currentPmdMonth.getPayMonth(), "yyyyMM")
                + (isDelay ? "1" : "0")
                + payWay
                + _orderNo;
    }

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
        String orderNo = createOrderNo(pmdMemberId, currentPmdMonth,
                pmdMember.getIsDelay(), PmdConstants.PMD_PAY_WAY_WSZF);
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
    public void savePayNotifyBean(HttpServletRequest request) {

        
        boolean verifySign = StringUtils.equals(request.getParameter("sign"), verifySign(request));

        PmdNotify record = new PmdNotify();
        try {
            record.setSn(request.getParameter("orderNo"));
            record.setAmt(request.getParameter("amount"));
            record.setParams(JSONUtils.toString(request.getParameterMap(), false));
            record.setIsSuccess(StringUtils.equals(request.getParameter("tranStat"), "1"));
            record.setVerifySign(verifySign);
            record.setRetTime(new Date());
            record.setIp(ContextHelper.getRealIp());
        } catch (Exception ex) {
            logger.error("支付通知异常", ex);
        }

        pmdNotifyMapper.insertSelective(record);
    }

    // 处理返回结果
    @Transactional
    public void callback(HttpServletRequest request) {

        processPayNotifyBean(request);
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
    public static String verifySign(HttpServletRequest request) {
        //orderDate=20140319163131&orderNo=1403190004&amount=10.00&jylsh=1303190000001&tranStat=1&return_type=1
        String md5Str = "orderDate=" + request.getParameter("orderDate") +
                "&orderNo=" + request.getParameter("orderNo") +
                "&amount=" + request.getParameter("amount") +
                "&jylsh=" + request.getParameter("jylsh") +
                "&tranStat=" + request.getParameter("tranStat") +
                "&return_type=" + request.getParameter("return_type") + key;

        return MD5Util.md5Hex(md5Str, "utf-8");
    }

    // 处理服务器支付结果通知
    @Transactional
    public void processPayNotifyBean(HttpServletRequest request) {

        String sign = request.getParameter("sign");
        String tranStat = request.getParameter("tranStat");
        String orderNo = request.getParameter("orderNo");
        String amount = request.getParameter("amount");
        try {

            boolean verifySign = StringUtils.equals(sign, verifySign(request));
            // 签名校验成功 且 确认交易成功
            if (verifySign && StringUtils.equals(tranStat, "1")) {

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
                                record.setRealPay(new BigDecimal(amount));
                                record.setIsSelfPay(true);
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
                            record.setRealPay(new BigDecimal(amount));
                            record.setIsSelfPay(true);
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
