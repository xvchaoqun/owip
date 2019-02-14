<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
  <div class="col-xs-12">
    <div id="body-content" class="myTableDiv"
         data-url-page="${ctx}/sysApprovalLog?displayType=${displayType}&type=${type}"
         data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
      <c:set var="_query" value="${not empty param.userId}"/>

      <div class="col-sm-12">
        <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
          <div class="widget-header">
            <h4 class="widget-title">搜索</h4>
            <div class="widget-toolbar">
              <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
              </a>
            </div>
          </div>
          <div class="widget-body">
            <div class="widget-main no-padding">
              <form class="form-inline search-form" id="searchForm">
                <div class="form-group">
                  <label>操作人</label>
                  <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                          name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                  </select>
                </div>


                <div class="clearfix form-actions center">
                  <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                  <c:if test="${_query}">&nbsp;
                    <button type="button" class="reloadBtn btn btn-warning btn-sm">
                      <i class="fa fa-reply"></i> 重置
                    </button>
                  </c:if>
                </div>
              </form>
            </div>
          </div>
        </div>
        <%--<div class="space-4"></div>--%>
        <table id="jqGrid" class="jqGrid table-striped"> </table>
        <div id="jqGridPager"> </div>
      </div>
    </div><div id="body-content-view"></div>
  </div>
</div>
<script>
  $.register.user_select($('#searchForm select[name=userId]'));
  $("#jqGrid").jqGrid({
    multiselect:false,
    url: "${ctx}/sysApprovalLog_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}",
    colModel: [
      { label: '操作内容',  name: 'stage', width: 200 },
      { label: '操作时间',  name: 'createTime', width: 200 },
      { label: '操作人', name: 'user.realname'},
      { label: '操作人账号', name: 'user.code'},
      { label:'审核结果',  name: 'statusName', formatter:function(cellvalue, options, rowObject){
        //return cellvalue==0?"未通过":"通过";
        return _cMap.SYS_APPROVAL_LOG_STATUS_MAP[rowObject.status];
      } },
      { label:'备注',  name: 'remark', width: 450, align:'left', formatter: $.jgrid.formatter.NoMultiSpace},
      { label:'IP',  name: 'ip', width: 150 },{hidden: true, name: 'status'}
    ],
    rowattr: function(rowData, currentObj, rowId)
    {
      if(rowData.status=='<%=SystemConstants.SYS_APPROVAL_LOG_STATUS_BACK%>') {
        //console.log(rowData)
        return {'class':'danger'}
      }
    }
  });
  $(window).triggerHandler('resize.jqGrid');
  $.initNavGrid("jqGrid", "jqGridPager");
</script>