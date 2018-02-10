package persistence.pmd;

import domain.pmd.PmdBranch;
import domain.pmd.PmdBranchExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PmdBranchMapper {
    long countByExample(PmdBranchExample example);

    int deleteByExample(PmdBranchExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdBranch record);

    int insertSelective(PmdBranch record);

    List<PmdBranch> selectByExampleWithRowbounds(PmdBranchExample example, RowBounds rowBounds);

    List<PmdBranch> selectByExample(PmdBranchExample example);

    PmdBranch selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdBranch record, @Param("example") PmdBranchExample example);

    int updateByExample(@Param("record") PmdBranch record, @Param("example") PmdBranchExample example);

    int updateByPrimaryKeySelective(PmdBranch record);

    int updateByPrimaryKey(PmdBranch record);
}