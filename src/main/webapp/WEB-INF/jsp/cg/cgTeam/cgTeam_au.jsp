<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="${not empty param.fid || not empty cgTeam.fid}" var="hasFid" />
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cgTeam!=null?'编辑':'添加'}委员会和领导小组</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cg/cgTeam_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cgTeam.id}" />
		<input type="hidden" name="fid" value="${param.fid}" />
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span> 委员会和领导小组名称</label>
			<div class="col-xs-6">
				<input required class="form-control" type="text" name="name" value="${cgTeam.name}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span> 类型</label>
			<div class="col-xs-6">
				<select required id="typeSelect1" name="type" data-placeholder="请选择类型"
						data-rel="select2" data-width="270">
					<option></option>
					<c:if test="${hasFid}">
						<c:forEach items="<%=CgConstants.CG_CHILD_TEAM_TYPE_MAP%>" var="cgTeamType">
							<option value="${cgTeamType.key}">${cgTeamType.value}</option>
						</c:forEach>
					</c:if>
					<c:if test="${!hasFid}">
						<c:forEach items="<%=CgConstants.CG_TEAM_TYPE_MAP%>" var="cgTeamType">
							<option value="${cgTeamType.key}">${cgTeamType.value}</option>
						</c:forEach>
					</c:if>
				</select>
				<script>
					$("#typeSelect1").val('${cgTeam.type}');
				</script>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span> 类别</label>
			<div class="col-xs-6">
				<select class="col-xs-6" required name="category" data-width="270"
						data-rel="select2" data-placeholder="请选择">
					<option></option>
					<c:import url="/metaTypes?__code=mc_cg_type"/>
				</select>
			</div>
			<script>
				$("#modalForm select[name=category]").val('${cgTeam.category}');
			</script>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label"> 备注</label>
			<div class="col-xs-6">
				<textarea class="form-control" name="remark">${cgTeam.remark}</textarea>
			</div>
		</div>
	</form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty cgTeam?'确定':'添加'}</button>
</div>
<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
</script>