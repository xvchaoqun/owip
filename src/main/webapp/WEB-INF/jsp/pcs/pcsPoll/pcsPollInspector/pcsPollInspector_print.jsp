<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pcs/constants.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta charset="UTF-8"/>
    <title>推荐票打印</title>
    <script type="text/javascript" src="${ctx}/assets/js/jquery.js"></script>
    <script type="text/javascript" src="${ctx}/js/qrcode.min.js"></script>
</head>
<body onload="print()">
<c:forEach items="${inspectors}" var="inspector" varStatus="vs">
    <div style="width: 1400px">
        <table style="width: 500px;float: left;margin: 10px;padding-right:10px; <c:if test='${(vs.index+1)%2==1}'> padding-right:10px; border-right: 1px dashed;</c:if>">
            <tr>
                <td style="height: 60px"></td>
            </tr>
            <tr>
                <td align="center"><span style='font-size:22.0pt;font-family:方正小标宋简体'>党代会推荐票</span></td>
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
                <td align="left" style="padding-right: 40px">
                    <table style="border: 1px solid;width: 100%; margin: 10px 20px;padding: 10px;">
                        <tr>
                            <td align="right">
                                <span style='font-size:14.0pt;font-family:黑体;'>网址：</span>
                            </td>
                            <td style="word-wrap:break-word;word-break:break-all;" align="left">${homeURL}/ddh</td>
                            <td rowspan="4" align="center" width="105">
                                <c:set var="loginUrl" value="${homeURL}/ddh?u=${inspector.username}&p=${inspector.passwd}"/>
                                <span style="display: none">${loginUrl}</span>
                                <div class="qrcode"
                                     data-url="${loginUrl}"
                                     style="width:100px; height:100px;"></div>
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
                    </table>
                </td>
            </tr>
            <tr>
                <td align="left">
                    <span style='font-size:15.0pt;font-family:黑体'>二、推荐票说明</span>
                </td>
            </tr>
            <tr>
                <td align="left" style="padding-right: 70px">
                    <table style="border: 0px solid;width: 100%; margin: 10px 30px;">
                        <tr>
                            <td>
                                <span style='font-size:12.0pt;font-family:方正小标宋简体'>
                                    <c:if test="${pcsPoll.stage==PcsConstants.PCS_POLL_FIRST_STAGE}">
                                        ${cm:htmlUnescape(_1_paper.content)}
                                    </c:if>
                                    <c:if test="${pcsPoll.stage==PcsConstants.PCS_POLL_SECOND_STAGE}">
                                        ${cm:htmlUnescape(_2_paper.content)}
                                    </c:if>
                                    <c:if test="${pcsPoll.stage==PcsConstants.PCS_POLL_THIRD_STAGE}">
                                        ${cm:htmlUnescape(_3_paper.content)}
                                    </c:if>
                                </span>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    <c:if test='${(vs.index+1)%2==0}'>
        <div style="page-break-after: left;clear: left;"></div>
    </c:if>
</c:forEach>

<script type="text/javascript">
    $(".qrcode").each(function () {
        var qrcode = new QRCode(this, {
            width: 100,
            height: 100
        });
        qrcode.makeCode($(this).data("url"));
    })
</script>
</body>
</html>