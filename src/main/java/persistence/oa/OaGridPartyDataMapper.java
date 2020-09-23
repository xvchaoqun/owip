package persistence.oa;

import domain.oa.OaGridPartyData;
import domain.oa.OaGridPartyDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OaGridPartyDataMapper {
    long countByExample(OaGridPartyDataExample example);

    int deleteByExample(OaGridPartyDataExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OaGridPartyData record);

    int insertSelective(OaGridPartyData record);

    List<OaGridPartyData> selectByExampleWithRowbounds(OaGridPartyDataExample example, RowBounds rowBounds);

    List<OaGridPartyData> selectByExample(OaGridPartyDataExample example);

    OaGridPartyData selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OaGridPartyData record, @Param("example") OaGridPartyDataExample example);

    int updateByExample(@Param("record") OaGridPartyData record, @Param("example") OaGridPartyDataExample example);

    int updateByPrimaryKeySelective(OaGridPartyData record);

    int updateByPrimaryKey(OaGridPartyData record);
}