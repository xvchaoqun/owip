package persistence.dp;

import domain.dp.DpMember;
import domain.dp.DpMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DpMemberMapper {
    long countByExample(DpMemberExample example);

    int deleteByExample(DpMemberExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(DpMember record);

    int insertSelective(DpMember record);

    List<DpMember> selectByExampleWithRowbounds(DpMemberExample example, RowBounds rowBounds);

    List<DpMember> selectByExample(DpMemberExample example);

    DpMember selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") DpMember record, @Param("example") DpMemberExample example);

    int updateByExample(@Param("record") DpMember record, @Param("example") DpMemberExample example);

    int updateByPrimaryKeySelective(DpMember record);

    int updateByPrimaryKey(DpMember record);
}