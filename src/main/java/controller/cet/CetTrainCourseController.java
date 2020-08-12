package controller.cet;

import controller.global.OpException;
import domain.cet.*;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sys.constants.CetConstants;
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/cet")
public class CetTrainCourseController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrainCourse:list")
    @RequestMapping("/cetTrainCourse")
    public String cetTrainCourse(@RequestParam(defaultValue = "1") Integer cls,  // cls=2 课程评估表
                                 @RequestParam(defaultValue = "1")Boolean isOnCampus,
                                 Integer projectId, Integer trainId, ModelMap modelMap) {

        Date startTime;
        Date endTime;

        CetProject cetProject;
        if(projectId!=null){ // 二级党委培训

            cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
            startTime = cetProject.getStartTime();
            endTime = cetProject.getEndTime();
        }else if(trainId!=null) { // 党校培训

            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
            startTime = cetTrain.getStartTime();
            endTime = cetTrain.getEndTime();
            modelMap.put("cetTrain", cetTrain);
            Integer planId = cetTrain.getPlanId();
            if (planId != null) {
                CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
                modelMap.put("cetProjectPlan", cetProjectPlan);
            }

            cetProject = iCetMapper.getCetProject(trainId);
        }else{
            return null;
        }

        if(isOnCampus) {

            modelMap.put("startTime", startTime);
            modelMap.put("endTime", endTime);

            modelMap.put("cetProject", cetProject);
            modelMap.put("cls", cls);
            return "cet/cetTrainCourse/cetTrainCourse_page";
        }

        return "cet/cetTrainCourse/cetTrainCourse_off_page";
    }

    @RequiresPermissions("cetTrainCourse:list")
    @RequestMapping("/cetTrainCourse_data")
    public void cetTrainCourse_data(HttpServletResponse response,
                                    @RequestParam(defaultValue = "1")Boolean isOnCampus,
                                 Integer projectId, Integer trainId, String name,
                                    Integer userId, // 学习详情 页面
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer[] ids, // 导出的记录
                                    HttpServletRequest request,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetTrainCourseExample example = new CetTrainCourseExample();
        CetTrainCourseExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order asc");

        if(projectId!=null) { // 二级党委培训

            criteria.andProjectIdEqualTo(projectId);

        }else if(trainId!=null) { // 党校培训
            criteria.andTrainIdEqualTo(trainId);
        }else{
            criteria.andIdIsNull();
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));

            if(isOnCampus)
                cetTrainCourse_export(example, response);
            else
                cetTrainCourse_off_export(example, response);
            return;
        }

        long count = cetTrainCourseMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTrainCourse> records = cetTrainCourseMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

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

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping(value = "/cetTrainCourse_selectCourses", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_selectCourses(int trainId,
                                               Integer[] courseIds,
                                               HttpServletRequest request) {

        cetTrainCourseService.selectCourses(trainId, courseIds);
        logger.info(addLog(LogConstants.LOG_CET, "添加培训班课程：%s, %s",trainId, StringUtils.join(courseIds, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping("/cetTrainCourse_selectCourses")
    public String cetTrainCourse_selectCourses(int trainId, Integer expertId, ModelMap modelMap) {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        modelMap.put("cetTrain", cetTrain);
        Integer planId = cetTrain.getPlanId();
        if(planId!=null) {
            CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
            modelMap.put("cetProjectPlan", cetProjectPlan);
        }

        if(expertId!=null) {
            modelMap.put("cetExpert", cetExpertMapper.selectByPrimaryKey(expertId));
        }

        return "cet/cetTrainCourse/cetTrainCourse_selectCourses";
    }

    @RequiresPermissions("cetTrainCourse:list")
    @RequestMapping("/cetTrainCourse_selectCourses_data")
    public void cetTrainCourse_selectCourses_data(HttpServletResponse response,
                                                  int trainId, Byte planType,
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
        if(planType!=null) {
            if (planType == CetConstants.CET_PROJECT_PLAN_TYPE_OFFLINE) {
                courseTypes = new Byte[]{CetConstants.CET_COURSE_TYPE_OFFLINE};
            } else if (planType == CetConstants.CET_PROJECT_PLAN_TYPE_ONLINE) {
                courseTypes = new Byte[]{CetConstants.CET_COURSE_TYPE_ONLINE};
            } else if (planType == CetConstants.CET_PROJECT_PLAN_TYPE_PRACTICE) {
                courseTypes = new Byte[]{CetConstants.CET_COURSE_TYPE_PRACTICE};
            }
        }

        int count = iCetMapper.countCetTrainCourseList(trainId, expertId, name, courseTypes);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetCourse> records= iCetMapper.selectCetTrainCourseList(trainId, expertId, name, courseTypes,
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
    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping("/cetTrainCourse_selectObjs")
    public String cetTrainCourse_selectObjs(int trainCourseId, ModelMap modelMap) {

        modelMap.put("cetTrainCourse", cetTrainCourseMapper.selectByPrimaryKey(trainCourseId));

        return "cet/cetTrainCourse/cetTrainCourse_selectObjs";
    }

    // 参训人列表（签到列表）
    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping("/cetTrainCourse_trainee")
    public String cetTrainCourse_trainee(int trainCourseId, Integer userId,  ModelMap modelMap) {

        modelMap.put("cetTrainCourse", cetTrainCourseMapper.selectByPrimaryKey(trainCourseId));
        modelMap.put("cetTraineeTypeMap", cetTraineeTypeService.findAll());
        if(userId!=null){
            modelMap.put("sysUser", CmTag.getUserById(userId));
        }

        return "cet/cetTrainCourse/cetTrainCourse_trainee_page";
    }

    @RequiresPermissions("cetTrainCourse:list")
    @RequestMapping("/cetTrainCourse_trainee_data")
    public void cetTrainCourse_trainee_data(HttpServletResponse response,
                                                  int trainCourseId,
                                            Integer userId, Boolean isFinished,
                                                  Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetTrainObjViewExample example = new CetTrainObjViewExample();
        CetTrainObjViewExample.Criteria criteria = example.createCriteria()
                .andTrainCourseIdEqualTo(trainCourseId);

        if(userId!=null){
            criteria.andUserIdEqualTo(userId);
        }
        if(isFinished!=null){
            criteria.andIsFinishedEqualTo(isFinished);
        }

        long count = cetTrainObjViewMapper.countByExample(example);

        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTrainObjView> records= cetTrainObjViewMapper.selectByExampleWithRowbounds(example,
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

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping(value = "/cetTrainCourse_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_au(CetTrainCourse record, HttpServletRequest request) {

        if(record.getId()!=null) {

            cetTrainCourseService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "更新培训班课程信息：%s", record.getId()));
        }else{

            cetTrainCourseService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "添加培训班课程信息：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping("/cetTrainCourse_au")
    public String cetTrainCourse_au(Integer trainCourseId,
                                    Integer projectId,
                                    Integer trainId,
                                    ModelMap modelMap) {

        if(trainCourseId!=null) {

            CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
            modelMap.put("cetTrainCourse", cetTrainCourse);

            projectId = cetTrainCourse.getProjectId();
            trainId = cetTrainCourse.getTrainId();
            if (trainId != null) {

                CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
                Integer planId = cetTrain.getPlanId();
                if (planId != null) {
                    CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
                    modelMap.put("cetProjectPlan", cetProjectPlan);
                }
            }
        }

        modelMap.put("projectId", projectId);
        modelMap.put("trainId", trainId);

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
        modelMap.put("cetProject", cetProject);

        return "cet/cetTrainCourse/cetTrainCourse_au";
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping(value = "/cetTrainCourse_applyStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_applyStatus(CetTrainCourse record, HttpServletRequest request) {

        cetTrainCourseService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_CET, "更新培训班选课/退课状态：%s", record.getId()));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping("/cetTrainCourse_applyStatus")
    public String cetTrainCourse_applyStatus(int trainCourseId, ModelMap modelMap) {

        modelMap.put("cetTrainCourse", cetTrainCourseMapper.selectByPrimaryKey(trainCourseId));
        return "cet/cetTrainCourse/cetTrainCourse_applyStatus";
    }

    // 导出已选课学员
    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping("/cetTrainCourse_exportChosenObjs")
    public void cetTrainCourse_exportChosenObjs(int trainCourseId, HttpServletResponse response) throws IOException {

        cetExportService.exportChosenObjs(trainCourseId, response);
        return;
    }

    // 补选课报名
    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping(value = "/cetTrainCourse_applyMsg", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_applyMsg(
                                int projectId,
                                Integer[] trainCourseIds,
                               String mobile,
                               String msg,
                                Boolean addSuffix,
                                String suffix,
                               HttpServletRequest request) {

        if(StringUtils.isNotBlank(mobile) && !CmTag.validMobile(mobile)){
            return failed("手机号码有误："+ mobile);
        }
        if(BooleanUtils.isTrue(addSuffix)){
            msg += StringUtils.trim(suffix);
        }
        Map<String, Integer> result = cetShortMsgService.sendApplyMsg(projectId, trainCourseIds, mobile, msg);
        logger.info(addLog(LogConstants.LOG_CET, "补选课报名：%s-%s-%s", msg, mobile, StringUtils.join(trainCourseIds, ",")));
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("totalCount", result.get("total"));
        resultMap.put("successCount", result.get("success"));
        return resultMap;
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping("/cetTrainCourse_applyMsg")
    public String cetTrainCourse_applyMsg(int projectId, Integer[] trainCourseIds,
                                          ModelMap modelMap) {

        CetTrainCourseExample example = new CetTrainCourseExample();
        example.createCriteria().andIdIn(Arrays.asList(trainCourseIds));
        List<CetTrainCourse> cetTrainCourses = cetTrainCourseMapper.selectByExample(example);
        modelMap.put("cetTrainCourses", cetTrainCourses);

        List<Integer> userIds = iCetMapper.notApplyUserIds(projectId, trainCourseIds);
        modelMap.put("userIds", userIds);

        return "cet/cetTrainCourse/cetTrainCourse_applyMsg";
    }

    @RequiresPermissions("cetTrainCourse:del")
    @RequestMapping(value = "/cetTrainCourse_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetTrainCourse_batchDel(HttpServletRequest request, Integer projectId, Integer trainId,
                                       Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            cetTrainCourseService.batchDel(ids, projectId, trainId);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除培训课程：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainCourse:changeOrder")
    @RequestMapping(value = "/cetTrainCourse_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetTrainCourseService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_CET, "培训课程调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetTrainCourse_export(CetTrainCourseExample example, HttpServletResponse response) {

        List<CetTrainCourse> records = cetTrainCourseMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"培训班次|200", "课程名称|300", "教师名称", "开始时间|200", "结束时间|200", "选课情况"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetTrainCourse record = records.get(i);
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(record.getTrainId());
            String[] values = {
                    cetTrain.getName(),
                    record.getName(),
                    record.getTeacher(),
                    DateUtils.formatDate(record.getStartTime(), DateUtils.YYYY_MM_DD_HH_MM),
                    DateUtils.formatDate(record.getEndTime(), DateUtils.YYYY_MM_DD_HH_MM),
                    record.getSelectedCount()+""
            };
            valuesList.add(values);
        }
        String fileName = "培训班课程_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    public void cetTrainCourse_off_export(CetTrainCourseExample example, HttpServletResponse response) {

        List<CetTrainCourse> records = cetTrainCourseMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"培训班次|200", "课程名称|300", "教师名称|80", "开始时间|200", "结束时间|200", "评估表|200", "评课情况（已完成/总数）|200"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetTrainCourse record = records.get(i);
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(record.getTrainId());
            CetTrainEvaTable trainEvaTable = cetTrainEvaTableMapper.selectByPrimaryKey(record.getEvaTableId());
            String[] values = {
                    cetTrain.getName(),
                    record.getName(),
                    record.getTeacher(),
                    DateUtils.formatDate(record.getStartTime(), DateUtils.YYYY_MM_DD_HH_MM),
                    DateUtils.formatDate(record.getEndTime(), DateUtils.YYYY_MM_DD_HH_MM),
                    trainEvaTable.getName(),
                    (record.getFinishCount()==null?0:record.getFinishCount())
                            + "/" + (cetTrain.getEvaCount()==null?0:cetTrain.getEvaCount())
            };
            valuesList.add(values);
        }
        String fileName = "培训课程_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping(value = "/cetTrainCourse_off_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_off_au(CetTrainCourse record, HttpServletRequest request) {

        Integer id = record.getId();
        record.setIsGlobal(BooleanUtils.isTrue(record.getIsGlobal()));

        if(record.getStartTime()!=null && record.getEndTime()!=null
                && record.getStartTime().after(record.getEndTime())){
            return failed("开始时间不能晚于结束时间");
        }

        if (id == null) {

            cetTrainCourseService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加培训课程：%s", record.getId()));
        } else {

            cetTrainCourseService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新培训课程：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping("/cetTrainCourse_off_au")
    public String cetTrainCourse_off_au(Integer id, Integer trainId, ModelMap modelMap) {

        if (id != null) {
            CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(id);
            modelMap.put("cetTrainCourse", cetTrainCourse);

            trainId = cetTrainCourse.getTrainId();
        }
        modelMap.put("cetTrain", cetTrainMapper.selectByPrimaryKey(trainId));

        return "cet/cetTrainCourse/cetTrainCourse_off_au";
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping("/cetTrainCourse_evaTable")
    public String cetTrainCourse_evaTable(int trainId, ModelMap modelMap) {

        modelMap.put("cetTrain", cetTrainMapper.selectByPrimaryKey(trainId));
        modelMap.put("cetTrainEvaTableMap", cetTrainEvaTableService.findAll());

        return "cet/cetTrainCourse/cetTrainCourse_evaTable";
    }

    @RequiresPermissions("cetTrainCourse:edit")
    @RequestMapping(value = "/cetTrainCourse_evaTable", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_evaTable(int trainId, Integer[] ids, int evaTableId) {

        cetTrainCourseService.evaTable(trainId, ids, evaTableId);
        //logger.info(addLog(LogConstants.LOG_ADMIN, "更新培训课程评估表：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainCourse:import")
    @RequestMapping("/cetTrainCourse_import")
    public String cetTrainCourse_import() {

        return "cet/cetTrainCourse/cetTrainCourse_import";
    }

    // 对外培训中导入课程
    @RequiresPermissions("cetTrainCourse:import")
    @RequestMapping(value="/cetTrainCourse_import", method=RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainCourse_import( int trainId, HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<CetTrainCourse> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            row++;
            CetTrainCourse record = new CetTrainCourse();

            String name = StringUtils.trimToNull(xlsRow.get(0));
            if(StringUtils.isBlank(name)){
                throw new OpException("第{0}行课程名称为空", row);
            }
            record.setName(name);

            String teacher = StringUtils.trimToNull(xlsRow.get(1));
            if(StringUtils.isBlank(teacher)){
                throw new OpException("第{0}行教师姓名为空", row);
            }
            record.setTeacher(teacher);

            record.setStartTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(2))));
            record.setEndTime(DateUtils.parseStringToDate(StringUtils.trimToNull(xlsRow.get(3))));

            record.setProjectId(null);
            record.setTrainId(trainId);
            records.add(record);
        }

        int addCount = cetTrainCourseService.offTrainCourseBatchImport(trainId, records);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", addCount);
        resultMap.put("total", records.size());

         logger.info(log(LogConstants.LOG_ADMIN,
                "导入对外培训课程成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }
}
