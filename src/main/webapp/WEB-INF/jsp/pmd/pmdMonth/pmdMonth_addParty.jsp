<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>新增缴费党委</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmd/pmdMonth_addParty" id="modalForm" method="post">
			<div class="form-group">
				<label class="col-xs-3 control-label">选择党委</label>
				<div class="col-xs-6">
                    <select name="partyId" required data-rel="select2" data-placeholder="请选择" data-width="350">
                        <option></option>
                        <c:forEach items="${partyList}" var="party">
                            <option value="${party.id}">${party.name}</option>
                        </c:forEach>
                    </select>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${unit!=null}">确定</c:if><c:if test="${unit==null}">添加</c:if>"/>
</div>
<script>
    $("#modal form").validate({
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
    $('[data-rel="select2"]').select2();
</script>