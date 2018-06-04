<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <c:set value="<%=ContentTplConstants.CONTENT_TPL_CET_MSG_MAP%>" var="CONTENT_TPL_CET_MSG_MAP"/>
    <h3>${CONTENT_TPL_CET_MSG_MAP.get(param.tplKey)}</h3>
</div>
<div class="modal-body">
    <c:if test="${commonList.recNum>0}">
        <table class="table table-actived table-striped table-bordered table-hover table-center">
            <thead>
            <tr>
                <th>姓名</th>
                <th>手机</th>
                <th>内容</th>
                <th>发送时间</th>
                <th>发送结果</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${cetShortMsgs}" var="cetShortMsg" varStatus="st">
                <c:set var="user" value="${cm:getUserById(cetShortMsg.userId)}"/>
                <tr>
                    <td>${empty user?'-':user.realname}</td>
                    <td>${cetShortMsg.mobile}</td>
                    <td title="${cetShortMsg.msg}">${cm:substr(cetShortMsg.msg, 0, 20, '...')}</td>
                    <td>${cm:formatDate(cetShortMsg.sendTime, "yyyy-MM-dd HH:mm:ss")}</td>
                    <td class="${cetShortMsg.success?"text-success":"text-danger"}">${cetShortMsg.success?"发送成功":"发送失败"}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${!empty commonList && commonList.pageNum>1 }">
            <wo:page commonList="${commonList}"
                     uri="${ctx}/cet/cetTrain_detail/msg_list?recordId=${param.recordId}&tplKey=${param.tplKey}"
                     target="#modal .modal-content"
                     pageNum="5"
                     model="3"/>
        </c:if>
    </c:if>
    <c:if test="${commonList.recNum==0}">
        <div class="well well-lg center">
            <h4 class="green lighter">暂无记录</h4>
        </div>
    </c:if>
</div>