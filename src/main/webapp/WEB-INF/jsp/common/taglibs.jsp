<%@ page import="sys.constants.SystemConstants" %>
<%@ page import="shiro.Constants" %>
<%@ page import="java.util.Date" %>
<%@ page import="sys.utils.DateUtils" %>
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

<c:set value="<%=new Date()%>" var="now"/>
<c:set value="<%=DateUtils.getDateBeforeOrAfterYears(new Date(), -1)%>" var="yearAgo"/>
<c:set value="${cm:formatDate(now,'yyyy-MM-dd')}" var="today"/>
<c:set value="${cm:formatDate(yearAgo,'yyyy-MM-dd')}" var="yearAgo"/>

<fmt:setBundle basename="spring" var="spring"/>

<fmt:message key="login.useCaptcha" bundle="${spring}" var="useCaptcha"/>
<fmt:message key="upload.path" bundle="${spring}" var="_uploadPath"/>
<fmt:message key="upload.maxSize" bundle="${spring}" var="_uploadMaxSize"/>
<fmt:message key="site.school" bundle="${spring}" var="_school"/>

<c:set var="CURRENT_USER" value="<%=Constants.CURRENT_USER%>"/>
<c:set var="_user" value="${requestScope[CURRENT_USER]}"/>

<c:set var="ROLE_ADMIN" value="<%=SystemConstants.ROLE_ADMIN%>"/>
<c:set var="ROLE_ODADMIN" value="<%=SystemConstants.ROLE_ODADMIN%>"/>
<c:set var="ROLE_PARTYADMIN" value="<%=SystemConstants.ROLE_PARTYADMIN%>"/>
<c:set var="ROLE_BRANCHADMIN" value="<%=SystemConstants.ROLE_BRANCHADMIN%>"/>
<c:set var="ROLE_CADREADMIN" value="<%=SystemConstants.ROLE_CADREADMIN%>"/>
<c:set var="ROLE_CADRE" value="<%=SystemConstants.ROLE_CADRE%>"/>
<c:set var="ROLE_CADRERESERVE" value="<%=SystemConstants.ROLE_CADRERESERVE%>"/>


<c:set var="PERMISSION_CADREADMIN" value="<%=SystemConstants.PERMISSION_CADREADMIN%>"/>
<c:set var="PERMISSION_CADREADMINSELF" value="<%=SystemConstants.PERMISSION_CADREADMINSELF%>"/>

<c:set var="ATTACH_FILE_TYPE_MAP" value="<%=SystemConstants.ATTACH_FILE_TYPE_MAP%>"/>

<c:set var="CONTENT_TPL_TYPE_MAP" value="<%=SystemConstants.CONTENT_TPL_TYPE_MAP%>"/>
<c:set var="CONTENT_TPL_CONTENT_TYPE_STRING" value="<%=SystemConstants.CONTENT_TPL_CONTENT_TYPE_STRING%>"/>
<c:set var="CONTENT_TPL_CONTENT_TYPE_HTML" value="<%=SystemConstants.CONTENT_TPL_CONTENT_TYPE_HTML%>"/>
<c:set var="CONTENT_TPL_CONTENT_TYPE_MAP" value="<%=SystemConstants.CONTENT_TPL_CONTENT_TYPE_MAP%>"/>
<c:set var="CONTENT_TPL_ENGINE_MAP" value="<%=SystemConstants.CONTENT_TPL_ENGINE_MAP%>"/>


<c:set var="USER_REG_STATUS_MAP" value="<%=SystemConstants.USER_REG_STATUS_MAP%>"/>
<c:set var="USER_REG_STATUS_APPLY" value="<%=SystemConstants.USER_REG_STATUS_APPLY%>"/>
<c:set var="USER_REG_STATUS_DENY" value="<%=SystemConstants.USER_REG_STATUS_DENY%>"/>
<c:set var="USER_REG_STATUS_PASS" value="<%=SystemConstants.USER_REG_STATUS_PASS%>"/>

<c:set var="USER_SOURCE_MAP" value="<%=SystemConstants.USER_SOURCE_MAP%>"/>
<c:set var="USER_SOURCE_JZG" value="<%=SystemConstants.USER_SOURCE_JZG%>"/>
<c:set var="USER_SOURCE_BKS" value="<%=SystemConstants.USER_SOURCE_BKS%>"/>
<c:set var="USER_SOURCE_YJS" value="<%=SystemConstants.USER_SOURCE_YJS%>"/>
<c:set var="USER_SOURCE_ADMIN" value="<%=SystemConstants.USER_SOURCE_ADMIN%>"/>
<c:set var="USER_SOURCE_REG" value="<%=SystemConstants.USER_SOURCE_REG%>"/>

<c:set var="SYNC_TYPE_MAP" value="<%=SystemConstants.SYNC_TYPE_MAP%>"/>
<c:set var="SYNC_TYPE_JZG" value="<%=SystemConstants.SYNC_TYPE_JZG%>"/>
<c:set var="SYNC_TYPE_BKS" value="<%=SystemConstants.SYNC_TYPE_BKS%>"/>
<c:set var="SYNC_TYPE_YJS" value="<%=SystemConstants.SYNC_TYPE_YJS%>"/>
<c:set var="SYNC_TYPE_ABROAD" value="<%=SystemConstants.SYNC_TYPE_ABROAD%>"/>

