package persistence.common;

import domain.Cadre;
import domain.DispatchCadre;
import domain.DispatchUnit;
import domain.SysUser;
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
    List<Cadre> selectCadreList(@Param("search") String search, RowBounds rowBounds);
    int countCadre(@Param("search") String search);

    // 根据账号、姓名、学工号 查找所在单位和兼职单位 都关联该单位的干部
    List<Cadre> selectCadreByUnitIdList(@Param("search") String search, @Param("unitId")int unitId, RowBounds rowBounds);
    int countCadreByUnitId(@Param("search") String searchint, @Param("unitId")int unitId);

    // 根据账号、姓名、学工号查找 不是 干部的用户
    List<SysUser> selectNotCadreList(@Param("search") String search, RowBounds rowBounds);
    int countNotCadre(@Param("search") String search);

    // 根据发文号查找单位发文
    List<DispatchUnit> selectDispatchUnitByCodeList(@Param("search") String code, @Param("unitId") int unitId, RowBounds rowBounds);
    int countDispatchByCodeUnit(@Param("search") String code, @Param("unitId") int unitId);


    // 查找干部发文（按发文时间排序）
    @ResultMap("persistence.DispatchCadreMapper.BaseResultMap")
    @Select("select distinct dc.* from base_dispatch_cadre dc, base_dispatch d " +
            "where dc.dispatch_id=d.id and dc.cadre_id=#{cadreId} order by d.pub_time desc")
    List<DispatchCadre> selectDispatchCadreList(@Param("cadreId") int cadreId);

    // 根据所属单位查找干部发文（按发文时间排序）
    @ResultMap("persistence.DispatchCadreMapper.BaseResultMap")
    @Select("select distinct dc.* from base_dispatch_cadre dc, base_dispatch d " +
            "where dc.dispatch_id=d.id and dc.unit_id=#{unitId} order by d.pub_time desc")
    List<DispatchCadre> selectDispatchCadreByUnitIdList(@Param("unitId") int unitId);

    // 查找单位发文（按发文时间排序）
    @ResultMap("persistence.DispatchUnitMapper.BaseResultMap")
    @Select("select distinct du.* from base_dispatch_unit du, base_dispatch d " +
            "where du.dispatch_id=d.id and du.unit_id=#{unitId} order by d.pub_time desc")
    List<DispatchUnit> selectDispatchUnitList(@Param("unitId") int unitId);
}
