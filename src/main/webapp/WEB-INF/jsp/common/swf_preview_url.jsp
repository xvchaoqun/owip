<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html>
<head>
    <title>${param.filename}</title>
</head>
<body>
<c:set value="${_uploadPath}${param.path}" var="path"/>
<c:if test="${empty param.path || !cm:exists(path)}">
    文件不存在：${param.path}
</c:if>
<c:if test="${not empty param.path && cm:exists(path)}">
    <div class="txt" id="flashContent">
        您还没有安装flash播放器。
        <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
                width="0" height="0"
                codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=10,0,0,0"></object>
    </div>

  <script type="text/javascript" src="<c:url value="/extend/flexpaper/js/swfobject/swfobject.js"/>"></script>
  <script>
    var swfVersionStr = "9.0.124";

    var xiSwfUrlStr = "${expressInstallSwf}";
    var flashvars = {
      SwfFile : escape("${ctx}/swf?path=${cm:encodeURI(param.path)}"),
      Scale : 0.6,
      ZoomTransition : "easeOut",
      ZoomTime : 0.5,
      ZoomInterval : 0.1,
      FitPageOnLoad : false,
      FitWidthOnLoad : true,
      PrintEnabled : false,
      FullScreenAsMaxWindow : false,
      localeChain: "zh_CN",
      ProgressiveLoading:true,
      SearchToolsVisible:false,
      ZoomToolsVisible :false
    };
    var params = {};
    params.quality = "high";
    params.bgcolor = "#ffffff";
    params.allowscriptaccess = "sameDomain";
    params.allowfullscreen = true;
    var attributes = {};
    attributes.id = "FlexPaperViewer";
    attributes.name = "FlexPaperViewer";
    attributes.wmode = "transparent"; // 解决FF下不显示问题
    swfobject.embedSWF(
            "${ctx}/extend/flexpaper/FlexPaperViewer.swf", "flashContent",
            "700", "800",
            swfVersionStr, xiSwfUrlStr,
            flashvars, params, attributes);
    swfobject.createCSS("#flashContent", "display:block;text-align:left;top:10px;");

  </script>
</c:if>
</body>
</html>
