<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="taglibs.jsp"%>
<script type="text/javascript">var ctx="${ctx}"; var _permissions=${cm:toJSONObject(cm:findPermissions(_user.username))};var _uploadMaxSize=${_uploadMaxSize}</script>
<!--[if lt IE 9]>
<script type="text/javascript">location.href="${ctx}/extend/unsupport.html";</script>
<![endif]-->
<script src="${ctx}/js/main.js"></script>
<script type="text/javascript">if('ontouchstart' in document.documentElement) document.write("<script src='${ctx}/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");</script>
<script src="${ctx}/js/ui.js"></script>
<script src="${ctx}/js/extend.js?_=${cm:lastModified(cm:getJsFilePath("extend.js"))}"></script>
<script src="${ctx}/js/prototype.js?_=${cm:lastModified(cm:getJsFilePath("prototype.js"))}"></script>
<script src="${ctx}/js/location.js?_=${cm:lastModified(cm:getJsFilePath("location.js"))}"></script>
<script src="${ctx}/js/metadata.js?_=${cm:lastModified(cm:getJsFilePath("metadata.js"))}"></script>
<script src="${ctx}/js/jquery.extend.js?_=${cm:lastModified(cm:getJsFilePath("jquery.extend.js"))}"></script>
<script src="${ctx}/js/setup.js?_=${cm:lastModified(cm:getJsFilePath("setup.js"))}"></script>