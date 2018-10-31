<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/sc/scGroupTopic"
                 data-url-export="${ctx}/sc/scGroupTopic_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
           <%-- <c:set var="_query"
                   value="${not empty param.year ||not empty param.groupId  ||not empty param.holdDate || not empty param.name || not empty param.unitIds}"/>--%>
                <div class="tabbable">
                    <jsp:include page="../scGroup/menu.jsp"/>
                    <div class="tab-content">
                        <div class="tab-pane in active">
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="scGroupTopic:edit">
                    <a class="openView btn btn-info btn-sm"  data-url="${ctx}/sc/scGroupTopic_au"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/sc/scGroupTopic_au"
                       data-grid-id="#jqGrid"
                       data-open-by="page"
                       ><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="scGroupTopic:del">
                    <button data-url="${ctx}/sc/scGroupTopic_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <%--<a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</a>--%>
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
                            <input type="hidden" name="cls" value="${cls}">

                            <div class="form-group">
                                <label>年份</label>
                                <div class="input-group" style="width: 150px">
                                    <input required class="form-control date-picker" placeholder="请选择年份"
                                           name="year"
                                           type="text"
                                           data-date-format="yyyy" data-date-min-view-mode="2"
                                           value="${param.year}"/>
                                                <span class="input-group-addon"> <i
                                                        class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>编号</label>
                                <select required name="groupId" data-rel="select2"
                                        data-width="240"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <c:forEach var="scGroup" items="${scGroups}">
                                        <option value="${scGroup.id}">干部小组会[${cm:formatDate(scGroup.holdDate, "yyyyMMdd")}]号</option>
                                    </c:forEach>
                                </select>
                                <script>
                                    $("#searchForm select[name=groupId]").val("${param.groupId}");
                                </script>
                            </div>
                            <div class="form-group">
                                <label>干部小组会日期</label>
                                <input required class="form-control date-picker" name="holdDate"
                                       type="text"
                                       data-date-format="yyyy-mm-dd"
                                       value="${param.holdDate}"/>
                            </div>
                            <div class="form-group">
                                <label>议题名称</label>
                                <input required class="form-control" type="text" name="name" value="${param.name}">
                            </div>
                            <div class="form-group">
                                <label>涉及单位</label>
                                <select class="multiselect" name="unitIds" multiple="">
                                    <optgroup label="正在运转单位">
                                        <c:forEach items="${runUnits}" var="unit">
                                            <option value="${unit.id}">${unit.name}</option>
                                        </c:forEach>
                                    </optgroup>
                                    <optgroup label="历史单位">
                                        <c:forEach items="${historyUnits}" var="unit">
                                            <option value="${unit.id}">${unit.name}</option>
                                        </c:forEach>
                                    </optgroup>
                                </select>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="resetBtn btn btn-warning btn-sm"
                                            data-querystr="cls=${cls}">
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
<script src="${ctx}/assets/js/bootstrap-multiselect.js"></script>
<link rel="stylesheet" href="${ctx}/assets/css/bootstrap-multiselect.css" />
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/sc/scGroupTopic_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年份', name: 'year', width: 80, frozen: true},
            {
                label: '编号', name: '_num', width: 180, formatter: function (cellvalue, options, rowObject) {
                var _num = "干部小组会[{0}]号".format($.date(rowObject.holdDate, "yyyyMMdd"))
                if(rowObject.groupFilePath==undefined) return _num;
                return $.swfPreview(rowObject.groupFilePath, _num);
            }, frozen: true},
            {label: '干部小组会日期', name: 'holdDate', width: 120, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            { label: '议题名称',name: 'name', width: 350, align:'left'},
            {
                label: '议题内容和讨论备忘',name: '_content', width: 150, formatter: function (cellvalue, options, rowObject) {
                return ('<button class="openView btn btn-primary btn-xs" ' +
                'data-url="${ctx}/sc/scGroupTopic_content?topicId={0}"><i class="fa fa-search"></i> 查看</button>')
                        .format(rowObject.id);
            }
            },
            {
                label: '涉及单位', name: 'unitIds', width:180, align:'left', formatter: function (cellvalue, options, rowObject) {

                if(cellvalue==undefined) return '-'

                var unitIds = cellvalue.split(",");
                var unitname = "-"
                for(i in unitIds){
                    var unit = _cMap.unitMap[unitIds[i]];
                    if(unit!=undefined && unit.name!=undefined) {
                        unitname = unit.name;
                        break;
                    }
                }
                if(unitIds.length>1){
                    unitname += "，..."
                }

                return ('<a href="javascript:;" class="popupBtn btn btn-link btn-xs" ' +
                'data-url="${ctx}/sc/scGroupTopicUnit?topicId={0}">{1}</a>')
                        .format(rowObject.id, unitname);
            }
            },
            {
                label: '参会人', name: 'users', width:280, align:'left', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '-'
                return $.map(cellvalue, function(u){
                    return u.realname;
                })
            }
            },
            { label: '列席人',name: 'attendUsers'},
            {label: '会议记录', name: 'logFile', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.logFile==undefined) return '-';
                return $.swfPreview(rowObject.logFile, '会议记录', '查看');
            }},
            { label: '备注',name: 'remark', width:300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $.register.date($('.date-picker'));
    $.register.multiselect($('#searchForm select[name="unitIds"]'), ${cm:toJSONArray(selectedUnitIds)},
            {enableClickableOptGroups: true, enableCollapsibleOptGroups: true});

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>