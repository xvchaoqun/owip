<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="LOGIN_TYPE_WX" value="<%=SystemConstants.LOGIN_TYPE_WX%>"/>
<c:if test="${!param.__includePage}">
    <jsp:include page="/WEB-INF/jsp/__includePage.jsp"/>
</c:if>
<div class="row">
    <div class="col-xs-12">
        <div class="error-container">
            <div class="well">
                <h3 class="grey lighter smaller">
					<span class="blue">
						<i class="ace-icon fa fa-smile-o"></i>
					</span>
                    您没有权限访问系统
                </h3>
                <hr/>
                <div>
                    <div class="space"></div>
                    <ul class="list-unstyled spaced inline">
                        <li>
                            <i class="ace-icon fa fa-arrow-right"></i> 请确认您的账号(${param.username}<c:if test="${empty param.username}"><shiro:principal property="code"/></c:if>)是否有访问权限
                        </li>
                    </ul>
                </div>
                <hr/>
                <div class="space"></div>
                <c:if test="${sessionScope._loginType==LOGIN_TYPE_WX}">
                     <shiro:user>
                        <div class="text-center">
                            <a href="${ctx}/" class="btn btn-primary">
                                <i class="ace-icon fa fa-home"></i>
                                回到首页
                            </a>
                        </div>
                    </shiro:user>
                </c:if>
                <c:if test="${sessionScope._loginType!=LOGIN_TYPE_WX}">
                <div class="text-center">
                    <a href="${ctx}/" class="btn btn-primary">
                        <i class="ace-icon fa fa-home"></i>
                        回到首页
                    </a>
                    <fmt:message key="logout.redirectUrl" bundle="${spring}" var="_logout_redirectUrl"/>
                    <c:if test="${empty _logout_redirectUrl || _logout_redirectUrl=='/'}">
                        <c:set var="logoutUrl" value="${ctx}/logout"/>
                    </c:if>
                    <c:if test="${not empty _logout_redirectUrl && _logout_redirectUrl!='/'}">
                        <c:set var="logoutUrl" value="${_logout_redirectUrl}"/>
                    </c:if>
                    <a href="${logoutUrl}" class="btn btn-success">
                        <i class="ace-icon fa fa-power-off"></i>
                        安全退出
                    </a>
                </div>
                </c:if>
            </div>
        </div>
    </div><!-- /.col -->
</div>
<c:if test="${!param.__includePage}">
    </div>
    </body>
    </html>
</c:if>
