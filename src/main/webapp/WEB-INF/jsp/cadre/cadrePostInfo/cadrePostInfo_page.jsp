<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="tabbable myTableDiv">
  <ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="${type==1?"active":""}">
      <a href="javascript:" onclick="_innerPage2(1)"><i class="fa fa-flag"></i> 专技岗位过程信息</a>
    </li>
    <li class="${type==2?"active":""}">
      <a href="javascript:" onclick="_innerPage2(2)"><i class="fa fa-flag"></i> 管理岗位过程信息</a>
    </li>
    <li class="${type==3?"active":""}">
      <a href="javascript:" onclick="_innerPage2(3)"><i class="fa fa-flag"></i> 工勤岗位过程信息</a>
    </li>
  </ul>

  <c:if test="${type==1}">
  <div class="space-4"></div>
        <table id="jqGrid_cadrePostPro" data-width-reduce="60" class="jqGrid2"></table>
        <div id="jqGridPager_cadrePostPro"></div>
  </c:if>
  <c:if test="${type==2}">
      <div class="space-4"></div>
      <table id="jqGrid_cadrePostAdmin" data-width-reduce="60" class="jqGrid2"></table>
      <div id="jqGridPager_cadrePostAdmin"></div>
  </c:if>
  <c:if test="${type==3}">
      <div class="space-4"></div>
      <table id="jqGrid_cadrePostWork" data-width-reduce="60" class="jqGrid2"></table>
      <div id="jqGridPager_cadrePostWork"></div>
  </c:if>
<script>
  function _innerPage2(type) {
    $("#view-box .tab-content").load("${ctx}/cadrePostInfo_page?type=" + type)
  }

  <c:if test="${type==1}">
    $("#jqGrid_cadrePostPro").jqGrid({
      ondblClickRow: function () {
      },
      pager: "#jqGridPager_cadrePostPro",
      url: '${ctx}/cadrePostPro_data?${cm:encodeQueryString(pageContext.request.queryString)}',
      colModel: [
        {label: '是否当前专技岗位', width: 150, name: 'isCurrent'},
        {label: '岗位类别', width: 120, name: 'type'},
        {label: '专业技术职务', name: 'post', width: 250},
        {label: '专技职务任职时间', name: 'holdTime', width: 150, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
        {label: '专技岗位等级', name: 'level', width: 120},
        {label: '专技岗位分级时间', name: 'gradeTime', width: 150, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
        {label: '专技岗位备注', name: 'remark', width: 350}
      ]
    });
  </c:if>
  <c:if test="${type==2}">
    $("#jqGrid_cadrePostAdmin").jqGrid({
      ondblClickRow: function () {
      },
      pager: "#jqGridPager_cadrePostAdmin",
      url: '${ctx}/cadrePostAdmin_data?${cm:encodeQueryString(pageContext.request.queryString)}',
      colModel: [
        {label: '是否当前管理岗位', width: 150, name: 'isCurrent'},
        {label: '管理岗位类型', width: 120, name: 'type'},
        {label: '管理岗位等级', name: 'level', width: 120},
        {label: '管理岗位分级时间', name: 'gradeTime', width: 150, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
        {label: '管理岗位备注', name: 'remark', width: 350}
      ]
    });
  </c:if>
  <c:if test="${type==3}">
    $("#jqGrid_cadrePostWork").jqGrid({
      ondblClickRow: function () {
      },
      pager: "#jqGridPager_cadrePostWork",
      url: '${ctx}/cadrePostWork_data?${cm:encodeQueryString(pageContext.request.queryString)}',
      colModel: [
        {label: '是否当前工勤岗位', width: 150, name: 'isCurrent'},
        {label: '工勤岗位类型', width: 120, name: 'type'},
        {label: '工勤岗位等级', name: 'level', width: 120},
        {label: '工勤岗位分级时间', name: 'gradeTime', width: 150, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
        {label: '工勤岗位备注', name: 'remark', width: 350}
      ]
    });
  </c:if>
    $(window).triggerHandler('resize.jqGrid2');
  $('#searchForm [data-rel="select2"]').select2();
  $('[data-rel="tooltip"]').tooltip();
</script>