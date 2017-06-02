package controller.cis;

import controller.BaseController;
import domain.cis.CisInspector;
import domain.cis.CisInspectorView;
import domain.cis.CisInspectorViewExample;
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
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CisInspectorController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cisInspector:list")
    @RequestMapping("/cisInspector")
    public String cisInspector(HttpServletResponse response,
                                    @RequestParam(required = false,
                                            defaultValue = SystemConstants.CIS_INSPECTOR_STATUS_NOW + "") Byte status,
                                    ModelMap modelMap) {

        modelMap.put("status", status);

        return "cis/cisInspector/cisInspector_page";
    }

    @RequiresPermissions("cisInspector:list")
    @RequestMapping("/cisInspector_data")
    public void cisInspector_data(HttpServletResponse response,
                                  @SortParam(required = false, defaultValue = "sort_order", tableName = "cis_inspector") String sort,
                                  @OrderParam(required = false, defaultValue = "desc") String order,
                                  Integer userId,
                                  @RequestParam(required = false,
                                          defaultValue = SystemConstants.CIS_INSPECTOR_STATUS_NOW + "") Byte status,
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

        CisInspectorViewExample example = new CisInspectorViewExample();
        CisInspectorViewExample.Criteria criteria = example.createCriteria().andStatusEqualTo(status);
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            cisInspector_export(example, response);
            return;
        }

        int count = cisInspectorViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CisInspectorView> records = cisInspectorViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(cisInspector.class, cisInspectorMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("cisInspector:edit")
    @RequestMapping(value = "/cisInspector_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cisInspector_au(CisInspector record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            record.setStatus(SystemConstants.CIS_INSPECTOR_STATUS_NOW);
            cisInspectorService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部考察组成员：%s", record.getId()));
        } else {

            cisInspectorService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部考察组成员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cisInspector:edit")
    @RequestMapping("/cisInspector_au")
    public String cisInspector_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CisInspector cisInspector = cisInspectorMapper.selectByPrimaryKey(id);
            modelMap.put("cisInspector", cisInspector);
        }
        return "cis/cisInspector/cisInspector_au";
    }

    @RequiresPermissions("cisInspector:edit")
    @RequestMapping(value = "/cisInspector_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cisInspector_abolish(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids) {

        if (null != ids && ids.length > 0) {

            cisInspectorService.abolish(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "撤销干部考察组成员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cisInspector:edit")
    @RequestMapping(value = "/cisInspector_reuse", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cisInspector_reuse(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids) {

        if (null != ids && ids.length > 0) {

            cisInspectorService.reuse(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "重新任用干部考察组成员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 逻辑删除
    @RequiresPermissions("cisInspector:del")
    @RequestMapping(value = "/cisInspector_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {

            cisInspectorService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部考察组成员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cisInspector:changeOrder")
    @RequestMapping(value = "/cisInspector_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cisInspector_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cisInspectorService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "干部考察组成员调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cisInspector_export(CisInspectorViewExample example, HttpServletResponse response) {

        List<CisInspectorView> records = cisInspectorViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"考察组成员", "排序"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CisInspectorView record = records.get(i);
            String[] values = {
                    record.getUserId() + "",
                    record.getSortOrder() + ""
            };
            valuesList.add(values);
        }
        String fileName = "干部考察组成员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    // <cisInspector.id, realname>
    @RequestMapping("/cisInspector_selects")
    @ResponseBody
    public Map cisInspector_selects(Byte status, Integer pageSize, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CisInspectorViewExample example = new CisInspectorViewExample();
        example.setOrderByClause("sort_order desc");

        if (StringUtils.isNotBlank(searchStr)) {
            CisInspectorViewExample.Criteria criteria = example.or().andUsernameLike("%" + searchStr + "%");
            CisInspectorViewExample.Criteria criteria1 = example.or().andCodeLike("%" + searchStr + "%");
            CisInspectorViewExample.Criteria criteria2 = example.or().andRealnameLike("%" + searchStr + "%");
            if (status != null) {
                criteria.andStatusEqualTo(status);
                criteria1.andStatusEqualTo(status);
                criteria2.andStatusEqualTo(status);
            }
        }else if (status != null) {
            example.createCriteria().andStatusEqualTo(status);
        }

        int count = cisInspectorViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CisInspectorView> cisInspectors = cisInspectorViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if (null != cisInspectors && cisInspectors.size() > 0) {

            for (CisInspectorView cisInspector : cisInspectors) {

                Select2Option option = new Select2Option();
                option.setText(cisInspector.getRealname());
                option.setId(cisInspector.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
