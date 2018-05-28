package controller.cet;

import domain.cet.CetCourse;
import domain.cet.CetPlanCourse;
import domain.cet.CetPlanCourseExample;
import domain.cet.CetPlanCourseExample.Criteria;
import domain.cet.CetProjectPlan;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sys.constants.CetConstants;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetPlanCourseController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetProjectPlan:list")
    @RequestMapping("/cetPlanCourse")
    public String cetPlanCourse(int planId, ModelMap modelMap) {

        CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
        Integer projectId = cetProjectPlan.getProjectId();
        modelMap.put("cetProject", cetProjectService.getView(projectId));

        return "cet/cetPlanCourse/cetPlanCourse_page";
    }

    @RequiresPermissions("cetProjectPlan:list")
    @RequestMapping("/cetPlanCourse_data")
    public void cetPlanCourse_data(HttpServletResponse response,
                                    Integer planId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetPlanCourseExample example = new CetPlanCourseExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order asc");

        if (planId!=null) {
            criteria.andPlanIdEqualTo(planId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetPlanCourse_export(example, response);
            return;
        }

        long count = cetPlanCourseMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetPlanCourse> records= cetPlanCourseMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetPlanCourse.class, cetPlanCourseMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetProjectPlan:edit")
    @RequestMapping(value = "/cetPlanCourse_selectCourses", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetPlanCourse_selectCourses(int planId,
                                               @RequestParam(value = "courseIds[]") Integer[] courseIds,
                                               HttpServletRequest request) {

        cetPlanCourseService.selectCourses(planId, courseIds);
        logger.info(addLog(LogConstants.LOG_CET, "添加培训班课程：%s, %s",planId, StringUtils.join(courseIds, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectPlan:edit")
    @RequestMapping("/cetPlanCourse_selectCourses")
    public String cetPlanCourse_selectCourses(int planId, ModelMap modelMap) {

        CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
        modelMap.put("cetProjectPlan", cetProjectPlan);

        return "cet/cetPlanCourse/cetPlanCourse_selectCourses";
    }

    // 选择学员/取消选择
    @RequiresPermissions("cetProjectPlan:edit")
    @RequestMapping(value = "/cetPlanCourse_selectObjs", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetPlanCourse_selectObjs(boolean select, int projectId, int planCourseId,
                                        @RequestParam(value = "ids[]", required = false) Integer[] ids ,
                                        HttpServletRequest request) {

        cetPlanCourseService.selectObjs(ids, select, projectId, planCourseId);
        logger.info(addLog(LogConstants.LOG_CET, "选择学员/取消选择： %s, %s, %s",
                StringUtils.join(ids, ","), select, planCourseId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetCourse:list")
    @RequestMapping("/cetPlanCourse_selectCourses_data")
    public void cetPlanCourse_selectCourses_data(HttpServletResponse response,
                                                  int planId, byte planType,
                                                  Integer expertId, String name,
                                                  Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        Byte[] courseTypes = null;
        if(planType== CetConstants.CET_PROJECT_PLAN_TYPE_SELF){
            courseTypes = new Byte[]{CetConstants.CET_COURSE_TYPE_SELF};
        }else if(planType== CetConstants.CET_PROJECT_PLAN_TYPE_SPECIAL){
            courseTypes = new Byte[]{CetConstants.CET_COURSE_TYPE_SPECIAL};
        }

        int count = iCetMapper.countCetPlanCourseList(planId, name, courseTypes);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetCourse> records= iCetMapper.selectCetPlanCourseList(planId, name, courseTypes,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetCourse.class, cetCourseMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    // 管理员给培训课程设置必选课的参训人
    @RequiresPermissions("cetProjectPlan:edit")
    @RequestMapping("/cetPlanCourse_selectObjs")
    public String cetPlanCourse_selectObjs(int planCourseId, ModelMap modelMap) {

        modelMap.put("cetPlanCourse", cetPlanCourseMapper.selectByPrimaryKey(planCourseId));

        return "cet/cetPlanCourse/cetPlanCourse_selectObjs";
    }

    @RequiresPermissions("cetProjectPlan:edit")
    @RequestMapping(value = "/cetPlanCourse_info", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetPlanCourse_info(CetPlanCourse record, HttpServletRequest request) {

        record.setNeedNote(BooleanUtils.isTrue(record.getNeedNote()));
        cetPlanCourseService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_CET, "更新培训班课程信息：%s", record.getId()));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectPlan:edit")
    @RequestMapping("/cetPlanCourse_info")
    public String cetPlanCourse_info(int planCourseId, ModelMap modelMap) {

        CetPlanCourse cetPlanCourse = cetPlanCourseMapper.selectByPrimaryKey(planCourseId);
        modelMap.put("cetPlanCourse", cetPlanCourse);

        modelMap.put("cetProjectPlan", cetProjectPlanMapper.selectByPrimaryKey(cetPlanCourse.getPlanId()));

        return "cet/cetPlanCourse/cetPlanCourse_info";
    }

    @RequiresPermissions("cetProjectPlan:edit")
    @RequestMapping(value = "/cetPlanCourse_upload", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetPlanCourse_upload(int planCourseId, MultipartFile _file, HttpServletRequest request) throws IOException, InterruptedException {

        if(_file!=null) {
            String savePath = upload(_file, "cetPlanCourse");
            if(savePath!=null) {
                CetPlanCourse record = new CetPlanCourse();
                record.setId(planCourseId);
                record.setFileName(FileUtils.getFileName(_file.getOriginalFilename()));
                record.setFilePath(savePath);
                cetPlanCourseService.updateByPrimaryKeySelective(record);
                logger.info(addLog(LogConstants.LOG_CET, "上传附件：%s", record.getId()));
            }
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectPlan:edit")
    @RequestMapping("/cetPlanCourse_upload")
    public String cetPlanCourse_upload(int planCourseId, ModelMap modelMap) {

        modelMap.put("cetPlanCourse", cetPlanCourseMapper.selectByPrimaryKey(planCourseId));
        return "cet/cetPlanCourse/cetPlanCourse_upload";
    }

    @RequiresPermissions("cetProjectPlan:del")
    @RequestMapping(value = "/cetPlanCourse_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetPlanCourse_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetPlanCourseService.del(id);
            logger.info(addLog(LogConstants.LOG_CET, "删除培训方案包含的培训课程：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectPlan:del")
    @RequestMapping(value = "/cetPlanCourse_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetPlanCourse_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetPlanCourseService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除培训方案包含的培训课程：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectPlan:changeOrder")
    @RequestMapping(value = "/cetPlanCourse_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetPlanCourse_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetPlanCourseService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_CET, "培训方案包含的培训课程调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetPlanCourse_export(CetPlanCourseExample example, HttpServletResponse response) {

        List<CetPlanCourse> records = cetPlanCourseMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"培训方案|100","课程|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetPlanCourse record = records.get(i);
            String[] values = {
                record.getPlanId()+"",
                            record.getCourseId()+""
            };
            valuesList.add(values);
        }
        String fileName = "培训方案包含的培训课程_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
