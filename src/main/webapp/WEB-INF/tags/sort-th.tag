<%@ tag description="排序字段" pageEncoding="UTF-8" body-content="tagdependent"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="field" type="java.lang.String" required="true" %>
<%@ attribute name="css" type="java.lang.String" required="false" %>
<th class="${css} sortable both <c:if test="${param.sort==field}">${param.order}</c:if>" data-field="${field}"><jsp:doBody/></th>
