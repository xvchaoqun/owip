package persistence.party;

import domain.party.OrgAdminView;
import domain.party.OrgAdminViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface OrgAdminViewMapper {
    long countByExample(OrgAdminViewExample example);

    List<OrgAdminView> selectByExampleWithRowbounds(OrgAdminViewExample example, RowBounds rowBounds);

    List<OrgAdminView> selectByExample(OrgAdminViewExample example);
}