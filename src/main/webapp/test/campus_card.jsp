<%@ page import="service.pmd.PayFormCampusCardBean" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="sys.utils.MD5Util" %>
<%@ page import="sys.utils.PropertiesUtils" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page import="service.sys.SysUserService" %>
<%@ page import="domain.sys.SysUserView" %>
<%@ page import="sys.utils.DateUtils" %>
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

            PayFormCampusCardBean bean = new PayFormCampusCardBean();
            bean.setPaycode(paycode);
            bean.setPayer(uv.getCode());
            // 缴费人账号类型，1：学工号，2：服务平台账号，3：校园卡号，4：身份证号
            bean.setPayertype("1");
            bean.setPayername(uv.getRealname());
            bean.setSn(DateUtils.getCurrentDateTime("yyyyMMddHHmmss") + "-test");
            bean.setAmt(amount);
            bean.setMacc("测试001");
            bean.setCommnet("党费收缴测试");
            bean.setSno_id_name(uv.getCode());
            String md5Str = keys + paycode + bean.getSn() +
                    bean.getAmt() + bean.getPayer() +
                    bean.getPayername() + StringUtils.reverse(keys);
            String sign = MD5Util.md5Hex(md5Str, "utf-8").toUpperCase();
            bean.setSign(sign);
            request.setAttribute("payFormBean", bean);
        }
    }
    request.setAttribute("pay_url", PropertiesUtils.getString("pay.campuscard.url"));
%>

<form id="payForm" action="${pay_url}" target="_blank" method="post">
    <table>
        <tr>
            <td>缴费项目</td>
            <td><input type="text" name="paycode" value="${payFormBean.paycode}"/></td>
        </tr>
        <tr>
            <td>缴费人账号</td>
            <td><input type="text" name="payer" value="${payFormBean.payer}"/></td>
        </tr>
        <tr>
            <td>缴费人账号类型</td>
            <td><input type="text" name="payertype" value="${payFormBean.payertype}"/></td>
        </tr>
        <tr>
            <td>缴费人姓名</td>
            <td><input type="text" name="payername" value="${payFormBean.payername}"/></td>
        </tr>
        <tr>
            <td>该次缴费的订单号</td>
            <td><input type="text" name="sn" value="${payFormBean.sn}"/></td>
        </tr>
        <tr>
            <td>该次缴费金额</td>
            <td><input type="text" name="amt" value="${payFormBean.amt}"/></td>
        </tr>
        <tr>
            <td>收费商户账号</td>
            <td><input type="text" name="macc" value="${payFormBean.macc}"/></td>
        </tr>
        <tr>
            <td>缴费说明</td>
            <td><input type="text" name="commnet" value="${payFormBean.commnet}"/></td>
        </tr>
        <tr>
            <td>提交人的学工号</td>
            <td><input type="text" name="sno_id_name" value="${payFormBean.sno_id_name}"/></td>
        </tr>
        <tr>
            <td>sign</td>
            <td><input type="text" name="sign" value="${payFormBean.sign}"/></td>
        </tr>
    </table>
    <input type="submit" value="去支付">
</form>
</body>
</html>
