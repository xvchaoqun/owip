package persistence.unit;

import domain.unit.UnitPostGroup;
import domain.unit.UnitPostGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UnitPostGroupMapper {
    long countByExample(UnitPostGroupExample example);

    int deleteByExample(UnitPostGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UnitPostGroup record);

    int insertSelective(UnitPostGroup record);

    List<UnitPostGroup> selectByExampleWithRowbounds(UnitPostGroupExample example, RowBounds rowBounds);

    List<UnitPostGroup> selectByExample(UnitPostGroupExample example);

    UnitPostGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UnitPostGroup record, @Param("example") UnitPostGroupExample example);

    int updateByExample(@Param("record") UnitPostGroup record, @Param("example") UnitPostGroupExample example);

    int updateByPrimaryKeySelective(UnitPostGroup record);

    int updateByPrimaryKey(UnitPostGroup record);
}