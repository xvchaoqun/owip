<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<jsp:include page="/WEB-INF/jsp/cadre/colModels.jsp"/>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
            <div class="myTableDiv"
                 data-url-page="${ctx}/leaderInfo"
                 data-url-co="${ctx}/cadre_changeOrder"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.cadreId ||not empty param.title || not empty param.code }"/>

                <div class="tabbable">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                        <li class="<c:if test="${status==CADRE_STATUS_LEADER}">active</c:if>">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/leaderInfo?status=${CADRE_STATUS_LEADER}"><i
                                    class="fa fa-flag"></i> ${CADRE_STATUS_MAP.get(CADRE_STATUS_LEADER)}</a>
                        </li>
                        <li class="<c:if test="${status==CADRE_STATUS_LEADER_LEAVE}">active</c:if>">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/leaderInfo?status=${CADRE_STATUS_LEADER_LEAVE}"><i
                                    class="fa fa-flag"></i> ${CADRE_STATUS_MAP.get(CADRE_STATUS_LEADER_LEAVE)}</a>
                        </li>
                        <div class="buttons pull-left hidden-sm hidden-xs" style="left:50px; position: relative">
                            <a class="popupBtn btn btn-danger btn-sm"
                               data-url="${ctx}/cadre_search"><i class="fa fa-search"></i> 查询账号所属干部库</a>
                            <shiro:hasPermission name="cadre:edit">
                                <button type="button" class="popupBtn btn btn-info btn-sm"
                                        data-url="${ctx}/cadre_transfer"><i class="fa fa-recycle"></i> 干部库转移</button>
                            </shiro:hasPermission>
                        </div>
                    </ul>

                    <div class="tab-content">
                        <div class="tab-pane in active rownumbers multi-row-head-table">
                            <div class="jqgrid-vertical-offset buttons">
                                <shiro:hasPermission name="cadre:changeCode">
                                    <a href="javascript:;" class="jqEditBtn btn btn-warning btn-sm"
                                       data-url="${ctx}/cadre_changeCode"
                                       data-id-name="cadreId">
                                        <i class="fa fa-refresh"></i> 更换工作证号</a>
                                </shiro:hasPermission>

                                <shiro:hasPermission name="cadre:edit">
                                    <a class="popupBtn btn btn-info btn-sm btn-success"
                                       data-url="${ctx}/cadre_au?status=${status}"><i class="fa fa-plus"></i> 添加</a>


                                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                        data-url="${ctx}/cadre_au"
                                        data-querystr="&status=${status}">
                                    <i class="fa fa-edit"></i> 修改信息
                                </button>
                                <c:if test="${status==CADRE_STATUS_LEADER}">
                                    <button class="jqOpenViewBtn btn btn-warning btn-sm"
                                            data-width="700"
                                            data-url="${ctx}/cadre_leave">
                                        <i class="fa fa-sign-out"></i> 离任
                                    </button>
                                </c:if>
                                <%--<a class="popupBtn btn btn-info btn-sm tooltip-info"
                                   data-url="${ctx}/cadre_import?status=${status}"
                                   data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i> 批量导入</a>--%>
                                </shiro:hasPermission>
