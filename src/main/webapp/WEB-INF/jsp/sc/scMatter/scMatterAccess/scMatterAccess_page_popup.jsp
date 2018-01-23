<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>调阅记录</h3>
</div>
<div class="modal-body">
  <table id="jqGrid_popup" class="table-striped"> </table>
  <div id="jqGridPager_popup"> </div>
</div>
<jsp:include page="scMatterAccess_colModel.jsp?cls=${param.cls}"/>
<script>
  $("#jqGrid_popup").jqGrid({
    multiselect:false,
    ondblClickRow:function(){},
    pager:"jqGridPager_popup",
    url: '${ctx}/sc/scMatterAccess_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
    colModel: colModel
  });
</script>