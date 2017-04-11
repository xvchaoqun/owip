<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li  class="<c:if test="${cls == 1}">active</c:if>">
        <a href="?cls=1"><i class="fa fa-th"></i> 出生时间</a>
    </li>
    <li  class="<c:if test="${cls==2}">active</c:if>">
        <a href="?cls=2"><i class="fa fa-th"></i> 参加工作时间</a>
    </li>
</ul>