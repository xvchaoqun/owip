<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>支部转移</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/branch_batchTransfer" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="ids" value="${param.ids}">
        <c:set var="count" value="${fn:length(fn:split(param.ids,\",\"))}"/>
        <c:if test="${count>1}">
            <div class="form-group">
                <label class="col-xs-4 control-label">转移支部</label>

                <div class="col-xs-4 label-text">
                        ${count} 个
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-4 control-label">选择${_p_partyName}</label>

            <div class="col-xs-4">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects?auth=1&notDirect=1&notBranchAdmin=1"
                        name="partyId" data-placeholder="请选择">
                    <option value="${party.id}">${party.name}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">转移原因</label>
            <div class="col-xs-4">
						<textarea class="form-control limited" type="text"
                                  name="remark" rows="3" style="width: 300px;"></textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">

    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    $.register.del_select($('#modalForm select[name=partyId]'), 300);
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal("hide");
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
</script>