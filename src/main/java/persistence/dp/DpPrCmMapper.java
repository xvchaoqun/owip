package persistence.dp;

import domain.dp.DpPrCm;
import domain.dp.DpPrCmExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DpPrCmMapper {
    long countByExample(DpPrCmExample example);

    int deleteByExample(DpPrCmExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DpPrCm record);

    int insertSelective(DpPrCm record);

    List<DpPrCm> selectByExampleWithRowbounds(DpPrCmExample example, RowBounds rowBounds);

    List<DpPrCm> selectByExample(DpPrCmExample example);

    DpPrCm selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DpPrCm record, @Param("example") DpPrCmExample example);

    int updateByExample(@Param("record") DpPrCm record, @Param("example") DpPrCmExample example);

    int updateByPrimaryKeySelective(DpPrCm record);

    int updateByPrimaryKey(DpPrCm record);
}