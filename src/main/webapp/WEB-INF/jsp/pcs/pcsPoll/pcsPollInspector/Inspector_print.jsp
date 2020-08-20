<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=RequestUtils.getHomeURL(request)%>" var="homeURL"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta charset="UTF-8"/>
    <title>账号打印</title>
    <script type="text/javascript" src="${ctx}/assets/js/jquery.js"></script>
    <script type="text/javascript" src="${ctx}/js/qrcode.min.js"></script>
</head>
<body onload="print()">
<c:forEach items="${inspectors}" var="inspector" varStatus="vs">
    <div style="width:794px;height:1123px;border:0px solid #000000;">
        <table style="width: 100%;padding:0px 50px;">
            <tr>
                <td style="height: 60px"></td>
            </tr>
            <tr>
                <td align="center"><span style='font-size:22.0pt;font-family:方正小标宋简体'>党代会推荐票说明</span></td>
            </tr>
            <tr>
                <td style="height: 30px"></td>
            </tr>
            <tr>
                <td align="left">
                    <span style='font-size:15.0pt;font-family:黑体'>一、账号信息</span>
                </td>
            </tr>
            <tr>
                <td align="left" style="padding-right: 70px">
                    <table style="border: 1px solid;width: 100%; margin: 10px 30px;padding: 10px;">
                        <tr>
                            <td align="right">
                                <span style='font-size:14.0pt;font-family:黑体;'>系统网址：</span>
                            </td>
                            <td align="left">${homeURL}/pcs/login</td>
                            <td rowspan="4" align="center" width="150">
                                <c:set var="loginUrl" value="${homeURL}/pcs/login?u=${inspector.username}&p=${inspector.passwd}"/>
                                <span style="display: none">${loginUrl}</span>
                                <div class="qrcode"
                                     data-url="${loginUrl}"
                                     style="width:120px; height:120px;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">
                                <span style='font-size:14.0pt;font-family:黑体;'>账号：</span>
                            </td>
                            <td align="left"><span style="${inspector.isFinished?'color:green':''}">${inspector.username}</span></td>
                        </tr>
                        <tr>
                            <td align="right">
                                <span style='font-size:14.0pt;font-family:黑体;'>密码：</span>
                            </td>
                            <td align="left">
                                ${inspector.passwd}
                            </td>
                        </tr>
                        <%--<tr>
                            <td align="right">
                                <span style='font-size:14.0pt;font-family:黑体;'>手机端登录：</span>
                            </td>
                            <td align="left">
                                扫描右侧二维码。
                            </td>
                        </tr>--%>
                    </table>
                </td>
            </tr>
            <tr>
                <td align="left">
                    <span style='font-size:15.0pt;font-family:黑体'>二、使用说明</span>
                </td>
            </tr>
            <tr>
                <td align="left" style="padding-right: 70px">
                    <table style="border: 0px solid;width: 100%; margin: 10px 30px;">
                        <tr>
                            <td><span style='font-size:12.0pt;font-family:方正小标宋简体'>${pcsPoll.notice}</span></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    <div style="page-break-after: always; clear: left;"></div>
    <div class="PageNext"></div>
</c:forEach>

<script type="text/javascript">
    $(".qrcode").each(function () {
        var qrcode = new QRCode(this, {
            width: 120,
            height: 120
        });
        qrcode.makeCode($(this).data("url"));
    })
</script>
</body>
</html>