<%--                                <shiro:hasPermission name="cadre:export">--%>
<%--                                <a class="jqExportBtn btn btn-success btn-sm" data-url="${ctx}/cadre_data"--%>
<%--                                   data-rel="tooltip" data-placement="bottom" title="导出选中记录或所有搜索结果"><i--%>
<%--                                        class="fa fa-download"></i> 导出</a>--%>
<%--                                </shiro:hasPermission>--%>
                                <shiro:hasPermission name="cadre:export">
                                    <div class="btn-group">
                                        <button data-toggle="dropdown"
                                                data-rel="tooltip" data-placement="top" data-html="true"
                                                title="<div style='width:180px'>导出选中记录或所有搜索结果</div>"
                                                class="btn btn-success btn-sm dropdown-toggle tooltip-success">
                                            <i class="fa fa-download"></i> 导出 <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu dropdown-success" role="menu" style="z-index: 1031">
                                            <shiro:hasPermission name="cadre:list">
                                                <li class="dropdown-hover" data-stopPropagation="true">
                                                    <a href="javascript:;" data-need-id="false">
                                                        <i class="fa fa-file-excel-o"></i> 导出干部一览表（全部字段）
                                                        <i class="ace-icon fa fa-caret-right pull-right"></i>
                                                    </a>
                                                    <div class="dropdown-menu" style="width: 675px;top:-220px;">
                                                        <form class="form-horizontal" id="exportForm">
                                                            <div style="padding: 7px 0 10px 10px">
                                                                <c:forEach items="${titles}" var="title" varStatus="vs">
                                                                    <div style="padding-left:5px;float: left;width:220px">
                                                                        <input class="big" type="checkbox"
                                                                               value="${vs.index}">
                                                                            ${fn:split(title, "|")[0]}</div>
                                                                </c:forEach>
                                                                <div style="clear: both"/>
                                                            </div>
                                                            <div class="form-actions center">
                                                                <div style="position: absolute; float:left; left:10px;padding-top: 3px">
                                                                    <input type="button" id="btnSelectAll"
                                                                           class="btn btn-success btn-xs" value="全选"/>
                                                                    <input type="button" id="btnDeselectAll"
                                                                           class="btn btn-danger btn-xs" value="全不选"/>
                                                                </div>
                                                                <button type="button"
                                                                        class="jqExportBtn btn btn-success"
                                                                        data-need-id="false"
                                                                        data-url="${ctx}/cadre_data"
                                                                        data-querystr="format=1">
                                                                    <i class="fa fa-file-excel-o"></i> 导出
                                                                </button>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="cadre:list2">
                                                <li>
                                                    <a href="javascript:;" class="jqExportBtn"
                                                       data-need-id="false" data-url="${ctx}/cadre_data"
                                                       data-querystr="format=1">
                                                        <i class="fa fa-file-excel-o"></i> 导出干部一览表（全部字段）</a>
                                                </li>
                                            </shiro:hasPermission>
                                            <jsp:include page="${ctx}/ext/cadre_download.jsp"/>

                                            <c:if test="${status==CADRE_STATUS_LEADER}">
                                                <shiro:hasPermission name="cadre:list">
                                                    <li role="separator" class="divider"></li>
                                                    <li>
                                                        <a href="javascript:;" class="jqExportBtn"
                                                           data-need-id="false" data-url="${ctx}/cadre_data"
                                                           data-querystr="format=2">
                                                            <i class="fa fa-file-excel-o"></i> 导出干部名单（部分字段，可单页打印）</a>
                                                    </li>
                                                    <li role="separator" class="divider"></li>
                                                    <li>
                                                        <a href="javascript:;" class="jqExportBtn"
                                                           data-need-id="false" data-url="${ctx}/cadreEdu_data?isExportLeader=1&status=${status}">
                                                            <i class="fa fa-file-excel-o"></i> 导出学习经历（批量）</a>
                                                    </li>
                                                    <li role="separator" class="divider"></li>
                                                    <li>
                                                        <a href="javascript:;" class="jqExportBtn"
                                                           data-need-id="false" data-url="${ctx}/cadreWork_data?isExportLeader=1&status=${status}">
                                                            <i class="fa fa-file-excel-o"></i> 导出工作经历（批量）</a>
                                                    </li>
                                                    <li role="separator" class="divider"></li>
                                                    <li>
                                                        <a href="javascript:;" class="jqExportBtn"
                                                           data-need-id="false" data-url="${ctx}/cadreEva_data?exportType=2&status=${status}">
                                                            <i class="fa fa-file-excel-o"></i> 导出近五年考核结果（批量）</a>
                                                    </li>
                                                    <li role="separator" class="divider"></li>
                                                    <li>
                                                        <a href="javascript:;" class="jqExportBtn"
                                                           data-need-id="false" data-url="${ctx}/cesResult_data?type=1&status=${status}">
                                                            <i class="fa fa-file-excel-o"></i> 导出年终考核测评数据（批量）</a>
                                                    </li>
                                                </shiro:hasPermission>
                                                <shiro:hasPermission name="cadre:exportFamily">
                                                    <li role="separator" class="divider"></li>
                                                    <li>
                                                        <a href="javascript:;" class="jqExportBtn"
                                                           data-need-id="false" data-url="${ctx}/cadreFamily_data?exportType=2">
                                                            <i class="fa fa-file-excel-o"></i> 导出家庭成员（批量）</a>
                                                    </li>
                                                </shiro:hasPermission>
                                            </c:if>
                                            <li role="separator" class="divider"></li>
                                            <li>
                                                <a href="javascript:;" class="jqExportBtn"
                                                   data-need-id="false" data-url="${ctx}/cadreParttime_data?exportType=${status==CADRE_STATUS_LEADER?2:3}&status=${status}">
                                                    <i class="fa fa-file-excel-o"></i> 导出社会或学术兼职（批量）</a>
                                            </li>
                                            <li role="separator" class="divider"></li>
                                            <li>
                                                <a href="javascript:;" class="jqExportBtn"
                                                   data-need-id="false" data-url="${ctx}/cadreCompany_data?formatType=2&export=1&idType=2&exportType=${status==CADRE_STATUS_LEADER?2:3}&cadreStatus=${status}"
                                                   data-querystr="cadreStatus=${status}">
                                                    <i class="fa fa-file-excel-o"></i> 导出企业、社团兼职（批量）</a>
                                            </li>
                                            <li role="separator" class="divider"></li>
                                            <li>
                                                <a href="javascript:;" class="jqExportBtn"
                                                   data-need-id="false" data-url="${ctx}/cadreTrain_data?exportType=${status==CADRE_STATUS_LEADER?2:3}&status=${status}">
                                                    <i class="fa fa-file-excel-o"></i> 导出培训情况（批量）</a>
                                            </li>

                                            <shiro:hasPermission name="cadre:list">
                                                <li role="separator" class="divider"></li>
                                                <li>
                                                    <a href="javascript:;" class="jqExportBtn"
                                                       data-need-id="false"
                                                       data-export="2"
                                                       data-url="${ctx}/cadre_data?format=1">
                                                        <i class="fa fa-file-excel-o"></i> 导出Word版任免审批表（批量）</a>
                                                </li>
                                                <li role="separator" class="divider"></li>
                                                <li>
                                                    <a href="javascript:;" class="jqExportBtn"
                                                       data-need-id="false"
                                                       data-export="2"
                                                       data-url="${ctx}/cadre_data?format=2">
                                                        <i class="fa fa-file-excel-o"></i> 导出中组部版任免审批表（批量）</a>
                                                </li>
                                                <li role="separator" class="divider"></li>
                                                <li>
                                                    <a href="javascript:;" class="jqExportBtn"
                                                       data-need-id="false"
                                                       data-export="3"
                                                       data-url="${ctx}/cadre_data">
                                                        <i class="fa fa-file-excel-o"></i> 导出信息采集表（批量）</a>
                                                </li>
                                                <li role="separator" class="divider"></li>
                                                <li>
                                                    <a href="javascript:;" class="jqExportBtn"
                                                       data-need-id="false"
                                                       data-export="6"
                                                       data-url="${ctx}/cadre_data">
                                                        <i class="fa fa-file-excel-o"></i> 导出干部信息表（简版）（批量）</a>
                                                </li>
                                                <li role="separator" class="divider"></li>
                                                <li>
                                                    <a href="javascript:;" class="jqExportBtn"
                                                       data-need-id="false"
                                                       data-export="5"
                                                       data-url="${ctx}/cadre_data">
                                                        <i class="fa fa-info-circle"></i> 干部信息完整性校验结果（批量）</a>
                                                </li>

                                            </shiro:hasPermission>
                                        </ul>
                                    </div>

                                </shiro:hasPermission>



                                <shiro:hasPermission name="cadre:del">
                                    <button data-url="${ctx}/cadre_batchDel"
                                            data-title="删除"
                                            data-msg="确定删除这{0}条数据（<span class='text-danger'>相关联数据全部删除，不可恢复</span>）？"
                                            data-grid-id="#jqGrid"
                                            class="jqBatchBtn btn btn-danger btn-sm">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                </shiro:hasPermission>
                            </div>
                            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                                <div class="widget-header">
                                    <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

                                    <div class="widget-toolbar">
                                        <a href="javascript:;" data-action="collapse">
                                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                                        </a>
                                    </div>
                                </div>
                                <div class="widget-body">
                                    <div class="widget-main no-padding">
                                        <form class="form-inline search-form" id="searchForm">
                                            <input type="hidden" name="cols">
                                            <input type="hidden" name="sortBy">
                                            <input type="hidden" name="status" value="${status}">
                                            <div class="form-group">
                                                <label>姓名</label>

                                                <div class="input-group">
                                                    <input type="hidden" name="status" value="${status}">
                                                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects?status=${status}"
                                                            name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                        <option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"><i
                                                        class="fa fa-search"></i> 查找</a>

                                                <c:if test="${_query || not empty param.sort}">&nbsp;
                                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                            data-querystr="status=${status}">
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
        </div>
        <div id="body-content-view">

        </div>
    </div>
