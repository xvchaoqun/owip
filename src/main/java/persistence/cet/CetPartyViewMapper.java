package persistence.cet;

import domain.cet.CetPartyView;
import domain.cet.CetPartyViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetPartyViewMapper {
    long countByExample(CetPartyViewExample example);

    int deleteByExample(CetPartyViewExample example);

    int insert(CetPartyView record);

    int insertSelective(CetPartyView record);

    List<CetPartyView> selectByExampleWithRowbounds(CetPartyViewExample example, RowBounds rowBounds);

    List<CetPartyView> selectByExample(CetPartyViewExample example);

    int updateByExampleSelective(@Param("record") CetPartyView record, @Param("example") CetPartyViewExample example);

    int updateByExample(@Param("record") CetPartyView record, @Param("example") CetPartyViewExample example);
}