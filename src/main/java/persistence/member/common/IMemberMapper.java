package persistence.member.common;

import domain.member.*;
import domain.sys.SysUserView;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2017/6/13.
 */
public interface IMemberMapper {

    // 更新党员发展模块中的预备党员所在党组织 与 党员库中不一致的情况
    @Update("update ow_member m, ow_member_apply ma set ma.party_id=m.party_id, ma.branch_id=m.branch_id where ma.stage = "
            + OwConstants.OW_APPLY_STAGE_GROW +" and ma.user_id=m.user_id and (ma.party_id!=m.party_id or ma.branch_id!=m.branch_id)")
    int adjustMemberApply();

    //查询分党委及党支部所有书记副书记
    @Select("select user_id from ow_party_member_view where group_party_id=#{partyId} and is_history=0 and is_deleted=0 and is_present=1 and(post_id=64 or  post_id=63)" +
            "union select user_id from ow_branch_member_view where group_party_id=#{partyId} and is_history=0 and is_deleted=0 and is_present=1 and type_id=80")
    Integer[] getPbMemberSelects(@Param("partyId") int partyId);
    // 批量生成账号的最大批次
    @Select("select max(import_seq) from ow_member_reg")
    Integer getMemberRegMaxSeq();

    @ResultMap("persistence.member.MemberViewMapper.BaseResultMap")
    @Select("select * from ow_member_view where user_id=#{userId}")
    MemberView getMemberView(@Param("userId") int userId);

    @ResultMap("persistence.member.ApplySnRangeMapper.BaseResultMap")
    @Select("select * from ow_apply_sn_range where year=#{year} and (#{startSn} in(start_sn, end_sn) " +
            "or #{endSn} in(start_sn, end_sn) " +
            "or start_sn in(#{startSn}, #{endSn}) " +
            "or end_sn in(#{startSn}, #{endSn}) )")
    List<ApplySnRange> getOverlapApplySnRanges(@Param("year") int year,
                                               @Param("startSn") Long startSn,
                                               @Param("endSn") Long endSn);

    @ResultMap("persistence.member.ApplySnMapper.BaseResultMap")
    @Select("select * from ow_apply_sn where sn=#{sn} and year=#{year}")
    ApplySn getApplySn(@Param("year") int year, @Param("sn") long sn);

    @ResultMap("persistence.member.ApplySnMapper.BaseResultMap")
    @Select("select * from ow_apply_sn where display_sn=#{displaySn} and year=#{year}")
    ApplySn getApplySnByDisplaySn(@Param("year") int year, @Param("displaySn") String displaySn);

    @ResultMap("persistence.member.ApplySnMapper.BaseResultMap")
    @Select("select * from ow_apply_sn where sn=#{sn} and range_id=#{rangeId}")
    ApplySn getApplySnByRangeId(@Param("rangeId") int rangeId, @Param("sn") long sn);

    Map selectMemberTeacherCount(@Param("addPermits") Boolean addPermits,
                                 @Param("adminPartyIdList") List<Integer> adminPartyIdList,
                                 @Param("adminBranchIdList") List<Integer> adminBranchIdList);

    Map selectMemberStudentCount(@Param("addPermits") Boolean addPermits,
                                 @Param("adminPartyIdList") List<Integer> adminPartyIdList,
                                 @Param("adminBranchIdList") List<Integer> adminBranchIdList);

    @Select("select max(code) from ow_member_stay where left(code, 4)=#{year}")
    String getMemberStayMaxCode(@Param("year") int year);

    // 根据账号、姓名、学工号查找 不是 党员的用户
    List<SysUserView> selectNotMemberList(@Param("query") String query,
                                          @Param("regRoleStr") String regRoleStr, RowBounds rowBounds);

    int countNotMemberList(@Param("query") String query, @Param("regRoleStr") String regRoleStr);

    // 党员发展申请数量分阶段统计
    List<MemberApplyCount> selectMemberApplyCount(@Param("addPermits") Boolean addPermits,
                                                  @Param("adminPartyIdList") List<Integer> adminPartyIdList,
                                                  @Param("adminBranchIdList") List<Integer> adminBranchIdList);

    // 根据类别、状态、账号、姓名、学工号查找流入党员
    List<MemberInflow> selectMemberInflowList(@Param("type") Byte type,
                                              @Param("inflowStatus") Byte inflowStatus,
                                              @Param("hasOutApply") Boolean hasOutApply, // 是否已经提交申请
                                              @Param("search") String search,
                                              @Param("addPermits") Boolean addPermits,
                                              @Param("adminPartyIdList") List<Integer> adminPartyIdList,
                                              @Param("adminBranchIdList") List<Integer> adminBranchIdList, RowBounds rowBounds);

