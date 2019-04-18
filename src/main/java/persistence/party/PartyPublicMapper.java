package persistence.party;

import domain.party.PartyPublic;
import domain.party.PartyPublicExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PartyPublicMapper {
    long countByExample(PartyPublicExample example);

    int deleteByExample(PartyPublicExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PartyPublic record);

    int insertSelective(PartyPublic record);

    List<PartyPublic> selectByExampleWithRowbounds(PartyPublicExample example, RowBounds rowBounds);

    List<PartyPublic> selectByExample(PartyPublicExample example);

    PartyPublic selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PartyPublic record, @Param("example") PartyPublicExample example);

    int updateByExample(@Param("record") PartyPublic record, @Param("example") PartyPublicExample example);

    int updateByPrimaryKeySelective(PartyPublic record);

    int updateByPrimaryKey(PartyPublic record);
}