package controller.pmd;

import controller.PmdBaseController;
import domain.pmd.PmdMemberPayView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import service.pmd.PayNotifyCampusCardBean;
import service.pmd.PayNotifyWszfBean;
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

    // 校园统一支付平台
    @RequestMapping("/callback/wszf")
    public String returnPage(PayNotifyWszfBean bean, HttpServletRequest request, HttpServletResponse response,
                             ModelMap modelMap) throws IOException {

        logger.info("pmd request.getParameterMap()=" +JSONUtils.toString(request.getParameterMap(), false));
        // 保存原始的支付通知
        pmdPayWszfService.savePayNotifyBean(bean);

        pmdPayWszfService.callback(bean);

        if(StringUtils.equals(bean.getReturn_type(), "1")) {

            // 页面返回同步通知
            PmdMemberPayView pmdMemberPayView = pmdMemberPayService.get(bean.getOrderNo());
            modelMap.put("pmdMemberPayView", pmdMemberPayView);

            return "pmd/pay/return_page";
        }else if(StringUtils.equals(bean.getReturn_type(), "2")) {
            // 服务器返回异步通知
            response.getWriter().write("success");
        }

        return null;
    }
    /*@RequestMapping("/notifyPage")
    public void notifyPage(PayNotifyWszfBean bean, HttpServletRequest request,
                           HttpServletResponse response, ModelMap modelMap) throws IOException {

        logger.info("pmd notifyPage request.getParameterMap()=" + JSONUtils.toString(request.getParameterMap(), false));
        // 保存原始的支付通知
        pmdPayWszfService.savePayNotifyBean(bean);

        boolean ret = pmdPayWszfService.notifyPage(bean);
        // 支付服务器要求返回字符串success
        response.getWriter().write(ret ? "success" : "failed");
    }*/

    // 校园卡
    @RequestMapping("/callback/campuscard")
    public void callback(PayNotifyCampusCardBean bean, HttpServletRequest request,
                           HttpServletResponse response, ModelMap modelMap) throws IOException {

        logger.info("pmd callback request.getParameterMap()=" + JSONUtils.toString(request.getParameterMap(), false));
        // 保存原始的支付通知
        pmdPayCampusCardService.savePayNotifyBean(bean);

        boolean ret = pmdPayCampusCardService.notify(bean);

        // 支付服务器要求返回200返回码
        response.getWriter().write(ret ? "success" : "failed");
    }
}
