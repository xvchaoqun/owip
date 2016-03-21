<%@ page import="sys.constants.SystemConstants" %>
<%@ page import="sys.constants.DispatchConstants" %>
<%@ page import="shiro.Constants" %>
<%@ page trimDirectiveWhitespaces="true"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cm" uri="http://java.sun.com/jsp/jstl/custom" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="wo" uri="/wo-tags" %>
<%@ taglib  tagdir="/WEB-INF/tags" prefix="mytag"%>
<c:set var="_path" value="${fn:escapeXml(requestScope['javax.servlet.forward.servlet_path'])}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<c:set var="CURRENT_USER" value="<%=Constants.CURRENT_USER%>"/>
<c:set var="_user" value="${requestScope[CURRENT_USER]}"/>


<c:set var="USER_SOURCE_ADMIN" value="<%=SystemConstants.USER_SOURCE_ADMIN%>"/>

<c:set var="USER_TYPE_MAP" value="<%=SystemConstants.USER_TYPE_MAP%>"/>
<c:set var="USER_TYPE_JZG" value="<%=SystemConstants.USER_TYPE_JZG%>"/>
<c:set var="GENDER_MAP" value="<%=SystemConstants.GENDER_MAP%>"/>
<c:set var="GENDER_MALE" value="<%=SystemConstants.GENDER_MALE%>"/>
<c:set var="GENDER_FEMALE" value="<%=SystemConstants.GENDER_FEMALE%>"/>

<c:set value="<%=SystemConstants.CADRE_STATUS_NOW%>" var="CADRE_STATUS_NOW"/>
<c:set value="<%=SystemConstants.CADRE_STATUS_TEMP%>" var="CADRE_STATUS_TEMP"/>
<c:set value="<%=SystemConstants.CADRE_STATUS_LEAVE%>" var="CADRE_STATUS_LEAVE"/>
<c:set value="<%=SystemConstants.CADRE_STATUS_LEADER_LEAVE%>" var="CADRE_STATUS_LEADER_LEAVE"/>
<c:set value="<%=SystemConstants.CADRE_STATUS_MAP%>" var="CADRE_STATUS_MAP"/>

<c:set value="<%=SystemConstants.CADRE_SCHOOL_TYPE_THIS_SCHOOL%>" var="CADRE_SCHOOL_TYPE_THIS_SCHOOL"/>
<c:set value="<%=SystemConstants.CADRE_SCHOOL_TYPE_DOMESTIC%>" var="CADRE_SCHOOL_TYPE_DOMESTIC"/>
<c:set value="<%=SystemConstants.CADRE_SCHOOL_TYPE_ABROAD%>" var="CADRE_SCHOOL_TYPE_ABROAD"/>
<c:set value="<%=SystemConstants.CADRE_SCHOOL_TYPE_MAP%>" var="CADRE_SCHOOL_TYPE_MAP"/>

<c:set value="<%=SystemConstants.CADRE_TUTOR_TYPE_MAP%>" var="CADRE_TUTOR_TYPE_MAP"/>

<c:set var="RETIRE_QUIT_TYPE_MAP" value="<%=SystemConstants.RETIRE_QUIT_TYPE_MAP%>"/>
<c:set var="MEMBER_STATUS_QUIT" value="<%=SystemConstants.MEMBER_STATUS_QUIT%>"/>

<c:set var="MEMBER_STATUS_NORMAL" value="<%=SystemConstants.MEMBER_STATUS_NORMAL%>"/>

<c:set var="MEMBER_TYPE_TEACHER" value="<%=SystemConstants.MEMBER_TYPE_TEACHER%>"/>
<c:set var="MEMBER_TYPE_STUDENT" value="<%=SystemConstants.MEMBER_TYPE_STUDENT%>"/>

<c:set var="MEMBER_POLITICAL_STATUS_MAP" value="<%=SystemConstants.MEMBER_POLITICAL_STATUS_MAP%>"/>
<c:set var="MEMBER_STAY_STATUS_BACK" value="<%=SystemConstants.MEMBER_STAY_STATUS_BACK%>"/>

<c:set var="MEMBER_INOUT_TYPE_INSIDE" value="<%=SystemConstants.MEMBER_INOUT_TYPE_INSIDE%>"/>
<c:set var="MEMBER_INOUT_TYPE_OUTSIDE" value="<%=SystemConstants.MEMBER_INOUT_TYPE_OUTSIDE%>"/>
<c:set var="MEMBER_INOUT_TYPE_MAP" value="<%=SystemConstants.MEMBER_INOUT_TYPE_MAP%>"/>

<c:set var="APPLY_STAGE_DENY" value="<%=SystemConstants.APPLY_STAGE_DENY%>"/>
<c:set var="APPLY_STAGE_INIT" value="<%=SystemConstants.APPLY_STAGE_INIT%>"/>
<c:set var="APPLY_STAGE_PASS" value="<%=SystemConstants.APPLY_STAGE_PASS%>"/>
<c:set var="APPLY_STAGE_ACTIVE" value="<%=SystemConstants.APPLY_STAGE_ACTIVE%>"/>
<c:set var="APPLY_STAGE_CANDIDATE" value="<%=SystemConstants.APPLY_STAGE_CANDIDATE%>"/>
<c:set var="APPLY_STAGE_PLAN" value="<%=SystemConstants.APPLY_STAGE_PLAN%>"/>
<c:set var="APPLY_STAGE_DRAW" value="<%=SystemConstants.APPLY_STAGE_DRAW%>"/>
<c:set var="APPLY_STAGE_GROW" value="<%=SystemConstants.APPLY_STAGE_GROW%>"/>
<c:set var="APPLY_STAGE_POSITIVE" value="<%=SystemConstants.APPLY_STAGE_POSITIVE%>"/>

