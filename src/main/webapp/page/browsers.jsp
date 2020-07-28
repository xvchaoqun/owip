<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="shortcut icon" type="image/x-icon" href="${ctx}/img/favicon.ico">
    <title>${param.type=='unsupport'?'您的浏览器不支持访问':'推荐浏览器下载'}</title>
    <link rel="stylesheet" href="${ctx}/extend/css/browsers.css">
    <c:if test="${param.type=='unsupport'}">
        <!--[if gte IE 9]> -->
        <script type="text/javascript">location.href = "${ctx}${empty param.url?'/':param.url}";</script>
        <!-- <![endif]-->
    </c:if>
</head>
<body>
<div id="container" style="text-align: center">
    <h1>为了您流畅使用本系统，建议安装IE9以上的浏览器或以下现代浏览器</h1>
    <table class="browsers">
        <tr>
            <td>
                <a class="clearfix" href="https://www.google.cn/intl/zh-CN/chrome" target="_blank">
                    <img src="${ctx}/extend/img/chrome.png" alt="Download Chrome">
                    <p>谷歌浏览器</p>
                    <p>（强烈推荐）</p>
                </a>
            </td>
            <td>
                <a class="clearfix" href="http://se.360.cn" target="_blank">
                    <img src="${ctx}/extend/img/360.png" alt="Download 360浏览器">
                    <p>360浏览器</p>
                </a>
                <p>（<a href="${ctx}/extend/360.html" target="_blank" style="text-decoration: none;">
                    安装后请选择“<span style="font-weight: bolder;color: green">极速模式</span>”，点击查看如何设置极速模式</a>）</p>
            </td>

            <td>
                <a class="clearfix" href="http://www.firefox.com.cn" target="_blank">
                    <img src="${ctx}/extend/img/firefox.png" alt="Download Firefox">
                    <p>Firefox</p>
                </a>
            </td>
        </tr>
    </table>
    <div class="footer">
        <c:if test="${param.type!='unsupport'}">
            <p><a href="${ctx}/">点击此处返回系统</a></p>
        </c:if>
        <p>&copy; Copyright ${_plantform_name}</p>
    </div>
</div>
<script type="text/javascript">
    <c:if test="${param.type=='unsupport'}">
        var ajax = new XMLHttpRequest();
        ajax.open("POST","/monitor?type=unsupport",true);
        ajax.send(null);
    </c:if>
</script>
</body>
</html>