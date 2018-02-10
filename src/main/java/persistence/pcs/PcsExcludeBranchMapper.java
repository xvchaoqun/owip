package persistence.pcs;

import domain.pcs.PcsExcludeBranch;
import domain.pcs.PcsExcludeBranchExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PcsExcludeBranchMapper {
    long countByExample(PcsExcludeBranchExample example);

    int deleteByExample(PcsExcludeBranchExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsExcludeBranch record);

    int insertSelective(PcsExcludeBranch record);

    List<PcsExcludeBranch> selectByExampleWithRowbounds(PcsExcludeBranchExample example, RowBounds rowBounds);

    List<PcsExcludeBranch> selectByExample(PcsExcludeBranchExample example);

    PcsExcludeBranch selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsExcludeBranch record, @Param("example") PcsExcludeBranchExample example);

    int updateByExample(@Param("record") PcsExcludeBranch record, @Param("example") PcsExcludeBranchExample example);

    int updateByPrimaryKeySelective(PcsExcludeBranch record);

    int updateByPrimaryKey(PcsExcludeBranch record);
}