<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs" id="myTab2">
  <li class="${cls==1?'active':''}">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/memberApply_layout?cls=1"><i class="fa fa-star"></i> 党员发展</a>
  </li>
  <shiro:hasPermission name="partyPublic:list">
  <li class="${cls==5?'active':''}">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/memberApply_layout?cls=5"><i class="fa fa-file-text"></i> 党员公示</a>
  </li>
  </shiro:hasPermission>
  <li class="${cls==4?'active':''}">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/memberApply_layout?cls=4"><i class="fa fa-download"></i> 数据导出</a>
  </li>
  <shiro:hasPermission name="applyOpenTime:*">
    <li class="${cls==2?'active':''}">
      <a href="javascript:;" class="loadPage" data-url="${ctx}/memberApply_layout?cls=2"><i class="fa fa-clock-o"></i> 开放时间</a>
    </li>
  </shiro:hasPermission>
  <li class="${cls==3?'active':''}">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/memberApply_layout?cls=3"><i class="fa fa-history"></i> 流程日志</a>
  </li>
</ul>