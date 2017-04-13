<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>发送短信</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/shortMsgTpl_send" id="modalForm" method="post">
            <input type="hidden" name="tplId" value="${param.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">选择用户</label>
				<div class="col-xs-6">
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                            name="receiverId" data-placeholder="请输入账号或姓名或学工号">
                        <option></option>
                    </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">手机号码</label>
				<div class="col-xs-6">
                    <input required class="form-control" type="text" name="mobile">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">短信内容</label>
				<div class="col-xs-6 label-text">
                    ${shortMsgTpl.content}
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="发送"/>
</div>

<script>
    var $selectCadre = register_user_select($('#modalForm select[name=receiverId]'));
    $selectCadre.on("change",function(){
        var user = $(this).select2("data")[0]['user']||{};
        $('#modalForm input[name=mobile]').val(user.mobile);
    });

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
</script>