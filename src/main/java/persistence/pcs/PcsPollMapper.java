package persistence.pcs;

import domain.pcs.PcsPoll;
import domain.pcs.PcsPollExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsPollMapper {
    long countByExample(PcsPollExample example);

    int deleteByExample(PcsPollExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsPoll record);

    int insertSelective(PcsPoll record);

    List<PcsPoll> selectByExampleWithRowbounds(PcsPollExample example, RowBounds rowBounds);

    List<PcsPoll> selectByExample(PcsPollExample example);

    PcsPoll selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsPoll record, @Param("example") PcsPollExample example);

    int updateByExample(@Param("record") PcsPoll record, @Param("example") PcsPollExample example);

    int updateByPrimaryKeySelective(PcsPoll record);

    int updateByPrimaryKey(PcsPoll record);
}