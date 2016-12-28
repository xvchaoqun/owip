package persistence.common;

import bean.MemberApplyCount;
import domain.cadre.Cadre;
import domain.dispatch.DispatchCadre;
import domain.dispatch.DispatchUnit;
import domain.member.Member;
import domain.member.MemberInflow;
import domain.party.BranchMember;
import domain.party.OrgAdmin;
import domain.party.PartyMember;
import domain.sys.SysUser;
import domain.sys.SysUserView;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;
import sys.constants.SystemConstants;

import java.util.List;
import java.util.Set;

/**
 * Created by fafa on 2015/11/16.
 */
public interface CommonMapper {

    @Select("select max(sort_order) from ${table} where ${whereSql}")
    Integer getMaxSortOrder(@Param("table") String table, @Param("whereSql") String whereSql);

    // 查询用户管理的分党委ID（现任分党委管理员）
    @Select("select distinct pmg.party_id from ow_party_member_group pmg, ow_party_member pm " +
            "where pmg.is_deleted=0 and pm.user_id=#{userId} and pm.is_admin=1 and pmg.is_present=1 and pm.group_id=pmg.id " +
            "union all select distinct oa.party_id from ow_org_admin oa,ow_party p where oa.user_id=#{userId} and oa.party_id is not null and oa.party_id=p.id and p.is_deleted=0")
    List<Integer> adminPartyIdList(@Param("userId") int userId);
    // 判断用户是否是现任分党委班子管理员(>0)
    @Select("select sum(tmpcount) from (select count(distinct pmg.party_id) as tmpcount from ow_party_member_group pmg, ow_party_member pm " +
            "where pmg.is_deleted=0 and pm.user_id=#{userId} and pm.is_admin=1 and pmg.is_present=1 and pmg.party_id=#{partyId} and pm.group_id=pmg.id " +
            "union all select count(distinct oa.party_id) as tmpcount from ow_org_admin oa,ow_party p where oa.user_id=#{userId} and oa.party_id=#{partyId} and oa.party_id=p.id and p.is_deleted=0) tmp")
    int isPartyAdmin(@Param("userId") int userId, @Param("partyId") int partyId);

    // 查询现任分党委的所有管理员(委员中的)(仅用于管理员删除或添加)
    @ResultMap("persistence.party.PartyMemberMapper.BaseResultMap")
    @Select("select pm.* from ow_party_member_group pmg, ow_party_member pm " +
            "where pm.user_id=#{userId} and pm.is_admin=1 and pmg.is_present=1 and pmg.party_id=#{partyId} and pm.group_id=pmg.id")
    List<PartyMember> findPartyAdminOfPartyMember(@Param("userId") int userId, @Param("partyId") int partyId);
    // 查询现任分党委的所有管理员(单独设定的)(仅用于管理员删除或添加)
    @ResultMap("persistence.party.OrgAdminMapper.BaseResultMap")
    @Select("select * from ow_org_admin where user_id=#{userId} and party_id=#{partyId}")
    List<OrgAdmin> findPartyAdminOfOrgAdmin(@Param("userId") int userId, @Param("partyId") int partyId);
    // 查询现任分党委的所有管理员
    @Select("select distinct pm.user_id from ow_party_member_group pmg, ow_party_member pm " +
            "where pm.is_admin=1 and pmg.is_present=1 and pmg.party_id=#{partyId} and pm.group_id=pmg.id " +
            "union all select distinct oa.user_id from ow_org_admin oa,ow_party p where oa.party_id=#{partyId} and oa.party_id=p.id and p.is_deleted=0")
    List<Integer> findPartyAdmin(@Param("partyId") int partyId);

    // 查询用户管理的支部ID（现任支部管理员）
    @Select("select distinct bmg.branch_id from ow_branch_member_group bmg, ow_branch_member bm " +
            "where bmg.is_deleted=0 and bm.user_id=#{userId} and bm.is_admin=1 and bmg.is_present=1 and bm.group_id=bmg.id " +
            "union all select distinct oa.branch_id from ow_org_admin oa, ow_branch b, ow_party p where oa.user_id=#{userId} " +
            "and oa.branch_id is not null and oa.branch_id=b.id and b.is_deleted=0 and b.party_id=p.id and p.is_deleted=0")
    List<Integer> adminBranchIdList(@Param("userId") int userId);
    // 判断用户是否是现任支部委员会管理员(>0)
    @Select("select sum(tmpcount) from (select count(distinct bmg.branch_id) as tmpcount from ow_branch_member_group bmg, ow_branch_member bm " +
            "where bm.user_id=#{userId} and bm.is_admin=1 and bmg.is_present=1 and bmg.branch_id=#{branchId} and bm.group_id=bmg.id " +
            "union all select count(distinct oa.branch_id) as tmpcount from ow_org_admin oa, ow_branch b, ow_party p where oa.user_id=#{userId} " +
            "and oa.branch_id=#{branchId} and oa.branch_id=b.id and b.is_deleted=0 and b.party_id=p.id and p.is_deleted=0) tmp")
    int isBranchAdmin(@Param("userId") int userId, @Param("branchId") int branchId);

