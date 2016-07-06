package controller.cadre;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.cadre.CadreCompany;
import domain.cadre.CadreCompanyExample;
import domain.sys.SysUser;
import org.apache.commons.lang3.StringUtils;
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
public class CadreCompanyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreCompany:list")
    @RequestMapping("/cadreCompany")
    public String cadreCompany() {

        return "index";
    }
    @RequiresPermissions("cadreCompany:list")
    @RequestMapping("/cadreCompany_page")
    public String cadreCompany_page(HttpServletResponse response,
                                    int cadreId,
                                 @RequestParam(required = false, defaultValue = "0") int export, ModelMap modelMap) {


        CadreCompanyExample example = new CadreCompanyExample();
        example.createCriteria().andCadreIdEqualTo(cadreId);
        List<CadreCompany> cadreCompanys = cadreCompanyMapper.selectByExample(example);
        modelMap.put("cadreCompanys", cadreCompanys);

        if (export == 1) {
            cadreCompany_export(example, response);
            return null;
        }
        
        return "cadre/cadreCompany/cadreCompany_page";
    }

    @RequiresPermissions("cadreCompany:edit")
    @RequestMapping(value = "/cadreCompany_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreCompany_au(CadreCompany record, String _startTime, HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_startTime)){
            record.setStartTime(DateUtils.parseDate(_startTime, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {
            cadreCompanyService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部企业兼职情况：%s", record.getId()));
        } else {

            cadreCompanyService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部企业兼职情况：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreCompany:edit")
    @RequestMapping("/cadreCompany_au")
    public String cadreCompany_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreCompany cadreCompany = cadreCompanyMapper.selectByPrimaryKey(id);
            modelMap.put("cadreCompany", cadreCompany);
        }
        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);
        return "cadre/cadreCompany/cadreCompany_au";
    }

    @RequiresPermissions("cadreCompany:del")
    @RequestMapping(value = "/cadreCompany_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreCompany_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreCompanyService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除干部企业兼职情况：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreCompany:del")
    @RequestMapping(value = "/cadreCompany_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreCompanyService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部企业兼职情况：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void cadreCompany_export(CadreCompanyExample example, HttpServletResponse response) {

        List<CadreCompany> cadreCompanys = cadreCompanyMapper.selectByExample(example);
        int rownum = cadreCompanyMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"兼职起始时间","兼职单位及职务"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreCompany cadreCompany = cadreCompanys.get(i);
            String[] values = {
                        DateUtils.formatDate(cadreCompany.getStartTime(), DateUtils.YYYY_MM_DD),
                                            cadreCompany.getUnit()
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "干部企业兼职情况_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