<c:set var="LOGIN_TYPE_MAP" value="<%=SystemConstants.LOGIN_TYPE_MAP%>"/>

<c:set var="UNIT_TYPE_ATTR_XY" value="<%=SystemConstants.UNIT_TYPE_ATTR_XY%>"/>
<c:set var="UNIT_TYPE_ATTR_JG" value="<%=SystemConstants.UNIT_TYPE_ATTR_JG%>"/>
<c:set var="UNIT_TYPE_ATTR_FS" value="<%=SystemConstants.UNIT_TYPE_ATTR_FS%>"/>
<c:set var="UNIT_TYPE_ATTR_MAP" value="<%=SystemConstants.UNIT_TYPE_ATTR_MAP%>"/>

<c:set var="CADRE_STAT_HISTORY_TYPE_MAP" value="<%=SystemConstants.CADRE_STAT_HISTORY_TYPE_MAP%>"/>

<%@ include file="taglibs-html-fragment.jsp" %>

<c:set var="USER_TYPE_MAP" value="<%=SystemConstants.USER_TYPE_MAP%>"/>
<c:set var="USER_TYPE_JZG" value="<%=SystemConstants.USER_TYPE_JZG%>"/>
<c:set var="GENDER_MAP" value="<%=SystemConstants.GENDER_MAP%>"/>
<c:set var="GENDER_MALE" value="<%=SystemConstants.GENDER_MALE%>"/>
<c:set var="GENDER_FEMALE" value="<%=SystemConstants.GENDER_FEMALE%>"/>

<c:set value="<%=SystemConstants.CADRE_RESERVE_TYPE_SCHOOL%>" var="CADRE_RESERVE_TYPE_SCHOOL"/>
<c:set value="<%=SystemConstants.CADRE_RESERVE_TYPE_MAP%>" var="CADRE_RESERVE_TYPE_MAP"/>

<c:set value="<%=SystemConstants.CADRE_RESERVE_STATUS_NORMAL%>" var="CADRE_RESERVE_STATUS_NORMAL"/>
<c:set value="<%=SystemConstants.CADRE_RESERVE_STATUS_ABOLISH%>" var="CADRE_RESERVE_STATUS_ABOLISH"/>
<c:set value="<%=SystemConstants.CADRE_RESERVE_STATUS_TO_INSPECT%>" var="CADRE_RESERVE_STATUS_TO_INSPECT"/>
<c:set value="<%=SystemConstants.CADRE_RESERVE_STATUS_ASSIGN%>" var="CADRE_RESERVE_STATUS_ASSIGN"/>
<c:set value="<%=SystemConstants.CADRE_RESERVE_STATUS_MAP%>" var="CADRE_RESERVE_STATUS_MAP"/>

<c:set value="<%=SystemConstants.CADRE_INSPECT_STATUS_NORMAL%>" var="CADRE_INSPECT_STATUS_NORMAL"/>
<c:set value="<%=SystemConstants.CADRE_INSPECT_STATUS_ASSIGN%>" var="CADRE_INSPECT_STATUS_ASSIGN"/>
<c:set value="<%=SystemConstants.CADRE_INSPECT_STATUS_ABOLISH%>" var="CADRE_INSPECT_STATUS_ABOLISH"/>
<c:set value="<%=SystemConstants.CADRE_INSPECT_STATUS_MAP%>" var="CADRE_INSPECT_STATUS_MAP"/>

<c:set value="<%=SystemConstants.CADRE_STATUS_MIDDLE%>" var="CADRE_STATUS_MIDDLE"/>
<c:set value="<%=SystemConstants.CADRE_STATUS_LEADER%>" var="CADRE_STATUS_LEADER"/>
<c:set value="<%=SystemConstants.CADRE_STATUS_INSPECT%>" var="CADRE_STATUS_INSPECT"/>
<c:set value="<%=SystemConstants.CADRE_STATUS_MIDDLE_LEAVE%>" var="CADRE_STATUS_MIDDLE_LEAVE"/>
<c:set value="<%=SystemConstants.CADRE_STATUS_LEADER_LEAVE%>" var="CADRE_STATUS_LEADER_LEAVE"/>
<c:set value="<%=SystemConstants.CADRE_STATUS_MAP%>" var="CADRE_STATUS_MAP"/>

