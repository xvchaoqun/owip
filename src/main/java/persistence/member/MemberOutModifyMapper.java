package persistence.member;

import domain.member.MemberOutModify;
import domain.member.MemberOutModifyExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface MemberOutModifyMapper {
    int countByExample(MemberOutModifyExample example);

    int deleteByExample(MemberOutModifyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberOutModify record);

    int insertSelective(MemberOutModify record);

    List<MemberOutModify> selectByExampleWithRowbounds(MemberOutModifyExample example, RowBounds rowBounds);

    List<MemberOutModify> selectByExample(MemberOutModifyExample example);

    MemberOutModify selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberOutModify record, @Param("example") MemberOutModifyExample example);

    int updateByExample(@Param("record") MemberOutModify record, @Param("example") MemberOutModifyExample example);

    int updateByPrimaryKeySelective(MemberOutModify record);

    int updateByPrimaryKey(MemberOutModify record);
}