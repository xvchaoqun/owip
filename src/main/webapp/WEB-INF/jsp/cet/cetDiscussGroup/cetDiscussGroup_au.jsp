<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetDiscussGroup!=null}">编辑</c:if><c:if test="${cetDiscussGroup==null}">添加</c:if>研讨小组</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetDiscussGroup_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetDiscussGroup.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">分组讨论</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="discussId" value="${cetDiscussGroup.discussId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">召集人</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="holdUserId" value="${cetDiscussGroup.holdUserId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">研讨主题</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="subject" value="${cetDiscussGroup.subject}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否允许修改研讨主题</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="subjectCanModify" value="${cetDiscussGroup.subjectCanModify}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">召开时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="discussTime" value="${cetDiscussGroup.discussTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">召开地点</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="discussAddress" value="${cetDiscussGroup.discussAddress}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">负责单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="untiId" value="${cetDiscussGroup.untiId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">负责单位管理员</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="adminUserId" value="${cetDiscussGroup.adminUserId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">排序</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="sortOrder" value="${cetDiscussGroup.sortOrder}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${cetDiscussGroup.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetDiscussGroup!=null}">确定</c:if><c:if test="${cetDiscussGroup==null}">添加</c:if></button>
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