package persistence.unit;

import domain.unit.UnitAdmin;
import domain.unit.UnitAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UnitAdminMapper {
    int countByExample(UnitAdminExample example);

    int deleteByExample(UnitAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UnitAdmin record);

    int insertSelective(UnitAdmin record);

    List<UnitAdmin> selectByExampleWithRowbounds(UnitAdminExample example, RowBounds rowBounds);

    List<UnitAdmin> selectByExample(UnitAdminExample example);

    UnitAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UnitAdmin record, @Param("example") UnitAdminExample example);

    int updateByExample(@Param("record") UnitAdmin record, @Param("example") UnitAdminExample example);

    int updateByPrimaryKeySelective(UnitAdmin record);

    int updateByPrimaryKey(UnitAdmin record);
}