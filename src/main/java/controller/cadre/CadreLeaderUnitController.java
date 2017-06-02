package controller.cadre;

import controller.BaseController;
import domain.cadre.CadreLeaderUnit;
import domain.cadre.CadreLeaderUnitExample;
import domain.cadre.CadreLeaderUnitExample.Criteria;
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
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class CadreLeaderUnitController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

   /*
    @RequiresPermissions("cadreLeaderUnit:list")
    @RequestMapping("/cadreLeaderUnit_page")
    public String cadreLeaderUnit_page(HttpServletResponse response,
                                 //@RequestParam(required = false, defaultValue = "sort_order") String sort,
                                 //@RequestParam(required = false, defaultValue = "asc") String order,
                                    Integer leaderId,
                                    Integer unitId,
                                    Integer typeId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreLeaderUnitExample example = new CadreLeaderUnitExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("typeId desc, sort_order asc");

        if (leaderId!=null) {
            criteria.andLeaderIdEqualTo(leaderId);
        }
        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }

        if (export == 1) {
            cadreLeaderUnit_export(example, response);
            return null;
        }

        int count = cadreLeaderUnitMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreLeaderUnit> LeaderUnits = cadreLeaderUnitMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("cadreLeaderUnits", LeaderUnits);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (leaderId!=null) {
            searchStr += "&leaderId=" + leaderId;
        }
        if (unitId!=null) {
            searchStr += "&unitId=" + unitId;
        }
        if (typeId!=null) {
            searchStr += "&typeId=" + typeId;
        }
       *//* if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }*//*
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "cadre/cadreLeaderUnit/cadreLeaderUnit_page";
    }*/

    @RequiresPermissions("cadreLeaderUnit:edit")
    @RequestMapping(value = "/cadreLeaderUnit_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreLeaderUnit_au(CadreLeaderUnit record, HttpServletRequest request) {

        Integer id = record.getId();

        if (cadreLeaderUnitService.idDuplicate(record.getLeaderId(), record.getUnitId(), record.getTypeId())) {
            return failed("添加重复");
        }
        if (id == null) {
            cadreLeaderUnitService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加校领导单位：%s", record.getId()));
        } else {

            cadreLeaderUnitService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新校领导单位：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreLeaderUnit:edit")
    @RequestMapping("/cadreLeaderUnit_au")
    public String cadreLeaderUnit_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CadreLeaderUnit cadreLeaderUnit = cadreLeaderUnitMapper.selectByPrimaryKey(id);
            modelMap.put("cadreLeaderUnit", cadreLeaderUnit);
        }
        return "cadre/cadreLeaderUnit/cadreLeaderUnit_au";
    }

    @RequiresPermissions("cadreLeaderUnit:del")
    @RequestMapping(value = "/cadreLeaderUnit_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreLeaderUnit_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreLeaderUnitService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除校领导单位：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreLeaderUnit:del")
    @RequestMapping(value = "/cadreLeaderUnit_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreLeaderUnitService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除校领导单位：%s", new Object[]{ids}));
        }

        return success(FormUtils.SUCCESS);
    }

    public void cadreLeaderUnit_export(CadreLeaderUnitExample example, HttpServletResponse response) {

        List<CadreLeaderUnit> cadreLeaderUnits = cadreLeaderUnitMapper.selectByExample(example);
        int rownum = cadreLeaderUnitMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"校领导","所属单位","类别"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreLeaderUnit cadreLeaderUnit = cadreLeaderUnits.get(i);
            String[] values = {
                        cadreLeaderUnit.getLeaderId()+"",
                                            cadreLeaderUnit.getUnitId()+"",
                                            cadreLeaderUnit.getTypeId()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "校领导单位_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
