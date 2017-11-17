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
import service.pmd.PayFormBean;
import service.pmd.PayNotifyBean;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import java.util.Map;

@Controller
@RequestMapping("/user/pmd")
public class UserPmdPayController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("userPmdMember:payConfirm")
    @RequestMapping("/pmdMember_payConfirm")
    public String pmdMember_payConfirm(int monthId, ModelMap modelMap) {

        int userId = ShiroHelper.getCurrentUserId();

        PmdMember pmdMember = pmdMemberService.get(monthId, userId);
        modelMap.put("pmdMember", pmdMember);

        PayFormBean payFormBean = pmdPayService.createPayFormBean(pmdMember.getId());
        modelMap.put("payFormBean", payFormBean);

        // test
        PayNotifyBean bean = new PayNotifyBean();
        bean.setOrderDate(payFormBean.getOrderDate());
        bean.setOrderNo(payFormBean.getOrderNo());
        bean.setAmount(payFormBean.getAmount());
        bean.setJylsh(String.valueOf(1303190000001L + pmdMember.getId()));
        bean.setTranStat("1");
        bean.setReturn_type("1");
        String sign = pmdPayService.verifySign(bean);
        String ret = "orderDate=" + bean.getOrderDate() +
                "&orderNo=" + bean.getOrderNo() +
                "&amount=" + bean.getAmount() +
                "&jylsh=" + bean.getJylsh() +
                "&tranStat=" + bean.getTranStat() +
                "&return_type=" + bean.getReturn_type() +
                "&sign=" + sign;
        modelMap.put("ret", ret);
        // test

        return "user/pmd/pmdMember_payConfirm";
    }

    @RequiresPermissions("userPmdMember:payConfirm")
    @RequestMapping(value = "/pmdMember_payConfirm", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMember_payConfirm(int monthId) {

        PayFormBean payFormBean = pmdPayService.payConfirm(monthId);
        logger.info(addLog(SystemConstants.LOG_PMD, "支付已确认，跳转至支付页面...%s",
                JSONUtils.toString(payFormBean, false)));

        return success(FormUtils.SUCCESS);
    }
}
