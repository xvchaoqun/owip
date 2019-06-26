<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${psMember!=null?'编辑':'添加'}二级党校班子成员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/ps/psMember_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${psMember.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 所属二级党校</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="psId" value="${psMember.psId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 届数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="seq" value="${psMember.seq}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 党校职务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${psMember.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 班子成员</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="userId" value="${psMember.userId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 所在单位及职务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="title" value="${psMember.title}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 联系方式</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="mobile" value="${psMember.mobile}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 任职起始时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="startDate" value="${psMember.startDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 任职结束时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="endDate" value="${psMember.endDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 现任/离任</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isHistory" value="${psMember.isHistory}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${psMember.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty psMember?'确定':'添加'}</button>
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