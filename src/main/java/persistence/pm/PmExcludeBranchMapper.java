package persistence.pm;

import domain.pm.PmExcludeBranch;
import domain.pm.PmExcludeBranchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmExcludeBranchMapper {
    long countByExample(PmExcludeBranchExample example);

    int deleteByExample(PmExcludeBranchExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmExcludeBranch record);

    int insertSelective(PmExcludeBranch record);

    List<PmExcludeBranch> selectByExampleWithRowbounds(PmExcludeBranchExample example, RowBounds rowBounds);

    List<PmExcludeBranch> selectByExample(PmExcludeBranchExample example);

    PmExcludeBranch selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmExcludeBranch record, @Param("example") PmExcludeBranchExample example);

    int updateByExample(@Param("record") PmExcludeBranch record, @Param("example") PmExcludeBranchExample example);

    int updateByPrimaryKeySelective(PmExcludeBranch record);

    int updateByPrimaryKey(PmExcludeBranch record);
}