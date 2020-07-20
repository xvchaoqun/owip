<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="LOGIN_TYPE_WX" value="<%=SystemConstants.LOGIN_TYPE_WX%>"/>
<c:set var="_requestPath" value="${_path}"/>
<c:set var="_queryString" value="${fn:escapeXml(requestScope['javax.servlet.forward.query_string'])}"/>
<c:if test="${not empty _queryString}">
    <c:set var="_requestPath" value="${_path}?${_queryString}"/>
</c:if>
<c:set value="${cm:getParentIdSet(_requestPath)}" var="parentIdSet"/>
<c:set value="${cm:getCurrentSysResource(_requestPath)}" var="currentSysResource"/>
<!DOCTYPE html>
<html>
	<head>
	<jsp:include page="/WEB-INF/jsp/common/m_head.jsp"></jsp:include>
	</head>
	<body class="no-skin">
		<!-- #section:basics/navbar.layout -->
		<div id="navbar" class="navbar navbar-default navbar-fixed-top">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>

			<div class="navbar-container" id="navbar-container">
				<!-- #section:basics/sidebar.mobile.toggle -->
				<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
					<span class="sr-only">Toggle sidebar</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>

				<!-- /section:basics/sidebar.mobile.toggle -->
				<div class="navbar-header pull-left">
					<!-- #section:basics/navbar.layout.brand -->
					<a href="javascript:;" class="navbar-brand">
						<span style="font-size: 16px; font-weight: bold">
							${_sysConfig.mobilePlantformName}
						</span>
					</a>
				</div>
				<!-- #section:basics/navbar.dropdown -->
				<div class="navbar-buttons navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<!-- #section:basics/navbar.user_menu -->
						<li class="light-blue">
							<a data-toggle="dropdown" href="javascript:;" class="dropdown-toggle">
								<img class="nav-user-photo" src="${ctx}/m/avatar?path=${cm:encodeURI(_user.avatar)}&m=1" width="90" alt="头像" />

								<i class="ace-icon fa fa-caret-down"></i>
							</a>
							<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="${ctx}/m/userInfo">
										<i class="ace-icon fa fa-cog"></i>
										账号信息
									</a>
								</li>

								<c:if test="${cm:isPermitted('sysLogin:switch') || cm:isPermitted('sysLogin:switchParty')}">
									<li class="divider"></li>
									<li>
										<a href="javascript:;"
										   data-url="${ctx}/m/sysLogin_switch?mobile=1"
										   class="popupBtn">
											<i class="fa fa-refresh"></i> 切换账号
										</a>
									</li>
								</c:if>
								<c:if test="${not empty sessionScope._switchUser}">
									<li class="divider"></li>
									<li>
										<a href="${ctx}/m/sysLogin_switch_back">
											<i class="fa fa-reply"></i> 返回主账号
										</a>
									</li>
								</c:if>
								<c:if test="${sessionScope._loginType!=LOGIN_TYPE_WX}">
								<li class="divider"></li>
								<li>
									<a href="${ctx}/m/logout">
										<i class="ace-icon fa fa-power-off"></i>
										安全退出
									</a>
								</li>
								</c:if>
							</ul>
						</li>
					</ul>
				</div>
			</div><!-- /.navbar-container -->
		</div>

		<!-- /section:basics/navbar.layout -->
		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<!-- #section:basics/sidebar -->
			<div id="sidebar" class="sidebar responsive">
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
				</script>

				<c:import url="/menu?isMobile=1"/>

				<!-- /section:basics/sidebar.layout.minimize -->
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
				</script>
			</div>
			<div class="main-content">
				<div class="main-content-inner">
					<!-- #section:basics/content.breadcrumbs -->
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<c:if test="${_path eq '/m/index'}">
								<li>
									<i class="ace-icon fa fa-home home-icon"></i>
									首页
								</li>
							</c:if>
							<c:if test="${_path ne '/m/index'}">
							<c:forEach var="parentId" items="${parentIdSet}" varStatus="vs">
								<c:if test="${parentId==0}">
									<li>
										<i class="ace-icon fa fa-home home-icon"></i>
										<a href="${ctx}/m/index">回到首页</a>
									</li>
								</c:if>
								<%--<c:set var="sysResource" value="${cm:getSysResource(parentId, true)}"/>
								<c:if test="${sysResource.parentId>0}">
									<li>
										${sysResource.name}
									</li>
								</c:if>--%>
							</c:forEach>
							<li>
								${currentSysResource.name}
							</li>
							</c:if>
						</ul>
					</div>
					<div class="page-content" id="page-content">
						<c:import url="${_path}_page">
							<c:param name="__includePage" value="true"/>
						</c:import>
					</div><!-- /.page-content -->
				</div>
			</div><!-- /.main-content -->

			<div class="footer">
				<div class="footer-inner">
					<div class="footer-content">
						<span class="bigger-120">
							${_sysConfig.schoolName}党委组织部
						</span>
					</div>
				</div>
			</div>

			<a href="javascript:;" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->
		<div id="modal" class="modal fade">
			<div class="modal-dialog" role="document" <%--style="min-width: 650px;"--%>>
				<div class="modal-content">
				</div>
			</div>
		</div>
	<script>
		function _refreshMenu(url) {
			// 处理左侧菜单
			$("#sidebar .nav-list li").removeClass("active").removeClass("open")
			$("#sidebar .nav-list li ul").removeClass("nav-show").css("display", "none")
			while ($("#sidebar .nav-list a[data-url='" + url + "']").length == 0) {
				var idx = url.lastIndexOf("&");
				if (idx == -1) {
					idx = url.indexOf("?");
					if (idx == -1) break;
				}
				url = url.substr(0, idx);
				//console.log(url)
			}
			//console.log(url + "  " + $(".nav-list a[href='"+url+"']").length)
			$("#sidebar .nav-list a[data-url='" + url + "']").parents("li").addClass("active open").children("ul").addClass("nav-show").css("display", "block");
			$("#sidebar .nav-list a[data-url='" + url + "']").closest("li").removeClass("open");
		}
		_refreshMenu('${_requestPath}')
	</script>
	</body>
</html>
