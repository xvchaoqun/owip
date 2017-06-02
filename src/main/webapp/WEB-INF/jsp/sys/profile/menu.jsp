<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-16">
  <li class="${type==1?"active":""}">
    <a href="#${ctx}/profile">
      <i class="green ace-icon fa fa-pencil-square-o bigger-125"></i>
      基本信息
    </a>
  </li>
  <%--<li class="${param.type==2?"active":""}">
    <a href="${ctx}/setting">
      <i class="purple ace-icon fa fa-cog bigger-125"></i>
      个人设置
    </a>
  </li>--%>
  <c:if test="${_user.source==USER_SOURCE_ADMIN||_user.source==USER_SOURCE_REG}">
    <shiro:hasPermission name="password:modify">
      <li class="${type==3?"active":""}">
        <a href="#${ctx}/profile?type=3">
          <i class="blue ace-icon fa fa-key bigger-125"></i>
          密码修改
        </a>
      </li>
    </shiro:hasPermission>
  </c:if>
  <shiro:hasPermission name="passportApply:*">
  <li class="${type==4?"active":""}">
    <a href="#${ctx}/profile?type=4">
      <i class="purple ace-icon fa fa-pencil  bigger-125"></i>
      手写签名
    </a>
  </li>
  </shiro:hasPermission>
</ul>
