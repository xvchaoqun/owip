<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="${cls==1?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/member?cls=1"}><i class="fa fa-th${cls==1?'-large':''}"></i> 学生党员（${cm:trimToZero(student_normalCount)}）</a>
  </li>
  <li class="${cls==2?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/member?cls=2"}><i class="fa fa-th${cls==2?'-large':''}"></i> 在职教职工党员（${cm:trimToZero(teacher_normalCount)}）</a>
  </li>
  <li class="${cls==3?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/member?cls=3"}><i class="fa fa-th${cls==3?'-large':''}"></i> 离退休党员（${cm:trimToZero(teacher_retireCount)}）</a>
  </li>
  <%--<li class="${cls==4?'active':''}">
    <a ${cls!=4?'href="?cls=4"':''}><i class="fa fa-th${cls==4?'-large':''}"></i> 应退休党员</a>
  </li>
  <li class="${cls==5?'active':''}">
    <a ${cls!=5?'href="?cls=5"':''}><i class="fa fa-th${cls==5?'-large':''}"></i> 已退休党员</a>
  </li>--%>
  <li class="dropdown <c:if test="${cls==6||cls==7}">active</c:if>" >
    <a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
      <i class="fa fa-sign-out"></i> 已转出党员${cls==6?"(学生)":(cls==7)?"(教职工)":""}
      <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
    </a>
    <ul class="dropdown-menu dropdown-info" style="min-width: 100px">
      <li>
        <a href="javascript:;" class="loadPage" data-url="${ctx}/member?cls=6">学生（${cm:trimToZero(student_transferCount)}）</a>
      </li>
      <li>
        <a href="javascript:;" class="loadPage" data-url="${ctx}/member?cls=7">教职工（${cm:trimToZero(teacher_transferCount)}）</a>
      </li>
    </ul>
  </li>
  <div class="buttons pull-left hidden-sm hidden-xs" style="left:50px; position: relative">
    <shiro:hasPermission name="member:add">
      <a href="javascript:;" class="openView btn btn-info btn-sm" data-url="${ctx}/member_au">
        <i class="fa fa-plus"></i> 添加党员</a>
    </shiro:hasPermission>
    <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN},${ROLE_PARTYADMIN},${ROLE_BRANCHADMIN}">
    <a class="popupBtn btn btn-danger btn-sm"
       data-rel="tooltip" data-placement="bottom" title="可查询所有教职工和学生的组织关系状态"
            data-url="${ctx}/member/search"><i class="fa fa-search"></i> 全校组织关系查询</a>
    </shiro:hasAnyRoles>
  </div>
  <div class="buttons pull-right hidden-sm hidden-xs" style="padding-right: 15px;">
    <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN}">
        <a class="popupBtn btn btn-info btn-sm tooltip-info"
           data-url="${ctx}/member_import?inSchool=1"
           data-rel="tooltip" data-placement="top" title="批量导入校内账号的党员信息"><i class="fa fa-upload"></i>
            批量导入(校内)</a>
        <a class="popupBtn btn btn-warning btn-sm tooltip-warning"
           data-url="${ctx}/member_import?inSchool=0"
           data-rel="tooltip" data-placement="top" title="批量导入校外账号的党员信息"><i class="fa fa-upload"></i>
            批量导入(校外)</a>
    </shiro:hasAnyRoles>
  </div>
</ul>
