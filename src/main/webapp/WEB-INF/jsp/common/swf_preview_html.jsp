<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<fmt:message key="upload.path" bundle="${spring}" var="_uploadPath"/>
<c:set value="${_uploadPath}${param.path}" var="path"/>
<c:set var="exists" value="${not empty param.path && cm:exists(path)}"/>
<c:if test="${!exists}">
    <div class="swf-file none well">
            ${empty param.path?"请上传文件":"文件不存在"}${param.path}
    </div>
</c:if>
<c:if test="${exists}">
    <div class="swf-file flash">
        <div id="flashContent">
            该功能需要安装flashplayer 10或更高版本，请 <a href="http://get.adobe.com/flashplayer">点击此处</a> 打开允许运行flash或免费下载安装flash
            <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
                    width="0" height="0"
                    codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=10,0,0,0"></object>
        </div>
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
        params.wmode = 'opaque';
        var attributes = {};
        attributes.id = "FlexPaperViewer";
        attributes.name = "FlexPaperViewer";
        attributes.wmode = "transparent"; // 解决FF下不显示问题
        swfobject.embedSWF(
                "${ctx}/extend/flexpaper/FlexPaperViewer.swf", "flashContent",
                "710", "750",
                swfVersionStr, xiSwfUrlStr,
                flashvars, params, attributes);
        swfobject.createCSS("#flashContent", "display:block;text-align:left;top:10px;");

    </script>
</c:if>