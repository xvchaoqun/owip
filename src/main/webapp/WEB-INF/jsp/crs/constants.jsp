<%@ page import="sys.constants.CrsConstants" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set value="<%=SystemConstants.UNIT_POST_STATUS_NORMAL%>" var="UNIT_POST_STATUS_NORMAL"/>

<c:set value="<%=CrsConstants.CRS_EXPERT_STATUS_NOW%>" var="CRS_EXPERT_STATUS_NOW"/>
<c:set value="<%=CrsConstants.CRS_EXPERT_STATUS_HISTORY%>" var="CRS_EXPERT_STATUS_HISTORY"/>
<c:set value="<%=CrsConstants.CRS_EXPERT_STATUS_DELETE%>" var="CRS_EXPERT_STATUS_DELETE"/>

<c:set value="<%=CrsConstants.CRS_TEMPLATE_TYPE_BASE%>" var="CRS_TEMPLATE_TYPE_BASE"/>
<c:set value="<%=CrsConstants.CRS_TEMPLATE_TYPE_POST%>" var="CRS_TEMPLATE_TYPE_POST"/>
<c:set value="<%=CrsConstants.CRS_TEMPLATE_TYPE_MEETINGNOTICE%>" var="CRS_TEMPLATE_TYPE_MEETINGNOTICE"/>
<c:set value="<%=CrsConstants.CRS_TEMPLATE_TYPE_POST_DUTY%>" var="CRS_TEMPLATE_TYPE_POST_DUTY"/>

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

<c:set value="<%=CrsConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_INIT%>" var="CRS_APPLICANT_REQUIRE_CHECK_STATUS_INIT"/>

<c:set value="<%=CrsConstants.CRS_POST_STATUS_MAP%>" var="CRS_POST_STATUS_MAP"/>
<c:set value="<%=CrsConstants.CRS_POST_STATUS_NORMAL%>" var="CRS_POST_STATUS_NORMAL"/>
<c:set value="<%=CrsConstants.CRS_POST_STATUS_FINISH%>" var="CRS_POST_STATUS_FINISH"/>
<c:set value="<%=CrsConstants.CRS_POST_STATUS_DELETE%>" var="CRS_POST_STATUS_DELETE"/>
<c:set value="<%=CrsConstants.CRS_POST_STATUS_ABOLISH%>" var="CRS_POST_STATUS_ABOLISH"/>

<c:set value="<%=CrsConstants.CRS_TEMPLATE_TYPE_MAP%>" var="CRS_TEMPLATE_TYPE_MAP"/>

<c:set value="<%=CrsConstants.CRS_APPLY_USER_STATUS_OPEN%>" var="CRS_APPLY_USER_STATUS_OPEN"/>
<c:set value="<%=CrsConstants.CRS_APPLY_USER_STATUS_CLOSED%>" var="CRS_APPLY_USER_STATUS_CLOSED"/>
<c:set value="<%=CrsConstants.CRS_APPLY_USER_STATUS_AMP%>" var="CRS_APPLY_USER_STATUS_AMP"/>
