<%@ tag description="排序字段" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="id" type="java.lang.String" required="false" %>
<%@ attribute name="css" type="java.lang.String" required="false" %>
<form class="${css}" id="${id}">
    <input type="hidden" name="sort" value="${param.sort}">
    <input type="hidden" name="order" value="${param.order}">
    <jsp:doBody/>
</form>
