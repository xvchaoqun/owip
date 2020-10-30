<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<shiro:user>
    <c:redirect url="/"/>
</shiro:user>
<jsp:include page="/ext/login.jsp"/>