<#assign TableName=tbn(tablesqlname, "TableName")>
<#assign tableName=tbn(tablesqlname, "tableName")>
<#assign tablename=tbn(tablesqlname, "tablename")>
package controller.${folder?replace('/', '.')};

import controller.BaseController;
import domain.${folder?replace('/', '.')}.${TableName};
import domain.${folder?replace('/', '.')}.${TableName}Example;
import domain.${folder?replace('/', '.')}.${TableName}Example.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import sys.constants.LogConstants;
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
<#if resFolder?? && resFolder?trim!=''>@RequestMapping("/${resFolder?trim}")</#if>
public class ${TableName}Controller extends ${tbn(resFolder?trim, "TableName")}BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("${tableName}:list")
    @RequestMapping("/${tableName}")
    public String ${tableName}() {

        return "${folder}/${tableName}/${tableName}_page";
    }

    @RequiresPermissions("${tableName}:list")
    @RequestMapping("/${tableName}_data")
    @ResponseBody
    public void ${tableName}_data(HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "sort_order", tableName = "${tablePrefix}${tablesqlname}") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                <#list searchColumnBeans as column>
                                    <#if column.type=="varchar"||column.type=="text">String<#elseif column.type=="datetime"||column.type=="date">Date<#elseif column.type=="int">Integer<#elseif column.type=="smallint">Short<#elseif column.type=="tinyint">Byte</#if> ${tbn(column.name, "tableName")},
                                </#list>
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

        ${TableName}Example example = new ${TableName}Example();
        Criteria criteria = example.createCriteria()<#if tableColumnsMap['status']??>.andStatusEqualTo(true)</#if>;
        example.setOrderByClause(String.format("%s %s", sort, order));

        <#list searchColumnBeans as column>
        <#if column.type=="int" || column.type=="tinyint" || column.type=="smallint">
        if (${tbn(column.name, "tableName")}!=null) {
            criteria.and${tbn(column.name, "TableName")}EqualTo(${tbn(column.name, "tableName")});
        }
        <#elseif column.type=="datetime"||column.type=="date">
        if (${tbn(column.name, "tableName")}!=null) {
        criteria.and${tbn(column.name, "TableName")}GreaterThan(${tbn(column.name, "tableName")});
        }
        <#else>
        if (StringUtils.isNotBlank(${tbn(column.name, "tableName")})) {
            criteria.and${tbn(column.name, "TableName")}Like("%" + ${tbn(column.name, "tableName")} + "%");
        }
        </#if>
        </#list>

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.and${tbn(key, "TableName")}In(Arrays.asList(ids));
            ${tableName}_export(example, response);
            return;
        }

        long count = ${tableName}Mapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<${TableName}> records= ${tableName}Mapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(${tableName}.class, ${tableName}Mixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("${tableName}:edit")
    @RequestMapping(value = "/${tableName}_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_${tableName}_au(${TableName} record, HttpServletRequest request) {

        Integer ${tbn(key, "tableName")} = record.get${tbn(key, "TableName")}();

        if (${tableName}Service.idDuplicate(${tbn(key, "tableName")}, code)) {
            return failed("添加重复");
        }
        if (${tbn(key, "tableName")} == null) {
            <#if tableColumnsMap['status']??>record.setStatus(true);</#if>
            ${tableName}Service.insertSelective(record);
            logger.info(addLog( ${logType}, "添加${cnTableName}：%s", record.get${tbn(key, "TableName")}()));
        } else {

            ${tableName}Service.updateByPrimaryKeySelective(record);
            logger.info(addLog( ${logType}, "更新${cnTableName}：%s", record.get${tbn(key, "TableName")}()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("${tableName}:edit")
    @RequestMapping("/${tableName}_au")
    public String ${tableName}_au(Integer ${tbn(key, "tableName")}, ModelMap modelMap) {

        if (${tbn(key, "tableName")} != null) {
            ${TableName} ${tableName} = ${tableName}Mapper.selectByPrimaryKey(${tbn(key, "tableName")});
            modelMap.put("${tableName}", ${tableName});
        }
        return "${folder}/${tableName}/${tableName}_au";
    }

    @RequiresPermissions("${tableName}:del")
    @RequestMapping(value = "/${tableName}_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_${tableName}_del(HttpServletRequest request, Integer ${tbn(key, "tableName")}) {

        if (${tbn(key, "tableName")} != null) {

            ${tableName}Service.del(${tbn(key, "tableName")});
            logger.info(addLog( ${logType}, "删除${cnTableName}：%s", ${tbn(key, "tableName")}));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("${tableName}:del")
    @RequestMapping(value = "/${tableName}_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map ${tableName}_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ${tbn(key, "tableName")}s, ModelMap modelMap) {


        if (null != ${tbn(key, "tableName")}s && ${tbn(key, "tableName")}s.length>0){
            ${tableName}Service.batchDel(${tbn(key, "tableName")}s);
            logger.info(addLog( ${logType}, "批量删除${cnTableName}：%s", StringUtils.join(${tbn(key, "tableName")}s, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("${tableName}:changeOrder")
    @RequestMapping(value = "/${tableName}_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_${tableName}_changeOrder(Integer ${tbn(key, "tableName")}, Integer addNum, HttpServletRequest request) {

        ${tableName}Service.changeOrder(${tbn(key, "tableName")}, addNum);
        logger.info(addLog( ${logType}, "${cnTableName}调序：%s,%s", ${tbn(key, "tableName")}, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void ${tableName}_export(${TableName}Example example, HttpServletResponse response) {

        List<${TableName}> records = ${tableName}Mapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {<#list tableColumns as column>"${column.comments}|100"<#if column_has_next>,</#if></#list>};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ${TableName} record = records.get(i);
            String[] values = {
            <#list tableColumns as column>
                <#if column.type=="datetime">
                DateUtils.formatDate(record.get${tbn(column.name, "TableName")}(), DateUtils.YYYY_MM_DD_HH_MM_SS)<#if column_has_next>,</#if>
                <#elseif column.type=="date">
                DateUtils.formatDate(record.get${tbn(column.name, "TableName")}(), DateUtils.YYYY_MM_DD)<#if column_has_next>,</#if>
                <#else>
                record.get${tbn(column.name, "TableName")}()<#if column.type=="int"||column.type=="smallint"||column.type=="tinyint">+""</#if><#if column_has_next>,</#if>
                </#if>
            </#list>};
            valuesList.add(values);
        }
        String fileName = "${cnTableName}_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/${tableName}_selects")
    @ResponseBody
    public Map ${tableName}_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ${TableName}Example example = new ${TableName}Example();
        Criteria criteria = example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr+"%");
        }

        long count = ${tableName}Mapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<${TableName}> records = ${tableName}Mapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(${TableName} record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getName());
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
