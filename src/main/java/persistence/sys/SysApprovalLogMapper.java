package persistence.sys;

import domain.sys.SysApprovalLog;
import domain.sys.SysApprovalLogExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface SysApprovalLogMapper {
    long countByExample(SysApprovalLogExample example);

    int deleteByExample(SysApprovalLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysApprovalLog record);

    int insertSelective(SysApprovalLog record);

    List<SysApprovalLog> selectByExampleWithRowbounds(SysApprovalLogExample example, RowBounds rowBounds);

    List<SysApprovalLog> selectByExample(SysApprovalLogExample example);

    SysApprovalLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysApprovalLog record, @Param("example") SysApprovalLogExample example);

    int updateByExample(@Param("record") SysApprovalLog record, @Param("example") SysApprovalLogExample example);

    int updateByPrimaryKeySelective(SysApprovalLog record);

    int updateByPrimaryKey(SysApprovalLog record);
}