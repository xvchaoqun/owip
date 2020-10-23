<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="OW_APPLY_APPROVAL_LOG_STATUS_MAP" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_MAP%>"/>
<div class="col-xs-offset-1">
  <div class="widget-box widget-color-blue collapsed">
    <!-- #section:custom/widget-box.options -->
    <div class="widget-header">
      <h5 class="widget-title bigger lighter">
        <i class="fa fa-history"></i>
        审核记录（点击查看详情）
      </h5>
      <div class="widget-toolbar">
        <a href="javascript:;" data-action="collapse">
          <i class="ace-icon fa fa-chevron-down"></i>
        </a>
      </div>
    </div>

    <!-- /section:custom/widget-box.options -->
    <div class="widget-body">
      <div class="widget-main no-padding">
        <table class="table table-striped table-bordered table-hover table-center">
          <thead class="thin-border-bottom">
          <tr>
            <th>阶段</th>
            <th>操作人</th>
            <th>审核结果</th>
            <th>备注</th>
            <th>时间</th>
          </tr>
          </thead>

          <tbody>
          <c:forEach var="record" items="${applyApprovalLogs}">
          <tr>
            <td>${record.stage}</td>
            <td>${cm:getUserById(record.userId).realname}</td>
            <td>${OW_APPLY_APPROVAL_LOG_STATUS_MAP.get(record.status)}</td>
            <td class="text-left">${record.remark}</td>
            <td>${cm:formatDate(record.createTime,'yyyy-MM-dd HH:mm:ss')}</td>
          </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>
