<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>报送</h3>
</div>
<div class="modal-body">
    <table class="table table-bordered table-unhover">
        <tr>
            <td class="bg-right">月份</td>
            <td>${cm:formatDate(pmdMonth.payMonth, "yyyy年MM月")}</td>
        </tr>
        <tr>
            <td class="bg-right">所属党委</td>
            <td>${partyMap.get(pmdBranch.partyId).name}</td>
        </tr>
        <tr>
            <td class="bg-right">所属党支部</td>
            <td>${branchMap.get(pmdBranch.branchId).name}</td>
        </tr>
    </table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">返回</a>
    <button id="submitBtn" type="button" class="btn btn-success"><i class="fa fa-hand-paper-o"></i> 确定报送</button>
</div>

<script>
    $("#submitBtn").click(function () {

        $.post("${ctx}/pmd/pmdBranch_report", {id: '${param.id}'}, function (ret) {
            if (ret.success) {
                $("#modal").modal('hide');
                $("#jqGrid").trigger("reloadGrid");
            }
        });
    });
</script>