<c:set value="<%=SystemConstants.CADRE_INFO_TYPE_WORK%>" var="CADRE_INFO_TYPE_WORK"/>
<c:set value="<%=SystemConstants.CADRE_INFO_TYPE_PARTTIME%>" var="CADRE_INFO_TYPE_PARTTIME"/>
<c:set value="<%=SystemConstants.CADRE_INFO_TYPE_TRAIN%>" var="CADRE_INFO_TYPE_TRAIN"/>
<c:set value="<%=SystemConstants.CADRE_INFO_TYPE_TEACH%>" var="CADRE_INFO_TYPE_TEACH"/>
<c:set value="<%=SystemConstants.CADRE_INFO_TYPE_RESEARCH%>" var="CADRE_INFO_TYPE_RESEARCH"/>
<c:set value="<%=SystemConstants.CADRE_INFO_TYPE_REWARD_OTHER%>" var="CADRE_INFO_TYPE_REWARD_OTHER"/>
<c:set value="<%=SystemConstants.CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY%>" var="CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY"/>
<c:set value="<%=SystemConstants.CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY%>" var="CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY"/>
<c:set value="<%=SystemConstants.CADRE_INFO_TYPE_BOOK_SUMMARY%>" var="CADRE_INFO_TYPE_BOOK_SUMMARY"/>
<c:set value="<%=SystemConstants.CADRE_INFO_TYPE_PAPER_SUMMARY%>" var="CADRE_INFO_TYPE_PAPER_SUMMARY"/>
<c:set value="<%=SystemConstants.CADRE_INFO_TYPE_RESEARCH_REWARD%>" var="CADRE_INFO_TYPE_RESEARCH_REWARD"/>

<c:set value="<%=SystemConstants.CADRE_INFO_TYPE_EDU%>" var="CADRE_INFO_TYPE_EDU"/>
<c:set value="<%=SystemConstants.CADRE_INFO_TYPE_MAP%>" var="CADRE_INFO_TYPE_MAP"/>

<c:set var="CADRE_REWARD_TYPE_OTHER" value="<%=SystemConstants.CADRE_REWARD_TYPE_OTHER%>"/>
<c:set var="CADRE_REWARD_TYPE_RESEARCH" value="<%=SystemConstants.CADRE_REWARD_TYPE_RESEARCH%>"/>
<c:set var="CADRE_REWARD_TYPE_TEACH" value="<%=SystemConstants.CADRE_REWARD_TYPE_TEACH%>"/>

<c:set var="CADRE_COURSE_TYPE_MAP" value="<%=SystemConstants.CADRE_COURSE_TYPE_MAP%>"/>
<c:set var="CADRE_BOOK_TYPE_MAP" value="<%=SystemConstants.CADRE_BOOK_TYPE_MAP%>"/>

<c:set var="CADRE_FAMLIY_TITLE_MAP" value="<%=SystemConstants.CADRE_FAMLIY_TITLE_MAP%>"/>

<c:set var="CADRE_RESEARCH_TYPE_DIRECT" value="<%=SystemConstants.CADRE_RESEARCH_TYPE_DIRECT%>"/>
<c:set var="CADRE_RESEARCH_TYPE_IN" value="<%=SystemConstants.CADRE_RESEARCH_TYPE_IN%>"/>

<c:set value="<%=SystemConstants.CADRE_SCHOOL_TYPE_THIS_SCHOOL%>" var="CADRE_SCHOOL_TYPE_THIS_SCHOOL"/>
<c:set value="<%=SystemConstants.CADRE_SCHOOL_TYPE_DOMESTIC%>" var="CADRE_SCHOOL_TYPE_DOMESTIC"/>
<c:set value="<%=SystemConstants.CADRE_SCHOOL_TYPE_ABROAD%>" var="CADRE_SCHOOL_TYPE_ABROAD"/>
<c:set value="<%=SystemConstants.CADRE_SCHOOL_TYPE_MAP%>" var="CADRE_SCHOOL_TYPE_MAP"/>

<c:set value="<%=SystemConstants.CADRE_COMPANY_TYPE_OTHER%>" var="CADRE_COMPANY_TYPE_OTHER"/>
<c:set value="<%=SystemConstants.CADRE_COMPANY_TYPE_MAP%>" var="CADRE_COMPANY_TYPE_MAP"/>

<c:set value="<%=SystemConstants.CADRE_TUTOR_TYPE_MAP%>" var="CADRE_TUTOR_TYPE_MAP"/>

<c:set value="<%=SystemConstants.CIS_INSPECTOR_STATUS_NOW%>" var="CIS_INSPECTOR_STATUS_NOW"/>
<c:set value="<%=SystemConstants.CIS_INSPECTOR_STATUS_HISTORY%>" var="CIS_INSPECTOR_STATUS_HISTORY"/>
<c:set value="<%=SystemConstants.CIS_INSPECTOR_STATUS_DELETE%>" var="CIS_INSPECTOR_STATUS_DELETE"/>

<c:set value="<%=SystemConstants.CIS_INSPECTOR_TYPE_OTHER%>" var="CIS_INSPECTOR_TYPE_OTHER"/>
<c:set value="<%=SystemConstants.CIS_INSPECTOR_TYPE_MAP%>" var="CIS_INSPECTOR_TYPE_MAP"/>

<c:set value="<%=SystemConstants.CIS_EVALUATE_TYPE_MAP%>" var="CIS_EVALUATE_TYPE_MAP"/>


