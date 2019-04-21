<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/modifyCadreAuth"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.cadreId || not empty param.cadreStatus || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="modifyCadreAuth:edit">
                    <a class="openView btn btn-success btn-sm"
                       data-url="${ctx}/modifyCadreAuth_batchAdd"
                            data-open-by="page"><i class="fa fa-plus-square"></i> 批量添加</a>
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/modifyCadreAuth_au"><i class="fa fa-user-plus"></i> 个别添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/modifyCadreAuth_au"><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="modifyCadreAuth:del">
                    <button data-url="${ctx}/modifyCadreAuth_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                    <button data-url="${ctx}/modifyCadreAuth_remove?status[]=${CADRE_STATUS_MIDDLE},${CADRE_STATUS_LEADER},${CADRE_STATUS_MIDDLE_LEAVE},${CADRE_STATUS_LEADER_LEAVE}"
                            data-title="一键清理"
                            data-msg="确认清除现有的权限设置（针对“现任/离任干部库”中的干部）？"
                            data-callback="_refresh"
                            class="confirm btn btn-warning btn-sm">
                        <i class="fa fa-trash"></i> 一键清理
                    </button>
                </shiro:hasPermission>
            </div>
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
                            <label>干部</label>
                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects?type=0"
                                    name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                <option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>所属干部库</label>
                            <select class="multiselect" multiple="" name="cadreStatus">
                                <c:forEach items="${CADRE_STATUS_MAP}" var="entity">
                                    <c:if test="${entity.key>0}">
                                    <option value="${entity.key}">${entity.value}</option>
                                    </c:if>
                                </c:forEach>
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
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $.register.multiselect($('#searchForm select[name=cadreStatus]'), ${cm:toJSONArray(selectCadreStatus)});

    function _refresh(){
        $("#jqGrid").trigger("reloadGrid");
    }
    $("#jqGrid").jqGrid({
        url: '${ctx}/modifyCadreAuth_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '设置时间',name: 'addTime', width: 150},
            { label: '工作证号', name: 'cadre.code', width: 120,frozen:true },
            { label: '姓名', name: 'cadre.realname', width: 120,frozen:true , formatter:function(cellvalue, options, rowObject){
                return $.cadre(rowObject.cadre.id, cellvalue);
            }},
            { label: '所在单位及职务', name: 'cadre.title', width: 250, align:'left'},
            { label: '所属干部库', name: 'cadre.status', width: 250, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--';
                return _cMap.CADRE_STATUS_MAP[cellvalue];
            }},
            { label: '起始日期',name: 'startTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}},
            { label: '结束日期',name: 'endTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}},
            { label: '是否永久有效',name: 'isUnlimited', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--';
                return cellvalue ? "是" : "否"
            }},
            { label: '创建人',name: 'addUser.realname'},
            { label: 'IP',name: 'addIp', width: 120}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.user_select($('#searchForm select[name=cadreId]'));
</script>