package controller.pmd;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/pmd")
public class PmdOwController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdOw:admin")
    @RequestMapping("/pmdOw")
    public String pmdOw(@RequestParam(required = false, defaultValue = "1") byte cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (cls == 2) {
            // 党费管理账簿
            return "forward:/pmd/pmdAccount";
        }

        return "forward:/pmd/pmdMonth";
    }

    @RequiresPermissions("pmdOw:export")
    @RequestMapping("/pmdOw_export_page")
    public String pmdOw_export_page(int monthId, ModelMap modelMap) throws IOException {

        modelMap.put("pmdMonth", pmdMonthMapper.selectByPrimaryKey(monthId));

        return "pmd/pmdMonth/pmdOw_export_page";
    }

    @RequiresPermissions("pmdOw:export")
    @RequestMapping("/pmdOw_export")
    public String pmdOw_export(int monthId, HttpServletResponse response) throws IOException {

        XSSFWorkbook wb = pmdExportService.reportOw(monthId);
        if (wb != null) {
            ExportHelper.output(wb, sysConfigService.getSchoolName()+"党费缴纳报表.xlsx", response);
        }

        return null;
    }

    @RequiresPermissions("pmdOw:export")
    @RequestMapping("/pmdOw_parties_export")
    public String pmdOw_parties_export(int monthId, boolean isDetail, HttpServletResponse response) throws IOException {

        XSSFWorkbook wb = pmdExportService.reportParties(monthId, isDetail);
        if (wb != null) {
            ExportHelper.output(wb, sysConfigService.getSchoolName()+"各党委线上缴纳党费"
                    +(isDetail?"明细":"总额")+".xlsx", response);
        }

        return null;
    }
}
