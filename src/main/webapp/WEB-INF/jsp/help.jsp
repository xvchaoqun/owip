<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
  <meta charset="utf-8"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
  <meta name="description" content="overview &amp; stats" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
  <title>组织工作管理与服务一体化平台</title>
  <link rel="stylesheet" href="${ctx}/assets/css/bootstrap.css" />
  <link href="${ctx}/extend/css/docs.min.css" rel="stylesheet">
  <link rel="stylesheet" href="${ctx}/extend/css/navbar.css" />
  <link href="${ctx}/css/doc.css" rel="stylesheet">
</head>
<body>
<div id="navbar" class="navbar navbar-default" id="top">
  <div class="navbar-container" id="navbar-container">
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
        <a href="${ctx}/#${ctx}/profile"><i class="fa fa-user"></i>
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
</div>
<div class="container bs-docs-container">
  <div class="row">
    <div class="col-md-9" role="main">
      <div class="bs-callout bs-callout-info" id="migration">
        <h4>本页面可以使用Ctrl+F进行搜索</h4>
      </div>
      <div class="bs-docs-section">
        <h1 id="download" class="page-header">帮助文档下载</h1>
        <div class="bs-callout bs-callout-warning">
            <h4>北京师范大学组织工作管理与服务平台用户手册-党建分册</h4>
            <a href="/attach?code=af_help_dj"
               class="btn btn-lg btn-outline" target="_blank">下载文档</a>
        </div>
        <div class="bs-callout bs-callout-warning">
          <h4>组织工作管理与服务一体化平台（党建部分使用说明）</h4>
          <p>
            <a href="/attach?code=af_ppt_dj"
               class="btn btn-lg btn-outline" target="_blank">下载文档</a>
          </p>
        </div>
      </div>

      <c:forEach items="${hfDocs}" var="doc">
      <div class="bs-docs-section">
        <h1 id="${doc.code}" class="page-header">${doc.title}</h1>
        ${doc.content}
        <c:forEach items="${doc.childs}" var="child">
          <h2 id="${child.code}">${child.title}</h2>
          ${child.content}
        </c:forEach>
        </div>
      </c:forEach>

      <%--<div class="bs-docs-section">
        <h1 id="whats-included" class="page-header">常见问题</h1>

        <h2 id="whats-included-precompiled">党建相关问题</h2>

        <h2 id="whats-included-source">干部相关问题</h2>

      </div>--%>

    </div>
    <div class="col-md-3">
      <div class="bs-docs-sidebar hidden-print hidden-xs hidden-sm" role="complementary">
        <ul class="nav bs-docs-sidenav">

          <li>
            <a href="#download">帮助文档下载</a>
          </li>
          <c:forEach items="${hfDocs}" var="doc">
            <li><a href="#${doc.code}">${doc.title}</a>
            <c:if test="${fn:length(doc.childs)>0}">
              <ul class="nav">
            <c:forEach items="${doc.childs}" var="child">
              <li><a href="#${child.code}">${child.title}</a>
            </c:forEach>
              </ul>
            </c:if>
            </li>
          </c:forEach>

          <%--<li>
            <a href="#whats-included">常见问题</a>
            <ul class="nav">
              <li><a href="#whats-included-precompiled">党建相关问题</a></li>
              <li><a href="#whats-included-source">干部相关问题</a></li>
            </ul>
          </li>--%>
        </ul>
        <a class="back-to-top" href="#top">
          返回顶部
        </a>

        <a style="margin-top:20px;margin-left: 20px;display: block" href="${ctx}/">
          返回主界面
        </a>

      </div>
    </div>
  </div>
</div>

<!-- Footer
================================================== -->
<footer class="bs-docs-footer" role="contentinfo">
  <div class="container">
    <div class="footer">
      <div class="footer-inner">
        <div class="footer-content">
            <span class="bigger-120">
                <fmt:message key="site.school" bundle="${spring}"/>党委组织部 <span class="blue bolder">&copy;2016</span>
            </span>
        </div>
      </div>
    </div>
  </div>
</footer>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="${ctx}/assets/js/jquery.js"></script>
<script src="${ctx}/assets/js/bootstrap.js"></script>
<script src="${ctx}/extend/js/docs.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="${ctx}/extend/js/ie10-viewport-bug-workaround.js"></script>

</body>
</html>
