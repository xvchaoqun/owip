package shiro;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.sys.LogService;
import sys.constants.SystemConstants;
import sys.utils.HttpUtils;
import sys.utils.JSONUtils;
import sys.utils.RequestUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(CaptchaFormAuthenticationFilter.class);

    @Autowired
    private LogService logService;
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthToken token = createToken(request, response);
        try {
            /*图形验证码验证*/
            //doCaptchaValidate((HttpServletRequest) request, token);
            Subject subject = getSubject(request, response);
            subject.login(token);//正常验证
            return onLoginSuccess(token, subject, request, response);
        }catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
    }

    // 验证码校验
    protected void doCaptchaValidate(HttpServletRequest request,
                                     AuthToken token) {

        //session中的图形码字符串
        String captcha = (String) request.getSession().getAttribute(
                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
         //比对
        if (StringUtils.isBlank(captcha) || !captcha.equalsIgnoreCase(token.getCaptcha())) {
            throw new IncorrectCaptchaException();
        }
    }

    @Override
    protected AuthToken createToken(ServletRequest request,
                                                       ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = WebUtils.getCleanParam(request, getCaptchaParam());
        String type = WebUtils.getCleanParam(request, getTypeParam());
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);

        return new AuthToken(username,
                password.toCharArray(), rememberMe, host, captcha, type);
    }


    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
    public static final String DEFAULT_TYPE_PARAM = "type";

    private String captchaParam = DEFAULT_CAPTCHA_PARAM;
    private String typeParam = DEFAULT_TYPE_PARAM;

    public String getCaptchaParam() {
        return captchaParam;
    }
    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }
    public String getTypeParam() {
        return typeParam;
    }
    public void setTypeParam(String typeParam) {
        this.typeParam = typeParam;
    }

    /*
     *  主要是针对登入成功的处理方法。对于请求头是AJAX的之间返回JSON字符串。
     */
@Override
protected boolean onLoginSuccess(AuthenticationToken token,
        Subject subject, ServletRequest request, ServletResponse response)
        throws Exception {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;

    if (!HttpUtils.isAjaxRequest(httpServletRequest)) {// 不是ajax请求
        issueSuccessRedirect(request, response);
    } else {

        String successUrl=((HttpServletRequest) request).getContextPath() + "/index";
        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
        if(savedRequest != null && savedRequest.getMethod().equalsIgnoreCase("GET")) {
            successUrl = savedRequest.getRequestUrl();
        }

        Map<String, Object> resultMap = new HashMap();
        resultMap.put("success", true);
        resultMap.put("msg", "登入成功");
        resultMap.put("url", successUrl);
        JSONUtils.write((HttpServletResponse) response, resultMap);
    }

    String userAgent = RequestUtils.getUserAgent(httpServletRequest);
    logger.info("login {}, {}", new Object[]{logService.log(SystemConstants.LOG_LOGIN, "登录成功", httpServletRequest),userAgent});

    return false;
}

/**
 * 主要是处理登入失败的方法
 */
@Override
protected boolean onLoginFailure(AuthenticationToken token,
        AuthenticationException e, ServletRequest request,
        ServletResponse response) {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    if (!HttpUtils.isAjaxRequest(httpServletRequest)) {// 不是ajax请求
        setFailureAttribute(request, e);
        return true;
    }
    Map<String, Object> resultMap = new HashMap();
    resultMap.put("success", false);
    try {
        String message = e.getClass().getSimpleName();
        String userAgent = RequestUtils.getUserAgent(httpServletRequest);
        logger.info("login  failed. {}, {}, {}, {}", new Object[]{token.getPrincipal(), message, userAgent});

        if ("SystemClosedException".equals(message)) {

            resultMap.put("msg", "参评人员测评未开启");
        } else if ("IncorrectCredentialsException".equals(message)) {
            resultMap.put("msg", "账号或密码错误");
        } else if ("UnknownAccountException".equals(message)) {
            resultMap.put("msg", "账号或密码错误");
        } else if ("IncorrectCaptchaException".equals(message)) {
            resultMap.put("msg", "验证码错误");
        } else if ("LockedAccountException".equals(message)) {
            resultMap.put("msg", "账号被锁定");
        }else if ("InspectorFinishException".equals(message)) {
            resultMap.put("msg", "该账号已经测评完成");
        }else {
            resultMap.put("msg", "系统错误");
        }
        JSONUtils.write((HttpServletResponse) response, resultMap);
    } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
    }
    return false;
}

/**
 * 所有请求都会经过的方法。
 */
@Override
protected boolean onAccessDenied(ServletRequest request,
        ServletResponse response) throws Exception {

    if (isLoginRequest(request, response)) {
        if (isLoginSubmission(request, response)) {
            if (logger.isTraceEnabled()) {
                logger.trace("Login submission detected.  Attempting to execute login.");
            }

                /*String code = request.getParameter("code");
                HttpServletRequest httpservletrequest = (HttpServletRequest) request;
                String sessionCode = (String) httpservletrequest
                        .getSession()
                        .getAttribute(
                                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
                if (sessionCode == null || "".equals(sessionCode)
                        || !sessionCode.equals(code)) {
                    if ("XMLHttpRequest"
                            .equalsIgnoreCase(((HttpServletRequest) request)
                                    .getHeader("X-Requested-With"))) {// 不是ajax请求
                        //response.setContentType("application/json;charset=UTF-8");
                        //response.setCharacterEncoding("UTF-8");
                        PrintWriter out = response.getWriter();
                        out.println("{'success':false,'message':'验证码错误'}");
                        out.flush();
                        out.close();
                        return false;
                    }else{
                        return this.onLoginFailure(null, new IncorrectCaptchaException(), request, response);
                    }
                }*/
            return executeLogin(request, response);
        } else {
            if (logger.isTraceEnabled()) {
                logger.trace("Login page view.");
            }

            // allow them to see the login page ;)
            return true;
        }
    } else {
        if (logger.isTraceEnabled()) {
            logger.trace("Attempting to access a path which requires authentication.  Forwarding to the "
                    + "Authentication url [" + getLoginUrl() + "]");
        }
        if (!HttpUtils.isAjaxRequest((HttpServletRequest) request)) {// 不是ajax请求
            saveRequestAndRedirectToLogin(request, response);
        } else {

            Map<String, Object> resultMap = new HashMap();
            resultMap.put("success", false);
            resultMap.put("msg", "login");
            JSONUtils.write((HttpServletResponse) response, resultMap);
        }
        return false;
    }
}
}