<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:if test="${empty dispatch || empty filePath}">
    <div class="modal-header">
        <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
        <h3>提示</h3>
    </div>
    <div class="modal-body">
        没有文件
    </div>
    <div class="modal-footer">
        <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
    </div>
</c:if>
<c:if test="${not empty dispatch && not empty filePath}">
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${dispatch.year}-${dispatch.code}</h3>
  </div>
  <div class="modal-body">
	   <div class="txt" id="flashContent">
	   </div>
  </div>
  <div class="modal-footer">
      <a href="/dispatch_download?id=${dispatch.id}&type=file" class="btn btn-success" target="_blank"><i class="fa fa-download"></i> 下载</a>
  <a href="#" data-dismiss="modal" class="btn btn-default"><i class="fa fa-reply"></i> 关闭</a>
  </div>
  <style>
	.modal-body{
		max-height:none;
	}
	.modal-content{
		width: 742px;
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
	 var params = {}
      params.quality = "high";
      params.bgcolor = "#ffffff";
      params.allowscriptaccess = "sameDomain";
      params.allowfullscreen = true;
      var attributes = {};
      attributes.id = "FlexPaperViewer";
      attributes.name = "FlexPaperViewer";
      swfobject.embedSWF(
          "${ctx}/extend/flexpaper/FlexPaperViewer.swf", "flashContent",
          "710", "600", 
          swfVersionStr, xiSwfUrlStr, 
          flashvars, params, attributes);
	 swfobject.createCSS("#flashContent", "display:block;text-align:left;top:10px;");

</script>
    </c:if>