<c:set value="<%=SystemConstants.TRAIN_INSPECTOR_STATUS_MAP%>" var="TRAIN_INSPECTOR_STATUS_MAP"/>
<c:set value="<%=SystemConstants.TRAIN_INSPECTOR_STATUS_INIT%>" var="TRAIN_INSPECTOR_STATUS_INIT"/>
<c:set value="<%=SystemConstants.TRAIN_INSPECTOR_STATUS_ABOLISH%>" var="TRAIN_INSPECTOR_STATUS_ABOLISH"/>
<c:set value="<%=SystemConstants.TRAIN_INSPECTOR_STATUS_ALL_FINISH%>" var="TRAIN_INSPECTOR_STATUS_ALL_FINISH"/>
<c:set value="<%=SystemConstants.TRAIN_INSPECTOR_STATUS_PART_FINISH%>" var="TRAIN_INSPECTOR_STATUS_PART_FINISH"/>

<c:set value="<%=SystemConstants.TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_MAP%>" var="TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_MAP"/>
<c:set value="<%=SystemConstants.TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF%>" var="TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF"/>
<c:set value="<%=SystemConstants.TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_ADMN_RESET%>" var="TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_ADMN_RESET"/>

<c:set value="<%=SystemConstants.TRAIN_INSPECTOR_COURSE_STATUS_SAVE%>" var="TRAIN_INSPECTOR_COURSE_STATUS_SAVE"/>
<c:set value="<%=SystemConstants.TRAIN_INSPECTOR_COURSE_STATUS_FINISH%>" var="TRAIN_INSPECTOR_COURSE_STATUS_FINISH"/>
<c:set value="<%=SystemConstants.TRAIN_INSPECTOR_COURSE_STATUS_MAP%>" var="TRAIN_INSPECTOR_COURSE_STATUS_MAP"/>

<c:set value="<%=SystemConstants.ENTER_APPLY_TYPE_MAP%>" var="ENTER_APPLY_TYPE_MAP"/>
<c:set value="<%=SystemConstants.ENTER_APPLY_STATUS_MAP%>" var="ENTER_APPLY_STATUS_MAP"/>

<c:set var="MEMBER_QUIT_TYPE_MAP" value="<%=SystemConstants.MEMBER_QUIT_TYPE_MAP%>"/>

<c:set var="MEMBER_SOURCE_MAP" value="<%=SystemConstants.MEMBER_SOURCE_MAP%>"/>

<c:set var="MEMBER_STATUS_MAP" value="<%=SystemConstants.MEMBER_STATUS_MAP%>"/>
<c:set var="MEMBER_STATUS_QUIT" value="<%=SystemConstants.MEMBER_STATUS_QUIT%>"/>
<c:set var="MEMBER_STATUS_NORMAL" value="<%=SystemConstants.MEMBER_STATUS_NORMAL%>"/>
<c:set var="MEMBER_STATUS_TRANSFER" value="<%=SystemConstants.MEMBER_STATUS_TRANSFER%>"/>

<c:set var="MEMBER_TYPE_TEACHER" value="<%=SystemConstants.MEMBER_TYPE_TEACHER%>"/>
<c:set var="MEMBER_TYPE_STUDENT" value="<%=SystemConstants.MEMBER_TYPE_STUDENT%>"/>

<c:set var="MEMBER_AGE_MAP" value="<%=SystemConstants.MEMBER_AGE_MAP%>"/>

<c:set var="PIE_COLOR_MAP" value="<%=SystemConstants.PIE_COLOR_MAP%>"/>

<c:set var="MEMBER_POLITICAL_STATUS_MAP" value="<%=SystemConstants.MEMBER_POLITICAL_STATUS_MAP%>"/>
<c:set var="MEMBER_POLITICAL_STATUS_GROW" value="<%=SystemConstants.MEMBER_POLITICAL_STATUS_GROW%>"/>
<c:set var="MEMBER_POLITICAL_STATUS_POSITIVE" value="<%=SystemConstants.MEMBER_POLITICAL_STATUS_POSITIVE%>"/>

<c:set var="MEMBER_INOUT_TYPE_INSIDE" value="<%=SystemConstants.MEMBER_INOUT_TYPE_INSIDE%>"/>
<c:set var="MEMBER_INOUT_TYPE_OUTSIDE" value="<%=SystemConstants.MEMBER_INOUT_TYPE_OUTSIDE%>"/>
<c:set var="MEMBER_INOUT_TYPE_MAP" value="<%=SystemConstants.MEMBER_INOUT_TYPE_MAP%>"/>

<c:set var="MEMBER_STAY_STATUS_MAP" value="<%=SystemConstants.MEMBER_STAY_STATUS_MAP%>"/>
<c:set var="MEMBER_STAY_STATUS_SELF_BACK" value="<%=SystemConstants.MEMBER_STAY_STATUS_SELF_BACK%>"/>
<c:set var="MEMBER_STAY_STATUS_BACK" value="<%=SystemConstants.MEMBER_STAY_STATUS_BACK%>"/>
<c:set var="MEMBER_STAY_STATUS_APPLY" value="<%=SystemConstants.MEMBER_STAY_STATUS_APPLY%>"/>
<c:set var="MEMBER_STAY_STATUS_PARTY_VERIFY" value="<%=SystemConstants.MEMBER_STAY_STATUS_PARTY_VERIFY%>"/>
<c:set var="MEMBER_STAY_STATUS_OW_VERIFY" value="<%=SystemConstants.MEMBER_STAY_STATUS_OW_VERIFY%>"/>