    int countMemberInflowList(@Param("type") Byte type,
                              @Param("inflowStatus") Byte inflowStatus,
                              @Param("hasOutApply") Boolean hasOutApply,
                              @Param("search") String search,
                              @Param("addPermits") Boolean addPermits,
                              @Param("adminPartyIdList") List<Integer> adminPartyIdList,
                              @Param("adminBranchIdList") List<Integer> adminBranchIdList);

    // 党员发展退回至状态
    //====================start

    // 退回至 预备党员
    @Update("update ow_member_apply set stage=" + OwConstants.OW_APPLY_STAGE_GROW
            + ", positive_status=null, positive_time=null "
            + "where user_id=#{userId} and stage in(" + OwConstants.OW_APPLY_STAGE_POSITIVE
            + "," + OwConstants.OW_APPLY_STAGE_GROW + ")")
    int memberApplyBackToGrow(@Param("userId") int userId);

    // 退回至 领取志愿书（初始状态）
    @Update("update ow_member_apply set stage=" + OwConstants.OW_APPLY_STAGE_DRAW
            + ", grow_status=null, grow_time=null "
            + ", positive_status=null, positive_time=null "
            + "where user_id=#{userId} and stage>=" + OwConstants.OW_APPLY_STAGE_DRAW)
    void memberApplyBackToDraw(@Param("userId") int userId);

    // 退回至 列入发展计划（初始状态）
    @Update("update ow_member_apply set stage=" + OwConstants.OW_APPLY_STAGE_PLAN
            + ", draw_status=null, draw_time=null "
            + ", grow_status=null, grow_time=null"
            + ", positive_status=null, positive_time=null "
            + "where user_id=#{userId} and stage>=" + OwConstants.OW_APPLY_STAGE_PLAN)
    void memberApplyBackToPlan(@Param("userId") int userId);

    // 退回至 发展对象（初始状态）
    @Update("update ow_member_apply set stage=" + OwConstants.OW_APPLY_STAGE_CANDIDATE
            + ", plan_time=null, plan_status=null"
            + ", draw_status=null, draw_time=null"
            + ", grow_status=null, grow_time=null"
            + ", positive_status=null, positive_time=null "
            + " where user_id=#{userId} and stage>=" + OwConstants.OW_APPLY_STAGE_CANDIDATE)
    void memberApplyBackToCandidate(@Param("userId") int userId);

    // 退回至 积极分子（初始状态）
    @Update("update ow_member_apply set stage=" + OwConstants.OW_APPLY_STAGE_ACTIVE
            + ", candidate_time=null,candidate_train_start_time=null,candidate_train_end_time=null"
            + ",candidate_grade=null,candidate_status=null, active_train_start_time=null, active_train_end_time=null,active_grade=null"
            + ", plan_time=null, plan_status=null"
            + ", draw_status=null, draw_time=null"
            + ", grow_status=null, grow_time=null"
            + ", positive_status=null, positive_time=null "
            + " where user_id=#{userId} and stage>=" + OwConstants.OW_APPLY_STAGE_ACTIVE)
    void memberApplyBackToActive(@Param("userId") int userId);

    // 退回至 申请（初始状态）
    @Update("update ow_member_apply set stage=" + OwConstants.OW_APPLY_STAGE_INIT
            + ", active_time=null, pass_time=null"
            + ", candidate_time=null,candidate_train_start_time=null,candidate_train_end_time=null"
            + ",candidate_grade=null,candidate_status=null, active_train_start_time=null, active_train_end_time=null,active_grade=null"
            + ", plan_time=null, plan_status=null"
            + ", draw_status=null, draw_time=null"
            + ", grow_status=null, grow_time=null"
            + ", positive_status=null, positive_time=null "
            + " where user_id=#{userId} and stage>=" + OwConstants.OW_APPLY_STAGE_INIT)
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
    @Update("update ${tableName} tmp, ow_branch ob set tmp.party_id=ob.party_id where " +
            "ob.id in (${brachIds}) and tmp.branch_id=ob.id")
    void batchTransfer(@Param("tableName") String tableName, @Param("brachIds") String brachIds);

    // 单独用于校内转接
    @Update("update ow_member_transfer tmp, ow_branch ob set tmp.to_party_id=ob.party_id where " +
            "ob.id in (${brachIds}) and tmp.to_branch_id=ob.id")
    void batchTransfer2(@Param("brachIds") String brachIds);

    // 党员发展退回至状态
    //====================end

    // 党员出党：退回
    @Update("update ow_member_quit set status= #{status}"
            + " where user_id=#{userId} and status >= #{status} and status<"
            + MemberConstants.MEMBER_QUIT_STATUS_OW_VERIFY)
    void memberQuit_back(@Param("userId") int userId, @Param("status") byte status);

