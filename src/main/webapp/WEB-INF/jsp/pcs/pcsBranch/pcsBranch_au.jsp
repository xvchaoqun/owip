<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${pcsBranch!=null?'编辑':'添加'}召开党代会的支部</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcsBranch_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${pcsBranch.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> ID</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="id" value="${pcsBranch.id}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 所属党代会</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="configId" value="${pcsBranch.configId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 支部ID</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="branchId" value="${pcsBranch.branchId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 是否直属党支部</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isDirectBranch" value="${pcsBranch.isDirectBranch}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${pcsBranch.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 排序</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="sortOrder" value="${pcsBranch.sortOrder}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 党员数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="memberCount" value="${pcsBranch.memberCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 正式党员数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="positiveCount" value="${pcsBranch.positiveCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 学生党员数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="studentMemberCount" value="${pcsBranch.studentMemberCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 教师党员数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="teacherMemberCount" value="${pcsBranch.teacherMemberCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 离退休党员数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="retireMemberCount" value="${pcsBranch.retireMemberCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 是否删除</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isDeleted" value="${pcsBranch.isDeleted}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty pcsBranch?'确定':'添加'}</button>
</div>
<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
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