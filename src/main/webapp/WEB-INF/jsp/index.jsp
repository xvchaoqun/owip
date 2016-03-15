<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
    <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
      <span class="sr-only">Toggle sidebar</span>

      <span class="icon-bar"></span>

      <span class="icon-bar"></span>

      <span class="icon-bar"></span>
    </button>

    <%--<div class="navbar-header pull-left hidden-xs hidden-sm">
      <a href="index" class="navbar-brand">
        <small>
          <i class="fa fa-connectdevelop"></i>
          北京师范大学组织工作管理与服务一体化平台
        </small>
      </a>
    </div>--%>
    <div class="navbar-header pull-left hidden-xs hidden-sm">
      <div class="logo"><img src="/extend/img/logo_white.png"></div>
      <div class="txt">组织工作管理与服务一体化平台</div>
    </div>

    <div class="navbar-header pull-left hidden-md hidden-lg ">
      <a href="index" class="navbar-brand">
        <small>
          组工系统
        </small>
      </a>
    </div>

  </div>
  <div class="navbar-buttons navbar-header pull-right hidden-xs hidden-sm hidden-md" role="navigation">
    <ul class="nav nav-pills">

      <li class="<c:if test="${_path=='/profile'}">active</c:if>">
        <a href="${ctx}/profile"><i class="fa fa-user"></i>
          <shiro:principal property="realname"/></a>
      </li>
      <%--<li class="<c:if test="${_path=='/setting'}">active</c:if>">
        <a href="${ctx}/setting"><i class="ace-icon fa fa-cog"></i> 个人设置</a>
      </li>--%>
      <li>
        <a href="${ctx}/logout"><i class="ace-icon fa fa-power-off"></i> 退出</a>
      </li>
    </ul>
  </div>
  <!-- /.navbar-container -->
</div>
<div class="main-container" id="main-container">

  <div id="sidebar" class="sidebar responsive">
    <script type="text/javascript">
      try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
    </script>
    <c:import url="/menu"/>
    <!-- /.nav-list -->
    <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
      <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left"
         data-icon2="ace-icon fa fa-angle-double-right"></i>
    </div>
  </div>

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
  <script>
      /*var url_anchor = window.location.toString().split("#");
      var anchor = url_anchor[1];
      if(anchor && anchor.length>0){
        if(url_anchor[0].endWith("unit") || url_anchor[0].endWith("cadre")) { //单位页或干部页
          $(".tabbable li a[href=#" + anchor + "]").click();
        }
      }*/
  </script>
  <div class="footer">
    <div class="footer-inner">
      <div class="footer-content">
            <span class="bigger-120">
                北京师范大学党委组织部 <span class="blue bolder">&copy; 2015</span>
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
</body>
</html>
