<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${unitTeamPlan!=null?'编辑':'添加'}班子下的干部配置方案</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/unitTeamPlan_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${unitTeamPlan.id}">
        <input type="hidden" name="unitTeamId" value="${param.unitTeamId}">

			<div class="form-group">
				<label class="col-xs-2 control-label">起始时间</label>
				<div class="col-xs-6">
					<div class="input-group">
                    <input required class="form-control date-picker" name="startDate" type="text"
                           data-date-format="yyyy-mm-dd"
                           value="${cm:formatDate(unitTeamPlan.startDate,'yyyy-MM-dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label">行政正职</label>
				<div class="col-xs-9 mainPosts" style="max-height: 200px;overflow-y: scroll">
				<table class="table table-condensed table-striped table-bordered table-unhover2 table-center">
					<tr>
						<td></td>
						<td>岗位名称</td>
						<td>分管工作</td>
					</tr>
					<c:forEach items="${unitPosts}" var="entity">
					<tr>
						<td>
							<input type="checkbox" value="${entity.id}" ${mainPostIds.contains(entity.id)?"checked":""}>
						</td>
						<td style="text-align: left">${entity.name}</td>
						<td style="text-align: left">${entity.job}</td>
					</tr>
					</c:forEach>
				</table>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-2 control-label">行政副职</label>
				<div class="col-xs-9 vicePosts" style="max-height: 200px;overflow-y: scroll">
				<table class="table table-condensed table-striped table-bordered table-unhover2 table-center">
					<tr>
						<td></td>
						<td>岗位名称</td>
						<td>分管工作</td>
					</tr>
					<c:forEach items="${unitPosts}" var="entity">
					<tr>
						<td>
							<input type="checkbox" value="${entity.id}" ${vicePostIds.contains(entity.id)?"checked":""}>
						</td>
						<td style="text-align: left">${entity.name}</td>
						<td style="text-align: left">${entity.job}</td>
					</tr>
					</c:forEach>
				</table>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-2 control-label">备注</label>
				<div class="col-xs-6">
					<textarea class="form-control limited" name="remark">${unitTeamPlan.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${unitTeamPlan!=null}">确定</c:if><c:if test="${unitTeamPlan==null}">添加</c:if></button>
</div>
<script>

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
				data: {mainPosts: $.map($(".mainPosts input[type=checkbox]:checked"),function(input){return $(input).val();}).join(","),
					vicePosts: $.map($(".vicePosts input[type=checkbox]:checked"),function(input){return $(input).val();}).join(",")},
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid_plan").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    $.register.del_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>