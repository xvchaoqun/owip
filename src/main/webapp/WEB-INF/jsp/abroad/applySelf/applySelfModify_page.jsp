<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!-- PAGE CONTENT BEGINS -->
<div class="widget-box transparent" id="useLogs">
  <div class="widget-header">
    <h4 class="widget-title lighter smaller">
      <a href="javascript:;" class="closeView btn btn-mini btn-xs btn-success">
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
    url: "${ctx}/applySelfModify_data?callback=?&applyId=${param.applyId}",
    colModel: [
      { label: '操作人', name: 'modifyUser.realname', width: 150, frozen:true },
      { label: '操作时间',  name: 'createTime', width: 150, frozen:true },
      { label:'IP',  name: 'ip', width: 150, frozen:true },
      {label: '本人说明材料', name: 'modifyProof', width: 150, formatter: function (cellvalue, options, rowObject) {
        if($.trim(cellvalue)=='') return ''
        return '<a href="${ctx}/attach/download?path={0}&filename={1}">{1}</a>'.format(cellvalue, rowObject.modifyProofFileName);
      }},
      {label: '备注', name: 'remark', width: 180},
      { label: '编号', align:'center', name: 'id', width: 80 ,formatter:function(cellvalue, options, rowObject){
        return "S{0}".format(rowObject.id);
      },frozen:true},
      { label: '申请日期', align:'center', name: 'applyDate', width: 100 },
      { label: '工作证号', align:'center', name: 'user.code', width: 100 },
      { label: '姓名',align:'center', name: 'user.realname', width: 75 },
      { label: '所在单位及职务',  name: 'cadre.title', width: 250 },
      { label: '出行时间', align:'center', name: 'startDate', width: 100 },
      { label: '回国时间', align:'center', name: 'endDate', width: 100 },
      { label: '出行天数', align:'center', name: 'code', width: 80,formatter:function(cellvalue, options, rowObject){
        return DateDiff(rowObject.startDate, rowObject.endDate);
      }},
      { label:'前往国家或地区', align:'center',name: 'toCountry', width: 180},
      { label:'因私出国（境）事由', align:'center', name: 'reason', width: 200, formatter:function(cellvalue, options, rowObject){
        return cellvalue.replace(/\+\+\+/g, ',');
      }}
    ],
    rowattr: function(rowData, currentObj, rowId)
    {
      if(rowData.status=='${APPLY_APPROVAL_LOG_STATUS_BACK}') {
        //console.log(rowData)
        return {'class':'danger'}
      }
    },
    gridComplete:function(){
      $(window).triggerHandler('resize.jqGrid2');
    }
  });
</script>