<c:set var="GRADUATE_ABROAD_TYPE_MAP" value="<%=SystemConstants.GRADUATE_ABROAD_TYPE_MAP%>"/>
<c:set var="GRADUATE_ABROAD_STATUS_MAP" value="<%=SystemConstants.GRADUATE_ABROAD_STATUS_MAP%>"/>
<c:set var="GRADUATE_ABROAD_STATUS_SELF_BACK" value="<%=SystemConstants.GRADUATE_ABROAD_STATUS_SELF_BACK%>"/>
<c:set var="GRADUATE_ABROAD_STATUS_BACK" value="<%=SystemConstants.GRADUATE_ABROAD_STATUS_BACK%>"/>
<c:set var="GRADUATE_ABROAD_STATUS_APPLY" value="<%=SystemConstants.GRADUATE_ABROAD_STATUS_APPLY%>"/>
<c:set var="GRADUATE_ABROAD_STATUS_BRANCH_VERIFY" value="<%=SystemConstants.GRADUATE_ABROAD_STATUS_BRANCH_VERIFY%>"/>
<c:set var="GRADUATE_ABROAD_STATUS_PARTY_VERIFY" value="<%=SystemConstants.GRADUATE_ABROAD_STATUS_PARTY_VERIFY%>"/>
<c:set var="GRADUATE_ABROAD_STATUS_OW_VERIFY" value="<%=SystemConstants.GRADUATE_ABROAD_STATUS_OW_VERIFY%>"/>

<c:set var="MEMBER_IN_STATUS_MAP" value="<%=SystemConstants.MEMBER_IN_STATUS_MAP%>"/>
<c:set var="MEMBER_IN_STATUS_SELF_BACK" value="<%=SystemConstants.MEMBER_IN_STATUS_SELF_BACK%>"/>
<c:set var="MEMBER_IN_STATUS_BACK" value="<%=SystemConstants.MEMBER_IN_STATUS_BACK%>"/>
<c:set var="MEMBER_IN_STATUS_APPLY" value="<%=SystemConstants.MEMBER_IN_STATUS_APPLY%>"/>
<c:set var="MEMBER_IN_STATUS_PARTY_VERIFY" value="<%=SystemConstants.MEMBER_IN_STATUS_PARTY_VERIFY%>"/>
<c:set var="MEMBER_IN_STATUS_OW_VERIFY" value="<%=SystemConstants.MEMBER_IN_STATUS_OW_VERIFY%>"/>

<c:set var="MEMBER_OUT_STATUS_MAP" value="<%=SystemConstants.MEMBER_OUT_STATUS_MAP%>"/>
<c:set var="MEMBER_OUT_STATUS_ABOLISH" value="<%=SystemConstants.MEMBER_OUT_STATUS_ABOLISH%>"/>
<c:set var="MEMBER_OUT_STATUS_SELF_BACK" value="<%=SystemConstants.MEMBER_OUT_STATUS_SELF_BACK%>"/>
<c:set var="MEMBER_OUT_STATUS_BACK" value="<%=SystemConstants.MEMBER_OUT_STATUS_BACK%>"/>
<c:set var="MEMBER_OUT_STATUS_APPLY" value="<%=SystemConstants.MEMBER_OUT_STATUS_APPLY%>"/>
<c:set var="MEMBER_OUT_STATUS_PARTY_VERIFY" value="<%=SystemConstants.MEMBER_OUT_STATUS_PARTY_VERIFY%>"/>
<c:set var="MEMBER_OUT_STATUS_OW_VERIFY" value="<%=SystemConstants.MEMBER_OUT_STATUS_OW_VERIFY%>"/>

<c:set var="MEMBER_TRANSFER_STATUS_MAP" value="<%=SystemConstants.MEMBER_TRANSFER_STATUS_MAP%>"/>
<c:set var="MEMBER_TRANSFER_STATUS_SELF_BACK" value="<%=SystemConstants.MEMBER_TRANSFER_STATUS_SELF_BACK%>"/>
<c:set var="MEMBER_TRANSFER_STATUS_BACK" value="<%=SystemConstants.MEMBER_TRANSFER_STATUS_BACK%>"/>
<c:set var="MEMBER_TRANSFER_STATUS_APPLY" value="<%=SystemConstants.MEMBER_TRANSFER_STATUS_APPLY%>"/>
<c:set var="MEMBER_TRANSFER_STATUS_FROM_VERIFY" value="<%=SystemConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY%>"/>
<c:set var="MEMBER_TRANSFER_STATUS_TO_VERIFY" value="<%=SystemConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY%>"/>

