package controller.sc;

import controller.ScBaseController;
import domain.sc.ScMatterCheckItem;
import domain.sc.ScMatterCheckItemExample;
import domain.sc.ScMatterCheckItemExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
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
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sc")
public class ScMatterCheckItemController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scMatterCheckItem:list")
    @RequestMapping("/scMatterCheckItem")
    public String scMatterCheckItem() {

        return "sc/scMatterCheckItem/scMatterCheckItem_page";
    }

    @RequiresPermissions("scMatterCheckItem:list")
    @RequestMapping("/scMatterCheckItem_data")
    public void scMatterCheckItem_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "sc_matter_check_item") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer checkId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScMatterCheckItemExample example = new ScMatterCheckItemExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (checkId!=null) {
            criteria.andCheckIdEqualTo(checkId);
        }

        long count = scMatterCheckItemMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScMatterCheckItem> records= scMatterCheckItemMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scMatterCheckItem.class, scMatterCheckItemMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scMatterCheckItem:edit")
    @RequestMapping(value = "/scMatterCheckItem_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterCheckItem_au(ScMatterCheckItem record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            scMatterCheckItemService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_SC, "添加个人有关事项-核查对象：%s", record.getId()));
        } else {

            scMatterCheckItemService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_SC, "更新个人有关事项-核查对象：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterCheckItem:edit")
    @RequestMapping("/scMatterCheckItem_au")
    public String scMatterCheckItem_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScMatterCheckItem scMatterCheckItem = scMatterCheckItemMapper.selectByPrimaryKey(id);
            modelMap.put("scMatterCheckItem", scMatterCheckItem);
        }
        return "sc/scMatterCheckItem/scMatterCheckItem_au";
    }

    @RequiresPermissions("scMatterCheckItem:del")
    @RequestMapping(value = "/scMatterCheckItem_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterCheckItem_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scMatterCheckItemService.del(id);
            logger.info(addLog( SystemConstants.LOG_SC, "删除个人有关事项-核查对象：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterCheckItem:del")
    @RequestMapping(value = "/scMatterCheckItem_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scMatterCheckItemService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_SC, "批量删除个人有关事项-核查对象：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    /*public void scMatterCheckItem_export(ScMatterCheckItemExample example, HttpServletResponse response) {

        List<ScMatterCheckItem> records = scMatterCheckItemMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"核查|100","核查对象|100","比对日期|100","干部监督机构查核结果|100","本人说明材料|100","认定结果|100","认定日期|100","干部管理机构处理意见|100","核查情况表|100","组织处理方式|100","组织处理日期|100","组织处理记录|100","组织处理影响期|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScMatterCheckItem record = records.get(i);
            String[] values = {
                record.getCheckId()+"",
                            record.getUserId()+"",
                            DateUtils.formatDate(record.getCompareDate(), DateUtils.YYYY_MM_DD),
                            record.getResultType(),
                            record.getSelfFile(),
                            record.getConfirmType(),
                            DateUtils.formatDate(record.getConfirmDate(), DateUtils.YYYY_MM_DD),
                            record.getHandleType(),
                            record.getCheckFile(),
                            record.getOwHandleType(),
                            DateUtils.formatDate(record.getOwHandleDate(), DateUtils.YYYY_MM_DD),
                            record.getOwHandleFile(),
                            DateUtils.formatDate(record.getOwAffectDate(), DateUtils.YYYY_MM_DD),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "个人有关事项-核查对象_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }*/

    @RequestMapping("/scMatterCheckItem_selects")
    @ResponseBody
    public Map scMatterCheckItem_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScMatterCheckItemExample example = new ScMatterCheckItemExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        /*if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }
*/
        long count = scMatterCheckItemMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<ScMatterCheckItem> scMatterCheckItems = scMatterCheckItemMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != scMatterCheckItems && scMatterCheckItems.size()>0){

            for(ScMatterCheckItem scMatterCheckItem:scMatterCheckItems){

                Select2Option option = new Select2Option();
                option.setText(scMatterCheckItem.getRemark());
                option.setId(scMatterCheckItem.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
