package persistence.member;

import domain.member.ApplyApprovalLog;
import domain.member.ApplyApprovalLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ApplyApprovalLogMapper {
    int countByExample(ApplyApprovalLogExample example);

    int deleteByExample(ApplyApprovalLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApplyApprovalLog record);

    int insertSelective(ApplyApprovalLog record);

    List<ApplyApprovalLog> selectByExampleWithRowbounds(ApplyApprovalLogExample example, RowBounds rowBounds);

    List<ApplyApprovalLog> selectByExample(ApplyApprovalLogExample example);

    ApplyApprovalLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApplyApprovalLog record, @Param("example") ApplyApprovalLogExample example);

    int updateByExample(@Param("record") ApplyApprovalLog record, @Param("example") ApplyApprovalLogExample example);

    int updateByPrimaryKeySelective(ApplyApprovalLog record);

    int updateByPrimaryKey(ApplyApprovalLog record);
}