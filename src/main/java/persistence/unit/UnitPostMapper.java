package persistence.unit;

import domain.unit.UnitPost;
import domain.unit.UnitPostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UnitPostMapper {
    long countByExample(UnitPostExample example);

    int deleteByExample(UnitPostExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UnitPost record);

    int insertSelective(UnitPost record);

    List<UnitPost> selectByExampleWithRowbounds(UnitPostExample example, RowBounds rowBounds);

    List<UnitPost> selectByExample(UnitPostExample example);

    UnitPost selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UnitPost record, @Param("example") UnitPostExample example);

    int updateByExample(@Param("record") UnitPost record, @Param("example") UnitPostExample example);

    int updateByPrimaryKeySelective(UnitPost record);

    int updateByPrimaryKey(UnitPost record);
}