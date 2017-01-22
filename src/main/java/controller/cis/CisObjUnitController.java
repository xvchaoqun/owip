package controller.cis;

import controller.BaseController;
import domain.cis.CisInspectorView;
import domain.cis.CisObjUnit;
import domain.cis.CisObjUnitExample;
import domain.cis.CisObjUnitExample.Criteria;
import domain.unit.Unit;
import interceptor.OrderParam;
import interceptor.SortParam;
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
import java.util.*;

@Controller
public class CisObjUnitController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cisObjUnit:list")
    @RequestMapping("/cisObjUnits_tree")
    @ResponseBody
    public Map cisObjUnits_tree(int objId) throws IOException {

        List<Unit> units = cisInspectObjService.getUnits(objId);
        Set<Integer> selectIdSet = new HashSet<>();
        for (Unit unit : units) {
            selectIdSet.add(unit.getId());
        }
        TreeNode tree = cisObjUnitService.getTree(selectIdSet);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("cisObjUnit:list")
    @RequestMapping("/cisObjUnit")
    public String cisObjUnit() {

        return "index";
    }

    @RequiresPermissions("cisObjUnit:list")
    @RequestMapping("/cisObjUnit_page")
    public String cisObjUnit_page(HttpServletResponse response, ModelMap modelMap) {

        return "cis/cisObjUnit/cisObjUnit_page";
    }

    @RequiresPermissions("cisObjUnit:list")
    @RequestMapping("/cisObjUnit_data")
    public void cisObjUnit_data(HttpServletResponse response,
                                Integer objId,
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

        CisObjUnitExample example = new CisObjUnitExample();
        Criteria criteria = example.createCriteria();
        //example.setOrderByClause(String.format("%s %s", sort, order));

        if (objId != null) {
            criteria.andObjIdEqualTo(objId);
        }

        int count = cisObjUnitMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CisObjUnit> cisObjUnits = cisObjUnitMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", cisObjUnits);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(cisObjUnit.class, cisObjUnitMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("cisObjUnit:edit")
    @RequestMapping(value = "/cisObjUnit_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cisObjUnit_au(CisObjUnit record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cisObjUnitService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加考察单位：%s", record.getId()));
        } else {

            cisObjUnitService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新考察单位：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cisObjUnit:edit")
    @RequestMapping("/cisObjUnit_au")
    public String cisObjUnit_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CisObjUnit cisObjUnit = cisObjUnitMapper.selectByPrimaryKey(id);
            modelMap.put("cisObjUnit", cisObjUnit);
        }
        return "cis/cisObjUnit/cisObjUnit_au";
    }

    @RequiresPermissions("cisObjUnit:del")
    @RequestMapping(value = "/cisObjUnit_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cisObjUnit_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cisObjUnitService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除考察单位：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cisObjUnit:del")
    @RequestMapping(value = "/cisObjUnit_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cisObjUnitService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除考察单位：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
