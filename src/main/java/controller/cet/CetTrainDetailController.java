package controller.cet;

import domain.cet.CetTrain;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.CrsConstants;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetTrainDetailController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_detail_trainee")
    public String cetTrain_detail_trainee(int trainId, ModelMap modelMap) {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        modelMap.put("cetTrain", cetTrain);

        return "cet/cetTrain/cetTrain_detail_trainee";
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping("/cetTrain_detail_time")
    public String cetTrain_detail_time(int trainId, ModelMap modelMap) {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        modelMap.put("cetTrain", cetTrain);

        return "cet/cetTrain/cetTrain_detail_time";
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping(value = "/cetTrain_detail_time", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrain_detail_time(int trainId, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startTime,
                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endTime,
                             HttpServletRequest request) {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);

        CetTrain record = new CetTrain();
        record.setId(trainId);
        record.setStartTime(startTime);
        record.setEndTime(endTime);

        cetTrainService.updateBase(record);

        logger.info(addLog(SystemConstants.LOG_CET, "更新培训班[{%s}]报名时间：%s~%s",
                cetTrain.getName(), DateUtils.formatDate(startTime, DateUtils.YYYY_MM_DD_HH_MM),
                DateUtils.formatDate(endTime, DateUtils.YYYY_MM_DD_HH_MM)));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrain:edit")
    @RequestMapping(value = "/cetTrain_detail_enrollStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map do_step2_enrollStatus(int trainId,
                             byte enrollStatus, // 开启、关闭、暂停报名
                             HttpServletRequest request) {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);

        CetTrain record = new CetTrain();
        record.setId(trainId);
        record.setEnrollStatus(enrollStatus);

        cetTrainService.updateBase(record);

        logger.info(addLog(SystemConstants.LOG_CET, "更新培训班[{%s}]报名开关：%s",
                cetTrain.getName(), CrsConstants.CRS_POST_ENROLL_STATUS_MAP.get(enrollStatus)));

        return success(FormUtils.SUCCESS);
    }
}
