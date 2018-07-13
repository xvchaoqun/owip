<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${unitPost!=null}">编辑</c:if><c:if test="${unitPost==null}">添加</c:if>干部岗位库</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/unitPost_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${unitPost.id}">
        <input type="hidden" name="unitId" value="${unitId}">

			<div class="form-group">
				<label class="col-xs-3 control-label">岗位编号</label>
				<div class="col-xs-6">
                    <input required class="form-control" type="text" name="code" value="${unitPost.code}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">岗位名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${unitPost.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">分管工作</label>
				<div class="col-xs-6">
                        <textarea class="form-control" name="job">${unitPost.job}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否正职</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isPrincipalPost" value="${unitPost.isPrincipalPost}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">行政级别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="adminLevel" value="${unitPost.adminLevel}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">职务属性</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="postType" value="${unitPost.postType}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">职务类别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="postClass" value="${unitPost.postClass}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否占干部职数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isCpc" value="${unitPost.isCpc}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">状态</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="status" value="${unitPost.status}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">撤销时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="abolishTime" value="${unitPost.abolishTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">noEnter
                        <input required class="form-control" type="text" name="remark" value="${unitPost.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${unitPost!=null}">确定</c:if><c:if test="${unitPost==null}">添加</c:if></button>
</div>
<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
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