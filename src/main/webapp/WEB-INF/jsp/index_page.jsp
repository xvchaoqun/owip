<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-body">
  <!-- PAGE CONTENT BEGINS -->
  <div class="widget-box transparent" id="view-box">
    <div class="widget-header">
      <h4 class="widget-title lighter smaller">
        &nbsp;
      </h4>
      <div class="widget-toolbar no-border">
        <ul class="nav nav-tabs">
          <li class="active">
            <a href="javascript:;" data-url="${ctx}/user_base">个人基本信息</a>
          </li>
          <shiro:hasAnyRoles name="admin, odAdmin">
          <li>
              <a href="javascript:;" data-url="${ctx}/stat_page">党建统计信息</a>
          </li>
          </shiro:hasAnyRoles>
        </ul>
      </div>
    </div>
    <div class="widget-body">
      <div class="widget-main padding-4">
        <div class="tab-content padding-8">
          <c:import url="/user_base"/>
        </div>
      </div><!-- /.widget-main -->
    </div><!-- /.widget-body -->
  </div><!-- /.widget-box -->
</div>
