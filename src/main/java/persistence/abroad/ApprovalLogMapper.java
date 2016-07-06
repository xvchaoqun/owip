package persistence.abroad;

import domain.abroad.ApprovalLog;
import domain.abroad.ApprovalLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ApprovalLogMapper {
    int countByExample(ApprovalLogExample example);

    int deleteByExample(ApprovalLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApprovalLog record);

    int insertSelective(ApprovalLog record);

    List<ApprovalLog> selectByExampleWithRowbounds(ApprovalLogExample example, RowBounds rowBounds);

    List<ApprovalLog> selectByExample(ApprovalLogExample example);

    ApprovalLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApprovalLog record, @Param("example") ApprovalLogExample example);

    int updateByExample(@Param("record") ApprovalLog record, @Param("example") ApprovalLogExample example);

    int updateByPrimaryKeySelective(ApprovalLog record);

    int updateByPrimaryKey(ApprovalLog record);
}