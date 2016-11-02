<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${applyLog!=null}">编辑</c:if><c:if test="${applyLog==null}">添加</c:if>入党申请操作日志</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/applyLog_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${applyLog.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">用户</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="userId" value="${applyLog.userId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">操作人</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="operatorId" value="${applyLog.operatorId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">当前阶段</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="stage" value="${applyLog.stage}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">操作内容</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="content" value="${applyLog.content}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${applyLog!=null}">确定</c:if><c:if test="${applyLog==null}">添加</c:if>"/>
</div>

<script>
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        //SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>