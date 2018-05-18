<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="sys.shiro.Constants" %>
<%@ page import="java.util.Date" %>
<%@ page import="sys.constants.*" %>
<%@ page trimDirectiveWhitespaces="true"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cm" uri="http://java.sun.com/jsp/jstl/custom" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="wo" uri="/wo-tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="_path" value="${fn:escapeXml(requestScope['javax.servlet.forward.servlet_path'])}"/>
<c:set value="${cm:getSysConfig()}" var="_sysConfig"/>
<c:set value="${_sysConfig.siteName}" var="_plantform_name"/>
<c:set value="${_sysConfig.siteShortName}" var="_plantform_short_name"/>
<c:set value="<%=new Date()%>" var="now"/>
<%--<c:set value="<%=DateUtils.getDateBeforeOrAfterYears(new Date(), -1)%>" var="yearAgo"/>--%>
<c:set value="${cm:formatDate(now,'yyyy-MM-dd')}" var="_today"/>
<c:set value="${cm:formatDate(now,'yyyy')}" var="_thisYear"/>
<%--<c:set value="${cm:formatDate(yearAgo,'yyyy-MM-dd')}" var="yearAgo"/>--%>

<fmt:setBundle basename="spring" var="spring"/>
<fmt:message key="login.useCaptcha" bundle="${spring}" var="useCaptcha"/>
<fmt:message key="upload.path" bundle="${spring}" var="_uploadPath"/>
<fmt:message key="upload.maxSize" bundle="${spring}" var="_uploadMaxSize"/>

<c:set var="CURRENT_USER" value="<%=Constants.CURRENT_USER%>"/>
<c:set var="_user" value="${requestScope[CURRENT_USER]}"/>

<c:set var="ROLE_ADMIN" value="<%=RoleConstants.ROLE_ADMIN%>"/>
<c:set var="ROLE_ODADMIN" value="<%=RoleConstants.ROLE_ODADMIN%>"/>
<c:set var="ROLE_PARTYADMIN" value="<%=RoleConstants.ROLE_PARTYADMIN%>"/>
<c:set var="ROLE_BRANCHADMIN" value="<%=RoleConstants.ROLE_BRANCHADMIN%>"/>
<c:set var="ROLE_CADREADMIN" value="<%=RoleConstants.ROLE_CADREADMIN%>"/>
<c:set var="ROLE_CADRE" value="<%=RoleConstants.ROLE_CADRE%>"/>
<c:set var="ROLE_CADRERECRUIT" value="<%=RoleConstants.ROLE_CADRERECRUIT%>"/>
<c:set var="ROLE_CADREINSPECT" value="<%=RoleConstants.ROLE_CADREINSPECT%>"/>
<c:set var="ROLE_CADRERESERVE" value="<%=RoleConstants.ROLE_CADRERESERVE%>"/>
<c:set var="ROLE_ONLY_CADRE_VIEW" value="role_cadre_view"/>

<c:set var="ROLE_PMD_OW" value="<%=RoleConstants.ROLE_PMD_OW%>"/>

<c:set var="PERMISSION_CADREADMIN" value="<%=SystemConstants.PERMISSION_CADREADMIN%>"/>
<c:set var="PERMISSION_CADREADMINSELF" value="<%=SystemConstants.PERMISSION_CADREADMINSELF%>"/>

<c:set var="UNAVAILABLE" value="<%=SystemConstants.UNAVAILABLE%>"/>
<c:set var="AVAILABLE" value="<%=SystemConstants.AVAILABLE%>"/>

<c:set var="CACHE_KEY_MAP" value="<%=CacheConstants.CACHE_KEY_MAP%>"/>

<c:set var="CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_SELF" value="<%=CacheConstants.CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_SELF%>"/>
<c:set var="CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_TW" value="<%=CacheConstants.CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_TW%>"/>
<c:set var="CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF" value="<%=CacheConstants.CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF%>"/>
<c:set var="CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_OTHER" value="<%=CacheConstants.CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_OTHER%>"/>

<c:set var="ATTACH_FILE_TYPE_PDF" value="<%=SystemConstants.ATTACH_FILE_TYPE_PDF%>"/>
<c:set var="ATTACH_FILE_TYPE_MAP" value="<%=SystemConstants.ATTACH_FILE_TYPE_MAP%>"/>

<c:set var="CONTENT_TPL_TYPE_MAP" value="<%=ContentTplConstants.CONTENT_TPL_TYPE_MAP%>"/>
<c:set var="CONTENT_TPL_CONTENT_TYPE_STRING" value="<%=ContentTplConstants.CONTENT_TPL_CONTENT_TYPE_STRING%>"/>
<c:set var="CONTENT_TPL_CONTENT_TYPE_HTML" value="<%=ContentTplConstants.CONTENT_TPL_CONTENT_TYPE_HTML%>"/>
<c:set var="CONTENT_TPL_CONTENT_TYPE_MAP" value="<%=ContentTplConstants.CONTENT_TPL_CONTENT_TYPE_MAP%>"/>
<c:set var="CONTENT_TPL_ENGINE_MAP" value="<%=ContentTplConstants.CONTENT_TPL_ENGINE_MAP%>"/>

<c:set value="<%=ContentTplConstants.CONTENT_TPL_CRS_MSG_MAP%>" var="CONTENT_TPL_CRS_MSG_MAP"/>
<c:set value="<%=ContentTplConstants.CONTENT_TPL_CET_MSG_MAP%>" var="CONTENT_TPL_CET_MSG_MAP"/>


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
<c:set var="SYNC_TYPE_RETIRE_SALARY" value="<%=SystemConstants.SYNC_TYPE_RETIRE_SALARY%>"/>
<c:set var="SYNC_TYPE_JZG_SALARY" value="<%=SystemConstants.SYNC_TYPE_JZG_SALARY%>"/>

<c:set var="RESOURCE_TYPE_MENU" value="<%=SystemConstants.RESOURCE_TYPE_MENU%>"/>
<c:set var="RESOURCE_TYPE_URL" value="<%=SystemConstants.RESOURCE_TYPE_URL%>"/>
<c:set var="RESOURCE_TYPE_FUNCTION" value="<%=SystemConstants.RESOURCE_TYPE_FUNCTION%>"/>

<c:set var="LOGIN_TYPE_MAP" value="<%=SystemConstants.LOGIN_TYPE_MAP%>"/>

<c:set var="UNIT_STATUS_RUN" value="<%=SystemConstants.UNIT_STATUS_RUN%>"/>
<c:set var="UNIT_STATUS_HISTORY" value="<%=SystemConstants.UNIT_STATUS_HISTORY%>"/>

<c:set var="UNIT_TYPE_ATTR_XY" value="<%=SystemConstants.UNIT_TYPE_ATTR_XY%>"/>
<c:set var="UNIT_TYPE_ATTR_JG" value="<%=SystemConstants.UNIT_TYPE_ATTR_JG%>"/>
<c:set var="UNIT_TYPE_ATTR_FS" value="<%=SystemConstants.UNIT_TYPE_ATTR_FS%>"/>
<c:set var="UNIT_TYPE_ATTR_MAP" value="<%=SystemConstants.UNIT_TYPE_ATTR_MAP%>"/>

<c:set var="CADRE_STAT_HISTORY_TYPE_CADRE_MIDDLE" value="<%=CadreConstants.CADRE_STAT_HISTORY_TYPE_CADRE_MIDDLE%>"/>
<c:set var="CADRE_STAT_HISTORY_TYPE_STAT_CADRE" value="<%=CadreConstants.CADRE_STAT_HISTORY_TYPE_STAT_CADRE%>"/>
<c:set var="CADRE_STAT_HISTORY_TYPE_STAT_CPC" value="<%=CadreConstants.CADRE_STAT_HISTORY_TYPE_STAT_CPC%>"/>
<c:set var="CADRE_STAT_HISTORY_TYPE_STAT_CPC_STAT" value="<%=CadreConstants.CADRE_STAT_HISTORY_TYPE_STAT_CPC_STAT%>"/>
<c:set var="CADRE_STAT_HISTORY_TYPE_MAP" value="<%=CadreConstants.CADRE_STAT_HISTORY_TYPE_MAP%>"/>

<%@ include file="taglibs-html-fragment.jsp" %>

