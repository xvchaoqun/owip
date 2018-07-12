<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>短信发送记录</h3>
</div>
<div class="modal-body">
  <table id="jqGrid_popup" class="table-striped"> </table>
  <div id="jqGridPager_popup"> </div>
</div>
<script>
  $("#jqGrid_popup").jqGrid({
    multiselect:false,
    ondblClickRow:function(){},
    pager:"jqGridPager_popup",
    url: "${ctx}/sc/scPassportMsgList_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}",
    colModel: [
      { label: '发送内容',  name: 'msg', width: 350 },
      { label: '发送时间',  name: 'sendTime', width: 150 },
      { label:'发送结果',  name: 'success', width: 80, formatter:function(cellvalue, options, rowObject){
        return cellvalue?"成功":"失败";
      } }
    ]
  });
</script>