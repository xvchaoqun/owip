package controller.pmd;

import controller.PmdBaseController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.pmd.PayNotifyWszfBean;
import service.pmd.PmdTestService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;
import sys.utils.MD5Util;
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by lm on 2017/11/7.
 */
@Controller
@RequestMapping("/pmd/pay")
public class PmdPayTestController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PmdTestService pmdTestService;

    @RequestMapping("/step")
    @ResponseBody
    public Map test(int step){

        switch (step){
            case 0:
                pmdTestService.step0();
                break;
            case 1:
                pmdTestService.step1();
                break;
            case 2:
                pmdTestService.step2();
                break;
            case 3:
                pmdTestService.step3();
                break;
        }

        return success("操作成功");
    }

    @RequestMapping("/test")
    public String test(@RequestParam(required = false, defaultValue = "0.01") String amount,
                       @RequestParam(required = false, defaultValue = "0") int type,
                            HttpServletRequest request, ModelMap modelMap) {

        String orderDate = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        String orderNo = DateUtils.formatDate(new Date(), "yyyyMMdd") + DateUtils.formatDate(new Date(), "HHmmss");

        String return_url = "http://zzbgz.bnu.edu.cn/pmd/pay/test/returnPage";
        String notify_url = "http://zzbgz.bnu.edu.cn/pmd/pay/test/notifyPage";

        String xmpch = "004-2014050001";
        String key = "umz4aea6g97skeect0jtxigvjkrimd0o";

        if(type==1){
            xmpch = PropertiesUtils.getString("pay.id");
            key= PropertiesUtils.getString("pay.key");
        }


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

    @RequestMapping("/test/returnPage")
    public String returnPage(PayNotifyWszfBean bean, HttpServletRequest request, ModelMap modelMap) {

        String ret =  JSONUtils.toString(request.getParameterMap(), false);
        logger.info("pmd returnPage request.getParameterMap()=" +ret);

        //pmdPayService.returnPage(bean);

        modelMap.put("ret", ret);

        return "pmd/pay/return_test_page";
    }

    @RequestMapping("/test/notifyPage")
    public void notifyPage(PayNotifyWszfBean bean, HttpServletRequest request,
                           HttpServletResponse response, ModelMap modelMap) throws IOException {

        logger.info("pmd notifyPage request.getParameterMap()=" + JSONUtils.toString(request.getParameterMap(), false));
        response.getWriter().write("success");
    }
}
