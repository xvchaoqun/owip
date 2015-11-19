package controller;

import domain.MetaClass;
import domain.MetaType;
import domain.MetaTypeExample;
import domain.MetaTypeExample.Criteria;
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
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MetaTypeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/metaTypes")
    public String metaTypes(String __code, ModelMap modelMap) {

        modelMap.put("metaTypes", metaTypeService.metaTypes(__code).values());
        return "metaType/metaTypes";
    }

    @RequiresPermissions("metaType:list")
    @RequestMapping("/metaType")
    public String metaType() {

        return "index";
    }
    @RequiresPermissions("metaType:list")
    @RequestMapping("/metaType_page")
    public String metaType_page(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @RequestParam(required = false, defaultValue = "sort_order") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String order,
                                 String name, String code, Integer classId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MetaTypeExample example = new MetaTypeExample();
        Criteria criteria = example.createCriteria().andAvailableEqualTo(true);
        example.setOrderByClause(String.format("%s %s", sort, order));
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike("%" + code + "%");
        }
        if(classId != null){
            modelMap.put("metaClass", metaClassService.findAll().get(classId));
            criteria.andClassIdEqualTo(classId);
        }
        if (export == 1) {
            metaType_export(example, response);
            return null;
        }

        int count = metaTypeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MetaType> MetaTypes = metaTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("metaTypes", MetaTypes);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;
        if (StringUtils.isNotBlank(name)) {
            searchStr += "&name=" + name;
        }
        if (StringUtils.isNotBlank(code)) {
            searchStr += "&code=" + code;
        }
        if(classId != null){
            searchStr += "&classId=" + classId;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "metaType/metaType_page";
    }

    @RequiresPermissions("metaType:edit")
    @RequestMapping(value = "/metaType_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_metaType_au(MetaType record, HttpServletRequest request) {

        Integer id = record.getId();
        record.setCode(StringUtils.lowerCase(record.getCode()));
        if (StringUtils.isBlank(record.getCode())) {
            record.setCode(metaTypeService.genCode());
        } else if (!metaTypeService.codeAvailable(id, record.getCode())) {
            return failed("编程代码重复");
        }

        MetaClass metaClass = metaClassService.findAll().get(record.getClassId());
        if(StringUtils.isNotBlank(metaClass.getBoolAttr())){
            if(record.getBoolAttr()==null){
                record.setBoolAttr(false);
            }
        }

        if (id == null) {
            record.setAvailable(true);
            metaTypeService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加元数据属性值：%s", id));
        } else {

            metaTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新元数据属性值：%s", id));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("metaType:edit")
    @RequestMapping("/metaType_au")
    public String metaType_au(Integer id, Integer classId, ModelMap modelMap) {

        //MetaClass metaClass = null;
        if (id != null) {
            MetaType metaType = metaTypeMapper.selectByPrimaryKey(id);
            modelMap.put("metaType", metaType);
            //metaClass = metaClassService.findAll().get(metaType.getClassId());
        }else if(classId!=null){
            MetaType metaType = new MetaType();
            metaType.setClassId(classId);
            modelMap.put("metaType", metaType);
            //metaClass = metaClassService.findAll().get(classId);
        }
        /*if(metaClass!=null) {
            metaClass.setBoolAttr(StringUtils.trimToNull(metaClass.getBoolAttr()));
            metaClass.setExtraAttr(StringUtils.trimToNull(metaClass.getExtraAttr()));
            modelMap.put("metaClass", metaClass);
        }*/

        return "metaType/metaType_au";
    }

    @RequiresPermissions("metaType:del")
    @RequestMapping(value = "/metaType_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_metaType_del(Integer id, HttpServletRequest request) {

        if (id != null) {
            metaTypeService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除元数据属性值：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("metaType:del")
    @RequestMapping(value = "/metaType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids){
            metaTypeService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "批量删除元数据属性值：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("metaType:changeOrder")
    @RequestMapping(value = "/metaType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_metaType_changeOrder(Integer id, Integer classId, Integer addNum, HttpServletRequest request) {

        Assert.isTrue(classId>0);
        metaTypeService.changeOrder(id, addNum, classId);
        logger.info(addLog(request, SystemConstants.LOG_ADMIN, "元数据属性值调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void metaType_export(MetaTypeExample example, HttpServletResponse response) {

        List<MetaType> metaTypes = metaTypeMapper.selectByExample(example);
        int rownum = metaTypeMapper.countByExample(example);
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属分类","名称","代码","布尔属性","附加属性","备注"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            MetaType metaType = metaTypes.get(i);
            String[] values = {
                    metaType.getClassId()+"",
                                        metaType.getName(),
                                        metaType.getCode(),
                                        metaType.getBoolAttr()+"",
                                        metaType.getExtraAttr(),
                                        metaType.getRemark()
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "元数据属性_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
