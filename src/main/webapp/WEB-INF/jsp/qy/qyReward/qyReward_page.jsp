<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=QyConstants.QY_REWARD_MAP%>" var="QY_REWARD_MAP"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.name ||not empty param.type || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">

                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="${cls==1?'active':''}">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/qyReward"}><i class="fa fa-list"></i>
                            奖项设置</a>
                    </li>

                    <li class="${cls==2?'active':''}">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/qyYear"}><i
                                class="fa fa-calendar-o"></i> 年度设置</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="qyReward:edit">
                                <button class="popupBtn btn btn-info btn-sm"
                                        data-url="${ctx}/qyReward_au">
                                    <i class="fa fa-plus"></i> 添加
                                </button>
                                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                        data-url="${ctx}/qyReward_au"
                                        data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                    修改
                                </button>
                                <button data-url="${ctx}/qyReward_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？(删除奖项时，该奖项关联的获奖记录及对象将一并删除，请谨慎操作！)"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
                          <%--  <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                    data-url="${ctx}/qyReward_data"
                                    data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-download"></i> 导出
                            </button>--%>
                        </div>


                        <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
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
                                            <label>奖项名称</label>
                                            <input class="form-control search-query" name="name" type="text"
                                                   value="${param.name}"
                                                   placeholder="请输入奖项名称">
                                        </div>
                                        <div class="form-group">
                                            <label>奖励对象</label>
                                            <select required data-rel="select2" name="type" data-placeholder="请选择">
                                                <option></option>
                                                <c:forEach var="_type" items="${QY_REWARD_MAP}">
                                                    <option value="${_type.key}">${_type.value}</option>
                                                </c:forEach>
                                            </select>
                                            <script type="text/javascript">
                                                $("#searchForm select[name=type]").val(${param.type});
                                            </script>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"
                                               data-url="${ctx}/qyReward"
                                               data-target="#page-content"
                                               data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-url="${ctx}/qyReward"
                                                        data-target="#page-content">
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
                    <div id="body-content-view"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/qyReward_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '奖项名称', name: 'name', width: 250,align:'left'},
            {label: '奖励对象', name: 'type', frozen: true, formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '-'
                    return _cMap.QY_REWARD_MAP[cellvalue];
                }
            },
            {label: '备注', name: 'remark', width: 300,align:'left'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>