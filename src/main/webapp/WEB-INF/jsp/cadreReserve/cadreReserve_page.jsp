<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=CadreConstants.CADRE_RESERVE_STATUS_NORMAL%>" var="CADRE_RESERVE_STATUS_NORMAL"/>
<c:set value="<%=CadreConstants.CADRE_RESERVE_STATUS_ABOLISH%>" var="CADRE_RESERVE_STATUS_ABOLISH"/>
<c:set value="<%=CadreConstants.CADRE_RESERVE_STATUS_TO_INSPECT%>" var="CADRE_RESERVE_STATUS_TO_INSPECT"/>
<c:set value="<%=CadreConstants.CADRE_RESERVE_STATUS_ASSIGN%>" var="CADRE_RESERVE_STATUS_ASSIGN"/>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
            <div class="myTableDiv"
             data-url-page="${ctx}/cadreReserve"
             data-url-co="${ctx}/cadreReserve_changeOrder"
             data-url-export="${ctx}/cadreReserve_data?reserveType=${reserveType}"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.cadreId ||not empty param.gender ||not empty param.nation
                ||not empty param.startAge||not empty param.endAge||not empty param.startDpAge||not empty param.endDpAge
                ||not empty param.startNowPostAge||not empty param.endNowPostAge||not empty param.startNowLevelAge||not empty param.endNowLevelAge
                ||not empty param._birth||not empty param._cadreGrowTime
                ||not empty param.dpTypes||not empty param.unitIds||not empty param.unitTypes||not empty param.adminLevels
                ||not empty param.maxEdus||not empty param.major ||not empty param.staffTypes ||not empty param.degreeType
                ||not empty param.proPosts ||not empty param.postTypes ||not empty param.proPostLevels
                ||not empty param.isPrincipal ||not empty param.isDouble ||not empty param.hasCrp || not empty param.code
                ||not empty param.leaderTypes  ||not empty param.type
                 ||not empty param.state  ||not empty param.title ||not empty param.labels ||not empty param.workTypes
                 ||not empty param.hasAbroadEdu || not empty param.staffStatus|| not empty param.isTemp || not empty param.authorizedTypes || not empty param.remark}"/>
                <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">

                    <c:forEach var="_type" items="${cm:getMetaTypes('mc_cadre_reserve_type')}">
                        <li class="${reserveStatus==CADRE_RESERVE_STATUS_NORMAL&&_type.key==reserveType?'active':''}">
                            <a href="javascript:;" class="loadPage"
                               data-url="${ctx}/cadreReserve?reserveType=${_type.key}">
                                <i class="fa fa-flag"></i>
                                    ${_type.value.name}(${normalCountMap.get(_type.key)})</a>
                        </li>
                    </c:forEach>
                    <li class="${reserveStatus==5?'active':''}">
                        <a href="javascript:;" class="loadPage"
                           data-url="${ctx}/cadreReserve?reserveStatus=5">
                            <i class="fa fa-flag"></i>
                            全部年轻干部(${allNum})</a>
                    </li>
                    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                        <c:forEach var="_status" items="<%=CadreConstants.CADRE_RESERVE_STATUS_MAP%>">
                            <c:if test="${_status.key!=CADRE_RESERVE_STATUS_NORMAL}">
                                <li class="<c:if test="${reserveStatus==_status.key}">active</c:if>">
                                    <a href="javascript:;" class="loadPage"
                                       data-url="${ctx}/cadreReserve?reserveStatus=${_status.key}">
                                        <c:if test="${_status.key==CADRE_RESERVE_STATUS_ABOLISH}">
                                            <i class="fa fa-times"></i>
                                        </c:if>
                                        <c:if test="${_status.key==CADRE_RESERVE_STATUS_ASSIGN}">
                                            <i class="fa fa-check"></i>
                                        </c:if>
                                        <c:if test="${_status.key==CADRE_RESERVE_STATUS_TO_INSPECT}">
                                            <i class="fa fa-circle-o-notch fa-spin"></i>
                                        </c:if>
                                        <c:if test="${_status.key==CADRE_RESERVE_STATUS_ALL}">
                                            <i class="fa fa-flag"></i>
                                        </c:if>
                                        ${_status.value}(${statusCountMap.get(_status.key)})</a>
                                </li>
                            </c:if>
                        </c:forEach>

                        <div class="buttons pull-left hidden-sm hidden-xs" style="left:20px; position: relative">
                            <button type="button" class="popupBtn btn btn-danger btn-sm"
                                    data-url="${ctx}/cadreReserve/search"><i class="fa fa-search"></i> 查询账号所属类别
                            </button>
                        </div>
                    </shiro:lacksPermission>
                </ul>
                <div class="tab-content multi-row-head-table">
                    <div class="tab-pane in active rownumbers">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                                <c:if test="${reserveStatus==CADRE_RESERVE_STATUS_NORMAL}">
                                    <shiro:hasPermission name="cadreReserve:edit">
                                        <a class="popupBtn btn btn-info btn-sm btn-success"
                                           data-url="${ctx}/cadreReserve_au?reserveType=${reserveType}"><i
                                                class="fa fa-plus"></i>
                                            添加
                                        </a>

                                        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                                data-url="${ctx}/cadreReserve_au"
                                                data-querystr="&reserveStatus=${reserveStatus}&reserveType=${reserveType}">
                                            <i class="fa fa-edit"></i> 修改信息
                                        </button>

                                    <button class="jqOpenViewBtn btn btn-success btn-sm"
                                            data-url="${ctx}/cadreReserve_pass">
                                        <i class="fa fa-check"></i> 列为考察对象
                                    </button>

                                    <button data-url="${ctx}/cadreReserve_abolish"
                                            data-title="撤销"
                                            data-msg="确认撤销？"
                                            class="jqItemBtn btn btn-danger btn-sm">
                                        <i class="fa fa-times"></i> 撤销
                                    </button>
                                    <a class="popupBtn btn btn-info btn-sm tooltip-info"
                                       data-url="${ctx}/cadreReserve_import?reserveType=${reserveType}"
                                       data-rel="tooltip" data-placement="top" title="批量导入"><i
                                            class="fa fa-upload"></i> 批量导入</a>

                                    <button type="button" class="jqOpenViewBatchBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cadreReserve_transfer?reserveType=${reserveType}"><i class="fa fa-recycle"></i> 批量转移
                                    </button>
                                    </shiro:hasPermission>
                                </c:if>

                                <button class="jqOpenViewBtn btn btn-info btn-sm"
                                        data-url="${ctx}/cadreAdLog"
                                        data-id-name="reserveId"
                                        data-open-by="page">
                                    <i class="fa fa-search"></i> 任免操作记录
                                </button>

                                <c:if test="${reserveStatus==CADRE_RESERVE_STATUS_TO_INSPECT}">
                                    <shiro:lacksPermission name="cadreInspect:list">
                                    <button class="jqOpenViewBtn btn btn-success btn-sm"
                                            data-url="${ctx}/cadreReserve_inspectPass">
                                        <i class="fa fa-check"></i> 通过常委会任命
                                    </button>
                                    </shiro:lacksPermission>
                                    <button class="jqBatchBtn btn btn-danger btn-sm"
                                            data-url="${ctx}/cadreReserve_batchDelPass" data-title="删除"
                                            data-msg="确定删除这{0}条记录吗？（该考察对象的关联数据都将删除，不可恢复。）"><i class="fa fa-trash"></i>
                                        删除
                                    </button>
                                </c:if>
                                <c:if test="${reserveStatus==CADRE_RESERVE_STATUS_ABOLISH}">
                                    <button data-url="${ctx}/cadreReserve_unAbolish"
                                            data-title="重新入库"
                                            data-msg="确认重新入库？"
                                            class="jqItemBtn btn btn-success btn-sm">
                                        <i class="fa fa-reply"></i> 重新入库
                                    </button>
                                    <button class="jqBatchBtn btn btn-danger btn-sm"
                                            data-url="${ctx}/cadreReserve_batchDel" data-title="删除"
                                            data-msg="确定删除这{0}条记录吗？"><i class="fa fa-trash"></i> 删除
                                    </button>
                                </c:if>
                            </shiro:lacksPermission>

                            <c:if test="${reserveStatus==CADRE_RESERVE_STATUS_NORMAL}">
                                <div class="btn-group">
                                <button data-toggle="dropdown"
                                        data-rel="tooltip" data-placement="top" data-html="true"
                                        title="<div style='width:180px'>导出选中记录或所有搜索结果</div>"
                                        class="btn btn-success btn-sm dropdown-toggle tooltip-success"
                                        data-querystr="format=1">
                                    <i class="fa fa-download"></i> 导出  <span class="caret"></span>
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
                                                                data-url="${ctx}/cadreReserve_data"
                                                                data-querystr="format=1">
                                                            <i class="fa fa-file-excel-o"></i> 导出
                                                        </button>
                                                    </div>
                                                </form>
                                            </div>
                                        </li>
                                        <li role="separator" class="divider"></li>
                                        <li>
                                            <a href="javascript:;" class="jqExportBtn"
                                               data-need-id="false" data-url="${ctx}/cadreReserve_data?reserveType=${reserveType}"
                                               data-querystr="format=2">
                                                <i class="fa fa-file-excel-o"></i> 导出名单（部分字段，可单页打印）</a>
                                        </li>
                                        <li role="separator" class="divider"></li>
                                        <li>
                                            <a href="javascript:;" class="jqExportBtn"
                                               data-need-id="false" data-url="${ctx}/cadreEdu_data?exportType=1&reserveType=${reserveType}">
                                                <i class="fa fa-file-excel-o"></i> 导出学习经历（批量）</a>
                                        </li>
                                        <li role="separator" class="divider"></li>
                                        <li>
                                            <a href="javascript:;" class="jqExportBtn"
                                               data-need-id="false" data-url="${ctx}/cadreWork_data?exportType=1&reserveType=${reserveType}">
                                                <i class="fa fa-file-excel-o"></i> 导出工作经历（批量）</a>
                                        </li>
                                        <li role="separator" class="divider"></li>
                                        <li>
                                            <a href="javascript:;" class="jqExportBtn"
                                               data-need-id="false" data-url="${ctx}/cadreEva_data?exportType=1&reserveType=${reserveType}">
                                                <i class="fa fa-file-excel-o"></i> 导出近五年考核结果（批量）</a>
                                        </li>
                                        <li role="separator" class="divider"></li>
                                        <li>
                                            <a href="javascript:;" class="jqExportBtn"
                                               data-need-id="false" data-url="${ctx}/cadreFamily_data?exportType=1&reserveType=${reserveType}">
                                                <i class="fa fa-file-excel-o"></i> 导出家庭成员（批量）</a>
                                        </li>
                                        <li role="separator" class="divider"></li>
                                        <li>
                                            <a href="javascript:;" class="jqExportBtn"
                                               data-need-id="false" data-url="${ctx}/cadreParttime_data?exportType=1&reserveType=${reserveType}">
                                                <i class="fa fa-file-excel-o"></i> 导出社会或学术兼职（批量）</a>
                                        </li>
                                        <li role="separator" class="divider"></li>
                                        <li>
                                            <a href="javascript:;" class="jqExportBtn"
                                               data-need-id="false" data-url="${ctx}/cadreCompany_data?exportType=1&reserveType=${reserveType}"
                                               data-querystr="cadreStatus=${status}">
                                                <i class="fa fa-file-excel-o"></i> 导出企业、社团兼职（批量）</a>
                                        </li>
                                        <li role="separator" class="divider"></li>
                                        <li>
                                            <a href="javascript:;" class="jqExportBtn"
                                               data-need-id="false" data-url="${ctx}/cadreTrain_data?exportType=1&reserveType=${reserveType}">
                                                <i class="fa fa-file-excel-o"></i> 导出培训情况（批量）</a>
                                        </li>
                                        <li role="separator" class="divider"></li>
                                        <li>
                                            <a href="javascript:;" class="jqExportBtn"
                                               data-need-id="false"
                                               data-export="2"
                                               data-url="${ctx}/cadreReserve_data?format=1">
                                                <i class="fa fa-file-excel-o"></i> 导出Word版任免审批表（批量）</a>
                                        </li>
                                        <li role="separator" class="divider"></li>
                                        <li>
                                            <a href="javascript:;" class="jqExportBtn"
                                               data-need-id="false"
                                               data-export="2"
                                               data-url="${ctx}/cadreReserve_data?format=2">
                                                <i class="fa fa-file-excel-o"></i> 导出中组部版任免审批表（批量）</a>
                                        </li>
                                        <li role="separator" class="divider"></li>
                                        <li>
                                            <a href="javascript:;" class="jqExportBtn"
                                               data-need-id="false"
                                               data-export="3"
                                               data-url="${ctx}/cadreReserve_data">
                                                <i class="fa fa-file-excel-o"></i> 导出信息采集表（批量）</a>
                                        </li>
                                        <li role="separator" class="divider"></li>
                                        <li>
                                            <a href="javascript:;" class="jqExportBtn"
                                               data-need-id="false"
                                               data-export="6"
                                               data-url="${ctx}/cadreReserve_data">
                                                <i class="fa fa-file-excel-o"></i> 导出干部信息表（简版）（批量）</a>
                                        </li>
                                    </shiro:hasPermission>
                                </ul>
                            </div>
                            </c:if>
                            <div class="pull-right hidden-sm hidden-xs">
                                <select id="sortBy" data-placeholder="请选择排序方式" data-width="250">
                                    <option></option>
                                    <option value="birth_asc">按年龄排序(降序)</option>
                                    <option value="birth_desc">按年龄排序(升序)</option>
                                    <option value="npWorkTime_asc">按现职务始任年限排序(降序)</option>
                                    <option value="npWorkTime_desc">按现职务始任年限排序(升序)</option>
                                    <option value="sWorkTime_asc">按现职级年限排序(降序)</option>
                                    <option value="sWorkTime_desc">按现职级年限排序(升序)</option>
                                    <option value="growTime">按党派加入时间排序</option>
                                    <option value="arriveTime">按到校日期排序</option>
                                    <option value="finishTime">按毕业时间排序</option>
                                </select>
                                <script>
                                    $("#sortBy").val('${param.sortBy}');
                                    $("#searchForm input[name=sortBy]").val('${param.sortBy}');
                                    $("#sortBy").select2({
                                        theme: "default"
                                    }).change(function () {
                                        $("#searchForm input[name=sortBy]").val($(this).val());
                                        $("#searchForm .jqSearchBtn").click();
                                        if($(this).val()==''){
                                            throw new Error();
                                        }
                                    })
                                </script>
                            </div>
                        </div>
                        <div class="jqgrid-vertical-offset widget-box collapsed hidden-sm hidden-xs">
                            <div class="widget-header">
                                <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

                                <div class="widget-toolbar">
                                    <a href="javascript:;" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-down}"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main no-padding">
                                    <form class="form-inline search-form" id="searchForm">
                                        <input type="hidden" name="cols">
                                        <input type="hidden" name="sortBy">
                                        <input name="reserveType" type="hidden" value="${reserveType}">
                                        <input name="reserveStatus" type="hidden" value="${reserveStatus}">
                                        <div class="columns">
                                            <div class="column">
                                                <label>姓名</label>
                                                <div class="input">
                                                    <select data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/cadreReserve_selects?reserveStatus=${reserveStatus}&reserveType=${reserveType}"
                                                            name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                        <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <jsp:include page="/WEB-INF/jsp/cadre/cadre_searchColumns.jsp"/>
                                            <div class="column">
                                                <label>是否在职</label>
                                                <div class="input">
                                                    <select data-width="100" data-rel="select2" data-placeholder="请选择" name="staffStatus">
                                                        <option></option>
                                                        <c:forEach items="${staffStatuses}" var="staffStatus">
                                                            <option value="${staffStatus}">${staffStatus}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <script>
                                                        $("#searchForm select[name=staffStatus]").val('${param.staffStatus}');
                                                    </script>
                                                </div>
                                            </div>
                                            <c:if test="${fn:length(isTemps)>0}">
                                                <div class="column">
                                                    <label>是否临时人员</label>
                                                    <div class="input">
                                                        <select data-width="100" data-rel="select2" data-placeholder="请选择" name="isTemp">
                                                            <option></option>
                                                            <c:forEach items="${isTemps}" var="isTemp">
                                                                <option value="${isTemp}">${isTemp}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=isTemp]").val('${param.isTemp}');
                                                        </script>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </div>
                                        <div class="clearfix"></div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm" data-method="POST"><i
                                                    class="fa fa-search"></i> 查找</a>

                                            <c:if test="${_query || not empty param.sort}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-querystr="reserveStatus=${reserveStatus}&reserveType=${reserveType}">
                                                    <i class="fa fa-reply"></i> 重置
                                                </button>
                                            </c:if>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="space-4"></div>

                        <table id="jqGrid" class="jqGrid table-striped" data-height-reduce="1"></table>
                        <div id="jqGridPager"></div>

                    </div>
                </div>
            </div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script type="text/template" id="sort_tpl">
    <a href="javascript:;" class="jqOrderBtn" data-id="{{=id}}" data-direction="1" title="上升"><i
            class="fa fa-arrow-up"></i></a>
    <input type="text" value="1" class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
           title="修改操作步长">
    <a href="javascript:;" class="jqOrderBtn" data-id="{{=id}}" data-direction="-1" title="下降"><i
            class="fa fa-arrow-down"></i></a>
