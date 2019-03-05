package persistence.member;

import domain.member.Member;
import domain.member.MemberExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface MemberMapper {
    long countByExample(MemberExample example);

    int deleteByExample(MemberExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(Member record);

    int insertSelective(Member record);

    List<Member> selectByExampleWithRowbounds(MemberExample example, RowBounds rowBounds);

    List<Member> selectByExample(MemberExample example);

    Member selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") Member record, @Param("example") MemberExample example);

    int updateByExample(@Param("record") Member record, @Param("example") MemberExample example);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);
}