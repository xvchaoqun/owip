<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!-- PAGE CONTENT BEGINS -->
<div class="widget-box transparent" id="useLogs">
  <div class="widget-header">
    <h4 class="widget-title lighter smaller">
      <a href="javascript:;" class="closeView btn btn-xs btn-success">
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
    url: "${ctx}/memberModify_data?callback=?&userId=${param.userId}",
    colModel: [
      { label: '操作人', name: 'opUser.realname', width: 150, frozen:true },
      { label: '操作时间',  name: 'opTime', width: 150 , frozen:true},
      { label:'IP',  name: 'ip', width: 150 , frozen:true},
      { label:'修改原因',  name: 'reason', width: 200, frozen:true },
      { label: '学工号',  name: 'user.code',width: 120, frozen:true},
      { label: '姓名',  name: 'user.realname', frozen:true},
      { label:'所属组织机构', name: 'party', width: 550, formatter:function(cellvalue, options, rowObject){
        return $.displayParty(rowObject.partyId, rowObject.branchId);
      } },
      { label:'党籍状态',name: 'politicalStatus', formatter:function(cellvalue, options, rowObject){
          return _cMap.MEMBER_POLITICAL_STATUS_MAP[cellvalue];
      }},
      { label:'类别',name: 'type', formatter:function(cellvalue, options, rowObject){
          return _cMap.MEMBER_TYPE_MAP[cellvalue];
      }},
      { label:'状态',name: 'status', formatter:function(cellvalue, options, rowObject){
        return _cMap.MEMBER_STATUS_MAP[cellvalue];
      }},
      { label:'提交书面申请书时间',  name: 'applyTime', width: 180, formatter:'date',formatoptions: {newformat:'Y-m-d'}},
      { label:'确定为入党积极分子时间',  name: 'activeTime', width: 180, formatter:'date',formatoptions: {newformat:'Y-m-d'}},
      { label:'确定为发展对象时间',  name: 'candidateTime', width: 180, formatter:'date',formatoptions: {newformat:'Y-m-d'}},
      { label:'入党时间',  name: 'growTime', formatter:'date',formatoptions: {newformat:'Y-m-d'}},
      { label:'转正时间',  name: 'positiveTime', formatter:'date',formatoptions: {newformat:'Y-m-d'}},
      { label:'党内职务',  name: 'partyPost', width: 150},
      { label:'党内奖励',  name: 'partyReward', width: 150},
      { label:'其他奖励',  name: 'otherReward', width: 150},
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