<c:set var="MEMBER_RETURN_STATUS_MAP" value="<%=SystemConstants.MEMBER_RETURN_STATUS_MAP%>"/>
<c:set var="MEMBER_RETURN_STATUS_DENY" value="<%=SystemConstants.MEMBER_RETURN_STATUS_DENY%>"/>
<c:set var="MEMBER_RETURN_STATUS_APPLY" value="<%=SystemConstants.MEMBER_RETURN_STATUS_APPLY%>"/>
<c:set var="MEMBER_RETURN_STATUS_BRANCH_VERIFY" value="<%=SystemConstants.MEMBER_RETURN_STATUS_BRANCH_VERIFY%>"/>
<c:set var="MEMBER_RETURN_STATUS_PARTY_VERIFY" value="<%=SystemConstants.MEMBER_RETURN_STATUS_PARTY_VERIFY%>"/>

<c:set var="MEMBER_TYPE_MAP" value="<%=SystemConstants.MEMBER_TYPE_MAP%>"/>

<c:set var="MEMBER_INFLOW_STATUS_MAP" value="<%=SystemConstants.MEMBER_INFLOW_STATUS_MAP%>"/>
<c:set var="MEMBER_INFLOW_STATUS_BACK" value="<%=SystemConstants.MEMBER_INFLOW_STATUS_BACK%>"/>
<c:set var="MEMBER_INFLOW_STATUS_APPLY" value="<%=SystemConstants.MEMBER_INFLOW_STATUS_APPLY%>"/>
<c:set var="MEMBER_INFLOW_STATUS_BRANCH_VERIFY" value="<%=SystemConstants.MEMBER_INFLOW_STATUS_BRANCH_VERIFY%>"/>
<c:set var="MEMBER_INFLOW_STATUS_PARTY_VERIFY" value="<%=SystemConstants.MEMBER_INFLOW_STATUS_PARTY_VERIFY%>"/>

<c:set var="MEMBER_INFLOW_OUT_STATUS_MAP" value="<%=SystemConstants.MEMBER_INFLOW_OUT_STATUS_MAP%>"/>
<c:set var="MEMBER_INFLOW_OUT_STATUS_SELF_BACK" value="<%=SystemConstants.MEMBER_INFLOW_OUT_STATUS_SELF_BACK%>"/>
<c:set var="MEMBER_INFLOW_OUT_STATUS_BACK" value="<%=SystemConstants.MEMBER_INFLOW_OUT_STATUS_BACK%>"/>
<c:set var="MEMBER_INFLOW_OUT_STATUS_APPLY" value="<%=SystemConstants.MEMBER_INFLOW_OUT_STATUS_APPLY%>"/>
<c:set var="MEMBER_INFLOW_OUT_STATUS_BRANCH_VERIFY" value="<%=SystemConstants.MEMBER_INFLOW_OUT_STATUS_BRANCH_VERIFY%>"/>
<c:set var="MEMBER_INFLOW_OUT_STATUS_PARTY_VERIFY" value="<%=SystemConstants.MEMBER_INFLOW_OUT_STATUS_PARTY_VERIFY%>"/>

<c:set var="OR_STATUS_MAP" value="<%=SystemConstants.OR_STATUS_MAP%>"/>
<c:set var="MEMBER_OUTFLOW_STATUS_MAP" value="<%=SystemConstants.MEMBER_OUTFLOW_STATUS_MAP%>"/>
<c:set var="MEMBER_OUTFLOW_STATUS_BACK" value="<%=SystemConstants.MEMBER_OUTFLOW_STATUS_BACK%>"/>
<c:set var="MEMBER_OUTFLOW_STATUS_APPLY" value="<%=SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY%>"/>
<c:set var="MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY" value="<%=SystemConstants.MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY%>"/>
<c:set var="MEMBER_OUTFLOW_STATUS_PARTY_VERIFY" value="<%=SystemConstants.MEMBER_OUTFLOW_STATUS_PARTY_VERIFY%>"/>

<c:set var="MEMBER_QUIT_STATUS_MAP" value="<%=SystemConstants.MEMBER_QUIT_STATUS_MAP%>"/>
<c:set var="MEMBER_QUIT_STATUS_SELF_BACK" value="<%=SystemConstants.MEMBER_QUIT_STATUS_SELF_BACK%>"/>
<c:set var="MEMBER_QUIT_STATUS_BACK" value="<%=SystemConstants.MEMBER_QUIT_STATUS_BACK%>"/>
<c:set var="MEMBER_QUIT_STATUS_APPLY" value="<%=SystemConstants.MEMBER_QUIT_STATUS_APPLY%>"/>
<c:set var="MEMBER_QUIT_STATUS_BRANCH_VERIFY" value="<%=SystemConstants.MEMBER_QUIT_STATUS_BRANCH_VERIFY%>"/>
<c:set var="MEMBER_QUIT_STATUS_PARTY_VERIFY" value="<%=SystemConstants.MEMBER_QUIT_STATUS_PARTY_VERIFY%>"/>
<c:set var="MEMBER_QUIT_STATUS_OW_VERIFY" value="<%=SystemConstants.MEMBER_QUIT_STATUS_OW_VERIFY%>"/>

<c:set var="GRADUATE_ABROAD_STATUS_MAP" value="<%=SystemConstants.GRADUATE_ABROAD_STATUS_MAP%>"/>

