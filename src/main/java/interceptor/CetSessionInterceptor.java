package interceptor;

import domain.cet.CetTraineeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import service.cet.CetTraineeTypeService;
import sys.tags.CmTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public class CetSessionInterceptor implements AsyncHandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static CetTraineeTypeService cetTraineeTypeService = CmTag.getBean(CetTraineeTypeService.class);

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

            Map<Integer, CetTraineeType> traineeTypeMap = cetTraineeTypeService.findAll();
            modelMap.put("traineeTypeMap", traineeTypeMap);
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
