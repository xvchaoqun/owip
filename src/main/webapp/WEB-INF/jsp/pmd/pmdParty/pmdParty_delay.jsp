<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>批量延迟缴费</h3>
</div>
<div class="modal-body">
    <table class="table table-bordered table-unhover">
        <tr>
            <td class="bg-right">${_p_partyName}名称</td>
            <td>${partyMap.get(pmdParty.partyId).name}</td>
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
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> 确定</button>
</div>
<script>
    $("#submitBtn").click(function () {
        var $this = $(this);
        var delayReason = $.trim($("#delayReason").val());

        $.post("${ctx}/pmd/pmdParty_delay",{id:${pmdParty.id}, delayReason:delayReason},function(ret){
            if(ret.success){
                $("#modal").modal('hide');
                $("#jqGrid2").trigger("reloadGrid");
            }
        });
    });
    $('textarea.limited').inputlimiter();
</script>