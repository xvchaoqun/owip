package controller.base;

import controller.BaseController;
import domain.base.*;
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
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class MetaTypeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/metaTypes")
    public String metaTypes(String __code, String extraAttr, ModelMap modelMap) {

        if (StringUtils.isNotBlank(extraAttr)) {
            List<MetaType> metaTypes = new ArrayList<>();
            for (MetaType metaType : metaTypeService.metaTypes(__code).values()) {
                if (StringUtils.equals(extraAttr, metaType.getExtraAttr())) {
                    metaTypes.add(metaType);
                }
            }
            modelMap.put("metaTypes", metaTypes);
        } else {
            modelMap.put("metaTypes", metaTypeService.metaTypes(__code).values());
        }

        return "base/metaType/metaTypes";
    }
    @RequestMapping("/metaType")
    public String metaType(@RequestParam(required = false, defaultValue = "2") int cls,ModelMap modelMap) {
        modelMap.put("cls",cls);
        return "base/metaType/metaType_page";
    }

    @RequiresPermissions("metaClassType:list")
    @RequestMapping("/metaType_data")
    public void metaType_data(HttpServletRequest request,
                           HttpServletResponse response, String className, String classCode,
                           String name, String code,
                           Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MetaTypeViewExample example = new MetaTypeViewExample();
        MetaTypeViewExample.Criteria criteria = example.createCriteria().andAvailableEqualTo(true);
        example.setOrderByClause("class_sort_order desc,sort_order asc");
        if (StringUtils.isNotBlank(className)) {
            criteria.andClassNameLike(SqlUtils.like(className));
        }
        if (StringUtils.isNotBlank(classCode)) {
            criteria.andClassCodeLike(SqlUtils.like(classCode));
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike(SqlUtils.like(code));
        }

        long count = metaTypeViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MetaTypeView> MetaTypes = metaTypeViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", MetaTypes);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(MetaClass.class, DispatchTypeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
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
        if (StringUtils.isNotBlank(metaClass.getBoolAttr())) {
            if (record.getBoolAttr() == null) {
                record.setBoolAttr(false);
            }
        }

        if (id == null) {

            metaTypeService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加元数据属性值：%s", id));
        } else {

            metaTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新元数据属性值：%s", id));
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

            modelMap.put("valid", metaTypeService.getValid(id));

        } else if (classId != null) {
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
        modelMap.put("metaClassMap", metaClassService.findAll());
        return "base/metaType/metaType_au";
    }

    @RequiresPermissions("metaType:del")
    @RequestMapping(value = "/metaType_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_metaType_del(Integer id, HttpServletRequest request) {

        if (id != null) {
            metaTypeService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除元数据属性值：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("metaType:del")
    @RequestMapping(value = "/metaType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids) {
            metaTypeService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除元数据属性值：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("metaType:changeOrder")
    @RequestMapping(value = "/metaType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_metaType_changeOrder(Integer id, Integer classId, Integer addNum, HttpServletRequest request) {

      /*  Assert.isTrue(classId > 0, "wrong classId");*/
        metaTypeService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "元数据属性值调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void metaType_export(MetaTypeExample example, HttpServletResponse response) {

        List<MetaType> metaTypes = metaTypeMapper.selectByExample(example);
        int rownum = (int) metaTypeMapper.countByExample(example);
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属分类", "名称", "代码", "布尔属性", "附加属性", "备注"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            MetaType metaType = metaTypes.get(i);
            String[] values = {
                    metaType.getClassId() + "",
                    metaType.getName(),
                    metaType.getCode(),
                    metaType.getBoolAttr() + "",
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

        String fileName = "元数据属性_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }
}
