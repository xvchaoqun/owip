package persistence.unit;

import domain.unit.UnitAdminGroup;
import domain.unit.UnitAdminGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UnitAdminGroupMapper {
    int countByExample(UnitAdminGroupExample example);

    int deleteByExample(UnitAdminGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UnitAdminGroup record);

    int insertSelective(UnitAdminGroup record);

    List<UnitAdminGroup> selectByExampleWithRowbounds(UnitAdminGroupExample example, RowBounds rowBounds);

    List<UnitAdminGroup> selectByExample(UnitAdminGroupExample example);

    UnitAdminGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UnitAdminGroup record, @Param("example") UnitAdminGroupExample example);

    int updateByExample(@Param("record") UnitAdminGroup record, @Param("example") UnitAdminGroupExample example);

    int updateByPrimaryKeySelective(UnitAdminGroup record);

    int updateByPrimaryKey(UnitAdminGroup record);
}