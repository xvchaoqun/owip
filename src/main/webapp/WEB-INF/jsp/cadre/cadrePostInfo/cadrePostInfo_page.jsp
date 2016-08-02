<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

  <div class="widget-box">
    <div class="widget-header">
      <h4 class="widget-title"><i class="fa fa-battery-full"></i> 专技岗位过程信息
      </h4>
      <div class="widget-toolbar">
        <a href="#" data-action="collapse">
          <i class="ace-icon fa fa-chevron-up"></i>
        </a>
      </div>
    </div>
    <div class="widget-body">
      <div class="widget-main table-nonselect">
        <table id="jqGrid_cadrePostPro" data-width-reduce="50" class="jqGrid4"></table>
        <div id="jqGridPager_cadrePostPro"></div>
      </div>
    </div>
  </div>
<div class="widget-box">
  <div class="widget-header">
    <h4 class="widget-title"><i class="fa fa-battery-full"></i> 管理岗位过程信息
    </h4>
    <div class="widget-toolbar">
      <a href="#" data-action="collapse">
        <i class="ace-icon fa fa-chevron-up"></i>
      </a>
    </div>
  </div>
  <div class="widget-body">
    <div class="widget-main table-nonselect">
      <table id="jqGrid_cadrePostAdmin" data-width-reduce="50" class="jqGrid4"></table>
      <div id="jqGridPager_cadrePostAdmin"></div>
    </div>
  </div>
</div>
<div class="widget-box">
  <div class="widget-header">
    <h4 class="widget-title"><i class="fa fa-battery-full"></i> 工勤岗位过程信息
    </h4>
    <div class="widget-toolbar">
      <a href="#" data-action="collapse">
        <i class="ace-icon fa fa-chevron-up"></i>
      </a>
    </div>
  </div>
  <div class="widget-body">
    <div class="widget-main table-nonselect">
      <table id="jqGrid_cadrePostWork" data-width-reduce="50" class="jqGrid4"></table>
      <div id="jqGridPager_cadrePostWork"></div>
    </div>
  </div>
</div>

<script>

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
    }).on("initGrid", function () {
      $(window).triggerHandler('resize.jqGrid4');
    });

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
    }).on("initGrid", function () {
      $(window).triggerHandler('resize.jqGrid4');
    });

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
    }).on("initGrid", function () {
      $(window).triggerHandler('resize.jqGrid4');
    });

  $('#searchForm [data-rel="select2"]').select2();
  $('[data-rel="tooltip"]').tooltip();
</script>