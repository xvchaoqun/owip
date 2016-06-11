package controller;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.ShiroUser;
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
    public String showLoginForm(HttpServletRequest request, Model model) {

        String ex = (String)request.getAttribute("shiroLoginFailure");
        String error = null;
        if(ex!=null){
            ex = ex.substring(ex.lastIndexOf(".")+1);
            error = SystemConstants.loginFailedResultMap(ex).get("msg").toString();

            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            String username = (shiroUser!=null)?shiroUser.getUsername():null;
            logger.warn("login failed. {}, {}, {}, {}, {}, {}, {}",
                    new Object[]{username, ex, request.getRequestURI(),
                            request.getMethod(),
                            JSONUtils.toString(request.getParameterMap(), false),
                            RequestUtils.getUserAgent(request), IpUtils.getRealIp(request)});
        }
        model.addAttribute("error", error);

        return "login";
    }
}
