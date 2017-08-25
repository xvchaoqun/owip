<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:if test="${fn:length(sysConfigLoginMsgs)>0}">
    <div class="alert alert-success">
        常用公告：
        <c:forEach items="${sysConfigLoginMsgs}" var="msg">
            <div class="buttons">
                <a class="popupBtn btn btn-link" href="javascript:;"
                   data-width="750"
                   data-url="${ctx}/sysConfigLoginMsg_au?id=${msg.id}">${msg.name}</a>
                    ${cm:formatDate(msg.createTime, "yyyy-MM-dd HH:mm:ss")}
                <a class="confirm btn btn-link" href="javascript:;"
                   data-url="${ctx}/sysConfigLoginMsg_del?id=${msg.id}"
                   data-title="删除"
                   data-msg="确定删除该公告吗？"
                   data-callback="_reloadLoginMsg">
                    <i class="fa fa-trash"></i> 删除
                </a>
            </div>
        </c:forEach>
    </div>
</c:if>
