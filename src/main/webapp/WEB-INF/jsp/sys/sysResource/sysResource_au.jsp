<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal">×</button>
	<h3><c:if test="${not empty param.parentId}"><span class="text-error">${parent.name}</span>-</c:if>
		<c:if test="${not empty param.id}"><span class="text-error">${sysResource.name}</span>-</c:if>
	${op}</h3>
</div>
<div  class="modal-body">
	<form:form method="post" commandName="sysResource" class="form-horizontal">
        <form:hidden path="id"/>
        <form:hidden path="available"/>
		<%--<form:hidden path="parentIds"/>--%>

		<c:if test="${not empty parent}">
		<div class="form-group">
			<label class="col-xs-3 control-label">
				父节点
			</label>
			<div class="col-xs-6 ">
				<select name="parentId">
					<option value="${parent.id}">${parent.name}</option>
				</select>
			</div>
		</div>
		</c:if>
		<div class="form-group">
				<label class="col-xs-3 control-label">
					<c:if test="${param.parentId>0}">子</c:if>节点名称
				</label>
				<div class="col-xs-6 ">
					<form:input path="name"  class="form-control"/>
				</div>
		</div>
		<div class="form-group">
				<label class="col-xs-3 control-label">类型</label>
				<div class="col-xs-6 ">
					<select id="typeSelect" name="type" data-placeholder="请选择资源类型"
							>
						<option></option>
						<option value="menu">菜单</option>
						<option value="url">链接</option>
						<option value="function">功能</option>
					</select>
					<script>
						$("#typeSelect").val('${sysResource.type}');
					</script>
				</div>
		</div>
		<div class="form-group menuNeeded">
				<label class="col-xs-3 control-label">排序</label>
				<div class="col-xs-6 ">
					<form:input path="sortOrder"  class="form-control" />
				</div>
		</div>
		<div class="form-group menuNeeded">
				<label class="col-xs-3 control-label">菜单样式</label>

			<div class="col-xs-8 ">
				<%
					List<String> icons = new LinkedList<String>();

					icons.add("fa fa-tachometer");
					icons.add("fa fa-star");
					icons.add("fa fa-star-o");
					icons.add("fa fa-pencil-square-o");
					icons.add("fa fa-users");
					icons.add("fa fa-sitemap");
					icons.add("fa fa-files-o");
					icons.add("fa fa-database");
					icons.add("fa fa-gear fa-spin");
					icons.add("fa fa-caret-right");
				%>
				<select id="menuCssSelect" name="menuCss" data-placeholder="请选择模块图标样式">
					<option></option>
					<c:forEach items="<%=icons%>" var="icon">
					<option value="${icon}">${icon}</option>
					</c:forEach>
				</select>
				<script>
					$("#menuCssSelect").val('${sysResource.menuCss}');
				</script>
			</div>
		</div>
		<div class="form-group menuNeeded">
				<label class="col-xs-3 control-label">URL路径</label>
				<div class="col-xs-6 ">
					<form:input path="url"  class="form-control" />
				</div>
		</div>
		<div class="form-group">
				<label class="col-xs-3 control-label">权限字符串</label>
				<div class="col-xs-6 ">
					<form:input path="permission"  class="form-control" />
					<div class="help-inline">由系统开发者维护</div>
				</div>
		</div>
    </form:form>
</div>
<div class="modal-footer">
	<a href="#" class="btn btn-default" data-dismiss="modal">取消</a> <input type="submit" class="btn btn-primary" value="${op}"/>
</div>

<script>

	/*if('${sysResource.type}'=="function"){
		$(".menuNeeded").hide();
	}else{
		$(".menuNeeded").show();
	}*/

	$("#modal select[name=parentId]").select2({
		ajax: {
			url: "${ctx}/sysResource_selects",
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
		},
		dropdownCssClass: "bigdrop",
		placeholder: "请选择上级对象"
	});

	$("#typeSelect").select2({ theme: "classic",width:'180px',allowClear:true })/*.on("select2:select",function(e){

		if($(this).val()=="function"){
			$(".menuNeeded").hide();
		}else{
			$(".menuNeeded").show();
		}
	});*/
	function format(state) {
		if (!state.id) return state.text; // optgroup
		return $("<span><i class='" + state.id.toLowerCase() + "'></i> " + state.text + "</span>");
	}
	$("#menuCssSelect").select2({
		templateResult: format,
		templateSelection: format,
		theme: "classic",width:'250px'
	});
	$(function(){
		
		//$('#modalForm input[type=checkbox],input[type=radio],input[type=file]').uniform();

		$("#modal input[type=submit]").click(function(){$("#modal form").submit();return false;});
		$("#modal form").validate({
				rules: {
					name: {
						required: true
					},
					type: {
						required: true
					},
					sortOrder: {
						digits: true
					},
					permission:{
						required: true
					}
				},
				submitHandler: function (form) {
					
					$(form).ajaxSubmit({
						success:function(data){
							if(data.success){
								_reload();
								toastr.success('操作成功。', '成功');
							}
						},error:function(ret){
							
							bootbox.alert("系统异常，请稍后再试。");
						}
					});
				}
			});
	 })
</script>