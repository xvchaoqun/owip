<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
  <shiro:hasAnyRoles name="${ROLE_CADRE},${ROLE_CADRERESERVE}">
    <li class="${cls==0?"active":""}">
      <a href="javascript:;" class="renderBtn" data-type="hashchange" data-querystr="cls=0&module=${module}"><i class="fa fa-th"></i> ${MODIFY_TABLE_APPLY_MODULE_MAP.get(module)}</a>
    </li>
  </shiro:hasAnyRoles>
  <li class="${cls==1?"active":""}">
    <a href="javascript:;" class="renderBtn" data-type="hashchange" data-querystr="cls=1&module=${module}"><i class="fa fa-edit"></i> 信息修改</a>
  </li>
  <li class="${cls==2?"active":""}">
    <a href="javascript:;" class="renderBtn" data-type="hashchange" data-querystr="cls=2&module=${module}"><i class="fa fa-check"></i> 审核完成</a>
  </li>
  <shiro:hasRole name="${ROLE_ADMIN}">
    <li class="${cls==3?"active":""}">
      <a href="javascript:;" class="renderBtn" data-type="hashchange" data-querystr="cls=3&module=${module}"><i class="fa fa-times"></i> 已删除</a>
    </li>
  </shiro:hasRole>
</ul>
