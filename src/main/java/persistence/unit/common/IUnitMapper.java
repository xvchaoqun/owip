package persistence.unit.common;

import domain.unit.Unit;
import domain.unit.UnitPost;
import domain.unit.UnitPostView;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by fafa on 2015/11/28.
 */
public interface IUnitMapper {

    // 查找未分组岗位

   /* @ResultMap("persistence.unit.UnitPostViewMapper.BaseResultMap")
    @Select("select * from unit_post_view where group_id =${groupId} or group_id is null order by status asc, sort_order asc")
    List<UnitPostView> getUnitPostByGroup(@Param("groupId") int groupId, RowBounds rowBounds);*/

    List<UnitPostView> getUnitPostByGroup(@Param("groupId") int groupId, @Param("name") String name, @Param("code") String code, RowBounds rowBounds);

    int countUnitPostByGroup(@Param("groupId") int groupId, @Param("name") String name, @Param("code") String code);

    @ResultMap("persistence.unit.UnitPostMapper.BaseResultMap")
    @Select("select * from unit_post where id in(${ids}) order by status asc, sort_order desc")
    List<UnitPost> getUnitPosts(@Param("ids") String ids);

    @ResultMap("persistence.unit.UnitPostViewMapper.BaseResultMap")
    @Select("select * from unit_post_view where id=#{id}")
    UnitPostView getUnitPost(@Param("id") int id);

    // 查找历史单位
    @ResultMap("persistence.unit.UnitMapper.BaseResultMap")
    @Select("select distinct u.* from unit u, unit_history_unit hu where hu.unit_id=#{unitId} and hu.old_unit_id=u.id " +
            "order by hu.sort_order desc")
    public List<Unit> findHistoryUnits(@Param("unitId")int unitId);
    // 查找正在运转单位
    @ResultMap("persistence.unit.UnitMapper.BaseResultMap")
    @Select("select distinct u.* from unit u, unit_history_unit hu where " +
            "hu.old_unit_id=#{unitId} and hu.unit_id=u.id and u.status=1 " +
            "order by u.sort_order desc")
    public List<Unit> findRunUnits(@Param("unitId")int unitId);

    // 查找单位的 现任 行政班子成员
    /*@ResultType(UnitAdminCadre.class)
    @Select("select uag.unit_id as unitId, ua.cadre_id as cadreId," +
            " ua.post_id as postId, mt.bool_attr as isPositive from " +
            "unit_admin ua, unit_admin_group uag, base_meta_type mt "+
            " where uag.unit_id=#{unitId} and uag.is_present=1 and ua.group_id=uag.id and ua.post_id = mt.id" +
            " order by ua.sort_order desc")
    public List<UnitAdminCadre> findUnitAdminCadre(@Param("unitId")int unitId);*/

    // 根据类型查找所有单位的 现任 行政班子成员
    /*@ResultType(UnitAdminCadre.class)
    @Select("select distinct uag.unit_id as unitId, ua.cadre_id as cadreId," +
            " ua.post_id as postId, mt.bool_attr as isPositive from " +
            "unit_admin ua, unit_admin_group uag, base_meta_type mt,unit u "+
            " where u.type_id=#{type} and u.status=1 and uag.unit_id=u.id and uag.is_present=1 and ua.group_id=uag.id and ua.post_id = mt.id" +
            " order by ua.sort_order desc")
    public List<UnitAdminCadre> findUnitAdminCadreByType(@Param("type")int type);*/

    // 查找未分配校领导的单位
    @ResultMap("persistence.unit.UnitMapper.BaseResultMap")
    @Select("select * from unit where status=1 and id not in(select unit_id from leader_unit)")
    public List<Unit> findLeaderUnitEscape();
}
