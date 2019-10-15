package persistence.party;

import domain.party.PartyPost;
import domain.party.PartyPostExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PartyPostMapper {
    long countByExample(PartyPostExample example);

    int deleteByExample(PartyPostExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PartyPost record);

    int insertSelective(PartyPost record);

    List<PartyPost> selectByExampleWithRowbounds(PartyPostExample example, RowBounds rowBounds);

    List<PartyPost> selectByExample(PartyPostExample example);

    PartyPost selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PartyPost record, @Param("example") PartyPostExample example);

    int updateByExample(@Param("record") PartyPost record, @Param("example") PartyPostExample example);

    int updateByPrimaryKeySelective(PartyPost record);

    int updateByPrimaryKey(PartyPost record);
}