<#assign TableName=tbn(tablesqlname, "TableName")>
<#assign tableName=tbn(tablesqlname, "tableName")>
<#assign tablename=tbn(tablesqlname, "tablename")>
<#assign _resFolder="">
<#if resFolder?? && resFolder?trim!=''><#assign _resFolder="${resFolder?trim}/"></#if>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>&{${tableName}!=null?'编辑':'添加'}${cnTableName}</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="&{ctx}/${_resFolder}${tableName}_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="&{${tableName}.id}">
	<#list tableColumns as column>
		<#assign columnName=tbn(column.name, "tableName")>
			<div class="form-group">
				<label class="col-xs-3 control-label">${column.comments}</label>
				<div class="col-xs-6">
					<#if column.length gt 300>
                        <textarea class="form-control" name="${columnName}">&{${tableName}.${columnName}}</textarea>
					<#else>
                        <input required class="form-control" type="text" name="${columnName}" value="&{${tableName}.${columnName}}">
					</#if>
				</div>
			</div>
	</#list>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="&{${tableName}!=null}">确定</c:if><c:if test="&{${tableName}==null}">添加</c:if></button>
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
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>