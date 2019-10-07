<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/cr/constants.jsp" %>
<c:forEach items="${crRequire.rules}" var="rule" varStatus="vs">
  <div>（${vs.count}）<c:forEach items="${rule.ruleItems}" var="item" varStatus="vs2">
    <span class="type">${CRS_POST_RULE_TYPE_MAP.get(item.type)}</span>： <span class="val">${item.val}</span>
    ${vs2.last?"":'<br/><span style="margin-left: 38px;">或 </span>'}
  </c:forEach>
  </div>
</c:forEach>