<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3><c:if test="${not empty sysUser}">${sysUser.realname}-</c:if>${SYS_APPROVAL_LOG_TYPE_MAP.get(type)}-操作记录</h3>
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
    url: "${ctx}/sysApprovalLog_data?callback=?&id=${param.id}&type=${type}",
    colModel: [
      { label: '操作内容',  name: 'stage', width: ${param.hideStatus==1?280:200} },
      { label: '操作时间',  name: 'createTime', width: 150 },
      { label: '操作人', name: 'user.realname' },
      <c:if test="${empty param.hideStatus || param.hideStatus==0}">
      { label:'审核结果',  name: 'statusName', width: 80, formatter:function(cellvalue, options, rowObject){
        //return cellvalue==0?"未通过":"通过";
        return _cMap.SYS_APPROVAL_LOG_STATUS_MAP[rowObject.status];
      } },
      </c:if>
      { label:'备注',  name: 'remark', width: 150, align:'left', formatter: $.jgrid.formatter.NoMultiSpace},
      { label:'IP',  name: 'ip', width: 120 },{hidden: true, name: 'status'}
    ],
    rowattr: function(rowData, currentObj, rowId)
    {
      if(rowData.status=='${SYS_APPROVAL_LOG_STATUS_BACK}') {
        //console.log(rowData)
        return {'class':'danger'}
      }
    }
  });
</script>