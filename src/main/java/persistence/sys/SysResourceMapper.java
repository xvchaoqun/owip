package persistence.sys;

import domain.sys.SysResource;
import domain.sys.SysResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SysResourceMapper {
    int countByExample(SysResourceExample example);

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