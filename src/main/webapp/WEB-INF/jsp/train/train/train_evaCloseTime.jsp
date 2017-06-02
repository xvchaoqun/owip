<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>评课设置</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/train_evaCloseTime" id="modalForm" method="post">
        <input type="hidden" name="id" value="${train.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">培训班次</label>
				<div class="col-xs-6 label-text">
					${train.name}
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否关闭评课</label>

				<div class="col-xs-6">
					<label>
						<input name="isClosed" ${train.isClosed?"checked":""} type="checkbox"/>
						<span class="lbl"></span>
					</label>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">评课结束时间</label>
				<div class="col-xs-6">
					<div class="input-group" style="width: 200px">
						<input class="form-control datetime-picker required" type="text"  name="_closeTime"
							   value="${cm:formatDate(train.closeTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
					</div>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
	function isClosedChanged(){
		if($('#modalForm input[name=isClosed]').bootstrapSwitch("state")) {
			$("#modalForm input[name=_closeTime]").val('').prop("disabled", true);
		}else{
			$("#modalForm input[name=_closeTime]").prop("disabled", false)
					.datetimepicker('update', '${cm:formatDate(train.closeTime, "yyyy-MM-dd HH:mm")}');
		}
	}
	$('#modalForm input[name=isClosed]').on('switchChange.bootstrapSwitch', function(event, state) {
		isClosedChanged()
	});
	isClosedChanged()

	register_datetime($('.datetime-picker'));
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