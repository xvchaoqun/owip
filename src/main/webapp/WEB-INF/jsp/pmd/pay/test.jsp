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
<form action="http://221.238.143.40:10500/zhifu/payAccept.aspx" method="post">

  <input type="text" name="orderDate" value="${orderDate}" style="width: 400px;"/>
  <br/> <br/>
  <input type="text" name="orderNo" value="${orderNo}" style="width: 400px;"/><br/> <br/>
  <input type="text" name="amount" value="${amount}" style="width: 400px;"/><br/> <br/>
  <input type="text" name="xmpch" value="${xmpch}" style="width: 400px;"/><br/> <br/>
  <input type="text" name="return_url" value="${return_url}" style="width: 400px;"/><br/> <br/>
  <input type="text" name="notify_url" value="${notify_url}" style="width: 400px;"/><br/> <br/>
  <input type="text" name="sign" value="${sign}" style="width: 400px;"/><br/> <br/>

  <input type="submit" value="submit">
</form>
</body>
</html>
