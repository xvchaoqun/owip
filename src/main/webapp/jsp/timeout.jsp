<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:if test="${!param.__includePage}">
<html>
<head>
    <title>登录超时</title>
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
						<i class="ace-icon fa fa-unlock"></i>

					</span>
          登录超时，请重新登录
        </h1>
        <hr />
        <%--<div style="padding-left: 50px">
          <div class="space"></div>
          <ul class="list-unstyled spaced inline bigger-110 margin-15 ">
            <li>
              <h3><i class="ace-icon fa fa-arrow-right"></i> 请确认您的账号(${param.username})是否有访问权限</h3>
            </li>
          </ul>
        </div>--%>
        <hr />
        <div class="space"></div>
        <div class="center">
          <a href="${ctx}/" class="btn btn-success">
            <i class="ace-icon fa fa-refresh"></i>
            重新登录
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
