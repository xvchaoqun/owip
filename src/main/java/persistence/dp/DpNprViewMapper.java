package persistence.dp;

import domain.dp.DpNprView;
import domain.dp.DpNprViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DpNprViewMapper {
    long countByExample(DpNprViewExample example);

    int deleteByExample(DpNprViewExample example);

    int insert(DpNprView record);

    int insertSelective(DpNprView record);

    List<DpNprView> selectByExampleWithRowbounds(DpNprViewExample example, RowBounds rowBounds);

    List<DpNprView> selectByExample(DpNprViewExample example);

    int updateByExampleSelective(@Param("record") DpNprView record, @Param("example") DpNprViewExample example);

    int updateByExample(@Param("record") DpNprView record, @Param("example") DpNprViewExample example);
}