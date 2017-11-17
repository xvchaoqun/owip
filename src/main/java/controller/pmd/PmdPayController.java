package controller.pmd;

import controller.PmdBaseController;
import domain.pmd.PmdMemberPayView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import service.pmd.PayNotifyBean;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lm on 2017/11/7.
 */
@Controller
@RequestMapping("/pmd/pay")
public class PmdPayController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/returnPage")
    public String returnPage(PayNotifyBean bean, HttpServletRequest request, ModelMap modelMap) {

        logger.info("pmd returnPage request.getParameterMap()=" +JSONUtils.toString(request.getParameterMap(), false));
        // 保存原始的支付通知
        pmdPayService.savePayNotifyBean(bean);

        pmdPayService.returnPage(bean);

        PmdMemberPayView pmdMemberPayView = pmdMemberPayService.get(bean.getOrderNo());
        modelMap.put("pmdMemberPayView", pmdMemberPayView);

        return "pmd/pay/return_page";
    }

    @RequestMapping("/notifyPage")
    public void notifyPage(PayNotifyBean bean, HttpServletRequest request,
                           HttpServletResponse response, ModelMap modelMap) throws IOException {

        logger.info("pmd notifyPage request.getParameterMap()=" + JSONUtils.toString(request.getParameterMap(), false));
        // 保存原始的支付通知
        pmdPayService.savePayNotifyBean(bean);

        boolean ret = pmdPayService.notifyPage(bean);
        // 支付服务器要求返回字符串success
        response.getWriter().write(ret ? "success" : "failed");
    }
}
