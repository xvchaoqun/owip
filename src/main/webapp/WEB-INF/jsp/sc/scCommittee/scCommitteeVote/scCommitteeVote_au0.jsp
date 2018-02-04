<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scCommitteeVote!=null}">编辑</c:if><c:if test="${scCommitteeVote==null}">添加</c:if>干部选拔任用表决</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scCommitteeVote_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scCommitteeVote.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">议题</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="topicId" value="${scCommitteeVote.topicId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所属干部</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="cadreId" value="${scCommitteeVote.cadreId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">类别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${scCommitteeVote.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">原任职务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="originalPost" value="${scCommitteeVote.originalPost}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">原任职务任职时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="originalPostTime" value="${scCommitteeVote.originalPostTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">干部类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="cadreTypeId" value="${scCommitteeVote.cadreTypeId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">任免方式</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="wayId" value="${scCommitteeVote.wayId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">任免程序</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="procedureId" value="${scCommitteeVote.procedureId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">职务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="post" value="${scCommitteeVote.post}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">职务属性</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="postId" value="${scCommitteeVote.postId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">行政级别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="adminLevelId" value="${scCommitteeVote.adminLevelId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所属单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unitId" value="${scCommitteeVote.unitId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">参会同意人数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="aggreeCount" value="${scCommitteeVote.aggreeCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${scCommitteeVote.remark}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">排序</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="sortOrder" value="${scCommitteeVote.sortOrder}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scCommitteeVote!=null}">确定</c:if><c:if test="${scCommitteeVote==null}">添加</c:if></button>
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