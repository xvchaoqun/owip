package persistence.party;

import domain.party.PartyEva;
import domain.party.PartyEvaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PartyEvaMapper {
    long countByExample(PartyEvaExample example);

    int deleteByExample(PartyEvaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PartyEva record);

    int insertSelective(PartyEva record);

    List<PartyEva> selectByExampleWithRowbounds(PartyEvaExample example, RowBounds rowBounds);

    List<PartyEva> selectByExample(PartyEvaExample example);

    PartyEva selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PartyEva record, @Param("example") PartyEvaExample example);

    int updateByExample(@Param("record") PartyEva record, @Param("example") PartyEvaExample example);

    int updateByPrimaryKeySelective(PartyEva record);

    int updateByPrimaryKey(PartyEva record);
}