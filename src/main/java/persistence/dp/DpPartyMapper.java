package persistence.dp;

import domain.dp.DpParty;
import domain.dp.DpPartyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DpPartyMapper {
    long countByExample(DpPartyExample example);

    int deleteByExample(DpPartyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DpParty record);

    int insertSelective(DpParty record);

    List<DpParty> selectByExampleWithRowbounds(DpPartyExample example, RowBounds rowBounds);

    List<DpParty> selectByExample(DpPartyExample example);

    DpParty selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DpParty record, @Param("example") DpPartyExample example);

    int updateByExample(@Param("record") DpParty record, @Param("example") DpPartyExample example);

    int updateByPrimaryKeySelective(DpParty record);

    int updateByPrimaryKey(DpParty record);
}