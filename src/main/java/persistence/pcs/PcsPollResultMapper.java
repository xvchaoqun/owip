package persistence.pcs;

import domain.pcs.PcsPollResult;
import domain.pcs.PcsPollResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsPollResultMapper {
    long countByExample(PcsPollResultExample example);

    int deleteByExample(PcsPollResultExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsPollResult record);

    int insertSelective(PcsPollResult record);

    List<PcsPollResult> selectByExampleWithRowbounds(PcsPollResultExample example, RowBounds rowBounds);

    List<PcsPollResult> selectByExample(PcsPollResultExample example);

    PcsPollResult selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsPollResult record, @Param("example") PcsPollResultExample example);

    int updateByExample(@Param("record") PcsPollResult record, @Param("example") PcsPollResultExample example);

    int updateByPrimaryKeySelective(PcsPollResult record);

    int updateByPrimaryKey(PcsPollResult record);
}