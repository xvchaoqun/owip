package service.train;

import bean.TrainTempData;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import domain.train.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;

import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrainInspectorCourseService extends BaseMapper {

    @Autowired
    private TrainEvaTableService trainEvaTableService;
    @Autowired
    private TrainCourseService trainCourseService;

    @Transactional
    public void doEva(int id, String feedback){

        TrainInspectorCourse tic = trainInspectorCourseMapper.selectByPrimaryKey(id);
        TrainCourse trainCourse = trainCourseMapper.selectByPrimaryKey(tic.getCourseId());
        TrainTempData tempdata = getTempdata(tic.getTempdata());
        Map<Integer, TrainEvaResult> trainEvaResultMap = tempdata.getTrainEvaResultMap();

        Map<Integer, TrainEvaTable> evaTableMap = trainEvaTableService.findAll();
        TrainEvaTable trainEvaTable = evaTableMap.get(trainCourse.getEvaTableId());
        List<TrainEvaNorm> normList = trainEvaTable.getNormList();
        int normSize = normList.size();

        for (int i=0; i<normSize; i++) {
            TrainEvaNorm norm = normList.get(i);
            TrainEvaNorm topNorm = norm.getTopNorm();
            if(!trainEvaResultMap.containsKey(norm.getId())){
                throw  new RuntimeException(String.format("未完成指标（第%s/%s步:%s)", (i+1), normSize+1,
                        (topNorm!=null?topNorm.getName()+"-":"") + norm.getName()));
            }

            TrainEvaResult trainEvaResult = trainEvaResultMap.get(norm.getId());
            trainEvaResultMapper.insertSelective(trainEvaResult);
        }
        {
            TrainInspectorCourse record = new TrainInspectorCourse();
            record.setId(id);
            record.setSubmitTime(new Date());
            record.setSubmitIp(ContextHelper.getRealIp());
            record.setFeedback(feedback);
            record.setStatus(SystemConstants.TRAIN_INSPECTOR_COURSE_STATUS_FINISH);
            trainInspectorCourseMapper.updateByPrimaryKeySelective(record);
        }
        {
            Map<Integer, TrainCourse> trainCourseMap = trainCourseService.findAll(trainCourse.getTrainId());
            int finishCourseNum = getFinishCourseNum(tic.getInspectorId());
            TrainInspector record = new TrainInspector();
            record.setId(tic.getInspectorId());
            record.setFinishCourseNum(finishCourseNum);
            if(finishCourseNum==trainCourseMap.size())
                record.setStatus(SystemConstants.TRAIN_INSPECTOR_STATUS_ALL_FINISH);
            else
                record.setStatus(SystemConstants.TRAIN_INSPECTOR_STATUS_PART_FINISH);

            trainInspectorMapper.updateByPrimaryKeySelective(record);
        }

        {
            TrainCourse record = new TrainCourse();
            record.setId(trainCourse.getId());
            record.setFinishCount((trainCourse.getFinishCount()==null?0:trainCourse.getFinishCount())+1);
            trainCourseMapper.updateByPrimaryKeySelective(record);
        }
    }

    public int getFinishCourseNum(int inspectorId){
        TrainInspectorCourseExample example = new TrainInspectorCourseExample();
        example.createCriteria().andInspectorIdEqualTo(inspectorId)
                .andStatusEqualTo(SystemConstants.TRAIN_INSPECTOR_COURSE_STATUS_FINISH);
        return trainInspectorCourseMapper.countByExample(example);
    }

    // 测评人某课程的测评暂存结果
    @Transactional
    public TrainInspectorCourse get(int inspectorId, int courseId) {

        TrainInspectorCourseExample example = new TrainInspectorCourseExample();
        example.createCriteria().andInspectorIdEqualTo(inspectorId).andCourseIdEqualTo(courseId);
        List<TrainInspectorCourse> trainInspectorCourses = trainInspectorCourseMapper.selectByExample(example);

        return trainInspectorCourses.size()==0?null:trainInspectorCourses.get(0);
    }

    // 测评人暂存结果情况 <课程ID，结果>
    @Transactional
    public Map<Integer, TrainInspectorCourse> get(int inspectorId) {

        TrainInspectorCourseExample example = new TrainInspectorCourseExample();
        example.createCriteria().andInspectorIdEqualTo(inspectorId);
        List<TrainInspectorCourse> trainInspectorCourses = trainInspectorCourseMapper.selectByExample(example);
        Map<Integer, TrainInspectorCourse> resultMap = new HashMap<>();
        for (TrainInspectorCourse tic : trainInspectorCourses) {

            resultMap.put(tic.getCourseId(), tic);
        }

        return resultMap;
    }

    // 获取暂存结果（反序列化）
    public TrainTempData getTempdata(int inspectorId, int courseId) {

        String tempdata = get(inspectorId, courseId).getTempdata();
        return  getTempdata(tempdata);
    }

    public TrainTempData getTempdata(String tempdata) {

        TrainTempData tempData = null;
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("tempData", TrainTempData.class);

        if (StringUtils.isNotBlank(tempdata)) {

            tempData = (TrainTempData) xStream.fromXML(tempdata);
        }
        tempData = (tempData == null) ? new TrainTempData() : tempData;

        return tempData;
    }

    // 暂存结果序列化
    public String tempdataToString(TrainTempData tempData){

        XStream xStream = new XStream(new DomDriver());
        xStream.alias("tempData", TrainTempData.class);

        StringWriter sw = new StringWriter();
        xStream.marshal(tempData, new CompactWriter(sw));
        return sw.toString();
    }
}
