<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="jqGridId" value="${param._auth!='1'?'jqGrid':'jqGrid_eva'}"/>
<c:set var="jqGridPagerId" value="${param._auth!='1'?'jqGridPager':'jqGridPager_eva'}"/>
<c:set var="jqGridClass" value="${param._auth!='1'?'jqGrid':'jqGrid2'}"/>
<div class="row" <c:if test="${param._auth!='1'}">id="body-content"</c:if>>
    <div class="col-xs-12">
        <c:set var="_query"
               value="${not empty param.cadreId || not empty param.year || not empty param.type || not empty param.title}"/>
        <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN)}">
            <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                <div class="jqgrid-vertical-offset buttons">
                    <shiro:hasPermission name="cadreEva:edit">
                        <button class="popupBtn btn btn-info btn-sm"
                                data-url="${ctx}/cadreEva_au?cadreId=${param.cadreId}&_auth=${param._auth}">
                            <i class="fa fa-plus"></i> 添加
                        </button>
                        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                data-url="${ctx}/cadreEva_au"
                                data-querystr="&cadreId=${param.cadreId}&_auth=${param._auth}"
                                data-grid-id="#${jqGridId}"><i class="fa fa-edit"></i>
                            修改
                        </button>
                        <c:if test="${param._auth!=1}">
                            <button class="popupBtn btn btn-info btn-sm tooltip-info"
                                    data-url="/cadreEva_import?cadreId=${param.cadreId}"
                                    data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                                批量导入
                            </button>
                        </c:if>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="cadreEva:del">
                        <button data-url="${ctx}/cadreEva_batchDel"
                                data-title="删除"
                                data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                                data-grid-id="#${jqGridId}"
                                class="jqBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> 删除
                        </button>
                    </shiro:hasPermission>
                    <c:if test="${param._auth!=1}">
                        <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                data-url="${ctx}/cadreEva_data?cadreId=${param.cadreId}" data-grid-id="#${jqGridId}"
                                data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                            <i class="fa fa-download"></i> 导出
                        </button>
                    </c:if>
                </div>
            </shiro:lacksPermission>

            <c:if test="${param._auth!='1'}">
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
                                    <div class="input-group" style="width: 120px">
                                        <input class="form-control date-picker" placeholder="请选择年份" name="year"
                                               type="text"
                                               data-date-format="yyyy" data-date-min-view-mode="2"
                                               value="${param.year}"/>
                                        <span class="input-group-addon"> <i
                                                class="fa fa-calendar bigger-110"></i></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label> 姓名</label>
                                    <select data-rel="select2-ajax"
                                            data-ajax-url="${ctx}/cadre_selects?status=${CADRE_STATUS_CJ},${CADRE_STATUS_KJ},${CADRE_STATUS_LEADER}"
                                            name="cadreId" data-placeholder="请选择">
                                        <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>考核情况</label>
                                    <select data-rel="select2" data-width="120"
                                            name="type" data-placeholder="请选择">
                                        <option></option>
                                        <c:import url="/metaTypes?__code=mc_cadre_eva"/>
                                    </select>
                                    <script type="text/javascript">
                                        $("#searchForm select[name=type]").val(${param.type});
                                    </script>
                                </div>
                                <div class="form-group">
                                    <label>时任职务</label>
                                    <input class="form-control search-query" name="title" type="text"
                                           value="${param.title}">
                                </div>

                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"
                                       data-url="${ctx}/cadreEva?cls=1"
                                       data-target="#page-content"
                                       data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                data-url="${ctx}/cadreEva?cls=1"
                                                data-target="#page-content">
                                            <i class="fa fa-reply"></i> 重置
                                        </button>
                                    </c:if>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </c:if>

        </c:if>

        <div class="space-4"></div>
        <table id="${jqGridId}" class="${jqGridClass}"></table>
        <div id="${jqGridPagerId}"></div>
    </div>
</div>
<c:if test="${param._auth!='1'}">
    <div id="body-content-view"></div>
</c:if>
<script>
    function _reloadGrid(){
        $("#${jqGridId}").trigger("reloadGrid");
    }
    $.register.date($('.date-picker'));
    $("#${jqGridId}").jqGrid({
        pager: "#${jqGridPagerId}",
        rownumbers: true,
        url: '${ctx}/cadreEva_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年份', name: 'year', width: 80},
            <c:if test="${param.cls==1}">
            {label: '工作证号', name: 'cadre.code', width: 110},
            {
                label: '姓名', name: 'cadre.realname', width: 120, formatter: function (cellvalue, options, rowObject) {
                    return $.cadre(rowObject.cadre.id, cellvalue);
                }, frozen: true
            },
            </c:if>
            {label: '考核情况', name: 'type', formatter: $.jgrid.formatter.MetaType},
            {label: '时任职务', name: 'title', width: 400, align: 'left'},
            {label: '备注', name: 'remark', width: 300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.${jqGridClass}');
    $.initNavGrid("${jqGridId}", "${jqGridPagerId}");
    $.register.user_select($('#searchForm select[name=cadreId]'));
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>