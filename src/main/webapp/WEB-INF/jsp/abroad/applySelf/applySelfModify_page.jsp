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
  var record =${not empty record?record:'null'};
  //console.dir(record)
  function cellattr(rowId, val, rowObject, cm, rdata) {
      if(record!=null) {
        if (cm.name == 'day') {
          record.day = $.dayDiff(record.startDate, record.endDate)
        }
        if (cm.name == 'reason') {
          record.reason = record.reason.replace(/\+\+\+/g, ',');
        }
        if (rowId != record.id && $.trim(val) != $.trim(record[cm.name])) {

          return 'class="danger"'
        }
      }
  }
  $("#jqGrid2").jqGrid({
    multiselect:false,
    pager:"jqGridPager2",
    url: "${ctx}/abroad/applySelfModify_data?callback=?&applyId=${param.applyId}",
    colModel: [
      { label: '操作', name: 'modifyType', width: 150,formatter:function(cellvalue, options, rowObject){
        if(cellvalue==undefined) return '--'
        return _cMap.ABROAD_APPLYSELF_MODIFY_TYPE_MAP[cellvalue];
      }, frozen:true },
      { label: '操作人', name: 'modifyUser.realname', width: 150, frozen:true},
      { label:'IP',  name: 'ip', width: 120, frozen:true },
      { label: '操作时间',  name: 'createTime', width: 150, frozen:true },
      { label: '出行时间',  name: 'startDate',cellattr:cellattr, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'} },
      { label: '回国时间',  name: 'endDate' ,cellattr:cellattr, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
      { label: '出行天数',  name: 'day', width: 80,cellattr:cellattr,formatter:function(cellvalue, options, rowObject){
        return $.dayDiff(rowObject.startDate, rowObject.endDate);
      }},
      { label:'前往国家或地区', name: 'toCountry', width: 180,cellattr:cellattr},
      { label:'因私出国（境）事由',  name: 'reason', width: 200,cellattr:cellattr, formatter:function(cellvalue, options, rowObject){
        return cellvalue.replace(/\+\+\+/g, ',');
      }},
      {label: '本人说明材料', name: 'modifyProof', formatter: function (cellvalue, options, rowObject) {
        return $.download(cellvalue, rowObject.modifyProofFileName, "下载");
      }},
      {label: '备注', name: 'remark', width: 500}
    ]
  });
  $(window).triggerHandler('resize.jqGrid2');
</script>