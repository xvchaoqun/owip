package persistence.pm;

import domain.pm.PmQuarterBranch;
import domain.pm.PmQuarterBranchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmQuarterBranchMapper {
    long countByExample(PmQuarterBranchExample example);

    int deleteByExample(PmQuarterBranchExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmQuarterBranch record);

    int insertSelective(PmQuarterBranch record);

    List<PmQuarterBranch> selectByExampleWithRowbounds(PmQuarterBranchExample example, RowBounds rowBounds);

    List<PmQuarterBranch> selectByExample(PmQuarterBranchExample example);

    PmQuarterBranch selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmQuarterBranch record, @Param("example") PmQuarterBranchExample example);

    int updateByExample(@Param("record") PmQuarterBranch record, @Param("example") PmQuarterBranchExample example);

    int updateByPrimaryKeySelective(PmQuarterBranch record);

    int updateByPrimaryKey(PmQuarterBranch record);
}