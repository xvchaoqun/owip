package persistence.pcs;

import domain.pcs.PcsPartyView;
import domain.pcs.PcsPartyViewExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PcsPartyViewMapper {
    long countByExample(PcsPartyViewExample example);

    int deleteByExample(PcsPartyViewExample example);

    int insert(PcsPartyView record);

    int insertSelective(PcsPartyView record);

    List<PcsPartyView> selectByExampleWithRowbounds(PcsPartyViewExample example, RowBounds rowBounds);

    List<PcsPartyView> selectByExample(PcsPartyViewExample example);

    int updateByExampleSelective(@Param("record") PcsPartyView record, @Param("example") PcsPartyViewExample example);

    int updateByExample(@Param("record") PcsPartyView record, @Param("example") PcsPartyViewExample example);
}