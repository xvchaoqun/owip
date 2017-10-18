<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>${crsPost.name}-应聘岗位操作记录</h3>
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
    url: "${ctx}/user/applicant_log_data?callback=?&applicantId=${param.applicantId}",
    colModel: [
      { label: '操作内容',  name: 'stage', width: 200 },
      { label: '操作时间',  name: 'createTime', width: 180 },
      { label:'IP',  name: 'ip', width: 220 }
    ]
  });
</script>