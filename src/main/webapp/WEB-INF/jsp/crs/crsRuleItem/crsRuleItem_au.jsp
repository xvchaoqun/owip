<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${crsRuleItem!=null}">编辑</c:if><c:if test="${crsRuleItem==null}">添加</c:if>具体条件</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crsRuleItem_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${crsRuleItem.id}">
        <input type="hidden" name="requireRuleId" value="${crsRequireRule.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">选择类别</label>
				<div class="col-xs-6">
                    <select  class="form-control" name="type" data-rel="select2" data-placeholder="请选择">
                        <option></option>
                        <c:forEach items="${CRS_POST_RULE_TYPE_MAP}" var="type">
                            <option value="${type.key}">${type.value}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modalForm select[name=type]").val('${crsRuleItem.type}');
                    </script>
				</div>
			</div>
            <div class="form-group">
				<label class="col-xs-3 control-label">规格</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="val" value="${crsRuleItem.val}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control limited" type="text" name="remark" >${crsRuleItem.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${crsRuleItem!=null}">确定</c:if><c:if test="${crsRuleItem==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        currentExpandRows = ["${crsRequireRule.id}"];
                        $("#modal").modal("hide");
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>