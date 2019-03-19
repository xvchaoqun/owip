package persistence.sys;

import domain.sys.SysProperty;
import domain.sys.SysPropertyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SysPropertyMapper {
    long countByExample(SysPropertyExample example);

    int deleteByExample(SysPropertyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysProperty record);

    int insertSelective(SysProperty record);

    List<SysProperty> selectByExampleWithRowbounds(SysPropertyExample example, RowBounds rowBounds);

    List<SysProperty> selectByExample(SysPropertyExample example);

    SysProperty selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysProperty record, @Param("example") SysPropertyExample example);

    int updateByExample(@Param("record") SysProperty record, @Param("example") SysPropertyExample example);

    int updateByPrimaryKeySelective(SysProperty record);

    int updateByPrimaryKey(SysProperty record);
}