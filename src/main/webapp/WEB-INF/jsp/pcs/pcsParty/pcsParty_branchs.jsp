<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="user" value="${cm:getUserById(param.userId)}"/>
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>推荐提名的党支部（${user.realname}-${user.code}）</h3>
  </div>
  <div class="modal-body">
    <div class="tip">
    <ul>
      <c:forEach items="${partyList}" var="party">
        <c:if test="${cm:isDirectBranch(party.id)}">
          <li>${party.name}</li>
        </c:if>
      </c:forEach>
      <c:forEach items="${branchList}" var="branch">
        <li>${branch.name}</li>
      </c:forEach>
    </ul>
      </div>
  </div>
  <div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-info">关闭</a></div>