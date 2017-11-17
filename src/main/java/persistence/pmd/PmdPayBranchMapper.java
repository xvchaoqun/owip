package persistence.pmd;

import domain.pmd.PmdPayBranch;
import domain.pmd.PmdPayBranchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmdPayBranchMapper {
    long countByExample(PmdPayBranchExample example);

    int deleteByExample(PmdPayBranchExample example);

    int deleteByPrimaryKey(Integer branchId);

    int insert(PmdPayBranch record);

    int insertSelective(PmdPayBranch record);

    List<PmdPayBranch> selectByExampleWithRowbounds(PmdPayBranchExample example, RowBounds rowBounds);

    List<PmdPayBranch> selectByExample(PmdPayBranchExample example);

    PmdPayBranch selectByPrimaryKey(Integer branchId);

    int updateByExampleSelective(@Param("record") PmdPayBranch record, @Param("example") PmdPayBranchExample example);

    int updateByExample(@Param("record") PmdPayBranch record, @Param("example") PmdPayBranchExample example);

    int updateByPrimaryKeySelective(PmdPayBranch record);

    int updateByPrimaryKey(PmdPayBranch record);
}