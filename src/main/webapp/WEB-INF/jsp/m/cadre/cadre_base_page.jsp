<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
  <div class="col-xs-12">
    <div class="col-xs-12">
      <div class="alert alert-block alert-success">
        <i class="ace-icon fa fa-check green"></i>
        欢迎使用组织工作管理与服务一体化平台
      </div>
    </div>
    <div class="profile-user-info profile-user-info-striped">
      <div class="profile-info-row">
        <div class="profile-info-name td"> 姓名 </div>

        <div class="profile-info-value td">
          <span class="editable">${_user.realname}</span>
        </div>
      </div>

      <div class="profile-info-row">
        <div class="profile-info-name td"> 工作证号 </div>
        <div class="profile-info-value td">
          <span class="editable">${_user.code}</span>
        </div>
      </div>

      <div class="profile-info-row">
        <div class="profile-info-name td"> 所在单位及职务 </div>

        <div class="profile-info-value td">
          <span class="editable">${cadre.title}</span>
        </div>
      </div>

    </div>
  </div>
</div>