<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>${sysResource.name}<c:if test="${not empty sysResource.permission}">（${sysResource.permission}）</c:if> - 所属角色</h3>
</div>
<div class="modal-body popup-jqgrid">
  <table id="jqGrid_popup" class="table-striped"> </table>
  <div id="jqGridPager_popup"> </div>
</div>
<script>
   // $("#searchForm_popup select").select2();
  //$.register.user_select($('#searchForm_popup select[name=userId]'));
  //$('#searchForm_popup [data-rel="select2"]').select2();
  $("#jqGrid_popup").jqGrid({
    multiselect:false,
    height:390,
    width:620,
    rowNum:10,
    ondblClickRow:function(){},
    pager:"jqGridPager_popup",
    url: "${ctx}/sysRole_data?callback=?&resourceId=${param.resourceId}&${cm:encodeQueryString(pageContext.request.queryString)}",
    colModel: [
            { label: '角色代码', name: 'role', width: 150},
            { label: '角色名称', name: 'description', width: 150},
            { label: '备注', name: 'description', width: 300,align:'left'},
    ]
  }).jqGrid("setFrozenColumns");
  $.initNavGrid("jqGrid_popup", "jqGridPager_popup");
</script>