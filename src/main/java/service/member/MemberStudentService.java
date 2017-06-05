package service.member;

import domain.member.MemberStudent;
import domain.member.MemberStudentExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.List;

@Service
public class MemberStudentService extends BaseMapper {

    public boolean idDuplicate(Integer userId, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "code is blank");

        MemberStudentExample example = new MemberStudentExample();
        MemberStudentExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if(userId!=null) criteria.andUserIdNotEqualTo(userId);

        return memberStudentMapper.countByExample(example) > 0;
    }

    public MemberStudent get(int userId){

        MemberStudentExample example = new MemberStudentExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<MemberStudent> memberTeachers = memberStudentMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(memberTeachers.size()>0) return memberTeachers.get(0);
        return null;
    }
}
