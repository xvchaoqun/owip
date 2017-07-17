package persistence.party;

import domain.party.Branch;
import domain.party.BranchExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface BranchMapper {
    int countByExample(BranchExample example);

    int deleteByExample(BranchExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Branch record);

    int insertSelective(Branch record);

    List<Branch> selectByExampleWithRowbounds(BranchExample example, RowBounds rowBounds);

    List<Branch> selectByExample(BranchExample example);

    Branch selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Branch record, @Param("example") BranchExample example);

    int updateByExample(@Param("record") Branch record, @Param("example") BranchExample example);

    int updateByPrimaryKeySelective(Branch record);

    int updateByPrimaryKey(Branch record);
}