</div>
<script>
    $("ul.dropdown-menu").on("click", "[data-stopPropagation]", function (e) {
        //console.log($(e.target).hasClass("jqExportBtn"))
        if (!$(e.target).hasClass("jqExportBtn")) {
            e.stopPropagation();
        }
    });

    $("#btnSelectAll").click(function () {
        $("#exportForm :checkbox").prop("checked", true);
        _updateCols()
    });
    $("#btnDeselectAll").click(function () {
        $("#exportForm :checkbox").prop("checked", false);
        _updateCols()
    });
    function _updateCols() {
        var cols = $.map($("#exportForm :checkbox:checked"), function (chk) {
            return $(chk).val();
        });
        $("#searchForm input[name=cols]").val(cols.join(','));
    }
    $("#exportForm :checkbox").click(function () {
        _updateCols()
    });

    function _reAssignCallback(){
        $.hashchange('', '${ctx}/cadreInspect');
    }
    $("#jqGrid").jqGrid({
        //forceFit:true,
        rownumbers: true,
        url: '${ctx}/cadre_data?status=${status}&callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: ${status==CADRE_STATUS_LEADER?'colModels.cadre':'colModels.cadreLeave'}
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $('[data-rel="tooltip"]').tooltip();
    });

    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $('[data-rel="select2"]').select2();
    $.register.user_select($('#searchForm select[name=cadreId]'));
</script>