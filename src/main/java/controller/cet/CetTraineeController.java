package controller.cet;

import domain.cet.*;
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
import sys.constants.LogConstants;
import sys.tags.CmTag;
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
@RequestMapping("/cet")
public class CetTraineeController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrainee:list")
    @RequestMapping("/cetTrainee_detail")
    public String cetTrainee_detail(int userId, ModelMap modelMap) {

        modelMap.put("sysUser", CmTag.getUserById(userId));

        return "cet/cetTrainee/cetTrainee_detail";
    }

    @RequiresPermissions("cetTrainee:list")
    @RequestMapping("/cetTrainee")
    public String cetTrainee(int trainId, Integer traineeTypeId,
                             Integer userId,
                             ModelMap modelMap) {

        CetProject cetProject = iCetMapper.getCetProject(trainId);
        Integer projectId = cetProject.getId();
        List<CetTraineeType> cetTraineeTypes = iCetMapper.getCetTraineeTypes(projectId);
        modelMap.put("cetTraineeTypes", cetTraineeTypes);

        Map<Integer, Integer> typeCountMap = new HashMap<>();
        List<Map> typeCountList = iCetMapper.projectObj_typeCount(projectId, false);
        for (Map resultMap : typeCountList) {
            int _traineeTypeId = ((Long) resultMap.get("trainee_type_id")).intValue();
            int num = ((Long) resultMap.get("num")).intValue();
            typeCountMap.put(_traineeTypeId, num);
        }
        modelMap.put("typeCountMap", typeCountMap);

        if (traineeTypeId == null) {
            traineeTypeId = cetTraineeTypes.get(0).getId();
        }
        modelMap.put("traineeTypeId", traineeTypeId);
        CetTraineeType cetTraineeType = cetTraineeTypeMapper.selectByPrimaryKey(traineeTypeId);
        modelMap.put("cetTraineeType", cetTraineeType);

        if(userId!=null) {
            modelMap.put("sysUser", CmTag.getUserById(userId));
        }

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        modelMap.put("cetTrain", cetTrain);

        Map<Integer, Object> yearPeriodMap = cetTrainService.traineeYearPeriodMap(trainId);
        modelMap.put("yearPeriodMap", yearPeriodMap);

        // 培训班下的课程总数
        CetTrainCourseExample example = new CetTrainCourseExample();
        example.createCriteria().andTrainIdEqualTo(trainId);
        modelMap.put("courseCount", cetTrainCourseMapper.countByExample(example));

        return "cet/cetTrainee/cetTrainee_page";
    }

    @RequiresPermissions("cetTrainee:list")
    @RequestMapping("/cetTrainee_data")
    public void cetTrainee_data(HttpServletResponse response,
                                int trainId,
                                int traineeTypeId,
                                Integer userId,
                                @RequestParam(required = false, defaultValue = "0") int export,
                                @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetTraineeViewExample example = new CetTraineeViewExample();
        CetTraineeViewExample.Criteria criteria =
                example.createCriteria().andTrainIdEqualTo(trainId)
                        .andTraineeTypeIdEqualTo(traineeTypeId);
        example.setOrderByClause("id asc");

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        int count = (int) cetTraineeViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTraineeView> records = cetTraineeViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        int total = commonList.pageNum;

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", total);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetTrainee.class, cetTraineeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    /*@RequiresPermissions("cetTrainee:edit")
    @RequestMapping(value = "/cetTrainee_add", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainee_add(int trainId, int traineeTypeId,
                                 @RequestParam(value = "userIds[]", required = false) Integer[] userIds ,
                                 HttpServletRequest request) {

        cetTraineeService.addOrUpdate(trainId, traineeTypeId, userIds);
        logger.info(addLog(LogConstants.LOG_CET, "编辑可选课人员： %s, %s, %s", trainId, traineeTypeId,
                StringUtils.join(userIds, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainee:edit")
    @RequestMapping("/cetTrainee_add")
    public String cetTrainee_add(int trainId, int traineeTypeId, ModelMap modelMap) {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        modelMap.put("cetTrain", cetTrain);
        CetTraineeType cetTraineeType = cetTraineeTypeMapper.selectByPrimaryKey(traineeTypeId);
        String code = cetTraineeType.getCode();
        switch (code) {
            // 干部
            case "t_cadre":
                return "cet/cetTrainee/cetTrainee_selectCadres";
        }

        return null;
    }

    @RequiresPermissions("cetTrainee:edit")
    @RequestMapping("/cetTrainee_selectCadres_tree")
    @ResponseBody
    public Map cetTrainee_selectCadres_tree(int trainId) throws IOException {

        Set<Integer> selectIdSet = cetTraineeService.getSelectedTraineeUserIdSet(trainId);

        Set<Byte> cadreStatusList = new HashSet<>();
        cadreStatusList.add(CadreConstants.CADRE_STATUS_MIDDLE);
        TreeNode tree = cadreCommonService.getTree(new LinkedHashSet<>(cadreService.findAll().values()),
                cadreStatusList, selectIdSet, null, false, true, false);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }*/

    @RequiresPermissions("cetTrainee:del")
    @RequestMapping(value = "/cetTrainee_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainee_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetTraineeService.del(id);
            logger.info(addLog(LogConstants.LOG_CET, "删除可选课人员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainee:del")
    @RequestMapping(value = "/cetTrainee_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetTrainee_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cetTraineeService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除可选课人员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
