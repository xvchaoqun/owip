package persistence.crs;

import domain.crs.CrsExpertView;
import domain.crs.CrsExpertViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CrsExpertViewMapper {
    long countByExample(CrsExpertViewExample example);

    List<CrsExpertView> selectByExampleWithRowbounds(CrsExpertViewExample example, RowBounds rowBounds);

    List<CrsExpertView> selectByExample(CrsExpertViewExample example);

    CrsExpertView selectByPrimaryKey(Integer id);
}