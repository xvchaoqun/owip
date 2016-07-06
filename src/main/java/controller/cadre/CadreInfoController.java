package controller.cadre;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.cadre.CadreInfo;
import domain.cadre.CadreInfoExample;
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
import sys.utils.PropertiesUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class CadreInfoController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreInfo:list")
    @RequestMapping("/cadreInfo")
    public String cadreInfo() {

        return "index";
    }
    @RequiresPermissions("cadreInfo:list")
    @RequestMapping("/cadreInfo_page")
    public String cadreInfo_page(HttpServletResponse response,
                                    int cadreId,
                                 @RequestParam(required = false, defaultValue = "0") int export, ModelMap modelMap) {

        CadreInfoExample example = new CadreInfoExample();
        example.createCriteria().andCadreIdEqualTo(cadreId);
        List<CadreInfo> cadreInfos = cadreInfoMapper.selectByExample(example);
        modelMap.put("cadreInfos", cadreInfos);

        if (export == 1) {
            cadreInfo_export(example, response);
            return null;
        }

        return "cadre/cadreInfo/cadreInfo_page";
    }

    @RequiresPermissions("cadreInfo:edit")
    @RequestMapping(value = "/cadreInfo_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreInfo_au(CadreInfo record, HttpServletRequest request) {

        Integer cadreId = record.getCadreId();

        if(record.getMobile()!=null) {
            if (!FormUtils.match(PropertiesUtils.getString("mobile.regex"), record.getMobile())) {
                throw new RuntimeException("手机号码有误");
            }
        }

        cadreInfoService.insertOrUpdate(record);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "添加/更新干部联系方式：%s", record.getCadreId()));

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("cadreInfo:edit")
    @RequestMapping(value = "/cadreInfo_updateWork", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreInfo_updateWork(int cadreId, String work, HttpServletRequest request) {


        CadreInfo record = new CadreInfo();
        record.setCadreId(cadreId);
        record.setWork(work);
        record.setWorkSaveDate(new Date());

        cadreInfoService.insertOrUpdate(record);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "添加/更新工作经历信息：%s, %s", record.getCadreId(), work));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreInfo:edit")
    @RequestMapping("/cadreInfo_au")
    public String cadreInfo_au(Integer cadreId, ModelMap modelMap) {

        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUser sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        if (cadreId != null) {
            CadreInfo cadreInfo = cadreInfoMapper.selectByPrimaryKey(cadreId);
            modelMap.put("cadreInfo", cadreInfo);
        }
        return "cadre/cadreInfo/cadreInfo_au";
    }

    @RequiresPermissions("cadreInfo:del")
    @RequestMapping(value = "/cadreInfo_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreInfo_del(HttpServletRequest request, Integer cadreId) {

        if (cadreId != null) {

            cadreInfoService.del(cadreId);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除干部联系方式：%s", cadreId));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreInfo:del")
    @RequestMapping(value = "/cadreInfo_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] cadreIds, ModelMap modelMap) {


        if (null != cadreIds && cadreIds.length>0){
            cadreInfoService.batchDel(cadreIds);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部联系方式：%s", StringUtils.join(cadreIds, ",")));
        }

        return success(FormUtils.SUCCESS);
    }


    public void cadreInfo_export(CadreInfoExample example, HttpServletResponse response) {

        List<CadreInfo> cadreInfos = cadreInfoMapper.selectByExample(example);
        int rownum = cadreInfoMapper.countByExample(example);

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

            CadreInfo cadreInfo = cadreInfos.get(i);
            String[] values = {
                        cadreInfo.getMobile(),
                                            cadreInfo.getOfficePhone(),
                                            cadreInfo.getEmail()
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
