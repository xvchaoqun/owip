package persistence.dp.common;

import domain.dp.DpOrgAdmin;
import domain.dp.DpPartyMember;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IDpPartyMapper {
    //查询用户管理的党派ID(现任党派管理员)
    @Select("select distinct pmg.party_id from dp_party_member_group pmg, dp_party_member pm " +
            "where pmg.is_deleted=0 and pm.user_id=#{userId} and pm.is_admin=1 and pmg.is_present=1 and pm.group_id=pmg.id " +
            "union all select distinct oa.party_id from dp_org_admin oa,dp_party p where oa.user_id=#{userId} and oa.party_id is not null and oa.party_id=p.id and p.is_deleted=0")
    List<Integer> adminDpPartyIdList(@Param("userId") int userId);
    // 判断用户是否是现任党派委员会管理员(>0)
    @Select("select sum(tmpcount) from (select count(distinct pmg.party_id) as tmpcount from dp_party_member_group pmg, dp_party_member pm " +
            "where pmg.is_deleted=0 and pm.user_id=#{userId} and pm.is_admin=1 and pmg.is_present=1 and pmg.party_id=#{partyId} and pm.group_id=pmg.id " +
            "union all select count(distinct oa.party_id) as tmpcount from dp_org_admin oa,dp_party p where oa.user_id=#{userId} and oa.party_id=#{partyId} and oa.party_id=p.id and p.is_deleted=0) tmp")
    int isDpPartyAdmin(@Param("userId") int userId, @Param("partyId") int partyId);
    //查询现任民主党派的所有管理员
    @Select("select distinct user_id from (select pm.user_id from dp_party_member_group pmg, dp_party_member pm " +
            "where pm.is_admin=1 and pmg.is_present=1 and pmg.party_id=#{partyId} and pm.group_id=pmg.id " +
            "union all select oa.user_id from dp_org_admin oa,dp_party p where oa.party_id=#{partyId} and oa.party_id=p.id and p.is_deleted=0) tmp")
    List<Integer> findDpPartyAdmin(@Param("partyId") int partyId);
    // 查询现任党派的所有管理员(委员中的)(仅用于管理员删除或添加)
    @ResultMap("persistence.dp.DpPartyMemberMapper.BaseResultMap")
    @Select("select pm.* from dp_party_member_group pmg, dp_party_member pm " +
            "where pm.user_id=#{userId} and pm.is_admin=1 and pmg.is_present=1 and pmg.party_id=#{partyId} and pm.group_id=pmg.id")
    List<DpPartyMember> findDpPartyAdminOfDpPartyMember(@Param("userId") int userId, @Param("partyId") int partyId);
    // 查询现任分党委的所有管理员(单独设定的)(仅用于管理员删除或添加)
    @ResultMap("persistence.dp.DpOrgAdminMapper.BaseResultMap")
    @Select("select * from dp_org_admin where user_id=#{userId} and party_id=#{partyId}")
    List<DpOrgAdmin> findDpPartyAdminOfOrgAdmin(@Param("userId") int userId, @Param("partyId") int partyId);

}
