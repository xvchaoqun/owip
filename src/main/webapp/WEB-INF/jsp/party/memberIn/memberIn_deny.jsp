<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>拒绝组织关系转入</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberIn_deny" id="modalForm" method="post">
        <input type="hidden" name="id" value="${param.id}">
		<div class="form-group">
			<label class="col-xs-3 control-label">用户</label>
			<div class="col-xs-6">
				<input disabled class="form-control" type="text" value="${param.realname}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">返回修改原因</label>
			<div class="col-xs-6">
				<textarea required class="form-control limited" type="text" name="reason" rows="5">${memberIn.reason}</textarea>
			</div>
		</div>
    </form>
    </div>
<div class="modal-footer">
    <a href="#"  data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${memberIn!=null}">确定</c:if><c:if test="${memberIn==null}">添加</c:if>"/>
</div>

<script>
	$("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
</script>