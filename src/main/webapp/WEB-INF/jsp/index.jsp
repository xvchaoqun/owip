<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="${cm:getParentIdSet(_path)}" var="parentIdSet"/>
<c:set value="${cm:getCurrentSysResource(_path)}" var="currentSysResource"/>
<!DOCTYPE html>
<html>
<head>
  <jsp:include page="/WEB-INF/jsp/common/head.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/jsp/common/scripts.jsp"></jsp:include>
</head>

<body class="no-skin">
<div id="navbar" class="navbar navbar-default">

  <div class="navbar-container" id="navbar-container">
    <!-- #section:basics/sidebar.mobile.toggle -->
<shiro:lacksRole name="reg">
    <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
      <span class="sr-only">Toggle sidebar</span>

      <span class="icon-bar"></span>

      <span class="icon-bar"></span>

      <span class="icon-bar"></span>
    </button>
</shiro:lacksRole>
    <div class="navbar-header pull-left hidden-xs hidden-sm">
      <div class="logo"  style="cursor: pointer;" onclick="location.href='${ctx}/'"><img src="${ctx}<fmt:message key="site.logo_white" bundle="${spring}"/>"></div>
      <div class="txt" style="cursor: pointer;" onclick="location.href='${ctx}/'">组织工作管理与服务一体化平台</div>
    </div>

    <div class="navbar-header pull-left hidden-md hidden-lg ">
      <a href="index" class="navbar-brand">
        <small  style="cursor: pointer;" onclick="location.href='${ctx}/'">
          组工系统
        </small>
      </a>
    </div>

  </div>
  <div class="navbar-buttons navbar-header pull-right hidden-xs hidden-sm hidden-md" role="navigation">
    <ul class="nav nav-pills">

      <li class="<c:if test="${_path=='/profile'}">active</c:if>">
        <a href="${ctx}/profile"><i class="fa fa-user"></i>
          <shiro:principal property="realname"/>（<shiro:principal property="code"/>）</a>
      </li>
      <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN},${ROLE_PARTYADMIN},${ROLE_BRANCHADMIN}">
      <li class="<c:if test="${_path=='/help'}">active</c:if>">
        <a href="${ctx}/help"><i class="ace-icon fa fa-question-circle"></i> 帮助文档</a>
      </li>
      </shiro:hasAnyRoles>
      <li>
        <a href="${ctx}/logout"><i class="ace-icon fa fa-power-off"></i> 退出</a>
      </li>
    </ul>
  </div>
  <!-- /.navbar-container -->
</div>
<div class="main-container" id="main-container">
  <shiro:lacksRole name="reg">
  <div id="sidebar" class="sidebar responsive">

    <c:import url="/menu"/>
    <!-- /.nav-list -->
    <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
      <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left"
         data-icon2="ace-icon fa fa-angle-double-right"></i>
    </div>
    <script type="text/javascript">
      try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
      $(".sidebar ul.submenu").each(function(){
        if($("li", this).length==0) $(this).remove();
      })
    </script>
  </div>
  </shiro:lacksRole>
  <div class="main-content" >
    <div class="main-content-inner">
      <c:if test="${fn:length(parentIdSet)>2}">
      <div class="breadcrumbs" id="breadcrumbs">
        <script type="text/javascript">
          try {ace.settings.check('breadcrumbs', 'fixed')} catch (e) {}
        </script>
        <ul class="breadcrumb">

          <c:forEach var="parentId" items="${parentIdSet}" varStatus="vs">
            <c:if test="${vs.count==2}">
              <li>
                <i class="ace-icon fa fa-home home-icon"></i>
                <a href="${ctx}/index">首页</a>
              </li>
            </c:if>
            <c:if test="${vs.count>=3}">
              <c:set var="sysResource" value="${cm:getSysResource(parentId)}"/>
                <li>
                  ${sysResource.name}
                </li>
            </c:if>
          </c:forEach>
          <c:if test="${fn:length(parentIdSet)>2}">
          <li class="active">${currentSysResource.name}</li>
          </c:if>
        </ul>
        <!-- /.breadcrumb -->

        <%--<div class="nav-search" id="nav-search">
          <form class="form-search">
              <span class="input-icon">
                  <input type="text" placeholder="搜索 ..." class="nav-search-input"
                         id="nav-search-input" autocomplete="off"/>
                  <i class="ace-icon fa fa-search nav-search-icon"></i>
              </span>
          </form>
        </div>--%>
        <!-- /.nav-search -->
      </div>
      </c:if>

      <div class="page-content" id="page-content">
          <c:import url="${_path}_page">
            <c:param name="__includePage" value="true"/>
          </c:import>
      </div>
      <!-- /.page-content -->
    </div>
  </div>
  <!-- /.main-content -->

  <div class="footer">
    <div class="footer-inner">
      <div class="footer-content">
            <span class="bigger-120">
                <fmt:message key="site.copyright" bundle="${spring}"/>
            </span>
      </div>
    </div>
  </div>

  <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
    <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
  </a>
</div>
<!-- /.main-container -->
<div id="modal" class="modal fade">
  <div class="modal-dialog" role="document" <%--style="min-width: 650px;"--%>>
    <div class="modal-content">
    </div>
  </div>
</div>
<script type="text/javascript" src="${ctx}/location_JSON"></script>
<script type="text/javascript" src="${ctx}/metadata"></script>
</body>
</html>
