<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<shiro:user>
	<c:redirect url="/m/index"/>
</shiro:user>
<jsp:include page="/ext/m_login.jsp"/>