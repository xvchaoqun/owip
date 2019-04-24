<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs" id="myTab2">
  <li class="${cls==1?'active':''}">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/memberApply_layout?cls=1"><i class="fa fa-star"></i> 党员发展</a>
  </li>
  <shiro:hasPermission name="applySnRange:*">
  <li class="dropdown <c:if test="${cls==6||cls==7}">active</c:if>" >
      <a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
          <i class="fa fa-sort-numeric-asc"></i> 志愿书编码管理${cls==6?"(编码段管理) ":cls==7?"(使用情况) ":" "}
          <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
      </a>
      <ul class="dropdown-menu dropdown-info" style="min-width: 100px">
          <li>
              <a href="javascript:;" class="loadPage" data-url="${ctx}/memberApply_layout?cls=6"><i class="fa fa-hand-o-right"></i> 编码段管理</a>
          </li>
          <li>
              <a href="javascript:;" class="loadPage" data-url="${ctx}/memberApply_layout?cls=7"><i class="fa fa-hand-o-right"></i> 使用情况</a>
          </li>
      </ul>
  </li>
  </shiro:hasPermission>
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