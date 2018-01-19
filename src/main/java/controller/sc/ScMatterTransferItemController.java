package controller.sc;

import controller.ScBaseController;
import domain.sc.ScMatterTransferItem;
import domain.sc.ScMatterTransferItemExample;
import domain.sc.ScMatterTransferItemExample.Criteria;
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
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sc")
public class ScMatterTransferItemController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scMatterTransferItem:list")
    @RequestMapping("/scMatterTransferItem")
    public String scMatterTransferItem() {

        return "sc/scMatterTransferItem/scMatterTransferItem_page";
    }

    @RequiresPermissions("scMatterTransferItem:list")
    @RequestMapping("/scMatterTransferItem_data")
    public void scMatterTransferItem_data(HttpServletResponse response,
                                    Integer transferId,
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

        ScMatterTransferItemExample example = new ScMatterTransferItemExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("matter_item_id asc");

        if (transferId!=null) {
            criteria.andTransferIdEqualTo(transferId);
        }

        long count = scMatterTransferItemMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScMatterTransferItem> records= scMatterTransferItemMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scMatterTransferItem.class, scMatterTransferItemMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scMatterTransferItem:edit")
    @RequestMapping(value = "/scMatterTransferItem_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterTransferItem_au(ScMatterTransferItem record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            scMatterTransferItemService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_SC, "添加个人有关事项-移交的填报记录：%s", record.getId()));
        } else {

            scMatterTransferItemService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_SC, "更新个人有关事项-移交的填报记录：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterTransferItem:edit")
    @RequestMapping("/scMatterTransferItem_au")
    public String scMatterTransferItem_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScMatterTransferItem scMatterTransferItem = scMatterTransferItemMapper.selectByPrimaryKey(id);
            modelMap.put("scMatterTransferItem", scMatterTransferItem);
        }
        return "sc/scMatterTransferItem/scMatterTransferItem_au";
    }

    @RequiresPermissions("scMatterTransferItem:del")
    @RequestMapping(value = "/scMatterTransferItem_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterTransferItem_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scMatterTransferItemService.del(id);
            logger.info(addLog( SystemConstants.LOG_SC, "删除个人有关事项-移交的填报记录：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterTransferItem:del")
    @RequestMapping(value = "/scMatterTransferItem_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scMatterTransferItemService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_SC, "批量删除个人有关事项-移交的填报记录：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

 /*

    public void scMatterTransferItem_export(ScMatterTransferItemExample example, HttpServletResponse response) {

        List<ScMatterTransferItem> records = scMatterTransferItemMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"移交记录|100","填报记录|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScMatterTransferItem record = records.get(i);
            String[] values = {
                record.getTransferId()+"",
                            record.getMatterItemId()+""
            };
            valuesList.add(values);
        }
        String fileName = "个人有关事项-移交的填报记录_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/scMatterTransferItem_selects")
    @ResponseBody
    public Map scMatterTransferItem_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScMatterTransferItemExample example = new ScMatterTransferItemExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        int count = scMatterTransferItemMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<ScMatterTransferItem> scMatterTransferItems = scMatterTransferItemMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != scMatterTransferItems && scMatterTransferItems.size()>0){

            for(ScMatterTransferItem scMatterTransferItem:scMatterTransferItems){

                Select2Option option = new Select2Option();
                option.setText(scMatterTransferItem.getName());
                option.setId(scMatterTransferItem.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }*/
}