<c:set var="DISPATCH_CADRE_TYPE_MAP" value="<%=DispatchConstants.DISPATCH_CADRE_TYPE_MAP%>"/>

<c:set var="APPLY_SELF_DATE_TYPE_MAP" value="<%=SystemConstants.APPLY_SELF_DATE_TYPE_MAP%>"/>
<c:set var="PASSPORT_DRAW_TYPE_MAP" value="<%=SystemConstants.PASSPORT_DRAW_TYPE_MAP%>"/>

<c:set var="PASSPORT_TYPE_KEEP" value="<%=SystemConstants.PASSPORT_TYPE_KEEP%>"/>
<c:set var="PASSPORT_TYPE_LOST" value="<%=SystemConstants.PASSPORT_TYPE_LOST%>"/>
<c:set var="PASSPORT_TYPE_CANCEL" value="<%=SystemConstants.PASSPORT_TYPE_CANCEL%>"/>
<c:set var="PASSPORT_TYPE_MAP" value="<%=SystemConstants.PASSPORT_TYPE_MAP%>"/>
<c:set var="PASSPORT_CANCEL_TYPE_MAP" value="<%=SystemConstants.PASSPORT_CANCEL_TYPE_MAP%>"/>

<c:set var="PASSPORT_LOST_TYPE_TRANSFER" value="<%=SystemConstants.PASSPORT_LOST_TYPE_TRANSFER%>"/>


<c:set var="PASSPORT_DRAW_TYPE_SELF" value="<%=SystemConstants.PASSPORT_DRAW_TYPE_SELF%>"/>
<c:set var="PASSPORT_DRAW_TYPE_TW" value="<%=SystemConstants.PASSPORT_DRAW_TYPE_TW%>"/>
<c:set var="PASSPORT_DRAW_TYPE_OTHER" value="<%=SystemConstants.PASSPORT_DRAW_TYPE_OTHER%>"/>

<c:set var="PASSPORT_DRAW_STATUS_INIT" value="<%=SystemConstants.PASSPORT_DRAW_STATUS_INIT%>"/>
<c:set var="PASSPORT_DRAW_STATUS_PASS" value="<%=SystemConstants.PASSPORT_DRAW_STATUS_PASS%>"/>
<c:set var="PASSPORT_DRAW_STATUS_NOT_PASS" value="<%=SystemConstants.PASSPORT_DRAW_STATUS_NOT_PASS%>"/>
<c:set var="PASSPORT_DRAW_STATUS_MAP" value="<%=SystemConstants.PASSPORT_DRAW_STATUS_MAP%>"/>

<c:set var="PASSPORT_DRAW_DRAW_STATUS_UNDRAW" value="<%=SystemConstants.PASSPORT_DRAW_DRAW_STATUS_UNDRAW%>"/>
<c:set var="PASSPORT_DRAW_DRAW_STATUS_DRAW" value="<%=SystemConstants.PASSPORT_DRAW_DRAW_STATUS_DRAW%>"/>
<c:set var="PASSPORT_DRAW_DRAW_STATUS_RETURN" value="<%=SystemConstants.PASSPORT_DRAW_DRAW_STATUS_RETURN%>"/>
<c:set var="PASSPORT_DRAW_DRAW_STATUS_ABOLISH" value="<%=SystemConstants.PASSPORT_DRAW_DRAW_STATUS_ABOLISH%>"/>
<c:set var="PASSPORT_DRAW_DRAW_STATUS_MAP" value="<%=SystemConstants.PASSPORT_DRAW_DRAW_STATUS_MAP%>"/>

<c:set var="PASSPORT_APPLY_STATUS_INIT" value="<%=SystemConstants.PASSPORT_APPLY_STATUS_INIT%>"/>
<c:set var="PASSPORT_APPLY_STATUS_PASS" value="<%=SystemConstants.PASSPORT_APPLY_STATUS_PASS%>"/>
<c:set var="PASSPORT_APPLY_STATUS_NOT_PASS" value="<%=SystemConstants.PASSPORT_APPLY_STATUS_NOT_PASS%>"/>
<c:set var="PASSPORT_APPLY_STATUS_MAP" value="<%=SystemConstants.PASSPORT_APPLY_STATUS_MAP%>"/>

<c:set var="APPROVER_TYPE_UNIT" value="<%=SystemConstants.APPROVER_TYPE_UNIT%>"/>
<c:set var="APPROVER_TYPE_LEADER" value="<%=SystemConstants.APPROVER_TYPE_LEADER%>"/>
<c:set var="APPROVER_TYPE_OTHER" value="<%=SystemConstants.APPROVER_TYPE_OTHER%>"/>
<c:set var="APPROVER_TYPE_MAP" value="<%=SystemConstants.APPROVER_TYPE_MAP%>"/>

