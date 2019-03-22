<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/crs/constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>确定专家组</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crsPostExpert_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${crsPostExpert.id}">
        <input type="hidden" name="postId" value="${postId}">
        <div class="form-group">
            <label class="col-xs-3 control-label">专家</label>
            <div class="col-xs-6">
                <select data-rel="select2-ajax"
                        data-ajax-url="${ctx}/crsExpert_selects?status=${CRS_EXPERT_STATUS_NOW}"
                        name="userId" data-placeholder="请输入账号或姓名或工号">
                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">角色</label>

            <div class="col-xs-6">
                <select class="form-control" name="role"
                        data-rel="select2" data-placeholder="请选择">
                    <option></option>
                    <c:forEach items="${CRS_POST_EXPERT_ROLE_MAP}" var="type">
                        <option value="${type.key}">${type.value}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#modalForm select[name=role]").val('${crsPostExpert.role}');
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" type="text" name="remark" >${crsPostExpert.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    $.register.user_select($('#modalForm select[name=userId]'));
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
						$("#step-content li.active .loadPage").click()
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>