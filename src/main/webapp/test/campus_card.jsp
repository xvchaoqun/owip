<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<br/>
<form id="payForm" action="campus_card_form.jsp" target="_blank" method="post">
    <table>
        <tr>
            <td>缴费学工号</td>
            <td><input type="text" name="code"/></td>
        </tr>
        <tr>
            <td>缴费金额</td>
            <td><input type="text" name="amount" value="0.01"/></td>
        </tr>
    </table>
    <br/>
    <input type="submit" value="生成支付订单">
</form>
</body>
</html>
