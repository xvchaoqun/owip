package persistence.common;

import bean.SafeBoxBean;
import domain.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface SelectMapper {

    @ResultMap("persistence.ApprovalOrderMapper.BaseResultMap")
    @Select("select aao.* from abroad_approval_order aao, abroad_approver_type aat " +
            "where aao.applicat_type_id=#{applicatTypeId} and aao.approver_type_id = aat.id order by aat.sort_order desc")
    List<ApprovalOrder> selectApprovalOrderList(@Param("applicatTypeId") int applicatTypeId, RowBounds rowBounds);
    @Select("select count(*) from abroad_approval_order where applicat_type_id=#{applicatTypeId}")
    int countApprovalOrders(@Param("applicatTypeId") int applicatTypeId);

    //查询校领导的分管单位
    @Select("select blu.unit_id from base_leader_unit blu, base_leader bl " +
            "where  bl.cadre_id = #{cadreId} and blu.leader_id = bl.id and blu.type_id=#{leaderTypeId}")
    List<Integer> getLeaderManagerUnitId(@Param("cadreId") Integer cadreId, @Param("leaderTypeId") Integer leaderTypeId);

    // 其他审批人身份的干部，查找他需要审批的干部
    @Select("select bc.id from abroad_applicat_post aap, abroad_applicat_type aat, base_cadre bc where aat.id in(" +
            "select aao.applicat_type_id from abroad_approver_type aat, abroad_approver aa, abroad_approval_order aao " +
            "where aa.cadre_id=#{cadreId} and aa.type_id = aat.id  and aao.approver_type_id = aat.id) and aap.type_id=aat.id " +
            "and bc.post_id = aap.post_id")
    List<Integer> getApprovalCadreIds(@Param("cadreId") Integer cadreId);

    // 其他审批人身份的干部，查找他需要审批的职务属性
    @Select("select aap.post_id from abroad_applicat_post aap, abroad_applicat_type aat where aat.id in(" +
            "select aao.applicat_type_id from abroad_approver_type aat, abroad_approver aa, abroad_approval_order aao " +
            "where aa.cadre_id=#{cadreId} and aa.type_id = aat.id  and aao.approver_type_id = aat.id) and aap.type_id=aat.id ")
    List<Integer> getApprovalPostIds(@Param("cadreId") Integer cadreId);

    // 其他审批人身份 的所在单位 给定一个干部id， 和审批人类别，查找他可以审批的干部
    @Select("select bc.id from abroad_applicat_post aap, abroad_applicat_type aat, base_cadre bc where aat.id in(" +
            "select aao.applicat_type_id from abroad_approver_type aat, abroad_approver aa, abroad_approval_order aao " +
            "where aa.cadre_id=#{cadreId} and aa.type_id=#{approverTypeId} and aa.type_id = aat.id  and aao.approver_type_id = aat.id) and aap.type_id=aat.id " +
            "and bc.post_id = aap.post_id")
    List<Integer> getApprovalCadreIds_approverTypeId(@Param("cadreId") Integer cadreId, @Param("approverTypeId") Integer approverTypeId);

    // 其他审批人身份 的所在单位 给定一个干部id， 和审批人类别，查找他可以审批的干部
    @Select("select aap.post_id from abroad_applicat_post aap, abroad_applicat_type aat where aat.id in(" +
            "select aao.applicat_type_id from abroad_approver_type aat, abroad_approver aa, abroad_approval_order aao " +
            "where aa.cadre_id=#{cadreId} and aa.type_id=#{approverTypeId} and aa.type_id = aat.id  and aao.approver_type_id = aat.id) and aap.type_id=aat.id ")
    List<Integer> getApprovalPostIds_approverTypeId(@Param("cadreId") Integer cadreId, @Param("approverTypeId") Integer approverTypeId);

    List<DispatchCadre> selectDispatchCadrePage(
            @Param("dispatchId") Integer dispatchId,
            @Param("wayId") Integer wayId,
            @Param("procedureId") Integer procedureId,
            @Param("cadreId") Integer cadreId,
            @Param("adminLevelId") Integer adminLevelId,
            @Param("unitId") Integer unitId, RowBounds rowBounds);

        @Select("select distinct parent_code from base_location order by parent_code asc")
        List<Integer> selectDistinctLocationParentCode();

    List<Passport> selectPassportList(@Param("cadreId") Integer cadreId,
                                      @Param("classId") Integer classId,
                                      @Param("code") String code,
                                      @Param("type") Byte type,
                                      @Param("safeBoxId") Integer safeBoxId,
                                      @Param("cancelConfirm") Byte cancelConfirm,
                                      @Param("abolish") Boolean abolish, RowBounds rowBounds);
    Integer countPassport(@Param("cadreId") Integer cadreId,
                              @Param("classId") Integer classId,
                              @Param("code") String code,
                              @Param("type") Byte type,
                              @Param("safeBoxId") Integer safeBoxId,
                              @Param("cancelConfirm") Byte cancelConfirm,
                              @Param("abolish") Boolean abolish);
    // 获取干部证件
   // List<Passport> selectCadrePassports(@Param("cadreId") Integer cadreId);


    List<ApplySelf> selectNotApprovalList(
            /* 本单位正职、分管校领导<approverType.id, List<unitId>> */
            @Param("approverTypeUnitIdListMap") Map<Integer, List<Integer>> approverTypeUnitIdListMap,
             /* 其他审批身份 <approverType.id, List<postId>> */
            @Param("approverTypePostIdListMap") Map<Integer, List<Integer>> approverTypePostIdListMap,
            RowBounds rowBounds);
    int countNotApproval(/* 本单位正职、分管校领导<approverType.id, List<unitId>> */
                         @Param("approverTypeUnitIdListMap") Map<Integer, List<Integer>> approverTypeUnitIdListMap,
             /* 其他审批身份 <approverType.id, List<postId>> */
             @Param("approverTypePostIdListMap") Map<Integer, List<Integer>> approverTypePostIdListMap);
    List<ApplySelf> selectHasApprovalList(
            /* 本单位正职、分管校领导<approverType.id, List<unitId>> */
            @Param("approverTypeUnitIdListMap") Map<Integer, List<Integer>> approverTypeUnitIdListMap,
             /* 其他审批身份 <approverType.id, List<postId>> */
            @Param("approverTypePostIdListMap") Map<Integer, List<Integer>> approverTypePostIdListMap,
            @Param("flowUserId") Integer flowUserId,
            RowBounds rowBounds);
    int countHasApproval(/* 本单位正职、分管校领导<approverType.id, List<unitId>> */
                         @Param("approverTypeUnitIdListMap") Map<Integer, List<Integer>> approverTypeUnitIdListMap,
             /* 其他审批身份 <approverType.id, List<postId>> */
             @Param("approverTypePostIdListMap") Map<Integer, List<Integer>> approverTypePostIdListMap,
             @Param("flowUserId") Integer flowUserId);

    List<SafeBoxBean> listAllSafeBoxs();
}
