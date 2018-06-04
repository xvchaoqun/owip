<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<fmt:message key="upload.path" bundle="${spring}" var="_uploadPath"/>
<c:set value="${_uploadPath}${path}" var="_path"/>
<c:if test="${empty path || !cm:exists(_path)}">
    <div class="modal-header">
        <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
        <h3>提示</h3>
    </div>
    <div class="modal-body">
        文件不存在：${path}
    </div>
    <div class="modal-footer">
        <a href="javascript:;" data-dismiss="modal" class="btn btn-default">关闭</a>
    </div>
</c:if>
<c:if test="${not empty path && cm:exists(_path)}">
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>
        ${filename}
    </h3>
  </div>
  <div class="modal-body">
	   <div class="txt" id="flashContent">
           该功能需要安装flashplayer 10或更高版本，请 <a href="http://get.adobe.com/flashplayer">点击此处</a> 打开允许运行flash或免费下载安装flash
           <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
                   width="0" height="0"
                   codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=10,0,0,0"></object>
	   </div>
  </div>
  <div class="modal-footer">
      <c:if test="${!np}">
      <a href="javascript:;" data-dismiss="modal" class="pirntBtn btn btn-info"
         data-url="${ctx}/pdf?path=${cm:encodeURI(path)}"><i class="fa fa-print"></i> 打印</a>
      </c:if>
      <c:if test="${!nd}">
      <a href="${ctx}/attach/download?path=${cm:encodeURI(path)}&filename=${filename}"
      class="btn btn-success"><i class="fa fa-download"></i> 下载</a>
      </c:if>
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default"><i class="fa fa-times"></i> 关闭</a>
  </div>
  <style>
      .modal .modal-body{
		max-height:none!important;
	}
	.modal-content{
		width: 742px!important;
	}
</style>
  <script type="text/javascript" src="<c:url value="/extend/flexpaper/js/swfobject/swfobject.js"/>"></script>
  <script>
  	var swfVersionStr = "9.0.124";

 	 var xiSwfUrlStr = "${expressInstallSwf}";
      var flashvars = { 
            SwfFile : escape("${ctx}/swf?path=${cm:encodeURI(path)}"),
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
          "710", "600", 
          swfVersionStr, xiSwfUrlStr, 
          flashvars, params, attributes);
	 swfobject.createCSS("#flashContent", "display:block;text-align:left;top:10px;");

</script>
    </c:if>