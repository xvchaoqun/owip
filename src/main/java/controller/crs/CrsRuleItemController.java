package controller.crs;

import domain.crs.CrsRuleItem;
import domain.crs.CrsRuleItemExample;
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
public class CrsRuleItemController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @RequiresPermissions("crsRuleItem:list")
    @RequestMapping("/crsRuleItem_data")
    public void crsRuleItem_data(HttpServletResponse response,
                                    int requireRuleId,
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

        CrsRuleItemExample example = new CrsRuleItemExample();
        CrsRuleItemExample.Criteria criteria = example.createCriteria().andRequireRuleIdEqualTo(requireRuleId);
        example.setOrderByClause("sort_order desc");

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            crsRuleItem_export(example, response);
            return;
        }

        long count = crsRuleItemMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CrsRuleItem> records = crsRuleItemMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(crsRuleItem.class, crsRuleItemMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("crsRuleItem:edit")
    @RequestMapping(value = "/crsRuleItem_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsRuleItem_au(CrsRuleItem record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            crsRuleItemService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "添加招聘岗位规则条例：%s", record.getId()));
        } else {

            crsRuleItemService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CRS, "更新招聘岗位规则条例：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsRuleItem:edit")
    @RequestMapping("/crsRuleItem_au")
    public String crsRuleItem_au(Integer id, Integer requireRuleId, ModelMap modelMap) {

        if (id != null) {
            CrsRuleItem crsRuleItem = crsRuleItemMapper.selectByPrimaryKey(id);
            modelMap.put("crsRuleItem", crsRuleItem);

            requireRuleId = crsRuleItem.getRequireRuleId();
        }

        modelMap.put("crsRequireRule", crsRequireRuleMapper.selectByPrimaryKey(requireRuleId));
        return "crs/crsRuleItem/crsRuleItem_au";
    }

/*    @RequiresPermissions("crsRuleItem:del")
    @RequestMapping(value = "/crsRuleItem_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsRuleItem_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            //crsRuleItemService.del(id);
            logger.info(addLog(LogConstants.LOG_CRS, "删除招聘岗位规则条例：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("crsRuleItem:del")
    @RequestMapping(value = "/crsRuleItem_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            crsRuleItemService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CRS, "批量删除招聘岗位规则条例：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crsRuleItem:changeOrder")
    @RequestMapping(value = "/crsRuleItem_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crsRuleItem_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        crsRuleItemService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_CRS, "招聘岗位规则条例调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void crsRuleItem_export(CrsRuleItemExample example, HttpServletResponse response) {

        List<CrsRuleItem> records = crsRuleItemMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属岗位规则", "条例", "规格", "备注", "排序"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CrsRuleItem record = records.get(i);
            String[] values = {
                    record.getRequireRuleId() + "",
                    CrsConstants.CRS_POST_RULE_TYPE_MAP.get(record.getType()),
                    record.getVal(),
                    record.getRemark(),
                    record.getSortOrder() + ""
            };
            valuesList.add(values);
        }
        String fileName = "招聘岗位规则条例_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
