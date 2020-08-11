<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>结业证书</h3>
</div>
<div class="modal-body">
    <div class="imgDiv">
        <img width="800"
          src="${ctx}/report/cet_cert?sourceType=${param.sourceType}&ids=${cm:sign(param.ids)}&format=image&_=<%=System.currentTimeMillis()%>"/>
    </div>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-url="${ctx}/report/cet_cert?sourceType=${param.sourceType}&ids=${param.ids}&format=pdf&download=1&filename=结业证书&_=<%=System.currentTimeMillis()%>"
           class="downloadBtn btn btn-success" data-type="download"><i class="fa fa-download"></i> 下载</a>
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>
<style>
    .imgDiv{
        background-image: url('${ctx}/img/loading.gif');
        background-repeat: no-repeat;
        background-position:center;
        width: 800px;
        height: 566px;
    }
</style>