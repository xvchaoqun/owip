<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="OW_APPLY_APPROVAL_LOG_TYPE_MAP" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MAP%>"/>
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
          <a href="javascript:"><c:if test="${not empty sysUser}">${sysUser.realname}-</c:if>${OW_APPLY_APPROVAL_LOG_TYPE_MAP.get(type)}-审核记录</a>
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
    url: "${ctx}/applyApprovalLog_data?callback=?&id=${param.id}&type=${type}",
    colModel: [
      { label: '阶段',  name: 'stage', width: 200 },
      { label: '操作时间',  name: 'createTime', width: 200 },
      { label: '操作人', name: 'user.realname', width: 150 },
      { label:'审核结果',  name: 'statusName', formatter:function(cellvalue, options, rowObject){
        //return cellvalue==0?"未通过":"通过";
        return _cMap.OW_APPLY_APPROVAL_LOG_STATUS_MAP[rowObject.status];
      } },
      { label:'备注',  name: 'remark', width: 450 },
      { label:'IP',  name: 'ip', width: 150 },{hidden: true, name: 'status'}
    ],
    rowattr: function(rowData, currentObj, rowId)
    {
      if(rowData.status=='<%=OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_BACK%>') {
        //console.log(rowData)
        return {'class':'danger'}
      }
    },
    gridComplete:function(){
      $(window).triggerHandler('resize.jqGrid2');
    }
  });
</script>