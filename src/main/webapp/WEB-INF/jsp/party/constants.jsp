<%@ page import="sys.constants.OwConstants" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set value="${_pMap['owCheckIntegrity']=='true'}" var="_p_owCheckIntegrity"/>
<c:set value="<%=OwConstants.OW_ORG_ADMIN_PARTY%>" var="OW_ORG_ADMIN_PARTY"/>

<c:set value="${_pMap['use_inside_pgb']=='true'}" var="_p_use_inside_pgb"/>

