package controller.sc;

import controller.ScBaseController;
import domain.sc.ScMatterItem;
import domain.sc.ScMatterItemExample;
import domain.sc.ScMatterItemExample.Criteria;
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
@RequestMapping("/sc")
public class ScMatterItemController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scMatterItem:list")
    @RequestMapping("/scMatterItem")
    public String scMatterItem() {

        return "sc/scMatterItem/scMatterItem_page";
    }

    @RequiresPermissions("scMatterItem:list")
    @RequestMapping("/scMatterItem_data")
    public void scMatterItem_data(HttpServletResponse response,
                                    Integer matterId,
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

        ScMatterItemExample example = new ScMatterItemExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("fill_time desc, id desc");

        if (matterId!=null) {
            criteria.andMatterIdEqualTo(matterId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scMatterItem_export(example, response);
            return;
        }

        long count = scMatterItemMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScMatterItem> records= scMatterItemMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scMatterItem.class, scMatterItemMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scMatterItem:edit")
    @RequestMapping(value = "/scMatterItem_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterItem_au(ScMatterItem record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            scMatterItemService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_SC, "添加个人有关事项-填报记录：%s", record.getId()));
        } else {

            scMatterItemService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_SC, "更新个人有关事项-填报记录：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterItem:edit")
    @RequestMapping("/scMatterItem_au")
    public String scMatterItem_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScMatterItem scMatterItem = scMatterItemMapper.selectByPrimaryKey(id);
            modelMap.put("scMatterItem", scMatterItem);
        }
        return "sc/scMatterItem/scMatterItem_au";
    }

    @RequiresPermissions("scMatterItem:del")
    @RequestMapping(value = "/scMatterItem_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterItem_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scMatterItemService.del(id);
            logger.info(addLog( SystemConstants.LOG_SC, "删除个人有关事项-填报记录：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterItem:del")
    @RequestMapping(value = "/scMatterItem_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scMatterItemService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_SC, "批量删除个人有关事项-填报记录：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void scMatterItem_export(ScMatterItemExample example, HttpServletResponse response) {

        List<ScMatterItem> records = scMatterItemMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"事项|100","填报对象|100","实交回日期|100","封面填表日期|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScMatterItem record = records.get(i);
            String[] values = {
                record.getMatterId()+"",
                            record.getUserId()+"",
                            DateUtils.formatDate(record.getRealHandTime(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getFillTime(), DateUtils.YYYY_MM_DD)
            };
            valuesList.add(values);
        }
        String fileName = "个人有关事项-填报记录_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

   /* @RequestMapping("/scMatterItem_selects")
    @ResponseBody
    public Map scMatterItem_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScMatterItemExample example = new ScMatterItemExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        int count = scMatterItemMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<ScMatterItem> scMatterItems = scMatterItemMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != scMatterItems && scMatterItems.size()>0){

            for(ScMatterItem scMatterItem:scMatterItems){

                Select2Option option = new Select2Option();
                option.setText(scMatterItem.getName());
                option.setId(scMatterItem.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }*/
}
