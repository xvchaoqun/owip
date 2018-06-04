<%@ page import="sys.constants.PcsConstants" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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