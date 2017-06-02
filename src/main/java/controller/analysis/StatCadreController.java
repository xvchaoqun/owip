package controller.analysis;

import controller.BaseController;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sys.utils.DateUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class StatCadreController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @RequiresPermissions("statCadre:list")
    @RequestMapping("/stat_cadre")
    public String stat_cadre(String type,
                                  @RequestParam(required = false, defaultValue = "0") int export,
                                  ModelMap modelMap, HttpServletResponse response) throws IOException {

        if (export == 1) {
            XSSFWorkbook wb = statCadreService.toXlsx();
            try {
                String fileName = "北京师范大学中层领导干部情况统计表（" + DateUtils.formatDate(new Date(), "yyyy-MM-dd") + "）";
                ServletOutputStream outputStream = response.getOutputStream();
                fileName = new String(fileName.getBytes(), "ISO8859_1");
                response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
                wb.write(outputStream);
                outputStream.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        Map<String, List> rs = statCadreService.stat(type);
        modelMap.put("rs", rs);

        return "analysis/cadre/stat_cadre_page";
    }
}
