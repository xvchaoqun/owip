<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${approvalLog!=null}">编辑</c:if><c:if test="${approvalLog==null}">添加</c:if>因私出国审批记录</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/approvalLog_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${approvalLog.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">申请记录</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="applyId" value="${approvalLog.applyId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">审批人</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="cadreId" value="${approvalLog.cadreId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">审批人类别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="typeId" value="${approvalLog.typeId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">组织部管理员审批类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="odType" value="${approvalLog.odType}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">审批状态</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="status" value="${approvalLog.status}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${approvalLog.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${approvalLog!=null}">确定</c:if><c:if test="${approvalLog==null}">添加</c:if>"/>
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
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('#modalForm [data-rel="select2-ajax"]').select2({
        ajax: {
            dataType: 'json',
            delay: 200,
            data: function (params) {
                return {
                    searchStr: params.term,
                    pageSize: 10,
                    pageNo: params.page
                };
            },
            processResults: function (data, params) {
                params.page = params.page || 1;
                return {results: data.options,  pagination: {
                    more: (params.page * 10) < data.totalCount
                }};
            },
            cache: true
        }
    });
</script>