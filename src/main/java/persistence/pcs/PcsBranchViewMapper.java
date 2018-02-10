package persistence.pcs;

import domain.pcs.PcsBranchView;
import domain.pcs.PcsBranchViewExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PcsBranchViewMapper {
    long countByExample(PcsBranchViewExample example);

    int deleteByExample(PcsBranchViewExample example);

    int insert(PcsBranchView record);

    int insertSelective(PcsBranchView record);

    List<PcsBranchView> selectByExampleWithRowbounds(PcsBranchViewExample example, RowBounds rowBounds);

    List<PcsBranchView> selectByExample(PcsBranchViewExample example);

    int updateByExampleSelective(@Param("record") PcsBranchView record, @Param("example") PcsBranchViewExample example);

    int updateByExample(@Param("record") PcsBranchView record, @Param("example") PcsBranchViewExample example);
}