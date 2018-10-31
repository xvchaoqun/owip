<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta name="description" content="${_sysConfig.siteName}"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,user-scalable=no"/>
<meta name="format-detection" content="telephone=no"/>
<link rel="shortcut icon" href="${ctx}/img/favicon64.ico?_=${cm:lastModified(cm:getAbsolutePath('/img/favicon64.ico'))}" type="image/x-icon">
<link rel="apple-touch-icon-precomposed" href="${ctx}/img/screen_icon_new.png?_=${cm:lastModified(cm:getAbsolutePath('/img/screen_icon_new.png'))}"/>
<link rel="stylesheet" href="${ctx}/mobile/css/main.css" />
<link rel="stylesheet" href="${ctx}/mobile/css/extend.css" />
<t:link href="${ctx}/mobile/css/setup.css"/>
<title>${_sysConfig.mobileTitle}</title>
<script src="${ctx}/assets/js/ace-extra.js"></script>
<script src='${ctx}/mobile/js/main.js'></script>
<script type="text/javascript">
    if ('ontouchstart' in document.documentElement) document.write("<script src='${ctx}/assets/js/jquery.mobile.custom.js'>" + "<" + "/script>");
</script>
<script>var ctx = "${ctx}";</script>
<t:script src="/mobile/js/extend.js"/>
<t:script src="/js/prototype.js"/>
<t:script src="/js/metadata.js"/>
<t:script src="/js/jquery.extend.js"/>
<t:script src="/mobile/js/setup.js"/>
