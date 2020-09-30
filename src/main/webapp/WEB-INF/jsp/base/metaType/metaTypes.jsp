<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:forEach var="metaType" items="${metaTypes}">
  <option value="${metaType.id}" data-bool-attr="${metaType.boolAttr}" data-extra-attr="${metaType.extraAttr}">${metaType.name}</option>
</c:forEach>
<shiro:hasPermission name="metaClass:edit">
<option value="__NEW" data-id="${cm:getMetaClassByCode(param.__code).id}">编辑属性</option>
</shiro:hasPermission>
