<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="rownumbers myTableDiv"
             data-url-page="${ctx}/pcsConfig_page"
             data-url-export="${ctx}/pcsConfig_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">

            </div>
            <%--<div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
              <div class="widget-header">
                <h4 class="widget-title">搜索</h4>

                <div class="widget-toolbar">
                  <a href="#" data-action="collapse">
                    <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                  </a>
                </div>
              </div>
              <div class="widget-body">
                <div class="widget-main no-padding">
                  <form class="form-inline search-form" id="searchForm">
                    <div class="form-group">
                      <label>党代会名称</label>
                      <input class="form-control search-query" name="name" type="text" value="${param.name}"
                             placeholder="请输入党代会名称">
                    </div>
                    <div class="clearfix form-actions center">
                      <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                      <c:if test="${_query}">&nbsp;
                        <button type="button" class="resetBtn btn btn-warning btn-sm">
                          <i class="fa fa-reply"></i> 重置
                        </button>
                      </c:if>
                    </div>
                  </form>
                </div>
              </div>
            </div>--%>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    function _reload(){
        $("#jqGrid").trigger("reloadGrid");
    }
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/pcsVoteGroup_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            <shiro:hasPermission name="pcsVoteGroup:record">
            {
                label: '报送', name: '_report', width: 80, formatter: function (cellvalue, options, rowObject) {
                if (rowObject.hasReport) return '<span class="text-success">已报送</span>'
                return ('<button class="confirm btn btn-success btn-xs" ' +
                'data-title="报送" data-msg="报送后不可修改，是否报送？" ' +
                'data-callback="_reload" ' +
                'data-url="${ctx}/pcsVoteGroup_report?groupId={0}" {1}><i class="fa fa-hand-paper-o"></i> 报送</button>')
                        .format(rowObject.id, rowObject.vote > 0 ? '' : 'disabled');
            }, frozen: true
            },
            {
                label: '报告单', name: '_export', width: 80, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.vote > 0)
                return ('<button class="linkBtn btn btn-warning btn-xs" ' +
                'data-url="${ctx}/pcsVoteCandidate_export?cls=0&groupId={0}"><i class="fa fa-download"></i> 导出</button>')
                        .format(rowObject.id);
                return '-'
            }, frozen: true
            },
            {
                label: '录入计票数据', name: '_record', width: 120, formatter: function (cellvalue, options, rowObject) {

                return ('<button class="openView btn btn-primary btn-xs" ' +
                'data-url="${ctx}/pcsVoteGroup_record?groupId={0}&type=${param.type}"><i class="fa fa-edit"></i> {1}</button>')
                        .format(rowObject.id, rowObject.vote > 0 ? '已录入' : '录入');

            }, frozen: true
            },
            </shiro:hasPermission>
            {label: '小组名称', name: 'name', width: 150},
            {label: '小组负责人', name: 'leader', width: 120},
            {label: '小组成员', name: 'member', width: 200, align: 'left'},
            {label: '计票录入人员', name: 'recordUser.realname', width: 120},
            {label: '领回选票张数', name: 'vote', width: 120},
            {label: '有效票数', name: 'valid', width: 80},
            {label: '无效票数', name: 'invalid', width: 80}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
</script>