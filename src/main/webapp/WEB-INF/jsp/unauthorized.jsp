<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2015/11/11
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>没有权限访问</title>
  <link rel="stylesheet" href="assets/css/bootstrap.css" />
  <link rel="stylesheet" href="assets/css/font-awesome.css" />
</head>
<body>
<div style="padding-top: 100px"></div>
<div class="container">
<div class="row">
  <div class="col-xs-12">
    <!-- PAGE CONTENT BEGINS -->
    <!-- #section:pages/error -->
    <div class="error-container">
      <div class="well">
        <h1 class="grey lighter smaller">
					<span class="blue bigger-125">
						<i class="ace-icon fa fa-sitemap"></i>
						401
					</span>
          您没有权限访问此页面
        </h1>
        <hr />
        <div style="padding-left: 50px">
          <div class="space"></div>
          <ul class="list-unstyled spaced inline bigger-110 margin-15 ">
            <li>
              <h3><i class="ace-icon fa fa-hand-o-right blue"></i> 请确认访问地址是否正确</h3>
            </li>

            <li>
              <h3><i class="ace-icon fa fa-hand-o-right blue"></i> 请联系系统管理员</h3>
            </li>

          </ul>
        </div>
        <hr />
        <div class="space"></div>
        <div class="center">
          <a href="javascript:history.back()" class="btn btn-grey">
            <i class="ace-icon fa fa-arrow-left"></i>
            返回
          </a>
          <a href="/" class="btn btn-primary">
            <i class="ace-icon fa fa-home"></i>
            回到首页
          </a>
        </div>
      </div>
    </div>

    <!-- /section:pages/error -->

    <!-- PAGE CONTENT ENDS -->
  </div><!-- /.col -->
</div>
  </div>
</body>
</html>
