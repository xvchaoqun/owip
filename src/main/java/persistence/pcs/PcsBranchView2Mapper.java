package persistence.pcs;

import domain.pcs.PcsBranchView2;
import domain.pcs.PcsBranchView2Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsBranchView2Mapper {
    long countByExample(PcsBranchView2Example example);

    int deleteByExample(PcsBranchView2Example example);

    int insert(PcsBranchView2 record);

    int insertSelective(PcsBranchView2 record);

    List<PcsBranchView2> selectByExampleWithRowbounds(PcsBranchView2Example example, RowBounds rowBounds);

    List<PcsBranchView2> selectByExample(PcsBranchView2Example example);

    int updateByExampleSelective(@Param("record") PcsBranchView2 record, @Param("example") PcsBranchView2Example example);

    int updateByExample(@Param("record") PcsBranchView2 record, @Param("example") PcsBranchView2Example example);
}