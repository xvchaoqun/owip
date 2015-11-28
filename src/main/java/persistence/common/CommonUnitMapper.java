package persistence.common;

import domain.Unit;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by fafa on 2015/11/28.
 */
public interface CommonUnitMapper {

    // 查找历史单位
    @ResultMap("persistence.UnitMapper.BaseResultMap")
    @Select("select distinct u.* from base_unit u, base_history_unit hu where hu.unit_id=#{unitId} and hu.old_unit_id=u.id " +
            "order by u.sort_order desc")
    public List<Unit> findHistoryUnits(@Param("unitId")int unitId);
    // 查找正在运转单位
    @ResultMap("persistence.UnitMapper.BaseResultMap")
    @Select("select distinct u.* from base_unit u, base_history_unit hu where " +
            "hu.old_unit_id=#{unitId} and hu.unit_id=u.id and u.status=1 " +
            "order by u.sort_order desc")
    public List<Unit> findRunUnits(@Param("unitId")int unitId);
}
