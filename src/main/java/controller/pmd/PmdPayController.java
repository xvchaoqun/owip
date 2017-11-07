package controller.pmd;

import controller.PmdBaseController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;
import sys.utils.MD5Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by lm on 2017/11/7.
 */
@Controller
@RequestMapping("/pmd/pay")
public class PmdPayController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/test")
    public String test(int num,
                            @RequestParam(required = false, defaultValue = "0.01") String amount,
                            HttpServletRequest request, ModelMap modelMap) {

        String orderDate = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        String orderNo = DateUtils.formatDate(new Date(), "yyyyMMdd") + String.format("%04d", num);
        String xmpch = "004-2014050001";
        String return_url = "http://zzbgz.bnu.edu.cn/pmd/pay/returnPage";
        String notify_url = "http://zzbgz.bnu.edu.cn/pmd/pay/notifyPage";
        String key = "umz4aea6g97skeect0jtxigvjkrimd0o";

        amount = StringUtils.trim(amount);
        String md5Str = "orderDate=" + orderDate +
                "&orderNo=" + orderNo +
                "&amount=" + amount +
                "&xmpch=" + xmpch +
                "&return_url=" + return_url +
                "&notify_url=" + notify_url + key;
        String sign = MD5Util.md5Hex(md5Str, "utf-8");

        modelMap.put("orderDate", orderDate);
        modelMap.put("orderNo", orderNo);
        modelMap.put("xmpch", xmpch);
        modelMap.put("amount", amount);
        modelMap.put("return_url", return_url);
        modelMap.put("notify_url", notify_url);
        modelMap.put("key", key);
        modelMap.put("sign", sign);

        logger.info(addLog(ShiroHelper.getCurrentUserId(), ShiroHelper.getCurrentUsername(),
                SystemConstants.LOG_PMD, "test跳转支付页面：%s", md5Str));

        return "pmd/pay/test";
    }

    @RequestMapping("/returnPage")
    public String returnPage(PayNotifyBean bean, HttpServletRequest request, ModelMap modelMap) {

        String ret =  JSONUtils.toString(request.getParameterMap(), false);
        logger.info("pmd returnPage request.getParameterMap()=" +ret);

        pmdPayService.returnPage(bean);

        modelMap.put("ret", ret);

        return "pmd/pay/return_page";
    }

    @RequestMapping("/notifyPage")
    public void notifyPage(PayNotifyBean bean, HttpServletRequest request,
                           HttpServletResponse response, ModelMap modelMap) throws IOException {

        logger.info("pmd notifyPage request.getParameterMap()=" + JSONUtils.toString(request.getParameterMap(), false));

        boolean ret = pmdPayService.notifyPage(bean);

        response.getWriter().write(ret ? "success" : "failed");
    }
}
