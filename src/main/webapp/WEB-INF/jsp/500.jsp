<%@ page import="org.apache.shiro.session.UnknownSessionException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%
  //exception.printStackTrace(response.getWriter());
  if(exception.getCause() instanceof UnknownSessionException){
    //ShiroHelper.kickOutUser(ShiroHelper.getCurrentUsername());
    response.sendRedirect("/");
  }
%>
<c:if test="${!param.__includePage}">
<html>
<head>
    <title>系统异常</title>
  <link rel="stylesheet" href="${ctx}/assets/css/bootstrap.css" />
  <link rel="stylesheet" href="${ctx}/assets/css/font-awesome.css" />
</head>
<body>
<div style="padding-top: 100px"></div>
<div class="container">
  </c:if>
<div class="row">
  <div class="col-xs-12">
    <div class="error-container">
      <div class="well">
        <h1 class="grey lighter smaller">
					<span class="blue bigger-125">
						<i class="ace-icon fa fa-sitemap"></i>
						500
					</span>
          系统异常
        </h1>
        <hr />
        <div style="/*padding-left: 50px*/">
          <div class="space"></div>
          <ul class="list-unstyled spaced inline bigger-110 margin-15 ">

            <li style="color: red;font-weight: bolder">${exception}</li>
            <li>
              <h3><i class="ace-icon fa fa-hand-o-right blue"></i> 系统内部错误，请稍后再试</h3>
            </li>

          </ul>
        </div>
        <hr />
        <div class="space"></div>
        <div class="center">

          <a href="javascript:history.go(-1)" class="btn btn-primary">
            <i class="ace-icon fa fa-reply"></i>
            返回
          </a>

          <%--<a href="${ctx}/" class="btn btn-primary">
            <i class="ace-icon fa fa-home"></i>
            回到首页
          </a>--%>

          <a href="logout" class="btn btn-success">
            <i class="ace-icon fa fa-power-off"></i>
            安全退出
          </a>
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
