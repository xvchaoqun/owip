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
  <!-- inline scripts related to this page -->
  <script src="${ctx}/assets/js/flot/jquery.flot.js"></script>
  <script src="${ctx}/assets/js/flot/jquery.flot.pie.js"></script>
  <script src="${ctx}/assets/js/flot/jquery.flot.resize.js"></script>
  <script src="${ctx}/assets/js/ace/elements.scroller.js"></script>
  <script src="${ctx}/extend/js/highcharts.js"></script>
  <script src="${ctx}/extend/js/exporting.js"></script>
</head>

<body class="no-skin" onload="$(window).trigger('hashchange')">
<div id="navbar" class="navbar navbar-default navbar-fixed-top">
 <%-- <script type="text/javascript">
    try{ace.settings.check('navbar' , 'fixed')}catch(e){}
  </script>--%>
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

      <li class="">
        <a href="javascript:;" class="hashchange" data-url="${ctx}/profile"><i class="fa fa-user"></i>
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
  <div id="sidebar" class="sidebar responsive sidebar-fixed">
    <%--<script type="text/javascript">
      try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
    </script>--%>

    <c:import url="/menu"/>
    <!-- /.nav-list -->
    <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
      <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left"
         data-icon2="ace-icon fa fa-angle-double-right"></i>
    </div>
    <script type="text/javascript">

     /* try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}*/
      $(".sidebar ul.submenu").each(function(){
        if($("li", this).length==0) $(this).remove();
      })
    </script>
  </div>
  </shiro:lacksRole>
  <div class="main-content" >
    <div class="main-content-inner">
      <div class="breadcrumbs" id="breadcrumbs" style="display: none;">

      </div>

      <div class="page-content" id="page-content">

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

  <a href="javascript:;" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
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
<script type="text/template" id="breadcrumbs_tpl">
  <ul class="breadcrumb">
    {{_.each(data.parents, function(p, idx){ }}
      {{if(idx==0){}}
        <li>
          <i class="ace-icon fa fa-home home-icon"></i>
          <a href="${ctx}/">回到首页</a>
        </li>
      {{}}}
      {{if(idx>=1){}}
        <li>
            {{=p.name}}
        </li>
      {{}}}
    {{});}}
      {{if(data.parents.length>=1){}}
      <li class="active">{{=data.cur.name}}</li>
      {{}}}
  </ul>
</script>
</body>
</html>
