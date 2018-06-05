package persistence.cla;

import domain.cla.ClaApprovalLog;
import domain.cla.ClaApprovalLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ClaApprovalLogMapper {
    long countByExample(ClaApprovalLogExample example);

    int deleteByExample(ClaApprovalLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ClaApprovalLog record);

    int insertSelective(ClaApprovalLog record);

    List<ClaApprovalLog> selectByExampleWithRowbounds(ClaApprovalLogExample example, RowBounds rowBounds);

    List<ClaApprovalLog> selectByExample(ClaApprovalLogExample example);

    ClaApprovalLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ClaApprovalLog record, @Param("example") ClaApprovalLogExample example);

    int updateByExample(@Param("record") ClaApprovalLog record, @Param("example") ClaApprovalLogExample example);

    int updateByPrimaryKeySelective(ClaApprovalLog record);

    int updateByPrimaryKey(ClaApprovalLog record);
}