<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-body" style="margin-top: -41px">
  <div class="widget-box transparent" id="view-box">
<shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
    <div class="widget-header">
      <h4 class="widget-title lighter smaller">
        &nbsp;
      </h4>
      <div class="widget-toolbar no-border">
        <ul class="nav nav-tabs">
            <li>
                <a href="javascript:;" data-url="${ctx}/sitemap_view">功能导航</a>
            </li>
          <li class="${(!cm:hasRole(ROLE_ADMIN) && !cm:hasRole(ROLE_ODADMIN))?'active':''}">
            <a href="javascript:;" data-url="${ctx}/user_base">个人基本信息</a>
          </li>
          <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN}">
          <li class="active">
              <a href="javascript:;" data-url="${ctx}/stat_member_page">党建统计信息</a>
          </li>
          <li>
              <a href="javascript:;" data-url="${ctx}/stat_sys_page">在线人数统计</a>
          </li>
          </shiro:hasAnyRoles>
        </ul>
      </div>
    </div>
</shiro:lacksRole>
    <div class="widget-body">
      <div class="widget-main padding-4">
        <div class="tab-content padding-8">
          <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN},${ROLE_ONLY_CADRE_VIEW}">
          <c:import url="/stat_member_page"/>
          </shiro:hasAnyRoles>
            <c:if test="${!cm:hasRole(ROLE_ADMIN) && !cm:hasRole(ROLE_ODADMIN) && !cm:hasRole(ROLE_ONLY_CADRE_VIEW)}">
          <c:import url="/user_base"/>
            </c:if>
        </div>
      </div><!-- /.widget-main -->
    </div><!-- /.widget-body -->
  </div><!-- /.widget-box -->
</div>
<div style="clear: both"></div>