<c:set var="APPLY_APPROVAL_LOG_TYPE_MAP" value="<%=SystemConstants.APPLY_APPROVAL_LOG_TYPE_MAP%>"/>
<c:set var="APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY" value="<%=SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY%>"/>
<c:set var="APPLY_APPROVAL_LOG_TYPE_MEMBER_ABROAD" value="<%=SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_ABROAD%>"/>
<c:set var="APPLY_APPROVAL_LOG_TYPE_MEMBER_IN" value="<%=SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_IN%>"/>
<c:set var="APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT" value="<%=SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT%>"/>
<c:set var="APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW" value="<%=SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW%>"/>
<c:set var="APPLY_APPROVAL_LOG_TYPE_MEMBER_OUTFLOW" value="<%=SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_OUTFLOW%>"/>
<c:set var="APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN" value="<%=SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN%>"/>
<c:set var="APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER" value="<%=SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER%>"/>
<c:set var="APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY" value="<%=SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY%>"/>
<c:set var="APPLY_APPROVAL_LOG_TYPE_USER_REG" value="<%=SystemConstants.APPLY_APPROVAL_LOG_TYPE_USER_REG%>"/>
<c:set var="APPLY_APPROVAL_LOG_TYPE_MEMBER_QUIT" value="<%=SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_QUIT%>"/>
<c:set var="APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW_OUT" value="<%=SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW_OUT%>"/>
<c:set var="APPLY_APPROVAL_LOG_TYPE_GRADUATE_ABROAD" value="<%=SystemConstants.APPLY_APPROVAL_LOG_TYPE_GRADUATE_ABROAD%>"/>

<c:set var="APPLY_APPROVAL_LOG_STATUS_DENY" value="<%=SystemConstants.APPLY_APPROVAL_LOG_STATUS_DENY%>"/>
<c:set var="APPLY_APPROVAL_LOG_STATUS_PASS" value="<%=SystemConstants.APPLY_APPROVAL_LOG_STATUS_PASS%>"/>
<c:set var="APPLY_APPROVAL_LOG_STATUS_BACK" value="<%=SystemConstants.APPLY_APPROVAL_LOG_STATUS_BACK%>"/>
<c:set var="APPLY_APPROVAL_LOG_STATUS_NONEED" value="<%=SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED%>"/>
<c:set var="APPLY_APPROVAL_LOG_STATUS_MAP" value="<%=SystemConstants.APPLY_APPROVAL_LOG_STATUS_MAP%>"/>

<c:set var="APPLYSELF_MODIFY_TYPE_ORIGINAL" value="<%=SystemConstants.APPLYSELF_MODIFY_TYPE_ORIGINAL%>"/>
<c:set var="APPLYSELF_MODIFY_TYPE_MODIFY" value="<%=SystemConstants.APPLYSELF_MODIFY_TYPE_MODIFY%>"/>

<c:set var="MODIFY_BASE_ITEM_TYPE_STRING" value="<%=SystemConstants.MODIFY_BASE_ITEM_TYPE_STRING%>"/>
<c:set var="MODIFY_BASE_ITEM_TYPE_INT" value="<%=SystemConstants.MODIFY_BASE_ITEM_TYPE_INT%>"/>
<c:set var="MODIFY_BASE_ITEM_TYPE_DATE" value="<%=SystemConstants.MODIFY_BASE_ITEM_TYPE_DATE%>"/>
<c:set var="MODIFY_BASE_ITEM_TYPE_IMAGE" value="<%=SystemConstants.MODIFY_BASE_ITEM_TYPE_IMAGE%>"/>
<c:set var="MODIFY_BASE_ITEM_TYPE_MAP" value="<%=SystemConstants.MODIFY_BASE_ITEM_TYPE_MAP%>"/>

<c:set var="MODIFY_BASE_APPLY_STATUS_APPLY" value="<%=SystemConstants.MODIFY_BASE_APPLY_STATUS_APPLY%>"/>
<c:set var="MODIFY_BASE_APPLY_STATUS_DELETE" value="<%=SystemConstants.MODIFY_BASE_APPLY_STATUS_DELETE%>"/>
<c:set var="MODIFY_BASE_APPLY_STATUS_ALL_CHECK" value="<%=SystemConstants.MODIFY_BASE_APPLY_STATUS_ALL_CHECK%>"/>

<c:set var="MODIFY_BASE_ITEM_STATUS_MAP" value="<%=SystemConstants.MODIFY_BASE_ITEM_STATUS_MAP%>"/>

<c:set var="MODIFY_TABLE_APPLY_STATUS_APPLY" value="<%=SystemConstants.MODIFY_TABLE_APPLY_STATUS_APPLY%>"/>
<c:set var="MODIFY_TABLE_APPLY_STATUS_MAP" value="<%=SystemConstants.MODIFY_TABLE_APPLY_STATUS_MAP%>"/>

<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_EDU" value="<%=SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_EDU%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_WORK" value="<%=SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_WORK%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK" value="<%=SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY" value="<%=SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE" value="<%=SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER" value="<%=SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME" value="<%=SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN" value="<%=SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_MAP" value="<%=SystemConstants.MODIFY_TABLE_APPLY_MODULE_MAP%>"/>

