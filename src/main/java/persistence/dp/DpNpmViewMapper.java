package persistence.dp;

import domain.dp.DpNpmView;
import domain.dp.DpNpmViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DpNpmViewMapper {
    long countByExample(DpNpmViewExample example);

    int deleteByExample(DpNpmViewExample example);

    int insert(DpNpmView record);

    int insertSelective(DpNpmView record);

    List<DpNpmView> selectByExampleWithRowbounds(DpNpmViewExample example, RowBounds rowBounds);

    List<DpNpmView> selectByExample(DpNpmViewExample example);

    int updateByExampleSelective(@Param("record") DpNpmView record, @Param("example") DpNpmViewExample example);

    int updateByExample(@Param("record") DpNpmView record, @Param("example") DpNpmViewExample example);
}