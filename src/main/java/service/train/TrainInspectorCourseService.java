package service.train;

import bean.TrainTempData;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import domain.train.TrainInspectorCourse;
import domain.train.TrainInspectorCourseExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrainInspectorCourseService extends BaseMapper {

    @Autowired
    private TrainService trainService;

    // 测评人某课程测评结果
    @Transactional
    public TrainInspectorCourse get(int inspectorId, int courseId) {

        TrainInspectorCourseExample example = new TrainInspectorCourseExample();
        example.createCriteria().andInspectorIdEqualTo(inspectorId).andCourseIdEqualTo(courseId);
        List<TrainInspectorCourse> trainInspectorCourses = trainInspectorCourseMapper.selectByExample(example);

        return trainInspectorCourses.size()==0?null:trainInspectorCourses.get(0);
    }

    // 测评人结果情况 <课程ID，结果>
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
