package service.sys;

import domain.sys.StudentInfo;
import domain.sys.StudentInfoExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;

/**
 * Created by fafa on 2015/12/11.
 */
@Service
public class StudentInfoService extends BaseMapper {

    public StudentInfo get(int userId){

        return studentInfoMapper.selectByPrimaryKey(userId);
    }

    public boolean idDuplicate(Integer userId){

        StudentInfoExample example = new StudentInfoExample();
        StudentInfoExample.Criteria criteria = example.createCriteria();
        if(userId!=null) criteria.andUserIdNotEqualTo(userId);

        return studentInfoMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(StudentInfo record){

        Assert.isTrue(!idDuplicate(record.getUserId()), "duplicate");
        studentInfoMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer userId){

        studentInfoMapper.deleteByPrimaryKey(userId);
    }

    @Transactional
    public void batchDel(Integer[] userIds){

        StudentInfoExample example = new StudentInfoExample();
        example.createCriteria().andUserIdIn(Arrays.asList(userIds));
        studentInfoMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(StudentInfo record){

        return studentInfoMapper.updateByPrimaryKeySelective(record);
    }
}
