package service.sys;

import domain.sys.SysUser;
import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import domain.sys.TeacherInfoExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.util.Arrays;

@Service
public class TeacherInfoService extends BaseMapper {

    // 不存在则创建
    public TeacherInfo get(int userId) {

        TeacherInfo teacherInfo = teacherInfoMapper.selectByPrimaryKey(userId);

        if (teacherInfo == null) {
            SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
            if (sysUser.getType() == SystemConstants.USER_TYPE_JZG) {
                teacherInfo = new TeacherInfo();
                teacherInfo.setUserId(userId);
                teacherInfoMapper.insertSelective(teacherInfo);
            }
        }

        return teacherInfo;
    }

    public boolean idDuplicate(Integer userId) {

        TeacherInfoExample example = new TeacherInfoExample();
        TeacherInfoExample.Criteria criteria = example.createCriteria();
        if (userId != null) criteria.andUserIdNotEqualTo(userId);

        return teacherInfoMapper.countByExample(example) > 0;
    }

    @Transactional
    public void del(Integer userId) {

        teacherInfoMapper.deleteByPrimaryKey(userId);
    }

    @Transactional
    public void batchDel(Integer[] userIds) {

        TeacherInfoExample example = new TeacherInfoExample();
        example.createCriteria().andUserIdIn(Arrays.asList(userIds));
        teacherInfoMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(TeacherInfo record) {

        SysUserView uv = CmTag.getUserById(record.getUserId());

        if (uv.getType() != SystemConstants.USER_TYPE_RETIRE) {
            record.setRetireTime(null);
            iMemberMapper.del_retireTime(record.getUserId());
        }

        return teacherInfoMapper.updateByPrimaryKeySelective(record);
    }
}
