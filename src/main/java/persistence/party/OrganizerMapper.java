package persistence.party;

import domain.party.Organizer;
import domain.party.OrganizerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OrganizerMapper {
    long countByExample(OrganizerExample example);

    int deleteByExample(OrganizerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Organizer record);

    int insertSelective(Organizer record);

    List<Organizer> selectByExampleWithRowbounds(OrganizerExample example, RowBounds rowBounds);

    List<Organizer> selectByExample(OrganizerExample example);

    Organizer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Organizer record, @Param("example") OrganizerExample example);

    int updateByExample(@Param("record") Organizer record, @Param("example") OrganizerExample example);

    int updateByPrimaryKeySelective(Organizer record);

    int updateByPrimaryKey(Organizer record);
}