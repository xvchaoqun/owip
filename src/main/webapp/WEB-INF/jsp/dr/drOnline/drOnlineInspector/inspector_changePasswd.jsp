<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cls==0?"修改密码":"重置参评人密码"}</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dr/inspector_changePasswd?passwdChangeType=${cls==0?"1":"2"}" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <c:if test="${cls!=0}">
            <input type="hidden" name="id" value="${inspector.id}">
        </c:if>
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span> 新密码</label>
				<div class="col-xs-6">
                    <input required class="form-control" type="password" name="passwd" id="pw1" placeholder="请输入新密码">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> 确认</button>
</div>
<script type="text/javascript">

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                        SysMsg.success('修改密码成功。', '成功');
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