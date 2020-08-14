<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=PcsConstants.PCS_POLL_CANDIDATE_TYPE%>" var="PCS_POLL_CANDIDATE_TYPE"/>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
        <a href="javascript:;" class="hideView btn btn-xs btn-success">
            <i class="ace-icon fa fa-backward"></i>
            返回</a>
    </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <c:set var="_query" value="${not empty param.userId ||not empty param.type}"/>
                <div>
                    <shiro:hasPermission name="pcsPollCandidate:edit">
                        <button class="popupBtn btn btn-primary btn-sm tooltip-success"
                                data-url="${ctx}/pcs/pcsPollCandidate_import?pollId=${param.pollId}"
                                data-rel="tooltip" data-placement="top" title="批量导入二下阶段推荐人名单"><i class="fa fa-upload"></i> 导入</button>
                    </shiro:hasPermission>
                    <%--<shiro:hasPermission name="pcsPollCandidate:del">
                        <button data-url="${ctx}/pcs/pcsPollCandidate_batchDel"
                                data-title="删除"
                                data-msg="确定删除这{0}条数据？"
                                data-grid-id="#jqGrid2"
                                class="jqBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> 删除
                        </button>
                    </shiro:hasPermission>--%>
                </div>
                <%--<div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                    <div class="widget-header">
                        <h4 class="widget-title">搜索</h4>
                        <span class="widget-note">${note_searchbar}</span>
                        <div class="widget-toolbar">
                            <a href="#" data-action="collapse">
                                <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                            </a>
                        </div>
                    </div>
                    <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm2">
                            <input type="hidden" name="pollId" value="${param.pollId}"/>
                            <div class="form-group">
                                <label>候选人</label>
                                <input class="form-control search-query" name="userId" type="text" value="${param.userId}"
                                       placeholder="请输入候选人">
                            </div>
                            <div class="form-group">
                                <label>推荐人类型</label>
                                <div class="col-xs-6">
                                    <select required data-rel="select2" name="type" data-width="270"
                                            data-placeholder="请选择">
                                        <option></option>
                                        <c:forEach items="${PCS_POLL_CANDIDATE_TYPE}" var="entry">
                                            <option value="${entry.key}">${entry.value}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <script> $('#searchForm2 select[name=type]').val(${param.type}) </script>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/pcs/pcsPollCandidate"
                                   data-target="#page-content"
                                   data-form="#searchForm2"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/pcs/pcsPollCandidate"
                                            data-target="#page-content">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                    </div>
                </div>--%>
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="100"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<script>
    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        rownumbers:true,
        url: '${ctx}/pcs/pcsPollCandidate_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '推荐人',name: 'user.realname'},
            { label: '推荐人类型',name: 'type', width: 120,formatter: function (cellvalue, options, rowObject) {
                    return _cMap.PCS_POLL_CANDIDATE_TYPE[cellvalue];
                }},
            <c:if test="${!_query}">
            { label:'排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{grid:'#jqGrid2',url:'${ctx}/pcs/pcsPollCandidate_changeOrder'},frozen:true },
            </c:if>
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm2 [data-rel="select2"]').select2();
    $('#searchForm2 [data-rel="tooltip"]').tooltip();
</script>