package persistence.party;

import domain.party.OrganizerGroupUser;
import domain.party.OrganizerGroupUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OrganizerGroupUserMapper {
    long countByExample(OrganizerGroupUserExample example);

    int deleteByExample(OrganizerGroupUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrganizerGroupUser record);

    int insertSelective(OrganizerGroupUser record);

    List<OrganizerGroupUser> selectByExampleWithRowbounds(OrganizerGroupUserExample example, RowBounds rowBounds);

    List<OrganizerGroupUser> selectByExample(OrganizerGroupUserExample example);

    OrganizerGroupUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrganizerGroupUser record, @Param("example") OrganizerGroupUserExample example);

    int updateByExample(@Param("record") OrganizerGroupUser record, @Param("example") OrganizerGroupUserExample example);

    int updateByPrimaryKeySelective(OrganizerGroupUser record);

    int updateByPrimaryKey(OrganizerGroupUser record);
}