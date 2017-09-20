package controller.crs;

import controller.CrsBaseController;
import domain.crs.CrsRequireRule;
import domain.crs.CrsRequireRuleExample;
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
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CrsRequireRuleController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crsRequireRule:list")
    @RequestMapping("/crsRequireRule")
    public String crsRequireRule(int postRequireId, ModelMap modelMap) {

        modelMap.put("crsPostRequire", crsPostRequireMapper.selectByPrimaryKey(postRequireId));

        return "crs/crsRequireRule/crsRequireRule_page";
    }

    @RequiresPermissions("crsRequireRule:list")
    @RequestMapping("/crsRequireRule_data")
    public void crsRequireRule_data(HttpServletResponse response,
                                    int postRequireId,
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

        CrsRequireRuleExample example = new CrsRequireRuleExample();
        CrsRequireRuleExample.Criteria criteria = example.createCriteria().andPostRequireIdEqualTo(postRequireId);
        example.setOrderByClause("sort_order desc");

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            crsRequireRule_export(example, response);
            return;
        }

        long count = crsRequireRuleMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrsRequireRule> records = crsRequireRuleMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(crsRequireRule.class, crsRequireRuleMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("crsRequireRule:edit")
    @RequestMapping(value = "/crsRequireRule_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsRequireRule_au(CrsRequireRule record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            crsRequireRuleService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加招聘岗位规则：%s", record.getId()));
        } else {

            crsRequireRuleService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新招聘岗位规则：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsRequireRule:edit")
    @RequestMapping("/crsRequireRule_au")
    public String crsRequireRule_au(Integer id, Integer postRequireId, ModelMap modelMap) {

        if (id != null) {
            CrsRequireRule crsRequireRule = crsRequireRuleMapper.selectByPrimaryKey(id);
            modelMap.put("crsRequireRule", crsRequireRule);

            postRequireId = crsRequireRule.getPostRequireId();
        }

        modelMap.put("crsPostRequire", crsPostRequireMapper.selectByPrimaryKey(postRequireId));
        return "crs/crsRequireRule/crsRequireRule_au";
    }

    @RequiresPermissions("crsRequireRule:del")
    @RequestMapping(value = "/crsRequireRule_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsRequireRule_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            //crsRequireRuleService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除招聘岗位规则：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsRequireRule:del")
    @RequestMapping(value = "/crsRequireRule_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            crsRequireRuleService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除招聘岗位规则：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsRequireRule:changeOrder")
    @RequestMapping(value = "/crsRequireRule_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsRequireRule_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        crsRequireRuleService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "招聘岗位规则调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void crsRequireRule_export(CrsRequireRuleExample example, HttpServletResponse response) {

        List<CrsRequireRule> records = crsRequireRuleMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属岗位模板", "指标名称", "备注", "排序"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CrsRequireRule record = records.get(i);
            String[] values = {
                    record.getPostRequireId() + "",
                    record.getName(),
                    record.getRemark(),
                    record.getSortOrder() + ""
            };
            valuesList.add(values);
        }
        String fileName = "招聘岗位规则_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
