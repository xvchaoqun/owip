<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="../constants.jsp" %>
<style>
    .backReason{
        font-size: 18px;
        color: black;
        font-weight: normal;
        text-align: left;
        margin: 10px 50px;
    }
</style>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>退回原因</h3>
</div>
<div class="modal-body">
    <div class="backReason">
        ${oaGridParty.backReason}
        <c:if test="${fn:length(oaGridParty.backReason)==0}">
            无退回原因
        </c:if>
    </div>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">确认</a>
</div>