package controller.cr;

import domain.cr.CrRequireRule;
import domain.cr.CrRequireRuleExample;
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
public class CrRequireRuleController extends CrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crRequire:list")
    @RequestMapping("/crRequireRule")
    public String crRequireRule(int requireId, ModelMap modelMap) {

        modelMap.put("crRequire", crRequireMapper.selectByPrimaryKey(requireId));

        return "cr/crRequireRule/crRequireRule_page";
    }

    @RequiresPermissions("crRequire:list")
    @RequestMapping("/crRequireRule_data")
    public void crRequireRule_data(HttpServletResponse response,
                                    int requireId,
                                    @RequestParam(required = false, defaultValue = "0") int export,
                                    Integer[] ids, // 导出的记录
                                    Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CrRequireRuleExample example = new CrRequireRuleExample();
        CrRequireRuleExample.Criteria criteria = example.createCriteria().andRequireIdEqualTo(requireId);
        example.setOrderByClause("sort_order desc");

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            crRequireRule_export(example, response);
            return;
        }

        long count = crRequireRuleMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrRequireRule> records = crRequireRuleMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(crRequireRule.class, crRequireRuleMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("crRequire:edit")
    @RequestMapping(value = "/crRequireRule_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crRequireRule_au(CrRequireRule record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            crRequireRuleService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "添加招聘规则：%s", record.getId()));
        } else {

            crRequireRuleService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "更新招聘规则：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crRequire:edit")
    @RequestMapping("/crRequireRule_au")
    public String crRequireRule_au(Integer id, Integer requireId, ModelMap modelMap) {

        if (id != null) {
            CrRequireRule crRequireRule = crRequireRuleMapper.selectByPrimaryKey(id);
            modelMap.put("crRequireRule", crRequireRule);

            requireId = crRequireRule.getRequireId();
        }

        modelMap.put("crRequire", crRequireMapper.selectByPrimaryKey(requireId));
        return "cr/crRequireRule/crRequireRule_au";
    }

    @RequiresPermissions("crRequire:edit")
    @RequestMapping(value = "/crRequireRule_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crRequireRule_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            //crRequireRuleService.del(id);
            logger.info(addLog(LogConstants.LOG_CRS, "删除招聘规则：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crRequire:edit")
    @RequestMapping(value = "/crRequireRule_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            crRequireRuleService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CRS, "批量删除招聘规则：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crRequire:edit")
    @RequestMapping(value = "/crRequireRule_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crRequireRule_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        crRequireRuleService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_CRS, "招聘规则调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void crRequireRule_export(CrRequireRuleExample example, HttpServletResponse response) {

        List<CrRequireRule> records = crRequireRuleMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属资格模板", "指标名称", "备注", "排序"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CrRequireRule record = records.get(i);
            String[] values = {
                    record.getRequireId() + "",
                    record.getName(),
                    record.getRemark(),
                    record.getSortOrder() + ""
            };
            valuesList.add(values);
        }
        String fileName = "招聘资格模板规则_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
