package persistence.sys;

import domain.sys.SysUserView;
import domain.sys.SysUserViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface SysUserViewMapper {
    long countByExample(SysUserViewExample example);

    List<SysUserView> selectByExampleWithRowbounds(SysUserViewExample example, RowBounds rowBounds);

    List<SysUserView> selectByExample(SysUserViewExample example);
}