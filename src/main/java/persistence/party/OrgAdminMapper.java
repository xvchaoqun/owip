package persistence.party;

import domain.party.OrgAdmin;
import domain.party.OrgAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OrgAdminMapper {
    int countByExample(OrgAdminExample example);

    int deleteByExample(OrgAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrgAdmin record);

    int insertSelective(OrgAdmin record);

    List<OrgAdmin> selectByExampleWithRowbounds(OrgAdminExample example, RowBounds rowBounds);

    List<OrgAdmin> selectByExample(OrgAdminExample example);

    OrgAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrgAdmin record, @Param("example") OrgAdminExample example);

    int updateByExample(@Param("record") OrgAdmin record, @Param("example") OrgAdminExample example);

    int updateByPrimaryKeySelective(OrgAdmin record);

    int updateByPrimaryKey(OrgAdmin record);
}