package service.cet;

import domain.cet.CetTrainCourseObj;
import domain.cet.CetTrainCourseObjExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

@Service
public class CetTrainCourseObjService extends BaseMapper {

    public boolean idDuplicate(Integer id, int trainCourseId, int objId){

        CetTrainCourseObjExample example = new CetTrainCourseObjExample();
        CetTrainCourseObjExample.Criteria criteria =
                example.createCriteria().andTrainCourseIdEqualTo(trainCourseId).andObjIdEqualTo(objId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetTrainCourseObjMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CetTrainCourseObj record){

        cetTrainCourseObjMapper.insertSelective(record);
    }

    public void del(Integer id){

        cetTrainCourseObjMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetTrainCourseObj record){
        return cetTrainCourseObjMapper.updateByPrimaryKeySelective(record);
    }
}
