package controller.cet;

import domain.cet.CetDiscuss;
import domain.cet.CetDiscussExample;
import domain.cet.CetDiscussExample.Criteria;
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
@RequestMapping("/cet")
public class CetDiscussController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetDiscuss:list")
    @RequestMapping("/cetDiscuss")
    public String cetDiscuss() {

        return "cet/cetDiscuss/cetDiscuss_page";
    }

    @RequiresPermissions("cetDiscuss:list")
    @RequestMapping("/cetDiscuss_data")
    public void cetDiscuss_data(HttpServletResponse response,
                                    Integer planId,
                                    String name,
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

        CetDiscussExample example = new CetDiscussExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (planId!=null) {
            criteria.andPlanIdEqualTo(planId);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetDiscuss_export(example, response);
            return;
        }

        long count = cetDiscussMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetDiscuss> records= cetDiscussMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetDiscuss.class, cetDiscussMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetDiscuss:edit")
    @RequestMapping(value = "/cetDiscuss_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetDiscuss_au(CetDiscuss record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cetDiscussService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "添加分组研讨：%s", record.getId()));
        } else {

            cetDiscussService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "更新分组研讨：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetDiscuss:edit")
    @RequestMapping("/cetDiscuss_au")
    public String cetDiscuss_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetDiscuss cetDiscuss = cetDiscussMapper.selectByPrimaryKey(id);
            modelMap.put("cetDiscuss", cetDiscuss);
        }
        return "cet/cetDiscuss/cetDiscuss_au";
    }

    @RequiresPermissions("cetDiscuss:del")
    @RequestMapping(value = "/cetDiscuss_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetDiscuss_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetDiscussService.del(id);
            logger.info(addLog( SystemConstants.LOG_CET, "删除分组研讨：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetDiscuss:del")
    @RequestMapping(value = "/cetDiscuss_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetDiscuss_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetDiscussService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_CET, "批量删除分组研讨：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetDiscuss:changeOrder")
    @RequestMapping(value = "/cetDiscuss_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetDiscuss_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetDiscussService.changeOrder(id, addNum);
        logger.info(addLog( SystemConstants.LOG_CET, "分组研讨调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetDiscuss_export(CetDiscussExample example, HttpServletResponse response) {

        List<CetDiscuss> records = cetDiscussMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属培训方案|100","开始日期|100","结束日期|100","研讨会名称|100","负责单位类型|100","学时|100","排序|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetDiscuss record = records.get(i);
            String[] values = {
                record.getPlanId()+"",
                            DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD),
                            record.getName(),
                            record.getUnitType()+"",
                            record.getPeriod()+"",
                            record.getSortOrder()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "分组研讨_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cetDiscuss_selects")
    @ResponseBody
    public Map cetDiscuss_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetDiscussExample example = new CetDiscussExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        long count = cetDiscussMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CetDiscuss> cetDiscusss = cetDiscussMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != cetDiscusss && cetDiscusss.size()>0){

            for(CetDiscuss cetDiscuss:cetDiscusss){

                Map<String, Object> option = new HashMap<>();
                option.put("text", cetDiscuss.getName());
                option.put("id", cetDiscuss.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
