package persistence.member;

import domain.member.MemberInModify;
import domain.member.MemberInModifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MemberInModifyMapper {
    long countByExample(MemberInModifyExample example);

    int deleteByExample(MemberInModifyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberInModify record);

    int insertSelective(MemberInModify record);

    List<MemberInModify> selectByExampleWithRowbounds(MemberInModifyExample example, RowBounds rowBounds);

    List<MemberInModify> selectByExample(MemberInModifyExample example);

    MemberInModify selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberInModify record, @Param("example") MemberInModifyExample example);

    int updateByExample(@Param("record") MemberInModify record, @Param("example") MemberInModifyExample example);

    int updateByPrimaryKeySelective(MemberInModify record);

    int updateByPrimaryKey(MemberInModify record);
}