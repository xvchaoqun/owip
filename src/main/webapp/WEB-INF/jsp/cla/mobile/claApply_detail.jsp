<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>详情</h3>
</div>
<div class="modal-body">
  <div class="profile-user-info profile-user-info-striped">
    <div class="profile-info-row">
      <div class="profile-info-name td"> 序号 </div>

      <div class="profile-info-value td">
        <span class="editable">S${claApply.id}</span>
      </div>
    </div>

    <div class="profile-info-row">
      <div class="profile-info-name td"> 申请日期 </div>
      <div class="profile-info-value td">
        <span class="editable">${cm:formatDate(claApply.applyDate, "yyyy年 MM月 dd日")}</span>
      </div>
    </div>

    <div class="profile-info-row">
      <div class="profile-info-name td"> 出发时间 </div>

      <div class="profile-info-value td">
        <span class="editable">
          ${cm:formatDate(claApply.startTime, "yyyy年 MM月 dd日 HH:mm")}
        </span>
      </div>
    </div>

    <div class="profile-info-row">
      <div class="profile-info-name td"> 返校时间 </div>

      <div class="profile-info-value td">
        <span class="editable">${cm:formatDate(claApply.endTime, "yyyy年 MM月 dd日 HH:mm")}</span>
      </div>
    </div>

    <div class="profile-info-row">
      <div class="profile-info-name td"> 出行天数 </div>

      <div class="profile-info-value td">
        <span class="editable">${cm:getDayCountBetweenDate(claApply.startTime, claApply.endTime)}</span>
      </div>
    </div>

    <div class="profile-info-row">
      <div class="profile-info-name td"> 目的地 </div>

      <div class="profile-info-value td">
        <span class="editable">${claApply.destination}</span>
      </div>
    </div>
    <div class="profile-info-row">
      <div class="profile-info-name td"> 请假事由 </div>

      <div class="profile-info-value td">
        <span class="editable" >${claApply.reason}</span>
      </div>
    </div>
    <div class="profile-info-row">
      <div class="profile-info-name td"> 审批情况 </div>

      <div class="profile-info-value td">
        <c:set var="approvalTdBean" value="${cm:getMapValue(0, claApply.approvalTdBeanMap )}"/>
        <span class="editable" >${approvalTdBean.tdType<=4?'待审批':(approvalTdBean.tdType==6?'通过审批':'未通过审批')}</span>
      </div>
    </div>
    <c:if test="${approvalTdBean.tdType==6}">
      <div class="profile-info-row">
        <div class="profile-info-name td"> 销假情况 </div>
        <div class="profile-info-value td">
          <span class="editable" >${claApply.isBack?'已销假':'未销假'}</span>
        </div>
      </div>
      <c:if test="${claApply.isBack}">
      <div class="profile-info-row">
        <div class="profile-info-name td"> 实际出发时间 </div>

        <div class="profile-info-value td">
        <span class="editable">
            ${cm:formatDate(claApply.realStartTime, "yyyy年 MM月 dd日 HH:mm")}
        </span>
        </div>
      </div>
      <div class="profile-info-row">
        <div class="profile-info-name td"> 实际返校时间 </div>

        <div class="profile-info-value td">
          <span class="editable">${cm:formatDate(claApply.realEndTime, "yyyy年 MM月 dd日 HH:mm")}</span>
        </div>
      </div>
      <div class="profile-info-row">
        <div class="profile-info-name td"> 备注 </div>
        <div class="profile-info-value td">
          <span class="editable">${claApply.realRemark}</span>
        </div>
      </div>
      </c:if>
    </c:if>
  </div>
</div>
<div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>
<script>
  window.setTimeout(function() {
    $.adjustLeftFloatDivHeight($(".profile-info-name.td"))
  }, 200);
</script>