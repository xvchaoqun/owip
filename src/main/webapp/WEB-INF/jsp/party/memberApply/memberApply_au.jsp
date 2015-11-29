<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${memberApply!=null}">编辑</c:if><c:if test="${memberApply==null}">添加</c:if>申请入党人员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberApply_au" id="modalForm" method="post">
        <input type="hidden" name="userId" value="${memberApply.userId}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所属分党委</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="partyId" value="${memberApply.partyId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所属党支部</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="branchId" value="${memberApply.branchId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${memberApply.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">提交申请书时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="applyTime" value="${memberApply.applyTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">信息填报时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="fillTime" value="${memberApply.fillTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input required class="form-control limited" type="text" name="remark" value="${memberApply.remark}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">阶段</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="status" value="${memberApply.stage}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">确定为入党积极分子时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="activeTime" value="${memberApply.activeTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发展对象审核状态</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="activeStatus" value="${memberApply.activeStatus}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">确定为发展对象时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="candidateTime" value="${memberApply.candidateTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">参加培训时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="trainTime" value="${memberApply.trainTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发展对象审核状态</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="candidateStatus" value="${memberApply.candidateStatus}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">列入发展计划时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="planTime" value="${memberApply.planTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">列入计划审核状态</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="planStatus" value="${memberApply.planStatus}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">领取志愿书时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="drawTime" value="${memberApply.drawTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">志愿书领取审核状态</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="drawStatus" value="${memberApply.drawStatus}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发展时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="growTime" value="${memberApply.growTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发展审核状态</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="growStatus" value="${memberApply.growStatus}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">转正时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="positive Time" value="${memberApply.positiveTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">转正审核状态</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="positiveStatus" value="${memberApply.positiveStatus}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">创建时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="createTime" value="${memberApply.createTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">更新时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="updateTime" value="${memberApply.updateTime}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${memberApply!=null}">确定</c:if><c:if test="${memberApply==null}">添加</c:if>"/>
</div>

<script>
	$('textarea.limited').inputlimiter();

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
            delay: 300,
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