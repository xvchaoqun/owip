package persistence.common;

import bean.*;
import domain.abroad.ApplySelf;
import domain.abroad.ApprovalOrder;
import domain.abroad.Passport;
import domain.cadre.CadreFamliy;
import domain.cadre.CadreLeader;
import domain.cadre.CadrePost;
import domain.cadre.CadreView;
import domain.dispatch.DispatchCadreView;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;
import sys.constants.SystemConstants;

import java.util.List;
import java.util.Map;

public interface SelectMapper {

    // 获取主职或兼职在某单位的现任干部
    @Select("select cp.* from cadre_post cp , cadre c where cp.cadre_id=c.id and " +
            "c.status in("+SystemConstants.CADRE_STATUS_MIDDLE+","+ SystemConstants.CADRE_STATUS_LEADER +") " +
            "order by c.sort_order desc, cp.is_main_post desc, cp.sort_order desc")
    public List<CadrePost> findCadrePosts(int unitId);

    // 获取2013年以来离任干部
    /*@ResultMap("persistence.cadre.CadreViewMapper.BaseResultMap")
    @Select("select cv.* from cadre_view cv, dispatch_cadre_view dcv where cv.id=dcv.cadre_id and cv.status=3 and cv.unit_id=#{unitId} and dcv.type=2 and dcv.year between 2013 and 2017")
    List<CadreView> leaveCadres(@Param("unitId")int unitId);*/
    @ResultMap("persistence.dispatch.DispatchCadreViewMapper.BaseResultMap")
    @Select("select dcv.* from dispatch_cadre_view dcv left join cadre_view cv on cv.id=dcv.cadre_id where dcv.unit_id=#{unitId} and dcv.type=2 and dcv.year between 2013 and 2017 order by  dcv.year desc, cv.sort_order desc")
    List<DispatchCadreView> leaveDispatchCadres(@Param("unitId")int unitId);

    @Select("select cadre_id from modify_cadre_auth where is_unlimited=1 or " +
            "(is_unlimited=0 and ( (curdate() between start_time and end_time) " +
            "or (start_time is null and curdate() <= end_time) " +
            "or (end_time is null and curdate() >= start_time)))")
    List<Integer> selectValidModifyCadreAuth();

    @ResultMap("persistence.abroad.ApprovalOrderMapper.BaseResultMap")
    @Select("select aao.* from abroad_approval_order aao, abroad_approver_type aat " +
            "where aao.applicat_type_id=#{applicatTypeId} and aao.approver_type_id = aat.id order by aat.sort_order desc")
    List<ApprovalOrder> selectApprovalOrderList(@Param("applicatTypeId") int applicatTypeId, RowBounds rowBounds);
    @Select("select count(*) from abroad_approval_order where applicat_type_id=#{applicatTypeId}")
    int countApprovalOrders(@Param("applicatTypeId") int applicatTypeId);

    // 查询干部家庭成员
    List<CadreFamliy> getCadreFamliys(@Param("cadreIds") Integer[] cadreIds, @Param("status") Byte status);

    //查询校领导的分管单位
    @Select("select blu.unit_id from cadre_leader_unit blu, cadre_leader bl " +
            "where  bl.cadre_id = #{cadreId} and blu.leader_id = bl.id and blu.type_id=#{leaderTypeId}")
    List<Integer> getLeaderManagerUnitId(@Param("cadreId") Integer cadreId, @Param("leaderTypeId") Integer leaderTypeId);

    //查询分管当前单位的校领导
    @ResultMap("persistence.cadre.CadreLeaderMapper.BaseResultMap")
    @Select("select bl.* from cadre_leader_unit blu, cadre_leader bl " +
            "where  blu.type_id=#{leaderTypeId} and blu.unit_id = #{unitId} and blu.leader_id = bl.id")
    List<CadreLeader> getManagerUnitLeaders(@Param("unitId") Integer unitId, @Param("leaderTypeId") Integer leaderTypeId);

    @ResultType(bean.ApplySelfModifyBean.class)
    @Select("select modify_proof as modifyProof, modify_proof_file_name as modifyProofFileName,remark from abroad_apply_self_modify " +
            "where apply_id=#{applyId} and modify_type=" + SystemConstants. APPLYSELF_MODIFY_TYPE_MODIFY)
    List<ApplySelfModifyBean> getApplySelfModifyList(@Param("applyId") Integer applyId);

    // 其他审批人身份的干部，查找他需要审批的干部
    @Select("select bc.id from abroad_applicat_cadre aac, abroad_applicat_type aat, cadre bc where aat.id in(" +
            "select aao.applicat_type_id from abroad_approver_type aat, abroad_approver aa, abroad_approval_order aao " +
            "where aa.cadre_id=#{cadreId} and aa.type_id = aat.id  and aao.approver_type_id = aat.id) and aac.type_id=aat.id " +
            "and bc.id = aac.cadre_id")
    List<Integer> getApprovalCadreIds(@Param("cadreId") Integer cadreId);


