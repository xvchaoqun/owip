package interceptor;

import domain.cet.CetProject;
import domain.cet.CetTraineeType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import persistence.cet.CetProjectMapper;
import persistence.cet.common.ICetMapper;
import service.cet.CetTraineeTypeService;
import shiro.ShiroHelper;
import sys.constants.CetConstants;
import sys.constants.RoleConstants;
import sys.tags.CmTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


public class CetSessionInterceptor implements AsyncHandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static CetTraineeTypeService cetTraineeTypeService = CmTag.getBean(CetTraineeTypeService.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean adminProject = true;
        if(!RoleConstants.isCetAdmin()) {

            String servletPath = request.getServletPath();
            if(!StringUtils.containsAny(servletPath, "/cet/cetUnitTrain")){
                adminProject = false;
                int projectId = NumberUtils.toInt(request.getParameter("projectId"));
                if (projectId > 0) {

                    CetProjectMapper cetProjectMapper = CmTag.getBean(CetProjectMapper.class);
                    CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
                    if(sys.utils.NumberUtils.contains(cetProject.getStatus(), CetConstants.CET_PROJECT_STATUS_UNREPORT
                        , CetConstants.CET_PROJECT_STATUS_UNPASS)) {

                        ICetMapper iCetMapper = CmTag.getBean(ICetMapper.class);
                        int userId = ShiroHelper.getCurrentUserId();
                        List<Integer> adminPartyIdList = iCetMapper.getAdminPartyIds(userId);

                        if (adminPartyIdList.contains(cetProject.getCetPartyId())) {
                            adminProject = true;
                        }
                    }
                }
            }
        }
        request.setAttribute("adminProject", adminProject);

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

        /*String servletPath = request.getServletPath();
        if(StringUtils.equalsAny(servletPath, "/cet/cetProjectObj",
                "/cet/cetTrainCourse",
                "/cet/cetTrain_detail/time",
                "/cet/cetTrainee", "/cet/cetProject_detail_obj")){
        }*/
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
