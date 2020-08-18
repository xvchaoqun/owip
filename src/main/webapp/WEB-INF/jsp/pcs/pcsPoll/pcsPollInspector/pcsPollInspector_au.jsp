<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>生成投票人账号</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcs/pcsPollInspector_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="pollId" value="${param.pollId}">
        <div class="form-group">
            <label class="col-xs-4 control-label"> 党支部成员数量</label>
            <div class="col-xs-8 label-text">
                ${branchMemberNum}
            </div>
        </div>
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span> 生成账号数量</label>
			<div class="col-xs-8">
				<input required style="width: 80px;" class="form-control digits" type="text"
					   name="count" data-rule-min="1">
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <div class="note">注：此数量是新生成账号的数量，不影响已生成的账号</div>
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty pcsPollInspector?'确定':'添加'}</button>
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
                        $("#jqGrid2").trigger("reloadGrid");
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