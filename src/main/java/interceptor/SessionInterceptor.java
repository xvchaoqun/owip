package interceptor;

import controller.BaseController;
import domain.cet.CetTrainInspector;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.ui.ModelMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import controller.cet.CetSessionUtils;
import sys.utils.HttpRequestDeviceUtils;
import sys.utils.IpUtils;
import sys.utils.RequestUtils;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


public class SessionInterceptor extends BaseController implements AsyncHandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String userAgent = RequestUtils.getUserAgent(request);
        logger.debug("request {}, {}, {}, {}, {},request.getContentType()={}, request.getHeader(\"Cookie\")={}", new Object[]{
                IpUtils.getRealIp(request), userAgent,
                request.getMethod(),
                request.getRequestURL(), RequestUtils.getQueryString(request),
                request.getContentType(),
                request.getHeader("Cookie")
        });

        String servletPath = request.getServletPath();
        if(servletPath.startsWith("/m/cet/")){

            if(StringUtils.equalsIgnoreCase(servletPath, "/m/cet/login")){
                return true;
            }else{
                CetTrainInspector trainInspector = CetSessionUtils.getTrainInspector(request);
                if(trainInspector==null){
                    WebUtils.issueRedirect(request, response, "/m/cet/login");
                    return false;
                }
                return true;
            }
        }

        if (handler instanceof HandlerMethod) {

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            NeedSign needSign = method.getAnnotation(NeedSign.class);
            if(needSign!=null) {

                MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
                String app = request.getParameter("app");
                String sign = request.getParameter("sign");
                if (StringUtils.isBlank(app) || StringUtils.isBlank(sign)) {

                    logger.info(String.format("app或sign参数为空, app=%s, sign=%s", app, sign));
                    throw new SignParamsException("app或sign参数不能为空");
                }
                Signature signature = new Signature();
                for (MethodParameter methodParameter : methodParameters) {
                    if (methodParameter.hasParameterAnnotation(SignParam.class)) {

                        SignParam signParam = methodParameter.getParameterAnnotation(SignParam.class);
                        String parameterName = signParam.value();
                        String parameterValue = request.getParameter(parameterName);
                        if(signParam.required() && StringUtils.isBlank(parameterValue)){

                            logger.info(String.format("参数%s为空, app=%s, sign=%s", parameterName, app, sign));
                            throw new SignParamsException(String.format("参数%s不能为空", parameterName));
                        }
                        signature.put(parameterName, parameterValue);
                    }
                }

                if (!signature.verify(app, sign)) {

                    logger.info(String.format("签名错误, app=%s, sign=%s", app, sign));
                    throw new SignParamsException("签名错误");
                }
            }
        }

        //String _commonUrls = PropertiesUtils.getString("sys.commonUrls");

        if(request.getDispatcherType() == DispatcherType.REQUEST) {
            /*if (servletPath.startsWith("/WEB-INF")) {
                servletPath = (String) request.getAttribute("javax.servlet.forward.request_uri");
            }*/
            //System.out.println(servletPath + "----------------" + request.getDispatcherType());
            //if(!PatternUtils.match(_commonUrls, servletPath)) {
            //if (HttpUtils.isMoblie(request)) {
            if (HttpRequestDeviceUtils.isMobileDevice(request)) {
                if(servletPath.startsWith("/login")){
                    //WebUtils.issueRedirect(request, response, "/m/abroad/login");
                    WebUtils.issueRedirect(request, response, "/m/login");
                    return false;
                }
                if (!servletPath.startsWith("/m/")) { // 移动端
                    //WebUtils.issueRedirect(request, response, "/m/abroad/index");
                    WebUtils.issueRedirect(request, response, "/m/index");
                    return false;
                }
            } else {
                if (servletPath.startsWith("/m/")) { // 非移动端
                    WebUtils.issueRedirect(request, response, "/");
                    return false;
                }
            }
            //}
        }
        if(request.getDispatcherType() == DispatcherType.INCLUDE) {
            servletPath = (String) request.getAttribute("javax.servlet.forward.request_uri");
        }

        if(servletPath.length()>1 && servletPath.endsWith("/")){
            response.setStatus(404);
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        if (null != modelAndView) {
            ModelMap modelMap = modelAndView.getModelMap();
            //modelMap.put("useCaptcha",springProps.useCaptcha);
            modelMap.putAll(getMetaMap());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request,
                                               HttpServletResponse response, Object handler) throws Exception {
        // TODO Auto-generated method stub

    }
}
