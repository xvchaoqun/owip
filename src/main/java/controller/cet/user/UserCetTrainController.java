package controller.cet.user;

import controller.cet.CetBaseController;
import domain.cet.*;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.cet.common.ICetProject;
import persistence.cet.common.ICetTrain;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/cet")
public class UserCetTrainController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 学员 第三级：培训班
    @RequiresPermissions("userCetTrain:list")
    @RequestMapping("/cetTrain")
    public String cetTrain(int planId, ModelMap modelMap) {

        CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
        modelMap.put("cetProjectPlan", cetProjectPlan);
        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(cetProjectPlan.getProjectId());
        modelMap.put("cetProject", cetProject);

        return "cet/user/cetTrain_page";
    }

    @RequiresPermissions("userCetTrain:list")
    @RequestMapping("/cetTrain_data")
    @ResponseBody
    public void cetTrain_data(HttpServletResponse response,
                              int planId,
                              Integer pageSize, Integer pageNo) throws IOException {

        int userId = ShiroHelper.getCurrentUserId();

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetTraineeViewExample example = new CetTraineeViewExample();
        example.createCriteria().andPlanIdEqualTo(planId).andUserIdEqualTo(userId);
        example.setOrderByClause("train_id desc");

        long count = cetTraineeViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTraineeView> records = cetTraineeViewMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    // 选课中心
    @RequiresPermissions("userCetTrain:list")
    @RequestMapping("/cetTrain_select")
    public String cetTrain(Byte cls, ModelMap modelMap) {

        int userId = ShiroHelper.getCurrentUserId();
        // 可选课的党校培训班数量
        long trainCount = iCetMapper.countUserCetTrainList(userId);
        // 可选课的二级党委培训班数量
        long projectCount = iCetMapper.countUserCetProjectList(userId);

        if(cls==null){
            cls= (trainCount<projectCount)?(byte)2:(byte)1;
        }
        modelMap.put("cls", cls);
        modelMap.put("trainCount", trainCount);
        modelMap.put("projectCount", projectCount);

        return "cet/user/cetTrain_select_page";
    }

    // 党校培训班选课
    @RequiresPermissions("userCetTrain:list")
    @RequestMapping("/cetTrain_select_data")
    @ResponseBody
    public void cetTrain_select_data(Integer pageSize, Integer pageNo) throws IOException {

        int userId = ShiroHelper.getCurrentUserId();

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        long count = iCetMapper.countUserCetTrainList(userId);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ICetTrain> records = iCetMapper.selectUserCetTrainList(userId,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetTrain.class, cetTrainMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    // 二级党委培训班选课
    @RequiresPermissions("userCetTrain:list")
    @RequestMapping("/cetProject_select_data")
    @ResponseBody
    public void cetProject_select_data(Integer pageSize, Integer pageNo) throws IOException {

        int userId = ShiroHelper.getCurrentUserId();

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        long count = iCetMapper.countUserCetProjectList(userId);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ICetProject> records = iCetMapper.selectUserCetProjectList(userId,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetTrain.class, cetTrainMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    // 选课/退课
    @RequiresPermissions("userCetTrain:edit")
    @RequestMapping(value = "/cetTrain_apply_item", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrain_apply_item(int trainCourseId, Boolean isApply, HttpServletRequest request) {

        int userId = ShiroHelper.getCurrentUserId();
        isApply = BooleanUtils.isTrue(isApply);
        cetTrainObjService.applyItem(userId, trainCourseId, isApply, false, true, (isApply ? "选课" : "退课"));

        logger.info(addLog(LogConstants.LOG_CET, (isApply ? "选课" : "退课") + "：%s", trainCourseId));

        return success(FormUtils.SUCCESS);
    }

    // 选课详情
    @RequiresPermissions("userCetTrain:list")
    @RequestMapping("/cetTrain_detail")
    public String cetTrain_detail(Integer trainId, Integer projectId,
                                  ModelMap modelMap) {

        cetTrainObjService.trainDetail(trainId, projectId, modelMap);

        return "cet/user/cetTrain_detail";
    }

    // 培训课程列表（二级党委培训）
    @RequiresPermissions("userCetTrain:list")
    @RequestMapping("/cetTrainCourse_data")
    public void cetTrainCourse_data(int projectId, HttpServletResponse response, HttpServletRequest request,
                                    Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetTrainCourseExample example = new CetTrainCourseExample();
        example.createCriteria().andProjectIdEqualTo(projectId);
        example.setOrderByClause("sort_order asc");

        long count = cetTrainCourseMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTrainCourse> records = cetTrainCourseMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        int userId = ShiroHelper.getCurrentUserId();
        request.setAttribute("userId", userId);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetTrainCourse.class, cetTrainCourseMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
