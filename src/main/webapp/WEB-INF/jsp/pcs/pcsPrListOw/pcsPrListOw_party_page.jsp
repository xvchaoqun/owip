<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/pcsPrListOw_party"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.partyId||not empty param.hasReport||not empty param.recommendStatus
                   || not empty param.sort}"/>
            <div class="tabbable">
                <div class="candidate-table tab-content">
                    <div class="tab-pane in active rownumbers">
                        <div class="jqgrid-vertical-offset buttons">
                            <a class="popupBtn btn btn-warning btn-sm"
                               data-url="${ctx}/pcsAdmin_msg?type=2&stage=${param.stage}"><i class="fa fa-send"></i> 短信催促未报送单位</a>
                            <a class="jqOpenViewBatchBtn btn btn-success btn-sm"
                               data-querystr="stage=${PCS_STAGE_THIRD}"
                               data-ids-name="partyIds[]"
                               data-url="${ctx}/pcsPrOw_check"><i class="fa fa-check-square"></i> 批量审批</a>

                        <span style="margin-left: 20px;">
                            分党委、党总支、直属党支部共${hasReportCount+hasNotReportCount}个，完成上传共${hasReportCount}个（通过审核${passCount}个），未上传${hasNotReportCount}个。
                        </span>
                        </div>
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
                                        <div class="form-group">
                                            <label>分党委</label>
                                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                                                    name="partyId" data-placeholder="请选择">
                                                <option value="${party.id}" title="${party.isDeleted}">${party.name}</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>是否上传选举结果</label>
                                            <select data-rel="select2"
                                                    name="hasReport" data-placeholder="请选择">
                                                <option></option>
                                                <option value="1">已上传</option>
                                                <option value="0">未上传</option>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=hasReport]").val("${param.hasReport}")
                                            </script>
                                        </div>

                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>
                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="resetBtn btn btn-warning btn-sm">
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
        url: '${ctx}/pcsPrListOw_party_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '分党委名称',name: 'name', align:'left', width:400, frozen:true},
            { label: '是否上传选举结果',name: 'hasReport', width:150, formatter: function (cellvalue, options, rowObject) {
                return "<span class='label {0}'>{1}</span>".format(cellvalue?"label-success":"label-default",
                        cellvalue?"已上传":"未上传");
            }},
            { label: '党员大会情况',name: '_hasReport', width:150, formatter: function (cellvalue, options, rowObject) {
                return ('<button class="openView btn btn-primary btn-xs" ' +
                'data-url="${ctx}/pcsPrListOw_party_detail_page?partyId={0}"><i class="fa fa-search"></i> 详情</button>')
                        .format(rowObject.id);
            }},
            { label: '审核情况',name: 'recommendStatus', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue == undefined) return "-"
                if(cellvalue == '${PCS_PR_RECOMMEND_STATUS_PASS}') return '<span class="text-success">审核通过</span>';
                if(cellvalue == '${PCS_PR_RECOMMEND_STATUS_DENY}') return '<span class="text-danger">审核不通过</span>';
                return ('<button class="popupBtn btn btn-success btn-xs" ' +
                'data-url="${ctx}/pcsPrOw_check?stage=${PCS_STAGE_THIRD}&partyIds[]={0}"><i class="fa fa-check-square-o"></i> 审核</button>')
                        .format(rowObject.id);
            }},
            { label: '短信提醒',name: 'recommendStatus', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue == undefined) return "-"
                var pass = -1;
                if(cellvalue == '${PCS_PR_RECOMMEND_STATUS_PASS}') pass = 1;
                if(cellvalue == '${PCS_PR_RECOMMEND_STATUS_DENY}') pass = 0;
                if(pass == -1) return '-'
                return ('<button class="popupBtn btn btn-warning btn-xs" ' +
                'data-url="${ctx}/pcsAdmin_msg?cls=3&partyId={0}&pass={1}"><i class="fa fa-send"></i> 短信提醒</button>')
                        .format(rowObject.id, pass);
            }},
            { label: '备注',name: 'checkRemark', width:300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.del_select($('#searchForm select[name=partyId]'));
    $('[data-rel="select2"]').select2();
</script>