<c:set var="MODIFY_TABLE_APPLY_TYPE_MODIFY" value="<%=SystemConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY%>"/>
<c:set var="MODIFY_TABLE_APPLY_TYPE_DELETE" value="<%=SystemConstants.MODIFY_TABLE_APPLY_TYPE_DELETE%>"/>
<c:set var="MODIFY_TABLE_APPLY_TYPE_MAP" value="<%=SystemConstants.MODIFY_TABLE_APPLY_TYPE_MAP%>"/>

<c:set var="APPLY_TYPE_TEACHER" value="<%=SystemConstants.APPLY_TYPE_TEACHER%>"/>
<c:set var="APPLY_TYPE_STU" value="<%=SystemConstants.APPLY_TYPE_STU%>"/>
<c:set var="APPLY_TYPE_MAP" value="<%=SystemConstants.APPLY_TYPE_MAP%>"/>

<c:set var="APPLY_STAGE_MAP" value="<%=SystemConstants.APPLY_STAGE_MAP%>"/>
<c:set var="APPLY_STAGE_OUT" value="<%=SystemConstants.APPLY_STAGE_OUT%>"/>
<c:set var="APPLY_STAGE_DENY" value="<%=SystemConstants.APPLY_STAGE_DENY%>"/>
<c:set var="APPLY_STAGE_INIT" value="<%=SystemConstants.APPLY_STAGE_INIT%>"/>
<c:set var="APPLY_STAGE_PASS" value="<%=SystemConstants.APPLY_STAGE_PASS%>"/>
<c:set var="APPLY_STAGE_ACTIVE" value="<%=SystemConstants.APPLY_STAGE_ACTIVE%>"/>
<c:set var="APPLY_STAGE_CANDIDATE" value="<%=SystemConstants.APPLY_STAGE_CANDIDATE%>"/>
<c:set var="APPLY_STAGE_PLAN" value="<%=SystemConstants.APPLY_STAGE_PLAN%>"/>
<c:set var="APPLY_STAGE_DRAW" value="<%=SystemConstants.APPLY_STAGE_DRAW%>"/>
<c:set var="APPLY_STAGE_GROW" value="<%=SystemConstants.APPLY_STAGE_GROW%>"/>
<c:set var="APPLY_STAGE_POSITIVE" value="<%=SystemConstants.APPLY_STAGE_POSITIVE%>"/>

<c:set var="DISPATCH_CADRE_TYPE_MAP" value="<%=SystemConstants.DISPATCH_CADRE_TYPE_MAP%>"/>

<c:set var="APPLY_SELF_DATE_TYPE_MAP" value="<%=SystemConstants.APPLY_SELF_DATE_TYPE_MAP%>"/>
<c:set var="PASSPORT_DRAW_TYPE_MAP" value="<%=SystemConstants.PASSPORT_DRAW_TYPE_MAP%>"/>

<c:set var="PASSPORT_TYPE_KEEP" value="<%=SystemConstants.PASSPORT_TYPE_KEEP%>"/>
<c:set var="PASSPORT_TYPE_LOST" value="<%=SystemConstants.PASSPORT_TYPE_LOST%>"/>
<c:set var="PASSPORT_TYPE_CANCEL" value="<%=SystemConstants.PASSPORT_TYPE_CANCEL%>"/>
<c:set var="PASSPORT_TYPE_MAP" value="<%=SystemConstants.PASSPORT_TYPE_MAP%>"/>

<c:set var="PASSPORT_CANCEL_TYPE_ABOLISH" value="<%=SystemConstants.PASSPORT_CANCEL_TYPE_ABOLISH%>"/>
<c:set var="PASSPORT_CANCEL_TYPE_OTHER" value="<%=SystemConstants.PASSPORT_CANCEL_TYPE_OTHER%>"/>
<c:set var="PASSPORT_CANCEL_TYPE_MAP" value="<%=SystemConstants.PASSPORT_CANCEL_TYPE_MAP%>"/>

<c:set var="PASSPORT_LOST_TYPE_TRANSFER" value="<%=SystemConstants.PASSPORT_LOST_TYPE_TRANSFER%>"/>

<c:set var="PASSPORT_DRAW_USE_TYPE_MAP" value="<%=SystemConstants.PASSPORT_DRAW_USE_TYPE_MAP%>"/>

<c:set var="PASSPORT_DRAW_TYPE_SELF" value="<%=SystemConstants.PASSPORT_DRAW_TYPE_SELF%>"/>
<c:set var="PASSPORT_DRAW_TYPE_TW" value="<%=SystemConstants.PASSPORT_DRAW_TYPE_TW%>"/>
<c:set var="PASSPORT_DRAW_TYPE_OTHER" value="<%=SystemConstants.PASSPORT_DRAW_TYPE_OTHER%>"/>
<c:set var="PASSPORT_DRAW_TYPE_LONG_SELF" value="<%=SystemConstants.PASSPORT_DRAW_TYPE_LONG_SELF%>"/>

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
<c:set var="APPROVER_TYPE_MAP" value="<%=SystemConstants.APPROVER_TYPE_MAP%>"/>

