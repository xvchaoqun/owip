package persistence.dp;

import domain.dp.DpPartyView;
import domain.dp.DpPartyViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DpPartyViewMapper {
    long countByExample(DpPartyViewExample example);

    int deleteByExample(DpPartyViewExample example);

    int insert(DpPartyView record);

    int insertSelective(DpPartyView record);

    List<DpPartyView> selectByExampleWithRowbounds(DpPartyViewExample example, RowBounds rowBounds);

    List<DpPartyView> selectByExample(DpPartyViewExample example);

    int updateByExampleSelective(@Param("record") DpPartyView record, @Param("example") DpPartyViewExample example);

    int updateByExample(@Param("record") DpPartyView record, @Param("example") DpPartyViewExample example);
}