package controller.cet;

import bean.UserBean;
import domain.cet.*;
import domain.sys.SysUserView;
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
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/cet")
public class CetProjectObjController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetProjectObj:list")
    @RequestMapping("/cetProjectObj")
    public String cetProjectObj(
            Integer userId,
            int projectId,
            Integer traineeTypeId, // 类别

            Integer trainCourseId, // 培训班选课页面时传入
            Integer planCourseId, // 培训方案选课页面时传入
            Integer planId, // 上传心得体会页面时传入
            Integer discussGroupId, // 选择小组学员页面时传入（分组研讨）

            @RequestParam(required = false, value = "dpTypes") Integer[] dpTypes,
            @RequestParam(required = false, value = "adminLevels") Integer[] adminLevels,
            @RequestParam(required = false, value = "postTypes") Integer[] postTypes,
            @RequestParam(defaultValue = "1") Integer cls, //
            @RequestParam(defaultValue = "0") Boolean isQuit,
            ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("isQuit", isQuit);

        List<CetTraineeType> cetTraineeTypes = iCetMapper.getCetTraineeTypes(projectId);
        modelMap.put("cetTraineeTypes", cetTraineeTypes);

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
        modelMap.put("cetProject", cetProject);

        Map<Integer, Integer> typeCountMap = new HashMap<>();
        List<Map> typeCountList = iCetMapper.projectObj_typeCount(projectId, isQuit);
        for (Map resultMap : typeCountList) {
            int _traineeTypeId = ((Long) resultMap.get("trainee_type_id")).intValue();
            int num = ((Long) resultMap.get("num")).intValue();
            typeCountMap.put(_traineeTypeId, num);
        }
        modelMap.put("typeCountMap", typeCountMap);

        if(traineeTypeId==null && typeCountList.size()>0){
            traineeTypeId = ((Long) typeCountList.get(0).get("trainee_type_id")).intValue();
        }
        if (traineeTypeId == null) {
            traineeTypeId = cetTraineeTypes.get(0).getId();
        }
        modelMap.put("traineeTypeId", traineeTypeId);
        CetTraineeType cetTraineeType = cetTraineeTypeMapper.selectByPrimaryKey(traineeTypeId);
        modelMap.put("cetTraineeType", cetTraineeType);

        if (planCourseId != null) {
            CetPlanCourse cetPlanCourse = cetPlanCourseMapper.selectByPrimaryKey(planCourseId);
            planId = cetPlanCourse.getPlanId();
        }
        if (planId != null) {
            CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
            modelMap.put("cetProjectPlan", cetProjectPlan);
        }
        if (discussGroupId != null) {
            // 获取所有培训对象的分组情况
            CetDiscussGroup cetDiscussGroup = cetDiscussGroupMapper.selectByPrimaryKey(discussGroupId);
            int discussId = cetDiscussGroup.getDiscussId();
            modelMap.put("cetDiscuss", cetDiscussMapper.selectByPrimaryKey(discussId));
           /* Map<Integer, CetDiscussGroupObj> objMap = cetDiscussService.getObjMap(discussId);
            modelMap.put("objMap", objMap);*/
        }

        if (dpTypes != null) {
            modelMap.put("selectDpTypes", Arrays.asList(dpTypes));
        }
        if (adminLevels != null) {
            modelMap.put("selectAdminLevels", Arrays.asList(adminLevels));
        }
        if (postTypes != null) {
            modelMap.put("selectPostTypes", Arrays.asList(postTypes));
        }

        if (userId != null)
            modelMap.put("sysUser", CmTag.getUserById(userId));

        return "cet/cetProjectObj/cetProjectObj_page";
    }

    @RequiresPermissions("cetProjectObj:list")
    @RequestMapping("/cetProjectObj_data")
    public void cetProjectObj_data(HttpServletResponse response,
                                   @RequestParam(defaultValue = "0") Boolean isQuit,
                                   int projectId,
                                   Integer trainCourseId, // 培训班选课页面时传入
                                   Integer planCourseId, // 培训方案选课页面时传入
                                   Integer discussGroupId, // 选择小组学员页面时传入（分组研讨）
                                   Integer userId,
                                   BigDecimal finishPeriodStart,
                                   BigDecimal finishPeriodEnd,
                                   Boolean hasChosen, // 是否选课 （线下、线上、实践）
                                   Boolean isCurrentGroup, // 是否本组 （分组讨论）
                                   Boolean isFinish, // 学习情况 （自主学习）、分组讨论（是否参会）
                                   Boolean hasUploadWrite, // 是否撰写心得体会
                                   @RequestParam(required = false, value = "dpTypes") Integer[] dpTypes,
                                   @RequestParam(required = false, value = "adminLevels") Integer[] adminLevels,
                                   @RequestParam(required = false, value = "postTypes") Integer[] postTypes,
                                   Integer traineeTypeId,
                                   @RequestParam(required = false, defaultValue = "0") int export,
                                   @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                   HttpServletRequest request,
                                   Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        List<Integer> applyUserIds = null;
        if (hasChosen != null && trainCourseId != null) {
            applyUserIds = iCetMapper.applyUserIds(trainCourseId);
        }

        List<Integer> groupUserIds = null;
        if(isFinish!=null) isCurrentGroup = true;
        if(isCurrentGroup != null && discussGroupId!=null){
            groupUserIds = iCetMapper.groupUserIds(discussGroupId, isFinish);
        }

        List<Integer> finishUserIds = null;
        if(isFinish != null && planCourseId!=null){
            finishUserIds = iCetMapper.finishUserIds(planCourseId, isFinish);
        }

        CetProjectObjExample example = new CetProjectObjExample();
        CetProjectObjExample.Criteria criteria = example.createCriteria().andProjectIdEqualTo(projectId)
                .andTraineeTypeIdEqualTo(traineeTypeId);
        example.setOrderByClause("id desc");

        if (traineeTypeId != null){
            criteria.andTraineeTypeIdEqualTo(traineeTypeId);
        }
        if (hasChosen != null && trainCourseId != null) {
            if (hasChosen) {
                if (applyUserIds != null && applyUserIds.size() > 0) {
                    criteria.andUserIdIn(applyUserIds);
                } else {
                    criteria.andIdIsNull();
                }
            } else {
                if (applyUserIds != null && applyUserIds.size() > 0) {
                    criteria.andUserIdNotIn(applyUserIds);
                }
            }
        }
        if(isCurrentGroup != null && discussGroupId!=null){

            if (isCurrentGroup) {
                if (groupUserIds != null && groupUserIds.size() > 0) {
                    criteria.andUserIdIn(groupUserIds);
                } else {
                    criteria.andIdIsNull();
                }
            } else {
                if (groupUserIds != null && groupUserIds.size() > 0) {
                    criteria.andUserIdNotIn(groupUserIds);
                }
            }
        }
        if(isFinish != null && planCourseId!=null){

            if (isFinish) {
                if (finishUserIds != null && finishUserIds.size() > 0) {
                    criteria.andUserIdIn(finishUserIds);
                } else {
                    criteria.andIdIsNull();
                }
            } else {
                if (finishUserIds != null && finishUserIds.size() > 0) {
                    criteria.andUserIdIn(finishUserIds);
                } else {
                    criteria.andIdIsNull();
                }
            }
        }

        if(hasUploadWrite!=null){
            if(hasUploadWrite){
                criteria.andWriteFilePathIsNotNull();
            }else{
                criteria.andWriteFilePathIsNull();
            }
        }

        criteria.andIsQuitEqualTo(isQuit);
        if (dpTypes != null) {
            criteria.andDpTypeIdIn(new HashSet<>(Arrays.asList(dpTypes)));
        }
        if (postTypes != null) {
            criteria.andPostTypeIn(Arrays.asList(postTypes));
        }
        if (adminLevels != null) {
            criteria.andAdminLevelIn(Arrays.asList(adminLevels));
        }

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (finishPeriodStart != null) {
            criteria.andFinishPeriodGreaterThanOrEqualTo(finishPeriodStart);
        }
        if (finishPeriodEnd != null) {
            criteria.andFinishPeriodLessThanOrEqualTo(finishPeriodEnd);
        }

        List<CetProjectObj> records;
        if (export == 1) {

            criteria.andWriteFilePathIsNotNull();
            records = cetProjectObjMapper.selectByExample(example);

            CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
            Map<String, File> fileMap = new LinkedHashMap<>();
            for (Object record : records) {

                CetProjectObj obj = (CetProjectObj) record;
                String writeFilePath = obj.getWriteFilePath();
                SysUserView uv = obj.getUser();
                String realname = uv.getRealname();

                fileMap.put(realname + "(" + uv.getCode() + ")" + FileUtils.getExtention(writeFilePath),
                        new File(springProps.uploadPath + writeFilePath));
            }
            DownloadUtils.addFileDownloadCookieHeader(response);

            CetTraineeType cetTraineeType = cetTraineeTypeMapper.selectByPrimaryKey(traineeTypeId);
            DownloadUtils.zip(fileMap, String.format("[%s]心得体会（%s）", cetProject.getName(),
                    cetTraineeType.getName()), request, response);
            return;
        }

        int count = (int) cetProjectObjMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        records = cetProjectObjMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        int total = commonList.pageNum;

        request.setAttribute("trainCourseId", trainCourseId);
        request.setAttribute("planCourseId", planCourseId);
        request.setAttribute("discussGroupId", discussGroupId);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", total);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetProjectObj.class, cetProjectObjMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    // 同步学员特定信息
    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping(value = "/cetProjectObj_syncTraineeInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectObj_syncTraineeInfo(int projectId, int traineeTypeId, HttpServletRequest request) {

        cetProjectObjService.syncTraineeInfo(projectId, traineeTypeId);

        logger.info(addLog(LogConstants.LOG_CET, "同步学员特定信息： %s, %s", projectId, traineeTypeId));
        return success(FormUtils.SUCCESS);
    }

    // 导出学时情况
    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping("/cetProjectObj_exportFinishPeriod")
    public void cetProjectObj_exportFinishPeriod(int projectId, int traineeTypeId, HttpServletResponse response) throws IOException {

        cetExportService.exportFinishPeriod(projectId, traineeTypeId, response);
        return;
    }

    // 设置应完成学时
    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping("/cetProjectObj_shouldFinishPeriod")
    public String cetProjectObj_shouldFinishPeriod(@RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (ids.length == 1) {
            modelMap.put("cetProjectObj", cetProjectObjMapper.selectByPrimaryKey(ids[0]));
        }

        return "cet/cetProjectObj/cetProjectObj_shouldFinishPeriod";
    }

    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping(value = "/cetProjectObj_shouldFinishPeriod", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectObj_shouldFinishPeriod(int projectId,
                                                   @RequestParam(value = "ids[]", required = false) Integer[] ids,
                                                   BigDecimal shouldFinishPeriod) {

        if (shouldFinishPeriod != null && shouldFinishPeriod.compareTo(BigDecimal.ZERO) <= 0) {
            return failed("学时数必须大于0");
        }

        cetProjectObjService.setShouldFinishPeriod(projectId, ids, shouldFinishPeriod);

        logger.info(addLog(LogConstants.LOG_PMD, "设置应完成学时-%s-%s",
                StringUtils.join(ids, ","), shouldFinishPeriod));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping(value = "/cetProjectObj_quit", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectObj_quit(boolean isQuit,
                                    @RequestParam(value = "ids[]", required = false) Integer[] ids,
                                    HttpServletRequest request) {

        cetProjectObjService.quit(isQuit, ids);
        logger.info(addLog(LogConstants.LOG_CET, "培训对象： %s, %s", isQuit ? "退出" : "重新学习",
                StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    /**
     * 设置为必选学员/退课
     *
     * @param opType  1：设置为必选 2：设置为可选 3: 选课  4：退课
     * @param trainCourseId
     * @param projectId
     * @param ids
     * @param request
     * @return
     */
    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping(value = "/cetProjectObj_apply", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectObj_apply(byte opType, int trainCourseId, int projectId, int traineeTypeId,
                                        @RequestParam(value = "ids[]", required = false) Integer[] ids,
                                        HttpServletRequest request) {

        cetProjectObjService.apply(projectId, traineeTypeId, ids, opType, trainCourseId);
        logger.info(addLog(LogConstants.LOG_CET, "设置为必选学员/选课/退课： %s, %s, %s",
                StringUtils.join(ids, ","), opType, trainCourseId));

        return success(FormUtils.SUCCESS);
    }

    // 导入选课情况
    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping("/cetProjectObj_course_import")
    public String cetProjectObj_course_import() {

        return "cet/cetProjectObj/cetProjectObj_course_import";
    }

    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping(value="/cetProjectObj_course_import", method=RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectObj_course_import(int projectId, int trainCourseId,
                                            HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);


        Map<String, Object> retMap = cetProjectObjService.imports(xlsRows, projectId, trainCourseId);

        int totalCount = xlsRows.size();
        int successCount = (int) retMap.get("success");
        List<Map<Integer, String>> failedXlsRows = (List<Map<Integer, String>>)retMap.get("failedXlsRows");

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("successCount", successCount);
        resultMap.put("failedXlsRows", failedXlsRows);
        resultMap.put("total", xlsRows.size());

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入培训对象成功，总共{0}条记录，其中成功导入{1}条记录，{2}条失败",
                totalCount, successCount, failedXlsRows.size()));

        return resultMap;
    }

    // 自动结业
    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping(value = "/cetProjectObj_autoGraduate", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectObj_autoGraduate(int projectId, HttpServletRequest request) {

        cetProjectObjService.autoGraduate(projectId);
        logger.info(addLog(LogConstants.LOG_CET, "自动结业： %s", projectId));

        return success(FormUtils.SUCCESS);
    }

    // 手动结业
    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping(value = "/cetProjectObj_forceGraduate", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectObj_forceGraduate(@RequestParam(value = "ids[]", required = false) Integer[] ids,
                                              HttpServletRequest request) {

        cetProjectObjService.forceGraduate(ids);
        logger.info(addLog(LogConstants.LOG_CET, "手动结业： %s", StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    /*@RequiresPermissions("cetProjectObj:edit")
    @RequestMapping(value = "/cetProjectObj_batchSelect", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectObj_batchSelect(int projectId, int traineeTypeId,
                                    @RequestParam(value = "userIds[]", required = false) Integer[] userIds,
                                    HttpServletRequest request) {

        cetProjectObjService.addOrUpdate(projectId, traineeTypeId, userIds);
        logger.info(addLog(LogConstants.LOG_CET, "批量选择培训对象： %s, %s, %s", projectId, traineeTypeId,
                StringUtils.join(userIds, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping("/cetProjectObj_batchSelect")
    public String cetProjectObj_batchSelect(int projectId, int traineeTypeId, ModelMap modelMap) {

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
        modelMap.put("cetProject", cetProject);
        CetTraineeType cetTraineeType = cetTraineeTypeMapper.selectByPrimaryKey(traineeTypeId);
        modelMap.put("cetTraineeType", cetTraineeType);

        return "cet/cetProjectObj/cetProjectObj_batchSelect";
    }*/

    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping(value = "/cetProjectObj_add", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectObj_add(int projectId, int traineeTypeId, int userId) {

        cetProjectObjService.addOrUpdate(projectId, traineeTypeId, new Integer[]{userId});
        logger.info(addLog(LogConstants.LOG_CET, "添加培训对象： %s, %s, %s", projectId, traineeTypeId, userId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping("/cetProjectObj_add")
    public String cetProjectObj_add(int projectId, int traineeTypeId, ModelMap modelMap) {

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
        modelMap.put("cetProject", cetProject);

        List<CetTraineeType> cetTraineeTypes = iCetMapper.getCetTraineeTypes(projectId);
        modelMap.put("cetTraineeTypes", cetTraineeTypes);

        return "cet/cetProjectObj/cetProjectObj_add";
    }

    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping(value = "/cetProjectObj_uploadWrite", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectObj_uploadWrite(int id,
                                            MultipartFile _writeFilePath,
                                            HttpServletRequest request) throws IOException, InterruptedException {

        String writeFilePath = upload(_writeFilePath, "cetProjectObj");

        if (StringUtils.isNotBlank(writeFilePath)) {

            CetProjectObj record = new CetProjectObj();
            record.setId(id);
            record.setWriteFilePath(writeFilePath);
            cetProjectObjService.updateByPrimaryKeySelective(record);

            logger.info(addLog(LogConstants.LOG_CET, "上传心得体会：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping("/cetProjectObj_uploadWrite")
    public String cetProjectObj_uploadWrite(int id, ModelMap modelMap) {

        CetProjectObj cetProjectObj = cetProjectObjMapper.selectByPrimaryKey(id);
        Integer userId = cetProjectObj.getUserId();
        modelMap.put("sysUser", CmTag.getUserById(userId));

        return "cet/cetProjectObj/cetProjectObj_uploadWrite";
    }

    // 短信提醒（撰写心得体会）
    @RequiresPermissions("cetProjectObj:list")
    @RequestMapping(value = "/cetProjectObj_uploadWriteMsg", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectObj_uploadWriteMsg(
            int projectId,
            @RequestParam(value = "objIds[]", required = false) Integer[] objIds,
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

        Map<String, Integer> result = cetShortMsgService.sendUploadWriteMsg(projectId, objIds, mobile, msg);
        logger.info(addLog(LogConstants.LOG_CET, "短信提醒（撰写心得体会）：%s-%s-%s",
                msg, mobile, StringUtils.join(objIds, ",")));
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("totalCount", result.get("total"));
        resultMap.put("successCount", result.get("success"));
        return resultMap;
    }

    @RequiresPermissions("cetProjectObj:list")
    @RequestMapping("/cetProjectObj_uploadWriteMsg")
    public String cetProjectObj_uploadWriteMsg(int projectId, @RequestParam(value = "objIds[]", required = false) Integer[] objIds,
                                               ModelMap modelMap) {

        List<Integer> userIds = iCetMapper.notUploadWriteUserIds(projectId, objIds);
        modelMap.put("userIds", userIds);

        return "cet/cetProjectObj/cetProjectObj_uploadWriteMsg";
    }

    @RequiresPermissions("cetProjectObj:del")
    @RequestMapping(value = "/cetProjectObj_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectObj_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetProjectObjService.del(id);
            logger.info(addLog(LogConstants.LOG_CET, "删除培训对象：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectObj:del")
    @RequestMapping(value = "/cetProjectObj_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetProjectObj_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cetProjectObjService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除培训对象：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectObj:clearWrite")
    @RequestMapping(value = "/cetProjectObj_clearWrite", method = RequestMethod.POST)
    @ResponseBody
    public Map cetProjectObj_clearWrite(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            cetProjectObjService.clearWrite(ids);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除心得体会：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectObj:list")
    @RequestMapping("/cetProjectObj_selects")
    @ResponseBody
    public Map cetProjectObj_selects(Integer pageSize, Integer pageNo,
                                     int projectId,
                                     String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);


        long count = iCetMapper.countObjList(projectId, searchStr);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetProjectObj> objs = iCetMapper.selectObjList(projectId, searchStr, new RowBounds((pageNo - 1) * pageSize, pageSize));
    
        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if (null != objs && objs.size() > 0) {

            for (CetProjectObj obj : objs) {
                SysUserView uv = obj.getUser();
                Map<String, Object> option = new HashMap<>();
                option.put("id", obj.getUserId() + "");
                option.put("text", uv.getRealname());
                UserBean userBean = userBeanService.get(obj.getUserId());
                option.put("user", userBean);
                option.put("code", uv.getCode());
                option.put("unit", userBean.getUnit());
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