</script>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
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
    $("#exportForm :checkbox").click(function () {
        _updateCols()
    });

    function _updateCols() {
        var cols = $.map($("#exportForm :checkbox:checked"), function (chk) {
            return $(chk).val();
        });
        $("#searchForm input[name=cols]").val(cols.join(','));
    }

    $("#jqGrid").jqGrid({
        //forceFit:true,
        rownumbers: true,
        mtype:'POST',
        postData:${cm:toJSONObject(pageContext.request.parameterMap)},
        url: '${ctx}/cadreReserve_data?reserveType=${reserveType}&callback=?',
        colModel: [
            {label: '工作证号', name: 'user.code', width: 110, frozen: true},
            {
                label: '姓名', name: 'user.realname', width: 100, formatter: function (cellvalue, options, rowObject) {
                    return $.cadre(rowObject.id, cellvalue);
                }, frozen: true
            },
            <shiro:hasPermission name="cadreReserve:changeOrder">
            <c:if test="${reserveStatus==CADRE_RESERVE_STATUS_NORMAL}">
            {
                label: '排序', width: 80, index: 'sort', formatter: function (cellvalue, options, rowObject) {
                    return _.template($("#sort_tpl").html().NoMultiSpace())({id: rowObject.reserveId})
                }, frozen: true
            },
            </c:if>
            </shiro:hasPermission>
            {label: '部门属性', name: 'unit.unitType.name', width: 150},
            {label: '所在单位', name: 'unit.name', width: 200, align: 'left'},
            {label: '现任职务', name: 'post', align: 'left', width: 250},
            {label: '所在单位及职务', name: 'title', align: 'left', width: 350},
            <c:if test="${reserveStatus==5}">
            {
                label: '类别',name: 'reserveType',width: 130,
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    return _cMap.metaTypeMap[cellvalue].name;

                }
            },
            </c:if>
            {label: '任职时间', name: 'reservePostTime', width: 80, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '行政级别', name: 'adminLevel', formatter: $.jgrid.formatter.MetaType},
            {label: '职务属性', name: 'postType', width: 150, formatter: $.jgrid.formatter.MetaType},
            {label: '是否正职', name: 'isPrincipal', formatter: $.jgrid.formatter.TRUEFALSE},
            {label: '性别', name: 'gender', width: 50, formatter: $.jgrid.formatter.GENDER},
            {label: '民族', name: 'nation', width: 60},
            {label: '籍贯', name: 'nativePlace', width: 120},
            {label: '身份证号', name: 'idcard', width: 160},
            {label: '出生时间', name: 'birth', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE},
            {label: '政治面貌', name: '_cadreParty', width: 80, width: 80, formatter: $.jgrid.formatter.cadreParty},
            {label: '党派<br/>加入时间', name: '_growTime', width: 80, formatter: $.jgrid.formatter.growTime},
            {label: '党龄', name: '_growAge', width: 50, formatter: $.jgrid.formatter.growAge},
            {label: '到校日期', name: 'arriveTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '最高学历', name: 'eduId', formatter: $.jgrid.formatter.MetaType},
            {label: '最高学位', name: 'degree'},
            {label: '毕业时间', name: 'finishTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '学习方式', name: 'learnStyle', formatter: $.jgrid.formatter.MetaType},
            {label: '毕业学校、学院', name: 'school', width: 150},
            {
                label: '学校类型', name: 'schoolType', formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    return _cMap.CADRE_SCHOOL_TYPE_MAP[cellvalue]
                }
            },
            {label: '所学专业', name: 'major'},
            {label: '专业技术职务', name: 'proPost', width: 120},
            {
                label: '现职务任命文件',
                width: 150,
                name: 'npDispatch',
                formatter: function (cellvalue, options, rowObject) {
                    if (!cellvalue || cellvalue.id == undefined) return '--';
                    var dispatchCode = cellvalue.dispatchCode;
                    return $.pdfPreview(cellvalue.file, cellvalue.fileName, dispatchCode, dispatchCode);
                }
            },
            {
                label: '任现职时间',
                name: 'lpWorkTime',
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {
                label: '现职务<br/>始任时间',
                width: 90,
                name: 'npWorkTime',
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {
                label: '任现职务<br/>年限',
                width: 70,
                name: 'cadrePostYear',
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    return cellvalue == 0 ? "未满一年" : cellvalue;
                }
            },
            {
                label: '职级<br/>始任日期',
                width: 90,
                name: 'sWorkTime',
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {
                label: '任职级<br/>年限',
                width: 60,
                name: 'adminLevelYear',
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    return cellvalue == 0 ? "未满一年" : cellvalue;
                }
            },
            {label: '是否有<br/>挂职经历', width:80, name: 'hasCrp', formatter: $.jgrid.formatter.TRUEFALSE},
            {
                label: '是否<br/>双肩挑', width:70, name: 'isDouble', formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    return cellvalue ? "是" : "否";
                }
            },
            {
                label: '双肩挑单位',
                name: 'doubleUnitIds',
                width: 250,
                formatter: function (cellvalue, options, rowObject) {

                    if ($.trim(cellvalue) == '') return '--'
                    return ($.map(cellvalue.split(","), function (unitId) {
                        return $.jgrid.formatter.unit(unitId);
                    })).join("，")
                }
            },
            {label: '联系方式', name: 'mobile',width:110},
            {label: '电子邮箱', name: 'email', width: 180},
            <c:if test="${_p_hasPartyModule}">
            {
                label: '所在党组织',
                name: 'partyId',
                align: 'left',
                width: 550,
                formatter: function (cellvalue, options, rowObject) {
                    return $.party(rowObject.partyId, rowObject.branchId);
                }
            },
            </c:if>
            <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
            {
                label: '短信称谓', name: 'msgTitle', width: 80, formatter: function (cellvalue, options, rowObject) {
                    // 短信称谓
                    var msgTitle = $.trim(cellvalue);
                    if (msgTitle == '' || msgTitle == rowObject.user.realname) {
                        msgTitle = '无'
                    }
                    return msgTitle;
                }
            },
            </shiro:lacksPermission>
            {label: '备注', name: 'reserveRemark', width: 150}, {hidden: true, key: true, name: 'reserveId'}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $('[data-rel="tooltip"]').tooltip();
    });
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('[data-rel="select2"]').select2();
    $.register.user_select($('#searchForm select[name=cadreId]'));
</script>