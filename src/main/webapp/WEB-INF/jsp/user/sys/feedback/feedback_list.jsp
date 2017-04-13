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
                <div class="message-item message-unread">
                    <span class="sender" title="John Doe">
                            ${cm:formatDate(feedback.createTime, "yyyy-MM-dd")}
                    </span>

                    <span class="summary">
                        <span class="badge badge-pink mail-tag"></span>
                        <span class="text" title="${feedback.content}">
                                ${feedback.content}
                        </span>
                    </span>
                </div>
            </c:forEach>
        </div>
    </div>
    <wo:page commonList="${commonList}" uri="${ctx}/user/feedback_list" target="#feedbacks" pageNum="5"
             model="3"/>

    <%--<div class="message-footer clearfix">
        <div class="pull-left"> 总共${commonList.recNum}条记录</div>
        <div class="pull-right">
            <div class="inline middle"> 当前第 ${commonList.pageNo}/${commonList.pageNum} 页</div>
            &nbsp; &nbsp;
            <ul class="pagination middle">
                <li class="disabled">
                    <span>
                        <i class="ace-icon fa fa-step-backward middle"></i>
                    </span>
                </li>

                <li class="disabled">
                    <span>
                        <i class="ace-icon fa fa-caret-left bigger-140 middle"></i>
                    </span>
                </li>

                <li>
                        <span>
                            <input value="1" maxlength="3" type="text"/>
                        </span>
                </li>

                <li>
                    <a href="#">
                        <i class="ace-icon fa fa-caret-right bigger-140 middle"></i>
                    </a>
                </li>

                <li>
                    <a href="#">
                        <i class="ace-icon fa fa-step-forward middle"></i>
                    </a>
                </li>
            </ul>
        </div>
    </div>--%>

</div>
</c:if>