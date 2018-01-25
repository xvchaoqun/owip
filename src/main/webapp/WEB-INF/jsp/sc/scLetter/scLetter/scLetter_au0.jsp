<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scLetter!=null}">编辑</c:if><c:if test="${scLetter==null}">添加</c:if>纪委函询文件管理</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scLetter_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scLetter.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">年份</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="year" value="${scLetter.year}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${scLetter.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">编号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="num" value="${scLetter.num}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">函询日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="queryDate" value="${scLetter.queryDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${scLetter.remark}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否删除</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isDeleted" value="${scLetter.isDeleted}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scLetter!=null}">确定</c:if><c:if test="${scLetter==null}">添加</c:if></button>
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