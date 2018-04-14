package controller.cet.user;

import controller.cet.CetBaseController;
import domain.cet.CetCourseType;
import domain.cet.CetProject;
import domain.cet.CetProjectObj;
import domain.cet.CetProjectPlan;
import domain.cet.CetTrain;
import domain.cet.CetTrainCourse;
import domain.cet.CetTrainCourseExample;
import domain.cet.CetTrainExample;
import domain.cet.CetTraineeView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.cet.common.ICetTrain;
import persistence.cet.common.ICetTrainCourse;
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
    public void cetTrain_data(HttpServletResponse response,
                              int planId,
                              Integer pageSize, Integer pageNo) throws IOException {

        int userId = ShiroHelper.getCurrentUserId();
        // 判断访问权限
        CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
        CetProjectObj cetProjectObj = cetProjectObjService.get(userId, cetProjectPlan.getProjectId());
        if (cetProjectObj == null) {
            throw new UnauthorizedException();
        }

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetTrainExample example = new CetTrainExample();
        CetTrainExample.Criteria criteria = example.createCriteria().andPlanIdEqualTo(planId);
        example.setOrderByClause("create_time desc");

        long count = cetTrainMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTrain> records = cetTrainMapper.selectByExampleWithRowbounds(example,
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

    // 选课中心
    @RequiresPermissions("userCetTrain:list")
    @RequestMapping("/cetTrain_select")
    public String cetTrain(@RequestParam(defaultValue = "1") Integer module,
                           @RequestParam(defaultValue = "0") Byte isFinished,
                           ModelMap modelMap) {

        modelMap.put("module", module);
        modelMap.put("isFinished", isFinished);

        return "cet/user/cetTrain_select_page";
    }

    @RequiresPermissions("userCetTrain:list")
    @RequestMapping("/cetTrain_select_data")
    public void cetTrain_select_data(@RequestParam(defaultValue = "1") Integer module,
                                     Byte isFinished,
                                     HttpServletResponse response,
                                     Integer pageSize, Integer pageNo) throws IOException {

        int userId = ShiroHelper.getCurrentUserId();

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        Boolean hasSelected = null;
        if (module == 2) { // 参训情况
            hasSelected = true;
        }

        long count = iCetMapper.countUserCetTrainList(userId, hasSelected, isFinished);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ICetTrain> records = iCetMapper.selectUserCetTrainList(userId, hasSelected, isFinished,
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

    // 选课报名
    @RequiresPermissions("userCetTrain:list")
    @RequestMapping("/cetTrain_apply")
    public String cetTrain_apply(int trainId,
                                 ModelMap modelMap) {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        modelMap.put("cetTrain", cetTrain);
        Integer planId = cetTrain.getPlanId();
        if (planId != null) {
            CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
            modelMap.put("cetProjectPlan", cetProjectPlan);
        }

        Map<Integer, CetCourseType> courseTypeMap = cetCourseTypeService.findAll();
        modelMap.put("courseTypeMap", courseTypeMap);

        int userId = ShiroHelper.getCurrentUserId();
        CetTraineeView cetTrainee = cetTraineeService.get(userId, trainId);
        if (cetTrainee != null) {
            modelMap.put("cetTrainee", cetTrainee);
            int traineeId = cetTrainee.getId();
            List<ICetTrainCourse> selectedCetTrainCourses = iCetMapper.selectedCetTrainCourses(traineeId);
            modelMap.put("selectedCetTrainCourses", selectedCetTrainCourses);
        }

        CetTrainCourseExample example = new CetTrainCourseExample();
        example.createCriteria().andTrainIdEqualTo(trainId);
        example.setOrderByClause("sort_order asc");
        List<CetTrainCourse> cetTrainCourses = cetTrainCourseMapper.selectByExample(example);

        modelMap.put("cetTrainCourses", cetTrainCourses);

        return "cet/user/cetTrain_apply";
    }

    // 报名
    @RequiresPermissions("userCetTrain:edit")
    @RequestMapping(value = "/cetTrain_apply", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrain_apply(int trainId,
                                 @RequestParam(value = "trainCourseIds[]") Integer[] trainCourseIds,
                                 HttpServletRequest request) {

        int userId = ShiroHelper.getCurrentUserId();
        cetTraineeCourseService.apply(userId, trainId, trainCourseIds);

        logger.info(addLog(LogConstants.LOG_CET, "报名：%s", StringUtils.join(trainCourseIds, ",")));

        return success(FormUtils.SUCCESS);
    }

    // 选课/退课
    @RequiresPermissions("userCetTrain:edit")
    @RequestMapping(value = "/cetTrain_apply_item", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrain_apply_item(int trainCourseId, Boolean isApply, HttpServletRequest request) {

        int userId = ShiroHelper.getCurrentUserId();
        isApply = BooleanUtils.isTrue(isApply);
        cetTraineeCourseService.applyItem(userId, trainCourseId, isApply, false, (isApply ? "选课" : "退课"));

        logger.info(addLog(LogConstants.LOG_CET, (isApply ? "选课" : "退课") + "：%s", trainCourseId));

        return success(FormUtils.SUCCESS);
    }

    // 退出培训班
    @RequiresPermissions("userCetTrain:edit")
    @RequestMapping(value = "/cetTrain_quit", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrain_quit(int traineeId, HttpServletRequest request) {

        int userId = ShiroHelper.getCurrentUserId();
        cetTraineeCourseService.quit(userId, traineeId);

        logger.info(addLog(LogConstants.LOG_CET, "退出培训班：%s", traineeId));
        return success(FormUtils.SUCCESS);
    }

    // 选课详情
    @RequiresPermissions("userCetTrain:list")
    @RequestMapping("/cetTrain_detail")
    public String cetTrain_detail(int trainId,
                                  ModelMap modelMap) {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        modelMap.put("cetTrain", cetTrain);
        Integer planId = cetTrain.getPlanId();
        CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
        modelMap.put("cetProjectPlan", cetProjectPlan);
        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(cetProjectPlan.getProjectId());
        modelMap.put("cetProject", cetProject);

        Map<Integer, CetCourseType> courseTypeMap = cetCourseTypeService.findAll();
        modelMap.put("courseTypeMap", courseTypeMap);

        int userId = ShiroHelper.getCurrentUserId();
        CetTraineeView cetTrainee = cetTraineeService.get(userId, trainId);
        if (cetTrainee == null) {
            throw new UnauthorizedException();
        }
        modelMap.put("cetTrainee", cetTrainee);

        int traineeId = cetTrainee.getId();

        List<ICetTrainCourse> selectedCetTrainCourses = iCetMapper.selectedCetTrainCourses(traineeId);
        List<CetTrainCourse> unSelectedCetTrainCourses = iCetMapper.unSelectedCetTrainCourses(trainId, traineeId);

        modelMap.put("selectedCetTrainCourses", selectedCetTrainCourses);
        modelMap.put("unSelectedCetTrainCourses", unSelectedCetTrainCourses);

        return "cet/user/cetTrain_detail";
    }
}
