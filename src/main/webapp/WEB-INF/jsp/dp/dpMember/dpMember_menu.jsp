<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="ROLE_DP_PARTY" value="<%=RoleConstants.ROLE_DP_PARTY%>"/>
<c:set var="ROLE_DP_ADMIN" value="<%=RoleConstants.ROLE_DP_ADMIN%>"/>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="${cls==1?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/dp/dpMember?cls=1"}><i class="fa fa-th${cls==1?'-large':''}"></i> 学生党派成员(${cm:trimToZero(student_normalCount)})</a>
  </li>
  <li class="${cls==2?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/dp/dpMember?cls=2"}><i class="fa fa-th${cls==2?'-large':''}"></i> 在职党派成员(${cm:trimToZero(teacher_normalCount)})</a>
  </li>
  <li class="${cls==3?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/dp/dpMember?cls=3"}><i class="fa fa-th${cls==3?'-large':''}"></i> 离退休党派成员(${cm:trimToZero(teacher_retireCount)})</a>
  </li>
  <li class="${cls==10?'active':''}">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/dp/dpMember?cls=10"}><i class="fa fa-th${cls==10?'-large':''}"></i>
      全部民主党派成员</a>
  </li>
  <li class="dropdown <c:if test="${cls==6||cls==7}">active</c:if>" >
    <a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
      <i class="fa fa-sign-out"></i> 已转出党派成员${cls==6?"(学生)":(cls==7)?"(教工)":""}
      <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
    </a>
    <ul class="dropdown-menu dropdown-info" style="min-width: 100px"  role="menu">
      <li>
        <a href="javascript:;" class="loadPage" data-url="${ctx}/dp/dpMember?cls=6"><i class="fa fa-hand-o-right"></i>  学生（${cm:trimToZero(student_transferCount)}）</a>
      </li>
      <li>
        <a href="javascript:;" class="loadPage" data-url="${ctx}/dp/dpMember?cls=7"><i class="fa fa-hand-o-right"></i>  教职工（${cm:trimToZero(teacher_transferCount)}）</a>
      </li>
    </ul>
  </li>
  <li>
  <div class="buttons hidden-sm hidden-xs" style="padding-left:10px; position: relative">
      <a href="javascript:;" class="openView btn btn-info btn-sm" data-url="${ctx}/dp/dpMember_au">
        <i class="fa fa-plus"></i> 添加成员</a>
    <shiro:hasAnyRoles name="${ROLE_DP_PARTY},${ROLE_DP_ADMIN}">
    <a class="popupBtn btn btn-danger btn-sm"
       data-rel="tooltip" data-placement="bottom" title="可查询所有教职工和学生的组织关系状态"
            data-url="${ctx}/dp/dpMember/search"><i class="fa fa-search"></i> 成员党派关系查询</a>
    </shiro:hasAnyRoles>
    <shiro:hasAnyRoles name="${ROLE_DP_PARTY},${ROLE_DP_ADMIN}">
      <div class="btn-group">
        <button data-toggle="dropdown" class="btn btn-success btn-sm dropdown-toggle">
            <i class="fa fa-upload"></i> 批量导入  <span class="caret"></span>
        </button>
        <ul class="dropdown-menu dropdown-success" role="menu">
            <li>
                <a href="javascript:;" class="popupBtn"
                 data-url="${ctx}/dp/dpMember_import?inSchool=1"><i class="fa fa-arrow-right"></i>
                  校园门户账号导入</a>
            </li>
            <li>
                <a href="javascript:;" class="popupBtn"
                 data-url="${ctx}/dp/dpMember_import?inSchool=0"><i class="fa fa-arrow-right"></i>
                  系统注册账号导入</a>
            </li>
        </ul>
    </div>
    </shiro:hasAnyRoles>
  </div>
  </li>
</ul>
