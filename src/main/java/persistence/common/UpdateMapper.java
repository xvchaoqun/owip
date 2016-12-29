package persistence.common;

import domain.member.MemberExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import sys.constants.SystemConstants;

import java.util.Date;
import java.util.List;

/**
 * Created by fafa on 2016/1/5.
 */
public interface UpdateMapper {

    @Update("${sql}")
    void excuteSql(@Param("sql") String sql);

    // 更新发文提交的干部任免数量
    @Update("update dispatch bd, (select dispatch_id, sum(IF(type=1, 1, 0)) as real_appoint_count, sum(IF(type=2, 1, 0)) as real_dismiss_count from dispatch_cadre group by dispatch_id) bdc set bd.real_appoint_count= bdc.real_appoint_count, " +
            "bd.real_dismiss_count=bdc.real_dismiss_count where bd.id=bdc.dispatch_id")
    void update_dispatch_real_count();

    @Update("update ow_apply_open_time set party_id=null, branch_id=null where id=#{id}")
    void globalApplyOpenTime(@Param("id") int id);

    @Update("update dispatch set file=null, file_name=null where id=#{id}")
    void del_dispatch_file(@Param("id") int id);
    @Update("update dispatch set ppt=null, ppt_name=null where id=#{id}")
    void del_dispatch_ppt(@Param("id") int id);

    @Update("update cadre_edu set degree=null, is_high_degree=null, degree_country=null, degree_unit=null, degree_time=null where id=#{id}")
    void del_caderEdu_hasDegree(@Param("id") int id);

    @Update("update cadre_work set unit_id=null where id=#{id}")
    void del_cadreWork_unitId(@Param("id") int id);

    @Update("update cadre_post set double_unit_id=null where id=#{id}")
    void del_cadrePost_doubleUnitId(@Param("id") int id);

    // 清除退休时间
    @Update("update sys_teacher_info set retire_time=null where user_id=#{userId}")
    void del_retireTime(@Param("userId") int userId);

    @Update("update modify_cadre_auth set start_time=null, end_time=null, is_unlimited=1 where id=#{id}")
    void del_ModifyCadreAuth_time(@Param("id") int id);

    // 如果修改成直属党支部， 则将支部ID设置为NULL
    @Update("update ${tableName} set party_id=#{partyId}, branch_id=null where ${idName}=#{id}")
    int updateToDirectBranch(@Param("tableName") String tableName, @Param("idName") String idName,
                             @Param("id") int id, @Param("partyId") int partyId);

    // 批量转校内组织关系
    int changeMemberParty(@Param("partyId") Integer partyId, @Param("branchId") Integer branchId,
                          @Param("example") MemberExample example);

    int increaseMemberOutPrintCount(@Param("idList") List<Integer> idList,
                                    @Param("lastPrintTime") Date lastPrintTime,
                                    @Param("lastPrintUserId") Integer lastPrintUserId);
    // 入党申请打回至状态
    //====================start

    @Update("update ow_member_apply set stage="+SystemConstants.APPLY_STAGE_GROW
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

    // 暂留：打回
    @Update("update ow_member_stay set status= #{status}"
            +" where id=#{id} and status >= #{status} and status<"
            + SystemConstants.MEMBER_STAY_STATUS_OW_VERIFY)
    void memberStay_back(@Param("id") int id, @Param("status") byte status);

    // 党员出国：打回
    @Update("update ow_graduate_abroad set status= #{status}"
            +" where id=#{id} and status >= #{status} and status<"
            + SystemConstants.GRADUATE_ABROAD_STATUS_OW_VERIFY)
    void graduateAbroad_back(@Param("id") int id, @Param("status") byte status);

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

    // 领取证件：重置归还状态为 “未归还”
    @Update("update abroad_passport_draw apd, abroad_passport p set p.is_lent=1, apd.draw_status="+SystemConstants.PASSPORT_DRAW_DRAW_STATUS_DRAW
            +" , apd.use_record=null, apd.attachment_filename=null,apd.attachment=null, apd.real_start_date=null, apd.real_end_date=null," +
            "apd.real_to_country=null, apd.return_remark=null," +
            "apd.use_passport=null, apd.real_return_date=null where apd.id=#{id} and p.id=apd.passport_id")
    int resetReturnPassport(@Param("id") int id);
}
