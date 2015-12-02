<#assign TableName=tbn(tablesqlname, "TableName")>
<#assign tableName=tbn(tablesqlname, "tableName")>
<#assign tablename=tbn(tablesqlname, "tablename")>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="&{${tableName}!=null}">编辑</c:if><c:if test="&{${tableName}==null}">添加</c:if>${cnTableName}</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="&{ctx}/${tableName}_au" id="modalForm" method="post">
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
    <input type="submit" class="btn btn-primary" value="<c:if test="&{${tableName}!=null}">确定</c:if><c:if test="&{${tableName}==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm").validate({
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