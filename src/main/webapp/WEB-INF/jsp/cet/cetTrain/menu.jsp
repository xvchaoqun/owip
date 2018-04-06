<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue" style="padding-left: 130px;">
  <a href="javascript:" class="hideView2 btn btn-sm btn-success" style="position: absolute; left: 10px; top:5px;">
    <i class="ace-icon fa fa-reply"></i>
    返回培训方案</a>
  <li class="${cls==1?'active':''}">
  <a href="javascript:;" class="loadPage" data-load-el="#body-content-view2" data-url="${ctx}/cet/cetTrain?planId=${param.planId}&cls=1"><i class="fa fa-circle-o-notch fa-spin ${cls==1?'fa-1g':''}"></i> 正在进行</a>
  </li>
  <li class="${cls==2?'active':''}">
  <a href="javascript:;" class="loadPage" data-load-el="#body-content-view2" data-url="${ctx}/cet/cetTrain?planId=${param.planId}&cls=2"><i class="fa fa-check ${cls==2?'fa-1g':''}"></i> 已结课</a>
  </li>
  <li class="${cls==3?'active':''}">
  <a href="javascript:;" class="loadPage" data-load-el="#body-content-view2" data-url="${ctx}/cet/cetTrain?planId=${param.planId}&cls=3"><i class="fa fa-times ${cls==3?'fa-1g':''}"></i> 已删除</a>
  </li>
  <li class="${cls==4?'active':''}">
  <a href="javascript:;" class="loadPage" data-load-el="#body-content-view2" data-url="${ctx}/cet/cetTrain?planId=${param.planId}&cls=4"><i class="fa fa-circle-o-notch ${cls==3?'fa-1g':''}"></i> 全部</a>
  </li>
</ul>
