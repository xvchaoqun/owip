package interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import service.abroad.ApproverTypeService;
import service.abroad.SafeBoxService;
import sys.tags.CmTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AbroadSessionInterceptor implements AsyncHandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static SafeBoxService safeBoxService = CmTag.getBean(SafeBoxService.class);
    private static ApproverTypeService approverTypeService = CmTag.getBean(ApproverTypeService.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        if (null != modelAndView) {
            ModelMap modelMap = modelAndView.getModelMap();

            modelMap.put("safeBoxMap", safeBoxService.findAll());
            modelMap.put("approverTypeMap", approverTypeService.findAll());
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
