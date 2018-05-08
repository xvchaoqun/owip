<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>编辑短信模板</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/contentTpl_au" id="shortMsgForm" method="post">
        <input type="hidden" name="id" value="${contentTpl.id}">
			<div class="form-group">
				<label class="col-xs-2 control-label">类型</label>
				<div class="col-xs-6 label-text">
					${CONTENT_TPL_CET_MSG_MAP.get(contentTpl.code)}
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label">内容</label>
				<div class="col-xs-8">
					<textarea required class="form-control" name="content" rows="8">${contentTpl.content}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${contentTpl!=null}">确定</c:if><c:if test="${contentTpl==null}">添加</c:if>"/>
</div>
<script>
	$('textarea.limited').inputlimiter();
    $("#shortMsgForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        _detailReload2();
                    }
                }
            });
        }
    });
</script>