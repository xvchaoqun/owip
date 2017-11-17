<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<table class="table table-bordered">
    <tr>
        <th nowrap>起始使用时间</th>
        <th nowrap>截止使用时间</th>
        <th nowrap>开启操作人</th>
        <th nowrap>关闭操作人</th>
    </tr>
    <c:forEach items="${pmdNormValueLogs}" var="log">
        <tr>
            <td nowrap>${cm:formatDate(log.startTime, "yyyy-MM-dd HH:mm:ss")}</td>
            <td nowrap>${cm:formatDate(log.endTime, "yyyy-MM-dd HH:mm:ss")}</td>
            <td>${cm:getUserById(log.startUserId).realname}</td>
            <td>${cm:getUserById(log.endUserId).realname}</td>
        </tr>
    </c:forEach>
</table>

