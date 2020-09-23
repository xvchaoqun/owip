package persistence.oa;

import domain.oa.OaGridParty;
import domain.oa.OaGridPartyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OaGridPartyMapper {
    long countByExample(OaGridPartyExample example);

    int deleteByExample(OaGridPartyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OaGridParty record);

    int insertSelective(OaGridParty record);

    List<OaGridParty> selectByExampleWithRowbounds(OaGridPartyExample example, RowBounds rowBounds);

    List<OaGridParty> selectByExample(OaGridPartyExample example);

    OaGridParty selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OaGridParty record, @Param("example") OaGridPartyExample example);

    int updateByExample(@Param("record") OaGridParty record, @Param("example") OaGridPartyExample example);

    int updateByPrimaryKeySelective(OaGridParty record);

    int updateByPrimaryKey(OaGridParty record);
}