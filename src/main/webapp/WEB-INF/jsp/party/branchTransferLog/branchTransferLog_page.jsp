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
          <a href="javascript:;">${branch.name}-转移记录</a>
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
    ondblClickRow:function(){},
    pager:"jqGridPager2",
    url: "${ctx}/branchTransferLog_data?callback=?&branchId=${param.branchId}",
    colModel: [
      { label: '原${_p_partyName}', name: 'partyId',align:'left', width: 400 ,  formatter:function(cellvalue, options, rowObject){
        return cellvalue==undefined?"":_cMap.partyMap[cellvalue].name;
      }},
      { label: '转移至${_p_partyName}', name: 'toPartyId',align:'left', width: 400 ,  formatter:function(cellvalue, options, rowObject){
        return cellvalue==undefined?"":_cMap.partyMap[cellvalue].name;
      }},
      { label: '操作人', name: 'user.realname', width: 150 },
      { label: '转移原因', name: 'remark', width: 300 },
      { label:'操作时间',  name: 'createTime', width: 150},
      { label:'IP',  name: 'ip', width: 150 }
    ],
    gridComplete:function(){
      $(window).triggerHandler('resize.jqGrid2');
    }
  });
</script>