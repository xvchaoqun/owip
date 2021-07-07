package controller.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fafa on 2016/6/11.
 */
@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request, Model model) {

        String ex = (String)request.getAttribute("shiroLoginFailure");
        String error = null;
        if(ex!=null){
            ex = ex.substring(ex.lastIndexOf(".")+1);
            error = SystemConstants.loginFailedResultMap(ex).get("msg").toString();

            logger.warn("login failed. {}, {}, {}, {}, {}, {}, {}",
                    new Object[]{ShiroHelper.getCurrentUsername(), ex, request.getRequestURI(),
                            request.getMethod(),
                            JSONUtils.toString(request.getParameterMap(), false),
                            RequestUtils.getUserAgent(request), IpUtils.getRealIp(request)});
        }
        model.addAttribute("error", error);

        return "login";
    }

    @RequestMapping(value = "/logout_quiet")
    @ResponseBody
    public void logout_quiet(HttpServletRequest request, Model model) {

        request.getSession().invalidate();
        ShiroHelper.logout();
    }
}
