<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>校内组织关系转接详情</h3>
</div>
<div class="modal-body">
   <table class="table table-bordered table-striped">
       <jsp:include page="memberTransfer_table.jsp"/>
    </table>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>