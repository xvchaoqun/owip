<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="${cls==1?'active':''}">
    <a href="javascript:;" class="renderBtn" data-url="${ctx}/approvalAuth?cls=1"}><i class="fa fa-th${cls==1?'-large':''}"></i> 申请人身份</a>
  </li>

  <li class="${cls==2?'active':''}">
    <a href="javascript:;" class="renderBtn" data-url="${ctx}/approvalAuth?cls=2"}><i class="fa fa-th${cls==2?'-large':''}"></i> 审批人身份</a>
  </li>
</ul>
