<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:if test="${commonList.recNum>0}">
<div class="message-container">
    <div id="id-message-list-navbar" class="message-navbar clearfix">
        <div class="message-bar">
            <div class="message-infobar" id="id-message-infobar">
                <span class="blue bigger-150">意见与建议</span>
                <span class="grey bigger-110"></span>
            </div>
        </div>
    </div>
    <div class="message-list-container">
        <div class="message-list" id="message-list">
            <c:forEach items="${feedbacks}" var="feedback">
                <div class="message-item message-unread" style="padding: 5px 10px 5px">
                    <span class="sender">
                            ${cm:formatDate(feedback.createTime, "yyyy-MM-dd")}
                    </span>
                    <span class="summary">
                        <span class="badge badge-pink mail-tag"></span>
                        <span class="text" title="${feedback.content}">
                                ${feedback.title}
                        </span>
                    </span>
                    <span class="reply" style="float: right">
                        <button class="openView btn ${feedback.replyCount>0?'btn-success':'btn-primary'} btn-xs"
                                data-url="${ctx}/feedback_detail?id=${feedback.id}">
                            <i class="fa fa-search"></i> 详情<c:if test="${feedback.replyCount>0}">(${feedback.replyCount})</c:if></button>
                         <c:if test="${feedback.selfCanEdit}">
                            <a class="popupBtn btn btn-primary btn-xs"
                               data-url="${ctx}/feedback_au?id=${feedback.id}&isNotReply=1"><i class="fa fa-edit"></i></a>
                             <button data-url="${ctx}/feedback_del?id=${feedback.id}"
                                            data-title="删除"
                                            data-msg="确定删除？"
                                            data-callback="_reloadList"
                                            class="confirm btn btn-danger btn-xs">
                                             <i class="fa fa-times"></i></button>
                        </c:if>
                    </span>
                </div>
            </c:forEach>
        </div>
    </div>
    <wo:page commonList="${commonList}" uri="${ctx}/user/feedback_list" target="#feedbacks" pageNum="5"
             model="3"/>
</div>
</c:if>
<script>
    function _reloadList(){
        $("#feedbacks").load("${ctx}/user/feedback_list?${pageContext.request.queryString}")
    }
</script>