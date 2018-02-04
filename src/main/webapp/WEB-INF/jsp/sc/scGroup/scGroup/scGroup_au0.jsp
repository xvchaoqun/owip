<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scGroup!=null}">编辑</c:if><c:if test="${scGroup==null}">添加</c:if>干部小组会</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scGroup_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scGroup.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">干部小组会议题</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="filePath" value="${scGroup.filePath}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">年份</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="year" value="${scGroup.year}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">干部小组会日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="holdDate" value="${scGroup.holdDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">议题数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="topicNum" value="${scGroup.topicNum}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">议题word版</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="wordFilePath" value="${scGroup.wordFilePath}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">会议记录</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="logFile" value="${scGroup.logFile}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">列席人</label>
				<div class="col-xs-6">
                        <textarea class="form-control" name="attendUsers">${scGroup.attendUsers}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否删除</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isDeleted" value="${scGroup.isDeleted}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scGroup!=null}">确定</c:if><c:if test="${scGroup==null}">添加</c:if></button>
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