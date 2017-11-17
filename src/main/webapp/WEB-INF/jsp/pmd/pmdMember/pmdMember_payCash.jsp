<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>缴费信息确认</h3>
</div>
<div class="modal-body">
    <table class="table table-bordered table-unhover">
        <tr>
            <td class="bg-right">月份</td>
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
            <td class="bg-right">应交金额</td>
            <td>${pmdMember.duePay}</td>
        </tr>
        <tr>
            <td class="bg-right">支付方式</td>
            <td>现金缴费</td>
        </tr>
        <tr>
            <td class="bg-right">实交金额</td>
            <td>${pmdMember.duePay}</td>
        </tr>
        <tr>
            <td class="bg-right">收款人</td>
            <td>${_user.realname}</td>
        </tr>
    </table>
</div>
<div class="modal-footer">
    <input id="submitBtn" type="button" class="btn btn-primary" value=" 确认缴费"/>
</div>
<script>
    $("#submitBtn").click(function () {
        var $this = $(this);
        $.post("${ctx}/pmd/pmdMember_payCash",{id:${pmdMember.id}},function(ret){
            if(ret.success){
                $("#modal").modal('hide');
                $("#jqGrid2").trigger("reloadGrid");
            }
        });
    });
</script>