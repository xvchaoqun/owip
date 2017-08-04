<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${crsExpert!=null}">编辑</c:if><c:if test="${crsExpert==null}">添加</c:if>专家组成员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crsExpert_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${crsExpert.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">请选择干部</label>
				<div class="col-xs-6">
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects?key=1&types=${CADRE_STATUS_LEADER},${CADRE_STATUS_MIDDLE}" data-width="350"
                            name="userId" data-placeholder="请输入账号或姓名或工号">
                        <option></option>
                    </select>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${crsExpert!=null}">确定</c:if><c:if test="${crsExpert==null}">添加</c:if>"/>
</div>

<script>
    register_user_select($('#modalForm select[name=userId]'));
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