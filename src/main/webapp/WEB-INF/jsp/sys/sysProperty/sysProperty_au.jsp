<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${sysProperty!=null?'编辑':'添加'}系统属性</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sysProperty_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${sysProperty.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">代码</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="code" value="${sysProperty.code}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${sysProperty.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">内容</label>
				<div class="col-xs-6">
					<textarea class="form-control limited noEnter" name="content">${sysProperty.content}</textarea>
				</div>
			</div>
			<%--<div class="form-group">
				<label class="col-xs-3 control-label">类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${sysProperty.type}">
				</div>
			</div>--%>
			<div class="form-group">
				<label class="col-xs-3 control-label">说明</label>
				<div class="col-xs-6">
					 <textarea class="form-control limited noEnter" name="remark">${sysProperty.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${sysProperty!=null}">确定</c:if><c:if test="${sysProperty==null}">添加</c:if></button>
</div>
<script>
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
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>