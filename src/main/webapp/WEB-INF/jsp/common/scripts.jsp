<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="taglibs.jsp"%>
<script>
	var ctx="${ctx}";
</script>
<!--[if lt IE 9]>
<script type="text/javascript">
location.href="${ctx}/extend/unsupport.html"
</script>
<![endif]-->
<script src="${ctx}/js/main.js"></script>
<script type="text/javascript">
	if('ontouchstart' in document.documentElement) document.write("<script src='${ctx}/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
</script>
<script src="${ctx}/js/ui.js"></script>
<script src="${ctx}/js/extend.js"></script>
<script type="text/javascript" src="${ctx}/location_JSON"></script>
<script type="text/javascript" src="${ctx}/metadata"></script>
<script src="${ctx}/js/setup.js"></script>