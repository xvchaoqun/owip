package service.cet;

import domain.cet.CetTrainCourseObjResult;
import domain.cet.CetTrainCourseObjResultExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class CetTrainCourseObjResultService extends BaseMapper {

    public boolean idDuplicate(Integer id, int trainCourseObjId, int courseItemId){

        CetTrainCourseObjResultExample example = new CetTrainCourseObjResultExample();
        CetTrainCourseObjResultExample.Criteria criteria = example.createCriteria()
                .andTrainCourseObjIdEqualTo(trainCourseObjId).andCourseItemIdEqualTo(courseItemId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetTrainCourseObjResultMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CetTrainCourseObjResult record){

        cetTrainCourseObjResultMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetTrainCourseObjResultExample example = new CetTrainCourseObjResultExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetTrainCourseObjResultMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetTrainCourseObjResult record){
        return cetTrainCourseObjResultMapper.updateByPrimaryKeySelective(record);
    }
}
