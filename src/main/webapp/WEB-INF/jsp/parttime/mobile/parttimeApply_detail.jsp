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
        <span class="editable">S${parttimeApply.id}</span>
      </div>
    </div>

    <div class="profile-info-row">
      <div class="profile-info-name td"> 申请日期 </div>
      <div class="profile-info-value td">
        <span class="editable">${cm:formatDate(parttimeApply.applyTime, "yyyy年 MM月 dd日")}</span>
      </div>
    </div>

    <div class="profile-info-row">
      <div class="profile-info-name td"> 兼职开始时间 </div>

      <div class="profile-info-value td">
        <span class="editable">
          ${cm:formatDate(parttimeApply.startTime, "yyyy年 MM月 dd日 HH:mm")}
        </span>
      </div>
    </div>

    <div class="profile-info-row">
      <div class="profile-info-name td"> 兼职结束时间 </div>

      <div class="profile-info-value td">
        <span class="editable">
          ${cm:formatDate(parttimeApply.endTime, "yyyy年 MM月 dd日 HH:mm")}
        </span>
      </div>
    </div>

    <div class="profile-info-row">
      <div parttimess="profile-info-name td"> 首次/连任 </div>

      <div class="profile-info-value td">
        <span class="editable">${parttimeApply.isFirst?'首次':'连任'}</span>
      </div>
    </div>

    <div class="profile-info-row">
      <div class="profile-info-name td"> 是否有国境外背景 </div>

      <div class="profile-info-value td">
        <span class="editable">${parttimeApply.background?'是':'否'}</span>
      </div>
    </div>

    <div class="profile-info-row">
      <div class="profile-info-name td"> 是否取酬 </div>

      <div class="profile-info-value td">
        <span class="editable">${parttimeApply.hasPay?'是':'否'}</span>
      </div>
    </div>
    <div class="profile-info-row">
      <div class="profile-info-name td"> 取酬金额 </div>

      <div class="profile-info-value td">
        <span class="editable" >${parttimeApply.balance}</span>
      </div>
    </div>
    <div class="profile-info-row">
      <div class="profile-info-name td"> 审批情况 </div>

      <div class="profile-info-value td">
        <c:set var="approvalTdBean" value="${cm:getMapValue(0, parttimeApply.approvalTdBeanMap )}"/>
        <span class="editable" >${approvalTdBean.tdType<=4?'待审批':(approvalTdBean.tdType==6?'通过审批':'未通过审批')}</span>
      </div>
    </div>
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