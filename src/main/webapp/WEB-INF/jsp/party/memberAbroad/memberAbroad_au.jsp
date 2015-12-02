<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${memberAbroad!=null}">编辑</c:if><c:if test="${memberAbroad==null}">添加</c:if>党员出国境信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberAbroad_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberAbroad.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">党员</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="userId" value="${memberAbroad.userId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">分党委名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="partyName" value="${memberAbroad.partyName}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">党支部名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="branchName" value="${memberAbroad.branchName}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">入党时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="growTime" value="${memberAbroad.growTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">出国时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="abroadTime" value="${memberAbroad.abroadTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">出国缘由</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="reason" value="${memberAbroad.reason}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">预计归国时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="expectReturnTime" value="${memberAbroad.expectReturnTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">实际归国时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="actualReturnTime" value="${memberAbroad.actualReturnTime}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${memberAbroad!=null}">确定</c:if><c:if test="${memberAbroad==null}">添加</c:if>"/>
</div>

<script>
    $("#modal form").validate({
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