    // 其他审批人身份 的所在单位 给定一个干部id， 和审批人类别，查找他可以审批的干部
    @Select("select bc.id from abroad_applicat_cadre aac, abroad_applicat_type aat, cadre bc where aat.id in(" +
            "select aao.applicat_type_id from abroad_approver_type aat, abroad_approver aa, abroad_approval_order aao " +
            "where aa.cadre_id=#{cadreId} and aa.type_id=#{approverTypeId} and aa.type_id = aat.id  and aao.approver_type_id = aat.id) and aac.type_id=aat.id " +
            "and bc.id = aac.cadre_id")
    List<Integer> getApprovalCadreIds_approverTypeId(@Param("cadreId") Integer cadreId, @Param("approverTypeId") Integer approverTypeId);

    // 其他审批人身份 的所在单位 给定一个干部id， 和审批人类别，查找他可以审批的干部的职务属性
    @Select("select distinct bc.post_id from abroad_applicat_cadre aac, abroad_applicat_type aat, cadre bc where aat.id in(" +
            "select aao.applicat_type_id from abroad_approver_type aat, abroad_approver aa, abroad_approval_order aao " +
            "where aa.cadre_id=#{cadreId} and aa.type_id=#{approverTypeId} and aa.type_id = aat.id  and aao.approver_type_id = aat.id) and aac.type_id=aat.id " +
            "and bc.id = aac.cadre_id")
    List<Integer> getApprovalPostIds_approverTypeId(@Param("cadreId") Integer cadreId, @Param("approverTypeId") Integer approverTypeId);

        @Select("select distinct parent_code from base_location order by parent_code asc")
        List<Integer> selectDistinctLocationParentCode();

    List<Passport> selectPassportList(@Param("bean") PassportSearchBean bean, RowBounds rowBounds);
    Integer countPassport(@Param("bean") PassportSearchBean bean);
    // 获取干部证件
   // List<Passport> selectCadrePassports(@Param("cadreId") Integer cadreId);


    List<ApplySelf> selectNotApprovalList(
            @Param("searchBean") ApplySelfSearchBean searchBean,
            /* 本单位正职、分管校领导<approverType.id, List<unitId>> */
            @Param("approverTypeUnitIdListMap") Map<Integer, List<Integer>> approverTypeUnitIdListMap,
             /* 其他审批身份 <approverType.id, List<postId>> */
            @Param("approverTypePostIdListMap") Map<Integer, List<Integer>> approverTypePostIdListMap,
            RowBounds rowBounds);
    int countNotApproval(@Param("searchBean") ApplySelfSearchBean searchBean,
                        /* 本单位正职、分管校领导<approverType.id, List<unitId>> */
                         @Param("approverTypeUnitIdListMap") Map<Integer, List<Integer>> approverTypeUnitIdListMap,
             /* 其他审批身份 <approverType.id, List<postId>> */
             @Param("approverTypePostIdListMap") Map<Integer, List<Integer>> approverTypePostIdListMap);
    List<ApplySelf> selectHasApprovalList(
            @Param("searchBean") ApplySelfSearchBean searchBean,
            /* 本单位正职、分管校领导<approverType.id, List<unitId>> */
            @Param("approverTypeUnitIdListMap") Map<Integer, List<Integer>> approverTypeUnitIdListMap,
             /* 其他审批身份 <approverType.id, List<postId>> */
            @Param("approverTypePostIdListMap") Map<Integer, List<Integer>> approverTypePostIdListMap,
            @Param("flowUserId") Integer flowUserId,
            RowBounds rowBounds);
    int countHasApproval(
            @Param("searchBean") ApplySelfSearchBean searchBean,
                        /* 本单位正职、分管校领导<approverType.id, List<unitId>> */
                         @Param("approverTypeUnitIdListMap") Map<Integer, List<Integer>> approverTypeUnitIdListMap,
             /* 其他审批身份 <approverType.id, List<postId>> */
             @Param("approverTypePostIdListMap") Map<Integer, List<Integer>> approverTypePostIdListMap,
             @Param("flowUserId") Integer flowUserId);

    List<SafeBoxBean> listSafeBoxs(RowBounds rowBounds);

    int passportCount();

    List<PassportStatByClassBean> passportStatByClass();

    List<PassportStatByLentBean> passportStatByLent();

    List<PassportStatByPostBean> passportStatByPost(@Param("selfPassportTypeId") Integer selfPassportTypeId,
                                                    @Param("twPassportTypeId") Integer twPassportTypeId);
}
