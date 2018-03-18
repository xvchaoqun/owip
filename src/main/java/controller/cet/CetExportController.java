package controller.cet;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.tags.CmTag;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/cet")
public class CetExportController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_stat_export")
    public void cetTrain_stat_export(int trainId, HttpServletResponse response) throws IOException {

        XSSFWorkbook wb = cetExportService.cetTrainStatExport(trainId);

        ExportHelper.output(wb, CmTag.getSysConfig().getSchoolName() +
                "党校培训情况统计表.xlsx", response);
    }
}
