<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:forEach items="${cadres}" var="cadre" varStatus="vs">
  ${cadre.realname}，${cadre.title}，${GENDER_MAP.get(cadre.gender)}，${cadre.nation}，${cm:calAge(cadre.birth)}，${cm:getMetaType(cadre.eduId).name}，${cadre.cadreDpType==-1?"-":(cm:cadreParty(cadre.cadreDpType, false, null))}，${empty cadre.proPost?cadre.manageLevel:cadre.proPost}，专业是${cadre.major}。
  <c:if test="${!vs.last}">
    <br/>
  </c:if>
</c:forEach>