package controller.oa;

import domain.cadre.CadreView;
import domain.oa.OaTask;
import domain.oa.OaTaskFile;
import domain.oa.OaTaskView;
import domain.oa.OaTaskViewExample;
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
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.constants.OaConstants;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/oa")
public class OaTaskController extends OaBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("oaTask:list")
    @RequestMapping("/oaTask")
    public String oaTask(@RequestParam(required = false, defaultValue = "1") Byte cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        return "oa/oaTask/oaTask_page";
    }

    @RequiresPermissions("oaTask:list")
    @RequestMapping("/oaTask_data")
    public void oaTask_data(HttpServletResponse response,
                            @RequestParam(required = false, defaultValue = "1") Byte cls,
                            Byte type,
                            String name,
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

        // 只能看到自己所属角色（干部管理员、党建管理员、培训管理员）发布的任务
        Set<Byte> types = oaTaskService.getAdminTypes();
        if (types.size() > 0) {
            criteria.andTypeIn(new ArrayList<>(types));
        } else {
            criteria.andTypeIsNull();
        }

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
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

            oaTaskService.checkAuth(record.getType());
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
        Set<Byte> adminTypes = oaTaskService.getAdminTypes();
        modelMap.put("adminTypes", adminTypes);

        return "oa/oaTask/oaTask_au";
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

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
        oaTaskService.checkAuth(oaTask.getType());

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
        oaTaskService.checkAuth(oaTask.getType());

        oaTaskFileMapper.deleteByPrimaryKey(id);
        logger.info(addLog(LogConstants.LOG_OA, "删除协同办公任务附件：%s", oaTaskFile.getFileName()));

        return success(FormUtils.SUCCESS);
    }

    // 查看任务对象列表
    @RequiresPermissions("oaTask:edit")
    @RequestMapping("/oaTask/selectUsers")
    public String selectUsers(int id, ModelMap modelMap) throws IOException {

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(id);
        modelMap.put("oaTask", oaTask);

        return "oa/oaTask/selectUsers";
    }

    // 查看任务对象列表
    @RequiresPermissions("oaTask:edit")
    @RequestMapping("/oaTask/selectUsers_tree")
    @ResponseBody
    public Map selectUsers_tree(int id) throws IOException {

        Set<Integer> selectIdSet = oaTaskUserService.getTaskUserIdSet(id);
        Set<Byte> cadreStatusList = new HashSet(Arrays.asList(CadreConstants.CADRE_STATUS_MIDDLE,
                CadreConstants.CADRE_STATUS_LEADER));
        TreeNode tree = cadreCommonService.getTree(new LinkedHashSet<CadreView>(cadreService.findAll().values()),
                cadreStatusList, selectIdSet, null, false, true, false);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    // 更新任务对象列表
    @RequiresPermissions("oaTask:edit")
    @RequestMapping(value = "/oaTask/selectUsers", method = RequestMethod.POST)
    @ResponseBody
    public Map do_selectUsers(Integer id, @RequestParam(value = "userIds[]", required = false) Integer[] userIds) {

        oaTaskService.updateTaskUserIds(id, userIds);
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
    public Map oaTask_abolish(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        oaTaskService.batchAbolish(ids);
        logger.info(addLog(LogConstants.LOG_OA, "作废任务：%s", StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    // 任务完结
    @RequiresPermissions("oaTask:edit")
    @RequestMapping(value = "/oaTask_finish", method = RequestMethod.POST)
    @ResponseBody
    public Map oaTask_finish(HttpServletRequest request, int id, ModelMap modelMap) {

        oaTaskService.finish(id);
        logger.info(addLog(LogConstants.LOG_OA, "任务完结：%s", id));

        return success(FormUtils.SUCCESS);
    }
}
