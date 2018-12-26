package service.cet;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import controller.global.OpException;
import domain.cet.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.cet.common.TrainTempData;
import sys.constants.CetConstants;
import sys.utils.ContextHelper;

import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CetTrainInspectorCourseService extends CetBaseMapper {

    @Autowired
    private CetTrainEvaTableService trainEvaTableService;

    @Transactional
    public void doEva(int id, String feedback){

        CetTrainInspectorCourse tic = cetTrainInspectorCourseMapper.selectByPrimaryKey(id);
        CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(tic.getTrainCourseId());
        TrainTempData tempdata = getTempdata(tic.getTempdata());
        Map<Integer, CetTrainEvaResult> trainEvaResultMap = tempdata.getTrainEvaResultMap();

        Map<Integer, CetTrainEvaTable> evaTableMap = trainEvaTableService.findAll();
        CetTrainEvaTable trainEvaTable = evaTableMap.get(cetTrainCourse.getEvaTableId());
        List<CetTrainEvaNorm> normList = trainEvaTable.getNormList();
        int normSize = normList.size();

        for (int i=0; i<normSize; i++) {
            CetTrainEvaNorm norm = normList.get(i);
            CetTrainEvaNorm topNorm = norm.getTopNorm();
            if(!trainEvaResultMap.containsKey(norm.getId())){
                throw  new OpException(String.format("未完成指标（第%s/%s步:%s)", (i+1), normSize+1,
                        (topNorm!=null?topNorm.getName()+"-":"") + norm.getName()));
            }

            CetTrainEvaResult trainEvaResult = trainEvaResultMap.get(norm.getId());
            cetTrainEvaResultMapper.insertSelective(trainEvaResult);
        }
        {
            CetTrainInspectorCourse record = new CetTrainInspectorCourse();
            record.setId(id);
            record.setSubmitTime(new Date());
            record.setSubmitIp(ContextHelper.getRealIp());
            record.setFeedback(feedback);
            record.setStatus(CetConstants.CET_TRAIN_INSPECTOR_COURSE_STATUS_FINISH);
            cetTrainInspectorCourseMapper.updateByPrimaryKeySelective(record);
        }
    }

    public int getFinishCourseNum(int inspectorId){

        CetTrainInspectorCourseExample example = new CetTrainInspectorCourseExample();
        example.createCriteria().andInspectorIdEqualTo(inspectorId)
                .andStatusEqualTo(CetConstants.CET_TRAIN_INSPECTOR_COURSE_STATUS_FINISH);

        return (int)cetTrainInspectorCourseMapper.countByExample(example);
    }

    // 测评人某课程的测评暂存结果
    @Transactional
    public CetTrainInspectorCourse get(int inspectorId, int trainCourseId) {

        CetTrainInspectorCourseExample example = new CetTrainInspectorCourseExample();
        example.createCriteria().andInspectorIdEqualTo(inspectorId).andTrainCourseIdEqualTo(trainCourseId);
        List<CetTrainInspectorCourse> cetTrainInspectorCourses = cetTrainInspectorCourseMapper.selectByExample(example);

        return cetTrainInspectorCourses.size()==0?null:cetTrainInspectorCourses.get(0);
    }

    // 测评人暂存结果情况 <课程ID，结果>
    @Transactional
    public Map<Integer, CetTrainInspectorCourse> get(int inspectorId) {

        CetTrainInspectorCourseExample example = new CetTrainInspectorCourseExample();
        example.createCriteria().andInspectorIdEqualTo(inspectorId);
        List<CetTrainInspectorCourse> cetTrainInspectorCourses = cetTrainInspectorCourseMapper.selectByExample(example);
        Map<Integer, CetTrainInspectorCourse> resultMap = new HashMap<>();
        for (CetTrainInspectorCourse tic : cetTrainInspectorCourses) {

            resultMap.put(tic.getTrainCourseId(), tic);
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
