<%@ page import="ext.utils.Pay" %>
<%@ page import="sys.constants.PmdConstants" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set value="${_pMap['payTest']=='true'}" var="_p_payTest"/>

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

<c:set value="<%=PmdConstants.PMD_USER_TYPE_MAP%>" var="PMD_USER_TYPE_MAP"/>
