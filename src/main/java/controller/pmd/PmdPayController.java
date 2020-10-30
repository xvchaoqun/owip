package controller.pmd;

import ext.common.pay.OrderNotifyBean;
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
 *
 * 支付后台通知
 */
@Controller
@RequestMapping("/pmd/pay")
public class PmdPayController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 新校园卡
    @RequestMapping({"/callback", "/callback/newcampuscard"})
    public void callback_newcampuscard(HttpServletRequest request,
                           HttpServletResponse response, ModelMap modelMap) throws IOException {
    
        Map<String, String[]> parameterMap = request.getParameterMap();
        logger.info("pmd callback request.getParameterMap()=" + JSONUtils.toString(parameterMap, false));
        String successMsg = "";
        if(parameterMap.size()>0) {
            OrderNotifyBean notify = pmdOrderService.notify(request, true);
            successMsg = notify.getSuccessMsg();
        }
        // 支付服务器要求返回200返回码
        response.getWriter().write(successMsg);
    }
}
