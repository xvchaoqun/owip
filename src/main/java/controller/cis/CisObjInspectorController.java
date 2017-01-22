package controller.cis;

import controller.BaseController;
import domain.cis.CisInspectorView;
import domain.cis.CisObjInspector;
import domain.cis.CisObjInspectorExample;
import domain.cis.CisObjInspectorExample.Criteria;
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
public class CisObjInspectorController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cisObjInspector:list")
    @RequestMapping("/cisObjInspectors_tree")
    @ResponseBody
    public Map cisObjInspectors_tree(int objId) throws IOException {

        List<CisInspectorView> inspectors = cisInspectObjService.getInspectors(objId);
        Set<Integer> selectIdSet = new HashSet<>();
        for (CisInspectorView inspector : inspectors) {
            selectIdSet.add(inspector.getId());
        }
        TreeNode tree = cisObjInspectorService.getTree(selectIdSet);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("cisObjInspector:list")
    @RequestMapping("/cisObjInspectors")
    public String select_Inspectors() throws IOException {

        return "cis/cisObjInspector/cisObjInspectors";
    }

    @RequiresPermissions("cisObjInspector:edit")
    @RequestMapping(value = "/cisObjInspectors", method = RequestMethod.POST)
    @ResponseBody
    public Map do_select_Inspectors(Integer objId, @RequestParam(value = "inspectorIds[]", required = false) Integer[] inspectorIds) {

        cisObjInspectorService.updateInspectIds(objId, inspectorIds);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cisObjInspector:list")
    @RequestMapping("/cisObjInspector")
    public String cisObjInspector() {

        return "index";
    }

    @RequiresPermissions("cisObjInspector:list")
    @RequestMapping("/cisObjInspector_page")
    public String cisObjInspector_page(HttpServletResponse response, ModelMap modelMap) {

        return "cis/cisObjInspector/cisObjInspector_page";
    }

    @RequiresPermissions("cisObjInspector:list")
    @RequestMapping("/cisObjInspector_data")
    public void cisObjInspector_data(HttpServletResponse response,
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

        CisObjInspectorExample example = new CisObjInspectorExample();
        Criteria criteria = example.createCriteria();
        //example.setOrderByClause(String.format("%s %s", sort, order));

        if (objId != null) {
            criteria.andObjIdEqualTo(objId);
        }

        int count = cisObjInspectorMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CisObjInspector> cisObjInspectors = cisObjInspectorMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", cisObjInspectors);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(cisObjInspector.class, cisObjInspectorMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("cisObjInspector:edit")
    @RequestMapping(value = "/cisObjInspector_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cisObjInspector_au(CisObjInspector record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cisObjInspectorService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部考察材料中的考察组成员：%s", record.getId()));
        } else {

            cisObjInspectorService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部考察材料中的考察组成员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cisObjInspector:edit")
    @RequestMapping("/cisObjInspector_au")
    public String cisObjInspector_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CisObjInspector cisObjInspector = cisObjInspectorMapper.selectByPrimaryKey(id);
            modelMap.put("cisObjInspector", cisObjInspector);
        }
        return "cis/cisObjInspector/cisObjInspector_au";
    }

    @RequiresPermissions("cisObjInspector:del")
    @RequestMapping(value = "/cisObjInspector_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cisObjInspector_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cisObjInspectorService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除干部考察材料中的考察组成员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cisObjInspector:del")
    @RequestMapping(value = "/cisObjInspector_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cisObjInspectorService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部考察材料中的考察组成员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
