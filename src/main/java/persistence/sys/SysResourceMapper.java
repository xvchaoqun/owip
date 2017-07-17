package persistence.sys;

import domain.sys.SysResource;
import domain.sys.SysResourceExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface SysResourceMapper {
    long countByExample(SysResourceExample example);

    int deleteByExample(SysResourceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysResource record);

    int insertSelective(SysResource record);

    List<SysResource> selectByExampleWithRowbounds(SysResourceExample example, RowBounds rowBounds);

    List<SysResource> selectByExample(SysResourceExample example);

    SysResource selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysResource record, @Param("example") SysResourceExample example);

    int updateByExample(@Param("record") SysResource record, @Param("example") SysResourceExample example);

    int updateByPrimaryKeySelective(SysResource record);

    int updateByPrimaryKey(SysResource record);
}