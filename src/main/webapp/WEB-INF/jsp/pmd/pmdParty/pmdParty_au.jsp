<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${pmdParty!=null}">编辑</c:if><c:if test="${pmdParty==null}">添加</c:if>每月参与线上收费的党委</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmdParty_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${pmdParty.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>月份</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="payMonth" value="${pmdParty.payMonth}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>党委</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="partyId" value="${pmdParty.partyId}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input id="submitBtn" type="button" class="btn btn-primary" value="<c:if test="${pmdParty!=null}">确定</c:if><c:if test="${pmdParty==null}">添加</c:if>"/>
</div>

<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>