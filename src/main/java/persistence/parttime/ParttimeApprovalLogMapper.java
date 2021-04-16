package persistence.parttime;

import domain.parttime.ParttimeApprovalLog;
import domain.parttime.ParttimeApprovalLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ParttimeApprovalLogMapper {
    long countByExample(ParttimeApprovalLogExample example);

    int deleteByExample(ParttimeApprovalLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ParttimeApprovalLog record);

    int insertSelective(ParttimeApprovalLog record);

    List<ParttimeApprovalLog> selectByExampleWithRowbounds(ParttimeApprovalLogExample example, RowBounds rowBounds);

    List<ParttimeApprovalLog> selectByExample(ParttimeApprovalLogExample example);

    ParttimeApprovalLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ParttimeApprovalLog record, @Param("example") ParttimeApprovalLogExample example);

    int updateByExample(@Param("record") ParttimeApprovalLog record, @Param("example") ParttimeApprovalLogExample example);

    int updateByPrimaryKeySelective(ParttimeApprovalLog record);

    int updateByPrimaryKey(ParttimeApprovalLog record);
}