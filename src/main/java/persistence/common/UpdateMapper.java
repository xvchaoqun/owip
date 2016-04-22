package persistence.common;

import domain.Member;
import domain.MemberExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import sys.constants.SystemConstants;

/**
 * Created by fafa on 2016/1/5.
 */
public interface UpdateMapper {

    @Update("update base_dispatch set file=null, file_name=null where id=#{id}")
    void del_dispatch_file(@Param("id") int id);
    @Update("update base_dispatch set ppt=null, ppt_name=null where id=#{id}")
    void del_dispatch_ppt(@Param("id") int id);

    // 如果修改成直属党支部， 则将支部ID设置为NULL
    @Update("update ${tableName} set party_id=#{partyId}, branch_id=null where ${idName}=#{id}")
    int updateToDirectBranch(@Param("tableName") String tableName, String idName, int id, int partyId);

    // 批量转校内组织关系
    int changeMemberParty(@Param("partyId") Integer partyId, @Param("branchId") Integer branchId,
                          @Param("example") MemberExample example);

    // 入党申请打回至状态
    //====================start
    @Update("update ow_member_apply set stage="+SystemConstants.APPLY_STAGE_PLAN
            +", grow_status=null, grow_time=null"
            +", draw_status=null, draw_time=null " +
            "where user_id=#{userId} and stage="+ SystemConstants.APPLY_STAGE_DRAW)
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

}
