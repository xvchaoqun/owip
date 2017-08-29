package persistence.pcs;

import domain.pcs.PcsPrAllocate;
import domain.pcs.PcsPrAllocateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsPrAllocateMapper {
    long countByExample(PcsPrAllocateExample example);

    int deleteByExample(PcsPrAllocateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsPrAllocate record);

    int insertSelective(PcsPrAllocate record);

    List<PcsPrAllocate> selectByExampleWithRowbounds(PcsPrAllocateExample example, RowBounds rowBounds);

    List<PcsPrAllocate> selectByExample(PcsPrAllocateExample example);

    PcsPrAllocate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsPrAllocate record, @Param("example") PcsPrAllocateExample example);

    int updateByExample(@Param("record") PcsPrAllocate record, @Param("example") PcsPrAllocateExample example);

    int updateByPrimaryKeySelective(PcsPrAllocate record);

    int updateByPrimaryKey(PcsPrAllocate record);
}