    // 查询现任支部委员会的所有管理员(委员中的)(仅用于管理员删除或添加)
    @ResultMap("persistence.party.BranchMemberMapper.BaseResultMap")
    @Select("select bm.* from ow_branch_member_group bmg, ow_branch_member bm " +
            "where bm.user_id=#{userId} and bm.is_admin=1 and bmg.is_present=1 and bmg.branch_id=#{branchId} and bm.group_id=bmg.id")
    List<BranchMember> findBranchAdminOfBranchMember(@Param("userId") int userId, @Param("branchId") int branchId);
    // 查询现任支部委员会的所有管理员(单独设定的)(仅用于管理员删除或添加)
    @ResultMap("persistence.party.OrgAdminMapper.BaseResultMap")
    @Select("select * from ow_org_admin where user_id=#{userId} and branch_id=#{branchId}")
    List<OrgAdmin> findBranchAdminOfOrgAdmin(@Param("userId") int userId, @Param("branchId") int branchId);
    // 查询现任支部委员会的所有管理员
    @Select("select distinct bm.user_id from ow_branch_member_group bmg, ow_branch_member bm " +
            "where bm.is_admin=1 and bmg.is_present=1 and bmg.branch_id=#{branchId} and bm.group_id=bmg.id " +
            "union all select distinct oa.user_id from ow_org_admin oa, ow_branch b, ow_party p " +
            "where oa.branch_id=#{branchId} and oa.branch_id=b.id and b.is_deleted=0 and b.party_id=p.id and p.is_deleted=0")
    List<Integer> findBranchAdmin(@Param("branchId") int branchId);

    // 查询现任支部委员会的所有书记
    @ResultMap("persistence.party.BranchMemberMapper.BaseResultMap")
    @Select("select bm.* from ow_branch_member_group bmg, ow_branch_member bm " +
            "where bmg.is_deleted=0 and bm.is_admin=1 and  bm.type_id=${metaTypeId} and bmg.is_present=1 and bmg.branch_id = #{branchId} and bm.group_id=bmg.id")
    List<BranchMember> findBranchSecretary(@Param("metaTypeId") int metaTypeId, @Param("branchId") int branchId);

    /*@Update("update ow_party set sort_order = sort_order - 1 where is_deleted=0 and sort_order >#{baseSortOrder} and sort_order<=#{targetSortOrder}")
    void downOrder_party(@Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);
    @Update("update ow_party set sort_order = sort_order + 1 where is_deleted=0 and sort_order <#{baseSortOrder} and sort_order>=#{targetSortOrder}")
    void upOrder_party(@Param("baseSortOrder") int baseSortOrder, @Param("targetSortOrder") int targetSortOrder);*/

    void downOrder(@Param("table") String table, @Param("whereSql") String whereSql,
                                @Param("baseSortOrder") int baseSortOrder,
                                @Param("targetSortOrder") int targetSortOrder);
    void upOrder(@Param("table") String table, @Param("whereSql") String whereSql,
                              @Param("baseSortOrder") int baseSortOrder,
                              @Param("targetSortOrder") int targetSortOrder);

    // 根据账号、姓名、学工号查找干部
    List<Cadre> selectCadreList(@Param("search") String search,
                                @Param("cadreStatusList")Set<Byte> cadreStatusList, RowBounds rowBounds);
    int countCadre(@Param("search") String search,
                   @Param("cadreStatusList")Set<Byte> cadreStatusList);

   /* // 根据账号、姓名、学工号 查找所在单位和兼职单位 都关联该单位的干部
    List<Cadre> selectCadreByUnitIdList(@Param("search") String search,
                                        @Param("cadreStatusList")List<Byte> cadreStatusList, @Param("unitId")int unitId, RowBounds rowBounds);
    int countCadreByUnitId(@Param("search") String search,
                           @Param("cadreStatusList")List<Byte> cadreStatusList, @Param("unitId")int unitId);*/

    // 根据账号、姓名、学工号查找 不是 干部的用户
    List<SysUserView> selectNotCadreList(@Param("search") String search,
                                         @Param("cadreStatusList")Set<Byte> cadreStatusList, @Param("regRoleStr") String regRoleStr, RowBounds rowBounds);
    int countNotCadre(@Param("search") String search,
                      @Param("cadreStatusList")Set<Byte> cadreStatusList, @Param("regRoleStr") String regRoleStr);

    // 根据账号、姓名、学工号查找 不是 党员的用户
    List<SysUserView> selectNotMemberList(@Param("search") String search, @Param("regRoleStr") String regRoleStr, RowBounds rowBounds);
    int countNotMember(@Param("search") String search, @Param("regRoleStr") String regRoleStr);


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


    // 查找干部发文（与干部任免排序规则相同） type:1任命 2免职 NULL 全部
    List<DispatchCadre> selectDispatchCadreList(@Param("cadreId") int cadreId, @Param("type") Byte type);

    // 根据所属单位查找干部发文（按发文时间排序）
    @ResultMap("persistence.dispatch.DispatchCadreMapper.BaseResultMap")
    @Select("select distinct dc.* from dispatch_cadre dc, dispatch d " +
            "where dc.dispatch_id=d.id and dc.unit_id=#{unitId} order by d.pub_time desc")
    List<DispatchCadre> selectDispatchCadreByUnitIdList(@Param("unitId") int unitId);

    // 查找单位发文（按发文时间排序）
    @ResultMap("persistence.dispatch.DispatchUnitMapper.BaseResultMap")
    @Select("select distinct du.* from dispatch_unit du, dispatch d " +
            "where du.dispatch_id=d.id and du.unit_id=#{unitId} order by d.pub_time desc")
    List<DispatchUnit> selectDispatchUnitList(@Param("unitId") int unitId);
}
