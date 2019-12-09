package persistence.dp;

import domain.dp.DpOmView;
import domain.dp.DpOmViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DpOmViewMapper {
    long countByExample(DpOmViewExample example);

    int deleteByExample(DpOmViewExample example);

    int insert(DpOmView record);

    int insertSelective(DpOmView record);

    List<DpOmView> selectByExampleWithRowbounds(DpOmViewExample example, RowBounds rowBounds);

    List<DpOmView> selectByExample(DpOmViewExample example);

    int updateByExampleSelective(@Param("record") DpOmView record, @Param("example") DpOmViewExample example);

    int updateByExample(@Param("record") DpOmView record, @Param("example") DpOmViewExample example);
}