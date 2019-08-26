<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="taglibs.jsp"%>
<script type="text/javascript">
	var ctx="${ctx}", isMobile=false, _hasLoginPage= ${_hasLoginPage=='true'},
			_permissions=${cm:toJSONObject(cm:findPermissions(_user.username, false))},
		_uploadMaxSize=${_uploadMaxSize}, _mobileRegex="${fn:replace(_pMap['mobileRegex'], "\\", "\\\\")}"
</script>
<!--[if lt IE 9]>
<script type="text/javascript">location.href="${ctx}/page/browsers.jsp?type=unsupport";</script>
<![endif]-->
<script src="${ctx}/js/main.js"></script>
<script type="text/javascript">if('ontouchstart' in document.documentElement) document.write("<script src='${ctx}/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");</script>
<script src="${ctx}/js/ui.js"></script>
<t:script src="/js/location.js"/>
<t:script src="/js/extend.js"/>
<t:script src="/js/prototype.js"/>
<t:script src="/js/metadata.js"/>
<t:script src="/js/jquery.extend.js"/>
<t:script src="/js/setup.js"/>