<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta charset="utf-8"/>
<title><fmt:message key="site.school" bundle="${spring}"/>组织工作一体化平台</title>

<meta name="description" content="Mailbox with some customizations as described in docs"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>

<!-- bootstrap & fontawesome -->
<link rel="stylesheet" href="${ctx}/assets/css/bootstrap.css"/>
<link rel="stylesheet" href="${ctx}/assets/css/font-awesome.css"/>

<!-- page specific plugin styles -->

<!-- text fonts -->
<link rel="stylesheet" href="${ctx}/assets/css/ace-fonts.css"/>

<!-- ace styles -->
<link rel="stylesheet" href="${ctx}/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style"/>

<!-- inline styles related to this page -->
<link rel="stylesheet" href="${ctx}/mobile/css/setup.css" />

<!-- ace settings handler -->
<script src="${ctx}/assets/js/ace-extra.js"></script>

<script src='${ctx}/assets/js/jquery.js'></script>

<script type="text/javascript">
    if ('ontouchstart' in document.documentElement) document.write("<script src='${ctx}/assets/js/jquery.mobile.custom.js'>" + "<" + "/script>");
</script>
<script src="${ctx}/assets/js/bootstrap.js"></script>

<!-- ace scripts -->
<script src="${ctx}/assets/js/ace/ace.js"></script>
<script src="${ctx}/assets/js/ace/ace.sidebar.js"></script>
<script>
    var ctx = "${ctx}";
</script>

<script src="${ctx}/extend/js/jquery.showLoading.min.js"></script>
<script src="${ctx}/extend/js/bootbox.min.js"></script>
<script src="${ctx}/js/prototype.js"></script>
<script src="${ctx}/extend/js/custom.js"></script>
<script src="${ctx}/mobile/js/jquery.extend.js"></script>
<script src="${ctx}/mobile/js/setup.js"></script>
