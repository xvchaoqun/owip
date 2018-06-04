<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pmd/constants.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>延迟缴费</h3>
</div>
<div class="modal-body">
    <table class="table table-bordered table-unhover">
        <tr>
            <td class="bg-right" width="130">月份</td>
            <td>${cm:formatDate(pmdMember.payMonth, "yyyy年MM月")}</td>
        </tr>
        <tr>
            <td class="bg-right">工作证号</td>
            <td>${pmdMember.user.code}</td>
        </tr>
        <tr>
            <td class="bg-right">姓名</td>
            <td>${pmdMember.user.realname}</td>
        </tr>
        <tr>
            <td class="bg-right">所属党委</td>
            <td>${partyMap.get(pmdMember.partyId).name}</td>
        </tr>
        <c:if test="${not empty pmdMember.branchId}">
            <tr>
                <td class="bg-right">所属党支部</td>
                <td>${branchMap.get(pmdMember.branchId).name}</td>
            </tr>
        </c:if>
        <tr>
            <td class="bg-right">党员类别</td>
            <td>${PMD_MEMBER_TYPE_MAP.get(pmdMember.type)}</td>
        </tr>
        <tr>
            <td class="bg-right"> 延迟缴费原因</td>
            <td>
                <textarea id="delayReason" class="limited" maxlength="100" style="width: 100%"></textarea>
            </td>
        </tr>
    </table>
</div>
<div class="modal-footer">
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> 确认</button>
</div>
<script>
    $("#submitBtn").click(function () {
        var $this = $(this);
        var delayReason = $.trim($("#delayReason").val());
        if(delayReason==''){
            $("#delayReason").focus();
            return;
        }
        $.post("${ctx}/pmd/pmdMember_delay",{id:${pmdMember.id}, delayReason:delayReason},function(ret){
            if(ret.success){
                $("#modal").modal('hide');
                $("#jqGrid2").trigger("reloadGrid");
            }
        });
    });
    $('textarea.limited').inputlimiter();
</script>