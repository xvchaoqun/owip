<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${psTask!=null?'编辑':'添加'}年度工作任务</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/ps/psTask_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${psTask.id}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 名称</label>
			<div class="col-xs-6">
				<input required class="form-control" type="text" name="name" value="${psTask.name}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 年度</label>
			<div class="col-xs-6">
				<div class="input-group">
					<input required class="form-control date-picker" name="_year" type="text"
						   data-date-format="yyyy"
						   data-date-min-view-mode="2"
						   value="${not empty psTask?psTask.year:cm:formatDate(now,'yyyy')}"/>
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>

			<div class="form-group">
				<label class="col-xs-3 control-label"> 发布时间</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_releaseDate" type="text"
							   data-date-format="yyyy-mm-dd"
							   value="${not empty psTask?
							   cm:formatDate(psTask.releaseDate,'yyyy-MM-dd'):cm:formatDate(now,'yyyy-MM-dd')}"/>
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 是否发布</label>
			<div class="col-xs-6">
				<label>
					<input name="isPublish" ${psTask.isPublish?"checked":""}  type="checkbox" />
					<span class="lbl"></span>
				</label>
			</div>
		</div>

			<%--<div class="form-group">
				<label class="col-xs-3 control-label"> 是否发布</label>
				<div class="col-xs-6">
					<input class="form-control" type="text" name="isPublish" value="${psTask.isPublish}">
				</div>
			</div>--%>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 备注</label>
				<div class="col-xs-6">
					<textarea class="form-control" name="remark">${psTask.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty psTask?'确定':'添加'}</button>
</div>
<script>

	/*$.fileInput($("#modalForm input[name=_file]"),{
		no_file:'请选择pdf或word文件',
		allowExt: ['pdf', 'doc', 'docx'],
		allowMime: ['application/pdf','application/msword',
			'application/vnd.openxmlformats-officedocument.wordprocessingml.document']
	});*/

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
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
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>