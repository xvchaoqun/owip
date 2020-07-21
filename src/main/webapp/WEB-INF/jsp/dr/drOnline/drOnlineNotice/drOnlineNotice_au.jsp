<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${drOnlineNotice!=null?'编辑':'添加'}线上民主推荐说明模板</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dr/drOnlineNotice_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${drOnlineNotice.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 模板名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${drOnlineNotice.name}">
				</div>
			</div>
			<div class="form-group">
				<div class="col-xs-6">
					<input type="hidden" name="content">
					<textarea id="contentId">${drOnlineNotice.content}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty drOnlineNotice?'确定':'添加'}</button>
</div>
<script>

	var ke = KindEditor.create('#contentId', {
		allowFileManager: true,
		uploadJson: '${ctx}/ke/upload_json',
		fileManagerJson: '${ctx}/ke/file_manager_json',
		height: '400px',
		width: '570px',
		minWidth: 570,
		items : [
			'source', '|', 'indent','outdent', 'fontname', 'fontsize', '|','forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
			'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'link', 'unlink', 'fullscreen']
	});

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
			$("#modalForm input[name=content]").val(ke.html());

            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>