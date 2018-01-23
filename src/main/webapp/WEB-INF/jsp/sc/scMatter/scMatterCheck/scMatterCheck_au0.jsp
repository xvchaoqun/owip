<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scMatterCheck!=null}">编辑</c:if><c:if test="${scMatterCheck==null}">添加</c:if>个人有关事项-抽查核实</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scMatterCheck_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scMatterCheck.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">核查日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="checkDate" value="${scMatterCheck.checkDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">核查文件</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="checkFile" value="${scMatterCheck.checkFile}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">核查编号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="num" value="${scMatterCheck.num}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">核查类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isRandom" value="${scMatterCheck.isRandom}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${scMatterCheck.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scMatterCheck!=null}">确定</c:if><c:if test="${scMatterCheck==null}">添加</c:if></button>
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