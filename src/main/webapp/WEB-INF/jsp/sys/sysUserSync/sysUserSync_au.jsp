<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${sysUserSync!=null}">编辑</c:if><c:if test="${sysUserSync==null}">添加</c:if>账号同步日志</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sysUserSync_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${sysUserSync.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${sysUserSync.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">触发账号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="userId" value="${sysUserSync.userId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否自动触发</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="autoStart" value="${sysUserSync.autoStart}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否正常结束</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="autoStop" value="${sysUserSync.autoStop}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否结束</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isStop" value="${sysUserSync.isStop}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">总页码</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="totalPage" value="${sysUserSync.totalPage}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">总记录数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="totalCount" value="${sysUserSync.totalCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">当前页码</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="currentPage" value="${sysUserSync.currentPage}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">当前记录数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="currentCount" value="${sysUserSync.currentCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">插入数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="insertCount" value="${sysUserSync.insertCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">更新数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="updateCount" value="${sysUserSync.updateCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">开始时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="startTime" value="${sysUserSync.startTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">结束时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="endTime" value="${sysUserSync.endTime}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${sysUserSync!=null}">确定</c:if><c:if test="${sysUserSync==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>