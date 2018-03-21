package controller.cet;

import domain.cet.CetTrain;
import domain.cet.CetTraineeCadreViewExample;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/cet")
public class CetTraineeController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrainee:list")
    @RequestMapping("/cetTrainee")
    public String cetTrainee(int trainId, Integer traineeTypeId, ModelMap modelMap) {

        List<CetTraineeType> cetTraineeTypes = iCetMapper.getCetTraineeTypes(trainId);
        modelMap.put("cetTraineeTypes", cetTraineeTypes);

        if (traineeTypeId == null) {
            traineeTypeId = cetTraineeTypes.get(0).getId();
        }
        modelMap.put("traineeTypeId", traineeTypeId);

        return "cet/cetTrainee/cetTrainee_page";
    }

    @RequiresPermissions("cetTrainee:list")
    @RequestMapping("/cetTrainee_data")
    public void cetTrainee_data(HttpServletResponse response,
                                @RequestParam(defaultValue = "1") Integer cls,
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

        CetTraineeType cetTraineeType = cetTraineeTypeMapper.selectByPrimaryKey(traineeTypeId);
        String code = cetTraineeType.getCode();

        List records = null;
        int count = 0, total = 0;
        switch (code) {
            // 中层干部、后备干部
            case "t_cadre":
            case "t_reserve":
                CetTraineeCadreViewExample example = new CetTraineeCadreViewExample();
                CetTraineeCadreViewExample.Criteria criteria = example.createCriteria().andTrainIdEqualTo(trainId)
                        .andTraineeTypeIdEqualTo(traineeTypeId);
                switch (cls){
                    case 2: // 已选课人员
                        criteria.andCourseCountGreaterThan(0);
                        break;
                    case 3: // 退班人员
                        criteria.andCourseCountEqualTo(0).andIsQuitEqualTo(true);
                        break;
                }

                if (userId != null) {
                    criteria.andUserIdEqualTo(userId);
                }

                count = (int) cetTraineeCadreViewMapper.countByExample(example);
                if ((pageNo - 1) * pageSize >= count) {

                    pageNo = Math.max(1, pageNo - 1);
                }
                records = cetTraineeCadreViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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
        //baseMixins.put(cetTrainee.class, cetTraineeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetTrainee:edit")
    @RequestMapping(value = "/cetTrainee_add", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainee_add(int trainId, int traineeTypeId,
                                 @RequestParam(value = "userIds[]", required = false) Integer[] userIds ,
                                 HttpServletRequest request) {

        cetTraineeService.addOrUpdate(trainId, traineeTypeId, userIds);
        logger.info(addLog(SystemConstants.LOG_CET, "编辑可选课人员： %s, %s, %s", trainId, traineeTypeId,
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
            // 中层干部
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
        cadreStatusList.add(SystemConstants.CADRE_STATUS_MIDDLE);
        TreeNode tree = cadreCommonService.getTree(new LinkedHashSet<>(cadreService.findAll().values()),
                cadreStatusList, selectIdSet, null, false, true, false);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("cetTrainee:del")
    @RequestMapping(value = "/cetTrainee_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainee_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetTraineeService.del(id);
            logger.info(addLog(SystemConstants.LOG_CET, "删除可选课人员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainee:del")
    @RequestMapping(value = "/cetTrainee_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetTrainee_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cetTraineeService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_CET, "批量删除可选课人员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
