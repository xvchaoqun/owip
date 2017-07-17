package persistence.cis;

import domain.cis.CisInspectorView;
import domain.cis.CisInspectorViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CisInspectorViewMapper {
    int countByExample(CisInspectorViewExample example);

    List<CisInspectorView> selectByExampleWithRowbounds(CisInspectorViewExample example, RowBounds rowBounds);

    List<CisInspectorView> selectByExample(CisInspectorViewExample example);

    CisInspectorView selectByPrimaryKey(Integer id);
}