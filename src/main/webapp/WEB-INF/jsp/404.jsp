<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:if test="${!param.__includePage}">
    <jsp:include page="__includePage.jsp"/>
</c:if>
<div class="row">
    <div class="col-xs-12">
        <div class="error-container">
            <div class="well">
                <h1 class="grey lighter smaller">
					<span class="blue bigger-125">
						<i class="ace-icon fa fa-sitemap"></i>
						404
					</span>
                    页面不存在
                </h1>
                <hr/>
                <div class="space"></div>
                <div class="text-center">
                    <%--<a href="javascript:history.go(-1)" class="btn btn-success">
                        <i class="ace-icon fa fa-reply"></i>
                        返回
                    </a>--%>
                    <a href="${ctx}/" class="btn btn-primary">
                        <i class="ace-icon fa fa-home"></i>
                        回到首页
                    </a>
                    <shiro:user>
                        <fmt:message key="logout.redirectUrl" bundle="${spring}" var="_logout_redirectUrl"/>
                        <c:if test="${empty _logout_redirectUrl || _logout_redirectUrl=='/'}">
                            <c:set var="logoutUrl" value="${ctx}/logout"/>
                        </c:if>
                        <c:if test="${not empty _logout_redirectUrl && _logout_redirectUrl!='/'}">
                            <c:set var="logoutUrl" value="${_logout_redirectUrl}"/>
                        </c:if>
                        <a href="${logoutUrl}" class="btn btn-danger">
                            <i class="ace-icon fa fa-power-off"></i>
                            安全退出
                        </a>
                    </shiro:user>
                </div>
            </div>
        </div>
        <!-- /section:pages/error -->
        <!-- PAGE CONTENT ENDS -->
    </div><!-- /.col -->
</div>
<c:if test="${!param.__includePage}">
    </div>
    </body>
    </html>
</c:if>
