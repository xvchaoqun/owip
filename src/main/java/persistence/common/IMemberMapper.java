package persistence.common;

import bean.MemberApplyCount;
import domain.member.Member;
import domain.member.MemberExample;
import domain.member.MemberInflow;
import domain.sys.SysUserView;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;
import sys.constants.SystemConstants;

import java.util.Date;
import java.util.List;

/**
 * Created by lm on 2017/6/13.
 */
public interface IMemberMapper {

    @Select("select max(code) from ow_member_stay where left(code, 4)=#{year}")
    String getMemberStayMaxCode(@Param("year") int year);

    // 根据账号、姓名、学工号查找 不是 党员的用户
    List<SysUserView> selectNotMemberList(@Param("search") String search, @Param("regRoleStr") String regRoleStr, RowBounds rowBounds);
    int countNotMember(@Param("search") String search, @Param("regRoleStr") String regRoleStr);


    List<MemberApplyCount> selectMemberApplyCount(@Param("addPermits")Boolean addPermits, @Param("adminPartyIdList")List<Integer> adminPartyIdList,
                                                  @Param("adminBranchIdList")List<Integer> adminBranchIdList);

    // 根据类别、状态、账号、姓名、学工号查找党员
    List<Member> selectMemberList(@Param("partyId")Integer partyId,
                                  @Param("type")Byte type,
                                  @Param("isRetire")Boolean isRetire,
                                  @Param("politicalStatus")Byte politicalStatus,
                                  @Param("status")Byte status, @Param("search") String search,
                                  @Param("addPermits")Boolean addPermits,
                                  @Param("adminPartyIdList")List<Integer> adminPartyIdList,
                                  @Param("adminBranchIdList")List<Integer> adminBranchIdList, RowBounds rowBounds);
    int countMember(@Param("partyId")Integer partyId,
                    @Param("type")Byte type,
                    @Param("isRetire")Boolean isRetire,
                    @Param("politicalStatus")Byte politicalStatus,
                    @Param("status")Byte status, @Param("search") String search,
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

    // 入党申请打回至状态
    //====================start

    @Update("update ow_member_apply set stage="+ SystemConstants.APPLY_STAGE_GROW
            +", positive_status=null, positive_time=null " +
            "where user_id=#{userId} and stage="+ SystemConstants.APPLY_STAGE_POSITIVE)
    int memberApplyBackToGrow(@Param("userId") int userId);

    @Update("update ow_member_apply set stage="+SystemConstants.APPLY_STAGE_PLAN
            +", grow_status=null, grow_time=null"
            +", draw_status=null, draw_time=null " +
            "where user_id=#{userId} and stage<="+ SystemConstants.APPLY_STAGE_DRAW)
    void memberApplyBackToPlan(@Param("userId") int userId);

    @Update("update ow_member_apply set stage="+SystemConstants.APPLY_STAGE_CANDIDATE
            +", grow_status=null, grow_time=null"
            +", draw_status=null, draw_time=null"
            +", plan_time=null, plan_status=null"
            + " where user_id=#{userId} and stage<="+ SystemConstants.APPLY_STAGE_DRAW
            + " and stage>"+SystemConstants.APPLY_STAGE_CANDIDATE)
    void memberApplyBackToCandidate(@Param("userId") int userId);

    @Update("update ow_member_apply set stage="+SystemConstants.APPLY_STAGE_ACTIVE
            +", grow_status=null, grow_time=null"
            +", draw_status=null, draw_time=null"
            +", plan_time=null, plan_status=null"
            +", candidate_time=null, train_time=null, candidate_status=null"
            +" where user_id=#{userId} and stage<="+ SystemConstants.APPLY_STAGE_DRAW
            + " and stage>"+SystemConstants.APPLY_STAGE_ACTIVE)
    void memberApplyBackToActive(@Param("userId") int userId);

    @Update("update ow_member_apply set stage="+SystemConstants.APPLY_STAGE_INIT
            +", grow_status=null, grow_time=null"
            +", draw_status=null, draw_time=null"
            +", plan_time=null, plan_status=null"
            +", candidate_time=null, train_time=null, candidate_status=null"
            +", active_time=null, pass_time=null"
            +" where user_id=#{userId} and stage<="+ SystemConstants.APPLY_STAGE_DRAW
            + " and stage>"+SystemConstants.APPLY_STAGE_INIT)
    void memberApplyBackToInit(@Param("userId") int userId);


    // 批量转校内组织关系
    int changeMemberParty(@Param("partyId") Integer partyId, @Param("branchId") Integer branchId,
                          @Param("example") MemberExample example);

    int increasePrintCount(@Param("tableName") String tableName, @Param("idList") List<Integer> idList,
                           @Param("lastPrintTime") Date lastPrintTime,
                           @Param("lastPrintUserId") Integer lastPrintUserId);

    // 如果修改成直属党支部， 则将支部ID设置为NULL
    @Update("update ${tableName} set party_id=#{partyId}, branch_id=null where ${idName}=#{id}")
    int updateToDirectBranch(@Param("tableName") String tableName, @Param("idName") String idName,
                             @Param("id") int id, @Param("partyId") int partyId);

    // 支部整建转移之后，需要修改关联表的支部所属分党委id
    @Update("update ${tableName} tmp, ow_branch ob set tmp.party_id=ob.party_id where ob.id in (${brachIds}) and tmp.branch_id=ob.id")
    void batchTransfer(@Param("tableName") String tableName, @Param("brachIds") String brachIds);
    // 单独用于校内转接
    @Update("update ow_member_transfer tmp, ow_branch ob set tmp.to_party_id=ob.party_id where ob.id in (${brachIds}) and tmp.to_branch_id=ob.id")
    void batchTransfer2(@Param("brachIds") String brachIds);

    // 入党申请打回至状态
    //====================end

    // 党员出党：打回
    @Update("update ow_member_quit set status= #{status}"
            +" where user_id=#{userId} and status >= #{status} and status<"
            + SystemConstants.MEMBER_QUIT_STATUS_OW_VERIFY)
    void memberQuit_back(@Param("userId") int userId, @Param("status") byte status);

    // 组织关系转入：打回
    @Update("update ow_member_in set status= #{status}"
            +" where id=#{id} and status >= #{status} and status<"
            + SystemConstants.MEMBER_IN_STATUS_OW_VERIFY)
    void memberIn_back(@Param("id") int id, @Param("status") byte status);

    // 党员出国：打回
    @Update("update ow_member_stay set status= #{status}"
            +" where id=#{id} and status >= #{status} and status<"
            + SystemConstants.MEMBER_STAY_STATUS_OW_VERIFY)
    void memberStay_back(@Param("id") int id, @Param("status") byte status);

    // 校内转接：打回
    @Update("update ow_member_transfer set status= #{status}"
            +" where id=#{id} and status >= #{status} and status<"
            + SystemConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY)
    void memberTransfer_back(@Param("id") int id, @Param("status") byte status);

    // 转出：打回
    @Update("update ow_member_out set status= #{status}"
            +" where id=#{id} and status >= #{status} and status<"
            + SystemConstants.MEMBER_OUT_STATUS_OW_VERIFY)
    void memberOut_back(@Param("id") int id, @Param("status") byte status);

    // 归国：打回
    @Update("update ow_member_return set status= #{status}"
            +" where id=#{id} and status >= #{status} and status<"
            + SystemConstants.MEMBER_RETURN_STATUS_PARTY_VERIFY)
    void memberReturn_back(@Param("id") int id, @Param("status") byte status);

    // 流入：打回
    @Update("update ow_member_inflow set inflow_status= #{status}"
            +" where id=#{id} and inflow_status >= #{status} and inflow_status<"
            + SystemConstants.MEMBER_INFLOW_STATUS_PARTY_VERIFY)
    void memberInflow_back(@Param("id") int id, @Param("status") byte status);
    // 流入转出：打回
    @Update("update ow_member_inflow set out_status= #{status}"
            +" where id=#{id} and out_status >= #{status} and out_status<"
            + SystemConstants.MEMBER_INFLOW_OUT_STATUS_PARTY_VERIFY)
    void memberInflowOut_back(@Param("id") int id, @Param("status") byte status);

    // 流出：打回
    @Update("update ow_member_outflow set status= #{status}"
            +" where id=#{id} and status >= #{status} and status<"
            + SystemConstants.MEMBER_OUTFLOW_STATUS_PARTY_VERIFY)
    void memberOutflow_back(@Param("id") int id, @Param("status") byte status);

    // 清空是否打回状态
    @Update("update ${tableName} set ${isBackName}=#{isBack} where ${idName}=#{id}")
    int resetIsBack(@Param("tableName") String tableName,
                    @Param("isBackName") String isBackName, @Param("isBack") Boolean isBack,
                    @Param("idName") String idName, @Param("id") int id);

    // 清除退休时间
    @Update("update sys_teacher_info set retire_time=null where user_id=#{userId}")
    void del_retireTime(@Param("userId") int userId);


    @Update("update ow_apply_open_time set party_id=null, branch_id=null where id=#{id}")
    void globalApplyOpenTime(@Param("id") int id);
}
