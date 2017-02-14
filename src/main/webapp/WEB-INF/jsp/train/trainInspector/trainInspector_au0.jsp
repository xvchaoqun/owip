<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${trainInspector!=null}">编辑</c:if><c:if test="${trainInspector==null}">添加</c:if>参评账号</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/trainInspector_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${trainInspector.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所属课程</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="courseId" value="${trainInspector.courseId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">登陆账号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="username" value="${trainInspector.username}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">登陆密码</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="passwd" value="${trainInspector.passwd}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">更改密码方式</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="passwdChangeType" value="${trainInspector.passwdChangeType}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">生成方式</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${trainInspector.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">暂存数据</label>
				<div class="col-xs-6">
                        <textarea class="form-control" name="tempdata">${trainInspector.tempdata}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">意见建议</label>
				<div class="col-xs-6">
                        <textarea class="form-control" name="feedback">${trainInspector.feedback}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">评估总分</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="score" value="${trainInspector.score}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">提交时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="submitTime" value="${trainInspector.submitTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">IP</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="submitIp" value="${trainInspector.submitIp}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">创建时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="createTime" value="${trainInspector.createTime}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${trainInspector!=null}">确定</c:if><c:if test="${trainInspector==null}">添加</c:if>"/>
</div>

<script>
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