<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="active">
    <a href="javascript:;" class="loadPage"
       data-load-el="#step-body-content-view" data-callback="$.menu.liSelected"
       data-url='${ctx}/crsPost_detail/step1_notice?id=${param.id}'><i
            class="fa fa-bullhorn"></i> 招聘公告
    </a>
  </li>
  <li>
    <a href="javascript:;" class="loadPage"
       data-load-el="#step-body-content-view" data-callback="$.menu.liSelected"
       data-url='${ctx}/crsPost_detail/step1_postDuty?id=${param.id}'><i
            class="fa fa-hand-rock-o"></i> 岗位职责
    </a>
  </li>
  <li>
    <a href="javascript:;" class="loadPage"
       data-load-el="#step-body-content-view" data-callback="$.menu.liSelected"
       data-url='${ctx}/crsPost_detail/step1_base?id=${param.id}'><i
            class="fa fa-calendar-o"></i> 基本条件
    </a>
  </li>
  <li>
    <a href="javascript:;" class="loadPage"
       data-load-el="#step-body-content-view" data-callback="$.menu.liSelected"
       data-url='${ctx}/crsPost_detail/step1_require?id=${param.id}'><i
            class="fa fa-tasks"></i> 任职资格
    </a>
  </li>
</ul>
<div class="col-xs-12" id="step-body-content-view" <%--style="min-height: 500px;"--%>>
<c:import url="${ctx}/crsPost_detail/step1_notice?id=${param.id}"/>
</div>
<div style="clear: both"></div>