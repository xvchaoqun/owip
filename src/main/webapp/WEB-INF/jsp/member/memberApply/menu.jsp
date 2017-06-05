<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs" id="myTab2">
  <li class="${cls==1?'active':''}">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/memberApply_layout?cls=1">申请流程</a>
  </li>
  <li class="${cls==4?'active':''}">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/memberApply_layout?cls=4">数据导出</a>
  </li>
  <shiro:hasPermission name="applyOpenTime:*">
    <li class="${cls==2?'active':''}">
      <a href="javascript:;" class="loadPage" data-url="${ctx}/memberApply_layout?cls=2">开放时间</a>
    </li>
  </shiro:hasPermission>
  <li class="${cls==3?'active':''}">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/memberApply_layout?cls=3">流程日志</a>
  </li>
</ul>