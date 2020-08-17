package interceptor;

import domain.pcs.PcsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
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
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        if (null != modelAndView) {

            ModelMap modelMap = modelAndView.getModelMap();
            // 当前党代会
            PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
            modelMap.put("_pcsConfig", pcsConfig);

            String _finishDate = null;
            if(pcsConfig!=null) {
                // 假定3个月后党代会结束
                Date finishDate = DateUtils.getDateBeforeOrAfterMonthes(pcsConfig.getCreateTime(), 3);
                if(DateUtils.compareDate(new Date(), finishDate)) { // 超过了结束时间，年龄计算冻结
                    _finishDate = DateUtils.formatDate(finishDate, DateUtils.YYYY_MM_DD);
                }
            }
            if(_finishDate==null){ // 在党代会召开期间，以当前日期为基准计算年龄
                _finishDate = DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD);
            }
            modelMap.put("_finishDate", _finishDate);

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