<c:set var="USER_TYPE_MAP" value="<%=SystemConstants.USER_TYPE_MAP%>"/>
<c:set var="USER_TYPE_JZG" value="<%=SystemConstants.USER_TYPE_JZG%>"/>
<c:set var="USER_TYPE_JZG" value="<%=SystemConstants.USER_TYPE_JZG%>"/>
<c:set var="GENDER_MAP" value="<%=SystemConstants.GENDER_MAP%>"/>
<c:set var="GENDER_MALE" value="<%=SystemConstants.GENDER_MALE%>"/>
<c:set var="GENDER_FEMALE" value="<%=SystemConstants.GENDER_FEMALE%>"/>

<c:set value="<%=CadreConstants.CADRE_RESERVE_TYPE_SCHOOL%>" var="CADRE_RESERVE_TYPE_SCHOOL"/>
<c:set value="<%=CadreConstants.CADRE_RESERVE_TYPE_MAP%>" var="CADRE_RESERVE_TYPE_MAP"/>

<c:set value="<%=CadreConstants.CADRE_RESERVE_STATUS_NORMAL%>" var="CADRE_RESERVE_STATUS_NORMAL"/>
<c:set value="<%=CadreConstants.CADRE_RESERVE_STATUS_ABOLISH%>" var="CADRE_RESERVE_STATUS_ABOLISH"/>
<c:set value="<%=CadreConstants.CADRE_RESERVE_STATUS_TO_INSPECT%>" var="CADRE_RESERVE_STATUS_TO_INSPECT"/>
<c:set value="<%=CadreConstants.CADRE_RESERVE_STATUS_ASSIGN%>" var="CADRE_RESERVE_STATUS_ASSIGN"/>
<c:set value="<%=CadreConstants.CADRE_RESERVE_STATUS_MAP%>" var="CADRE_RESERVE_STATUS_MAP"/>

<c:set value="<%=CadreConstants.CADRE_INSPECT_STATUS_NORMAL%>" var="CADRE_INSPECT_STATUS_NORMAL"/>
<c:set value="<%=CadreConstants.CADRE_INSPECT_STATUS_ASSIGN%>" var="CADRE_INSPECT_STATUS_ASSIGN"/>
<c:set value="<%=CadreConstants.CADRE_INSPECT_STATUS_ABOLISH%>" var="CADRE_INSPECT_STATUS_ABOLISH"/>
<c:set value="<%=CadreConstants.CADRE_INSPECT_STATUS_MAP%>" var="CADRE_INSPECT_STATUS_MAP"/>

<c:set value="<%=CadreConstants.CADRE_STATUS_MIDDLE%>" var="CADRE_STATUS_MIDDLE"/>
<c:set value="<%=CadreConstants.CADRE_STATUS_LEADER%>" var="CADRE_STATUS_LEADER"/>
<c:set value="<%=CadreConstants.CADRE_STATUS_INSPECT%>" var="CADRE_STATUS_INSPECT"/>
<c:set value="<%=CadreConstants.CADRE_STATUS_MIDDLE_LEAVE%>" var="CADRE_STATUS_MIDDLE_LEAVE"/>
<c:set value="<%=CadreConstants.CADRE_STATUS_LEADER_LEAVE%>" var="CADRE_STATUS_LEADER_LEAVE"/>
<c:set value="<%=CadreConstants.CADRE_STATUS_MAP%>" var="CADRE_STATUS_MAP"/>

<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_WORK%>" var="CADRE_INFO_TYPE_WORK"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_PARTTIME%>" var="CADRE_INFO_TYPE_PARTTIME"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_TRAIN%>" var="CADRE_INFO_TYPE_TRAIN"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_TEACH%>" var="CADRE_INFO_TYPE_TEACH"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_RESEARCH%>" var="CADRE_INFO_TYPE_RESEARCH"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_REWARD_OTHER%>" var="CADRE_INFO_TYPE_REWARD_OTHER"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY%>" var="CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY%>" var="CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_BOOK_SUMMARY%>" var="CADRE_INFO_TYPE_BOOK_SUMMARY"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_PAPER_SUMMARY%>" var="CADRE_INFO_TYPE_PAPER_SUMMARY"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_RESEARCH_REWARD%>" var="CADRE_INFO_TYPE_RESEARCH_REWARD"/>

<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_EDU%>" var="CADRE_INFO_TYPE_EDU"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_RM_WORK%>" var="CADRE_INFO_TYPE_RM_WORK"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_MAP%>" var="CADRE_INFO_TYPE_MAP"/>

<c:set value="<%=CadreConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST%>" var="CADRE_INFO_CHECK_RESULT_NOT_EXIST"/>
<c:set value="<%=CadreConstants.CADRE_INFO_CHECK_RESULT_EXIST%>" var="CADRE_INFO_CHECK_RESULT_EXIST"/>
<c:set value="<%=CadreConstants.CADRE_INFO_CHECK_RESULT_MODIFY%>" var="CADRE_INFO_CHECK_RESULT_MODIFY"/>
<c:set value="<%=CadreConstants.CADRE_INFO_CHECK_RESULT_MAP%>" var="CADRE_INFO_CHECK_RESULT_MAP"/>

<c:set var="CADRE_REWARD_TYPE_OTHER" value="<%=CadreConstants.CADRE_REWARD_TYPE_OTHER%>"/>
<c:set var="CADRE_REWARD_TYPE_RESEARCH" value="<%=CadreConstants.CADRE_REWARD_TYPE_RESEARCH%>"/>
<c:set var="CADRE_REWARD_TYPE_TEACH" value="<%=CadreConstants.CADRE_REWARD_TYPE_TEACH%>"/>

<c:set var="CADRE_COURSE_TYPE_MAP" value="<%=CadreConstants.CADRE_COURSE_TYPE_MAP%>"/>
<c:set var="CADRE_BOOK_TYPE_MAP" value="<%=CadreConstants.CADRE_BOOK_TYPE_MAP%>"/>

<c:set var="CADRE_FAMILY_TITLE_MAP" value="<%=CadreConstants.CADRE_FAMILY_TITLE_MAP%>"/>

<c:set var="CADRE_RESEARCH_TYPE_DIRECT" value="<%=CadreConstants.CADRE_RESEARCH_TYPE_DIRECT%>"/>
<c:set var="CADRE_RESEARCH_TYPE_IN" value="<%=CadreConstants.CADRE_RESEARCH_TYPE_IN%>"/>

<c:set value="<%=CadreConstants.CADRE_SCHOOL_TYPE_THIS_SCHOOL%>" var="CADRE_SCHOOL_TYPE_THIS_SCHOOL"/>
<c:set value="<%=CadreConstants.CADRE_SCHOOL_TYPE_DOMESTIC%>" var="CADRE_SCHOOL_TYPE_DOMESTIC"/>
<c:set value="<%=CadreConstants.CADRE_SCHOOL_TYPE_ABROAD%>" var="CADRE_SCHOOL_TYPE_ABROAD"/>
<c:set value="<%=CadreConstants.CADRE_SCHOOL_TYPE_MAP%>" var="CADRE_SCHOOL_TYPE_MAP"/>

<c:set value="<%=CadreConstants.CADRE_COMPANY_TYPE_OTHER%>" var="CADRE_COMPANY_TYPE_OTHER"/>
<c:set value="<%=CadreConstants.CADRE_COMPANY_TYPE_MAP%>" var="CADRE_COMPANY_TYPE_MAP"/>

<c:set value="<%=CadreConstants.CADRE_TUTOR_TYPE_MAP%>" var="CADRE_TUTOR_TYPE_MAP"/>

<c:set value="<%=ScConstants.SC_MATTER_CHECK_ITEM_CONFIRM_TYPE_MAP%>" var="SC_MATTER_CHECK_ITEM_CONFIRM_TYPE_MAP"/>

<c:set value="<%=CetConstants.CET_PROJECT_TYPE_MAP%>" var="CET_PROJECT_TYPE_MAP"/>

<c:set value="<%=CetConstants.CET_DISCUSS_UNIT_TYPE_OW%>" var="CET_DISCUSS_UNIT_TYPE_OW"/>
<c:set value="<%=CetConstants.CET_DISCUSS_UNIT_TYPE_UNIT%>" var="CET_DISCUSS_UNIT_TYPE_UNIT"/>
<c:set value="<%=CetConstants.CET_DISCUSS_UNIT_TYPE_PARTY%>" var="CET_DISCUSS_UNIT_TYPE_PARTY"/>
<c:set value="<%=CetConstants.CET_DISCUSS_UNIT_TYPE_PARTY_SCHOOL%>" var="CET_DISCUSS_UNIT_TYPE_PARTY_SCHOOL"/>
<c:set value="<%=CetConstants.CET_DISCUSS_UNIT_TYPE_MAP%>" var="CET_DISCUSS_UNIT_TYPE_MAP"/>

