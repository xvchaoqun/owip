<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pcs/constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>投票详情</h3>
</div>
<div class="modal-body">
    <div class="popTableDiv">
        <table class="table table-center table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th>序号</th>
                <th>推荐人姓名</th>
                <th>推荐人类型</th>
                <th width="100">得票</th>
                <c:if test="${stage!=PCS_POLL_FIRST_STAGE}">
                    <th width="100">另选推荐人</th>
                </c:if>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pcsPollResults}" var="result" varStatus="vs">
                <tr>
                    <td nowrap>${vs.index+1}</td>
                <c:if test="${stage==PCS_POLL_FIRST_STAGE}">
                    <c:set var="user" value="${cm:getUserById(result.userId)}"/>

                        <td nowrap>${user.realname}</td>
                        <td nowrap>${PCS_USER_TYPE_MAP.get(result.type)}</td>
                        <td nowrap>${result.status==1?1:0}</td>
                </c:if>
                <c:if test="${stage!=PCS_POLL_FIRST_STAGE}">
                    <c:set var="user" value="${cm:getUserById(result.candidateUserId)}"/>
                    <c:set var="otherUser" value="${cm:getUserById(result.userId)}"/>
                        <td nowrap>${user.realname}</td>
                        <td nowrap>${PCS_USER_TYPE_MAP.get(result.type)}</td>
                        <td nowrap>${result.status==1?1:0}</td>
                        <td nowrap>${user.id!=otherUser.id?otherUser.realname:'--'}</td>
                </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${fn:length(pcsPollResults)==0}">
            <div class="well well-lg center">
                <h4 class="green lighter">空票</h4>
            </div>
        </c:if>
    </div>
</div>
<script>
</script>