package controller.cadre;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.cadre.CadreConcat;
import domain.cadre.CadreConcatExample;
import domain.sys.SysUser;
import domain.sys.SysUserView;
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
import sys.utils.PropertiesUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class CadreConcatController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreConcat:list")
    @RequestMapping("/cadreConcat")
    public String cadreConcat() {

        return "index";
    }
    @RequiresPermissions("cadreConcat:list")
    @RequestMapping("/cadreConcat_page")
    public String cadreConcat_page(HttpServletResponse response,
                                    int cadreId,
                                 @RequestParam(required = false, defaultValue = "0") int export, ModelMap modelMap) {

        CadreConcatExample example = new CadreConcatExample();
        example.createCriteria().andCadreIdEqualTo(cadreId);
        List<CadreConcat> cadreConcats = cadreConcatMapper.selectByExample(example);
        modelMap.put("cadreConcats", cadreConcats);

        if (export == 1) {
            cadreConcat_export(example, response);
            return null;
        }

        return "cadre/cadreConcat/cadreConcat_page";
    }

    @RequiresPermissions("cadreConcat:edit")
    @RequestMapping(value = "/cadreConcat_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreConcat_au(CadreConcat record, HttpServletRequest request) {

        Integer cadreId = record.getCadreId();

        if(record.getMobile()!=null) {
            if (!FormUtils.match(PropertiesUtils.getString("mobile.regex"), record.getMobile())) {
                throw new RuntimeException("手机号码有误");
            }
        }

        cadreConcatService.insertOrUpdate(record);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "添加/更新干部联系方式：%s", record.getCadreId()));

        return success(FormUtils.SUCCESS);
    }
  /*  @RequiresPermissions("cadreConcat:edit")
    @RequestMapping(value = "/cadreConcat_updateWork", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreConcat_updateWork(int cadreId, String work, HttpServletRequest request) {


        CadreConcat record = new CadreConcat();
        record.setCadreId(cadreId);
        record.setWork(work);
        record.setWorkSaveDate(new Date());

        cadreConcatService.insertOrUpdate(record);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "添加/更新工作经历信息：%s, %s", record.getCadreId(), work));
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("cadreConcat:edit")
    @RequestMapping("/cadreConcat_au")
    public String cadreConcat_au(Integer cadreId, ModelMap modelMap) {

        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        if (cadreId != null) {
            CadreConcat cadreConcat = cadreConcatMapper.selectByPrimaryKey(cadreId);
            modelMap.put("cadreConcat", cadreConcat);
        }
        return "cadre/cadreConcat/cadreConcat_au";
    }

    /*@RequiresPermissions("cadreConcat:del")
    @RequestMapping(value = "/cadreConcat_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreConcat_del(HttpServletRequest request, Integer cadreId) {

        if (cadreId != null) {

            cadreConcatService.del(cadreId);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除干部联系方式：%s", cadreId));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreConcat:del")
    @RequestMapping(value = "/cadreConcat_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] cadreIds, ModelMap modelMap) {


        if (null != cadreIds && cadreIds.length>0){
            cadreConcatService.batchDel(cadreIds);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部联系方式：%s", StringUtils.join(cadreIds, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/


    public void cadreConcat_export(CadreConcatExample example, HttpServletResponse response) {

        List<CadreConcat> cadreConcats = cadreConcatMapper.selectByExample(example);
        int rownum = cadreConcatMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"手机号","办公电话","电子邮箱"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreConcat cadreConcat = cadreConcats.get(i);
            String[] values = {
                        cadreConcat.getMobile(),
                                            cadreConcat.getOfficePhone(),
                                            cadreConcat.getEmail()
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "干部联系方式_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
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
