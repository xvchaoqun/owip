package persistence.cis;

import domain.cis.CisInspectObjView;
import domain.cis.CisInspectObjViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CisInspectObjViewMapper {
    int countByExample(CisInspectObjViewExample example);

    List<CisInspectObjView> selectByExampleWithBLOBsWithRowbounds(CisInspectObjViewExample example, RowBounds rowBounds);

    List<CisInspectObjView> selectByExampleWithBLOBs(CisInspectObjViewExample example);

    List<CisInspectObjView> selectByExampleWithRowbounds(CisInspectObjViewExample example, RowBounds rowBounds);

    List<CisInspectObjView> selectByExample(CisInspectObjViewExample example);
}