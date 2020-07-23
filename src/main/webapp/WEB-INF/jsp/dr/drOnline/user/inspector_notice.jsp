<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header" align="center">
    <h3>推荐说明</h3>
</div>
<div style="width:70%; margin:0 auto; padding-top: 10px">
    <div class="modal-body" style="text-align: left;word-wrap:break-word">
        ${notice}
    </div>
</div>
<div class="modal-footer">
    <button class="btn btn-default" data-dismiss="modal" aria-hidden="true"><i class="fa fa-times"></i> 关闭</button>
</div>