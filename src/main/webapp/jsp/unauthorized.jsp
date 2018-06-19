<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2015/11/11
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:if test="${!param.__includePage}">
<html>
<head>
    <title>没有权限访问</title>
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
						<i class="ace-icon fa fa-smile-o"></i>

					</span>
          您没有权限访问系统
        </h1>
        <hr />
        <div style="padding-left: 50px">
          <div class="space"></div>
          <ul class="list-unstyled spaced inline bigger-110 margin-15 ">
            <li>
              <h3><i class="ace-icon fa fa-arrow-right"></i> 请确认您的账号是否有权限访问系统</h3>
            </li>

          </ul>
        </div>
        <hr />
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
