<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${shortMsg!=null}">编辑</c:if><c:if test="${shortMsg==null}">添加</c:if>短信</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/shortMsg_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${shortMsg.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">发送方</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="senderId" value="${shortMsg.senderId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">接收方</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="receiverId" value="${shortMsg.receiverId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">类别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${shortMsg.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">手机号码</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="mobile" value="${shortMsg.mobile}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">短信内容</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="content" value="${shortMsg.content}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${shortMsg.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${shortMsg!=null}">确定</c:if><c:if test="${shortMsg==null}">添加</c:if>"/>
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
    $("#modalForm :checkbox").bootstrapSwitch();
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