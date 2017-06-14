package persistence.sys;

import domain.sys.SysApprovalLog;
import domain.sys.SysApprovalLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SysApprovalLogMapper {
    long countByExample(SysApprovalLogExample example);

    int deleteByExample(SysApprovalLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysApprovalLog record);

    int insertSelective(SysApprovalLog record);

    List<SysApprovalLog> selectByExampleWithBLOBsWithRowbounds(SysApprovalLogExample example, RowBounds rowBounds);

    List<SysApprovalLog> selectByExampleWithBLOBs(SysApprovalLogExample example);

    List<SysApprovalLog> selectByExampleWithRowbounds(SysApprovalLogExample example, RowBounds rowBounds);

    List<SysApprovalLog> selectByExample(SysApprovalLogExample example);

    SysApprovalLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysApprovalLog record, @Param("example") SysApprovalLogExample example);

    int updateByExampleWithBLOBs(@Param("record") SysApprovalLog record, @Param("example") SysApprovalLogExample example);

    int updateByExample(@Param("record") SysApprovalLog record, @Param("example") SysApprovalLogExample example);

    int updateByPrimaryKeySelective(SysApprovalLog record);

    int updateByPrimaryKeyWithBLOBs(SysApprovalLog record);

    int updateByPrimaryKey(SysApprovalLog record);
}