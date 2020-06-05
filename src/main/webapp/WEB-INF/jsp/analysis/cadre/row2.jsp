<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="row${param.row}" var="key"></c:set>
<c:set value="${rs.get(key)}" var="row"></c:set>
<td class=xl83 colspan=2>
${row.get(0)}
</td>
<td class=xl71 colspan=2>
${row.get(1)}
</td>
<td class=xl73 colspan=2>
${row.get(2)}
</td>
<td class=xl73 colspan=2>
${row.get(3)}
</td>
<td class=xl71 colspan=2>
${row.get(4)}
</td>
<td class=xl73 colspan=2>
${row.get(5)}
</td>
