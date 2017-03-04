package persistence.party;

import domain.party.BranchTransferLog;
import domain.party.BranchTransferLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface BranchTransferLogMapper {
    int countByExample(BranchTransferLogExample example);

    int deleteByExample(BranchTransferLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BranchTransferLog record);

    int insertSelective(BranchTransferLog record);

    List<BranchTransferLog> selectByExampleWithRowbounds(BranchTransferLogExample example, RowBounds rowBounds);

    List<BranchTransferLog> selectByExample(BranchTransferLogExample example);

    BranchTransferLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BranchTransferLog record, @Param("example") BranchTransferLogExample example);

    int updateByExample(@Param("record") BranchTransferLog record, @Param("example") BranchTransferLogExample example);

    int updateByPrimaryKeySelective(BranchTransferLog record);

    int updateByPrimaryKey(BranchTransferLog record);
}