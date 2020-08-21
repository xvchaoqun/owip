<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=PcsConstants.PCS_POLL_FIRST_STAGE%>" var="PCS_POLL_FIRST_STAGE"/>
<c:set value="<%=PcsConstants.PCS_POLL_SECOND_STAGE%>" var="PCS_POLL_SECOND_STAGE"/>
<c:set value="<%=PcsConstants.PCS_POLL_THIRD_STAGE%>" var="PCS_POLL_THIRD_STAGE"/>
<c:set value="<%=PcsConstants.PCS_POLL_CANDIDATE_TYPE%>" var="PCS_POLL_CANDIDATE_TYPE"/>
<c:set value="<%=PcsConstants.PCS_POLL_CANDIDATE_DW%>" var="PCS_POLL_CANDIDATE_DW"/>
<c:set value="<%=PcsConstants.PCS_POLL_CANDIDATE_JW%>" var="PCS_POLL_CANDIDATE_JW"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>投票详情</h3>
</div>
<div class="modal-body">
    <div class="popTableDiv">
        <table class="table table-actived table-striped table-bordered table-hover">
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
                        <td nowrap>${PCS_POLL_CANDIDATE_TYPE.get(result.type)}</td>
                        <td nowrap>${result.status==1?1:0}</td>
                </c:if>
                <c:if test="${stage!=PCS_POLL_FIRST_STAGE}">
                    <c:set var="user" value="${cm:getUserById(result.candidateUserId)}"/>
                    <c:set var="otherUser" value="${cm:getUserById(result.userId)}"/>
                    <tr>
                        <td nowrap><span style="${result.candidateUserId!=result.userId?'color:red':''}">${user.realname}</span></td>
                        <td nowrap>${PCS_POLL_CANDIDATE_TYPE.get(result.type)}</td>
                        <td nowrap>${result.status==1?1:0}</td>
                        <td nowrap>${user.id!=otherUser.id?otherUser.realname:'--'}</td>
                </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${fn:length(pcsPollResults)==0}">
            <div class="well well-lg center">
                <h4 class="green lighter">暂无投票记录</h4>
            </div>
        </c:if>
    </div>
</div>
<script>
</script>