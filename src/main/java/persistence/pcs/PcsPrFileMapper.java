package persistence.pcs;

import domain.pcs.PcsPrFile;
import domain.pcs.PcsPrFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsPrFileMapper {
    long countByExample(PcsPrFileExample example);

    int deleteByExample(PcsPrFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsPrFile record);

    int insertSelective(PcsPrFile record);

    List<PcsPrFile> selectByExampleWithRowbounds(PcsPrFileExample example, RowBounds rowBounds);

    List<PcsPrFile> selectByExample(PcsPrFileExample example);

    PcsPrFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsPrFile record, @Param("example") PcsPrFileExample example);

    int updateByExample(@Param("record") PcsPrFile record, @Param("example") PcsPrFileExample example);

    int updateByPrimaryKeySelective(PcsPrFile record);

    int updateByPrimaryKey(PcsPrFile record);
}