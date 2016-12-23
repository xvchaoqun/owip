<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!-- inline scripts related to this page -->
<script src="${ctx}/assets/js/flot/jquery.flot.js"></script>
<script src="${ctx}/assets/js/flot/jquery.flot.pie.js"></script>
<script src="${ctx}/assets/js/flot/jquery.flot.resize.js"></script>
<script src="${ctx}/assets/js/ace/elements.scroller.js"></script>
<script src="${ctx}/extend/js/highcharts.js"></script>
<script src="${ctx}/extend/js/exporting.js"></script>
<div class="modal-body">
  <!-- PAGE CONTENT BEGINS -->
  <div class="widget-box transparent" id="view-box">
    <div class="widget-header">
      <h4 class="widget-title lighter smaller">
        &nbsp;
      </h4>
      <div class="widget-toolbar no-border">
        <ul class="nav nav-tabs">
          <li <shiro:lacksRole name="${ROLE_ADMIN}">
  <shiro:lacksRole name="${ROLE_ODADMIN}">
          class="active"
            </shiro:lacksRole>
            </shiro:lacksRole>
                  >
            <a href="javascript:" data-url="${ctx}/user_base">个人基本信息</a>
          </li>
          <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN}">
          <li class="active">
              <a href="javascript:" data-url="${ctx}/stat_member_page">党建统计信息</a>
          </li>
          <li>
              <a href="javascript:" data-url="${ctx}/stat_sys_page">系统统计信息</a>
          </li>
          </shiro:hasAnyRoles>
        </ul>
      </div>
    </div>
    <div class="widget-body">
      <div class="widget-main padding-4">
        <div class="tab-content padding-8">
          <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN}">
          <c:import url="/stat_member_page"/>
          </shiro:hasAnyRoles>
          <shiro:lacksRole name="${ROLE_ADMIN}">
            <shiro:lacksRole name="${ROLE_ODADMIN}">
          <c:import url="/user_base"/>
              </shiro:lacksRole>
           </shiro:lacksRole>
        </div>
      </div><!-- /.widget-main -->
    </div><!-- /.widget-body -->
  </div><!-- /.widget-box -->
</div>
