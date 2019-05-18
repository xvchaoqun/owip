package persistence.party;

import domain.party.OrganizerGroupUnit;
import domain.party.OrganizerGroupUnitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OrganizerGroupUnitMapper {
    long countByExample(OrganizerGroupUnitExample example);

    int deleteByExample(OrganizerGroupUnitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrganizerGroupUnit record);

    int insertSelective(OrganizerGroupUnit record);

    List<OrganizerGroupUnit> selectByExampleWithRowbounds(OrganizerGroupUnitExample example, RowBounds rowBounds);

    List<OrganizerGroupUnit> selectByExample(OrganizerGroupUnitExample example);

    OrganizerGroupUnit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrganizerGroupUnit record, @Param("example") OrganizerGroupUnitExample example);

    int updateByExample(@Param("record") OrganizerGroupUnit record, @Param("example") OrganizerGroupUnitExample example);

    int updateByPrimaryKeySelective(OrganizerGroupUnit record);

    int updateByPrimaryKey(OrganizerGroupUnit record);
}