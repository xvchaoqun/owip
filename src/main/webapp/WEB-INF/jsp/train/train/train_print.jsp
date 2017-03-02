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
<body>
<div style="width: 1200px;" id="printArea">
	<table style="width: 1000px;padding-left: 120px;padding-right: 80px;">
		<tr>
			<td colspan="2">
				${train.note}
			</td>
		</tr>
		<tr>
			<td></td>
			<td align="left">
				<fmt:message key="site.train.login" bundle="${spring}" var="loginUrl"/>
				<c:set var="loginUrl" value="${loginUrl}?trainId=${train.id}"></c:set>
				<img src="${ctx}/qrcode?content=${cm:encodeURI(loginUrl)}"/>
			</td>
		</tr>
		<tr style="height: 100px;font-size: 20pt;font-weight: bolder">
			<td align="right">培训班次：</td>
			<td align="left">${train.name}</td>
		</tr>
		<tr style="font-size: 20pt;font-weight: bolder">
			<td align="right">说明：</td>
			<td align="left">请用手机扫描以上二维码进行登录</td>
		</tr>
	</table>
</div>

</body>
</html>