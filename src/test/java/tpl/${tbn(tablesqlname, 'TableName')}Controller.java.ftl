<#assign TableName=tbn(tablesqlname, "TableName")>
<#assign tableName=tbn(tablesqlname, "tableName")>
<#assign tablename=tbn(tablesqlname, "tablename")>
package controller;

import domain.${TableName};
import domain.${TableName}Example;
import domain.${TableName}Example.Criteria;
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
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;
import sys.constants.SystemConstants;

import java.util.ArrayList;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class ${TableName}Controller extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("${tableName}:list")
    @RequestMapping("/${tableName}")
    public String ${tableName}() {

        return "index";
    }
    @RequiresPermissions("${tableName}:list")
    @RequestMapping("/${tableName}_page")
    public String ${tableName}_page(HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "sort_order") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                <#list searchColumnBeans as column>
                                    <#if column.type=="varchar"||column.type=="text"||column.type=="datetime"||column.type=="date">String<#elseif column.type=="int">Integer</#if> ${tbn(column.name, "tableName")},
                                </#list>
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ${TableName}Example example = new ${TableName}Example();
        Criteria criteria = example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause(String.format("%s %s", sort, order));

        <#list searchColumnBeans as column>
        <#if column.type=="int">
        if (${tbn(column.name, "tableName")}!=null) {
            criteria.and${tbn(column.name, "TableName")}EqualTo(${tbn(column.name, "tableName")});
        }
        <#else>
        if (StringUtils.isNotBlank(${tbn(column.name, "tableName")})) {
            criteria.and${tbn(column.name, "TableName")}Like("%" + ${tbn(column.name, "tableName")} + "%");
        }
        </#if>
        </#list>

        if (export == 1) {
            ${tableName}_export(example, response);
            return null;
        }

        int count = ${tableName}Mapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<${TableName}> ${TableName}s = ${tableName}Mapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("${tableName}s", ${TableName}s);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        <#list searchColumnBeans as column>
        <#if column.type=="int">
        if (${tbn(column.name, "tableName")}!=null) {
            searchStr += "&${tbn(column.name, "tableName")}=" + ${tbn(column.name, "tableName")};
        }
        <#else>
        if (StringUtils.isNotBlank(${tbn(column.name, "tableName")})) {
            searchStr += "&${tbn(column.name, "tableName")}=" + ${tbn(column.name, "tableName")};
        }
        </#if>
        </#list>
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "${tableName}/${tableName}_page";
    }

    @RequiresPermissions("${tableName}:edit")
    @RequestMapping(value = "/${tableName}_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_${tableName}_au(${TableName} record, HttpServletRequest request) {

        Integer id = record.get${tbn(key, "TableName")}();

        if (${tableName}Service.idDuplicate(id, code)) {
            return failed("添加重复");
        }
        if (id == null) {
            record.setStatus(true);
            ${tableName}Service.insertSelective(record);
            logger.info(addLog(request, ${logType}, "添加${cnTableName}：%s", record.get${tbn(key, "TableName")}()));
        } else {

            ${tableName}Service.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, ${logType}, "更新${cnTableName}：%s", record.get${tbn(key, "TableName")}()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("${tableName}:edit")
    @RequestMapping("/${tableName}_au")
    public String ${tableName}_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ${TableName} ${tableName} = ${tableName}Mapper.selectByPrimaryKey(id);
            modelMap.put("${tableName}", ${tableName});
        }
        return "${tableName}/${tableName}_au";
    }

    @RequiresPermissions("${tableName}:del")
    @RequestMapping(value = "/${tableName}_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_${tableName}_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            ${tableName}Service.del(id);
            logger.info(addLog(request, ${logType}, "删除${cnTableName}：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("${tableName}:del")
    @RequestMapping(value = "/${tableName}_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            ${tableName}Service.batchDel(ids);
            logger.info(addLog(request, ${logType}, "批量删除${cnTableName}：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("${tableName}:changeOrder")
    @RequestMapping(value = "/${tableName}_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_${tableName}_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        ${tableName}Service.changeOrder(id, addNum);
        logger.info(addLog(request, ${logType}, "${cnTableName}调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void ${tableName}_export(${TableName}Example example, HttpServletResponse response) {

        List<${TableName}> ${tableName}s = ${tableName}Mapper.selectByExample(example);
        int rownum = ${tableName}Mapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {<#list tableColumns as column>"${column.comments}"<#if column_has_next>,</#if></#list>};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            ${TableName} ${tableName} = ${tableName}s.get(i);
            String[] values = {
                    <#list tableColumns as column>
                        <#if column.type=="datetime">
                        DateUtils.formatDate(${tableName}.get${tbn(column.name, "TableName")}(), DateUtils.YYYY_MM_DD_HH_MM_SS)<#if column_has_next>,</#if>
                        <#elseif column.type=="date">
                        DateUtils.formatDate(${tableName}.get${tbn(column.name, "TableName")}(), DateUtils.YYYY_MM_DD)<#if column_has_next>,</#if>
                        <#else>
                        ${tableName}.get${tbn(column.name, "TableName")}()<#if column.type=="int">+""</#if><#if column_has_next>,</#if>
                        </#if>
                    </#list>};

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "${cnTableName}_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

        int count = ${tableName}Mapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<${TableName}> ${tableName}s = ${tableName}Mapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != ${tableName}s && ${tableName}s.size()>0){

            for(${TableName} ${tableName}:${tableName}s){

                Select2Option option = new Select2Option();
                option.setText(${tableName}.getName());
                option.setId(${tableName}.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
