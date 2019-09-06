package persistence.dp;

import domain.dp.DpPrCmView;
import domain.dp.DpPrCmViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DpPrCmViewMapper {
    long countByExample(DpPrCmViewExample example);

    int deleteByExample(DpPrCmViewExample example);

    int insert(DpPrCmView record);

    int insertSelective(DpPrCmView record);

    List<DpPrCmView> selectByExampleWithRowbounds(DpPrCmViewExample example, RowBounds rowBounds);

    List<DpPrCmView> selectByExample(DpPrCmViewExample example);

    int updateByExampleSelective(@Param("record") DpPrCmView record, @Param("example") DpPrCmViewExample example);

    int updateByExample(@Param("record") DpPrCmView record, @Param("example") DpPrCmViewExample example);
}