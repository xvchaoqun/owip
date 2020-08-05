<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>培训内容（${CET_PROJECT_PLAN_TYPE_MAP.get(cetProjectPlan.type)}）</h3>
</div>
<div class="modal-body">
    ${cetProjectPlan.summary}
</div>
<div class="modal-footer center">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>