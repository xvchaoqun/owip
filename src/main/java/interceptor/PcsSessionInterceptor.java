package interceptor;

import domain.pcs.PcsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import service.pcs.PcsConfigService;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


public class PcsSessionInterceptor implements AsyncHandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static PcsConfigService pcsConfigService = CmTag.getBean(PcsConfigService.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 当前党代会
        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        request.setAttribute("_pcsConfig", pcsConfig);

        Date finishDate = pcsConfigService.getAgeBaseDate(pcsConfig.getCreateTime());
        request.setAttribute("_ageBaseDate", DateUtils.formatDate(finishDate, DateUtils.YYYY_MM_DD));

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        if (null != modelAndView) {

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
