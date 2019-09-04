<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cgRule!=null?'编辑':'添加'}委员会或领导小组相关规程</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cg/cgRule_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cgRule.id}">
		<input type="hidden" name="teamId" value="${teamId}">
		<div class="form-group">
			<label class="col-xs-3 control-label">是否当前规程</label>
			<div class="col-xs-6">
				<input type="checkbox" name="isCurrent" ${(cgRule.isCurrent)?"checked":""}/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 规程类型</label>
			<div class="col-xs-6">
				<select required id="typeSelect1" name="type" data-placeholder="请选择规程类型"
						data-rel="select2">
					<option></option>
					<c:forEach items="<%=CgConstants.CG_RULE_TYPE_MAP%>" var="cgRuleType">
						<option value="${cgRuleType.key}">${cgRuleType.value}</option>
					</c:forEach>
				</select>
				<script>
					$("#typeSelect1").val('${cgRule.type}');
				</script>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">规程确定时间</label>
			<div class="col-xs-6">
				<div class="input-group" style="width: 200px">
					<input class="form-control date-picker"
						   name="confirmDate"
						   type="text"
						   data-date-format="yyyy.mm.dd"
						   value="${cm:formatDate(cgRule.confirmDate,'yyyy.MM.dd')}"/>
					<span class="input-group-addon">
						<i class="fa fa-calendar bigger-110"></i>
					</span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">相关文件</label>
			<div class="col-xs-6">
				<input class="form-control" type="file" name="_file" multiple="multiple"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">备注</label>
			<div class="col-xs-6">
				<textarea class="form-control noEnter" name="remark">${cgRule.remark}</textarea>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty cgRule?'确定':'添加'}</button>
</div>
<script>

	$.fileInput($("#modalForm input[type=file]"),{
		no_file:'请上传pdf文件',
		allowExt: ['pdf'],
		allowMime: ['application/pdf']
	});

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid_current").trigger("reloadGrid");
                        $("#jqGrid_history").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $.register.date($('.date-picker'));

</script>