package controller.dispatch;

import controller.BaseController;
import domain.DispatchType;
import domain.DispatchTypeExample;
import domain.DispatchTypeExample.Criteria;
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
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class DispatchTypeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dispatchType:list")
    @RequestMapping("/dispatchType")
    public String dispatchType() {

        return "index";
    }
    @RequiresPermissions("dispatchType:list")
    @RequestMapping("/dispatchType_page")
    public String dispatchType_page(HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "sort_order") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                    String name,
                                    String attr,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DispatchTypeExample example = new DispatchTypeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (StringUtils.isNotBlank(attr)) {
            criteria.andAttrLike("%" + attr + "%");
        }

        if (export == 1) {
            dispatchType_export(example, response);
            return null;
        }

        int count = dispatchTypeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DispatchType> dispatchTypes = dispatchTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("dispatchTypes", dispatchTypes);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (StringUtils.isNotBlank(name)) {
            searchStr += "&name=" + name;
        }
        if (StringUtils.isNotBlank(attr)) {
            searchStr += "&attr=" + attr;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "dispatch/dispatchType/dispatchType_page";
    }

    @RequiresPermissions("dispatchType:edit")
    @RequestMapping(value = "/dispatchType_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchType_au(DispatchType record, HttpServletRequest request) {

        Integer id = record.getId();

        if (dispatchTypeService.idDuplicate(id, record.getName())) {
            return failed("添加重复");
        }
        if (id == null) {
            record.setCreateTime(new Date());
            dispatchTypeService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加发文类型：%s", record.getId()));
        } else {

            dispatchTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新发文类型：%s", record.getId()));
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
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除发文类型：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchType:del")
    @RequestMapping(value = "/dispatchType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            dispatchTypeService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "批量删除发文类型：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dispatchType:changeOrder")
    @RequestMapping(value = "/dispatchType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dispatchType_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        DispatchType dispatchType = dispatchTypeMapper.selectByPrimaryKey(id);
        dispatchTypeService.changeOrder(id, addNum, dispatchType.getYear());
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "发文类型调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void dispatchType_export(DispatchTypeExample example, HttpServletResponse response) {

        List<DispatchType> dispatchTypes = dispatchTypeMapper.selectByExample(example);
        int rownum = dispatchTypeMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"名称","发文属性","所属年份","添加时间"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            DispatchType dispatchType = dispatchTypes.get(i);
            String[] values = {
                        dispatchType.getName(),
                                            dispatchType.getAttr(),
                                            dispatchType.getYear()+"",
                                            DateUtils.formatDate(dispatchType.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "发文类型_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
        example.setOrderByClause("sort_order desc");

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
