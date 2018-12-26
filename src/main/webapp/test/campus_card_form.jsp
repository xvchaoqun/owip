<%@ page import="domain.pmd.PmdOrder" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="sys.utils.MD5Util" %>
<%@ page import="sys.utils.PropertiesUtils" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page import="service.sys.SysUserService" %>
<%@ page import="domain.sys.SysUserView" %>
<%@ page import="sys.utils.DateUtils" %>
<%@ page import="org.apache.commons.lang.math.RandomUtils" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
    String code = request.getParameter("code");
    if(code!=null) {
        SysUserService sysUserService = CmTag.getBean(SysUserService.class);
        SysUserView uv = sysUserService.findByCode(code);

        if (uv != null) {
            String amount = request.getParameter("amount");
            String paycode = PropertiesUtils.getString("pay.campuscard.paycode");
            String keys = PropertiesUtils.getString("pay.campuscard.keys");

            PmdOrder bean = new PmdOrder();
            bean.setPayer(uv.getCode());
            bean.setPayername(uv.getRealname());
            bean.setSn(DateUtils.getCurrentDateTime("yyyyMMddHHmmss")+ String.format("%03d", RandomUtils.nextInt(999)) + "test");
            bean.setAmt(amount);
            String md5Str = keys + paycode + bean.getSn() +
                    bean.getAmt() + bean.getPayer() +
                    bean.getPayername() + StringUtils.reverse(keys);
            String sign = MD5Util.md5Hex(md5Str, "utf-8").toUpperCase();
            bean.setSign(sign);

            Map<String, Object> params = new HashMap<>();
            params.put("paycode", paycode);
            params.put("payer", bean.getPayer());
            params.put("payername", bean.getPayername());
            params.put("payertype", "1");
            params.put("amt", bean.getAmt());
            params.put("macc", "");
            params.put("commnet", "");
            params.put("sno_id_name", bean.getPayername());
            params.put("sn", bean.getSn());
            params.put("sign", sign);

            request.setAttribute("order", params);
        }
    }
    request.setAttribute("pay_url", PropertiesUtils.getString("pay.campuscard.url"));
%>

<form id="payForm" action="${pay_url}" target="_blank" method="post">
    <table>
        <tr>
            <td>缴费项目</td>
            <td><input type="text" name="paycode" value="${order.paycode}"/></td>
        </tr>
        <tr>
            <td>缴费人账号</td>
            <td><input type="text" name="payer" value="${order.payer}"/></td>
        </tr>
        <tr>
            <td>缴费人账号类型</td>
            <td><input type="text" name="payertype" value="${order.payertype}"/></td>
        </tr>
        <tr>
            <td>缴费人姓名</td>
            <td><input type="text" name="payername" value="${order.payername}"/></td>
        </tr>
        <tr>
            <td>该次缴费的订单号</td>
            <td><input type="text" name="sn" value="${order.sn}"/></td>
        </tr>
        <tr>
            <td>该次缴费金额</td>
            <td><input type="text" name="amt" value="${order.amt}"/></td>
        </tr>
        <tr>
            <td>收费商户账号</td>
            <td><input type="text" name="macc" value="${order.macc}"/></td>
        </tr>
        <tr>
            <td>缴费说明</td>
            <td><input type="text" name="commnet" value="${order.commnet}"/></td>
        </tr>
        <tr>
            <td>提交人的学工号</td>
            <td><input type="text" name="sno_id_name" value="${order.snoIdName}"/></td>
        </tr>
        <tr>
            <td>sign</td>
            <td><input type="text" name="sign" value="${order.sign}"/></td>
        </tr>
    </table>
    <input type="submit" value="去支付">
</form>
</body>
</html>
