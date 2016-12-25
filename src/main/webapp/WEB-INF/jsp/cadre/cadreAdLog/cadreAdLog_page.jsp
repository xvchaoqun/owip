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
          <a href="javascript:;">任免操作记录</a>
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
    url: "${ctx}/cadreAdLog_data?callback=?&cadreId=${cadreId}",
    colModel: [
      { label: '操作人', name: 'opUser.realname', width: 150, frozen:true },
      { label: '操作时间',  name: 'createTime', width: 150 , frozen:true},
      { label:'IP',  name: 'ip', width: 150 , frozen:true},
      { label:'操作内容',  name: 'content', width: 200, frozen:true },
      {
        label: '类别', name: 'status', formatter: function (cellvalue, options, rowObject) {
        if (cellvalue == undefined) return '';
        return _cMap.CADRE_STATUS_MAP[cellvalue];
      }},
      {label: '工作证号', name: 'user.code', width: 100},
      {label: '姓名', name: 'user.realname', width: 120},
      {label: '部门属性', name: 'unit.unitType.name', width: 150},
      {label: '所在单位', name: 'unit.name', width: 200},
      {label: '现任职务', name: 'post', align: 'left', width: 350},
      {label: '所在单位及职务', name: 'title', align: 'left', width: 350},
      {
        label: '行政级别', name: 'typeId', formatter: function (cellvalue, options, rowObject) {
        if (cellvalue == undefined) return '';
        return _cMap.adminLevelMap[cellvalue].name;
      }
      },
      {
        label: '职务属性', name: 'postId', width: 150, formatter: function (cellvalue, options, rowObject) {
        if (cellvalue == undefined) return '';
        return _cMap.postMap[cellvalue].name;
      }
      }
    ],
    rowattr: function(rowData, currentObj, rowId)
    {
      /*if(rowData.status=='${APPLY_APPROVAL_LOG_STATUS_BACK}') {
        //console.log(rowData)
        return {'class':'danger'}
      }*/
    },
    gridComplete:function(){
      $(window).triggerHandler('resize.jqGrid2');
    }
  }).jqGrid("setFrozenColumns");
</script>