<c:set value="<%=CetConstants.CET_PROJECT_PLAN_TYPE_OFFLINE%>" var="CET_PROJECT_PLAN_TYPE_OFFLINE"/>
<c:set value="<%=CetConstants.CET_PROJECT_PLAN_TYPE_ONLINE%>" var="CET_PROJECT_PLAN_TYPE_ONLINE"/>
<c:set value="<%=CetConstants.CET_PROJECT_PLAN_TYPE_SELF%>" var="CET_PROJECT_PLAN_TYPE_SELF"/>
<c:set value="<%=CetConstants.CET_PROJECT_PLAN_TYPE_SPECIAL%>" var="CET_PROJECT_PLAN_TYPE_SPECIAL"/>
<c:set value="<%=CetConstants.CET_PROJECT_PLAN_TYPE_PRACTICE%>" var="CET_PROJECT_PLAN_TYPE_PRACTICE"/>
<c:set value="<%=CetConstants.CET_PROJECT_PLAN_TYPE_MAP%>" var="CET_PROJECT_PLAN_TYPE_MAP"/>

<c:set value="<%=CetConstants.CET_COURSE_TYPE_OFFLINE%>" var="CET_COURSE_TYPE_OFFLINE"/>
<c:set value="<%=CetConstants.CET_COURSE_TYPE_ONLINE%>" var="CET_COURSE_TYPE_ONLINE"/>
<c:set value="<%=CetConstants.CET_COURSE_TYPE_SELF%>" var="CET_COURSE_TYPE_SELF"/>
<c:set value="<%=CetConstants.CET_COURSE_TYPE_PRACTICE%>" var="CET_COURSE_TYPE_PRACTICE"/>
<c:set value="<%=CetConstants.CET_COURSE_TYPE_SPECIAL%>" var="CET_COURSE_TYPE_SPECIAL"/>
<c:set value="<%=CetConstants.CET_COURSE_TYPE_MAP%>" var="CET_COURSE_TYPE_MAP"/>

<c:set value="<%=CetConstants.CET_PROJECT_STATUS_INIT%>" var="CET_PROJECT_STATUS_INIT"/>
<c:set value="<%=CetConstants.CET_PROJECT_STATUS_START%>" var="CET_PROJECT_STATUS_START"/>
<c:set value="<%=CetConstants.CET_PROJECT_STATUS_FINISH%>" var="CET_PROJECT_STATUS_FINISH"/>

<c:set value="<%=CetConstants.CET_PROJECT_PUB_STATUS_PUBLISHED%>" var="CET_PROJECT_PUB_STATUS_PUBLISHED"/>

<c:set value="<%=CetConstants.CET_TRAINEE_TYPE_TEMPLATE_MAP%>" var="CET_TRAINEE_TYPE_TEMPLATE_MAP"/>

<c:set value="<%=CetConstants.CET_TRAIN_ENROLL_STATUS_DEFAULT%>" var="CET_TRAIN_ENROLL_STATUS_DEFAULT"/>
<c:set value="<%=CetConstants.CET_TRAIN_ENROLL_STATUS_OPEN%>" var="CET_TRAIN_ENROLL_STATUS_OPEN"/>
<c:set value="<%=CetConstants.CET_TRAIN_ENROLL_STATUS_CLOSED%>" var="CET_TRAIN_ENROLL_STATUS_CLOSED"/>
<c:set value="<%=CetConstants.CET_TRAIN_ENROLL_STATUS_PAUSE%>" var="CET_TRAIN_ENROLL_STATUS_PAUSE"/>
<c:set value="<%=CetConstants.CET_TRAIN_ENROLL_STATUS_NOT_BEGIN%>" var="CET_TRAIN_ENROLL_STATUS_NOT_BEGIN"/>
<c:set value="<%=CetConstants.CET_TRAIN_ENROLL_STATUS_MAP%>" var="CET_TRAIN_ENROLL_STATUS_MAP"/>

<c:set value="<%=CetConstants.CET_TRAIN_PUB_STATUS_UNPUBLISHED%>" var="CET_TRAIN_PUB_STATUS_UNPUBLISHED"/>
<c:set value="<%=CetConstants.CET_TRAIN_PUB_STATUS_PUBLISHED%>" var="CET_TRAIN_PUB_STATUS_PUBLISHED"/>
<c:set value="<%=CetConstants.CET_TRAIN_PUB_STATUS_CANCEL%>" var="CET_TRAIN_PUB_STATUS_CANCEL"/>
<c:set value="<%=CetConstants.CET_TRAINEE_SIGN_TYPE_MANUAL%>" var="CET_TRAINEE_SIGN_TYPE_MANUAL"/>

<c:set value="<%=CetConstants.CET_TRAIN_COURSE_APPLY_STATUS_MAP%>" var="CET_TRAIN_COURSE_APPLY_STATUS_MAP"/>
<c:set value="<%=CetConstants.CET_TRAIN_COURSE_APPLY_STATUS_DEFAULT%>" var="CET_TRAIN_COURSE_APPLY_STATUS_DEFAULT"/>
<c:set value="<%=CetConstants.CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_APPLY%>" var="CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_APPLY"/>
<c:set value="<%=CetConstants.CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_QUIT%>" var="CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_QUIT"/>
<c:set value="<%=CetConstants.CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_ALL%>" var="CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_ALL"/>

<c:set value="<%=CetConstants.CET_TRAIN_INSPECTOR_STATUS_MAP%>" var="CET_TRAIN_INSPECTOR_STATUS_MAP"/>
<c:set value="<%=CetConstants.CET_TRAIN_INSPECTOR_STATUS_INIT%>" var="CET_TRAIN_INSPECTOR_STATUS_INIT"/>
<c:set value="<%=CetConstants.CET_TRAIN_INSPECTOR_STATUS_ABOLISH%>" var="CET_TRAIN_INSPECTOR_STATUS_ABOLISH"/>

<c:set value="<%=CetConstants.CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_MAP%>" var="CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_MAP"/>
<c:set value="<%=CetConstants.CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF%>" var="CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF"/>
<c:set value="<%=CetConstants.CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_ADMN_RESET%>" var="CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_ADMN_RESET"/>

<c:set value="<%=CetConstants.CET_TRAIN_INSPECTOR_COURSE_STATUS_SAVE%>" var="CET_TRAIN_INSPECTOR_COURSE_STATUS_SAVE"/>
<c:set value="<%=CetConstants.CET_TRAIN_INSPECTOR_COURSE_STATUS_FINISH%>" var="CET_TRAIN_INSPECTOR_COURSE_STATUS_FINISH"/>
<c:set value="<%=CetConstants.CET_TRAIN_INSPECTOR_COURSE_STATUS_MAP%>" var="CET_TRAIN_INSPECTOR_COURSE_STATUS_MAP"/>


<c:set value="<%=PmdConstants.PMD_MONTH_STATUS_INIT%>" var="PMD_MONTH_STATUS_INIT"/>
<c:set value="<%=PmdConstants.PMD_MONTH_STATUS_START%>" var="PMD_MONTH_STATUS_START"/>
<c:set value="<%=PmdConstants.PMD_MONTH_STATUS_END%>" var="PMD_MONTH_STATUS_END"/>
<c:set value="<%=PmdConstants.PMD_MONTH_STATUS_MAP%>" var="PMD_MONTH_STATUS_MAP"/>

<c:set value="<%=PmdConstants.PMD_ADMIN_TYPE_MAP%>" var="PMD_ADMIN_TYPE_MAP"/>

<c:set value="<%=PmdConstants.PMD_NORM_TYPE_PAY%>" var="PMD_NORM_TYPE_PAY"/>
<c:set value="<%=PmdConstants.PMD_NORM_TYPE_REDUCE%>" var="PMD_NORM_TYPE_REDUCE"/>
<c:set value="<%=PmdConstants.PMD_NORM_TYPE_MAP%>" var="PMD_NORM_TYPE_MAP"/>

<c:set value="<%=PmdConstants.PMD_NORM_SET_TYPE_FIXED%>" var="PMD_NORM_SET_TYPE_FIXED"/>
<c:set value="<%=PmdConstants.PMD_NORM_SET_TYPE_SET%>" var="PMD_NORM_SET_TYPE_SET"/>
<c:set value="<%=PmdConstants.PMD_NORM_SET_TYPE_FREE%>" var="PMD_NORM_SET_TYPE_FREE"/>
<c:set value="<%=PmdConstants.PMD_NORM_SET_TYPE_FORMULA%>" var="PMD_NORM_SET_TYPE_FORMULA"/>
<c:set value="<%=PmdConstants.PMD_NORM_SET_TYPE_MAP%>" var="PMD_NORM_SET_TYPE_MAP"/>

