package persistence.party;

import domain.party.OrganizerGroup;
import domain.party.OrganizerGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OrganizerGroupMapper {
    long countByExample(OrganizerGroupExample example);

    int deleteByExample(OrganizerGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrganizerGroup record);

    int insertSelective(OrganizerGroup record);

    List<OrganizerGroup> selectByExampleWithRowbounds(OrganizerGroupExample example, RowBounds rowBounds);

    List<OrganizerGroup> selectByExample(OrganizerGroupExample example);

    OrganizerGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrganizerGroup record, @Param("example") OrganizerGroupExample example);

    int updateByExample(@Param("record") OrganizerGroup record, @Param("example") OrganizerGroupExample example);

    int updateByPrimaryKeySelective(OrganizerGroup record);

    int updateByPrimaryKey(OrganizerGroup record);
}