package persistence.dp;

import domain.dp.DpPartyMemberGroup;
import domain.dp.DpPartyMemberGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DpPartyMemberGroupMapper {
    long countByExample(DpPartyMemberGroupExample example);

    int deleteByExample(DpPartyMemberGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DpPartyMemberGroup record);

    int insertSelective(DpPartyMemberGroup record);

    List<DpPartyMemberGroup> selectByExampleWithRowbounds(DpPartyMemberGroupExample example, RowBounds rowBounds);

    List<DpPartyMemberGroup> selectByExample(DpPartyMemberGroupExample example);

    DpPartyMemberGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DpPartyMemberGroup record, @Param("example") DpPartyMemberGroupExample example);

    int updateByExample(@Param("record") DpPartyMemberGroup record, @Param("example") DpPartyMemberGroupExample example);

    int updateByPrimaryKeySelective(DpPartyMemberGroup record);

    int updateByPrimaryKey(DpPartyMemberGroup record);
}