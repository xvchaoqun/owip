package persistence;

import domain.MemberModify;
import domain.MemberModifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

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