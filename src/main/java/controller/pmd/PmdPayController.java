package controller.pmd;

import domain.pmd.PmdMemberPayView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by lm on 2017/11/7.
 */
@Controller
@RequestMapping("/pmd/pay")
public class PmdPayController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 校园统一支付平台
    @RequestMapping("/callback/wszf")
    public String returnPage(HttpServletRequest request, HttpServletResponse response,
                             ModelMap modelMap) throws IOException {

        logger.info("pmd request.getParameterMap()=" +JSONUtils.toString(request.getParameterMap(), false));
        // 保存原始的支付通知
        pmdOrderWszfService.savePayNotifyBean(request);

        pmdOrderWszfService.callback(request);

        String returnType = request.getParameter("return_type");
        String orderNo = request.getParameter("orderNo");
        if(StringUtils.equals(returnType, "1")) {

            // 页面返回同步通知
            PmdMemberPayView pmdMemberPayView = pmdMemberPayService.get(orderNo);
            modelMap.put("pmdMemberPayView", pmdMemberPayView);

            return "pmd/pay/return_page";
        }else if(StringUtils.equals(returnType, "2")) {
            // 服务器返回异步通知
            response.getWriter().write("success");
        }

        return null;
    }

    // 校园卡
    @RequestMapping("/callback/campuscard")
    public void callback_campuscard(HttpServletRequest request,
                           HttpServletResponse response, ModelMap modelMap) throws IOException {
    
        Map<String, String[]> parameterMap = request.getParameterMap();
        logger.info("pmd callback request.getParameterMap()=" + JSONUtils.toString(parameterMap, false));
        boolean ret = true;
        if(parameterMap.size()>0) {
           
            ret = pmdOrderCampusCardService.notify(request);
        }
        // 支付服务器要求返回200返回码
        response.getWriter().write(ret ? "success" : "failed");
    }

    // 新校园卡
    @RequestMapping("/callback/newcampuscard")
    public void callback_newcampuscard(HttpServletRequest request,
                           HttpServletResponse response, ModelMap modelMap) throws IOException {
    
        Map<String, String[]> parameterMap = request.getParameterMap();
        logger.info("pmd callback request.getParameterMap()=" + JSONUtils.toString(request.getParameterMap(), false));
        boolean ret = true;
        if(parameterMap.size()>0) {
        
        }
        // 支付服务器要求返回200返回码
        response.getWriter().write(ret ? "pok" : "failed");
    }
}
