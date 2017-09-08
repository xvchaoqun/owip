<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${party.name}-${param.type==1?"支部":(param.type==2?"推荐支部":"未推荐支部")}</h3>
</div>
<div class="modal-body">
    <div class="tip">
        <ul>
            <c:forEach var="branch" items="${branchs}">
                <li>${branch.name}</li>
            </c:forEach>
        </ul>
    </div>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-info">关闭</a></div>