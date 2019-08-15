package service.dp.dpCommon;

import domain.sys.TeacherInfo;
import domain.sys.TeacherInfoExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import service.dp.DpBaseMapper;

import java.util.List;

@Service
public class DpCommonService extends DpBaseMapper {

    public TeacherInfo findById(int id){
        TeacherInfoExample example = new TeacherInfoExample();
        example.createCriteria().andUserIdEqualTo(id);
        List<TeacherInfo> teacherInfos = teacherInfoMapper.selectByExampleWithRowbounds(example, new RowBounds(0,1));

        return (teacherInfos.size() > 0) ? teacherInfos.get(0) : null;

    }
}