<c:set value="<%=PmdConstants.PMD_FORMULA_TYPE_MAP%>" var="PMD_FORMULA_TYPE_MAP"/>

<c:set value="<%=PmdConstants.PMD_NORM_STATUS_INIT%>" var="PMD_NORM_STATUS_INIT"/>
<c:set value="<%=PmdConstants.PMD_NORM_STATUS_USE%>" var="PMD_NORM_STATUS_USE"/>
<c:set value="<%=PmdConstants.PMD_NORM_STATUS_ABOLISH%>" var="PMD_NORM_STATUS_ABOLISH"/>

<c:set value="<%=PmdConstants.PMD_MEMBER_TYPE_ONJOB%>" var="PMD_MEMBER_TYPE_ONJOB"/>
<c:set value="<%=PmdConstants.PMD_MEMBER_TYPE_RETIRE%>" var="PMD_MEMBER_TYPE_RETIRE"/>
<c:set value="<%=PmdConstants.PMD_MEMBER_TYPE_STUDENT%>" var="PMD_MEMBER_TYPE_STUDENT"/>
<c:set value="<%=PmdConstants.PMD_MEMBER_TYPE_MAP%>" var="PMD_MEMBER_TYPE_MAP"/>

<c:set value="<%=PmdConstants.PMD_FORMULA_TYPE_ONJOB%>" var="PMD_FORMULA_TYPE_ONJOB"/>
<c:set value="<%=PmdConstants.PMD_FORMULA_TYPE_EXTERNAL%>" var="PMD_FORMULA_TYPE_EXTERNAL"/>
<c:set value="<%=PmdConstants.PMD_FORMULA_TYPE_RETIRE%>" var="PMD_FORMULA_TYPE_RETIRE"/>

<c:set value="<%=OaConstants.OA_TASK_TYPE_MAP%>" var="OA_TASK_TYPE_MAP"/>

<c:set value="<%=OaConstants.OA_TASK_USER_STATUS_INIT%>" var="OA_TASK_USER_STATUS_INIT"/>
<c:set value="<%=OaConstants.OA_TASK_USER_STATUS_PASS%>" var="OA_TASK_USER_STATUS_PASS"/>
<c:set value="<%=OaConstants.OA_TASK_USER_STATUS_DENY%>" var="OA_TASK_USER_STATUS_DENY"/>


<c:set value="<%=CisConstants.CIS_INSPECTOR_STATUS_NOW%>" var="CIS_INSPECTOR_STATUS_NOW"/>
<c:set value="<%=CisConstants.CIS_INSPECTOR_STATUS_HISTORY%>" var="CIS_INSPECTOR_STATUS_HISTORY"/>
<c:set value="<%=CisConstants.CIS_INSPECTOR_STATUS_DELETE%>" var="CIS_INSPECTOR_STATUS_DELETE"/>

<c:set value="<%=CisConstants.CIS_INSPECTOR_TYPE_OW%>" var="CIS_INSPECTOR_TYPE_OW"/>
<c:set value="<%=CisConstants.CIS_INSPECTOR_TYPE_OTHER%>" var="CIS_INSPECTOR_TYPE_OTHER"/>
<c:set value="<%=CisConstants.CIS_INSPECTOR_TYPE_MAP%>" var="CIS_INSPECTOR_TYPE_MAP"/>

<c:set value="<%=CisConstants.CIS_EVALUATE_TYPE_MAP%>" var="CIS_EVALUATE_TYPE_MAP"/>

<c:set value="<%=CrpConstants.CRP_RECORD_TYPE_OUT%>" var="CRP_RECORD_TYPE_OUT"/>
<c:set value="<%=CrpConstants.CRP_RECORD_TYPE_IN%>" var="CRP_RECORD_TYPE_IN"/>
<c:set value="<%=CrpConstants.CRP_RECORD_TYPE_TRANSFER%>" var="CRP_RECORD_TYPE_TRANSFER"/>
<c:set value="<%=CrpConstants.CRP_RECORD_TYPE_MAP%>" var="CRP_RECORD_TYPE_MAP"/>

<c:set value="<%=PcsConstants.PCS_ADMIN_TYPE_MAP%>" var="PCS_ADMIN_TYPE_MAP"/>
<c:set value="<%=PcsConstants.PCS_ADMIN_TYPE_NORMAL%>" var="PCS_ADMIN_TYPE_NORMAL"/>
<c:set value="<%=PcsConstants.PCS_ADMIN_TYPE_SECRETARY%>" var="PCS_ADMIN_TYPE_SECRETARY"/>
<c:set value="<%=PcsConstants.PCS_ADMIN_TYPE_VICE_SECRETARY%>" var="PCS_ADMIN_TYPE_VICE_SECRETARY"/>

<c:set value="<%=PcsConstants.PCS_USER_TYPE_DW%>" var="PCS_USER_TYPE_DW"/>
<c:set value="<%=PcsConstants.PCS_USER_TYPE_JW%>" var="PCS_USER_TYPE_JW"/>
<c:set value="<%=PcsConstants.PCS_USER_TYPE_MAP%>" var="PCS_USER_TYPE_MAP"/>

<c:set value="<%=PcsConstants.PCS_STAGE_FIRST%>" var="PCS_STAGE_FIRST"/>
<c:set value="<%=PcsConstants.PCS_STAGE_SECOND%>" var="PCS_STAGE_SECOND"/>
<c:set value="<%=PcsConstants.PCS_STAGE_THIRD%>" var="PCS_STAGE_THIRD"/>
<c:set value="<%=PcsConstants.PCS_STAGE_MAP%>" var="PCS_STAGE_MAP"/>

<c:set value="<%=PcsConstants.PCS_PR_TYPE_PRO%>" var="PCS_PR_TYPE_PRO"/>
<c:set value="<%=PcsConstants.PCS_PR_TYPE_STU%>" var="PCS_PR_TYPE_STU"/>
<c:set value="<%=PcsConstants.PCS_PR_TYPE_RETIRE%>" var="PCS_PR_TYPE_RETIRE"/>
<c:set value="<%=PcsConstants.PCS_PR_TYPE_MAP%>" var="PCS_PR_TYPE_MAP"/>

<c:set value="<%=PcsConstants.PCS_PR_USER_TYPE_CADRE%>" var="PCS_PR_USER_TYPE_CADRE"/>
<c:set value="<%=PcsConstants.PCS_PR_USER_TYPE_TEACHER%>" var="PCS_PR_USER_TYPE_TEACHER"/>
<c:set value="<%=PcsConstants.PCS_PR_USER_TYPE_STU%>" var="PCS_PR_USER_TYPE_STU"/>

<c:set value="<%=PcsConstants.PCS_PR_RECOMMEND_STATUS_INIT%>" var="PCS_PR_RECOMMEND_STATUS_INIT"/>
<c:set value="<%=PcsConstants.PCS_PR_RECOMMEND_STATUS_PASS%>" var="PCS_PR_RECOMMEND_STATUS_PASS"/>
<c:set value="<%=PcsConstants.PCS_PR_RECOMMEND_STATUS_DENY%>" var="PCS_PR_RECOMMEND_STATUS_DENY"/>
<c:set value="<%=PcsConstants.PCS_PR_RECOMMEND_STATUS_MAP%>" var="PCS_PR_RECOMMEND_STATUS_MAP"/>


<c:set value="<%=PcsConstants.PCS_PROPOSAL_STATUS_SAVE%>" var="PCS_PROPOSAL_STATUS_SAVE"/>
<c:set value="<%=PcsConstants.PCS_PROPOSAL_STATUS_INIT%>" var="PCS_PROPOSAL_STATUS_INIT"/>
<c:set value="<%=PcsConstants.PCS_PROPOSAL_STATUS_PASS%>" var="PCS_PROPOSAL_STATUS_PASS"/>
<c:set value="<%=PcsConstants.PCS_PROPOSAL_STATUS_MAP%>" var="PCS_PROPOSAL_STATUS_MAP"/>

<c:set value="<%=CrsConstants.CRS_EXPERT_STATUS_NOW%>" var="CRS_EXPERT_STATUS_NOW"/>
<c:set value="<%=CrsConstants.CRS_EXPERT_STATUS_HISTORY%>" var="CRS_EXPERT_STATUS_HISTORY"/>
<c:set value="<%=CrsConstants.CRS_EXPERT_STATUS_DELETE%>" var="CRS_EXPERT_STATUS_DELETE"/>

