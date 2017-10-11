<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${shortMsgTpl!=null}">编辑</c:if><c:if test="${shortMsgTpl==null}">添加</c:if>定向短信模板</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/shortMsgTpl_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${shortMsgTpl.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所属角色</label>
				<div class="col-xs-8">
                    <select required name="roleId" data-rel="select2" data-placeholder="请选择" data-width="370">
                        <option></option>
                        <c:forEach items="${sysRoles}" var="sysRole">
                            <option value="${sysRole.id}">${sysRole.description}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modalForm select[name=roleId]").val('${shortMsgTpl.roleId}');
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">模板名称</label>
				<div class="col-xs-8">
                        <input required class="form-control" type="text" name="name" value="${shortMsgTpl.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">短信内容</label>
				<div class="col-xs-8">
                    <textarea required class="form-control limited" type="text"
                              name="content" rows="8" maxlength="500">${shortMsgTpl.content}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-8">
                    <textarea class="form-control limited" type="text"
                              name="remark" rows="6">${shortMsgTpl.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${shortMsgTpl!=null}">确定</c:if><c:if test="${shortMsgTpl==null}">添加</c:if>"/>
</div>

<script>
    $('textarea.limited').inputlimiter();

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
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>