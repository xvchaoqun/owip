package controller.cet;

import domain.cet.CetProject;
import domain.cet.CetTrainCourse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.utils.FormUtils;

import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetProjectDetailController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetProject:edit")
    @RequestMapping("/cetProject_detail")
    public String cetProject_detail(Integer projectId, ModelMap modelMap) {

        if (projectId != null) {
            CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
            modelMap.put("cetProject", cetProject);
        }
        return "cet/cetProject/cetProject_detail/menu";
    }

    @RequiresPermissions("cetProject:edit")
    @RequestMapping("/cetProject_detail/obj")
    public String projectee(int projectId, Integer trainCourseId, ModelMap modelMap) {

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
        modelMap.put("cetProject", cetProject);

        if(trainCourseId!=null){
            CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
            modelMap.put("cetTrainCourse", cetTrainCourse);
        }

        return "cet/cetProject/cetProject_detail/obj";
    }

    @RequiresPermissions("cetProject:edit")
    @RequestMapping("/cetProject_detail/setting")
    public String time(int projectId, ModelMap modelMap) {

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
        modelMap.put("cetProject", cetProject);

        return "cet/cetProject/cetProject_detail/setting";
    }

    @RequiresPermissions("cetProject:edit")
    @RequestMapping(value = "/cetProject_detail/setting", method = RequestMethod.POST)
    @ResponseBody
    public Map do_time(int projectId, BigDecimal requirePeriod) {

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);

        CetProject record = new CetProject();
        record.setId(projectId);
        record.setRequirePeriod(requirePeriod);

        cetProjectMapper.updateByPrimaryKeySelective(record);

        logger.info(addLog(SystemConstants.LOG_CET, "更新专题培训参数设置：%s~%s",
                cetProject.getName(), requirePeriod));

        return success(FormUtils.SUCCESS);
    }
}
