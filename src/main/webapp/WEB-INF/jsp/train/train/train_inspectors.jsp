<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>生成评课账号</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/trainInspector_gen" id="modalForm" method="post">
        <input type="hidden" name="trainId" value="${train.id}">
        <input type="hidden" name="type" value="1">
			<div class="form-group">
				<label class="col-xs-3 control-label">培训班次</label>
				<div class="col-xs-6 label-text">
					${train.name}
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">账号数量</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control digits" data-validation="min[${train.totalCount}]"
							   type="text" name="count" value="${train.totalCount}">
					</div>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
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