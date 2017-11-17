<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: lm
  Date: 2017/11/7
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<c:set var="test" value="http://221.238.143.40:10500/zhifu/payAccept.aspx"/>
<c:set var="real" value="http://wszf.bnu.edu.cn/zhifu/payAccept.aspx"/>
<form action="${param.type==1?real:test}" method="post" target="_blank">

  <input type="text" name="orderDate" value="${orderDate}" style="width: 400px;"/>
  <br/> <br/>
  <input type="text" name="orderNo" value="${orderNo}" style="width: 400px;"/><br/> <br/>
  <input type="text" name="amount" value="${amount}" style="width: 400px;"/><br/> <br/>
  <input type="text" name="xmpch" value="${xmpch}" style="width: 400px;"/><br/> <br/>
  <input type="text" name="return_url" value="${return_url}" style="width: 400px;"/><br/> <br/>
  <input type="text" name="notify_url" value="${notify_url}" style="width: 400px;"/><br/> <br/>
  <input type="text" name="sign" value="${sign}" style="width: 400px;"/><br/> <br/>

  <input type="submit" value="缴费测试">
</form>

<br/>
模拟测试：
<br/>
<a href="${ctx}/pmd/pay/step?step=0" target="_blank">设置所有党员的额度</a>
<br/><br/>
<a href="${ctx}/pmd/pay/step?step=1" target="_blank">所有党员缴费</a>
<br/><br/>
<a href="${ctx}/pmd/pay/step?step=2" target="_blank">报送所有支部</a>
<br/><br/>
<a href="${ctx}/pmd/pay/step?step=3" target="_blank">报送所有分党委</a>

</body>
</html>
