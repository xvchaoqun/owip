<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/member/constants.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="dropdown <c:if test="${cls==1}">active</c:if>" >
        <a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
            <i class="fa fa-circle-o"></i>学生党员(${USER_TYPE_MAP.get(cm:toByte(param.userType))}${cm:trimToZero(student_normalCount)})
            <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
        </a>
        <ul class="dropdown-menu dropdown-info" style="min-width: 100px">
            <li>
                <a href="javascript:;" class="loadPage" data-url="${ctx}/member?cls=1"><i class="fa fa-hand-o-right"></i>学生党员</a>
            </li>
            <li>
                <a href="javascript:;" class="loadPage" data-url="${ctx}/member?cls=1&userType=${USER_TYPE_BKS}"><i class="fa fa-hand-o-right"></i> 本科生</a>
            </li>
            <li>
                <a href="javascript:;" class="loadPage" data-url="${ctx}/member?cls=1&userType=${USER_TYPE_SS}"><i class="fa fa-hand-o-right"></i> 硕士研究生</a>
            </li>
            <li>
                <a href="javascript:;" class="loadPage" data-url="${ctx}/member?cls=1&userType=${USER_TYPE_BS}"><i class="fa fa-hand-o-right"></i> 博士研究生</a>
            </li>
        </ul>
    </li>
  <li class="${cls==2?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/member?cls=2"}><i class="fa fa-th${cls==2?'-large':''}"></i> 在职教职工党员(${cm:trimToZero(teacher_normalCount)})</a>
  </li>
  <li class="${cls==3?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/member?cls=3"}><i class="fa fa-th${cls==3?'-large':''}"></i> 离退休党员(${cm:trimToZero(teacher_retireCount)})</a>
  </li>
  <li class="${cls==-1?'active':''}">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/member?cls=-1"}><i class="fa fa-th${cls==-1?'-large':''}"></i>
      全部党员</a>
  </li>
    <shiro:hasPermission name="memberOut:list">
  <li class="dropdown <c:if test="${cls>=6}">active</c:if>" >
    <a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
      <i class="fa fa-sign-out"></i> 已转移党员${cls>=6?"(":""}${cls==6?"转出学生":cls==7?"转出教职工":cls==8?"历史党员":cls==9?"暂留党员":""}${cls>=6?")":""}
      <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
    </a>
    <ul class="dropdown-menu dropdown-info" style="min-width: 100px"  role="menu">
      <li>
        <a href="javascript:;" class="loadPage" data-url="${ctx}/member?cls=6"><i class="fa fa-hand-o-right"></i>  已转出学生党员</a>
      </li>
      <li>
        <a href="javascript:;" class="loadPage" data-url="${ctx}/member?cls=7"><i class="fa fa-hand-o-right"></i>  已转出教职工党员</a>
      </li>
        <shiro:hasPermission name="memberHistory:edit">
      <li>
        <a href="javascript:;" class="loadPage" data-url="${ctx}/member?cls=8"><i class="fa fa-hand-o-right"></i>  已转移至历史党员库</a>
      </li>
        </shiro:hasPermission>
      <li>
        <a href="javascript:;" class="loadPage" data-url="${ctx}/member?cls=9"><i class="fa fa-hand-o-right"></i>  已转移至暂留党员库</a>
      </li>
    </ul>
  </li>
        </shiro:hasPermission>
  <li>
  <div class="buttons hidden-sm hidden-xs" style="padding-left:10px; position: relative">
    <shiro:hasPermission name="member:add">
      <a href="javascript:;" class="openView btn btn-info btn-sm" data-url="${ctx}/member_au">
        <i class="fa fa-plus"></i> 添加党员</a>
    </shiro:hasPermission>
    <a class="popupBtn btn btn-danger btn-sm"
       data-rel="tooltip" data-placement="bottom" title="可查询所有教职工和学生的组织关系状态"
            data-url="${ctx}/member/search"><i class="fa fa-search"></i> 全校组织关系查询</a>
    <shiro:hasPermission name="member:import">
      <div class="btn-group">
        <button data-toggle="dropdown" class="btn btn-success btn-sm dropdown-toggle">
            <i class="fa fa-upload"></i> 批量导入  <span class="caret"></span>
        </button>
        <ul class="dropdown-menu dropdown-success" role="menu">
            <li>
                <a href="javascript:;" class="popupBtn"
                 data-url="${ctx}/member_import?inSchool=1"><i class="fa fa-arrow-right"></i>
                  校园门户账号导入</a>
            </li>
            <li>
                <a href="javascript:;" class="popupBtn"
                 data-url="${ctx}/member_import?inSchool=0"><i class="fa fa-arrow-right"></i>
                  系统注册账号导入</a>
            </li>
            <shiro:hasRole name="${ROLE_SUPER}">
            <li>
              <a href="javascript:;" class="popupBtn"
                 data-url="${ctx}/member_import?all=1"><i class="fa fa-arrow-right"></i>
                党员信息一张表导入</a>
            </li>
            </shiro:hasRole>
            <shiro:hasRole name="${ROLE_ODADMIN}">
            <li>
              <a href="javascript:;" class="popupBtn"
                 data-url="${ctx}/member_update"><i class="fa fa-arrow-right"></i>
                组织关系批量调整</a>
            </li>
            </shiro:hasRole>
        </ul>
    </div>
    </shiro:hasPermission>
  </div>
  </li>
</ul>
