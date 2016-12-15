<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row footer-margin">
  <div class="col-xs-12">
    <!-- PAGE CONTENT BEGINS -->
    <div class="col-xs-offset-1 col-xs-10" style="padding-top: 50px">

      <div class="page-header">
        <h1>
          <i class="fa fa-check-square-o"></i>
          流入党员申请信息
        </h1>
      </div>
      <div class="profile-user-info profile-user-info-striped">
        <div class="profile-info-row">
          <div class="profile-info-name">  ${(user.type==USER_TYPE_JZG)?"教工号":"学号"} </div>

          <div class="profile-info-value">
            <span class="editable">${user.code}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 分党委 </div>

          <div class="profile-info-value">
            <span class="editable" id="signup">${cm:displayParty(memberInflow.partyId, null)}</span>
          </div>
        </div>
        <c:if test="${memberInflow.branchId>0}">
          <div class="profile-info-row">
            <div class="profile-info-name"> 党支部 </div>

            <div class="profile-info-value">
              <span class="editable" id="login">${cm:displayParty(null, memberInflow.branchId)}</span>
            </div>
          </div>
        </c:if>

        <div class="profile-info-row">
          <div class="profile-info-name"> 原职业 </div>

          <div class="profile-info-value">
            <span class="editable" >${jobMap.get(memberInflow.originalJob).name}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 流入前所在省份 </div>

          <div class="profile-info-value">
            <span class="editable" >${locationMap.get(memberInflow.province).name}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 是否持有《中国共产党流动党员活动证》 </div>

          <div class="profile-info-value">
            <span class="editable" >${memberInflow.hasPapers?"有":"无"}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 流入时间 </div>

          <div class="profile-info-value">
            <span class="editable" >${cm:formatDate(memberInflow.flowTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 流入原因 </div>

          <div class="profile-info-value">
            <span class="editable" >${memberInflow.flowReason}</span>
          </div>
        </div>

        <div class="profile-info-row">
          <div class="profile-info-name"> 入党时间 </div>

          <div class="profile-info-value">
            <span class="editable">${cm:formatDate(memberInflow.growTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 组织关系所在地 </div>

          <div class="profile-info-value">
            <span class="editable">${memberInflow.orLocation}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 提交时间 </div>

          <div class="profile-info-value">
            <span class="editable" >${cm:formatDate(memberInflow.createTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
      </div>
      <div style="padding-top: 50px">
        <ul class="steps">
          <li data-step="1" class="complete">
            <span class="step">0</span>
        <span class="title">申请已提交
        <c:if test="${memberInflow.inflowStatus==MEMBER_INFLOW_STATUS_APPLY}">
          <small>
            <button class="btn btn-white btn-warning btn-xs" onclick="_applyBack()">
              <i class="fa fa-undo"></i>
              撤销
            </button>
          </small>
        </c:if>
      </span>
      <span class="subtitle">
        ${cm:formatDate(memberInflow.createTime,'yyyy-MM-dd')}
      </span>
          </li>
          <c:if test="${memberInflow.inflowStatus==-1}">
            <li data-step="2" class="active">
              <span class="step">1</span>
              <span class="title">未通过申请</span>
            </li>
          </c:if>
<c:if test="${memberInflow.branchId>0}">
          <li data-step="1" <c:if test="${memberInflow.inflowStatus>0}">class="complete"</c:if>>
            <span class="step">1</span>
            <span class="title">党支部审核</span>

          </li>
  </c:if>
          <li data-step="2" <c:if test="${memberInflow.inflowStatus>1}">class="complete"</c:if>>
            <span class="step">2</span>
            <span class="title">分党委审核</span>

          </li>
        </ul>
      </div>
    </div>
  </div>
</div>
<script>
  function _applyBack(){
    bootbox.confirm("确定撤销申请吗？", function (result) {
      if(result){
        $.post("${ctx}/user/applyBack",function(ret){

          if(ret.success){
            bootbox.alert("撤销成功。",function(){
              location.reload();
            });
          }
        });
      }
    });
  }
</script>
