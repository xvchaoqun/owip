<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/pcsOw"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.partyId|| not empty param.hasReport|| not empty param.sort}"/>
            <div class="tabbable">
<shiro:lacksRole name="role_pcs_check">
                <jsp:include page="menu.jsp"/>
    </shiro:lacksRole>
                <div class="tab-content">
                    <div class="tab-pane in active rownumbers">
                        <shiro:lacksRole name="role_pcs_check">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="pcsOw:admin">
                            <a class="popupBtn btn btn-warning btn-sm"
                               data-url="${ctx}/pcsAdmin_msg?type=1&stage=${param.stage}"><i class="fa fa-send"></i> 短信催促未报送单位</a>
                            <span style="margin-left: 20px;">
                            分党委、党总支、直属党支部共${hasReportCount+hasNotReportCount}个，完成报送共${hasReportCount}个，未报送${hasNotReportCount}个。
                                </span>
                            </shiro:hasPermission>
                        </div>
                        </shiro:lacksRole>
                        <div class="space-4"></div>
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
                                        <input type="hidden" name="stage" value="${param.stage}">
                                        <div class="form-group">
                                            <label>分党委</label>
                                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                                                    name="partyId" data-placeholder="请选择">
                                                <option value="${party.id}" title="${party.isDeleted}">${party.name}</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>推荐情况</label>
                                            <select data-rel="select2"
                                                    name="hasReport" data-placeholder="请选择">
                                                <option></option>
                                                <option value="1">已上报</option>
                                                <option value="0">未上报</option>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=hasReport]").val("${param.hasReport}")
                                            </script>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>
                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="resetBtn btn btn-warning btn-sm"
                                                        data-querystr="cls=${param.cls}&stage=${param.stage}">
                                                    <i class="fa fa-reply"></i> 重置
                                                </button>
                                            </c:if>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <div class="space-4"></div>
                        <table id="jqGrid" class="jqGrid table-striped"></table>
                        <div id="jqGridPager"></div>
                    </div>
                </div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/pcsOw_party_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '分党委名称',name: 'name', align:'left', width:400},
            { label: '党支部数',name: 'branchCount'},
            { label: '党员总数',name: 'memberCount', formatter: function (cellvalue, options, rowObject) {
                return ($.trim(cellvalue)=='')?0:cellvalue;
            }},
            { label: '应参会党员数',name: 'expectMemberCount', width:120},
            { label: '实参会党员数',name: 'actualMemberCount', width:120},
            { label: '推荐情况',name: 'reportId', formatter: function (cellvalue, options, rowObject) {
                var hasReport = (cellvalue==undefined)?false:(cellvalue>0);
               // if(!hasReport) return "未上报"
                return ('<button class="openView btn {2} btn-xs" ' +
                'data-url="${ctx}/pcsOw_party_detail_page?stage=${param.stage}&partyId={0}"><i class="fa {3}"></i> {1}</button>')
                        .format(rowObject.id, hasReport?"已上报":"未上报",
                        hasReport?"btn-success":"btn-default", hasReport?"fa-hand-paper-o":"fa-hand-rock-o");
            }},
            <shiro:hasPermission name="pcsOw:admin">
            { label: '退回',name: 'reportId', formatter: function (cellvalue, options, rowObject) {

                var hasReport = (cellvalue==undefined)?false:(cellvalue>0);
                if(!hasReport) return "-"
                return ('<button class="confirm btn btn-danger btn-xs" data-callback="_reload"  data-title="退回"  data-msg="确定退回“{1}”的报送？"' +
                'data-url="${ctx}/pcsOw_party_report_back?stage=${param.stage}&reportId={0}"><i class="fa fa-reply"></i> 退回</button>')
                        .format(rowObject.reportId, rowObject.name);
            }}
            </shiro:hasPermission>
        ]
    }).jqGrid("setFrozenColumns");
    function _reload(){
        $("#jqGrid").trigger("reloadGrid");
    }
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.party_select($('#searchForm select[name=partyId]'));
    $('[data-rel="select2"]').select2();
</script>