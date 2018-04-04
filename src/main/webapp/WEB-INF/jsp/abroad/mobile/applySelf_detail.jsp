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
        <span class="editable">S${applySelf.id}</span>
      </div>
    </div>

    <div class="profile-info-row">
      <div class="profile-info-name td"> 申请日期 </div>
      <div class="profile-info-value td">
        <span class="editable">${cm:formatDate(applySelf.applyDate, "yyyy年 MM月 dd日")}</span>
      </div>
    </div>

    <div class="profile-info-row">
      <div class="profile-info-name td"> 出发时间 </div>

      <div class="profile-info-value td">
        <span class="editable">
          ${cm:formatDate(applySelf.startDate, "yyyy年 MM月 dd日")}
        </span>
      </div>
    </div>

    <div class="profile-info-row">
      <div class="profile-info-name td"> 回国时间 </div>

      <div class="profile-info-value td">
        <span class="editable">${cm:formatDate(applySelf.endDate, "yyyy年 MM月 dd日")}</span>
      </div>
    </div>

    <div class="profile-info-row">
      <div class="profile-info-name td"> 出行天数 </div>

      <div class="profile-info-value td">
        <span class="editable">${cm:getDayCountBetweenDate(applySelf.startDate, applySelf.endDate)}</span>
      </div>
    </div>

    <div class="profile-info-row">
      <div class="profile-info-name td"> 前往国家或地区 </div>

      <div class="profile-info-value td">
        <span class="editable">${applySelf.toCountry}</span>
      </div>
    </div>
    <div class="profile-info-row">
      <div class="profile-info-name td"> 出国（境）事由 </div>

      <div class="profile-info-value td">
        <span class="editable" >${fn:replace(applySelf.reason, "+++", ",")}</span>
      </div>
    </div>
    <div class="profile-info-row">
      <div class="profile-info-name td"> 审批情况 </div>

      <div class="profile-info-value td">
        <c:set var="approvalTdBean" value="${cm:getMapValue(0, applySelf.approvalTdBeanMap )}"/>
        <span class="editable" >${approvalTdBean.tdType<=4?'待审批':(approvalTdBean.tdType==6?'通过审批':'未通过审批')}</span>
      </div>
    </div>
  </div>
</div>
<div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>