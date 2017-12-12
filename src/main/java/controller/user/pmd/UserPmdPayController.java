package controller.user.pmd;

import controller.PmdBaseController;
import domain.pmd.PmdMember;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.pmd.PayFormCampusCardBean;
import service.pmd.PayFormWszfBean;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.PropertiesUtils;

import java.util.Map;

@Controller
@RequestMapping("/user/pmd")
public class UserPmdPayController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 校园统一支付平台
    @RequiresPermissions("userPmdMember:payConfirm")
    @RequestMapping("/payConfirm_wszf")
    public String payConfirm_wszf(int monthId, ModelMap modelMap) {

        int userId = ShiroHelper.getCurrentUserId();

        PmdMember pmdMember = pmdMemberService.get(monthId, userId);
        modelMap.put("pmdMember", pmdMember);

        PayFormWszfBean payFormBean = pmdPayWszfService.createPayFormBean(pmdMember.getId());
        modelMap.put("payFormBean", payFormBean);

        modelMap.put("pay_url", PropertiesUtils.getString("pay.wszf.url"));

        // test
        /*PayNotifyWszfBean bean = new PayNotifyWszfBean();
        bean.setOrderDate(payFormBean.getOrderDate());
        bean.setOrderNo(payFormBean.getOrderNo());
        bean.setAmount(payFormBean.getAmount());
        bean.setJylsh(String.valueOf(1303190000001L + pmdMember.getId()));
        bean.setTranStat("1");
        bean.setReturn_type("1");
        String sign = pmdPayWszfService.verifySign(bean);
        String ret = "orderDate=" + bean.getOrderDate() +
                "&orderNo=" + bean.getOrderNo() +
                "&amount=" + bean.getAmount() +
                "&jylsh=" + bean.getJylsh() +
                "&tranStat=" + bean.getTranStat() +
                "&return_type=" + bean.getReturn_type() +
                "&sign=" + sign;
        modelMap.put("ret", ret);*/
        // test

        return "user/pmd/payConfirm_wszf";
    }
    @RequiresPermissions("userPmdMember:payConfirm")
    @RequestMapping(value = "/payConfirm_wszf", method = RequestMethod.POST)
    @ResponseBody
    public Map do_payConfirm_wszf(int monthId) {

        PayFormWszfBean payFormBean = pmdPayWszfService.payConfirm(monthId);
        logger.info(addLog(SystemConstants.LOG_PMD, "支付已确认，跳转至支付页面...%s",
                JSONUtils.toString(payFormBean, false)));

        return success(FormUtils.SUCCESS);
    }

    // 校园卡
    @RequiresPermissions("userPmdMember:payConfirm")
    @RequestMapping("/payConfirm_campuscard")
    public String payConfirm_campuscard(int monthId, ModelMap modelMap) {

        int userId = ShiroHelper.getCurrentUserId();

        PmdMember pmdMember = pmdMemberService.get(monthId, userId);
        modelMap.put("pmdMember", pmdMember);

        PayFormCampusCardBean payFormBean = pmdPayCampusCardService.createPayFormBean(pmdMember.getId());
        modelMap.put("payFormBean", payFormBean);

        modelMap.put("pay_url", PropertiesUtils.getString("pay.campuscard.url"));

        // test
        /*PayNotifyCampusCardBean bean = new PayNotifyCampusCardBean();
        bean.setPaycode(payFormBean.getPaycode());
        bean.setPayitem("ZZBGZ001");
        bean.setPayer(payFormBean.getPayer());
        bean.setPayertype(payFormBean.getPayertype());
        bean.setSn(payFormBean.getSn());
        bean.setAmt(payFormBean.getAmt());
        bean.setPaid("true");
        bean.setPaidtime(DateUtils.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));

        String sign = pmdPayCampusCardService.signMd5Str(bean);
        String ret = "paycode=" + bean.getPaycode() +
                "&payitem=" + bean.getPayitem() +
                "&payer=" + bean.getPayer() +
                "&payertype=" + bean.getPayertype() +
                "&sn=" + bean.getSn() +
                "&amt=" + bean.getAmt() +
                "&paid=" + bean.getPaid() +
                "&paidtime=" + bean.getPaidtime() +
                "&sign=" + sign;
        modelMap.put("ret", ret);*/
        // test

        return "user/pmd/payConfirm_campuscard";
    }

    @RequiresPermissions("userPmdMember:payConfirm")
    @RequestMapping(value = "/payConfirm_campuscard", method = RequestMethod.POST)
    @ResponseBody
    public Map do_payConfirm_campuscard(int monthId) {

        PayFormCampusCardBean payFormBean = pmdPayCampusCardService.payConfirm(monthId);
        logger.info(addLog(SystemConstants.LOG_PMD, "支付已确认，跳转至支付页面...%s",
                JSONUtils.toString(payFormBean, false)));

        return success(FormUtils.SUCCESS);
    }
}
