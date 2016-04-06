<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <meta charset="utf-8" />
  <title>北京师范大学组织工作一体化平台</title>

  <meta name="description" content="Mailbox with some customizations as described in docs" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

  <!-- bootstrap & fontawesome -->
  <link rel="stylesheet" href="${ctx}/assets/css/bootstrap.css" />
  <link rel="stylesheet" href="${ctx}/assets/css/font-awesome.css" />

  <!-- page specific plugin styles -->

  <!-- text fonts -->
  <link rel="stylesheet" href="${ctx}/assets/css/ace-fonts.css" />

  <!-- ace styles -->
  <link rel="stylesheet" href="${ctx}/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />

  <!--[if lte IE 9]>
  <link rel="stylesheet" href="${ctx}/assets/css/ace-part2.css" class="ace-main-stylesheet" />
  <![endif]-->

  <!--[if lte IE 9]>
  <link rel="stylesheet" href="${ctx}/assets/css/ace-ie.css" />
  <![endif]-->

  <!-- inline styles related to this page -->

  <!-- ace settings handler -->
  <script src="${ctx}/assets/js/ace-extra.js"></script>

  <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

  <!--[if lte IE 8]>
  <script src="${ctx}/assets/js/html5shiv.js"></script>
  <script src="${ctx}/assets/js/respond.js"></script>
  <![endif]-->

  <!-- basic scripts -->
  <!--[if !IE]> -->
  <script type="text/javascript">
    window.jQuery || document.write("<script src='${ctx}/assets/js/jquery.js'>"+"<"+"/script>");
  </script>
  <!-- <![endif]-->
  <!--[if IE]>
  <script type="text/javascript">
    window.jQuery || document.write("<script src='${ctx}/assets/js/jquery1x.js'>"+"<"+"/script>");
  </script>
  <![endif]-->
  <script type="text/javascript">
    if('ontouchstart' in document.documentElement) document.write("<script src='${ctx}/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
  </script>
  <script src="${ctx}/assets/js/bootstrap.js"></script>

  <!-- ace scripts -->
  <script src="${ctx}/assets/js/ace/ace.js"></script>
  <script src="${ctx}/assets/js/ace/ace.sidebar.js"></script>
  <script>
    var ctx="${ctx}";
  </script>

<script src="${ctx}/extend/js/jquery.showLoading.min.js"></script>
<script src="${ctx}/extend/js/bootbox.min.js"></script>
<script src="${ctx}/extend/js/prototype.js"></script>
<script src="${ctx}/extend/js/m/custom.js"></script>
<script src="${ctx}/extend/js/m/setup.js"></script>
