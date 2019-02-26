package controller.dispatch;

import domain.dispatch.DispatchType;
import domain.dispatch.DispatchTypeExample;
import domain.dispatch.DispatchTypeExample.Criteria;
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
import sys.tool.jackson.Select2Option;
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
public class DispatchTypeController extends DispatchBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dispatchType:list")
    @RequestMapping("/dispatchType")
    public String dispatchType() {

        return "dispatch/dispatchType/dispatchType_page";
    }
    @RequiresPermissions("dispatchType:list")
    @RequestMapping("/dispatchType_data")
    @ResponseBody
    public void dispatchType_data(HttpServletResponse response,
                                    Short year,
                                    String name,
                                    String attr,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DispatchTypeExample example = new DispatchTypeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("year desc, sort_order desc");

        if(year!=null){
            criteria.andYearEqualTo(year);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (StringUtils.isNotBlank(attr)) {
            criteria.andAttrLike("%" + attr + "%");
        }

        if (export == 1) {
            dispatchType_export(example, response);
            return;
        }

        int count = dispatchTypeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DispatchType> dispatchTypes = dispatchTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", dispatchTypes);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("dispatchType:edit")
    @RequestMapping(value = "/dispatchType_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchType_au(DispatchType record, HttpServletRequest request) {

        Integer id = record.getId();

        if (dispatchTypeService.idDuplicate(id, record.getName(), record.getYear())) {
            return failed("添加重复");
        }
        if (id == null) {
            record.setCreateTime(new Date());
            dispatchTypeService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加发文类型：%s", record.getId()));
        } else {

            dispatchTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新发文类型：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchType:edit")
    @RequestMapping("/dispatchType_au")
    public String dispatchType_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            DispatchType dispatchType = dispatchTypeMapper.selectByPrimaryKey(id);
            modelMap.put("dispatchType", dispatchType);
        }
        return "dispatch/dispatchType/dispatchType_au";
    }

    @RequiresPermissions("dispatchType:del")
    @RequestMapping(value = "/dispatchType_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchType_del(HttpServletRequest request, Integer id) {

        if (id != null) {
            DispatchType dispatchType = dispatchTypeMapper.selectByPrimaryKey(id);
            dispatchTypeService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除发文类型：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchType:del")
    @RequestMapping(value = "/dispatchType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            dispatchTypeService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除发文类型：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchType:changeOrder")
    @RequestMapping(value = "/dispatchType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchType_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        dispatchTypeService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "发文类型调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void dispatchType_export(DispatchTypeExample example, HttpServletResponse response) {

        List<DispatchType> records = dispatchTypeMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"名称","发文属性","所属年份","添加时间"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DispatchType record = records.get(i);
            String[] values = {
                    record.getName(),
                    record.getAttr(),
                    record.getYear()+"",
                    DateUtils.formatDate(record.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
            };
            valuesList.add(values);
        }
        String fileName = "发文类型_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/dispatchType_selects")
    @ResponseBody
    public Map dispatchType_selects(Integer pageSize,Integer pageNo, Short year, String searchStr) throws IOException {

        Map resultMap = success();
        resultMap.put("totalCount", 0);
        if(year==null) return resultMap;

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DispatchTypeExample example = new DispatchTypeExample();
        Criteria criteria = example.createCriteria().andYearEqualTo(year);
        example.setOrderByClause("year desc, sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        int count = dispatchTypeMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DispatchType> dispatchTypes = dispatchTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != dispatchTypes && dispatchTypes.size()>0){

            for(DispatchType dispatchType:dispatchTypes){

                Select2Option option = new Select2Option();
                option.setText(dispatchType.getName());
                option.setId(dispatchType.getId() + "");

                options.add(option);
            }
        }

        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
