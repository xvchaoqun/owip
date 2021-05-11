<%@ page import="sys.constants.OwConstants" %>
<%@ page import="sys.constants.MemberConstants" %>
<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="USER_TYPE_MAP" value="<%=SystemConstants.USER_TYPE_MAP%>"/>
<c:set var="USER_TYPE_JZG" value="<%=SystemConstants.USER_TYPE_JZG%>"/>
<c:set var="USER_TYPE_RETIRE" value="<%=SystemConstants.USER_TYPE_RETIRE%>"/>
<c:set var="USER_TYPE_BKS" value="<%=SystemConstants.USER_TYPE_BKS%>"/>
<c:set var="USER_TYPE_SS" value="<%=SystemConstants.USER_TYPE_SS%>"/>
<c:set var="USER_TYPE_BS" value="<%=SystemConstants.USER_TYPE_BS%>"/>

<c:set var="MEMBER_AGE_MAP" value="<%=MemberConstants.MEMBER_AGE_MAP%>"/>

<c:set var="OW_APPLY_STAGE_MAP" value="<%=OwConstants.OW_APPLY_STAGE_MAP%>"/>
<c:set var="OW_APPLY_STAGE_INIT" value="<%=OwConstants.OW_APPLY_STAGE_INIT%>"/>
<c:set var="OW_APPLY_STAGE_PASS" value="<%=OwConstants.OW_APPLY_STAGE_PASS%>"/>
<c:set var="OW_APPLY_STAGE_CANDIDATE" value="<%=OwConstants.OW_APPLY_STAGE_CANDIDATE%>"/>
<c:set var="OW_APPLY_STAGE_PLAN" value="<%=OwConstants.OW_APPLY_STAGE_PLAN%>"/>
<c:set var="OW_APPLY_STAGE_DRAW" value="<%=OwConstants.OW_APPLY_STAGE_DRAW%>"/>
<c:set var="OW_APPLY_STAGE_GROW" value="<%=OwConstants.OW_APPLY_STAGE_GROW%>"/>

<c:set var="MEMBER_STAY_TYPE_MAP" value="<%=MemberConstants.MEMBER_STAY_TYPE_MAP%>"/>
<c:set var="MEMBER_STAY_TYPE_ABROAD" value="<%=MemberConstants.MEMBER_STAY_TYPE_ABROAD%>"/>
<c:set var="MEMBER_STAY_TYPE_INTERNAL" value="<%=MemberConstants.MEMBER_STAY_TYPE_INTERNAL%>"/>

<c:set var="MEMBER_TYPE_TEACHER" value="<%=MemberConstants.MEMBER_TYPE_TEACHER%>"/>
<c:set var="MEMBER_TYPE_STUDENT" value="<%=MemberConstants.MEMBER_TYPE_STUDENT%>"/>

<c:set value="${_pMap['ignore_plan_and_draw']=='true'}" var="_ignore_plan_and_draw"/>

<c:set value="<%=CrpConstants.CRP_RECORD_TYPE_OUT%>" var="CRP_RECORD_TYPE_OUT"/>
<c:set value="<%=CrpConstants.CRP_RECORD_TYPE_IN%>" var="CRP_RECORD_TYPE_IN"/>
<c:set value="<%=CrpConstants.CRP_RECORD_TYPE_TRANSFER%>" var="CRP_RECORD_TYPE_TRANSFER"/>
<c:set value="${_pMap['birthToDay']=='true'?'Y.m.d':'Y.m'}" var="_p_birthToDayFormat"/>
<c:set value="${_pMap['ignore_plan_and_draw']}" var="_p_ignore_plan_and_draw"/>

