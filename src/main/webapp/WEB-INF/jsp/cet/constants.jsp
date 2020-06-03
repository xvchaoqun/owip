<%@ page import="sys.constants.CetConstants" %>
<%@ page import="sys.utils.RequestUtils" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set value="<%=RequestUtils.getHomeURL(request)%>" var="cetInspectorLoginUrl"/>
<c:set value="${cetInspectorLoginUrl}/m/cet_eva/login" var="cetInspectorLoginUrl"/>

<c:set value="<%=CetConstants.CET_TYPE_MAP%>" var="CET_TYPE_MAP"/>
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

<c:set value="<%=CetConstants.CET_TRAINEE_TYPE_TEMPLATE_MAP%>" var="CET_TRAINEE_TYPE_TEMPLATE_MAP"/>

<c:set value="<%=CetConstants.CET_TRAIN_ENROLL_STATUS_DEFAULT%>" var="CET_TRAIN_ENROLL_STATUS_DEFAULT"/>
<c:set value="<%=CetConstants.CET_TRAIN_ENROLL_STATUS_OPEN%>" var="CET_TRAIN_ENROLL_STATUS_OPEN"/>
<c:set value="<%=CetConstants.CET_TRAIN_ENROLL_STATUS_CLOSED%>" var="CET_TRAIN_ENROLL_STATUS_CLOSED"/>
<c:set value="<%=CetConstants.CET_TRAIN_ENROLL_STATUS_PAUSE%>" var="CET_TRAIN_ENROLL_STATUS_PAUSE"/>
<c:set value="<%=CetConstants.CET_TRAIN_ENROLL_STATUS_NOT_BEGIN%>" var="CET_TRAIN_ENROLL_STATUS_NOT_BEGIN"/>
<c:set value="<%=CetConstants.CET_TRAIN_ENROLL_STATUS_MAP%>" var="CET_TRAIN_ENROLL_STATUS_MAP"/>

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