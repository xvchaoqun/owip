<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="USER_TYPE_JZG" value="<%=SystemConstants.USER_TYPE_JZG%>"/>
<div class="row">
  <div class="col-xs-12">
    <!-- PAGE CONTENT BEGINS -->
    <ul class="steps">
      <li data-step="1" class="active">
        <span class="step">0</span>
        <span class="title">申请已提交</span>
      </li>
      <c:if test="${memberApply.stage==-1}">
      <li data-step="2" class="active">
        <span class="step">1</span>
        <span class="title">未通过申请</span>
      </li>
      </c:if>

      <li data-step="1" <c:if test="${memberApply.stage>0}">class="active"</c:if>>
        <span class="step">1</span>
        <span class="title">申请已通过</span>
      </li>
      <li data-step="2" <c:if test="${memberApply.stage>1}">class="active"</c:if>>
        <span class="step">2</span>
        <span class="title">入党积极分子</span>
      </li>

      <li data-step="3" <c:if test="${memberApply.stage>2}">class="active"</c:if>>
        <span class="step">3</span>
        <span class="title">成为发展对象</span>
      </li>

      <li data-step="4" <c:if test="${memberApply.stage>3}">class="active"</c:if>>
        <span class="step">4</span>
        <span class="title">例入发展计划</span>
      </li>
      <li data-step="5" <c:if test="${memberApply.stage>4}">class="active"</c:if>>
        <span class="step">5</span>
        <span class="title">领取志愿书</span>
      </li>
      <li data-step="6" <c:if test="${memberApply.stage>5}">class="active"</c:if>>
        <span class="step">6</span>
        <span class="title">预备党员</span>
      </li>
    </ul>

    </div>
  </div>
