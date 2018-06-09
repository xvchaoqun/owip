package controller.cet.mobile;

import persistence.cet.common.TrainTempData;
import controller.cet.CetBaseController;
import controller.global.OpException;
import domain.cet.CetTrainCourse;
import domain.cet.CetTrainEvaNorm;
import domain.cet.CetTrainEvaResult;
import domain.cet.CetTrainEvaTable;
import domain.cet.CetTrainInspector;
import domain.cet.CetTrainInspectorCourse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.CetConstants;
import sys.constants.LogConstants;
import sys.helper.CetHelper;
import sys.utils.ContextHelper;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/m/cet")
public class MobileTrainEvaController extends CetBaseController {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/eva")
    public String next() {

        return "cet/mobile/index";
    }

    @RequestMapping("/eva_page")
    public String eva_page(int trainCourseId,
                           HttpServletRequest request, ModelMap modelMap) {

        CetTrainInspector trainInspector = CetHelper.getTrainInspector(request);
        Integer trainId = trainInspector.getTrainId();
        Map<Integer, CetTrainCourse> trainCourseMap = cetTrainCourseService.findAll(trainId);
        CetTrainCourse trainCourse = trainCourseMap.get(trainCourseId);
        modelMap.put("tc", trainCourse);

        Map<Integer, CetTrainEvaTable> evaTableMap = cetTrainEvaTableService.findAll();
        CetTrainEvaTable trainEvaTable = evaTableMap.get(trainCourse.getEvaTableId());
        if(trainEvaTable==null){
            throw new OpException("该课程还没有分配课程评估表，不可以进行测评");
        }

        return "cet/mobile/eva_page";
    }

    @RequestMapping("/eva_page_next")
    public String eva_page_next(int trainCourseId,

                           Integer lastStep,
                           Integer lastRankId,

                           Integer step,
                           HttpServletRequest request, ModelMap modelMap) {

        CetTrainInspector trainInspector = CetHelper.getTrainInspector(request);
        Integer trainId = trainInspector.getTrainId();
        Map<Integer, CetTrainCourse> trainCourseMap = cetTrainCourseService.findAll(trainId);
        CetTrainCourse trainCourse = trainCourseMap.get(trainCourseId);
        modelMap.put("tc", trainCourse);

        Map<Integer, CetTrainEvaTable> evaTableMap = cetTrainEvaTableService.findAll();
        CetTrainEvaTable trainEvaTable = evaTableMap.get(trainCourse.getEvaTableId());
        if(trainEvaTable==null){
            throw new OpException("该课程当前没有分配课程评估表，不可以进行测评");
        }
        List<CetTrainEvaNorm> normList = trainEvaTable.getNormList();
        int stepNum = normList.size();
        int maxStep = stepNum + 1;
        if (step != null && step <= 0) step = 1;
        if (step != null && step > maxStep) step = maxStep;
        modelMap.put("maxStep", maxStep);

        CetTrainInspectorCourse tic = cetTrainInspectorCourseService.get(trainInspector.getId(), trainCourseId);
        modelMap.put("tic", tic);
        TrainTempData tempdata = null;
        if (tic != null) {
            tempdata = cetTrainInspectorCourseService.getTempdata(tic.getTempdata());
            if (step == null) { // 默认进入上一次到达的步骤
                step = tempdata.getStopStep();
            }
        }
        modelMap.put("tempdata", tempdata);

        //if (step == null || step <= stepNum) {
        step = step == null ? 1 : step;
        modelMap.put("step", step);
        if (step <= stepNum) {
            CetTrainEvaNorm norm = normList.get(step - 1);
            modelMap.put("norm", norm); // 准备下一步要显示的指标

            modelMap.put("rankNum", trainEvaTable.getRankNum());
            modelMap.put("ranks", trainEvaTable.getRanks());
        }else {
            List<CetTrainEvaNorm> norms = trainEvaTable.getNorms();
            modelMap.put("topNorms", norms);
        }

        if (tempdata == null) {
            tempdata = new TrainTempData();
        }
        tempdata.setStepNum(stepNum);
        tempdata.setStopStep(step);

        if (lastStep != null && lastStep > 0 && lastStep <= stepNum && lastRankId > 0) { // 更新暂存结果

            CetTrainEvaNorm lastNorm = normList.get(lastStep - 1);
            Map<Integer, CetTrainEvaResult> trainEvaResultMap = tempdata.getTrainEvaResultMap();
            CetTrainEvaResult trainEvaResult = trainEvaResultMap.get(lastNorm.getId());
            if (trainEvaResult == null) trainEvaResult = new CetTrainEvaResult();
            trainEvaResult.setInspectorId(trainInspector.getId());
            trainEvaResult.setTrainId(trainId);
            trainEvaResult.setTrainCourseId(trainCourseId);
            trainEvaResult.setEvaTableId(trainEvaTable.getId());
            trainEvaResult.setNormId(lastNorm.getId());
            trainEvaResult.setRankId(lastRankId);

            trainEvaResultMap.put(lastNorm.getId(), trainEvaResult);
        }

        String tempdataToString = cetTrainInspectorCourseService.tempdataToString(tempdata);
        CetTrainInspectorCourse record = new CetTrainInspectorCourse();
        record.setInspectorId(trainInspector.getId());
        record.setTrainCourseId(trainCourseId);
        record.setStatus(CetConstants.CET_TRAIN_INSPECTOR_COURSE_STATUS_SAVE);
        record.setSubmitIp(ContextHelper.getRealIp());
        record.setSubmitTime(new Date());
        record.setTempdata(tempdataToString);
        if (tic == null) {
            cetTrainInspectorCourseMapper.insertSelective(record);
        } else {
            record.setId(tic.getId());
            cetTrainInspectorCourseMapper.updateByPrimaryKeySelective(record);
        }

        /*}else{
            modelMap.put("step", step);
        }*/

        return "cet/mobile/eva_page_next";
    }

    @RequestMapping(value = "/eva", method = RequestMethod.POST)
    @ResponseBody
    public Map do_eva(int id, String feedback, HttpServletRequest request) {

        cetTrainInspectorCourseService.doEva(id, feedback);

        CetTrainInspector trainInspector = CetHelper.getTrainInspector(request);
        logger.info(addNoLoginLog(trainInspector.getId(), trainInspector.getUsername(),
                LogConstants.LOG_CET, "提交评课：" + id));

        return success(FormUtils.SUCCESS);
    }
}
