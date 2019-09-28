package controller.oa;

import controller.global.OpException;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.oa.*;
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
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.constants.OaConstants;
import sys.gson.GsonUtils;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
@RequestMapping("/oa")
public class OaTaskController extends OaBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("oaTask:list")
    @RequestMapping("/oaTask")
    public String oaTask(@RequestParam(required = false, defaultValue = "1") Byte cls,
                         @RequestParam(required = false, defaultValue = "1") Boolean showAll,
                         ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("showAll", showAll);
        Integer currentUserId = ShiroHelper.getCurrentUserId();
        List<Integer> oaTaskTypes = oaTaskAdminService.adminTypes(currentUserId);
        modelMap.put("oaTaskTypes", oaTaskTypes);

        OaTaskAdmin oaTaskAdmin = oaTaskAdminMapper.selectByPrimaryKey(currentUserId);
        modelMap.put("oaTaskAdmin", oaTaskAdmin);

        return "oa/oaTask/oaTask_page";
    }

    @RequiresPermissions("oaTask:list")
    @RequestMapping("/oaTask_data")
    public void oaTask_data(HttpServletResponse response,
                            @RequestParam(required = false, defaultValue = "1") Byte cls,
                            Integer type,
                            String name,
                            @RequestParam(required = false, defaultValue = "1") Boolean showAll,
                            Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        OaTaskViewExample example = new OaTaskViewExample();
        OaTaskViewExample.Criteria criteria = example.createCriteria()
                .andIsDeleteEqualTo(false);
        example.setOrderByClause("create_time desc");

        switch (cls) {
            case 1:
                criteria.andStatusIn(Arrays.asList(OaConstants.OA_TASK_STATUS_INIT,
                        OaConstants.OA_TASK_STATUS_BACK, OaConstants.OA_TASK_STATUS_PUBLISH));
                break;
            case 2:
                criteria.andStatusEqualTo(OaConstants.OA_TASK_STATUS_FINISH);
                break;
            case 3:
                criteria.andStatusEqualTo(OaConstants.OA_TASK_STATUS_ABOLISH);
                break;
        }

        showAll = BooleanUtils.isTrue(showAll)
                && BooleanUtils.isTrue(oaTaskAdminMapper
                .selectByPrimaryKey(ShiroHelper.getCurrentUserId()).getShowAll());

        if(!showAll){
            criteria.listCreateOrShareTasks(ShiroHelper.getCurrentUserId());
        }

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }

        long count = oaTaskViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<OaTaskView> records = oaTaskViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(oaTask.class, oaTaskMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("oaTask:edit")
    @RequestMapping(value = "/oaTask_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaTask_au(OaTask record, HttpServletRequest request) {

        Integer id = record.getId();
        if (id == null) {

            oaTaskService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_OA, "添加协同办公任务：%s", record.getId()));
        } else {

            oaTaskService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_OA, "更新协同办公任务：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("oaTask:edit")
    @RequestMapping("/oaTask_au")
    public String oaTask_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            OaTask oaTask = oaTaskMapper.selectByPrimaryKey(id);
            modelMap.put("oaTask", oaTask);
        }
        List<Integer> oaTaskTypes = oaTaskAdminService.adminTypes(ShiroHelper.getCurrentUserId());
        modelMap.put("oaTaskTypes", oaTaskTypes);

        return "oa/oaTask/oaTask_au";
    }

    @RequiresPermissions("oaTask:edit")
    @RequestMapping(value = "/oaTask_share", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaTask_share(int taskId, int userId, boolean share) {

        oaTaskService.share(taskId, userId, share);
        logger.info(addLog(LogConstants.LOG_OA, "共享协同办公任务：%s, %s, %s", taskId, userId, share));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("oaTask:edit")
    @RequestMapping("/oaTask_share")
    public String oaTask_share(int taskId, ModelMap modelMap) {

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
        modelMap.put("oaTask", oaTask);

        List<SysUserView> uvs = new ArrayList<>();
        Set<Integer> userIdSet = NumberUtils.toIntSet(oaTask.getUserIds(), ",");
        for (Integer userId : userIdSet) {
            uvs.add(sysUserService.findById(userId));
        }
        modelMap.put("userList", uvs);

        return "oa/oaTask/oaTask_share";
    }

    @RequiresPermissions("oaTask:del")
    @RequestMapping(value = "/oaTask_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            oaTaskService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_OA, "批量删除协同办公任务：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("oaTask:edit")
    @RequestMapping(value = "/oaTaskFile_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaTaskFile_au(int taskId, MultipartFile[] files, HttpServletRequest request) throws IOException, InterruptedException {

        if (files == null || files.length == 0) {
            return failed("附件不能为空。");
        }

        oaTaskService.checkAuth(taskId, null);

        for (MultipartFile file : files) {

            OaTaskFile record = new OaTaskFile();
            record.setTaskId(taskId);
            record.setFileName(file.getOriginalFilename());
            record.setFilePath(upload(file, "oa_task_file"));
            record.setCreateTime(new Date());

            oaTaskFileMapper.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_OA, "添加协同办公任务附件：%s", record.getFileName()));

        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("oaTask:edit")
    @RequestMapping("/oaTaskFiles")
    public String oaTaskFiles(int taskId, ModelMap modelMap) {

        modelMap.put("oaTask", oaTaskMapper.selectByPrimaryKey(taskId));
        modelMap.put("oaTaskFiles", oaTaskService.getTaskFiles(taskId));

        return "oa/oaTask/oaTaskFiles";
    }

    @RequiresPermissions("oaTask:delFile")
    @RequestMapping(value = "/oaTaskFile_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaTaskFile_del(HttpServletRequest request, Integer id) {

        OaTaskFile oaTaskFile = oaTaskFileMapper.selectByPrimaryKey(id);

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(oaTaskFile.getTaskId());
        oaTaskService.checkAuth(oaTask.getId(), null);

        oaTaskFileMapper.deleteByPrimaryKey(id);
        logger.info(addLog(LogConstants.LOG_OA, "删除协同办公任务附件：%s", oaTaskFile.getFileName()));

        return success(FormUtils.SUCCESS);
    }

    // 从干部库中选择任务对象
    @RequiresPermissions("oaTask:edit")
    @RequestMapping("/oaTask_selectCadres")
    public String selectCadres(ModelMap modelMap) throws IOException {

        return "oa/oaTask/oaTask_selectCadres";
    }

    @RequiresPermissions("oaTask:edit")
    @RequestMapping("/oaTask_selectCadres_tree")
    @ResponseBody
    public Map oaTask_selectCadres_tree(@RequestParam(required = false) Integer[] userIds) throws IOException {

        Set<Integer> selectIdSet = null;
        if (userIds != null && userIds.length > 0) {
            selectIdSet = new HashSet<>(Arrays.asList(userIds));
        }

        Set<Byte> cadreStatusList = new HashSet(Arrays.asList(CadreConstants.CADRE_STATUS_MIDDLE,
                CadreConstants.CADRE_STATUS_LEADER));
        TreeNode tree = cadreCommonService.getTree(new LinkedHashSet<CadreView>(cadreService.findAll().values()),
                cadreStatusList, selectIdSet, null, false, true, false);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("oaTask:edit")
    @RequestMapping(value = "/oaTask_selectCadres", method = RequestMethod.POST)
    public void do_oaTask_selectCadres(
            @RequestParam(value = "userIds[]") Integer[] userIds,
            HttpServletResponse response) throws IOException {

        List<CadreView> cadres = new ArrayList<>();
        if (userIds != null) {

            CadreViewExample example = new CadreViewExample();
            example.createCriteria().andUserIdIn(Arrays.asList(userIds));
            example.setOrderByClause("sort_order desc");
            cadres = cadreViewMapper.selectByExample(example);
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("cadres", cadres);
        JSONUtils.write(response, resultMap);
    }

    @RequiresPermissions("oaTask:edit")
    @RequestMapping(value = "/oaTask_selectUser", method = RequestMethod.POST)
    public void do_oaTask_selectUser(
            Integer userId,
            String mobile,
            HttpServletResponse response) throws IOException {

        TaskUser user = new TaskUser();
        if (userId != null) {
            CadreView cv = cadreService.dbFindByUserId(userId);
            if (cv != null) {
                user.setUserId(cv.getUserId());
                user.setRealname(cv.getRealname());
                user.setCode(cv.getCode());
                user.setTitle(cv.getTitle());
                user.setMobile(cv.getMobile());
            } else {

                SysUserView uv = sysUserService.findById(userId);
                user.setUserId(uv.getId());
                user.setRealname(uv.getRealname());
                user.setCode(uv.getCode());
                user.setTitle(uv.getUnit());
                user.setMobile(uv.getMobile());
            }
        }

        if (StringUtils.isNotBlank(mobile)) {
            user.setMobile(mobile);
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("user", user);
        JSONUtils.write(response, resultMap);
    }

    @RequiresPermissions("oaTask:edit")
    @RequestMapping(value = "/oaTask_importUsers", method = RequestMethod.POST)
    public void do_oaTask_importUsers(HttpServletRequest request, HttpServletResponse response) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<TaskUser> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            TaskUser user = new TaskUser();
            row++;
            String code = StringUtils.trimToNull(xlsRow.get(0));
            if (StringUtils.isBlank(code)) {
                throw new OpException("第{0}行工作证号为空", row);
            }
            SysUserView uv = sysUserService.findByCode(code);
            if (uv == null) {
                throw new OpException("第{0}行工作证号{1}不存在", row, code);
            }
            CadreView cv = cadreService.dbFindByUserId(uv.getUserId());
            if (cv != null) {
                user.setUserId(cv.getUserId());
                user.setRealname(cv.getRealname());
                user.setCode(cv.getCode());
                user.setTitle(cv.getTitle());
                user.setMobile(cv.getMobile());
            } else {
                user.setUserId(uv.getId());
                user.setRealname(uv.getRealname());
                user.setCode(uv.getCode());
                user.setTitle(uv.getUnit());
                user.setMobile(uv.getMobile());
            }

            String mobile = StringUtils.trimToNull(xlsRow.get(2));
            if (StringUtils.isNotBlank(mobile)) {
                user.setMobile(mobile);
            }
            String title = StringUtils.trimToNull(xlsRow.get(3));
            if (StringUtils.isNotBlank(title)) {
                user.setTitle(title);
            }

            records.add(user);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("users", records);
        JSONUtils.write(response, resultMap);
    }

    @RequiresPermissions("oaTask:edit")
    @RequestMapping(value = "/oaTask_selectUsersFromHisthory", method = RequestMethod.POST)
    public void do_oaTask_selectUsersFromHisthory(int taskId, HttpServletRequest request, HttpServletResponse response) throws InvalidFormatException, IOException {

        List<OaTaskUser> taskUsers = oaTaskUserService.getTaskUsers(taskId);

        List<TaskUser> records = new ArrayList<>();
        for (OaTaskUser taskUser:taskUsers) {

            TaskUser user = new TaskUser();
            CadreView cv = cadreService.dbFindByUserId(taskUser.getUserId());
            if (cv != null) {
                user.setUserId(cv.getUserId());
                user.setRealname(cv.getRealname());
                user.setCode(cv.getCode());
                user.setTitle(cv.getTitle());
                user.setMobile(cv.getMobile());
            } else {
                SysUserView uv = sysUserService.findById(taskUser.getUserId());
                user.setUserId(uv.getId());
                user.setRealname(uv.getRealname());
                user.setCode(uv.getCode());
                user.setTitle(uv.getUnit());
                user.setMobile(uv.getMobile());
            }

            records.add(user);
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("users", records);
        JSONUtils.write(response, resultMap);
    }

    // 任务对象列表管理
    @RequiresPermissions("oaTask:edit")
    @RequestMapping("/oaTask_users")
    public String oaTask_users(int id, ModelMap modelMap) throws IOException {

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(id);
        modelMap.put("oaTask", oaTask);

        OaTaskUserViewExample example = new OaTaskUserViewExample();
        example.createCriteria().andTaskIdEqualTo(id).andIsDeleteEqualTo(false);
        example.setOrderByClause("sort_order asc");
        List<OaTaskUserView> selectUsers = oaTaskUserViewMapper.selectByExample(example);
        modelMap.put("selectUsers", selectUsers);

        return "oa/oaTask/oaTask_users";
    }

    // 更新任务对象
    @RequiresPermissions("oaTask:edit")
    @RequestMapping(value = "/oaTask_users", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaTask_users(Integer id, String users) throws UnsupportedEncodingException {

        List<TaskUser> taskUsers = GsonUtils.toBeans(users, TaskUser.class);
        oaTaskService.updateTaskUsers(id, taskUsers);

        return success(FormUtils.SUCCESS);
    }

    // 发布/召回
    @RequiresPermissions("oaTask:edit")
    @RequestMapping(value = "/oaTask_publish", method = RequestMethod.POST)
    @ResponseBody
    public Map oaTask_publish(HttpServletRequest request, Integer id, Boolean publish, ModelMap modelMap) {

        if (id != null) {

            OaTask oaTask = oaTaskMapper.selectByPrimaryKey(id);

            publish = BooleanUtils.isTrue(publish);
            OaTask record = new OaTask();
            record.setId(id);
            record.setStatus(publish ? OaConstants.OA_TASK_STATUS_PUBLISH
                    : OaConstants.OA_TASK_STATUS_BACK);
            record.setIsPublish(publish);
            if (publish && oaTask.getPubDate() == null) {
                record.setPubDate(new Date()); // 记录第一次发布的日期
            }

            oaTaskService.updateByPrimaryKeySelective(record);

            logger.info(addLog(LogConstants.LOG_OA, (BooleanUtils.isTrue(publish) ? "发布" : "召回") + "任务：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    // 作废
    @RequiresPermissions("oaTask:edit")
    @RequestMapping(value = "/oaTask_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map oaTask_abolish(HttpServletRequest request,
                              @RequestParam(value = "ids[]") Integer[] ids,
                              @RequestParam(required = false, defaultValue = "1") Boolean isAbolish,
                              ModelMap modelMap) {

        oaTaskService.batchAbolish(ids, isAbolish);
        logger.info(addLog(LogConstants.LOG_OA, "作废任务：%s", StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    // 任务完结
    @RequiresPermissions("oaTask:edit")
    @RequestMapping(value = "/oaTask_finish", method = RequestMethod.POST)
    @ResponseBody
    public Map oaTask_finish(HttpServletRequest request, int id, @RequestParam(required = false, defaultValue = "1") Boolean isFinish) {

        oaTaskService.finish(id, isFinish);
        logger.info(addLog(LogConstants.LOG_OA, "任务完结：%s, isFinish=%s", id, isFinish));

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/oaTask_selects")
    @ResponseBody
    public Map oaTask_selects(Integer notTaskId, Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        OaTaskExample example = new OaTaskExample();
        OaTaskExample.Criteria criteria = example.createCriteria()
                .andIsDeleteEqualTo(false)
                .andStatusNotEqualTo(OaConstants.OA_TASK_STATUS_ABOLISH);
        if(notTaskId!=null){
            criteria.andIdNotEqualTo(notTaskId);
        }
        example.setOrderByClause("create_time desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }

        long count = oaTaskMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<OaTask> records = oaTaskMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
        if(null != records && records.size()>0){

            for(OaTask record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("id", record.getId() + "");
                option.put("text", record.getName());

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);

        return resultMap;
    }
}
