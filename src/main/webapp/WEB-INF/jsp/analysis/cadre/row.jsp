<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="row${param.row}" var="key"></c:set>
<c:set value="${rs.get(key)}" var="row"></c:set>
<c:set value="${cm:encodeQueryString(pageContext.request.queryString)}&firstTypeCode=${param.firstTypeCode}&firstTypeNum=${param.firstTypeNum}" var="params"/>
<td ${(empty param.type && param.row=="1")?'class=xl83 colspan=2':'class=xl73'}>
    <c:if test="${row.get(0)==0}">0</c:if>
    <c:if test="${row.get(0)>0}">
        <c:if test="${param.firstTypeCode == null}">
            ${row.get(0)}
        </c:if>
        <c:if test="${param.firstTypeCode != null}">
            <a href="javascript:;" class="popupBtn" data-width="750"
               data-url="${ctx}/stat_cadre?${params}&export=2&secondNum=1">${row.get(0)}</a>
        </c:if>
    </c:if>
</td>
<c:if test="${not empty param.type || param.row!='1'}">
    <td class=xl83>${row.get(1)}</td>
</c:if>
<td class=xl71>
    <c:if test="${row.get(2)==0}">0</c:if>
    <c:if test="${row.get(2)>0}">
        <c:if test="${param.firstTypeCode == null}">
            ${row.get(2)}
        </c:if>
        <c:if test="${param.firstTypeCode != null}">
            <a href="javascript:;" class="popupBtn" data-width="750"
               data-url="${ctx}/stat_cadre?${params}&export=2&secondNum=2">${row.get(2)}</a>
        </c:if>
    </c:if>
</td>
<td class=xl75>${row.get(3)}</td>
<td class=xl73>
    <c:if test="${row.get(4)==0}">0</c:if>
    <c:if test="${row.get(4)>0}">
        <c:if test="${param.firstTypeCode == null}">
            ${row.get(4)}
        </c:if>
        <c:if test="${param.firstTypeCode != null}">
            <a href="javascript:;" class="popupBtn" data-width="750"
               data-url="${ctx}/stat_cadre?${params}&export=2&secondNum=3">${row.get(4)}</a>
        </c:if>
    </c:if>
</td>
<td class=xl74>${row.get(5)}</td>
<td class=xl73>
    <c:if test="${row.get(6)==0}">0</c:if>
    <c:if test="${row.get(6)>0}">
        <c:if test="${param.firstTypeCode == null}">
            ${row.get(6)}
        </c:if>
        <c:if test="${param.firstTypeCode != null}">
            <a href="javascript:;" class="popupBtn" data-width="750"
               data-url="${ctx}/stat_cadre?${params}&export=2&secondNum=4">${row.get(6)}</a>
        </c:if>
    </c:if>
</td>
<td class=xl83>${row.get(7)}</td>
<td class=xl71>
    <c:if test="${row.get(8)==0}">0</c:if>
    <c:if test="${row.get(8)>0}">
        <c:if test="${param.firstTypeCode == null}">
            ${row.get(8)}
        </c:if>
        <c:if test="${param.firstTypeCode != null}">
            <a href="javascript:;" class="popupBtn" data-width="750"
               data-url="${ctx}/stat_cadre?${params}&export=2&secondNum=5">${row.get(8)}</a>
        </c:if>
    </c:if>
</td>
<td class=xl74>${row.get(9)}</td>
<td class=xl73>
    <c:if test="${row.get(10)==0}">0</c:if>
    <c:if test="${row.get(10)>0}">
        <c:if test="${param.firstTypeCode == null}">
            ${row.get(10)}
        </c:if>
        <c:if test="${param.firstTypeCode != null}">
            <a href="javascript:;" class="popupBtn" data-width="750"
               data-url="${ctx}/stat_cadre?${params}&export=2&secondNum=6">${row.get(10)}</a>
        </c:if>
    </c:if>
</td>
<td class=xl74>${row.get(11)}</td>
