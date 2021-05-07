package persistence.parttime.common;

import domain.parttime.ParttimeApply;
import domain.parttime.ParttimeApprovalOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;
import sys.constants.AbroadConstants;

import java.util.List;
import java.util.Map;

public interface IParttimeMapper {
    // 其他审批人身份的干部，查找他需要审批的干部
    @Select("select c.id from parttime_applicat_cadre aac, parttime_applicat_type aat, cadre c where aat.id in(" +
            "select aao.applicate_type_id from parttime_approver_type aat, parttime_approver aa, parttime_approval_order aao " +
            "where aa.cadre_id=#{cadreId} and aa.type_id = aat.id  and aao.approver_type_id = aat.id) and aac.type_id=aat.id " +
            "and c.id = aac.cadre_id")
    List<Integer> getApprovalCadreIds(@Param("cadreId") Integer cadreId);

    // 其他审批人身份 的所在单位 给定一个干部id， 和审批人类别，查找他可以审批的干部的职务属性
    @Select("select distinct c.post_type from parttime_applicat_cadre aac, parttime_applicat_type aat, cadre_view c where aat.id in(" +
            "select aao.applicate_type_id from parttime_approver_type aat, parttime_approver aa, parttime_approval_order aao " +
            "where aa.cadre_id=#{cadreId} and aa.type_id=#{approverTypeId} and aa.type_id = aat.id  and aao.approver_type_id = aat.id) and aac.type_id=aat.id " +
            "and c.id = aac.cadre_id")
    List<Integer> getApprovalPostIds_approverTypeId(@Param("cadreId") Integer cadreId, @Param("approverTypeId") Integer approverTypeId);

    @Select("select modify_proof as modifyProof, modify_proof_file_name as modifyProofFileName,remark from parttime_apply_modify " +
            "where apply_id=#{applyId} and modify_type=" + AbroadConstants.ABROAD_APPLYSELF_MODIFY_TYPE_MODIFY)
    List<ParttimeApplyModifyBean> getApplyModifyList(@Param("applyId") Integer applyId);

    int countNotApproval(@Param("searchBean") ParttimeApplySearchBean searchBean,
            /* 本单位正职、分管校领导<approverType.id, List<unitId>> */
                         @Param("approverTypeUnitIdListMap") Map<Integer, List<Integer>> approverTypeUnitIdListMap,
            /* 其他审批身份 <approverType.id, List<postId>> */
                         @Param("approverTypeCadreIdListMap") Map<Integer, List<Integer>> approverTypeCadreIdListMap);

    int countHasApproval(
            @Param("searchBean") ParttimeApplySearchBean searchBean,
            /* 本单位正职、分管校领导<approverType.id, List<unitId>> */
            @Param("approverTypeUnitIdListMap") Map<Integer, List<Integer>> approverTypeUnitIdListMap,
            /* 其他审批身份 <approverType.id, List<postId>> */
            @Param("approverTypeCadreIdListMap") Map<Integer, List<Integer>> approverTypeCadreIdListMap,
            @Param("flowUserId") Integer flowUserId);

    List<ParttimeApply> selectNotApprovalList(
            @Param("searchBean") ParttimeApplySearchBean searchBean,
            /* 本单位正职、分管校领导<approverType.id, List<unitId>> */
            @Param("approverTypeUnitIdListMap") Map<Integer, List<Integer>> approverTypeUnitIdListMap,
            /* 其他审批身份 <approverType.id, List<postId>> */
            @Param("approverTypeCadreIdListMap") Map<Integer, List<Integer>> approverTypeCadreIdListMap,
            RowBounds rowBounds);

    List<ParttimeApply> selectHasApprovalList(
            @Param("searchBean") ParttimeApplySearchBean searchBean,
            /* 本单位正职、分管校领导<approverType.id, List<unitId>> */
            @Param("approverTypeUnitIdListMap") Map<Integer, List<Integer>> approverTypeUnitIdListMap,
            /* 其他审批身份 <approverType.id, List<postId>> */
            @Param("approverTypeCadreIdListMap") Map<Integer, List<Integer>> approverTypeCadreIdListMap,
            @Param("flowUserId") Integer flowUserId,
            RowBounds rowBounds);

    @Select("select count(*) from parttime_approval_order where applicate_type_id=#{applicatTypeId}")
    int countApprovalOrders(@Param("applicatTypeId") int applicatTypeId);

    @ResultMap("persistence.parttime.ParttimeApprovalOrderMapper.BaseResultMap")
    @Select("select aao.* from parttime_approval_order aao, parttime_approver_type aat " +
            "where aao.applicate_type_id=#{applicatTypeId} and aao.approver_type_id = aat.id order by aat.sort_order desc")
    List<ParttimeApprovalOrder> selectApprovalOrderList(@Param("applicatTypeId") int applicatTypeId, RowBounds rowBounds);
}
