package controller.cet.user;

import controller.cet.CetBaseController;
import controller.global.OpException;
import domain.cet.CetUnitProject;
import domain.cet.CetUnitProjectExample;
import domain.cet.CetUnitTrain;
import domain.cet.CetUnitTrainExample;
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
import shiro.ShiroHelper;
import sys.constants.CetConstants;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/user/cet")
public class UserCetUnitTrainController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("userCetUnitTrain:list")
    @RequestMapping("/cetUnitTrain")
    public String cetUnitTrain(@RequestParam(required = false, defaultValue = "5") Byte cls,
                               ModelMap modelMap) {

        modelMap.put("cls", cls);
        return "cet/user/cetUnitTrain_page";
    }

    @RequiresPermissions("userCetUnitTrain:list")
    @RequestMapping("/cetUnitTrain_data")
    @ResponseBody
    public void cetUnitTrain_data(HttpServletResponse response,
                                  @RequestParam(required = false, defaultValue = "5") Byte cls,
                                   Integer pageSize, Integer pageNo) throws IOException {

        int userId = ShiroHelper.getCurrentUserId();

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetUnitTrainExample example = new CetUnitTrainExample();
        CetUnitTrainExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        
        example.setOrderByClause("id desc");

        if (cls == 5){
            criteria.andStatusEqualTo(CetConstants.CET_UNITTRAIN_RERECORD_PASS);
        }else if (cls == 6){
            List<Byte> statusList = new ArrayList<>();
            statusList.add(CetConstants.CET_UNITTRAIN_RERECORD_UNIT);
            statusList.add(CetConstants.CET_UNITTRAIN_RERECORD_PARTY);
            statusList.add(CetConstants.CET_UNITTRAIN_RERECORD_SAVE);
            criteria.andStatusIn(statusList);
        }

        long count = cetUnitTrainMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetUnitTrain> records = cetUnitTrainMapper.selectByExampleWithRowbounds(example,
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

    @RequiresPermissions("userCetUnitTrain:edit")
    @RequestMapping("/cetUnitTrain_list")
    public String cetUnitTrain_list(String type, Integer userId, ModelMap modelMap){

        userId = ShiroHelper.getCurrentUserId();
        modelMap.put("userId", userId);

        CetUnitTrainExample example = new CetUnitTrainExample();
        example.createCriteria().andUserIdEqualTo(userId).andStatusNotEqualTo(CetConstants.CET_UNITTRAIN_RERECORD_PASS);
        List<CetUnitTrain> cetUnitTrains = cetUnitTrainMapper.selectByExample(example);
        modelMap.put("cetUnitTrains", cetUnitTrains);

        return "/cet/user/cetUnitTrain_list";
    }

    @RequiresPermissions("userCetUnitTrain:edit")
    @RequestMapping(value = "/cetUnitTrain_list", method = RequestMethod.POST)
    @ResponseBody
    public Map do_userCetUnitTrain_list(Integer userId,
                                    Integer projectId){

        CetUnitTrain record = new CetUnitTrain();
        if (userId != null && projectId != null) {
            if (cetUnitTrainService.idDuplicate(null, userId, projectId)) {
                return failed("添加重复。");
            }
            record.setUserId(userId);
            record.setProjectId(projectId);
            record.setAddTime(new Date());
            record.setAddUserId(userId);
            record.setStatus(CetConstants.CET_UNITTRAIN_RERECORD_UNIT);
            CetUnitProject project = cetUnitProjectMapper.selectByPrimaryKey(projectId);
            record.setPeriod(project.getPeriod());
            cetUnitTrainService.reRecord(record);
        }else {
            throw new OpException("补录失败");
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("userCetUnitTrain:edit")
    @RequestMapping("/cetUnitTrain_au")
    public String cetUnitTrain_au(String type,
                                  Integer userId,
                                  String projectName,
                                  HttpServletResponse response,
                                  Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = 8;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        userId = ShiroHelper.getCurrentUserId();

        CetUnitTrainExample example = new CetUnitTrainExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<CetUnitTrain> cetUnitTrains = cetUnitTrainMapper.selectByExample(example);
        List<CetUnitProject> projects = new ArrayList<>();
        List<Integer> projectIds = new ArrayList<>();
        for (CetUnitTrain cetUnitTrain : cetUnitTrains) {
            projects.add(cetUnitTrain.getProject());
            projectIds.add(cetUnitTrain.getProjectId());
        }

        List<Byte> statuss = new ArrayList<>();
        statuss.add(CetConstants.CET_UNIT_PROJECT_STATUS_DELETE);
        statuss.add(CetConstants.CET_UNIT_PROJECT_STATUS_UNPASS);

        CetUnitProjectExample projectExample = new CetUnitProjectExample();
        CetUnitProjectExample.Criteria proCritrria = projectExample.createCriteria().andStatusNotIn(statuss);

        if (projectIds.size() > 0) {
            if (!StringUtils.equalsIgnoreCase(type, "edit")) {

                proCritrria.andIdIn(projectIds);
            } else {
                proCritrria.andIdNotIn(projectIds);
            }
        }

        if (StringUtils.isNotBlank(projectName)){
            proCritrria.andProjectNameLike(SqlUtils.trimLike(projectName));
        }

        int count = (int) cetUnitProjectMapper.countByExample(projectExample);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<CetUnitProject> cetUnitProjects = cetUnitProjectMapper.selectByExampleWithRowbounds(projectExample, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("cetUnitProjects", cetUnitProjects);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        modelMap.put("commonList", commonList);

        return "cet/user/cetUnitTrain_au";
    }

    @RequiresPermissions("userCetUnitTrain:edit")
    @RequestMapping(value = "/cetUnitTrain_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUnitTrain_batchDel( @RequestParam(value = "ids[]") Integer[] ids,
                                         HttpServletRequest request) {

        if (null != ids && ids.length > 0) {
            cetUnitTrainService.userBatchDel(ids);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除补录信息：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
