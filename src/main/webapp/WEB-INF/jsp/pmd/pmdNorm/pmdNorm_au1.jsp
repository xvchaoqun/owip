<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
	<div class="widget-header">
		<h4 class="widget-title lighter smaller">
			<a href="javascript:" class="hideView btn btn-xs btn-success">
				<i class="ace-icon fa fa-backward"></i>
				返回</a>
		</h4>
	</div>
	<div class="widget-body">
		<div class="widget-main" style="width: 900px">
			<form class="form-horizontal" action="${ctx}/pmd/pmdNorm_au"
				  id="modalForm" method="post">
				<input type="hidden" name="id" value="${pmdNorm.id}">
				<input type="hidden" name="type" value="${param.type}">
				<div class="form-group">
					<label class="col-xs-3 control-label">标准名称</label>
					<div class="col-xs-6">
						<input required class="form-control" type="text" name="name" value="${pmdNorm.name}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">额度设定类型</label>
					<div class="col-xs-6">
						<div class="radio">
							<label>
								<input required name="isFixed" type="radio" class="ace" value="0"/>
								<span class="lbl"> 基层党委设定</span>
							</label>
							<label>
								<input required name="isFixed" type="radio" class="ace" value="1"/>
								<span class="lbl"> 统一标准</span>
							</label>
						</div>
					</div>
				</div>
			</form>
			<div id="normValueDiv" style="margin-bottom: 20px;display: ${pmdNorm.isFixed?'block':'none'}">
				<c:import url="/pmd/pmdNormValue?pmdNormId=${pmdNorm.id}"/>
			</div>
			<div class="modal-footer center">
				<a href="javascript:;" class="hideView btn btn-default">返回</a>
				<button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> 确定</button>
			</div>
		</div>

	</div>
</div>
<script>
	$("input[name=isFixed]", "#modalForm").click(function(){
		if($(this).val()==1){
			$("#normValueDiv").show();
		}else{
			$("#normValueDiv").hide();
		}
	})
	$("#submitBtn").click(function(){$("#modalForm").submit();return false;});
	$("#modalForm").validate({
		submitHandler: function (form) {
			$(form).ajaxSubmit({
				success:function(ret){
					if(ret.success){
						$("#modal").modal('hide');
						$("#jqGrid").trigger("reloadGrid");
					}
				}
			});
		}
	});
</script>