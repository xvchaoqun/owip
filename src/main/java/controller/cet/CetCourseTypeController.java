package controller.cet;

import domain.cet.CetCourseType;
import domain.cet.CetCourseTypeExample;
import domain.cet.CetCourseTypeExample.Criteria;
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
public class CetCourseTypeController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetCourseType:list")
    @RequestMapping("/cetCourseType")
    public String cetCourseType() {

        return "cet/cetCourseType/cetCourseType_page";
    }

    @RequiresPermissions("cetCourseType:list")
    @RequestMapping("/cetCourseType_data")
    public void cetCourseType_data(HttpServletResponse response,
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

        CetCourseTypeExample example = new CetCourseTypeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order asc");

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetCourseType_export(example, response);
            return;
        }

        long count = cetCourseTypeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetCourseType> records= cetCourseTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetCourseType.class, cetCourseTypeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetCourseType:edit")
    @RequestMapping(value = "/cetCourseType_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetCourseType_au(CetCourseType record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cetCourseTypeService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "添加专题分类：%s", record.getId()));
        } else {

            cetCourseTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "更新专题分类：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetCourseType:edit")
    @RequestMapping("/cetCourseType_au")
    public String cetCourseType_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetCourseType cetCourseType = cetCourseTypeMapper.selectByPrimaryKey(id);
            modelMap.put("cetCourseType", cetCourseType);
        }
        return "cet/cetCourseType/cetCourseType_au";
    }

    @RequiresPermissions("cetCourseType:del")
    @RequestMapping(value = "/cetCourseType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetCourseType_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetCourseTypeService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_CET, "批量删除专题分类：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetCourseType:changeOrder")
    @RequestMapping(value = "/cetCourseType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetCourseType_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetCourseTypeService.changeOrder(id, addNum);
        logger.info(addLog( SystemConstants.LOG_CET, "专题分类调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetCourseType_export(CetCourseTypeExample example, HttpServletResponse response) {

        List<CetCourseType> records = cetCourseTypeMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"名称|100","排序|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetCourseType record = records.get(i);
            String[] values = {
                record.getName(),
                            record.getSortOrder()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "专题分类_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cetCourseType_selects")
    @ResponseBody
    public Map cetCourseType_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetCourseTypeExample example = new CetCourseTypeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        long count = cetCourseTypeMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CetCourseType> cetCourseTypes = cetCourseTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != cetCourseTypes && cetCourseTypes.size()>0){

            for(CetCourseType cetCourseType:cetCourseTypes){

                Select2Option option = new Select2Option();
                option.setText(cetCourseType.getName());
                option.setId(cetCourseType.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
