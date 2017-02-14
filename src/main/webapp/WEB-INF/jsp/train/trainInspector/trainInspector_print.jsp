<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta charset="UTF-8" />
<title>账号打印</title>
<%--<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/jquery.PrintArea.js"></script>
<script>
function printResult(){

	$("#printArea").printArea();
}
</script>--%>
<style>
table tr td {
	padding-left: 10px;
}

table tr td.title{
	text-align:center;
	font-size: 18pt;
	font-weight: bolder;
	padding-bottom: 20px;
	vertical-align: bottom;
}

</style>
</head>
<body>
<%--<div style="width: 850px;text-align: right;font-size: 20pt">
	<a href="javascript:printResult()"
	class="btn btn-success btn-xs"><i class="icon-print icon-white"></i>
	打印</a>
</div>--%>
<div style="width: 1200px" id="printArea">
<c:forEach items="${trainInspectors}" var="inspector" varStatus="vs">
<table style="width: 550px;float: left;margin: 10px;padding-right:22px; <c:if test='${(vs.index+1)%2==1}'> padding-right:21px; border-right: 1px dashed;</c:if>">
	<tr>
		<td colspan="2">
			${trainCourse.note}
		</td>
	</tr>
	<tr>
		<td colspan="2" style="text-align:center;
	font-size: 18pt; 
	font-weight: bolder;
	padding-bottom: 20px;
	vertical-align: bottom;">账号信息</td>
	</tr>
	<tr>
		<td style="
	/*width: 140px;*/
	text-align: right;
	vertical-align: middle;
	font-weight: bolder;
	padding-left: 0px;">评课系统网址：</td>
		<td><fmt:message key="site.train.login" bundle="${spring}"/></td>
	</tr>
	<tr>
		<td style="
	/*width: 140px;*/
	text-align: right;
	vertical-align: middle;
	font-weight: bolder;
	padding-left: 0px;">培训班次：</td>
		<td>${train.name}</td>
	</tr>
	<tr>
		<td style="
	/*width: 140px;*/
	text-align: right;
	vertical-align: middle;
	font-weight: bolder;
	padding-left: 0px;">账号：</td>
		<td>${inspector.username}</td>
	</tr>
	<tr>
		<td style="
	/*width: 140px;*/
	text-align: right;
	vertical-align: middle;
	font-weight: bolder;
	padding-left: 0px;">密码：</td>
		<td>
			<c:if test="${inspector.passwdChangeType==TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF}">
				******
			</c:if>
			<c:if test="${inspector.passwdChangeType!=TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF}">
			${inspector.passwd}
			</c:if>
		</td>
	</tr>
</table>
<fmt:message key="site.train.export.pagesize" bundle="${spring}" var="pagesize"/>
<c:if test='${(vs.index+1)%pagesize==0}'>
<div style="page-break-after: always; clear: left;"></div>
	<div class="PageNext"></div>
</c:if>
</c:forEach>
</div>
</body>
</html>