package persistence.common;

import domain.unit.Unit;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by fafa on 2015/11/28.
 */
public interface CommonUnitMapper {

    // 查找历史单位
    @ResultMap("persistence.unit.UnitMapper.BaseResultMap")
    @Select("select distinct u.* from base_unit u, base_history_unit hu where hu.unit_id=#{unitId} and hu.old_unit_id=u.id " +
            "order by u.sort_order desc")
    public List<Unit> findHistoryUnits(@Param("unitId")int unitId);
    // 查找正在运转单位
    @ResultMap("persistence.unit.UnitMapper.BaseResultMap")
    @Select("select distinct u.* from base_unit u, base_history_unit hu where " +
            "hu.old_unit_id=#{unitId} and hu.unit_id=u.id and u.status=1 " +
            "order by u.sort_order desc")
    public List<Unit> findRunUnits(@Param("unitId")int unitId);

    // 查找单位的 现任 行政班子成员
    @ResultType(persistence.common.UnitAdminCadre.class)
    @Select("select uag.unit_id as unitId, ua.cadre_id as cadreId," +
            " ua.post_id as postId, mt.bool_attr as isPositive from " +
            "base_unit_admin ua, base_unit_admin_group uag, base_meta_type mt "+
            " where uag.unit_id=#{unitId} and uag.is_present=1 and ua.group_id=uag.id and ua.post_id = mt.id" +
            " order by ua.sort_order desc")
    public List<UnitAdminCadre> findUnitAdminCadre(@Param("unitId")int unitId);

    // 根据类型查找所有单位的 现任 行政班子成员
    @ResultType(persistence.common.UnitAdminCadre.class)
    @Select("select distinct uag.unit_id as unitId, ua.cadre_id as cadreId," +
            " ua.post_id as postId, mt.bool_attr as isPositive from " +
            "base_unit_admin ua, base_unit_admin_group uag, base_meta_type mt,base_unit u "+
            " where u.type_id=#{type} and u.status=1 and uag.unit_id=u.id and uag.is_present=1 and ua.group_id=uag.id and ua.post_id = mt.id" +
            " order by ua.sort_order desc")
    public List<UnitAdminCadre> findUnitAdminCadreByType(@Param("type")int type);
}
