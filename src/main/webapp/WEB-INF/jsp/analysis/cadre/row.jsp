<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="row${param.row}" var="key"></c:set>
<c:set value="${rs.get(key)}" var="row"></c:set>
<c:set value="unitTypeGroup=${unitTypeGroup}&cadreType=${cadreType}" var="date"></c:set>
<c:set value="${param.firstData}" var="firstData"></c:set>
<c:set value="&firstTypeCode=${param.firstTypeCode}&firstTypeNum=${param.firstTypeNum}" var="paramDate"/>
<td ${(empty param.type && param.row==1)?'class=xl83 colspan=2':'class=xl73'}>
    <c:if test="${row.get(0)==0}">0</c:if>
    <c:if test="${row.get(0)>0}">
        <a href="javascript:;" class="popupBtn"
           data-url="${ctx}/stat_cadre_list?${date}${paramDate}&secondNum=1">${row.get(0)}</a>
    </c:if>
</td>
<c:if test="${not empty param.type || param.row!=1}">
    <td class=xl83>${row.get(1)}</td>
</c:if>
<td class=xl71>
    <c:if test="${row.get(2)==0}">0</c:if>
    <c:if test="${row.get(2)>0}">
        <a href="javascript:;" class="popupBtn"
           data-url="${ctx}/stat_cadre_list?${date}${paramDate}&secondNum=2">${row.get(2)}</a>
    </c:if>
</td>
<td class=xl75>${row.get(3)}</td>
<td class=xl73>
    <c:if test="${row.get(4)==0}">0</c:if>
    <c:if test="${row.get(4)>0}">
        <a href="javascript:;" class="popupBtn"
           data-url="${ctx}/stat_cadre_list?${date}${paramDate}&secondNum=3">${row.get(4)}</a>
    </c:if>
</td>
<td class=xl74>${row.get(5)}</td>
<td class=xl73>
    <c:if test="${row.get(6)==0}">0</c:if>
    <c:if test="${row.get(6)>0}">
        <a href="javascript:;" class="popupBtn"
           data-url="${ctx}/stat_cadre_list?${date}${paramDate}&secondNum=4">${row.get(6)}</a>
    </c:if>
</td>
<td class=xl83>${row.get(7)}</td>
<td class=xl71>
    <c:if test="${row.get(8)==0}">0</c:if>
    <c:if test="${row.get(8)>0}">
        <a href="javascript:;" class="popupBtn"
           data-url="${ctx}/stat_cadre_list?${date}${paramDate}&secondNum=5">${row.get(8)}</a>
    </c:if>
</td>
<td class=xl74>${row.get(9)}</td>
<td class=xl73>
    <c:if test="${row.get(10)==0}">0</c:if>
    <c:if test="${row.get(10)>0}">
        <a href="javascript:;" class="popupBtn"
           data-url="${ctx}/stat_cadre_list?${date}${paramDate}&secondNum=6">${row.get(10)}</a>
    </c:if>
</td>
<td class=xl74>${row.get(11)}</td>
