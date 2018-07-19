package persistence.abroad.common;

import domain.abroad.ApplySelf;
import domain.abroad.ApprovalOrder;
import domain.abroad.Approver;
import domain.abroad.Passport;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;
import sys.constants.AbroadConstants;

import java.util.List;
import java.util.Map;

public interface IAbroadMapper {

    @ResultMap("persistence.abroad.ApprovalOrderMapper.BaseResultMap")
    @Select("select aao.* from abroad_approval_order aao, abroad_approver_type aat " +
            "where aao.applicat_type_id=#{applicatTypeId} and aao.approver_type_id = aat.id order by aat.sort_order desc")
    List<ApprovalOrder> selectApprovalOrderList(@Param("applicatTypeId") int applicatTypeId, RowBounds rowBounds);

    @Select("select count(*) from abroad_approval_order where applicat_type_id=#{applicatTypeId}")
    int countApprovalOrders(@Param("applicatTypeId") int applicatTypeId);


    @ResultType(ApplySelfModifyBean.class)
    @Select("select modify_proof as modifyProof, modify_proof_file_name as modifyProofFileName,remark from abroad_apply_self_modify " +
            "where apply_id=#{applyId} and modify_type=" + AbroadConstants.ABROAD_APPLYSELF_MODIFY_TYPE_MODIFY)
    List<ApplySelfModifyBean> getApplySelfModifyList(@Param("applyId") Integer applyId);

    // 其他审批人身份的干部，查找他需要审批的干部
    /*@Select("select bc.id from abroad_applicat_cadre aac, abroad_applicat_type aat, cadre bc where aat.id in(" +
            "select aao.applicat_type_id from abroad_approver_type aat, abroad_approver aa, abroad_approval_order aao " +
            "where aa.cadre_id=#{cadreId} and aa.type_id = aat.id  and aao.approver_type_id = aat.id) and aac.type_id=aat.id " +
            "and bc.id = aac.cadre_id")
    List<Integer> getApprovalCadreIds(@Param("cadreId") Integer cadreId);*/

    // 审批人类型为“本单位人员”时，查找某个单位（包含兼审单位）的审批人
    @ResultMap("persistence.abroad.ApproverMapper.BaseResultMap")
    @Select("select aa.* from abroad_approver aa, cadre c where aa.cadre_id=c.id and aa.type_id=#{approverTypeId} and c.unit_id=#{unitId} " +
            "union all select aa.* from abroad_approver aa, abroad_additional_post aap " +
            "where aa.cadre_id=aap.cadre_id and aa.type_id=#{approverTypeId} and aap.unit_id=#{unitId}")
    List<Approver> findApprovarByTypeAndUnit(@Param("approverTypeId") int approverTypeId, @Param("unitId") int unitId);

    // 根据审批人身份（除本单位正职、分管校领导、本单位人员外），查找某个审批人可以审批的干部
    @Select("select bc.id from abroad_applicat_cadre aac, abroad_applicat_type aat, cadre bc where aat.id in(" +
            "select aao.applicat_type_id from abroad_approver_type aat, abroad_approver aa, abroad_approval_order aao " +
            "where aa.cadre_id=#{cadreId} and aa.type_id=#{approverTypeId} and aa.type_id = aat.id  and aao.approver_type_id = aat.id) " +
            "and aac.type_id=aat.id and bc.id = aac.cadre_id")
    List<Integer> getApprovalCadreIds_approverTypeId(@Param("cadreId") Integer cadreId, @Param("approverTypeId") Integer approverTypeId);

    // 审批人身份为本单位人员时，查找某个审批人可以审批的干部
    @Select("select bc.id from abroad_applicat_cadre aac, abroad_applicat_type aat, cadre bc where aat.id in(" +
            "select aao.applicat_type_id from abroad_approver_type aat, abroad_approver aa, abroad_approval_order aao " +
            "where aa.cadre_id=#{cadreId} and aa.type_id=#{approverTypeId} and aa.type_id = aat.id  and aao.approver_type_id = aat.id) " +
            "and bc.unit_id in(select unit_id from cadre where id=#{cadreId} union all select unit_id from abroad_additional_post where cadre_id=#{cadreId})" +
            "and aac.type_id=aat.id and bc.id = aac.cadre_id")
    List<Integer> getApprovalCadreIds_approverTypeIdOfUnit(@Param("cadreId") Integer cadreId,
                                                           @Param("approverTypeId") Integer approverTypeId);

