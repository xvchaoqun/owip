<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta charset="UTF-8" />
<title>登录二维码</title>
</head>
<body onload="print()">
<div style="width: 1200px; " id="printArea">
	<table style="width: 1100px;padding-left: 80px;padding-right: 80px;font-size: 20pt">
		<tr>
			<td colspan="2">
				${cetTrain.evaNote}
			</td>
		</tr>
		<tr>
			<td></td>
			<td align="left">
				<fmt:message key="site.train.login" bundle="${spring}" var="loginUrl"/>
				<c:set var="loginUrl" value="${loginUrl}?trainId=${cetTrain.id}" scope="request"></c:set>
				<img src="${ctx}/qrcode?content=${cm:encodeURI(loginUrl)}" style="width: 400px;margin: 20px"/>
				<%
					//System.out.println(request.getAttribute("loginUrl"));
				%>
			</td>
		</tr>
		<tr>
			<td align="right">培训班次：</td>
			<td align="left">${cetTrain.name}</td>
		</tr>
		<tr>
			<td align="right">说明：</td>
			<td align="left">请用手机扫描以上二维码进行登录</td>
		</tr>
	</table>
	<hr  style="height:1px;border-top:1px dashed; border-bottom:none;margin: 20px"/>
	<table style="width: 1100px;padding-left: 80px;padding-right: 80px;font-size: 20pt">
		<tr>
			<td colspan="2">
				${cetTrain.evaNote}
			</td>
		</tr>
		<tr>
			<td></td>
			<td align="left">
				<fmt:message key="site.train.login" bundle="${spring}" var="loginUrl"/>
				<c:set var="loginUrl" value="${loginUrl}?trainId=${cetTrain.id}"></c:set>
				<%--${loginUrl}--%>
				<img src="${ctx}/qrcode?content=${cm:encodeURI(loginUrl)}"  style="width: 400px;margin: 20px"/>
			</td>
		</tr>
		<tr>
			<td align="right">培训班次：</td>
			<td align="left">${cetTrain.name}</td>
		</tr>
		<tr>
			<td align="right">说明：</td>
			<td align="left">请用手机扫描以上二维码进行登录</td>
		</tr>
	</table>
</div>

</body>
</html>