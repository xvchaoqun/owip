<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${branchMember!=null}">编辑</c:if><c:if test="${branchMember==null}">添加</c:if>基层党组织成员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/branchMember_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${branchMember.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所属支部</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="groupId" value="${branchMember.groupId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">账号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="userId" value="${branchMember.userId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">类别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="typeId" value="${branchMember.typeId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否管理员</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isAdmin" value="${branchMember.isAdmin}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${branchMember!=null}">确定</c:if><c:if test="${branchMember==null}">添加</c:if>"/>
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
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>