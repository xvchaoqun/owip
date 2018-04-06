package controller.cet;

import domain.cet.CetProject;
import domain.cet.CetProjectObjCadreViewExample;
import domain.cet.CetTraineeType;
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
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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
            @RequestParam(required = false, value = "dpTypes") Integer[] dpTypes,
            @RequestParam(required = false, value = "adminLevels") Integer[] adminLevels,
            @RequestParam(required = false, value = "postIds") Integer[] postIds,
            @RequestParam(defaultValue = "1") Integer cls,
            Integer traineeTypeId, ModelMap modelMap) {

        modelMap.put("cls", cls);

        List<CetTraineeType> cetTraineeTypes = iCetMapper.getCetTraineeTypes(projectId);
        modelMap.put("cetTraineeTypes", cetTraineeTypes);

        if (traineeTypeId == null) {
            traineeTypeId = cetTraineeTypes.get(0).getId();
        }
        modelMap.put("traineeTypeId", traineeTypeId);


        if (dpTypes != null) {
            modelMap.put("selectDpTypes", Arrays.asList(dpTypes));
        }
        if (adminLevels != null) {
            modelMap.put("selectAdminLevels", Arrays.asList(adminLevels));
        }
        if (postIds != null) {
            modelMap.put("selectPostIds", Arrays.asList(postIds));
        }

        if(userId!=null)
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "cet/cetProjectObj/cetProjectObj_page";
    }

    @RequiresPermissions("cetProjectObj:list")
    @RequestMapping("/cetProjectObj_data")
    public void cetProjectObj_data(HttpServletResponse response,
                                   @RequestParam(defaultValue = "1") Integer cls,
                                   int projectId,
                                   Integer userId,
                                   @RequestParam(required = false, value = "dpTypes") Long[] dpTypes,
                                   @RequestParam(required = false, value = "adminLevels") Integer[] adminLevels,
                                   @RequestParam(required = false, value = "postIds") Integer[] postIds,
                                   int traineeTypeId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetTraineeType cetTraineeType = cetTraineeTypeMapper.selectByPrimaryKey(traineeTypeId);
        String code = cetTraineeType.getCode();

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

                criteria.andIsQuitEqualTo(cls==2);
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
                break;
            default:
                break;
        }

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


    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping(value = "/cetProjectObj_quit", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectObj_add(boolean isQuit,
                                 @RequestParam(value = "ids[]", required = false) Integer[] ids ,
                                 HttpServletRequest request) {

        cetProjectObjService.quit(isQuit, ids);
        logger.info(addLog(SystemConstants.LOG_CET, "培训对象： %s, %s", isQuit?"退出":"重新学习",
                StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectObj:edit")
    @RequestMapping(value = "/cetProjectObj_add", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectObj_add(int projectId, int traineeTypeId,
                                 @RequestParam(value = "userIds[]", required = false) Integer[] userIds ,
                                 HttpServletRequest request) {

        cetProjectObjService.addOrUpdate(projectId, traineeTypeId, userIds);
        logger.info(addLog(SystemConstants.LOG_CET, "编辑培训对象： %s, %s, %s", projectId, traineeTypeId,
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
        cadreStatusList.add(SystemConstants.CADRE_STATUS_MIDDLE);
        TreeNode tree = cadreCommonService.getTree(new LinkedHashSet<>(cadreService.findAll().values()),
                cadreStatusList, selectIdSet, null, false, true, false);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }
    @RequiresPermissions("cetProjectObj:del")
    @RequestMapping(value = "/cetProjectObj_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectObj_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetProjectObjService.del(id);
            logger.info(addLog( SystemConstants.LOG_CET, "删除培训对象：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectObj:del")
    @RequestMapping(value = "/cetProjectObj_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetProjectObj_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetProjectObjService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_CET, "批量删除培训对象：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
