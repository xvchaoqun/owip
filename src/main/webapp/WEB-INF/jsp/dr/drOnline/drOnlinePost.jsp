<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.name || not empty param.onlineType||not empty param.postType
                ||not empty param.unitTypes||not empty param.unitId||not empty param.adminLevel}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="tab-content multi-row-head-table">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
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
                                    <form class="form-inline search-form" id="searchForm">
                                        <div class="form-group">
                                            <label>推荐职务</label>
                                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                                   placeholder="请输入推荐职务">
                                        </div>
                                        <div class="form-group">
                                            <label>推荐类型</label>
                                            <select data-width="230" name="onlineType" data-rel="select2" data-placeholder="请选择">
                                                <option></option>
                                                <c:import url="/metaTypes?__code=mc_dr_type"/>
                                            </select>
                                            <script>         $("#searchForm select[name=onlineType]").val('${param.onlineType}');     </script>
                                        </div>
                                        <div class="form-group">
                                            <label>岗位级别</label>
                                            <select  data-rel="select2" name="adminLevel" data-width="120" data-placeholder="请选择">
                                                <option></option>
                                                <jsp:include page="/metaTypes?__code=mc_admin_level"/>
                                            </select>
                                            <script type="text/javascript">
                                                $("#searchForm select[name=adminLevel]").val('${param.adminLevel}');
                                            </script>
                                        </div>
                                        <div class="form-group">
                                            <label>职务属性</label>
                                            <select name="postType" data-rel="select2" data-placeholder="请选择职务属性">
                                                <option></option>
                                                <c:import url="/metaTypes?__code=mc_post"/>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=postType]").val('${param.postType}');
                                            </script>
                                        </div>
                                        <div class="form-group">
                                            <label>所属单位</label>
                                            <select name="unitId" data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                                                    data-placeholder="请选择所属单位">
                                                <option value="${unit.id}" delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                                            </select>
                                            <script>
                                                $.register.del_select($("#searchForm select[name=unitId]"), 250)
                                            </script>
                                        </div>
                                        <div class="form-group">
                                            <label>单位类型</label>
                                            <select class="multiselect" multiple="" name="unitTypes">
                                                <c:import url="/metaTypes?__code=mc_unit_type"/>
                                            </select>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"
                                               data-url="${ctx}/dr/drOnline?cls=2"
                                               data-target="#page-content"
                                               data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-url="${ctx}/dr/drOnline?cls=2"
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
        <table id="jqGrid" class="jqGrid table-striped" data-height-reduce="35"></table>
        <div id="jqGridPager"></div>
                    </div>
                </div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>

    $.register.multiselect($('#searchForm select[name=unitTypes]'), ${cm:toJSONArray(selectUnitTypes)});

    $("#jqGrid").jqGrid({
        pager: "jqGridPager",
        url: '${ctx}/dr/drOnlinePost_data?callback=?&cls=2&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '推荐编号',name: 'drOnline.code', frozen: true, width: 210},
                { label: '推荐类型',name: 'onlineType', frozen: true, width: 105, formatter: $.jgrid.formatter.MetaType},
                { label: '推荐职务',name: 'name', width: 252, frozen: true},
                { label: '最多推荐<br/>人数',name: 'competitiveNum',width:75},
                { label: '分管工作',name: 'job', width: 180},
                { label: '岗位级别',name: 'adminLevel', width: 100, formatter: $.jgrid.formatter.MetaType},
                { label: '职务属性',name: 'postType', width: 120, formatter: $.jgrid.formatter.MetaType},
                { label: '所属单位',name: 'unitId', width: 200, formatter: $.jgrid.formatter.unit},
                { label: '单位类型',name: 'typeId', width: 120, formatter: $.jgrid.formatter.MetaType},
                {hidden: true, key: true, name: 'id'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>