<c:set value="<%=CrsConstants.CRS_TEMPLATE_TYPE_BASE%>" var="CRS_TEMPLATE_TYPE_BASE"/>
<c:set value="<%=CrsConstants.CRS_TEMPLATE_TYPE_POST%>" var="CRS_TEMPLATE_TYPE_POST"/>
<c:set value="<%=CrsConstants.CRS_TEMPLATE_TYPE_MEETINGNOTICE%>" var="CRS_TEMPLATE_TYPE_MEETINGNOTICE"/>

<c:set value="<%=CrsConstants.CRS_POST_TYPE_MAP%>" var="CRS_POST_TYPE_MAP"/>

<c:set value="<%=CrsConstants.CRS_POST_RULE_TYPE_MAP%>" var="CRS_POST_RULE_TYPE_MAP"/>

<c:set value="<%=CrsConstants.CRS_APPLICANT_INFO_CHECK_STATUS_INIT%>" var="CRS_APPLICANT_INFO_CHECK_STATUS_INIT"/>
<c:set value="<%=CrsConstants.CRS_APPLICANT_INFO_CHECK_STATUS_PASS%>" var="CRS_APPLICANT_INFO_CHECK_STATUS_PASS"/>
<c:set value="<%=CrsConstants.CRS_APPLICANT_INFO_CHECK_STATUS_UNPASS%>" var="CRS_APPLICANT_INFO_CHECK_STATUS_UNPASS"/>
<c:set value="<%=CrsConstants.CRS_APPLICANT_INFO_CHECK_STATUS_MAP%>" var="CRS_APPLICANT_INFO_CHECK_STATUS_MAP"/>

<c:set value="<%=CrsConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_PASS%>" var="CRS_APPLICANT_REQUIRE_CHECK_STATUS_PASS"/>
<c:set value="<%=CrsConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_UNPASS%>" var="CRS_APPLICANT_REQUIRE_CHECK_STATUS_UNPASS"/>


<c:set value="<%=CrsConstants.CRS_POST_PUB_STATUS_PUBLISHED%>" var="CRS_POST_PUB_STATUS_PUBLISHED"/>

<c:set value="<%=CrsConstants.CRS_POST_ENROLL_STATUS_DEFAULT%>" var="CRS_POST_ENROLL_STATUS_DEFAULT"/>
<c:set value="<%=CrsConstants.CRS_POST_ENROLL_STATUS_OPEN%>" var="CRS_POST_ENROLL_STATUS_OPEN"/>
<c:set value="<%=CrsConstants.CRS_POST_ENROLL_STATUS_CLOSED%>" var="CRS_POST_ENROLL_STATUS_CLOSED"/>
<c:set value="<%=CrsConstants.CRS_POST_ENROLL_STATUS_PAUSE%>" var="CRS_POST_ENROLL_STATUS_PAUSE"/>
<c:set value="<%=CrsConstants.CRS_POST_ENROLL_STATUS_MAP%>" var="CRS_POST_ENROLL_STATUS_MAP"/>

<c:set value="<%=CrsConstants.CRS_POST_EXPERT_ROLE_HEAD%>" var="CRS_POST_EXPERT_ROLE_HEAD"/>
<c:set value="<%=CrsConstants.CRS_POST_EXPERT_ROLE_LEADER%>" var="CRS_POST_EXPERT_ROLE_LEADER"/>
<c:set value="<%=CrsConstants.CRS_POST_EXPERT_ROLE_MEMBER%>" var="CRS_POST_EXPERT_ROLE_MEMBER"/>
<c:set value="<%=CrsConstants.CRS_POST_EXPERT_ROLE_MAP%>" var="CRS_POST_EXPERT_ROLE_MAP"/>

<c:set value="<%=CrsConstants.CRS_POST_FILE_TYPE_PIC%>" var="CRS_POST_FILE_TYPE_PIC"/>
<c:set value="<%=CrsConstants.CRS_POST_FILE_TYPE_AUDIO%>" var="CRS_POST_FILE_TYPE_AUDIO"/>
<c:set value="<%=CrsConstants.CRS_POST_FILE_TYPE_MAP%>" var="CRS_POST_FILE_TYPE_MAP"/>

<c:set value="<%=CrsConstants.CRS_APPLICANT_STATUS_SAVE%>" var="CRS_APPLICANT_STATUS_SAVE"/>
<c:set value="<%=CrsConstants.CRS_APPLICANT_STATUS_SUBMIT%>" var="CRS_APPLICANT_STATUS_SUBMIT"/>
<c:set value="<%=CrsConstants.CRS_APPLICANT_STATUS_DELETE%>" var="CRS_APPLICANT_STATUS_DELETE"/>

<c:set value="<%=CrsConstants.CRS_POST_STATUS_MAP%>" var="CRS_POST_STATUS_MAP"/>
<c:set value="<%=CrsConstants.CRS_POST_STATUS_NORMAL%>" var="CRS_POST_STATUS_NORMAL"/>
<c:set value="<%=CrsConstants.CRS_POST_STATUS_FINISH%>" var="CRS_POST_STATUS_FINISH"/>
<c:set value="<%=CrsConstants.CRS_POST_STATUS_DELETE%>" var="CRS_POST_STATUS_DELETE"/>

<c:set value="<%=CrsConstants.CRS_TEMPLATE_TYPE_MAP%>" var="CRS_TEMPLATE_TYPE_MAP"/>

<c:set value="<%=CrsConstants.CRS_APPLY_USER_STATUS_OPEN%>" var="CRS_APPLY_USER_STATUS_OPEN"/>
<c:set value="<%=CrsConstants.CRS_APPLY_USER_STATUS_CLOSED%>" var="CRS_APPLY_USER_STATUS_CLOSED"/>
<c:set value="<%=CrsConstants.CRS_APPLY_USER_STATUS_AMP%>" var="CRS_APPLY_USER_STATUS_AMP"/>


<c:set value="<%=VerifyConstants.VERIFY_STATUS_DEL%>" var="VERIFY_STATUS_DEL"/>

<c:set value="<%=VerifyConstants.VERIFY_AGE_TYPE_MAP%>" var="VERIFY_AGE_TYPE_MAP"/>

<c:set value="<%=VerifyConstants.VERIFY_WORK_TIME_TYPE_MAP%>" var="VERIFY_WORK_TIME_TYPE_MAP"/>

<c:set value="<%=OwConstants.OW_ENTER_APPLY_TYPE_MAP%>" var="OW_ENTER_APPLY_TYPE_MAP"/>
<c:set value="<%=OwConstants.OW_ENTER_APPLY_STATUS_MAP%>" var="OW_ENTER_APPLY_STATUS_MAP"/>

<c:set var="MEMBER_QUIT_TYPE_MAP" value="<%=MemberConstants.MEMBER_QUIT_TYPE_MAP%>"/>

<c:set var="MEMBER_SOURCE_MAP" value="<%=MemberConstants.MEMBER_SOURCE_MAP%>"/>

<c:set var="MEMBER_STATUS_MAP" value="<%=MemberConstants.MEMBER_STATUS_MAP%>"/>
<c:set var="MEMBER_STATUS_QUIT" value="<%=MemberConstants.MEMBER_STATUS_QUIT%>"/>
<c:set var="MEMBER_STATUS_NORMAL" value="<%=MemberConstants.MEMBER_STATUS_NORMAL%>"/>
<c:set var="MEMBER_STATUS_TRANSFER" value="<%=MemberConstants.MEMBER_STATUS_TRANSFER%>"/>

<c:set var="MEMBER_TYPE_TEACHER" value="<%=MemberConstants.MEMBER_TYPE_TEACHER%>"/>
<c:set var="MEMBER_TYPE_STUDENT" value="<%=MemberConstants.MEMBER_TYPE_STUDENT%>"/>

<c:set var="MEMBER_AGE_MAP" value="<%=MemberConstants.MEMBER_AGE_MAP%>"/>

<c:set var="PIE_COLOR_MAP" value="<%=SystemConstants.PIE_COLOR_MAP%>"/>

<c:set var="MEMBER_POLITICAL_STATUS_MAP" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_MAP%>"/>
<c:set var="MEMBER_POLITICAL_STATUS_GROW" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_GROW%>"/>
<c:set var="MEMBER_POLITICAL_STATUS_POSITIVE" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE%>"/>

