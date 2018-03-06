<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="shortcut icon" type="image/x-icon" href="${ctx}/img/favicon.ico">
    <title>${param.type=='unsupport'?'您的浏览器不支持访问':'推荐浏览器下载'}</title>
    <meta name="Keywords" content="组工系统">
    <meta name="Description" content="专注组工系统">
    <link rel="stylesheet" href="${ctx}/extend/css/browsers.css">
    <c:if test="${param.type=='unsupport'}">
    <!--[if gte IE 9]> -->
    <script type="text/javascript">location.href="${ctx}/";</script>
    <!-- <![endif]-->
    </c:if>
</head>
<body>
<div id="container">
    <h1>为了您流畅使用本系统，建议安装IE9以上的浏览器或以下现代浏览器</h1>
    <table class="browsers">
        <tr>
            <td>
                <a class="clearfix" href="http://rj.baidu.com/soft/detail/14744.html?ald" target="_blank">
                    <img src="${ctx}/extend/img/chrome.png" alt="Download Chrome">
                    <p>谷歌浏览器</p>
                    <p>（强烈推荐）</p>
                </a>
            </td>
            <td>
                <a class="clearfix" href="http://se.360.cn/" target="_blank">
                    <img src="${ctx}/extend/img/360.png" alt="Download 360浏览器">
                    <p>360浏览器</p>
                </a>
                <p>（<a href="${ctx}/extend/360.html" target="_blank" style="text-decoration: none;">
                    安装后请选择“<span style="font-weight: bolder;color: green">极速模式</span>”，点击查看如何设置极速模式</a>）</p>
            </td>

            <td>
                <a class="clearfix" href="http://rj.baidu.com/soft/detail/11843.html" target="_blank">
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
        <p>&copy; Copyright  组织工作管理与服务一体化平台</p>
    </div>
</div>
<!--[if !IE]> -->
<script type="text/javascript">
    window.jQuery || document.write("<script src='${ctx}/assets/js/jquery.js'>"+"<"+"/script>");
</script>
<!-- <![endif]-->
<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='${ctx}/assets/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->
<script src="${ctx}/extend/js/DD_belatedPNG.js"></script>
<script type="text/javascript">
    DD_belatedPNG.fix('img');
    <c:if test="${param.type=='unsupport'}">
    $.post("/monitor",{type:'unsupport'});
    </c:if>
</script>
</body>
</html>