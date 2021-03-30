<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="jqGridId" value="${param._auth!='1'?'jqGrid':'jqGrid_ces'}"/>
<c:set var="jqGridPagerId" value="${param._auth!='1'?'jqGridPager':'jqGridPager_ces'}"/>
<c:set var="jqGridClass" value="${param._auth!='1'?'jqGrid':'jqGrid2'}"/>
<div class="row" <c:if test="${param._auth!='1'}">id="body-content"</c:if>>
    <div class="col-xs-12">
        <c:set var="_query" value="${not empty param.year || not empty param.name || not empty param.title || not empty param.unitId || not empty param.cadreId}"/>

        <div class="jqgrid-vertical-offset buttons">
            <shiro:hasPermission name="cesResult:edit">
                <button class="popupBtn btn btn-info btn-sm"
                        data-url="${ctx}/cesResult_au?cadreId=${param.cadreId}&type=${param.type}&_auth=${param._auth}&unitId=${param.unitId}">
                    <i class="fa fa-plus"></i> 添加</button>
                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                        data-url="${ctx}/cesResult_au?type=${param.type}&_auth=${param._auth}&cadreId=${param.cadreId}&unitId=${param.unitId}"
                        data-grid-id="#${jqGridId}"><i class="fa fa-edit"></i>
                    修改</button>
                <c:if test="${param._auth!=1}">
                <button class="popupBtn btn btn-info btn-sm tooltip-info" data-url="/cesResult_import_page?type=${param.type}&_cadreId=${param.cadreId}&_unitId=${param.unitId}" data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                    批量导入
                </button>
                </c:if>
            </shiro:hasPermission>
            <shiro:hasPermission name="cesResult:del">
                <button data-url="${ctx}/cesResult_batchDel"
                        data-title="删除"
                        data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                        data-grid-id="#${jqGridId}"
                        class="jqBatchBtn btn btn-danger btn-sm">
                    <i class="fa fa-trash"></i> 删除
                </button>
            </shiro:hasPermission>
            <c:if test="${param._auth!=1}">
             <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                data-url="${ctx}/cesResult_data?type=${param.type}&cadreId=${param.cadreId}&unitId=${param.unitId}" data-grid-id="#${jqGridId}"
                data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                 <i class="fa fa-download"></i> 导出</button>
            </c:if>
        </div>
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
                            <label>年份</label>
                            <div class="input-group" style="width: 120px">
                                <input class="form-control date-picker" placeholder="请选择" name="year" type="text"
                                       data-date-format="yyyy" data-date-min-view-mode="2" value="${param.year}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>测评类别</label>
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入测评类别">
                        </div>
                        <shiro:hasPermission name="cesResult:edit">
                        <c:if test="${param.type==CES_RESULT_TYPE_UNIT}">
                            <div class="form-group">
                                <label>所属单位</label>
                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                                        name="unitId" data-placeholder="请选择" required>
                                    <option value="${unit.id}" delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                                </select>
                            </div>
                        </c:if>
                        </shiro:hasPermission>
                        <c:if test="${param.type==CES_RESULT_TYPE_CADRE}">
                            <div class="form-group">
                                <label> 姓名</label>
                                <select data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/cadre_selects?status=${CADRE_STATUS_CJ},${CADRE_STATUS_KJ},${CADRE_STATUS_LEADER}"
                                        name="cadreId" data-placeholder="请选择" required>
                                    <option value="${cesResult.id}">${cesResult.user.realname}-${cesResult.user.code}</option>
                                </select>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label>${param.type==CES_RESULT_TYPE_CADRE?'时任职务':'班子名称'}</label>
                            <input class="form-control search-query" name="title" type="text" value="${param.title}">
                        </div>

                        <div class="clearfix form-actions center">
                            <a class="jqSearchBtn btn btn-default btn-sm"
                               data-url="${ctx}/cesResult?type=${param.type}"
                               data-target="#page-content"
                               data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                            <c:if test="${_query}">&nbsp;
                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                        data-url="${ctx}/cesResult?type=${param.type}"
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
        <div class="space-4"></div>
        <table id="${jqGridId}" class="${jqGridClass} table-striped"></table>
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
        rownumbers:true,
        url: '${ctx}/cesResult_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '年份',name: 'year',width: 80, class:"jqgrow"},
            <c:if test="${param._auth!='1' && param.type==CES_RESULT_TYPE_CADRE}">
            { label: '工作证号',name: 'cadre.code', width:110},
            {
                label: '姓名', name: 'cadre.realname', width: 120, formatter: function (cellvalue, options, rowObject) {
                    return $.cadre(rowObject.cadre.id, cellvalue);
                }, frozen: true
            },
            </c:if>
            <c:if test="${param._auth!='1'}">
            { label: '${param.type==CES_RESULT_TYPE_CADRE?"时任单位":"所在单位"}', name: 'unit.id', width: 250, align:'left', formatter:$.jgrid.formatter.unit,frozen:true },
            </c:if>
            <c:if test="${param._auth=='1'}">
            { label: '${param.type==CES_RESULT_TYPE_CADRE?"时任单位":"所在单位"}', name: 'unit.name', width:250,align:"left"},
            </c:if>
            { label: '${param.type==CES_RESULT_TYPE_CADRE?"时任职务":"班子名称"}', name: 'title', width:250,align:"left"},
            { label: '测评类别',name: 'name',width: 250,align:"left"},
            { label:'排名', name:'rank',width: 80},
            { label: "${param.type == CES_RESULT_TYPE_CADRE ? '总人数' : '班子总人数'}", name: 'num'},
            { label: '备注',name: 'remark',width: 350}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.${jqGridClass}');
    $.initNavGrid("${jqGridId}", "${jqGridPagerId}");
    $('[data-rel="tooltip"]').tooltip();
    $.register.user_select($('#searchForm select[name=cadreId]'));
    $.register.ajax_select($('#searchForm select[name=unitId]'));
</script>