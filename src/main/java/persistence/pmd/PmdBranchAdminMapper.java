package persistence.pmd;

import domain.pmd.PmdBranchAdmin;
import domain.pmd.PmdBranchAdminExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PmdBranchAdminMapper {
    long countByExample(PmdBranchAdminExample example);

    int deleteByExample(PmdBranchAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdBranchAdmin record);

    int insertSelective(PmdBranchAdmin record);

    List<PmdBranchAdmin> selectByExampleWithRowbounds(PmdBranchAdminExample example, RowBounds rowBounds);

    List<PmdBranchAdmin> selectByExample(PmdBranchAdminExample example);

    PmdBranchAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdBranchAdmin record, @Param("example") PmdBranchAdminExample example);

    int updateByExample(@Param("record") PmdBranchAdmin record, @Param("example") PmdBranchAdminExample example);

    int updateByPrimaryKeySelective(PmdBranchAdmin record);

    int updateByPrimaryKey(PmdBranchAdmin record);
}