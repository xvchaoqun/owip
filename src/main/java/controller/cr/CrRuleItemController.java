package controller.cr;

import domain.cr.CrRuleItem;
import domain.cr.CrRuleItemExample;
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
import sys.constants.CrsConstants;
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
public class CrRuleItemController extends CrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crRequire:list")
    @RequestMapping("/crRuleItem_data")
    public void crRuleItem_data(HttpServletResponse response,
                                    int requireRuleId,
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

        CrRuleItemExample example = new CrRuleItemExample();
        CrRuleItemExample.Criteria criteria = example.createCriteria().andRequireRuleIdEqualTo(requireRuleId);
        example.setOrderByClause("sort_order desc");

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            crRuleItem_export(example, response);
            return;
        }

        long count = crRuleItemMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrRuleItem> records = crRuleItemMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(crRuleItem.class, crRuleItemMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("crRequire:edit")
    @RequestMapping(value = "/crRuleItem_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crRuleItem_au(CrRuleItem record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            crRuleItemService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "添加招聘规则条例：%s", record.getId()));
        } else {

            crRuleItemService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "更新招聘规则条例：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crRequire:edit")
    @RequestMapping("/crRuleItem_au")
    public String crRuleItem_au(Integer id, Integer requireRuleId, ModelMap modelMap) {

        if (id != null) {
            CrRuleItem crRuleItem = crRuleItemMapper.selectByPrimaryKey(id);
            modelMap.put("crRuleItem", crRuleItem);

            requireRuleId = crRuleItem.getRequireRuleId();
        }

        modelMap.put("crRequireRule", crRequireRuleMapper.selectByPrimaryKey(requireRuleId));
        return "cr/crRuleItem/crRuleItem_au";
    }

/*    @RequiresPermissions("crRequire:del")
    @RequestMapping(value = "/crRuleItem_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crRuleItem_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            //crRuleItemService.del(id);
            logger.info(addLog(LogConstants.LOG_CRS, "删除招聘规则条例：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("crRequire:edit")
    @RequestMapping(value = "/crRuleItem_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            crRuleItemService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CRS, "批量删除招聘规则条例：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crRequire:edit")
    @RequestMapping(value = "/crRuleItem_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crRuleItem_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        crRuleItemService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_CRS, "招聘规则条例调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void crRuleItem_export(CrRuleItemExample example, HttpServletResponse response) {

        List<CrRuleItem> records = crRuleItemMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属规则", "条例", "规格", "备注", "排序"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CrRuleItem record = records.get(i);
            String[] values = {
                    record.getRequireRuleId() + "",
                    CrsConstants.CRS_POST_RULE_TYPE_MAP.get(record.getType()),
                    record.getVal(),
                    record.getRemark(),
                    record.getSortOrder() + ""
            };
            valuesList.add(values);
        }
        String fileName = "招聘规则条例_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
