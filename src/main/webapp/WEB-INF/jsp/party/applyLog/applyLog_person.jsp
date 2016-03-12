<%@ page import="sys.constants.SystemConstants" %>
<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2015/12/9
  Time: 19:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="APPLY_STAGE_MAP" value="<%=SystemConstants.APPLY_STAGE_MAP%>"/>
<div class="modal-body">
  <!-- PAGE CONTENT BEGINS -->
  <div class="widget-box transparent" id="view-box">
    <div class="widget-header">
      <h4 class="widget-title lighter smaller">
        <a href="javascript:;" class="closeView btn btn-mini btn-xs btn-success">
          <i class="ace-icon fa fa-backward"></i>
          返回</a>
      </h4>
      <div class="widget-toolbar no-border">
        <ul class="nav nav-tabs">
          <li class="active">
            <a href="javascript:;" data-url="${ctx}/applyLog_person?userId=${param.userId}">操作记录</a>
          </li>
        </ul>
      </div>
    </div>
    <div class="widget-body">
      <div class="widget-main padding-4">
        <div class="tab-content padding-8">
          <table class="table table-actived table-striped table-bordered table-hover table-condensed">
            <thead>
            <tr>
              <th>申请人</th>
              <th>操作人</th>
              <th>当前阶段</th>
              <th>操作内容</th>
              <th>IP</th>
              <th>时间</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${applyLogs}" var="applyLog" varStatus="st">
              <tr>
                <td>${cm:getUserById(applyLog.userId).realname}</td>
                <td>${cm:getUserById(applyLog.operatorId).realname}</td>
                <td>${APPLY_STAGE_MAP.get(applyLog.stage)}</td>
                <td>${applyLog.content}</td>
                <td>${applyLog.ip}</td>
                <td>${cm:formatDate(applyLog.createTime,'yyyy-MM-dd HH:mm:ss')}</td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
        </div>
      </div><!-- /.widget-main -->
    </div><!-- /.widget-body -->
  </div><!-- /.widget-box -->
</div>
