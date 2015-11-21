<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${member!=null}">编辑</c:if><c:if test="${member==null}">添加</c:if>党员信息表</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/member_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${member.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所属分党委</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="partyId" value="${member.partyId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所属党支部</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="branchId" value="${member.branchId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">政治面貌</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="politicalStatus" value="${member.politicalStatus}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">类别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${member.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">状态</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="status" value="${member.status}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">来源</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="source" value="${member.source}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">组织关系转入时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="transferTime" value="${member.transferTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">提交书面申请书时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="applyTime" value="${member.applyTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">确定为入党积极分子时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="activeTime" value="${member.activeTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">确定为发展对象时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="candidateTime" value="${member.candidateTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">入党时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="growTime" value="${member.growTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">转正时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="positiveTime" value="${member.positiveTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">创建时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="createTime" value="${member.createTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">更新时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="updateTime" value="${member.updateTime}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${member!=null}">确定</c:if><c:if test="${member==null}">添加</c:if>"/>
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