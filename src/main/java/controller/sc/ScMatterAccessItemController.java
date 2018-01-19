package controller.sc;

import controller.ScBaseController;
import domain.sc.ScMatterAccessItem;
import domain.sc.ScMatterAccessItemExample;
import domain.sc.ScMatterAccessItemExample.Criteria;
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
public class ScMatterAccessItemController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scMatterAccessItem:list")
    @RequestMapping("/scMatterAccessItem")
    public String scMatterAccessItem() {

        return "sc/scMatterAccessItem/scMatterAccessItem_page";
    }

    @RequiresPermissions("scMatterAccessItem:list")
    @RequestMapping("/scMatterAccessItem_data")
    public void scMatterAccessItem_data(HttpServletResponse response,
                                    Integer accessId,
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

        ScMatterAccessItemExample example = new ScMatterAccessItemExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("matter_item_id desc");

        if (accessId!=null) {
            criteria.andAccessIdEqualTo(accessId);
        }

        long count = scMatterAccessItemMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScMatterAccessItem> records= scMatterAccessItemMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scMatterAccessItem.class, scMatterAccessItemMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scMatterAccessItem:edit")
    @RequestMapping(value = "/scMatterAccessItem_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterAccessItem_au(ScMatterAccessItem record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            scMatterAccessItemService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_SC, "添加个人有关事项-调阅对象及调阅明细：%s", record.getId()));
        } else {

            scMatterAccessItemService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_SC, "更新个人有关事项-调阅对象及调阅明细：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterAccessItem:edit")
    @RequestMapping("/scMatterAccessItem_au")
    public String scMatterAccessItem_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScMatterAccessItem scMatterAccessItem = scMatterAccessItemMapper.selectByPrimaryKey(id);
            modelMap.put("scMatterAccessItem", scMatterAccessItem);
        }
        return "sc/scMatterAccessItem/scMatterAccessItem_au";
    }

    @RequiresPermissions("scMatterAccessItem:del")
    @RequestMapping(value = "/scMatterAccessItem_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterAccessItem_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scMatterAccessItemService.del(id);
            logger.info(addLog( SystemConstants.LOG_SC, "删除个人有关事项-调阅对象及调阅明细：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterAccessItem:del")
    @RequestMapping(value = "/scMatterAccessItem_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scMatterAccessItemService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_SC, "批量删除个人有关事项-调阅对象及调阅明细：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/scMatterAccessItem_selects")
    @ResponseBody
    public Map scMatterAccessItem_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScMatterAccessItemExample example = new ScMatterAccessItemExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        /*if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }*/

        long count = scMatterAccessItemMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<ScMatterAccessItem> scMatterAccessItems = scMatterAccessItemMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != scMatterAccessItems && scMatterAccessItems.size()>0){

            for(ScMatterAccessItem scMatterAccessItem:scMatterAccessItems){

                Select2Option option = new Select2Option();
                option.setText(scMatterAccessItem.getMatterItemId()+"");
                option.setId(scMatterAccessItem.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
