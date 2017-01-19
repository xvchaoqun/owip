<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="row${param.row}" var="key"></c:set>
<c:set value="${rs.get(key)}" var="row"></c:set>
<td ${(empty param.type && param.row==1)?'class=xl83 colspan=2':'class=xl73'}>${row.get(0)}</td>
<c:if test="${not empty param.type || param.row!=1}">
<td class=xl83>${row.get(1)}</td>
</c:if>
<td class=xl71>${row.get(2)}</td>
<td class=xl75>${row.get(3)}</td>
<td class=xl73>${row.get(4)}</td>
<td class=xl74>${row.get(5)}</td>
<td class=xl73>${row.get(6)}</td>
<td class=xl83>${row.get(7)}</td>
<td class=xl71>${row.get(8)}</td>
<td class=xl74>${row.get(9)}</td>
<td class=xl73>${row.get(10)}</td>
<td class=xl74>${row.get(11)}</td>
