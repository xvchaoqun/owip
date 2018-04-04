<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="profile-user-info profile-user-info-striped">
  <div class="profile-info-row">
    <div class="profile-info-name td"> 姓名 </div>

    <div class="profile-info-value td">
      <span class="editable">${_user.realname}</span>
    </div>
  </div>
  <div class="profile-info-row">
    <div class="profile-info-name td"> ${_user.type==USER_TYPE_JZG?'工作证号':'学生证号'} </div>
    <div class="profile-info-value td">
      <span class="editable">${_user.code}</span>
    </div>
  </div>

  <div class="profile-info-row">
    <div class="profile-info-name td"> 性别 </div>

    <div class="profile-info-value td">
      <span class="editable">${GENDER_MAP.get(_user.gender)}</span>
    </div>
  </div>

  <div class="profile-info-row">
    <div class="profile-info-name td"> 出生年月 </div>

    <div class="profile-info-value td">
      <span class="editable">${cm:formatDate(_user.birth,'yyyy-MM-dd')}</span>
    </div>
  </div>

  <div class="profile-info-row">
    <div class="profile-info-name td"> 身份证号 </div>

    <div class="profile-info-value td">
      <span class="editable">${_user.idcard}</span>
    </div>
  </div>

  <div class="profile-info-row">
    <div class="profile-info-name td"> 联系电话 </div>

    <div class="profile-info-value td">
      <span class="editable">${_user.mobile}</span>
    </div>
  </div>
</div>