    // 组织关系转入：退回
    @Update("update ow_member_in set status= #{status}"
            + " where id=#{id} and status >= #{status} and status<"
            + MemberConstants.MEMBER_IN_STATUS_OW_VERIFY)
    void memberIn_back(@Param("id") int id, @Param("status") byte status);

    // 党员出国：退回
    @Update("update ow_member_stay set status= #{status}"
            + " where id=#{id} and status >= #{status} and status<"
            + MemberConstants.MEMBER_STAY_STATUS_OW_VERIFY)
    void memberStay_back(@Param("id") int id, @Param("status") byte status);

    // 校内转接：退回
    @Update("update ow_member_transfer set status= #{status}"
            + " where id=#{id} and status >= #{status} and status<"
            + MemberConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY)
    void memberTransfer_back(@Param("id") int id, @Param("status") byte status);

    // 转出：退回
    @Update("update ow_member_out set status= #{status}"
            + " where id=#{id} and status >= #{status} and status<"
            + MemberConstants.MEMBER_OUT_STATUS_OW_VERIFY)
    void memberOut_back(@Param("id") int id, @Param("status") byte status);

    // 归国：退回
    @Update("update ow_member_return set status= #{status}"
            + " where id=#{id} and status >= #{status} and status<"
            + MemberConstants.MEMBER_RETURN_STATUS_PARTY_VERIFY)
    void memberReturn_back(@Param("id") int id, @Param("status") byte status);

    // 流入：退回
    @Update("update ow_member_inflow set inflow_status= #{status}"
            + " where id=#{id} and inflow_status >= #{status} and inflow_status<"
            + MemberConstants.MEMBER_INFLOW_STATUS_PARTY_VERIFY)
    void memberInflow_back(@Param("id") int id, @Param("status") byte status);

    // 流入转出：退回
    @Update("update ow_member_inflow set out_status= #{status}"
            + " where id=#{id} and out_status >= #{status} and out_status<"
            + MemberConstants.MEMBER_INFLOW_OUT_STATUS_PARTY_VERIFY)
    void memberInflowOut_back(@Param("id") int id, @Param("status") byte status);

    // 流出：退回
    @Update("update ow_member_outflow set status= #{status}"
            + " where id=#{id} and status >= #{status} and status<"
            + MemberConstants.MEMBER_OUTFLOW_STATUS_PARTY_VERIFY)
    void memberOutflow_back(@Param("id") int id, @Param("status") byte status);

    // 清空是否退回状态
    @Update("update ${tableName} set ${isBackName}=#{isBack} where ${idName}=#{id}")
    int resetIsBack(@Param("tableName") String tableName,
                    @Param("isBackName") String isBackName, @Param("isBack") Boolean isBack,
                    @Param("idName") String idName, @Param("id") int id);

    // 清除退休时间
    @Update("update sys_teacher_info set retire_time=null where user_id=#{userId}")
    void del_retireTime(@Param("userId") int userId);

    @Update("update ow_apply_open_time set party_id=null, branch_id=null where id=#{id}")
    void globalApplyOpenTime(@Param("id") int id);

    // 更新志愿书编码端使用情况
    @Update("update ow_apply_sn_range asr," +
            "(select range_id, sum(if(is_used=1 and is_abolished=0, 1, 0)) as use_count, " +
            "sum(if(is_abolished=1, 1, 0)) as abolish_count from ow_apply_sn group by range_id) as tmp " +
            "set asr.use_count=tmp.use_count, asr.abolish_count=tmp.abolish_count " +
            "where asr.id=tmp.range_id and asr.id=#{rangeId}")
    void updateApplySnRangeCount(@Param("rangeId") int rangeId);

    // 查询状态为“组织部审批通过”的记录（不包含已归档记录）
    List<MemberOutView> selectMemberOutList(@Param("query")String query,
                                          @Param("type") Byte type,
                                            @Param("addPermits") Boolean addPermits,
                                            @Param("adminPartyIdList") List<Integer> adminPartyIdList,
                                            @Param("adminBranchIdList") List<Integer> adminBranchIdList,
                                          RowBounds rowBounds);
    int countMemberOutList(@Param("query")String query,
                           @Param("type") Byte type,
                              @Param("addPermits") Boolean addPermits,
                              @Param("adminPartyIdList") List<Integer> adminPartyIdList,
                              @Param("adminBranchIdList") List<Integer> adminBranchIdList);

    Map countMemberNotIntegrity(@Param("partyId") Integer partyId,
                                @Param("branchId") Integer branchId);
}
