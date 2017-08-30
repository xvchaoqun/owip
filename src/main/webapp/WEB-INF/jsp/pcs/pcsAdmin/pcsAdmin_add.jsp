<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>添加党代会分党委管理员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcsAdmin_add" id="modalForm" method="post">
        <div class="form-group">
            <label class="col-xs-3 control-label">所属党代会</label>
            <div class="col-xs-6">
                <c:if test="${empty pcsConfig}">
                    还没有设置当前党代会，请先设置当前党代会后再添加管理员
                </c:if>
                <c:if test="${not empty pcsConfig}">
                ${pcsConfig.name}
                    <input type="hidden" name="configId" value="${pcsConfig.id}">
                </c:if>
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所属分党委</label>
				<div class="col-xs-6">
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects?del=0"
                            name="partyId" data-placeholder="请选择">
                        <option value="${party.id}">${party.name}</option>
                    </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">用户</label>
				<div class="col-xs-6">
                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                        <option value="${sysUser.id}">${sysUser.username}-${sysUser.code}</option>
                    </select>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
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
    register_party_select($('#modalForm select[name=partyId]'));
    register_user_select($('#modalForm select[name=userId]'));
</script>