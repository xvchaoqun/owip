package persistence.dp;

import domain.dp.DpPartyMemberView;
import domain.dp.DpPartyMemberViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DpPartyMemberViewMapper {
    long countByExample(DpPartyMemberViewExample example);

    int deleteByExample(DpPartyMemberViewExample example);

    int insert(DpPartyMemberView record);

    int insertSelective(DpPartyMemberView record);

    List<DpPartyMemberView> selectByExampleWithRowbounds(DpPartyMemberViewExample example, RowBounds rowBounds);

    List<DpPartyMemberView> selectByExample(DpPartyMemberViewExample example);

    int updateByExampleSelective(@Param("record") DpPartyMemberView record, @Param("example") DpPartyMemberViewExample example);

    int updateByExample(@Param("record") DpPartyMemberView record, @Param("example") DpPartyMemberViewExample example);
}