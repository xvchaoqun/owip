<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:forEach var="entity" items="${cm:getMetaTypes('mc_democratic_party')}">
  <c:set value="${entity.value}" var="metaType"/>
  <c:if test="${(param.dp==1 && !metaType.boolAttr) || param.dp!=1}"><!--param.dp=1时只显示民主党派，不显示群众-->
  <option value="${metaType.id}" data-bool-attr="${metaType.boolAttr}" data-extra-attr="${metaType.extraAttr}">${metaType.name}</option>
  </c:if>
</c:forEach>
