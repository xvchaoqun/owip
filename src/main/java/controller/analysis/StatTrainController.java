package controller.analysis;

import controller.BaseController;
import domain.train.Train;
import domain.train.TrainCourse;
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
public class StatTrainController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("statTrain:list")
    @RequestMapping("/stat_train")
    public String stat_train(int trainId, Integer courseId,
                             @RequestParam(required = false, defaultValue = "0") int export,
                             ModelMap modelMap, HttpServletResponse response) throws IOException {

        if (export == 1) {
            Train train = trainMapper.selectByPrimaryKey(trainId);
            XSSFWorkbook wb = statTrainService.toXlsx(trainId);

            String fileName = train.getName() + "测评结果（截止" + DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + "）";
            ExportHelper.output(wb, fileName + ".xlsx", response);
            return null;
        }

        Map<Integer, TrainCourse> trainCourseMap = trainCourseService.findAll(trainId);
        modelMap.put("trainCourses", trainCourseMap.values());

        if (courseId == null) {
            Map<String, Object> resultMap = statTrainService.statTrain(trainId);
            modelMap.putAll(resultMap);
        } else {
            Map<String, Object> resultMap = statTrainService.statCourse(courseId);
            modelMap.putAll(resultMap);
        }

        return "analysis/train/stat_train_page";
    }
}
