<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetTraineeType!=null}">编辑</c:if><c:if test="${cetTraineeType==null}">添加</c:if>参训人员类型</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetTraineeType_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetTraineeType.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>参训人员类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${cetTraineeType.name}">
				</div>
			</div>
            <div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>类型代码</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="code" value="${cetTraineeType.code}">
                        <span class="label-inline"> * 由开发人员维护</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>信息模板</label>
				<div class="col-xs-6">
                    <select required name="templateId" data-rel="select2"
                            data-width="275"
                            data-placeholder="请选择">
                        <option></option>
                        <c:forEach var="template" items="${CET_TRAINEE_TYPE_TEMPLATE_MAP}">
                            <option value="${template.key}">
                                    ${template.value}
                            </option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modalForm select[name=templateId]").val("${cetTraineeType.templateId}");
                    </script>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control limited"
                              name="remark">${cetTraineeType.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetTraineeType!=null}">确定</c:if><c:if test="${cetTraineeType==null}">添加</c:if></button>
</div>

<script>
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
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>