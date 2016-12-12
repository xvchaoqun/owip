<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:if test="${empty dispatch || empty filePath}">
    <div class="modal-header">
        <button type="button" onclick="closeSwfPreview(0)" aria-hidden="true" class="close">&times;</button>
        <h3>提示</h3>
    </div>
    <div class="modal-body">
        没有文件
    </div>
    <div class="modal-footer">
        <a href="#" onclick="closeSwfPreview()" class="btn btn-default">返回</a>
    </div>
</c:if>
<c:if test="${not empty dispatch && not empty filePath}">
  <div class="modal-header">
    <button type="button" onclick="closeSwfPreview(0)" aria-hidden="true" class="close">&times;</button>
    <h3>${cm:getDispatchCode(dispatch.code, dispatch.dispatchTypeId, dispatch.year)}</h3>
  </div>
  <div class="modal-body">
	   <div class="txt" id="flashContent">
           您还没有安装flash播放器。
           <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
                   width="0" height="0"
                   codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=10,0,0,0"></object>
	   </div>
  </div>
  <div class="modal-footer">
  <a href="#" onclick="closeSwfPreview()" class="btn btn-default">返回</a>
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
            SwfFile : escape("${ctx}/dispatch_swf?id=${dispatch.id}&type=${param.type}"),
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