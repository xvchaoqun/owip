<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-16">
  <li class="${param.type==1?"active":""}">
    <a href="${ctx}/profile">
      <i class="green ace-icon fa fa-pencil-square-o bigger-125"></i>
      基本信息
    </a>
  </li>

  <li class="${param.type==2?"active":""}">
    <a href="${ctx}/setting">
      <i class="purple ace-icon fa fa-cog bigger-125"></i>
      个人设置
    </a>
  </li>
  <c:if test="${_user.source==USER_SOURCE_ADMIN}">
    <shiro:hasPermission name="password:modify">
      <li class="${param.type==3?"active":""}">
        <a href="${ctx}/password">
          <i class="blue ace-icon fa fa-key bigger-125"></i>
          密码修改
        </a>
      </li>
    </shiro:hasPermission>
  </c:if>
</ul>
