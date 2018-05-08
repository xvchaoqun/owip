package controller.cet;

import domain.base.ContentTpl;
import domain.cet.CetDiscuss;
import domain.cet.CetDiscussGroup;
import domain.cet.CetPlanCourse;
import domain.cet.CetProject;
import domain.cet.CetProjectPlan;
import domain.cet.CetTrain;
import domain.cet.CetTrainCourse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.ContentTplConstants;
import sys.constants.LogConstants;
import sys.utils.FormUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        return "cet/cetProject/cetProject_detail/cetProject_detail";
    }

    @RequiresPermissions("cetProject:edit")
    @RequestMapping("/cetProject_detail_obj")
    public String cetProject_detail_obj(int projectId,
                                        /** cls=1 培训对象
                                         *  cls=2 培训班选择学员
                                         *  cls=3 培训方案选择学员
                                         *  cls=4 撰写心得体会
                                         *  cls=5 设置小组成员（分组讨论）
                                         *  cls=6 自主学习
                                         */
                            @RequestParam(defaultValue = "1") Integer cls, // 同 /cetProjectObj 的cls
                            Integer trainCourseId, // 培训班选课
                            Integer planCourseId,  // 培训方案选课
                            Integer discussGroupId,  // 讨论小组
                            ModelMap modelMap) {

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
        modelMap.put("cetProject", cetProject);

        if(trainCourseId!=null){
            CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
            modelMap.put("cetTrainCourse", cetTrainCourse);
            Integer trainId = cetTrainCourse.getTrainId();
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
            modelMap.put("cetTrain", cetTrain);
            Integer planId = cetTrain.getPlanId();
            if(planId!=null){
                CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
                modelMap.put("cetProjectPlan", cetProjectPlan);
            }

        }else if(planCourseId!=null){

            CetPlanCourse cetPlanCourse = cetPlanCourseMapper.selectByPrimaryKey(planCourseId);
            modelMap.put("cetPlanCourse", cetPlanCourse);
            Integer planId = cetPlanCourse.getPlanId();
            if(planId!=null){
                CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
                modelMap.put("cetProjectPlan", cetProjectPlan);
            }
        }else if(discussGroupId!=null){

            CetDiscussGroup cetDiscussGroup = cetDiscussGroupMapper.selectByPrimaryKey(discussGroupId);
            modelMap.put("cetDiscussGroup", cetDiscussGroup);

            Integer discussId = cetDiscussGroup.getDiscussId();
            CetDiscuss cetDiscuss = cetDiscussMapper.selectByPrimaryKey(discussId);
            modelMap.put("cetDiscuss", cetDiscuss);

            /*CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(cetDiscuss.getPlanId());
            modelMap.put("cetProjectPlan", cetProjectPlan);*/
        }

        modelMap.put("cls", cls);

        return "cet/cetProject/cetProject_detail/cetProject_detail_obj";
    }

    @RequiresPermissions("cetProject:edit")
    @RequestMapping("/cetProject_detail_setting")
    public String cetProject_detail_setting(int projectId, ModelMap modelMap) {

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
        modelMap.put("cetProject", cetProject);

        return "cet/cetProject/cetProject_detail/cetProject_detail_setting";
    }

    @RequiresPermissions("cetProject:edit")
    @RequestMapping(value = "/cetProject_detail_setting", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProject_detail_setting(int projectId, BigDecimal requirePeriod) {

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);

        CetProject record = new CetProject();
        record.setId(projectId);
        record.setRequirePeriod(requirePeriod);

        cetProjectMapper.updateByPrimaryKeySelective(record);

        logger.info(addLog(LogConstants.LOG_CET, "更新专题培训参数设置：%s~%s",
                cetProject.getName(), requirePeriod));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProject:edit")
    @RequestMapping("/cetProject_detail_begin")
    public String cetProject_detail_begin(int projectId, ModelMap modelMap) {

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
        modelMap.put("cetProject", cetProject);

        Map<String, ContentTpl> contentTplMap = contentTplService.codeKeyMap();
        List<ContentTpl> tplList = new ArrayList<>();
        tplList.add(contentTplMap.get(ContentTplConstants.CONTENT_TPL_CET_MSG_1));
        modelMap.put("tplList", tplList);

        return "cet/cetProject/cetProject_detail/cetProject_detail_begin";
    }

    @RequiresPermissions("cetProject:edit")
    @RequestMapping(value = "/cetProject_detail_begin", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProject_detail_begin(int projectId, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date openTime,
                                          String openAddress) {

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);

        CetProject record = new CetProject();
        record.setId(projectId);
        record.setOpenTime(openTime);
        record.setOpenAddress(openAddress);

        cetProjectMapper.updateByPrimaryKeySelective(record);

        logger.info(addLog(LogConstants.LOG_CET, "更新开班仪式设置：%s~%s",
                openTime, openAddress));

        return success(FormUtils.SUCCESS);
    }
}
