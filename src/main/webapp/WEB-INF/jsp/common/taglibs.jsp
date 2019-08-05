<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="sys.constants.*" %>
<%@ page import="sys.shiro.Constants" %>
<%@ page import="sys.utils.RequestUtils" %>
<%@ page import="java.util.Date" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cm" uri="http://java.sun.com/jsp/jstl/custom" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="wo" uri="/wo-tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="_homeUrl" value="<%=RequestUtils.getHomeURL(request)%>"/>
<c:set var="_path" value="${fn:escapeXml(requestScope['javax.servlet.forward.servlet_path'])}"/>
<c:set value="${cm:getSysConfig()}" var="_sysConfig"/>
<c:set value="${_sysConfig.siteName}" var="_plantform_name"/>
<c:set value="${_sysConfig.schoolName}" var="_school"/>
<c:set value="${_sysConfig.siteShortName}" var="_plantform_short_name"/>

<c:set value="<%=new Date()%>" var="now"/>
<c:set value="${cm:formatDate(now,'yyyy-MM-dd')}" var="_today"/>
<c:set value="${cm:formatDate(now,'yyyy-MM-dd HH:mm')}" var="_todayMinute"/>
<c:set value="${cm:formatDate(now,'yyyy')}" var="_thisYear"/>

<fmt:setBundle basename="spring" var="spring"/>
<fmt:message key="site.hasLoginPage" bundle="${spring}" var="_hasLoginPage"/>
<fmt:message key="upload.maxSize" bundle="${spring}" var="_uploadMaxSize"/>
<fmt:message key="global.session.timeout" bundle="${spring}" var="_global_session_timeout"/>

<c:set value="${_pMap['siteHome']}" var="_p_siteHome"/>
<c:set value="${_pMap['host']}" var="_p_host"/>
<c:set value="${_pMap['hideHelp']=='true'}" var="_p_hideHelp"/>
<c:set value="${_pMap['hasPartyModule']=='true'}" var="_p_hasPartyModule"/>
<c:set value="${_pMap['partyName']}" var="_p_partyName"/>
<c:set value="${_pMap['hasKjCadre']=='true'}" var="_p_hasKjCadre"/>
<c:set value="${_pMap['useCadreState']=='true'}" var="_p_useCadreState"/>

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

<c:set var="PERMISSION_PARTYVIEWALL" value="<%=SystemConstants.PERMISSION_PARTYVIEWALL%>"/>
<c:set var="PERMISSION_CADREONLYVIEW" value="<%=SystemConstants.PERMISSION_CADREONLYVIEW%>"/>
<c:set var="PERMISSION_CADREADMIN" value="<%=SystemConstants.PERMISSION_CADREADMIN%>"/>
<c:set var="PERMISSION_CADREADMINSELF" value="<%=SystemConstants.PERMISSION_CADREADMINSELF%>"/>

<c:set var="USER_SOURCE_JZG" value="<%=SystemConstants.USER_SOURCE_JZG%>"/>
<c:set var="USER_SOURCE_BKS" value="<%=SystemConstants.USER_SOURCE_BKS%>"/>
<c:set var="USER_SOURCE_YJS" value="<%=SystemConstants.USER_SOURCE_YJS%>"/>
<c:set var="USER_SOURCE_ADMIN" value="<%=SystemConstants.USER_SOURCE_ADMIN%>"/>
<c:set var="USER_SOURCE_REG" value="<%=SystemConstants.USER_SOURCE_REG%>"/>

<c:set var="USER_TYPE_MAP" value="<%=SystemConstants.USER_TYPE_MAP%>"/>
<c:set var="USER_TYPE_JZG" value="<%=SystemConstants.USER_TYPE_JZG%>"/>
<c:set var="GENDER_MAP" value="<%=SystemConstants.GENDER_MAP%>"/>

<c:set value="<%=CadreConstants.CADRE_STATUS_MIDDLE%>" var="CADRE_STATUS_MIDDLE"/>
<c:set value="<%=CadreConstants.CADRE_STATUS_LEADER%>" var="CADRE_STATUS_LEADER"/>
<c:set value="<%=CadreConstants.CADRE_STATUS_INSPECT%>" var="CADRE_STATUS_INSPECT"/>
<c:set value="<%=CadreConstants.CADRE_STATUS_MIDDLE_LEAVE%>" var="CADRE_STATUS_MIDDLE_LEAVE"/>
<c:set value="<%=CadreConstants.CADRE_STATUS_LEADER_LEAVE%>" var="CADRE_STATUS_LEADER_LEAVE"/>
<c:set value="<%=CadreConstants.CADRE_STATUS_RESERVE%>" var="CADRE_STATUS_RESERVE"/>
<c:set value="<%=CadreConstants.CADRE_STATUS_MAP%>" var="CADRE_STATUS_MAP"/>
<c:set value="<%=CadreConstants.CADRE_TYPE_CJ%>" var="CADRE_TYPE_CJ"/>
<c:set value="<%=CadreConstants.CADRE_TYPE_KJ%>" var="CADRE_TYPE_KJ"/>
<c:set value="<%=CadreConstants.CADRE_TYPE_MAP%>" var="CADRE_TYPE_MAP"/>

<c:set var="MEMBER_SOURCE_MAP" value="<%=MemberConstants.MEMBER_SOURCE_MAP%>"/>

<c:set var="MEMBER_STATUS_MAP" value="<%=MemberConstants.MEMBER_STATUS_MAP%>"/>
<c:set var="MEMBER_STATUS_NORMAL" value="<%=MemberConstants.MEMBER_STATUS_NORMAL%>"/>
<c:set var="MEMBER_STATUS_TRANSFER" value="<%=MemberConstants.MEMBER_STATUS_TRANSFER%>"/>

<c:set var="MEMBER_TYPE_TEACHER" value="<%=MemberConstants.MEMBER_TYPE_TEACHER%>"/>
<c:set var="MEMBER_TYPE_STUDENT" value="<%=MemberConstants.MEMBER_TYPE_STUDENT%>"/>
<c:set var="MEMBER_TYPE_MAP" value="<%=MemberConstants.MEMBER_TYPE_MAP%>"/>

<c:set var="MEMBER_POLITICAL_STATUS_MAP" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_MAP%>"/>

<c:set var="DISPATCH_CADRE_TYPE_MAP" value="<%=DispatchConstants.DISPATCH_CADRE_TYPE_MAP%>"/>

<c:set value="<%=SystemConstants.UNIT_STATUS_RUN%>" var="UNIT_STATUS_RUN"/>
<c:set value="<%=SystemConstants.UNIT_STATUS_HISTORY%>" var="UNIT_STATUS_HISTORY"/>
