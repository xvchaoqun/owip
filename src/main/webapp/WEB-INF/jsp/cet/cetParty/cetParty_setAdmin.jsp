<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>设置管理员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetParty_setAdmin" id="modalForm" method="post">
        <input type="hidden" name="id" value="${param.id}">
            <div class="form-group">
                <label class="col-xs-3 control-label">所属院系级党委</label>
                <div class="col-xs-6 label-text">
                    ${cetParty.partyName}
                </div>
            </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">管理员</label>
				<div class="col-xs-6">
                    <select name="userId" data-rel="select2-ajax" data-width="350"
                            data-ajax-url="${ctx}/sysUser_selects"
                            data-placeholder="请输入账号或姓名或教工号">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
                    <script>
                        $.register.user_select($("#modalForm select[name=userId]"))
                    </script>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> 确定</button>
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
</script>