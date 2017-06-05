<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!-- PAGE CONTENT BEGINS -->
<div class="widget-box transparent" id="useLogs">
  <div class="widget-header">
    <h4 class="widget-title lighter smaller">
      <a href="javascript:" class="hideView btn btn-xs btn-success">
        <i class="ace-icon fa fa-backward"></i>
        返回</a>
    </h4>

    <div class="widget-toolbar no-border">
      <ul class="nav nav-tabs">
        <li class="active">
          <a href="javascript:">修改记录</a>
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
    url: "${ctx}/memberInModify_data?callback=?&inId=${param.inId}",
    colModel: [
      { label: '操作人', name: 'user.realname', width: 150, frozen:true },
      { label: '操作时间',  name: 'createTime', width: 150, frozen:true },
      { label:'IP',  name: 'ip', width: 150, frozen:true },
      {label: '介绍信抬头', name: 'fromTitle', width: 250, align:'left', frozen:true},
      {label: '介绍信有效期天数', name: 'validDays', width: 140},
      {label: '是否有回执', name: 'hasReceipt', formatter:$.jgrid.formatter.TRUEFALSE},
      {
        label: '转入组织机构', name: 'party',  width: 450, align:'left',
        formatter: function (cellvalue, options, rowObject) {
          return $.displayParty(rowObject.partyId, rowObject.branchId);
        }
      },
      {label: '类别', name: 'type', width: 50, formatter: function (cellvalue, options, rowObject) {
        return _cMap.MEMBER_INOUT_TYPE_MAP[cellvalue];
      }},
      {label: '转出单位', name: 'fromUnit', width: 200, align:'left'},
      {label: '转出单位地址', name: 'fromAddress', width: 350, align:'left'},
      {label: '转出单位联系电话', name: 'fromPhone', width: 150},
      {label: '转出单位传真', name: 'fromFax', width: 120},
      {label: '转出单位邮编', name: 'fromPostCode', width: 120},
      {label: '转出办理时间', name: 'fromHandleTime', width: 120, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
      {label: '转入办理时间', name: 'handleTime', width: 120,formatter:'date',formatoptions: {newformat:'Y-m-d'}},
      {label: '提交书面申请书时间', name: 'applyTime', width: 160,formatter:'date',formatoptions: {newformat:'Y-m-d'}},
      {label: '确定为入党积极分子时间', name: 'activeTime', width: 180,formatter:'date',formatoptions: {newformat:'Y-m-d'}},
      {label: '确定为发展对象时间', name: 'candidateTime', width: 160,formatter:'date',formatoptions: {newformat:'Y-m-d'}},
      {label: '入党时间', name: 'growTime',formatter:'date',formatoptions: {newformat:'Y-m-d'}},
      {label: '转正时间', name: 'positiveTime',formatter:'date',formatoptions: {newformat:'Y-m-d'}}
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