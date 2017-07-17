package persistence.sys;

import domain.sys.SysUserSync;
import domain.sys.SysUserSyncExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface SysUserSyncMapper {
    int countByExample(SysUserSyncExample example);

    int deleteByExample(SysUserSyncExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysUserSync record);

    int insertSelective(SysUserSync record);

    List<SysUserSync> selectByExampleWithRowbounds(SysUserSyncExample example, RowBounds rowBounds);

    List<SysUserSync> selectByExample(SysUserSyncExample example);

    SysUserSync selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysUserSync record, @Param("example") SysUserSyncExample example);

    int updateByExample(@Param("record") SysUserSync record, @Param("example") SysUserSyncExample example);

    int updateByPrimaryKeySelective(SysUserSync record);

    int updateByPrimaryKey(SysUserSync record);
}