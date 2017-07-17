package persistence.sys;

import domain.sys.SysUserView;
import domain.sys.SysUserViewExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface SysUserViewMapper {
    int countByExample(SysUserViewExample example);

    int deleteByExample(SysUserViewExample example);

    int insert(SysUserView record);

    int insertSelective(SysUserView record);

    List<SysUserView> selectByExampleWithRowbounds(SysUserViewExample example, RowBounds rowBounds);

    List<SysUserView> selectByExample(SysUserViewExample example);

    int updateByExampleSelective(@Param("record") SysUserView record, @Param("example") SysUserViewExample example);

    int updateByExample(@Param("record") SysUserView record, @Param("example") SysUserViewExample example);
}