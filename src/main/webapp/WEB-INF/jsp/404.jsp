<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:if test="${!param.__includePage}">
    <html>
    <head>
        <meta charset="utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
        <meta name="description" content="overview &amp; stats"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
        <title>${_plantform_name}</title>
        <link rel="stylesheet" href="${ctx}/assets/css/bootstrap.css"/>
        <link rel="stylesheet" href="${ctx}/assets/css/font-awesome.css"/>
        <link href="${ctx}/extend/css/docs.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${ctx}/extend/css/navbar.css"/>
    </head>
    <body class="no-skin mob-safari">
    <div id="navbar" class="navbar navbar-default" id="top">
        <button type="button" class="navbar-toggle menu-toggler pull-left" onclick="location.href='${ctx}/'">
            <span class="sr-only">Toggle sidebar</span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>
        </button>
        <div class="navbar-container" id="navbar-container">
            <div class="navbar-header pull-left hidden-xs hidden-sm">
                <div class="logo" style="cursor: pointer;" onclick="location.href='${ctx}/'"><t:img
                        src="/img/logo_white.png"/></div>
                <div class="separator"></div>
                <div class="txt" style="cursor: pointer;" onclick="location.href='${ctx}/'">${_plantform_name}</div>
            </div>
            <div class="navbar-header pull-left hidden-md hidden-lg " style="left: 36px;top: 0px;position: absolute;">
                <a href="${ctx}/" class="navbar-brand">
          <span style="cursor: pointer;font-size: 16px; font-weight: bold">
                  ${_sysConfig.mobilePlantformName}
          </span>
                </a>
            </div>
        </div>
        <div class="navbar-buttons navbar-header pull-right hidden-xs hidden-sm hidden-md" role="navigation">
            <ul class="nav nav-pills">
                <shiro:user>
                    <li class="<c:if test="${_path=='/profile'}">active</c:if>">
                        <a href="${ctx}/#/profile"><i class="fa fa-user"></i>
                                ${_user.realname}（${_user.code}）</a>
                    </li>
                    <c:if test="${!_p_hideHelp}">
                        <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN},${ROLE_PARTYADMIN},${ROLE_BRANCHADMIN}">
                            <li class="<c:if test="${_path=='/help'}">active</c:if>">
                                <a href="${ctx}/help"><i class="ace-icon fa fa-question-circle"></i> 帮助文档</a>
                            </li>
                        </shiro:hasAnyRoles>
                    </c:if>
                    <li>
                        <a href="${ctx}/logout"><i class="ace-icon fa fa-power-off"></i> 退出</a>
                    </li>
                </shiro:user>
            </ul>
        </div>
    </div>
    <div style="padding-top: 20px"></div>
    <div class="container">
</c:if>
<div class="row">
    <div class="col-xs-12">
        <div class="error-container" style="padding-top: 150px;">
            <div class="well">
                <h1 class="grey lighter smaller">
					<span class="blue bigger-125">
						<i class="ace-icon fa fa-sitemap"></i>
						404
					</span>
                    页面不存在
                </h1>
                <hr/>
                <div class="space"></div>
                <div class="center">

                    <a href="javascript:history.go(-1)" class="btn btn-success">
                        <i class="ace-icon fa fa-reply"></i>
                        返回
                    </a>

                    <a href="${ctx}/" class="btn btn-primary">
                        <i class="ace-icon fa fa-home"></i>
                        回到首页
                    </a>
                    <shiro:user>
                        <a href="${ctx}/logout" class="btn btn-danger">
                            <i class="ace-icon fa fa-power-off"></i>
                            安全退出
                        </a>
                    </shiro:user>
                </div>
            </div>
        </div>
        <!-- /section:pages/error -->
        <!-- PAGE CONTENT ENDS -->
    </div><!-- /.col -->
</div>

<c:if test="${!param.__includePage}">
    </div>
    </body>
    </html>
</c:if>
