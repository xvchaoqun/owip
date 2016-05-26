package persistence.common;

import bean.MemberApplyCount;
import domain.*;
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

    // 查询用户管理的分党委ID（现任分党委管理员）
    @Select("select pmg.party_id from ow_party_member_group pmg, ow_party_member pm " +
            "where pm.user_id=#{userId} and pm.is_admin=1 and pmg.is_present=1 and pm.group_id=pmg.id " +
            "union all select party_id from ow_org_admin where user_id=#{userId} and party_id is not null")
    List<Integer> adminPartyIdList(@Param("userId") int userId);
    // 判断用户是否是现任分党委班子管理员(>0)
    @Select("select sum(tmpcount) from (select count(*) as tmpcount from ow_party_member_group pmg, ow_party_member pm " +
            "where pm.user_id=#{userId} and pm.is_admin=1 and pmg.is_present=1 and pmg.party_id=#{partyId} and pm.group_id=pmg.id " +
            "union all select count(*) as tmpcount from ow_org_admin where user_id=#{userId} and party_id=#{partyId}) tmp")
    int isPartyAdmin(@Param("userId") int userId, @Param("partyId") int partyId);

    // 查询用户管理的支部ID（现任支部管理员）
    @Select("select bmg.branch_id from ow_branch_member_group bmg, ow_branch_member bm " +
            "where bm.user_id=#{userId} and bm.is_admin=1 and bmg.is_present=1 and bm.group_id=bmg.id " +
            "union all select branch_id from ow_org_admin where user_id=#{userId} and branch_id is not null")
    List<Integer> adminBranchIdList(@Param("userId") int userId);
    // 判断用户是否是现任支部委员会管理员(>0)
    @Select("select sum(tmpcount) from (select count(*) as tmpcount from ow_branch_member_group bmg, ow_branch_member bm " +
            "where bm.user_id=#{userId} and bm.is_admin=1 and bmg.is_present=1 and bmg.branch_id=#{branchId} and bm.group_id=bmg.id " +
            "union all select count(*) as tmpcount from ow_org_admin where user_id=#{userId} and branch_id=#{branchId}) tmp")
    int isBranchAdmin(@Param("userId") int userId, @Param("branchId") int branchId);

    @Update("update ${tableName} set sort_order = sort_order - 1 where sort_order >#{baseSortOrder} and sort_order<=#{targetSortOrder}")
    void downOrder(@Param("tableName") String tableName, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);
    @Update("update ${tableName} set sort_order = sort_order + 1 where sort_order <#{baseSortOrder} and sort_order>=#{targetSortOrder}")
    void upOrder(@Param("tableName") String tableName,  @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);

    @Update("update base_unit_admin set sort_order = sort_order - 1 where group_id = #{groupId} and sort_order >#{baseSortOrder} and sort_order<=#{targetSortOrder}")
    void downOrder_unitAdmin(@Param("groupId") int groupId, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);
    @Update("update base_unit_admin set sort_order = sort_order + 1 where group_id = #{groupId} and sort_order <#{baseSortOrder} and sort_order>=#{targetSortOrder}")
    void upOrder_unitAdmin(@Param("groupId") int groupId, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);

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


    @Update("update base_dispatch_type set sort_order = sort_order - 1 where year=#{year} and sort_order >#{baseSortOrder} and sort_order<=#{targetSortOrder}")
    void downOrder_dispatchType(@Param("year") int year, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);
    @Update("update base_history_unit set sort_order = sort_order + 1 where year=#{year} and sort_order <#{baseSortOrder} and sort_order>=#{targetSortOrder}")
    void upOrder_dispatchType(@Param("year") int year, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);


    @Update("update abroad_approval_order set sort_order = sort_order - 1 where applicat_type_id=#{applicatTypeId} and sort_order >#{baseSortOrder} and sort_order<=#{targetSortOrder}")
    void downOrder_approvalOrder(@Param("applicatTypeId") int applicatTypeId, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);
    @Update("update abroad_approval_order set sort_order = sort_order + 1 where applicat_type_id=#{applicatTypeId} and sort_order <#{baseSortOrder} and sort_order>=#{targetSortOrder}")
    void upOrder_approvalOrder(@Param("applicatTypeId") int applicatTypeId, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);

    @Update("update base_cadre_tutor set sort_order = sort_order - 1 where cadre_id=#{cadreId} and sort_order >#{baseSortOrder} and sort_order<=#{targetSortOrder}")
    void downOrder_cadreTutor(@Param("cadreId") int cadreId, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);
    @Update("update base_cadre_tutor set sort_order = sort_order + 1 where cadre_id=#{cadreId} and sort_order <#{baseSortOrder} and sort_order>=#{targetSortOrder}")
    void upOrder_cadreTutor(@Param("cadreId") int cadreId, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);

    @Update("update base_cadre_edu set sort_order = sort_order - 1 where cadre_id=#{cadreId} and sort_order >#{baseSortOrder} and sort_order<=#{targetSortOrder}")
    void downOrder_cadreEdu(@Param("cadreId") int cadreId, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);
    @Update("update base_cadre_edu set sort_order = sort_order + 1 where cadre_id=#{cadreId} and sort_order <#{baseSortOrder} and sort_order>=#{targetSortOrder}")
    void upOrder_cadreEdu(@Param("cadreId") int cadreId, @Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);


    // 根据账号、姓名、学工号查找干部
    List<Cadre> selectCadreList(@Param("search") String search, RowBounds rowBounds);
    int countCadre(@Param("search") String search);

    // 根据账号、姓名、学工号 查找所在单位和兼职单位 都关联该单位的干部
    List<Cadre> selectCadreByUnitIdList(@Param("search") String search, @Param("unitId")int unitId, RowBounds rowBounds);
    int countCadreByUnitId(@Param("search") String searchint, @Param("unitId")int unitId);

    // 根据账号、姓名、学工号查找 不是 干部的用户
    List<SysUser> selectNotCadreList(@Param("search") String search, RowBounds rowBounds);
    int countNotCadre(@Param("search") String search);

    // 根据账号、姓名、学工号查找 不是 党员的用户
    List<SysUser> selectNotMemberList(@Param("search") String search, RowBounds rowBounds);
    int countNotMember(@Param("search") String search);


    List<MemberApplyCount> selectMemberApplyCount(@Param("addPermits")Boolean addPermits, @Param("adminPartyIdList")List<Integer> adminPartyIdList,
                                                  @Param("adminBranchIdList")List<Integer> adminBranchIdList);

    // 根据类别、状态、账号、姓名、学工号查找党员
    List<Member> selectMemberList(@Param("type")Byte type, @Param("status")Byte status, @Param("search") String search,
                                  @Param("addPermits")Boolean addPermits,
                                  @Param("adminPartyIdList")List<Integer> adminPartyIdList,
                                  @Param("adminBranchIdList")List<Integer> adminBranchIdList, RowBounds rowBounds);
    int countMember(@Param("type")Byte type, @Param("status")Byte status, @Param("search") String search,
                    @Param("addPermits")Boolean addPermits,
                    @Param("adminPartyIdList")List<Integer> adminPartyIdList,
                    @Param("adminBranchIdList")List<Integer> adminBranchIdList);

    // 根据类别、状态、账号、姓名、学工号查找流入党员
    List<MemberInflow> selectMemberInflowList(@Param("type")Byte type,
                                              @Param("inflowStatus")Byte inflowStatus,
                                              @Param("hasOutApply")Boolean hasOutApply, // 是否已经提交申请
                                              @Param("search") String search,
                                  @Param("addPermits")Boolean addPermits,
                                  @Param("adminPartyIdList")List<Integer> adminPartyIdList,
                                  @Param("adminBranchIdList")List<Integer> adminBranchIdList, RowBounds rowBounds);
    int countMemberInflow(@Param("type")Byte type,
                          @Param("inflowStatus")Byte inflowStatus,
                          @Param("hasOutApply")Boolean hasOutApply,
                          @Param("search") String search,
                    @Param("addPermits")Boolean addPermits,
                    @Param("adminPartyIdList")List<Integer> adminPartyIdList,
                    @Param("adminBranchIdList")List<Integer> adminBranchIdList);

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
