package persistence.sys;

import domain.sys.SysOnlineStatic;
import domain.sys.SysOnlineStaticExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface SysOnlineStaticMapper {
    int countByExample(SysOnlineStaticExample example);

    int deleteByExample(SysOnlineStaticExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysOnlineStatic record);

    int insertSelective(SysOnlineStatic record);

    List<SysOnlineStatic> selectByExampleWithRowbounds(SysOnlineStaticExample example, RowBounds rowBounds);

    List<SysOnlineStatic> selectByExample(SysOnlineStaticExample example);

    SysOnlineStatic selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysOnlineStatic record, @Param("example") SysOnlineStaticExample example);

    int updateByExample(@Param("record") SysOnlineStatic record, @Param("example") SysOnlineStaticExample example);

    int updateByPrimaryKeySelective(SysOnlineStatic record);

    int updateByPrimaryKey(SysOnlineStatic record);
}