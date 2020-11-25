<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<c:set value="<%=CetConstants.CET_UNIT_PROJECT_STATUS_UNREPORT%>" var="_UNREPORT"/>
<c:set value="<%=CetConstants.CET_UNIT_PROJECT_STATUS_REPORT%>" var="_REPORT"/>
<c:set value="<%=CetConstants.CET_UNIT_PROJECT_STATUS_PASS%>" var="_PASS"/>
<c:set value="<%=CetConstants.CET_UNIT_PROJECT_STATUS_UNPASS%>" var="_UNPASS"/>
<c:set value="<%=CetConstants.CET_UNIT_PROJECT_STATUS_DELETE%>" var="_DELETE"/>

<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers multi-row-head-table" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year ||not empty param.projectName ||
             not empty param.cetPartyId || not empty param.unitId || not empty param.startDate || not empty param.endDate || not empty param.projectTypeId
             || not empty param.isOnline || not empty param.reportName || not empty param.reporter || not empty param.prePeriod || not empty param.subPeriod
              || not empty param.address}"/>

            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <li class="<c:if test="${cls==1}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/cet/cetUnitProject?cls=1"><i
                            class="fa fa-list"></i> 已审批(${cm:trimToZero(statusCountMap.get(_PASS))})
                    </a>
                </li>
                <li class="<c:if test="${cls==2}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/cet/cetUnitProject?cls=2"><i
                            class="fa fa-circle-o"></i> 待报送(${cm:trimToZero(statusCountMap.get(_UNREPORT))})
                    <span id="unreportCount"></span>
                    </a>
                </li>
                <li class="<c:if test="${cls==3}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/cet/cetUnitProject?cls=3"><i
                            class="fa fa-history"></i> 待组织部审核(${cm:trimToZero(statusCountMap.get(_REPORT))})
                    <span id="checkCount"></span>
                    </a>
                </li>
                <li class="<c:if test="${cls==4}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/cet/cetUnitProject?cls=4"><i
                            class="fa fa-times"></i> 未通过审核(${cm:trimToZero(statusCountMap.get(_UNPASS))})</a>
                </li>
                <shiro:hasRole name="${ROLE_CET_ADMIN}">
                <li class="<c:if test="${cls==5}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/cet/cetUnitProject?cls=5"><i
                            class="fa fa-trash"></i> 已删除(${cm:trimToZero(statusCountMap.get(_DELETE))})</a>
                </li>
                </shiro:hasRole>
                <div class="buttons pull-left" style="left:20px; position: relative">
                <button class="popupBtn btn btn-success btn-sm" data-width="900"
                            data-url="${ctx}/cet/cetUnitProject_au?addType=${param.addType}">
                        <i class="fa fa-plus"></i> 添加</button>
                </div>
            </ul>
            <div class="space-4"></div>
            <div class="jqgrid-vertical-offset buttons">

                <c:if test="${cls!=5 && (cm:hasRole(ROLE_CET_ADMIN) || (cls==2))}">
                <shiro:hasPermission name="cetUnitProject:edit">
                    <button class="jqOpenViewBtn btn btn-primary btn-sm" data-width="900"
                       data-url="${ctx}/cet/cetUnitProject_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                </c:if>
                <shiro:hasRole name="${ROLE_CET_ADMIN}">
                <c:if test="${cls!=2}">
                <shiro:hasPermission name="cetUnitProject:edit">
                    <button class="jqBatchBtn btn btn-warning btn-sm"
                            data-title="返回待报送"
                            data-msg="确定返回待报送？（已选{0}条数据）"
                       data-url="${ctx}/cet/cetUnitProject_back"
                       data-grid-id="#jqGrid"><i class="fa fa-reply"></i>
                        返回待报送</button>
                </shiro:hasPermission>
                </c:if>
                <c:if test="${cls==3}">
                    <button class="jqOpenViewBatchBtn btn btn-success btn-sm"
                                    data-url="${ctx}/cet/cetUnitProject_check"
                                    data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                    审批
                    </button>
                </c:if>
                </shiro:hasRole>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                        data-url="${ctx}/cet/cetUnitProject_data"
                        data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
                <shiro:lacksRole name="${ROLE_CET_ADMIN}">
                <c:if test="${cls==2}">
                    <button data-url="${ctx}/cet/cetUnitProject_del"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？（删除后不可恢复，请谨慎操作！）"
                            data-grid-id="#jqGrid"
                            class="jqItemBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </c:if>
                </shiro:lacksRole>
                <shiro:hasRole name="${ROLE_CET_ADMIN}">
                    <c:if test="${cls!=5}">
                    <button data-url="${ctx}/cet/cetUnitProject_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                    </c:if>
                </shiro:hasRole>
                <button class="jqOpenViewBtn btn btn-info btn-sm"
                        data-url="${ctx}/sysApprovalLog"
                        data-querystr="&type=<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_UNIT_TRAIN%>"
                        data-open-by="page">
                    <i class="fa fa-search"></i> 操作记录
                </button>
                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/cet/cetUnitProject_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>--%>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

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
                            <label>年度</label>
                            <input class="form-control date-picker" placeholder="请选择年份"
                                   name="year" type="text" style="width: 80px;"
                                   data-date-format="yyyy" data-date-min-view-mode="2"
                                   value="${param.year}"/>
                        </div>
                        <div class="form-group">
                            <label>培训班名称</label>
                            <input class="form-control search-query" name="projectName" type="text" value="${param.projectName}"
                                   placeholder="请输入">
                        </div>
                        <div class="form-group">
                            <label>培训主办方</label>

                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/cet/cetParty_selects?auth=${cm:hasRole(ROLE_CET_ADMIN)?0:1}"
                                         data-width="308" name="cetPartyId" data-placeholder="请选择">
                                <option value="${cetParty.id}" delete="${cetParty.isDeleted}">${cetParty.name}</option>
                            </select>
                            <script>
                                $.register.del_select($("#searchForm select[name=cetPartyId]"))
                            </script>
                        </div>
                        <div class="form-group">
                            <label>主办单位</label>
                            <select name="unitId" data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                                    data-placeholder="请选择所属单位">
                                <option value="${unit.id}" delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                            </select>
                            <script>
                                $("#searchForm select[name=unitId]").val(${param.unitId});
                                $.register.del_select($("#searchForm select[name=unitId]"), 250)
                            </script>
                        </div>
                        <div class="form-group">
                            <label>培训开始时间</label>
                            <div class="input-group tooltip-success" data-rel="tooltip" title="开始时间范围">
                                            <span class="input-group-addon">
                                                <i class="fa fa-calendar bigger-110"></i>
                                            </span>
                                <input placeholder="请选择开始时间范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="startDate" value="${param.startDate}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>培训结束时间</label>
                            <div class="input-group tooltip-success" data-rel="tooltip" title="结束时间范围">
                                            <span class="input-group-addon">
                                                <i class="fa fa-calendar bigger-110"></i>
                                            </span>
                                <input placeholder="请选择结束时间范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="endDate" value="${param.endDate}"/>
                            </div>
                        </div>
                        <%--<div class="form-group">
                            <label>培训班类型</label>
                            <select data-rel="select2" name="projectTypeId"
                                    data-width="120"
                                    data-placeholder="请选择">
                                <option></option>
                                <c:import url="/metaTypes?__code=mc_cet_upper_train_type2"/>
                            </select>
                            <script type="text/javascript">
                                $("#searchForm select[name=projectTypeId]").val(${param.projectTypeId});
                            </script>
                        </div>--%>
                        <div class="form-group">
                            <label>培训形式</label>
                            <select name="isOnline" data-width="120" data-rel="select2"
                                    data-placeholder="请选择">
                                <option></option>
                                <option value="1">线上培训</option>
                                <option value="0">线下培训 </option>
                            </select>
                            <script>
                                $("#searchForm select[name=isOnline]").val('${param.isOnline}');
                            </script>
                        </div>
                        <div class="form-group">
                            <label>报告名称</label>
                            <input class="form-control search-query" name="reportName" type="text" value="${param.reportName}"
                                   placeholder="请输入">
                        </div>
                        <div class="form-group">
                            <label>主讲人</label>
                            <input class="form-control search-query" name="reporter" type="text" value="${param.reporter}"
                                   placeholder="请输入">
                        </div>
                        <div class="form-group">
                            <label>培训学时</label>
                            <div class="input-group">
                                <input style="width: 50px" class="form-control search-query" type="text" name="prePeriod"
                                       value="${param.prePeriod}">
                            </div>
                            <label>至</label>
                            <div class="input-group">
                                <input style="width: 50px" class="form-control search-query"
                                       type="text"
                                       name="subPeriod"
                                       value="${param.subPeriod}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label>培训地点</label>
                            <input class="form-control search-query" name="address" type="text" value="${param.address}"
                                   placeholder="请输入">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/cet/cetUnitProject?addType=${param.addType}&cls=${cls}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cet/cetUnitProject?addType=${param.addType}&cls=${cls}"
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
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $.register.date($('.date-picker'));
    function _report(){
        $.loadPage({url:"${ctx}/cet/cetUnitProject?cls=2&addType=${param.addType}"})
    }

    var specialProjectTypeMap = ${cm:toJSONObject(specialProjectTypeMap)};
    var dailyProjectTypeMap = ${cm:toJSONObject(dailyProjectTypeMap)};

    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/cet/cetUnitProject_data?callback=?&cls=${cls}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                <c:if test="${cls==4}">
                { label: '退回原因',name: 'backReason', width:120, frozen:true},
                </c:if>
                <c:if test="${cls==2}">
                { label: '报送',name: '_report', width:80, formatter: function (cellvalue, options, rowObject) {
                  return ('<button class="confirm btn btn-success btn-xs" data-msg="报送后不可修改，请核实后确认。" ' +
                      'data-callback="_report" ' +
                  'data-url="${ctx}/cet/cetUnitProject_report?id={0}&cls=${cls}"><i class="fa fa-hand-paper-o"></i> 报送</button>')
                          .format(rowObject.id);
                }, frozen:true},
                </c:if>
                {label: '参训人列表', name: '_detail', formatter: function (cellvalue, options, rowObject) {
                    return ('<button class="openView btn btn-warning btn-xs" ' +
                    'data-url="${ctx}/cet/cetUnitTrain?projectId={0}"><i class="fa fa-users"></i> 查看({1})</button>')
                            .format(rowObject.id, rowObject.totalCount);
                }, frozen: true},
                { label: '年度',name: 'year', width: 60, frozen: true},
                {label: '培训班名称', name: 'projectName', align: 'left',width: 350, frozen: true},
                { label: '培训班主办方',name: 'cetParty.name', align:'left', width: 310},
                { label: '主办单位',name: 'unitId', width: 150, align:'left', formatter: $.jgrid.formatter.unit},
                {label: '培训<br/>开始时间', name: 'startDate', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
                {label: '培训<br/>结束时间', name: 'endDate', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
                {
                  label: '培训天数', name: '_day', width: 80, formatter: function (cellvalue, options, rowObject) {
                    return $.dayDiff(rowObject.startDate, rowObject.endDate);
                }
                },
                {
                    label: '培训班类型', name: 'projectTypeId', width: 130, formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--'
                        if(rowObject.specialType==${CET_PROJECT_TYPE_SPECIAL}) {
                            if (specialProjectTypeMap[cellvalue] == undefined) return '--'
                            return specialProjectTypeMap[cellvalue].name
                        }else{
                            if (dailyProjectTypeMap[cellvalue] == undefined) return '--'
                            return dailyProjectTypeMap[cellvalue].name
                        }
                }},
                {label: '培训内容分类', name: 'category', align:'left', width: 180, formatter: function (cellvalue, options, rowObject) {
                    if($.trim(cellvalue)=='') return '--'
                    return ($.map(cellvalue.split(","), function(category){
                        return $.jgrid.formatter.MetaType(category);
                    })).join("，")
                }},
                { label: '培训形式', name: 'isOnline', width: 90, formatter:$.jgrid.formatter.TRUEFALSE, formatoptions:{on:'<span class="green bolder">线上培训</span>', off:'线下培训'}},
                {label: '培训类别', name: 'specialType', width: 80, formatter: function (cellvalue, options, rowObject) {
                        if(cellvalue==undefined) return '--'
                        return _cMap.CET_PROJECT_TYPE_MAP[cellvalue]
                    }},
                {label: '报告名称', name: 'reportName', width: 150},
                {label: '主讲人', name: 'reporter', width: 80},
                {label: '培训学时', name: 'period', width: 80},
                {label: '培训地点', name: 'address', align: 'left', width: 180},
                /*{ label: '参训人数',name: 'totalCount'},*/
                {label: '是否计入<br/>年度学习任务', name: 'isValid', formatter: function (cellvalue, options, rowObject) {
                  if (cellvalue==undefined) {
                    return '--'
                  }
                  return cellvalue?'是':'否'
                }},
                { label: '备注',name: 'remark', align: 'left', width: 150},
                /*{label: '操作人', name: 'addUser.realname'},*/
                {label: '状态', name: 'status', formatter: function (cellvalue, options, rowObject) {
                    return _cMap.CET_UNIT_PROJECT_STATUS_MAP[cellvalue]
                }},
                /*{label: '添加时间', name: 'addTime', width: 150},*/

        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>