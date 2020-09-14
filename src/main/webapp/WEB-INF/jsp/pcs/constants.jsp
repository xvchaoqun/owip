<%@ page import="sys.constants.PcsConstants" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set value="${cm:getHtmlFragment('hf_pcs_poll_1_pc_notice')}" var="_1_pc"/>
<c:set value="${cm:getHtmlFragment('hf_pcs_poll_2_pc_notice')}" var="_2_pc"/>
<c:set value="${cm:getHtmlFragment('hf_pcs_poll_3_pc_notice')}" var="_3_pc"/>
<c:set value="${cm:getHtmlFragment('hf_pcs_poll_1_m_notice')}" var="_1_m"/>
<c:set value="${cm:getHtmlFragment('hf_pcs_poll_2_m_notice')}" var="_2_m"/>
<c:set value="${cm:getHtmlFragment('hf_pcs_poll_3_m_notice')}" var="_3_m"/>
<c:set value="${cm:getHtmlFragment('hf_pcs_poll_1_paper_notice')}" var="_1_paper"/>
<c:set value="${cm:getHtmlFragment('hf_pcs_poll_2_paper_notice')}" var="_2_paper"/>
<c:set value="${cm:getHtmlFragment('hf_pcs_poll_3_paper_notice')}" var="_3_paper"/>

<c:set value="${_pMap['pcs_poll_member_need_vote']}" var="_member_need_vote"/>
<c:set value="${_pMap['pcs_poll_site_name']}" var="_p_pcsSiteName"/>
<c:set value="<%=RequestUtils.getHomeURL(request)%>" var="homeURL"/>

<c:set value="<%=RoleConstants.ROLE_PCS_ADMIN%>" var="ROLE_PCS_ADMIN"/>

<c:set value="<%=PcsConstants.RESULT_STATUS_MAP%>" var="RESULT_STATUS_MAP"/>
<c:set value="<%=PcsConstants.RESULT_STATUS_AGREE%>" var="RESULT_STATUS_AGREE"/>
<c:set value="<%=PcsConstants.RESULT_STATUS_DISAGREE%>" var="RESULT_STATUS_DISAGREE"/>
<c:set value="<%=PcsConstants.RESULT_STATUS_ABSTAIN%>" var="RESULT_STATUS_ABSTAIN"/>
<c:set value="<%=PcsConstants.RESULT_STATUS_OTHER%>" var="RESULT_STATUS_OTHER"/>

<c:set value="<%=PcsConstants.PCS_POLL_STAGE_MAP%>" var="PCS_POLL_STAGE_MAP"/>
<c:set value="<%=PcsConstants.PCS_POLL_FIRST_STAGE%>" var="PCS_POLL_FIRST_STAGE"/>
<c:set value="<%=PcsConstants.PCS_POLL_SECOND_STAGE%>" var="PCS_POLL_SECOND_STAGE"/>
<c:set value="<%=PcsConstants.PCS_POLL_THIRD_STAGE%>" var="PCS_POLL_THIRD_STAGE"/>

<c:set value="<%=PcsConstants.PCS_USER_TYPE_MAP%>" var="PCS_USER_TYPE_MAP"/>
<c:set value="<%=PcsConstants.PCS_USER_TYPE_DW%>" var="PCS_USER_TYPE_DW"/>
<c:set value="<%=PcsConstants.PCS_USER_TYPE_JW%>" var="PCS_USER_TYPE_JW"/>
<c:set value="<%=PcsConstants.PCS_USER_TYPE_PR%>" var="PCS_USER_TYPE_PR"/>

<c:set value="<%=PcsConstants.PCS_STAGE_FIRST%>" var="PCS_STAGE_FIRST"/>
<c:set value="<%=PcsConstants.PCS_STAGE_SECOND%>" var="PCS_STAGE_SECOND"/>
<c:set value="<%=PcsConstants.PCS_STAGE_THIRD%>" var="PCS_STAGE_THIRD"/>
<c:set value="<%=PcsConstants.PCS_STAGE_MAP%>" var="PCS_STAGE_MAP"/>

<c:set value="<%=PcsConstants.PCS_PR_TYPE_PRO%>" var="PCS_PR_TYPE_PRO"/>
<c:set value="<%=PcsConstants.PCS_PR_TYPE_STU%>" var="PCS_PR_TYPE_STU"/>
<c:set value="<%=PcsConstants.PCS_PR_TYPE_RETIRE%>" var="PCS_PR_TYPE_RETIRE"/>
<c:set value="<%=PcsConstants.PCS_PR_TYPE_MAP%>" var="PCS_PR_TYPE_MAP"/>

<c:set value="<%=PcsConstants.PCS_PR_USER_TYPE_MAP%>" var="PCS_PR_USER_TYPE_MAP"/>
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