    // 在指定的单位中，查找需要某种审批身份审批的申请人
    @Select("select bc.id from abroad_applicat_cadre aac, abroad_approval_order aao, cadre bc where " +
            "aao.approver_type_id=#{approverTypeId} and aac.type_id=aao.applicat_type_id " +
            "and bc.id = aac.cadre_id and bc.unit_id in(${unitIdStr})")
    List<Integer> getApprovalCadreIds_approverTypeIdInUnits( @Param("approverTypeId") Integer approverTypeId,
                                                             @Param("unitIdStr") String unitIdStr);

    // 其他审批人身份 的所在单位 给定一个干部id， 和审批人类别，查找他可以审批的干部的职务属性
    /*@Select("select distinct bc.post_id from abroad_applicat_cadre aac, abroad_applicat_type aat, cadre bc where aat.id in(" +
            "select aao.applicat_type_id from abroad_approver_type aat, abroad_approver aa, abroad_approval_order aao " +
            "where aa.cadre_id=#{cadreId} and aa.type_id=#{approverTypeId} and aa.type_id = aat.id  and aao.approver_type_id = aat.id) and aac.type_id=aat.id " +
            "and bc.id = aac.cadre_id")
    List<Integer> getApprovalPostIds_approverTypeId(@Param("cadreId") Integer cadreId, @Param("approverTypeId") Integer approverTypeId);
    */
    List<Passport> selectPassportList(@Param("bean") PassportSearchBean bean, RowBounds rowBounds);

    Integer countPassport(@Param("bean") PassportSearchBean bean);
    // 获取干部证件
    // List<Passport> selectCadrePassports(@Param("cadreId") Integer cadreId);


    List<ApplySelf> selectNotApprovalList(
            @Param("searchBean") ApplySelfSearchBean searchBean,
            /*<approverType.id, List<cadreId>> */
            @Param("approverTypeCadreIdListMap") Map<Integer, List<Integer>> approverTypeCadreIdListMap,
            RowBounds rowBounds);

    int countNotApproval(@Param("searchBean") ApplySelfSearchBean searchBean,
                        /*<approverType.id, List<cadreId>> */
                         @Param("approverTypeCadreIdListMap") Map<Integer, List<Integer>> approverTypeCadreIdListMap);

    List<ApplySelf> selectHasApprovalList(
            @Param("searchBean") ApplySelfSearchBean searchBean,
            /*<approverType.id, List<cadreId>> */
            @Param("approverTypeCadreIdListMap") Map<Integer, List<Integer>> approverTypeCadreIdListMap,
            @Param("flowUserId") Integer flowUserId,
            RowBounds rowBounds);

    int countHasApproval(
            @Param("searchBean") ApplySelfSearchBean searchBean,
            /*<approverType.id, List<cadreId>> */
            @Param("approverTypeCadreIdListMap") Map<Integer, List<Integer>> approverTypeCadreIdListMap,
            @Param("flowUserId") Integer flowUserId);

    List<SafeBoxBean> selectSafeBoxList(RowBounds rowBounds);

    int passportCount();

    List<PassportStatByClassBean> passportStatByClass();

    List<PassportStatByLentBean> passportStatByLent();

    List<PassportStatByPostBean> passportStatByPost(@Param("selfPassportTypeId") Integer selfPassportTypeId,
                                                    @Param("twPassportTypeId") Integer twPassportTypeId);


    // 领取证件：重置归还状态为 “未归还”
    @Update("update abroad_passport_draw apd, abroad_passport p set p.is_lent=1, apd.draw_status="+AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_DRAW
            +" , apd.use_record=null, apd.attachment_filename=null,apd.attachment=null, apd.real_start_date=null, apd.real_end_date=null," +
            "apd.real_to_country=null, apd.return_remark=null," +
            "apd.use_passport=null, apd.real_return_date=null where apd.id=#{id} and p.id=apd.passport_id")
    int resetReturnPassport(@Param("id") int id);
}
