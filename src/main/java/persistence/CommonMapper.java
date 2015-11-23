package persistence;

import domain.Cadre;
import domain.DispatchUnit;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by fafa on 2015/11/16.
 */
public interface CommonMapper {

    // 判断用户是否是现在分党委班子管理员(>0)
    @Select("select count(*) from ow_party_member_group pmg, ow_party_member pm " +
            "where pm.user_id=#{userId} and pm.is_admin=1 and pmg.is_present=1 and pmg.party_id=#{partyId} and pm.group_id=pmg.id")
    int isPartyAdmin(@Param("userId") int userId, @Param("partyId") int partyId);

    // 判断用户是否是现任支部委员会管理员(>0)
    @Select("select count(*) from ow_branch_member_group bmg, ow_branch_member bm " +
            "where bm.user_id=#{userId} and bm.is_admin=1 and bmg.is_present=1 and bmg.branch_id=#{branchId} and bm.group_id=bmg.id")
    int isBranchAdmin(@Param("userId") int userId, @Param("branchId") int branchId);

    @Update("update ${tableName} set sort_order = sort_order - 1 where sort_order >#{baseSortOrder} and sort_order<=#{targetSortOrder}")
    void downOrder(@Param("tableName") String tableName, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);
    @Update("update ${tableName} set sort_order = sort_order + 1 where sort_order <#{baseSortOrder} and sort_order>=#{targetSortOrder}")
    void upOrder(@Param("tableName") String tableName,  @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);

    @Update("update base_dispatch_unit_relate set sort_order = sort_order - 1 where dispatch_unit_id = #{dispatchUnitId} and sort_order >#{baseSortOrder} and sort_order<=#{targetSortOrder}")
    void downOrder_dispatchUnit(@Param("dispatchUnitId") int dispatchUnitId, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);
    @Update("update base_dispatch_unit_relate set sort_order = sort_order + 1 where dispatch_unit_id = #{dispatchUnitId} and sort_order <#{baseSortOrder} and sort_order>=#{targetSortOrder}")
    void upOrder_dispatchUnit(@Param("dispatchUnitId") int dispatchUnitId, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);

    @Update("update base_unit set sort_order = sort_order - 1 where status=#{status} and sort_order >#{baseSortOrder} and sort_order<=#{targetSortOrder}")
    void downOrder_unit(@Param("status") byte status, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);
    @Update("update base_unit set sort_order = sort_order + 1 where status=#{status} and sort_order <#{baseSortOrder} and sort_order>=#{targetSortOrder}")
    void upOrder_unit(@Param("status") byte status, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);

    @Update("update base_cadre set sort_order = sort_order - 1 where status=#{status} and sort_order >#{baseSortOrder} and sort_order<=#{targetSortOrder}")
    void downOrder_cadre(@Param("status") byte status, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);
    @Update("update base_cadre set sort_order = sort_order + 1 where status=#{status} and sort_order <#{baseSortOrder} and sort_order>=#{targetSortOrder}")
    void upOrder_cadre(@Param("status") byte status, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);


    @Update("update base_meta_type set sort_order = sort_order - 1 where class_id=#{classId} and sort_order >#{baseSortOrder} and sort_order<=#{targetSortOrder}")
    void downOrder_metaType(@Param("classId") int classId, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);
    @Update("update base_meta_type set sort_order = sort_order + 1 where class_id=#{classId} and sort_order <#{baseSortOrder} and sort_order>=#{targetSortOrder}")
    void upOrder_metaType(@Param("classId") int classId, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);

    @Update("update base_history_unit set sort_order = sort_order - 1 where unit_id=#{unitId} and sort_order >#{baseSortOrder} and sort_order<=#{targetSortOrder}")
    void downOrder_historyUnit(@Param("unitId") int unitId, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);
    @Update("update base_history_unit set sort_order = sort_order + 1 where unit_id=#{unitId} and sort_order <#{baseSortOrder} and sort_order>=#{targetSortOrder}")
    void upOrder_historyUnit(@Param("unitId") int unitId, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);


    // 根据账号、姓名、学工号查找干部
    @ResultMap("persistence.CadreMapper.BaseResultMap")
    @Select("select bc.* from base_cadre bc, sys_user user where bc.user_id= user.id and " +
            "(user.username like '%${search}%' or user.realname like '%${search}%' or user.code like '%${search}%')" +
            "order by sort_order desc")
    List<Cadre> selectCadreList(@Param("search") String search, RowBounds rowBounds);
    @Select("select count(bc.id) from base_cadre bc, sys_user user where bc.user_id= user.id and " +
            "(user.username like '%${search}%' or user.realname like '%${search}%' or user.code like '%${search}%')")
    int countCadre(@Param("search") String search);

    // 根据发文号查找单位发文
    @ResultMap("persistence.DispatchUnitMapper.BaseResultMap")
    @Select("select bdu.* from base_dispatch_unit bdu, base_dispatch d " +
            "where bdu.unit_id=#{unitId} and bdu.dispatch_id=d.id and d.code like '%${search}%' order by bdu.sort_order desc")
    List<DispatchUnit> selectDispatchUnitList(@Param("search") String code, @Param("unitId") int unitId, RowBounds rowBounds);
    @Select("select count(bdu.id) from base_dispatch_unit bdu, base_dispatch d " +
            "where bdu.unit_id=#{unitId} and bdu.dispatch_id=d.id and d.code like '%${search}%'")
    int countDispatchUnit(@Param("search") String code, @Param("unitId") int unitId);

}
