<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="widget-box transparent" id="useLogs">
  <div class="widget-header">
    <h4 class="widget-title lighter smaller">
      <a href="javascript:;" class="hideView btn btn-xs btn-success">
        <i class="ace-icon fa fa-backward"></i>
        返回</a>
    </h4>

    <div class="widget-toolbar no-border">
      <ul class="nav nav-tabs">
        <li class="active">
          <a href="javascript:;">修改记录</a>
        </li>
      </ul>
    </div>
  </div>
  <div class="widget-body">
    <div class="widget-main padding-4">
      <div class="tab-content padding-8">
        <div class="space-4"></div>
        <table id="jqGrid2" class="jqGrid2 table-striped"> </table>
        <div id="jqGridPager2"> </div>
      </div>
    </div><!-- /.widget-main -->
  </div><!-- /.widget-body -->
</div><!-- /.widget-box -->
<script>
  $("#jqGrid2").jqGrid({
    multiselect:false,
    pager:"jqGridPager2",
    url: "${ctx}/memberOutModify_data?callback=?&outId=${param.outId}",
    colModel: [
      { label: '操作人', name: 'user.realname', width: 150, frozen:true },
      { label: '操作时间',  name: 'createTime', width: 150, frozen:true },
      { label:'IP',  name: 'ip', width: 150, frozen:true },
      {label: '类别', name: 'type', width: 80, formatter: $.jgrid.formatter.MetaType, frozen:true},
      {label: '联系电话', name: 'phone', width: 180},
      {label: '转入单位', name: 'toUnit', width: 150},
      {label: '转入单位抬头', name: 'toTitle', width: 200},
      {label: '转出单位', name: 'fromUnit', width: 200},
      {label: '转出单位地址', name: 'fromAddress', width: 120},
      {label: '转出单位联系电话', name: 'fromPhone', width: 150},
      {label: '转出单位传真', name: 'fromFax', width: 120},
      {label: '转出单位邮编', name: 'fromPostCode', width: 120},
      {label: '党费缴纳至年月', name: 'payTime', width: 150, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m'}},
      {label: '介绍信有效期天数', name: 'validDays', width: 150},
      {label: '办理时间', name: 'handleTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}},
      {label: '是否有回执', name: 'hasReceipt', formatter: function (cellvalue, options, rowObject) {
        return cellvalue?"是":"否"
      }}
    ],
    rowattr: function(rowData, currentObj, rowId)
    {
      if(rowData.status=='<%=OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_BACK%>') {
        //console.log(rowData)
        return {'class':'danger'}
      }
    }
  });
  $(window).triggerHandler('resize.jqGrid2');
</script>