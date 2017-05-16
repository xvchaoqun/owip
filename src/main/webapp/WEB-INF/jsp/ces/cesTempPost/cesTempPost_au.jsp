<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cesTempPost!=null}">编辑</c:if><c:if test="${cesTempPost==null}">添加</c:if>干部挂职锻炼</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cesTempPost_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cesTempPost.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">关联干部</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="cadreId" value="${cesTempPost.cadreId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">姓名</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="realname" value="${cesTempPost.realname}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否现任干部</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isPresentCadre" value="${cesTempPost.isPresentCadre}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">时任职务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="presentPost" value="${cesTempPost.presentPost}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">委派单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="toUnitType" value="${cesTempPost.toUnitType}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">单位名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="toUnit" value="${cesTempPost.toUnit}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">挂职类别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="tempPostType" value="${cesTempPost.tempPostType}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">挂职类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="tempPost" value="${cesTempPost.tempPost}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">挂职单位及所任职务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="title" value="${cesTempPost.title}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">挂职开始时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="startDate" value="${cesTempPost.startDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">挂职拟结束时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="endDate" value="${cesTempPost.endDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">挂职实际结束时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="realEndDate" value="${cesTempPost.realEndDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">挂职分类</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${cesTempPost.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${cesTempPost.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cesTempPost!=null}">确定</c:if><c:if test="${cesTempPost==null}">添加</c:if>"/>
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