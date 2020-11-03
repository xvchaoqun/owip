<%@ page import="ext.utils.Pay" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pmd/constants.jsp"%>
<html>
<head>
    <title>跳转中...</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
</head>
<body>
<div>跳转至支付平台，请稍后...</div>
<c:set value="<%=Pay.payURL%>" var="_payURL"/>
<form id="form" action="${cm:getStringProperty("payTest")?null:_payURL}" method="post">
    <input type="hidden" name="tranamt" value="${param.tranamt}"/>
    <input type="hidden" name="account" value="${param.account}"/>
    <input type="hidden" name="sno" value="${param.sno}"/>
    <input type="hidden" name="toaccount" value="${param.toaccount}"/>
    <input type="hidden" name="thirdsystem" value="${param.thirdsystem}"/>
    <input type="hidden" name="thirdorderid" value="${param.thirdorderid}"/>
    <input type="hidden" name="ordertype" value="${param.ordertype}"/>
    <input type="hidden" name="orderdesc" value="${param.orderdesc}"/>
    <input type="hidden" name="praram1" value="${param.praram1}"/>
    <input type="hidden" name="thirdurl" value="${param.thirdurl}"/>
    <input type="hidden" name="sign" value="${param.sign}"/>
</form>
<script>
    document.getElementById("form").submit();
</script>
</body>
</html>
