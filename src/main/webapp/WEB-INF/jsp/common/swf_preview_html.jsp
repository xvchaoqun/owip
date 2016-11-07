<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
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
        </div>
    </div>
    <script type="text/javascript" src="<c:url value="/extend/flexpaper/js/swfobject/swfobject.js"/>"></script>
    <script>
        var swfVersionStr = "9.0.124";

        var xiSwfUrlStr = "${expressInstallSwf}";
        var flashvars = {
            SwfFile : escape("${ctx}/swf?path=${fn:replace(param.path, '\\', '\\\\')}"),
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
                "710", "750",
                swfVersionStr, xiSwfUrlStr,
                flashvars, params, attributes);
        swfobject.createCSS("#flashContent", "display:block;text-align:left;top:10px;");

    </script>
</c:if>