<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
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
<script type="text/javascript" src="${ctx}/assets/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/qrcode.min.js"></script>
</head>
<body>
<%--<div style="width: 850px;text-align: right;font-size: 20pt">
	<a href="javascript:printResult()"
	class="btn btn-success btn-xs"><i class="icon-print icon-white"></i>
	打印</a>
</div>--%>
<div style="width: 1200px" id="printArea">
<c:forEach items="${cetTrainInspectors}" var="inspector" varStatus="vs">
<table style="width: 580px;float: left;margin: 10px;padding-right:22px; <c:if test='${(vs.index+1)%2==1}'> padding-right:21px; border-right: 1px dashed;</c:if>">
	<tr>
		<td colspan="3">
			${cetTrain.evaNote}
		</td>
	</tr>
	<tr>
		<td colspan="3" style="text-align:center;
	font-size: 18pt; 
	font-weight: bolder;
	padding-bottom: 20px;
	vertical-align: bottom;">评课账号信息</td>
	</tr>
	<tr>
		<td style="
	/*width: 140px;*/
	text-align: right;
	vertical-align: middle;
	font-weight: bolder;
	padding-left: 0px;" nowrap>评课系统网址：</td>
		<td>${cetInspectorLoginUrl}</td>
		<td rowspan="4">
			<div class="qrcode" data-url="${cetInspectorLoginUrl}?u=${inspector.username}&p=${inspector.passwd}"
				 style="width:100px; height:100px;"></div>
		</td>
	</tr>
	<tr>
		<td style="
	/*width: 140px;*/
	text-align: right;
	vertical-align: middle;
	font-weight: bolder;
	padding-left: 0px;">培训班次：</td>
		<td>${cetTrain.name}</td>

	</tr>
	<tr>
		<td style="
	/*width: 140px;*/
	text-align: right;
	vertical-align: middle;
	font-weight: bolder;
	padding-left: 0px;">账号：</td>
		<td style="color:${inspector.finishCourseNum>0?(inspector.finishCourseNum==courseNum?'green':'blue'):'auto'}">${inspector.username}</td>
	</tr>
	<tr>
		<td style="
	/*width: 140px;*/
	text-align: right;
	vertical-align: middle;
	font-weight: bolder;
	padding-left: 0px;">密码：</td>
		<td>
			<c:if test="${inspector.passwdChangeType==CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF}">
				******
			</c:if>
			<c:if test="${inspector.passwdChangeType!=CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF}">
			${inspector.passwd}
			</c:if>
		</td>
	</tr>
</table>
<%--<fmt:message key="site.cetTrain.export.pagesize" bundle="${spring}" var="pagesize"/>--%>
<c:if test='${(vs.index+1)%param.pagesize==0}'>
<div style="page-break-after: always; clear: left;"></div>
	<div class="PageNext"></div>
</c:if>
</c:forEach>
</div>
<script type="text/javascript">
	$(".qrcode").each(function(){
		var qrcode = new QRCode(this, {
			width : 100,
			height : 100
		});
		qrcode.makeCode($(this).data("url"));
	})
</script>
</body>
</html>