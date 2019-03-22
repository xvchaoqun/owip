<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${sysSync!=null}">编辑</c:if><c:if test="${sysSync==null}">添加</c:if>账号同步日志</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sysSync_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${sysSync.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${sysSync.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>触发账号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="userId" value="${sysSync.userId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>是否自动触发</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="autoStart" value="${sysSync.autoStart}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>是否正常结束</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="autoStop" value="${sysSync.autoStop}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>是否结束</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isStop" value="${sysSync.isStop}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>总页码</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="totalPage" value="${sysSync.totalPage}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>总记录数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="totalCount" value="${sysSync.totalCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>当前页码</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="currentPage" value="${sysSync.currentPage}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>当前记录数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="currentCount" value="${sysSync.currentCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>插入数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="insertCount" value="${sysSync.insertCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>更新数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="updateCount" value="${sysSync.updateCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>开始时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="startTime" value="${sysSync.startTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>结束时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="endTime" value="${sysSync.endTime}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${sysSync!=null}">确定</c:if><c:if test="${sysSync==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm").validate({
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