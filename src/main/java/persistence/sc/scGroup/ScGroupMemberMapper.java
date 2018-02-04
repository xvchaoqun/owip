package persistence.sc.scGroup;

import domain.sc.scGroup.ScGroupMember;
import domain.sc.scGroup.ScGroupMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScGroupMemberMapper {
    long countByExample(ScGroupMemberExample example);

    int deleteByExample(ScGroupMemberExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScGroupMember record);

    int insertSelective(ScGroupMember record);

    List<ScGroupMember> selectByExampleWithRowbounds(ScGroupMemberExample example, RowBounds rowBounds);

    List<ScGroupMember> selectByExample(ScGroupMemberExample example);

    ScGroupMember selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScGroupMember record, @Param("example") ScGroupMemberExample example);

    int updateByExample(@Param("record") ScGroupMember record, @Param("example") ScGroupMemberExample example);

    int updateByPrimaryKeySelective(ScGroupMember record);

    int updateByPrimaryKey(ScGroupMember record);
}