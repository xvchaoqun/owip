package controller.front.train;

import bean.TrainTempData;
import controller.BaseController;
import domain.train.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.SessionUtils;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/train")
public class FrontTrainEvaController extends BaseController {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/eva")
    public String next() {

        return "front/train/index";
    }

    @RequestMapping("/eva_page")
    public String eva_page(int courseId,

                           Integer lastStep,
                           Integer lastRankId,

                           Integer step,
                           HttpServletRequest request, ModelMap modelMap) {

        TrainInspector trainInspector = SessionUtils.getTrainInspector(request);
        Integer trainId = trainInspector.getTrainId();
        Map<Integer, TrainCourse> trainCourseMap = trainCourseService.findAll(trainId);
        TrainCourse trainCourse = trainCourseMap.get(courseId);
        modelMap.put("tc", trainCourse);

        Map<Integer, TrainEvaTable> evaTableMap = trainEvaTableService.findAll();
        TrainEvaTable trainEvaTable = evaTableMap.get(trainCourse.getEvaTableId());
        List<TrainEvaNorm> normList = trainEvaTable.getNormList();
        int stepNum = normList.size();
        int maxStep = stepNum + 1;
        if (step != null && step <= 0) step = 1;
        if (step != null && step > maxStep) step = maxStep;
        modelMap.put("maxStep", maxStep);

        TrainInspectorCourse tic = trainInspectorCourseService.get(trainInspector.getId(), courseId);
        modelMap.put("tic", tic);
        TrainTempData tempdata = null;
        if (tic != null) {
            tempdata = trainInspectorCourseService.getTempdata(tic.getTempdata());
            if (step == null) { // 默认进入上一次到达的步骤
                step = tempdata.getStopStep();
            }
        }
        modelMap.put("tempdata", tempdata);

        //if (step == null || step <= stepNum) {
            step = step == null ? 1 : step;
            modelMap.put("step", step);
            if(step <= stepNum) {
                TrainEvaNorm norm = normList.get(step - 1);
                modelMap.put("norm", norm); // 准备下一步要显示的指标

                modelMap.put("rankNum", trainEvaTable.getRankNum());
                modelMap.put("ranks", trainEvaTable.getRanks());
            }

        if (tempdata == null) {
            tempdata = new TrainTempData();
        }
        tempdata.setStepNum(stepNum);
        tempdata.setStopStep(step);

                if (lastStep != null && lastStep > 0 && lastStep<=stepNum && lastRankId > 0) { // 更新暂存结果

                    TrainEvaNorm lastNorm = normList.get(lastStep - 1);
                    Map<Integer, TrainEvaResult> trainEvaResultMap = tempdata.getTrainEvaResultMap();
                    TrainEvaResult trainEvaResult = trainEvaResultMap.get(lastNorm.getId());
                    if (trainEvaResult == null) trainEvaResult = new TrainEvaResult();
                    trainEvaResult.setInspectorId(trainInspector.getId());
                    trainEvaResult.setTrainId(trainId);
                    trainEvaResult.setCourseId(courseId);
                    trainEvaResult.setEvaTableId(trainEvaTable.getId());
                    trainEvaResult.setNormId(lastNorm.getId());
                    trainEvaResult.setRankId(lastRankId);

                    trainEvaResultMap.put(lastNorm.getId(), trainEvaResult);
                }

                String tempdataToString = trainInspectorCourseService.tempdataToString(tempdata);
                TrainInspectorCourse record = new TrainInspectorCourse();
                record.setInspectorId(trainInspector.getId());
                record.setCourseId(courseId);
                record.setStatus(SystemConstants.TRAIN_INSPECTOR_COURSE_STATUS_SAVE);
                record.setSubmitIp(ContextHelper.getRealIp());
                record.setSubmitTime(new Date());
                record.setTempdata(tempdataToString);
                if (tic == null) {
                    trainInspectorCourseMapper.insertSelective(record);
                } else {
                    record.setId(tic.getId());
                    trainInspectorCourseMapper.updateByPrimaryKeySelective(record);
                }

        /*}else{
            modelMap.put("step", step);
        }*/

        return "front/train/eva_page";
    }

    @RequestMapping(value = "/eva", method = RequestMethod.POST)
    @ResponseBody
    public Map do_eva(int id, int score, String feedback, HttpServletRequest request) {

        trainInspectorCourseService.doEva(id, score, feedback);

        TrainInspector trainInspector = SessionUtils.getTrainInspector(request);
        logger.info(logService.trainInspectorLog(trainInspector.getId(), trainInspector.getUsername(),
                SystemConstants.LOG_USER, "提交评课：" + id));

        return success(FormUtils.SUCCESS);
    }
}
