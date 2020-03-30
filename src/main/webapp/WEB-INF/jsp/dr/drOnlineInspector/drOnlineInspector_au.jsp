<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${inspector!=null?'编辑':'添加'}参评人</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dr/drOnlineInspector_au?passwdChangeType=2" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${inspector.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 新密码</label>
				<div class="col-xs-6">
                    <input required class="form-control" type="password" name="passwd" id="pw1" placeholder="请输入新密码" onkeyup="check()">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 确认密码</label>
				<div class="col-xs-6">
					<input required class="form-control" type="password" name="repasswd" id="pw2" placeholder="请再次输入密码" onkeyup="check()">
					<span id="tip"></span>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty inspector?'确定':'添加'}</button>
</div>
<script type="text/javascript">

	function check(){
		var pw1 = $("#pw1").val();
		var pw2 = $("#pw2").val();

		if (pw1 == pw2){
			$("#tip").html("两次密码相同");
			$("#tip").css("color","green");
			$("#submitBtn").removeAttr("disabled");
		}else {
			$("#tip").html("两次密码不相同");
			$("#tip").css("color","red");
			$("#submitBtn").attr("disabled", "disabled");
		}
	}

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