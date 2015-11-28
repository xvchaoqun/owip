package service.party;

import domain.MemberTeacher;
import domain.MemberTeacherExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.List;

@Service
public class MemberTeacherService extends BaseMapper {

    public boolean idDuplicate(Integer userId, String code){

        Assert.isTrue(StringUtils.isNotBlank(code));

        MemberTeacherExample example = new MemberTeacherExample();
        MemberTeacherExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if(userId!=null) criteria.andUserIdNotEqualTo(userId);

        return memberTeacherMapper.countByExample(example) > 0;
    }

    public MemberTeacher get(int userId){

        MemberTeacherExample example = new MemberTeacherExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<MemberTeacher> memberTeachers = memberTeacherMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(memberTeachers.size()>0) return memberTeachers.get(0);
        return null;
    }
}
