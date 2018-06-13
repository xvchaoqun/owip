package persistence.sys;

import domain.sys.SysSync;
import domain.sys.SysSyncExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SysSyncMapper {
    long countByExample(SysSyncExample example);

    int deleteByExample(SysSyncExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysSync record);

    int insertSelective(SysSync record);

    List<SysSync> selectByExampleWithRowbounds(SysSyncExample example, RowBounds rowBounds);

    List<SysSync> selectByExample(SysSyncExample example);

    SysSync selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysSync record, @Param("example") SysSyncExample example);

    int updateByExample(@Param("record") SysSync record, @Param("example") SysSyncExample example);

    int updateByPrimaryKeySelective(SysSync record);

    int updateByPrimaryKey(SysSync record);
}