<c:set var="JASPER_PRINT_TYPE_INSIDE" value="<%=SystemConstants.JASPER_PRINT_TYPE_INSIDE%>"/>
<c:set var="JASPER_PRINT_TYPE_OUTSIDE" value="<%=SystemConstants.JASPER_PRINT_TYPE_OUTSIDE%>"/>
<c:set var="JASPER_PRINT_TYPE_MEMBER_STAY_ABROAD" value="<%=SystemConstants.JASPER_PRINT_TYPE_MEMBER_STAY_ABROAD%>"/>
<c:set var="JASPER_PRINT_TYPE_MEMBER_STAY_INTERNAL" value="<%=SystemConstants.JASPER_PRINT_TYPE_MEMBER_STAY_INTERNAL%>"/>


<c:set var="MEMBER_INOUT_TYPE_INSIDE" value="<%=MemberConstants.MEMBER_INOUT_TYPE_INSIDE%>"/>
<c:set var="MEMBER_INOUT_TYPE_OUTSIDE" value="<%=MemberConstants.MEMBER_INOUT_TYPE_OUTSIDE%>"/>
<c:set var="MEMBER_INOUT_TYPE_MAP" value="<%=MemberConstants.MEMBER_INOUT_TYPE_MAP%>"/>

<c:set var="MEMBER_STAY_TYPE_ABROAD" value="<%=MemberConstants.MEMBER_STAY_TYPE_ABROAD%>"/>
<c:set var="MEMBER_STAY_TYPE_INTERNAL" value="<%=MemberConstants.MEMBER_STAY_TYPE_INTERNAL%>"/>
<c:set var="MEMBER_STAY_TYPE_MAP" value="<%=MemberConstants.MEMBER_STAY_TYPE_MAP%>"/>

<c:set var="MEMBER_STAY_ABROAD_TYPE_MAP_MAP" value="<%=MemberConstants.MEMBER_STAY_ABROAD_TYPE_MAP_MAP%>"/>

<c:set var="MEMBER_STAY_STATUS_MAP" value="<%=MemberConstants.MEMBER_STAY_STATUS_MAP%>"/>
<c:set var="MEMBER_STAY_STATUS_SELF_BACK" value="<%=MemberConstants.MEMBER_STAY_STATUS_SELF_BACK%>"/>
<c:set var="MEMBER_STAY_STATUS_BACK" value="<%=MemberConstants.MEMBER_STAY_STATUS_BACK%>"/>
<c:set var="MEMBER_STAY_STATUS_APPLY" value="<%=MemberConstants.MEMBER_STAY_STATUS_APPLY%>"/>
<c:set var="MEMBER_STAY_STATUS_BRANCH_VERIFY" value="<%=MemberConstants.MEMBER_STAY_STATUS_BRANCH_VERIFY%>"/>
<c:set var="MEMBER_STAY_STATUS_PARTY_VERIFY" value="<%=MemberConstants.MEMBER_STAY_STATUS_PARTY_VERIFY%>"/>
<c:set var="MEMBER_STAY_STATUS_OW_VERIFY" value="<%=MemberConstants.MEMBER_STAY_STATUS_OW_VERIFY%>"/>

<c:set var="MEMBER_IN_STATUS_MAP" value="<%=MemberConstants.MEMBER_IN_STATUS_MAP%>"/>
<c:set var="MEMBER_IN_STATUS_SELF_BACK" value="<%=MemberConstants.MEMBER_IN_STATUS_SELF_BACK%>"/>
<c:set var="MEMBER_IN_STATUS_BACK" value="<%=MemberConstants.MEMBER_IN_STATUS_BACK%>"/>
<c:set var="MEMBER_IN_STATUS_APPLY" value="<%=MemberConstants.MEMBER_IN_STATUS_APPLY%>"/>
<c:set var="MEMBER_IN_STATUS_PARTY_VERIFY" value="<%=MemberConstants.MEMBER_IN_STATUS_PARTY_VERIFY%>"/>
<c:set var="MEMBER_IN_STATUS_OW_VERIFY" value="<%=MemberConstants.MEMBER_IN_STATUS_OW_VERIFY%>"/>

<c:set var="MEMBER_OUT_STATUS_MAP" value="<%=MemberConstants.MEMBER_OUT_STATUS_MAP%>"/>
<c:set var="MEMBER_OUT_STATUS_ABOLISH" value="<%=MemberConstants.MEMBER_OUT_STATUS_ABOLISH%>"/>
<c:set var="MEMBER_OUT_STATUS_SELF_BACK" value="<%=MemberConstants.MEMBER_OUT_STATUS_SELF_BACK%>"/>
<c:set var="MEMBER_OUT_STATUS_BACK" value="<%=MemberConstants.MEMBER_OUT_STATUS_BACK%>"/>
<c:set var="MEMBER_OUT_STATUS_APPLY" value="<%=MemberConstants.MEMBER_OUT_STATUS_APPLY%>"/>
<c:set var="MEMBER_OUT_STATUS_PARTY_VERIFY" value="<%=MemberConstants.MEMBER_OUT_STATUS_PARTY_VERIFY%>"/>
<c:set var="MEMBER_OUT_STATUS_OW_VERIFY" value="<%=MemberConstants.MEMBER_OUT_STATUS_OW_VERIFY%>"/>

<c:set var="MEMBER_TRANSFER_STATUS_MAP" value="<%=MemberConstants.MEMBER_TRANSFER_STATUS_MAP%>"/>
<c:set var="MEMBER_TRANSFER_STATUS_SELF_BACK" value="<%=MemberConstants.MEMBER_TRANSFER_STATUS_SELF_BACK%>"/>
<c:set var="MEMBER_TRANSFER_STATUS_BACK" value="<%=MemberConstants.MEMBER_TRANSFER_STATUS_BACK%>"/>
<c:set var="MEMBER_TRANSFER_STATUS_APPLY" value="<%=MemberConstants.MEMBER_TRANSFER_STATUS_APPLY%>"/>
<c:set var="MEMBER_TRANSFER_STATUS_FROM_VERIFY" value="<%=MemberConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY%>"/>
<c:set var="MEMBER_TRANSFER_STATUS_TO_VERIFY" value="<%=MemberConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY%>"/>

<c:set var="MEMBER_RETURN_STATUS_MAP" value="<%=MemberConstants.MEMBER_RETURN_STATUS_MAP%>"/>
<c:set var="MEMBER_RETURN_STATUS_DENY" value="<%=MemberConstants.MEMBER_RETURN_STATUS_DENY%>"/>
<c:set var="MEMBER_RETURN_STATUS_APPLY" value="<%=MemberConstants.MEMBER_RETURN_STATUS_APPLY%>"/>
<c:set var="MEMBER_RETURN_STATUS_BRANCH_VERIFY" value="<%=MemberConstants.MEMBER_RETURN_STATUS_BRANCH_VERIFY%>"/>
<c:set var="MEMBER_RETURN_STATUS_PARTY_VERIFY" value="<%=MemberConstants.MEMBER_RETURN_STATUS_PARTY_VERIFY%>"/>

<c:set var="MEMBER_TYPE_MAP" value="<%=MemberConstants.MEMBER_TYPE_MAP%>"/>

<c:set var="MEMBER_INFLOW_STATUS_MAP" value="<%=MemberConstants.MEMBER_INFLOW_STATUS_MAP%>"/>
<c:set var="MEMBER_INFLOW_STATUS_BACK" value="<%=MemberConstants.MEMBER_INFLOW_STATUS_BACK%>"/>
<c:set var="MEMBER_INFLOW_STATUS_APPLY" value="<%=MemberConstants.MEMBER_INFLOW_STATUS_APPLY%>"/>
<c:set var="MEMBER_INFLOW_STATUS_BRANCH_VERIFY" value="<%=MemberConstants.MEMBER_INFLOW_STATUS_BRANCH_VERIFY%>"/>
<c:set var="MEMBER_INFLOW_STATUS_PARTY_VERIFY" value="<%=MemberConstants.MEMBER_INFLOW_STATUS_PARTY_VERIFY%>"/>

