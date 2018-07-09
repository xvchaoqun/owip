<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scPassport!=null}">编辑</c:if><c:if test="${scPassport==null}">添加</c:if>上交证件信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scPassport_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scPassport.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">关联上交证件记录</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="handId" value="${scPassport.handId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">证件名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="classId" value="${scPassport.classId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">干部是否拥有该证件</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isExist" value="${scPassport.isExist}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">集中保管日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="keepDate" value="${scPassport.keepDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">证件号码</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="code" value="${scPassport.code}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">上传证件首页</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="pic" value="${scPassport.pic}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发证机关</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="authority" value="${scPassport.authority}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发证日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="issueDate" value="${scPassport.issueDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">有效期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="expiryDate" value="${scPassport.expiryDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">存放保险柜</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="safeBoxId" value="${scPassport.safeBoxId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">交证件时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="handTime" value="${scPassport.handTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${scPassport.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scPassport!=null}">确定</c:if><c:if test="${scPassport==null}">添加</c:if></button>
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
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>