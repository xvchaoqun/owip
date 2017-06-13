package service.sys;

import domain.sys.TeacherInfo;
import domain.sys.TeacherInfoExample;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class TeacherInfoService extends BaseMapper {

    public TeacherInfo get(int userId){

        return teacherInfoMapper.selectByPrimaryKey(userId);
    }

    public boolean idDuplicate(Integer userId){

        TeacherInfoExample example = new TeacherInfoExample();
        TeacherInfoExample.Criteria criteria = example.createCriteria();
        if(userId!=null) criteria.andUserIdNotEqualTo(userId);

        return teacherInfoMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(TeacherInfo record){

        Assert.isTrue(!idDuplicate(record.getUserId()), "duplicate");
        teacherInfoMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer userId){

        teacherInfoMapper.deleteByPrimaryKey(userId);
    }

    @Transactional
    public void batchDel(Integer[] userIds){

        TeacherInfoExample example = new TeacherInfoExample();
        example.createCriteria().andUserIdIn(Arrays.asList(userIds));
        teacherInfoMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(TeacherInfo record){

        if(BooleanUtils.isNotTrue(record.getIsRetire())){
            record.setRetireTime(null);
            iMemberMapper.del_retireTime(record.getUserId());
        }

        return teacherInfoMapper.updateByPrimaryKeySelective(record);
    }
}
