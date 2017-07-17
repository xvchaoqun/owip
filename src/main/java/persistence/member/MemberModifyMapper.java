package persistence.member;

import domain.member.MemberModify;
import domain.member.MemberModifyExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface MemberModifyMapper {
    int countByExample(MemberModifyExample example);

    int deleteByExample(MemberModifyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberModify record);

    int insertSelective(MemberModify record);

    List<MemberModify> selectByExampleWithRowbounds(MemberModifyExample example, RowBounds rowBounds);

    List<MemberModify> selectByExample(MemberModifyExample example);

    MemberModify selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberModify record, @Param("example") MemberModifyExample example);

    int updateByExample(@Param("record") MemberModify record, @Param("example") MemberModifyExample example);

    int updateByPrimaryKeySelective(MemberModify record);

    int updateByPrimaryKey(MemberModify record);
}