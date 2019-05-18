package controller.cis;

import domain.cis.CisInspector;
import domain.cis.CisInspectorExample;
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
import sys.constants.CisConstants;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CisInspectorController extends CisBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cisInspector:list")
    @RequestMapping("/cisInspector")
    public String cisInspector(HttpServletResponse response,
                                    Integer userId,
                                    @RequestParam(required = false,
                                            defaultValue = CisConstants.CIS_INSPECTOR_STATUS_NOW + "") Byte status,
                                    ModelMap modelMap) {

        modelMap.put("status", status);
        if(userId!= null) modelMap.put("sysUser", sysUserService.findById(userId));
        return "cis/cisInspector/cisInspector_page";
    }

    @RequiresPermissions("cisInspector:list")
    @RequestMapping("/cisInspector_data")
    public void cisInspector_data(HttpServletResponse response,
                                  Integer userId,
                                  @RequestParam(required = false,
                                          defaultValue = CisConstants.CIS_INSPECTOR_STATUS_NOW + "") Byte status,
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

        CisInspectorExample example = new CisInspectorExample();
        CisInspectorExample.Criteria criteria = example.createCriteria().andStatusEqualTo(status);
        example.setOrderByClause("sort_order desc");

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            cisInspector_export(example, response);
            return;
        }

        int count = cisInspectorMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CisInspector> records = cisInspectorMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cisInspector.class, cisInspectorMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cisInspector:edit")
    @RequestMapping(value = "/cisInspector_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cisInspector_au(CisInspector record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            record.setStatus(CisConstants.CIS_INSPECTOR_STATUS_NOW);
            cisInspectorService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加干部考察组成员：%s", record.getId()));
        } else {

            cisInspectorService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新干部考察组成员：%s", record.getId()));
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
            logger.info(addLog(LogConstants.LOG_ADMIN, "撤销干部考察组成员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cisInspector:edit")
    @RequestMapping(value = "/cisInspector_reuse", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cisInspector_reuse(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids) {

        if (null != ids && ids.length > 0) {

            cisInspectorService.reuse(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "重新任用干部考察组成员：%s", StringUtils.join(ids, ",")));
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
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除干部考察组成员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cisInspector:changeOrder")
    @RequestMapping(value = "/cisInspector_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cisInspector_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cisInspectorService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "干部考察组成员调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cisInspector_export(CisInspectorExample example, HttpServletResponse response) {

        List<CisInspector> records = cisInspectorMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"考察组成员", "排序"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CisInspector record = records.get(i);
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

        searchStr = StringUtils.trimToNull(searchStr);
        if (searchStr != null) searchStr = SqlUtils.like(searchStr);

        int count = iCisMapper.countInspectorList(status, searchStr);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CisInspector> cisInspectors = iCisMapper.selectInspectorList(status, searchStr,
         new RowBounds((pageNo - 1) * pageSize, pageSize));

        List<Map> options = new ArrayList<Map>();
        if (null != cisInspectors && cisInspectors.size() > 0) {

            for (CisInspector cisInspector : cisInspectors) {

                Map option = new HashMap();
                option.put("text", cisInspector.getUser().getRealname());
                option.put("id", cisInspector.getId() + "");
                option.put("del", cisInspector.getStatus()!=CisConstants.CIS_INSPECTOR_STATUS_NOW);
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
