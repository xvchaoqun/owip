<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>选择填报记录</h3>
</div>
<div class="modal-body rownumbers">
  <table id="jqGridPopup" class="table-striped"></table>
</div>
<div class="modal-footer">
  <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
  <input type="button" id="selectBtn" class="btn btn-primary" value="选择"/>
</div>
<style>
  .rownumbers #jqGridPopup_cb {
    text-align: left !important;
  }
</style>
<script>
  var matterItems = ${cm:toJSONArray(scMatterItemViews)};
  $("#jqGridPopup").jqGrid({
    pager: null,
    rownumbers: true,
    multiboxonly: false,
    height: 400,
    width: 865,
    datatype: "local",
    rowNum: matterItems.length,
    data: matterItems,
    colModel: [
      {label: '年度', name: 'year', width: 80},
      {
        label: '填报类型', name: 'type', width: 130, formatter: function (cellvalue, options, rowObject) {
        return (rowObject.type) ? '个别填报' : '年度集中填报';
      }
      },
      {label: '工作证号', name: 'code', width: 120, frozen: true},
      {label: '姓名', name: 'realname', frozen: true},
      {label: '所在单位及职务', name: 'title', align: 'left', width: 250},
      {label: '封面填表日期', name: 'fillTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}}
    ]
  });

  // 已选的邀请人
  $("#jqGridPopup").jqGrid('resetSelection');
  $.each(matterItems, function (i, item) {
      //console.log("item.transferId=" + item.transferId)
      //console.log("transferId=${param.transferId}")
      if(item.transferId==parseInt(${param.transferId}))
        $("#jqGridPopup").jqGrid('setSelection', item.id, true);
  });

  $("#modal #selectBtn").click(function () {

    var ids = $("#jqGridPopup").getGridParam("selarrrow");
    if (ids.length == 0) return;

    $.post("${ctx}/sc/scMatterTransfer_items",{transferId:${param.transferId}, matterItemIds:ids},function(data){

      if(data.success){
        $("#modal").modal('hide');
      }
    });
  });
</script>