<c:set var="MEMBER_INFLOW_OUT_STATUS_MAP" value="<%=MemberConstants.MEMBER_INFLOW_OUT_STATUS_MAP%>"/>
<c:set var="MEMBER_INFLOW_OUT_STATUS_SELF_BACK" value="<%=MemberConstants.MEMBER_INFLOW_OUT_STATUS_SELF_BACK%>"/>
<c:set var="MEMBER_INFLOW_OUT_STATUS_BACK" value="<%=MemberConstants.MEMBER_INFLOW_OUT_STATUS_BACK%>"/>
<c:set var="MEMBER_INFLOW_OUT_STATUS_APPLY" value="<%=MemberConstants.MEMBER_INFLOW_OUT_STATUS_APPLY%>"/>
<c:set var="MEMBER_INFLOW_OUT_STATUS_BRANCH_VERIFY" value="<%=MemberConstants.MEMBER_INFLOW_OUT_STATUS_BRANCH_VERIFY%>"/>
<c:set var="MEMBER_INFLOW_OUT_STATUS_PARTY_VERIFY" value="<%=MemberConstants.MEMBER_INFLOW_OUT_STATUS_PARTY_VERIFY%>"/>

<c:set var="OW_OR_STATUS_MAP" value="<%=OwConstants.OW_OR_STATUS_MAP%>"/>
<c:set var="MEMBER_OUTFLOW_STATUS_MAP" value="<%=MemberConstants.MEMBER_OUTFLOW_STATUS_MAP%>"/>
<c:set var="MEMBER_OUTFLOW_STATUS_BACK" value="<%=MemberConstants.MEMBER_OUTFLOW_STATUS_BACK%>"/>
<c:set var="MEMBER_OUTFLOW_STATUS_APPLY" value="<%=MemberConstants.MEMBER_OUTFLOW_STATUS_APPLY%>"/>
<c:set var="MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY" value="<%=MemberConstants.MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY%>"/>
<c:set var="MEMBER_OUTFLOW_STATUS_PARTY_VERIFY" value="<%=MemberConstants.MEMBER_OUTFLOW_STATUS_PARTY_VERIFY%>"/>

<c:set var="MEMBER_QUIT_STATUS_MAP" value="<%=MemberConstants.MEMBER_QUIT_STATUS_MAP%>"/>
<c:set var="MEMBER_QUIT_STATUS_SELF_BACK" value="<%=MemberConstants.MEMBER_QUIT_STATUS_SELF_BACK%>"/>
<c:set var="MEMBER_QUIT_STATUS_BACK" value="<%=MemberConstants.MEMBER_QUIT_STATUS_BACK%>"/>
<c:set var="MEMBER_QUIT_STATUS_APPLY" value="<%=MemberConstants.MEMBER_QUIT_STATUS_APPLY%>"/>
<c:set var="MEMBER_QUIT_STATUS_BRANCH_VERIFY" value="<%=MemberConstants.MEMBER_QUIT_STATUS_BRANCH_VERIFY%>"/>
<c:set var="MEMBER_QUIT_STATUS_PARTY_VERIFY" value="<%=MemberConstants.MEMBER_QUIT_STATUS_PARTY_VERIFY%>"/>
<c:set var="MEMBER_QUIT_STATUS_OW_VERIFY" value="<%=MemberConstants.MEMBER_QUIT_STATUS_OW_VERIFY%>"/>

<c:set var="MEMBER_STAY_STATUS_MAP" value="<%=MemberConstants.MEMBER_STAY_STATUS_MAP%>"/>

<c:set var="OW_APPLY_APPROVAL_LOG_TYPE_MAP" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MAP%>"/>
<c:set var="OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY%>"/>
<c:set var="OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_ABROAD" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_ABROAD%>"/>
<c:set var="OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_IN" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_IN%>"/>
<c:set var="OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT%>"/>
<c:set var="OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW%>"/>
<c:set var="OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_OUTFLOW" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_OUTFLOW%>"/>
<c:set var="OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN%>"/>
<c:set var="OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER%>"/>
<c:set var="OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY%>"/>
<c:set var="OW_APPLY_APPROVAL_LOG_TYPE_USER_REG" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_USER_REG%>"/>
<c:set var="OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_QUIT" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_QUIT%>"/>
<c:set var="OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW_OUT" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW_OUT%>"/>
<c:set var="OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY%>"/>

<c:set var="OW_APPLY_APPROVAL_LOG_STATUS_DENY" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_DENY%>"/>
<c:set var="OW_APPLY_APPROVAL_LOG_STATUS_PASS" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS%>"/>
<c:set var="OW_APPLY_APPROVAL_LOG_STATUS_BACK" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_BACK%>"/>
<c:set var="OW_APPLY_APPROVAL_LOG_STATUS_NONEED" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED%>"/>
<c:set var="OW_APPLY_APPROVAL_LOG_STATUS_MAP" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_MAP%>"/>

<c:set var="ABROAD_APPLYSELF_MODIFY_TYPE_ORIGINAL" value="<%=AbroadConstants.ABROAD_APPLYSELF_MODIFY_TYPE_ORIGINAL%>"/>
<c:set var="ABROAD_APPLYSELF_MODIFY_TYPE_MODIFY" value="<%=AbroadConstants.ABROAD_APPLYSELF_MODIFY_TYPE_MODIFY%>"/>

<c:set var="MODIFY_BASE_ITEM_TYPE_STRING" value="<%=ModifyConstants.MODIFY_BASE_ITEM_TYPE_STRING%>"/>
<c:set var="MODIFY_BASE_ITEM_TYPE_INT" value="<%=ModifyConstants.MODIFY_BASE_ITEM_TYPE_INT%>"/>
<c:set var="MODIFY_BASE_ITEM_TYPE_DATE" value="<%=ModifyConstants.MODIFY_BASE_ITEM_TYPE_DATE%>"/>
<c:set var="MODIFY_BASE_ITEM_TYPE_IMAGE" value="<%=ModifyConstants.MODIFY_BASE_ITEM_TYPE_IMAGE%>"/>
<c:set var="MODIFY_BASE_ITEM_TYPE_MAP" value="<%=ModifyConstants.MODIFY_BASE_ITEM_TYPE_MAP%>"/>

<c:set var="MODIFY_BASE_APPLY_STATUS_APPLY" value="<%=ModifyConstants.MODIFY_BASE_APPLY_STATUS_APPLY%>"/>
<c:set var="MODIFY_BASE_APPLY_STATUS_DELETE" value="<%=ModifyConstants.MODIFY_BASE_APPLY_STATUS_DELETE%>"/>
<c:set var="MODIFY_BASE_APPLY_STATUS_ALL_CHECK" value="<%=ModifyConstants.MODIFY_BASE_APPLY_STATUS_ALL_CHECK%>"/>

<c:set var="MODIFY_BASE_ITEM_STATUS_MAP" value="<%=ModifyConstants.MODIFY_BASE_ITEM_STATUS_MAP%>"/>

<c:set var="MODIFY_TABLE_APPLY_STATUS_APPLY" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_STATUS_APPLY%>"/>
<c:set var="MODIFY_TABLE_APPLY_STATUS_MAP" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_STATUS_MAP%>"/>

<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_EDU" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_EDU%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_WORK" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_WORK%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN%>"/>

<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_POSTPRO" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_POSTPRO%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_POSTADMIN" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_POSTADMIN%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_POSTWORK" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_POSTWORK%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILY" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILY%>"/>
<c:set var="MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILYABROAD" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILYABROAD%>"/>

<c:set var="MODIFY_TABLE_APPLY_MODULE_MAP" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_MODULE_MAP%>"/>

<c:set var="MODIFY_TABLE_APPLY_TYPE_MODIFY" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MODIFY%>"/>
<c:set var="MODIFY_TABLE_APPLY_TYPE_DELETE" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_TYPE_DELETE%>"/>
<c:set var="MODIFY_TABLE_APPLY_TYPE_MAP" value="<%=ModifyConstants.MODIFY_TABLE_APPLY_TYPE_MAP%>"/>

<c:set var="OW_APPLY_TYPE_TEACHER" value="<%=OwConstants.OW_APPLY_TYPE_TEACHER%>"/>
<c:set var="OW_APPLY_TYPE_STU" value="<%=OwConstants.OW_APPLY_TYPE_STU%>"/>
<c:set var="APPLY_TYPE_MAP" value="<%=OwConstants.OW_APPLY_TYPE_MAP%>"/>

