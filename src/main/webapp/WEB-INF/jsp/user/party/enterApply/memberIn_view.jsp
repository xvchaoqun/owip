<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row">
  <div class="col-xs-12">
    <!-- PAGE CONTENT BEGINS -->
    <div class="col-xs-offset-1 col-xs-10" style="padding-top: 50px">

      <div class="page-header">
        <h1>
          <i class="fa fa-check-square-o"></i>
          组织关系转入申请
        </h1>
      </div>
      <div class="col-xs-12">
        <div class="col-xs-6">
          <div class="profile-user-info profile-user-info-striped">
            <div class="profile-info-row">
              <div class="profile-info-name"> 介绍信抬头 </div>

              <div class="profile-info-value">
                <span class="editable" >${memberIn.fromTitle}</span>
              </div>
            </div>
            <div class="profile-info-row">
              <div class="profile-info-name"> 介绍信有效期天数 </div>

              <div class="profile-info-value">
                <span class="editable" >${memberIn.validDays}</span>
              </div>
            </div>
            <div class="profile-info-row">
              <div class="profile-info-name"> 类别 </div>

              <div class="profile-info-value">
                <span class="editable" >${MEMBER_INOUT_TYPE_MAP.get(memberIn.type)}</span>
              </div>
            </div>

            <div class="profile-info-row">
              <div class="profile-info-name">  ${(user.type==USER_TYPE_JZG)?"教工号":"学号"} </div>

              <div class="profile-info-value">
                <span class="editable">${user.code}</span>
              </div>
            </div>

            <div class="profile-info-row">
              <div class="profile-info-name">  姓名 </div>

              <div class="profile-info-value">
                <span class="editable">${user.realname}</span>
              </div>
            </div>

            <div class="profile-info-row">
              <div class="profile-info-name"> 转出单位 </div>

              <div class="profile-info-value">
                <span class="editable" >${memberIn.fromUnit}</span>
              </div>
            </div>
            <div class="profile-info-row">
              <div class="profile-info-name"> 转出单位地址 </div>

              <div class="profile-info-value">
                <span class="editable" >${memberIn.fromAddress}</span>
              </div>
            </div>
            <div class="profile-info-row">
              <div class="profile-info-name"> 转出单位联系电话 </div>

              <div class="profile-info-value">
                <span class="editable" >${memberIn.fromPhone}</span>
              </div>
            </div>
            <div class="profile-info-row">
              <div class="profile-info-name"> 转出单位传真 </div>

              <div class="profile-info-value">
                <span class="editable" >${memberIn.fromFax}</span>
              </div>
            </div>
            <div class="profile-info-row">
              <div class="profile-info-name"> 转出单位邮编 </div>
              <div class="profile-info-value">
                <span class="editable" >${memberIn.fromPostCode}</span>
              </div>
            </div>
          </div></div>
        <div class="col-xs-6"><div class="profile-user-info profile-user-info-striped">
          <div class="profile-info-row">
            <div class="profile-info-name"> 党籍状态 </div>

            <div class="profile-info-value">
              <span class="editable" >${MEMBER_POLITICAL_STATUS_MAP.get(memberIn.politicalStatus)}</span>
            </div>
          </div>
          <div class="profile-info-row">
            <div class="profile-info-name"> 党费缴纳至年月 </div>
            <div class="profile-info-value">
              <span class="editable" >${cm:formatDate(memberIn.payTime,'yyyy-MM')}</span>
            </div>
          </div>
          <div class="profile-info-row">
            <div class="profile-info-name"> 转出办理时间 </div>

            <div class="profile-info-value">
              <span class="editable" >${cm:formatDate(memberIn.fromHandleTime,'yyyy-MM-dd')}</span>
            </div>
          </div>
          <div class="profile-info-row">
            <div class="profile-info-name"> 转入办理时间 </div>
            <div class="profile-info-value">
              <span class="editable" >${cm:formatDate(memberIn.handleTime,'yyyy-MM-dd')}</span>
            </div>
          </div>

          <div class="profile-info-row">
            <div class="profile-info-name"> 提交书面申请书时间 </div>
            <div class="profile-info-value">
              <span class="editable" >${cm:formatDate(memberIn.applyTime,'yyyy-MM-dd')}</span>
            </div>
          </div>
          <div class="profile-info-row">
            <div class="profile-info-name"> 确定为入党积极分子时间 </div>
            <div class="profile-info-value">
              <span class="editable" >${cm:formatDate(memberIn.activeTime,'yyyy-MM-dd')}</span>
            </div>
          </div>
          <div class="profile-info-row">
            <div class="profile-info-name"> 确定为发展对象时间 </div>
            <div class="profile-info-value">
              <span class="editable" >${cm:formatDate(memberIn.candidateTime,'yyyy-MM-dd')}</span>
            </div>
          </div>
          <div class="profile-info-row">
            <div class="profile-info-name"> 入党时间 </div>
            <div class="profile-info-value">
              <span class="editable" >${cm:formatDate(memberIn.growTime,'yyyy-MM-dd')}</span>
            </div>
          </div>
          <div class="profile-info-row">
            <div class="profile-info-name"> 转正时间 </div>
            <div class="profile-info-value">
              <span class="editable" >${cm:formatDate(memberIn.positiveTime,'yyyy-MM-dd')}</span>
            </div>
          </div>
          <div class="profile-info-row">
            <div class="profile-info-name"> 申请转入组织机构 </div>

            <div class="profile-info-value">
                                    <span class="editable">
                                      ${cm:displayParty(memberIn.partyId, memberIn.branchId)}
                                    </span>
            </div>
          </div>
        </div></div>
      </div>
      <div style="padding-top: 50px">
        <ul class="steps">
          <li data-step="1" class="complete">
            <span class="step">0</span>
        <span class="title">申请已提交
        <c:if test="${memberIn.status==MEMBER_IN_STATUS_APPLY}">
          <small>
            <button class="btn btn-white btn-warning btn-xs" onclick="_applyBack()">
              <i class="fa fa-undo"></i>
              撤销
            </button>
          </small>
        </c:if>
      </span>
      <span class="subtitle">
        ${cm:formatDate(memberIn.createTime,'yyyy-MM-dd')}
      </span>
          </li>
          <c:if test="${memberIn.status==-1}">
            <li data-step="2" class="active">
              <span class="step">1</span>
              <span class="title">未通过申请</span>
            </li>
          </c:if>
          <li data-step="1" <c:if test="${memberIn.status>=MEMBER_IN_STATUS_PARTY_VERIFY}">class="complete"</c:if>>
            <span class="step">1</span>
            <span class="title">分党委审核</span>

          </li>
          <li data-step="2" <c:if test="${memberIn.status==MEMBER_IN_STATUS_OW_VERIFY}">class="complete"</c:if>>
            <span class="step">2</span>
            <span class="title">组织部审核</span>

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
