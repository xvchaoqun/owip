package persistence.cla.common;

import domain.cla.ClaApply;
import domain.cla.ClaApprovalOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;
import sys.constants.AbroadConstants;

import java.util.List;
import java.util.Map;

public interface IClaMapper {

    @ResultMap("persistence.cla.ClaApprovalOrderMapper.BaseResultMap")
    @Select("select aao.* from cla_approval_order aao, cla_approver_type aat " +
            "where aao.applicat_type_id=#{applicatTypeId} and aao.approver_type_id = aat.id order by aat.sort_order desc")
    List<ClaApprovalOrder> selectApprovalOrderList(@Param("applicatTypeId") int applicatTypeId, RowBounds rowBounds);

    @Select("select count(*) from cla_approval_order where applicat_type_id=#{applicatTypeId}")
    int countApprovalOrders(@Param("applicatTypeId") int applicatTypeId);


    @Select("select modify_proof as modifyProof, modify_proof_file_name as modifyProofFileName,remark from cla_apply_modify " +
            "where apply_id=#{applyId} and modify_type=" + AbroadConstants.ABROAD_APPLYSELF_MODIFY_TYPE_MODIFY)
    List<ClaApplyModifyBean> getApplyModifyList(@Param("applyId") Integer applyId);

    // 其他审批人身份的干部，查找他需要审批的干部
    @Select("select c.id from cla_applicat_cadre aac, cla_applicat_type aat, cadre c where aat.id in(" +
            "select aao.applicat_type_id from cla_approver_type aat, cla_approver aa, cla_approval_order aao " +
            "where aa.cadre_id=#{cadreId} and aa.type_id = aat.id  and aao.approver_type_id = aat.id) and aac.type_id=aat.id " +
            "and c.id = aac.cadre_id")
    List<Integer> getApprovalCadreIds(@Param("cadreId") Integer cadreId);


    // 其他审批人身份 的所在单位 给定一个干部id， 和审批人类别，查找他可以审批的干部
    @Select("select c.id from cla_applicat_cadre aac, cla_applicat_type aat, cadre c where aat.id in(" +
            "select aao.applicat_type_id from cla_approver_type aat, cla_approver aa, cla_approval_order aao " +
            "where aa.cadre_id=#{cadreId} and aa.type_id=#{approverTypeId} and aa.type_id = aat.id  and aao.approver_type_id = aat.id) and aac.type_id=aat.id " +
            "and c.id = aac.cadre_id")
    List<Integer> getApprovalCadreIds_approverTypeId(@Param("cadreId") Integer cadreId, @Param("approverTypeId") Integer approverTypeId);

    // 其他审批人身份 的所在单位 给定一个干部id， 和审批人类别，查找他可以审批的干部的职务属性
    @Select("select distinct c.post_type from cla_applicat_cadre aac, cla_applicat_type aat, cadre c where aat.id in(" +
            "select aao.applicat_type_id from cla_approver_type aat, cla_approver aa, cla_approval_order aao " +
            "where aa.cadre_id=#{cadreId} and aa.type_id=#{approverTypeId} and aa.type_id = aat.id  and aao.approver_type_id = aat.id) and aac.type_id=aat.id " +
            "and c.id = aac.cadre_id")
    List<Integer> getApprovalPostIds_approverTypeId(@Param("cadreId") Integer cadreId, @Param("approverTypeId") Integer approverTypeId);


    List<ClaApply> selectNotApprovalList(
            @Param("searchBean") ClaApplySearchBean searchBean,
            /* 本单位正职、分管校领导<approverType.id, List<unitId>> */
            @Param("approverTypeUnitIdListMap") Map<Integer, List<Integer>> approverTypeUnitIdListMap,
             /* 其他审批身份 <approverType.id, List<postId>> */
            @Param("approverTypeCadreIdListMap") Map<Integer, List<Integer>> approverTypeCadreIdListMap,
            RowBounds rowBounds);

    int countNotApproval(@Param("searchBean") ClaApplySearchBean searchBean,
                        /* 本单位正职、分管校领导<approverType.id, List<unitId>> */
                         @Param("approverTypeUnitIdListMap") Map<Integer, List<Integer>> approverTypeUnitIdListMap,
             /* 其他审批身份 <approverType.id, List<postId>> */
                         @Param("approverTypeCadreIdListMap") Map<Integer, List<Integer>> approverTypeCadreIdListMap);

    List<ClaApply> selectHasApprovalList(
            @Param("searchBean") ClaApplySearchBean searchBean,
            /* 本单位正职、分管校领导<approverType.id, List<unitId>> */
            @Param("approverTypeUnitIdListMap") Map<Integer, List<Integer>> approverTypeUnitIdListMap,
             /* 其他审批身份 <approverType.id, List<postId>> */
            @Param("approverTypeCadreIdListMap") Map<Integer, List<Integer>> approverTypeCadreIdListMap,
            @Param("flowUserId") Integer flowUserId,
            RowBounds rowBounds);

    int countHasApproval(
            @Param("searchBean") ClaApplySearchBean searchBean,
                        /* 本单位正职、分管校领导<approverType.id, List<unitId>> */
            @Param("approverTypeUnitIdListMap") Map<Integer, List<Integer>> approverTypeUnitIdListMap,
             /* 其他审批身份 <approverType.id, List<postId>> */
            @Param("approverTypeCadreIdListMap") Map<Integer, List<Integer>> approverTypeCadreIdListMap,
            @Param("flowUserId") Integer flowUserId);
}
