package persistence.party.common;

import domain.party.BranchMember;
import domain.party.OrgAdmin;
import domain.party.PartyMember;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by lm on 2017/6/13.
 */
public interface IPartyMapper {

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
    @Select("select distinct user_id from (select pm.user_id from ow_party_member_group pmg, ow_party_member pm " +
            "where pm.is_admin=1 and pmg.is_present=1 and pmg.party_id=#{partyId} and pm.group_id=pmg.id " +
            "union all select oa.user_id from ow_org_admin oa,ow_party p where oa.party_id=#{partyId} and oa.party_id=p.id and p.is_deleted=0) tmp")
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
    @Select("select distinct user_id from (select bm.user_id from ow_branch_member_group bmg, ow_branch_member bm " +
            "where bm.is_admin=1 and bmg.is_present=1 and bmg.branch_id=#{branchId} and bm.group_id=bmg.id " +
            "union all select oa.user_id from ow_org_admin oa, ow_branch b, ow_party p " +
            "where oa.branch_id=#{branchId} and oa.branch_id=b.id and b.is_deleted=0 and b.party_id=p.id and p.is_deleted=0) tmp")
    List<Integer> findBranchAdmin(@Param("branchId") int branchId);

    // 根据委员类别查询所有的现任支部委员会委员
    @ResultMap("persistence.party.BranchMemberMapper.BaseResultMap")
    @Select("select bm.* from ow_branch_member_group bmg, ow_branch_member bm " +
            "where bmg.is_deleted=0 and bm.is_admin=1 and  bm.type_id=#{metaTypeId} and bmg.is_present=1 and bmg.branch_id = #{branchId} and bm.group_id=bmg.id")
    List<BranchMember> findBranchMembers(@Param("metaTypeId") int metaTypeId, @Param("branchId") int branchId);

    // 根据委员类别、用户ID查询现任支部委员会委员
    @ResultMap("persistence.party.BranchMemberMapper.BaseResultMap")
    @Select("select bm.* from ow_branch_member_group bmg, ow_branch_member bm " +
            "where bmg.is_deleted=0 and bm.is_admin=1 and  bm.user_id=#{userId} and  bm.type_id=#{metaTypeId} and bmg.is_present=1 and bmg.branch_id = #{branchId} and bm.group_id=bmg.id")
    BranchMember findBranchMember(@Param("metaTypeId") int metaTypeId,
                                  @Param("branchId") int branchId,
                                  @Param("userId") int userId);


    // 更新支部转移次数
    @Update("update ow_branch ob , (select branch_id, count(*) num from ow_branch_transfer_log " +
            "where branch_id in(${brachIds}) group by branch_id) tmp set ob.transfer_count=tmp.num where ob.id=tmp.branch_id")
    void updateBranchTransferCount(@Param("brachIds") String brachIds);
}
