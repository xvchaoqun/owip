package service.party;

import domain.member.Teacher;
import domain.member.TeacherExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class TeacherService extends BaseMapper {

    public Teacher get(int userId){

        return teacherMapper.selectByPrimaryKey(userId);
    }

    public boolean idDuplicate(Integer userId){

        TeacherExample example = new TeacherExample();
        TeacherExample.Criteria criteria = example.createCriteria();
        if(userId!=null) criteria.andUserIdNotEqualTo(userId);

        return teacherMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(Teacher record){

        Assert.isTrue(!idDuplicate(record.getUserId()));
        teacherMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer userId){

        teacherMapper.deleteByPrimaryKey(userId);
    }

    @Transactional
    public void batchDel(Integer[] userIds){

        TeacherExample example = new TeacherExample();
        example.createCriteria().andUserIdIn(Arrays.asList(userIds));
        teacherMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(Teacher record){
        return teacherMapper.updateByPrimaryKeySelective(record);
    }
}
