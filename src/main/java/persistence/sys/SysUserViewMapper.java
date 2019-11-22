package persistence.sys;

import domain.sys.SysUserView;
import domain.sys.SysUserViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface SysUserViewMapper {
    long countByExample(SysUserViewExample example);

    List<SysUserView> selectByExampleWithBLOBsWithRowbounds(SysUserViewExample example, RowBounds rowBounds);

    List<SysUserView> selectByExampleWithBLOBs(SysUserViewExample example);

    List<SysUserView> selectByExampleWithRowbounds(SysUserViewExample example, RowBounds rowBounds);

    List<SysUserView> selectByExample(SysUserViewExample example);
}