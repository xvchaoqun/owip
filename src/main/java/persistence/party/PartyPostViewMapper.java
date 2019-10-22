package persistence.party;

import domain.party.PartyPostView;
import domain.party.PartyPostViewExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PartyPostViewMapper {
    long countByExample(PartyPostViewExample example);

    int deleteByExample(PartyPostViewExample example);

    int insert(PartyPostView record);

    int insertSelective(PartyPostView record);

    List<PartyPostView> selectByExampleWithRowbounds(PartyPostViewExample example, RowBounds rowBounds);

    List<PartyPostView> selectByExample(PartyPostViewExample example);

    int updateByExampleSelective(@Param("record") PartyPostView record, @Param("example") PartyPostViewExample example);

    int updateByExample(@Param("record") PartyPostView record, @Param("example") PartyPostViewExample example);
}