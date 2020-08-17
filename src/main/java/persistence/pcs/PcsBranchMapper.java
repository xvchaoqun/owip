package persistence.pcs;

import domain.pcs.PcsBranch;
import domain.pcs.PcsBranchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsBranchMapper {
    long countByExample(PcsBranchExample example);

    int deleteByExample(PcsBranchExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsBranch record);

    int insertSelective(PcsBranch record);

    List<PcsBranch> selectByExampleWithRowbounds(PcsBranchExample example, RowBounds rowBounds);

    List<PcsBranch> selectByExample(PcsBranchExample example);

    PcsBranch selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsBranch record, @Param("example") PcsBranchExample example);

    int updateByExample(@Param("record") PcsBranch record, @Param("example") PcsBranchExample example);

    int updateByPrimaryKeySelective(PcsBranch record);

    int updateByPrimaryKey(PcsBranch record);
}