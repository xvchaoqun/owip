<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scPublic!=null}">编辑</c:if><c:if test="${scPublic==null}">添加</c:if>干部任前公示</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scPublic_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${scPublic.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>所属党委常委会</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="committeeId" value="${scPublic.committeeId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>年度</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="year" value="${scPublic.year}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>编号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="num" value="${scPublic.num}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>公示文件WORD版</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="wordFilePath" value="${scPublic.wordFilePath}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>公示文件PDF版</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="pdfFilePath" value="${scPublic.pdfFilePath}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>公示时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="publicDate" value="${scPublic.publicDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>发布时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="publishDate" value="${scPublic.publishDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>是否公示结束</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isFinished" value="${scPublic.isFinished}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>是否确认公示结束</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isConfirmed" value="${scPublic.isConfirmed}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scPublic!=null}">确定</c:if><c:if test="${scPublic==null}">添加</c:if></button>
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