<c:set var="OW_APPLY_STAGE_MAP" value="<%=OwConstants.OW_APPLY_STAGE_MAP%>"/>
<c:set var="OW_APPLY_STAGE_OUT" value="<%=OwConstants.OW_APPLY_STAGE_OUT%>"/>
<c:set var="OW_APPLY_STAGE_DENY" value="<%=OwConstants.OW_APPLY_STAGE_DENY%>"/>
<c:set var="OW_APPLY_STAGE_INIT" value="<%=OwConstants.OW_APPLY_STAGE_INIT%>"/>
<c:set var="OW_APPLY_STAGE_PASS" value="<%=OwConstants.OW_APPLY_STAGE_PASS%>"/>
<c:set var="OW_APPLY_STAGE_ACTIVE" value="<%=OwConstants.OW_APPLY_STAGE_ACTIVE%>"/>
<c:set var="OW_APPLY_STAGE_CANDIDATE" value="<%=OwConstants.OW_APPLY_STAGE_CANDIDATE%>"/>
<c:set var="OW_APPLY_STAGE_PLAN" value="<%=OwConstants.OW_APPLY_STAGE_PLAN%>"/>
<c:set var="OW_APPLY_STAGE_DRAW" value="<%=OwConstants.OW_APPLY_STAGE_DRAW%>"/>
<c:set var="OW_APPLY_STAGE_GROW" value="<%=OwConstants.OW_APPLY_STAGE_GROW%>"/>
<c:set var="OW_APPLY_STAGE_POSITIVE" value="<%=OwConstants.OW_APPLY_STAGE_POSITIVE%>"/>

<c:set var="DISPATCH_CADRE_TYPE_MAP" value="<%=DispatchConstants.DISPATCH_CADRE_TYPE_MAP%>"/>

<c:set var="DISPATCH_WORK_FILE_TYPE_MAP" value="<%=DispatchConstants.DISPATCH_WORK_FILE_TYPE_MAP%>"/>

<c:set var="ABROAD_APPLY_SELF_DATE_TYPE_MAP" value="<%=AbroadConstants.ABROAD_APPLY_SELF_DATE_TYPE_MAP%>"/>
<c:set var="ABROAD_PASSPORT_DRAW_TYPE_MAP" value="<%=AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_MAP%>"/>

<c:set var="ABROAD_PASSPORT_TYPE_KEEP" value="<%=AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP%>"/>
<c:set var="ABROAD_PASSPORT_TYPE_LOST" value="<%=AbroadConstants.ABROAD_PASSPORT_TYPE_LOST%>"/>
<c:set var="ABROAD_PASSPORT_TYPE_CANCEL" value="<%=AbroadConstants.ABROAD_PASSPORT_TYPE_CANCEL%>"/>
<c:set var="ABROAD_PASSPORT_TYPE_MAP" value="<%=AbroadConstants.ABROAD_PASSPORT_TYPE_MAP%>"/>

<c:set var="ABROAD_PASSPORT_CANCEL_TYPE_ABOLISH" value="<%=AbroadConstants.ABROAD_PASSPORT_CANCEL_TYPE_ABOLISH%>"/>
<c:set var="ABROAD_PASSPORT_CANCEL_TYPE_OTHER" value="<%=AbroadConstants.ABROAD_PASSPORT_CANCEL_TYPE_OTHER%>"/>
<c:set var="ABROAD_PASSPORT_CANCEL_TYPE_MAP" value="<%=AbroadConstants.ABROAD_PASSPORT_CANCEL_TYPE_MAP%>"/>

<c:set var="ABROAD_PASSPORT_LOST_TYPE_TRANSFER" value="<%=AbroadConstants.ABROAD_PASSPORT_LOST_TYPE_TRANSFER%>"/>

<c:set var="ABROAD_PASSPORT_DRAW_USE_TYPE_MAP" value="<%=AbroadConstants.ABROAD_PASSPORT_DRAW_USE_TYPE_MAP%>"/>

<c:set var="ABROAD_PASSPORT_DRAW_TYPE_SELF" value="<%=AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_SELF%>"/>
<c:set var="ABROAD_PASSPORT_DRAW_TYPE_TW" value="<%=AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_TW%>"/>
<c:set var="ABROAD_PASSPORT_DRAW_TYPE_OTHER" value="<%=AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_OTHER%>"/>
<c:set var="ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF" value="<%=AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF%>"/>

<c:set var="ABROAD_PASSPORT_DRAW_STATUS_INIT" value="<%=AbroadConstants.ABROAD_PASSPORT_DRAW_STATUS_INIT%>"/>
<c:set var="ABROAD_PASSPORT_DRAW_STATUS_PASS" value="<%=AbroadConstants.ABROAD_PASSPORT_DRAW_STATUS_PASS%>"/>
<c:set var="ABROAD_PASSPORT_DRAW_STATUS_NOT_PASS" value="<%=AbroadConstants.ABROAD_PASSPORT_DRAW_STATUS_NOT_PASS%>"/>
<c:set var="ABROAD_PASSPORT_DRAW_STATUS_MAP" value="<%=AbroadConstants.ABROAD_PASSPORT_DRAW_STATUS_MAP%>"/>

<c:set var="ABROAD_PASSPORT_DRAW_DRAW_STATUS_UNDRAW" value="<%=AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_UNDRAW%>"/>
<c:set var="ABROAD_PASSPORT_DRAW_DRAW_STATUS_DRAW" value="<%=AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_DRAW%>"/>
<c:set var="ABROAD_PASSPORT_DRAW_DRAW_STATUS_RETURN" value="<%=AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_RETURN%>"/>
<c:set var="ABROAD_PASSPORT_DRAW_DRAW_STATUS_ABOLISH" value="<%=AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_ABOLISH%>"/>
<c:set var="ABROAD_PASSPORT_DRAW_DRAW_STATUS_MAP" value="<%=AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_MAP%>"/>

<c:set var="ABROAD_PASSPORT_DRAW_USEPASSPORT_REFUSE_RETURN" value="<%=AbroadConstants.ABROAD_PASSPORT_DRAW_USEPASSPORT_REFUSE_RETURN%>"/>

<c:set var="ABROAD_PASSPORT_APPLY_STATUS_INIT" value="<%=AbroadConstants.ABROAD_PASSPORT_APPLY_STATUS_INIT%>"/>
<c:set var="ABROAD_PASSPORT_APPLY_STATUS_PASS" value="<%=AbroadConstants.ABROAD_PASSPORT_APPLY_STATUS_PASS%>"/>
<c:set var="ABROAD_PASSPORT_APPLY_STATUS_NOT_PASS" value="<%=AbroadConstants.ABROAD_PASSPORT_APPLY_STATUS_NOT_PASS%>"/>
<c:set var="ABROAD_PASSPORT_APPLY_STATUS_MAP" value="<%=AbroadConstants.ABROAD_PASSPORT_APPLY_STATUS_MAP%>"/>

<c:set var="ABROAD_TAIWAN_RECORD_HANDLE_TYPE_OW" value="<%=AbroadConstants.ABROAD_TAIWAN_RECORD_HANDLE_TYPE_OW%>"/>
<c:set var="ABROAD_TAIWAN_RECORD_HANDLE_TYPE_OFFICE" value="<%=AbroadConstants.ABROAD_TAIWAN_RECORD_HANDLE_TYPE_OFFICE%>"/>
<c:set var="ABROAD_TAIWAN_RECORD_HANDLE_TYPE_MAP" value="<%=AbroadConstants.ABROAD_TAIWAN_RECORD_HANDLE_TYPE_MAP%>"/>

<c:set var="ABROAD_APPROVER_TYPE_UNIT" value="<%=AbroadConstants.ABROAD_APPROVER_TYPE_UNIT%>"/>
<c:set var="ABROAD_APPROVER_TYPE_LEADER" value="<%=AbroadConstants.ABROAD_APPROVER_TYPE_LEADER%>"/>
<c:set var="ABROAD_APPROVER_TYPE_MAP" value="<%=AbroadConstants.ABROAD_APPROVER_TYPE_MAP%>"/>

<c:set var="SYS_APPROVAL_LOG_TYPE_MAP" value="<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_MAP%>"/>
<c:set var="SYS_APPROVAL_LOG_TYPE_APPLYSELF" value="<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_APPLYSELF%>"/>
<c:set var="SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT" value="<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_CRS_APPLICANT%>"/>
<c:set var="SYS_APPROVAL_LOG_TYPE_PMD_MEMBER" value="<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER%>"/>
<c:set var="SYS_APPROVAL_LOG_TYPE_CET_TRAINEE" value="<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_TRAINEE%>"/>
<c:set var="SYS_APPROVAL_LOG_TYPE_CET_SPECIAL_OBJ" value="<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_SPECIAL_OBJ%>"/>

<c:set var="SYS_APPROVAL_LOG_STATUS_BACK" value="<%=SystemConstants.SYS_APPROVAL_LOG_STATUS_BACK%>"/>

<c:set var="LOG_MAP" value="<%=LogConstants.LOG_MAP%>"/>