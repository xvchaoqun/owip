<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<shiro:hasPermission name="suspend:ow">
    <c:import url="/suspend_ow"/>
</shiro:hasPermission>
<shiro:hasPermission name="suspend:oa">
    <c:import url="/suspend_oa"/>
</shiro:hasPermission>
