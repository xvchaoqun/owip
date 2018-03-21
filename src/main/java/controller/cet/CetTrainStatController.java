package controller.cet;

import domain.cet.CetTrain;
import domain.cet.CetTrainCourse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetTrainStatController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrainStat:list")
    @RequestMapping("/cetTrainStat")
    public String cetTrainStat(int trainId, Integer trainCourseId,
                             @RequestParam(required = false, defaultValue = "0") int export,
                             ModelMap modelMap, HttpServletResponse response) throws IOException {

        if (export == 1) {
            CetTrain train = cetTrainMapper.selectByPrimaryKey(trainId);
            XSSFWorkbook wb = cetTrainStatService.toXlsx(trainId);

            String fileName = train.getName() + "测评结果（截止" + DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + "）";
            ExportHelper.output(wb, fileName + ".xlsx", response);
            return null;
        }

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        modelMap.put("cetTrain", cetTrain);
        Map<Integer, CetTrainCourse> trainCourseMap = cetTrainCourseService.findAll(trainId);
        modelMap.put("trainCourses", trainCourseMap.values());

        if (trainCourseId == null) {
            Map<String, Object> resultMap = cetTrainStatService.statTrain(trainId);
            modelMap.putAll(resultMap);
        } else {
            Map<String, Object> resultMap = cetTrainStatService.statCourse(trainCourseId);
            modelMap.putAll(resultMap);
        }

        return "cet/cetTrainStat/cetTrainStat_page";
    }
}
