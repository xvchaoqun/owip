package controller.cet;

import controller.global.OpException;
import domain.cet.CetDiscussGroup;
import domain.cet.CetPlanCourse;
import domain.cet.CetProject;
import domain.cet.CetProjectObj;
import domain.cet.CetProjectObjCadreView;
import domain.cet.CetProjectObjCadreViewExample;
import domain.cet.CetProjectObjView;
import domain.cet.CetProjectObjViewExample;
import domain.cet.CetProjectPlan;
import domain.cet.CetTraineeType;
import domain.ext.ExtJzg;
import mixin.MixinUtils;
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
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.DownloadUtils;
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/cet")
public class CetProjectObjController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetProjectObj:list")
    @RequestMapping("/cetProjectObj")
    public String cetProjectObj(
            Integer userId,
            int projectId,
            Integer traineeTypeId,
            Integer trainCourseId, // 培训班选课页面时传入
            Integer planCourseId, // 培训方案选课页面时传入
            Integer planId, // 上传心得体会页面时传入
            Integer discussGroupId, // 选择小组学员页面时传入（分组研讨）

            @RequestParam(required = false, value = "dpTypes") Integer[] dpTypes,
            @RequestParam(required = false, value = "adminLevels") Integer[] adminLevels,
            @RequestParam(required = false, value = "postIds") Integer[] postIds,
            @RequestParam(defaultValue = "1") Integer cls, //
            @RequestParam(defaultValue = "0") Boolean isQuit,
            ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("isQuit", isQuit);

        List<CetTraineeType> cetTraineeTypes = iCetMapper.getCetTraineeTypes(projectId);
        modelMap.put("cetTraineeTypes", cetTraineeTypes);

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
        modelMap.put("cetProject", cetProject);

        if (traineeTypeId == null) {
            traineeTypeId = cetTraineeTypes.get(0).getId();
        }
        modelMap.put("traineeTypeId", traineeTypeId);

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
        if (postIds != null) {
            modelMap.put("selectPostIds", Arrays.asList(postIds));
        }

        if (userId != null)
            modelMap.put("sysUser", sysUserService.findById(userId));

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
                                   Boolean hasChosen, // 是否选课 （线下、线上、实践）
                                   Boolean isCurrentGroup, // 是否本组 （分组讨论）
                                   Boolean isFinish, // 学习情况 （自主学习）、分组讨论（是否参会）
                                   Boolean hasUploadWrite, // 是否撰写心得体会
                                   @RequestParam(required = false, value = "dpTypes") Long[] dpTypes,
                                   @RequestParam(required = false, value = "adminLevels") Integer[] adminLevels,
                                   @RequestParam(required = false, value = "postIds") Integer[] postIds,
                                   int traineeTypeId,
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

        CetTraineeType cetTraineeType = cetTraineeTypeMapper.selectByPrimaryKey(traineeTypeId);
        String code = cetTraineeType.getCode();

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

        List records = null;
        int count = 0, total = 0;
        switch (code) {
            // 中层干部、后备干部
            case "t_cadre":
            case "t_reserve":
                CetProjectObjCadreViewExample example = new CetProjectObjCadreViewExample();
                CetProjectObjCadreViewExample.Criteria criteria = example.createCriteria().andProjectIdEqualTo(projectId)
                        .andTraineeTypeIdEqualTo(traineeTypeId);
                example.setOrderByClause("id asc");

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
                        criteria.andPdfWriteIsNotNull();
                    }else{
                        criteria.andPdfWriteIsNull();
                    }
                }

                criteria.andIsQuitEqualTo(isQuit);
                if (dpTypes != null) {
                    criteria.andCadreDpTypeIn(Arrays.asList(dpTypes));
                }
                if (postIds != null) {
                    criteria.andPostIdIn(Arrays.asList(postIds));
                }
                if (adminLevels != null) {
                    criteria.andTypeIdIn(Arrays.asList(adminLevels));
                }

                if (userId != null) {
                    criteria.andUserIdEqualTo(userId);
                }

                count = (int) cetProjectObjCadreViewMapper.countByExample(example);
                if ((pageNo - 1) * pageSize >= count) {

                    pageNo = Math.max(1, pageNo - 1);
                }
                records = cetProjectObjCadreViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
                CommonList commonList = new CommonList(count, pageNo, pageSize);
                total = commonList.pageNum;

                if (export == 1) {
                    if (count == 0) {
                        throw new OpException("还没有上传心得体会。");
                    }
                    CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
                    Map<String, File> fileMap = new LinkedHashMap<>();
                    for (Object record : records) {

                        CetProjectObjCadreView obj = (CetProjectObjCadreView) record;
                        String wordWrite = obj.getWordWrite();
                        String pdfWrite = obj.getPdfWrite();
                        String realname = obj.getRealname();

                        fileMap.put(realname + "(" + obj.getCode() + ")" + FileUtils.getExtention(wordWrite),
                                new File(springProps.uploadPath + wordWrite));
                        fileMap.put(realname + "(" + obj.getCode() + ")" + FileUtils.getExtention(pdfWrite),
                                new File(springProps.uploadPath + pdfWrite));
                    }

                    DownloadUtils.zip(fileMap, String.format("[%s]心得体会（%s）", cetProject.getName(),
                            cetTraineeType.getName()), request, response);
                }
                break;
            default:
                break;
        }

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

    // 设置应完成学时
    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping("/cetProjectObj_shouldFinishPeriod")
    public String cetProjectObj_shouldFinishPeriod(@RequestParam(value = "ids[]") int[] ids, ModelMap modelMap) {

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
    public Map do_cetProjectObj_add(boolean isQuit,
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
    public Map do_cetProjectObj_apply(byte opType, int trainCourseId, int projectId,
                                        @RequestParam(value = "ids[]", required = false) Integer[] ids,
                                        HttpServletRequest request) {

        cetProjectObjService.apply(projectId, ids, opType, trainCourseId);
        logger.info(addLog(LogConstants.LOG_CET, "设置为必选学员/选课/退课： %s, %s, %s",
                StringUtils.join(ids, ","), opType, trainCourseId));

        return success(FormUtils.SUCCESS);
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

    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping(value = "/cetProjectObj_add", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectObj_add(int projectId, int traineeTypeId,
                                    @RequestParam(value = "userIds[]", required = false) Integer[] userIds,
                                    HttpServletRequest request) {

        cetProjectObjService.addOrUpdate(projectId, traineeTypeId, userIds);
        logger.info(addLog(LogConstants.LOG_CET, "编辑培训对象： %s, %s, %s", projectId, traineeTypeId,
                StringUtils.join(userIds, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping("/cetProjectObj_add")
    public String cetProjectObj_add(int projectId, int traineeTypeId, ModelMap modelMap) {

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
        modelMap.put("cetProject", cetProject);
        CetTraineeType cetTraineeType = cetTraineeTypeMapper.selectByPrimaryKey(traineeTypeId);
        modelMap.put("cetTraineeType", cetTraineeType);
        String code = cetTraineeType.getCode();
        switch (code) {
            // 中层干部
            case "t_cadre":
                return "cet/cetProjectObj/cetProjectObj_selectCadres";
        }

        return null;
    }

    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping("/cetProjectObj_selectCadres_tree")
    @ResponseBody
    public Map cetProjectObj_selectCadres_tree(int projectId) throws IOException {

        Set<Integer> selectIdSet = cetProjectObjService.getSelectedProjectObjUserIdSet(projectId);

        Set<Byte> cadreStatusList = new HashSet<>();
        cadreStatusList.add(CadreConstants.CADRE_STATUS_MIDDLE);
        TreeNode tree = cadreCommonService.getTree(new LinkedHashSet<>(cadreService.findAll().values()),
                cadreStatusList, selectIdSet, null, false, true, false);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }


    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping(value = "/cetProjectObj_uploadWrite", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectObj_uploadWrite(int id, MultipartFile _pdfFilePath,
                                            MultipartFile _wordFilePath,
                                            HttpServletRequest request) throws IOException, InterruptedException {

        String wordWrite = upload(_wordFilePath, "cetProjectObj");
        String pdfWrite = uploadPdf(_pdfFilePath, "cetProjectObj");

        if (StringUtils.isNotBlank(wordWrite) || StringUtils.isNotBlank(pdfWrite)) {

            CetProjectObj record = new CetProjectObj();
            record.setId(id);
            record.setWordWrite(wordWrite);
            record.setPdfWrite(pdfWrite);
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
        modelMap.put("sysUser", sysUserService.findById(userId));

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
            HttpServletRequest request) {

        if(StringUtils.isNotBlank(mobile) && !FormUtils.match(PropertiesUtils.getString("mobile.regex"), mobile)){
            return failed("手机号码有误："+ mobile);
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

        CetProjectObjViewExample example = new CetProjectObjViewExample();
        //example.setOrderByClause("create_time desc");
        if (StringUtils.isNotBlank(searchStr)) {
            example.or().andProjectIdEqualTo(projectId).andUsernameLike(searchStr + "%");
            example.or().andProjectIdEqualTo(projectId).andCodeLike(searchStr + "%");
            example.or().andProjectIdEqualTo(projectId).andRealnameLike(searchStr + "%");
        } else {
            example.createCriteria().andProjectIdEqualTo(projectId);
        }

        long count = cetProjectObjViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetProjectObjView> uvs = cetProjectObjViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if (null != uvs && uvs.size() > 0) {
            for (CetProjectObjView uv : uvs) {
                Map<String, Object> option = new HashMap<>();
                option.put("id", uv.getUserId() + "");
                option.put("text", uv.getRealname());
                option.put("user", userBeanService.get(uv.getUserId()));

                if (StringUtils.isNotBlank(uv.getCode())) {
                    option.put("code", uv.getCode());
                    ExtJzg extJzg = extJzgService.getByCode(uv.getCode());
                    if (extJzg != null) {
                        option.put("unit", extJzg.getDwmc());
                    }
                }
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
