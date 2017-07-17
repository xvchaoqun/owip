package persistence.base;

import domain.base.Location;
import domain.base.LocationExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface LocationMapper {
    int countByExample(LocationExample example);

    int deleteByExample(LocationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Location record);

    int insertSelective(Location record);

    List<Location> selectByExampleWithRowbounds(LocationExample example, RowBounds rowBounds);

    List<Location> selectByExample(LocationExample example);

    Location selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Location record, @Param("example") LocationExample example);

    int updateByExample(@Param("record") Location record, @Param("example") LocationExample example);

    int updateByPrimaryKeySelective(Location record);

    int updateByPrimaryKey(Location record);
}