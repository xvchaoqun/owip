package persistence.party;

import domain.party.PartyPublicUser;
import domain.party.PartyPublicUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PartyPublicUserMapper {
    long countByExample(PartyPublicUserExample example);

    int deleteByExample(PartyPublicUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PartyPublicUser record);

    int insertSelective(PartyPublicUser record);

    List<PartyPublicUser> selectByExampleWithRowbounds(PartyPublicUserExample example, RowBounds rowBounds);

    List<PartyPublicUser> selectByExample(PartyPublicUserExample example);

    PartyPublicUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PartyPublicUser record, @Param("example") PartyPublicUserExample example);

    int updateByExample(@Param("record") PartyPublicUser record, @Param("example") PartyPublicUserExample example);

    int updateByPrimaryKeySelective(PartyPublicUser record);

    int updateByPrimaryKey(PartyPublicUser record);
}