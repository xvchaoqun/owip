<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div style="${param.detail==1?'width:500px':''}">
<div class="modal-header">
    <%--<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>--%>
    <h3>评课设置</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetTrain_evaCloseTime" id="modalForm" method="post">
        <input type="hidden" name="trainId" value="${cetTrain.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">培训班次</label>
				<div class="col-xs-6 label-text">
					${cetTrain.name}
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否关闭评课</label>

				<div class="col-xs-6">
					<label>
						<input name="evaClosed" ${cetTrain.evaClosed?"checked":""} type="checkbox"/>
						<span class="lbl"></span>
					</label>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">评课结束时间</label>
				<div class="col-xs-6">
					<div class="input-group" style="width: 200px">
						<input class="form-control datetime-picker required" type="text"  name="_evaCloseTime"
							   value="${cm:formatDate(cetTrain.evaCloseTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
					</div>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer center">
	<c:if test="${param.detail!=1}">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input id="submitBtn" type="button" class="btn btn-primary" value="确定"/>
	</c:if>
	<c:if test="${param.detail==1}">
    <input  id="submitBtn" type="button" class="btn btn-primary" value="设置"/>
	</c:if>
</div>
</div>
<script>
	function evaClosedChanged(){
		if($('#modalForm input[name=evaClosed]').bootstrapSwitch("state")) {
			$("#modalForm input[name=_evaCloseTime]").val('').prop("disabled", true);
		}else{
			$("#modalForm input[name=_evaCloseTime]").prop("disabled", false)
					.datetimepicker('update', '${cm:formatDate(cetTrain.evaCloseTime, "yyyy-MM-dd HH:mm")}');
		}
	}
	$('#modalForm input[name=evaClosed]').on('switchChange.bootstrapSwitch', function(event, state) {
		evaClosedChanged()
	});
	evaClosedChanged()

	register_datetime($('.datetime-picker'));
	$("#submitBtn").click(function(){
		$("#modalForm").submit();
		return false;
	})
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
						<c:if test="${param.detail!=1}">
						$("#modal").modal('hide');
						$("#jqGrid").trigger("reloadGrid");
						</c:if>
						<c:if test="${param.detail==1}">
						SysMsg.success("设置成功。");
						</c:if>
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>