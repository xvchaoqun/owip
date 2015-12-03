package persistence;

import domain.SysUserSync;
import domain.SysUserSyncExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

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