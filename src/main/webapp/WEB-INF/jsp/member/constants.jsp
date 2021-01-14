<%@ page import="sys.constants.OwConstants" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${_pMap['memberApply_needContinueDevelop']=='true'}" var="_memberApply_needContinueDevelop"/>
<c:set value="${_pMap['memberApply_timeLimit']=='true'}" var="_memberApply_timeLimit"/>
<c:set value="${_pMap['memberApply_needCandidateTrain']=='true'}" var="_memberApply_needCandidateTrain"/>
<c:set value="${_pMap['draw_od_check']=='true'}" var="_p_draw_od_check"/>
<c:set value="${cm:trimToZero(_pMap['contactUsers_count'])}" var="_p_contactUsers_count"/>
<c:set value="${cm:trimToZero(_pMap['sponsorUsers_count'])}" var="_p_sponsorUsers_count"/>
<c:set value="${cm:trimToZero(_pMap['growContactUsers_count'])}" var="_p_growContactUsers_count"/>
<c:set value="${_pMap['ignore_plan_and_draw']=='true'}" var="_ignore_plan_and_draw"/>
<c:set value="${_pMap['memberOutNeedOwCheck']=='true'}" var="_p_memberOutNeedOwCheck"/>
<c:set value="${_pMap['use_code_as_identify']=='true'}" var="_use_code_as_identify"/>

<c:set var="OW_APPLY_STAGE_MAP" value="<%=OwConstants.OW_APPLY_STAGE_MAP%>"/>
<c:set var="OW_APPLY_STAGE_REMOVE" value="<%=OwConstants.OW_APPLY_STAGE_REMOVE%>"/>
<c:set var="OW_APPLY_STAGE_OUT" value="<%=OwConstants.OW_APPLY_STAGE_OUT%>"/>
<c:set var="OW_APPLY_STAGE_DENY" value="<%=OwConstants.OW_APPLY_STAGE_DENY%>"/>
<c:set var="OW_APPLY_TYPE_TEACHER" value="<%=OwConstants.OW_APPLY_TYPE_TEACHER%>"/>
<c:set var="OW_APPLY_TYPE_STU" value="<%=OwConstants.OW_APPLY_TYPE_STU%>"/>
<c:set var="OW_APPLY_STAGE_INIT" value="<%=OwConstants.OW_APPLY_STAGE_INIT%>"/>
<c:set var="OW_APPLY_STAGE_PASS" value="<%=OwConstants.OW_APPLY_STAGE_PASS%>"/>
<c:set var="OW_APPLY_STAGE_ACTIVE" value="<%=OwConstants.OW_APPLY_STAGE_ACTIVE%>"/>
<c:set var="OW_APPLY_STAGE_CANDIDATE" value="<%=OwConstants.OW_APPLY_STAGE_CANDIDATE%>"/>
<c:set var="OW_APPLY_STAGE_PLAN" value="<%=OwConstants.OW_APPLY_STAGE_PLAN%>"/>
<c:set var="OW_APPLY_STAGE_POSITIVE" value="<%=OwConstants.OW_APPLY_STAGE_POSITIVE%>"/>
<c:set var="OW_APPLY_STAGE_DRAW" value="<%=OwConstants.OW_APPLY_STAGE_DRAW%>"/>
<c:set var="OW_APPLY_STAGE_GROW" value="<%=OwConstants.OW_APPLY_STAGE_GROW%>"/>
<c:set var="OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY%>"/>
<c:set var="OW_APPLY_CONTINUE_MAP" value="<%=OwConstants.OW_APPLY_CONTINUE_MAP%>"/>
<c:set var="OW_ENTER_APPLY_TYPE_MEMBERAPPLY" value="<%=OwConstants.OW_ENTER_APPLY_TYPE_MEMBERAPPLY%>"/>
<c:set var="OW_ENTER_APPLY_TYPE_RETURN" value="<%=OwConstants.OW_ENTER_APPLY_TYPE_RETURN%>"/>
<c:set var="OW_ENTER_APPLY_TYPE_MEMBERIN" value="<%=OwConstants.OW_ENTER_APPLY_TYPE_MEMBERIN%>"/>
<c:set var="OW_ENTER_APPLY_TYPE_MEMBERINFLOW" value="<%=OwConstants.OW_ENTER_APPLY_TYPE_MEMBERINFLOW%>"/>

<c:set var="MEMBER_IN_STATUS_MAP" value="<%=MemberConstants.MEMBER_IN_STATUS_MAP%>"/>
<c:set var="MEMBER_IN_STATUS_APPLY" value="<%=MemberConstants.MEMBER_IN_STATUS_APPLY%>"/>
<c:set var="MEMBER_IN_STATUS_PARTY_VERIFY" value="<%=MemberConstants.MEMBER_IN_STATUS_PARTY_VERIFY%>"/>
<c:set var="MEMBER_IN_STATUS_OW_VERIFY" value="<%=MemberConstants.MEMBER_IN_STATUS_OW_VERIFY%>"/>

<c:set var="MEMBER_INFLOW_STATUS_APPLY" value="<%=MemberConstants.MEMBER_INFLOW_STATUS_APPLY%>"/>

<c:set var="MEMBER_RETURN_STATUS_APPLY" value="<%=MemberConstants.MEMBER_RETURN_STATUS_APPLY%>"/>

<c:set var="MEMBER_OUT_STATUS_MAP" value="<%=MemberConstants.MEMBER_OUT_STATUS_MAP%>"/>
<c:set var="MEMBER_OUT_STATUS_APPLY" value="<%=MemberConstants.MEMBER_OUT_STATUS_APPLY%>"/>

<c:set var="JASPER_PRINT_TYPE_LETTER_PRINT" value="<%=SystemConstants.JASPER_PRINT_TYPE_LETTER_PRINT%>"/>
<c:set var="JASPER_PRINT_TYPE_LETTER_FILL_PRINT" value="<%=SystemConstants.JASPER_PRINT_TYPE_LETTER_FILL_PRINT%>"/>
