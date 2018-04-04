<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta charset="utf-8"/>
<title>${sysConfig.schoolName}${sysConfig.mobilePlantformName}</title>
<meta name="description" content="Mailbox with some customizations as described in docs"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
<link rel="stylesheet" href="${ctx}/mobile/css/main.css" />
<link rel="stylesheet" href="${ctx}/mobile/css/extend.css" />
<link rel="stylesheet" href="${ctx}/mobile/css/setup.css" />
<script src="${ctx}/assets/js/ace-extra.js"></script>
<script src='${ctx}/mobile/js/main.js'></script>
<script type="text/javascript">
    if ('ontouchstart' in document.documentElement) document.write("<script src='${ctx}/assets/js/jquery.mobile.custom.js'>" + "<" + "/script>");
</script>
<script>var ctx = "${ctx}";</script>
<t:script src="/mobile/js/extend.js"/>
<t:script src="/js/prototype.js"/>
<t:script src="/js/jquery.extend.js"/>
<t:script src="/mobile/js/setup.js"/>
