<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/abroad/taiwanRecord_page"
                 data-url-export="${ctx}/abroad/taiwanRecord_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.cadreId ||not empty param.recordDate ||not empty param.startDate ||not empty param.endDate || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="taiwanRecord:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/abroad/taiwanRecord_au"><i class="fa fa-plus"></i> 添加</a>
                    <button id="editBtn" class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/abroad/taiwanRecord_au"
                       data-grid-id="#jqGrid"
                       ><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <button id="handleBtn" class="jqOpenViewBtn btn btn-warning btn-sm"
                        data-url="${ctx}/abroad/shortMsg_view" data-querystr="&type=taiwanRecordHandle">
                    <i class="fa fa-hand-paper-o"></i> 催交证件
                </button>
                <shiro:hasPermission name="taiwanRecord:del">
                    <button data-url="${ctx}/abroad/taiwanRecord_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>【注：上交证件后不可删除】
                </shiro:hasPermission>
               <%-- <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
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
                        <div class="form-group">
                            <label>干部</label>
                            <input class="form-control search-query" name="cadreId" type="text" value="${param.cadreId}"
                                   placeholder="请输入干部">
                        </div>
                        <div class="form-group">
                            <label>备案时间</label>
                            <input class="form-control search-query" name="recordDate" type="text" value="${param.recordDate}"
                                   placeholder="请输入备案时间">
                        </div>
                        <div class="form-group">
                            <label>离境时间</label>
                            <input class="form-control search-query" name="startDate" type="text" value="${param.startDate}"
                                   placeholder="请输入离境时间">
                        </div>
                        <div class="form-group">
                            <label>回国时间</label>
                            <input class="form-control search-query" name="endDate" type="text" value="${param.endDate}"
                                   placeholder="请输入回国时间">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="resetBtn btn btn-warning btn-sm">
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
    $("#jqGrid").jqGrid({
        url: '${ctx}/abroad/taiwanRecord_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '备案时间',name: 'recordDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '工作证号', name: 'user.code', width: 100, frozen: true},
            {
                label: '姓名', name: 'user.realname', width: 75, formatter: function (cellvalue, options, rowObject) {
                return $.cadre(rowObject.cadre.id, cellvalue);
            }, frozen: true
            },
            { label: '离境时间',name: 'startDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            { label: '回国时间',name: 'endDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {
                label: '出访天数', name: '_day', width: 80, formatter: function (cellvalue, options, rowObject) {
                if($.trim(rowObject.startDate)=='' || $.trim(rowObject.endDate)=='') return '-'
                return $.dayDiff(rowObject.startDate, rowObject.endDate);
            }
            },
            {
                label: '出访活动类别', name: 'reason', width: 150, formatter: function (cellvalue, options, rowObject) {
                return cellvalue.replace(/\+\+\+/g, ',');
            }, align:'left'
            },
            { label: '备注',name: 'remark', width: 200, align:'left'},
            { label: '现持有台湾通行证号码',name: 'passportCode', width: 160, formatter: function (cellvalue, options, rowObject) {
                if($.trim(cellvalue)=='') return '无'
                return cellvalue;
            }},
            {
                label: '办理新证件方式', name: 'handleType', width: 160, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.ABROAD_TAIWAN_RECORD_HANDLE_TYPE_MAP[cellvalue]
            },cellattr:function(rowId, val, rowObject, cm, rdata) {
                if($.trim(rowObject.handleType)=='') {
                    var _date = rowObject.endDate;
                    if ($.trim(rowObject.passportCode)=='' && _date <= new Date().format('yyyy-MM-dd'))
                        return "class='danger'";
                }
            }
            },
            { label: '新证件应交组织部日期',name: '_expectDate', width: 160, formatter: function (cellvalue, options, rowObject) {
                if($.trim(rowObject.passportCode)!='' ||
                        rowObject.handleType!='${ABROAD_TAIWAN_RECORD_HANDLE_TYPE_OFFICE}') return '-'

                //if($.trim(rowObject.handleDate)!='') return rowObject.expectDate.substr(0,10);
                if($.trim(rowObject.expectDate)!='') return rowObject.expectDate.substr(0,10);

                return ((rowObject.expectDate==undefined)?"":rowObject.expectDate.substr(0,10)) +
                        '&nbsp;<button data-url="${ctx}/abroad/taiwanRecord_expectDate?id={0}" class="popupBtn btn btn-primary btn-xs">'
                                .format(rowObject.id)
                        + '<i class="fa fa-edit"></i> {0}</button>'.format((rowObject.expectDate==undefined)?"确认日期":"修改")
            },cellattr:function(rowId, val, rowObject, cm, rdata) {
                if($.trim(rowObject.handleDate)=='') {
                    var _date = rowObject.expectDate;
                    if (_date <= new Date().format('yyyy-MM-dd'))
                        return "class='danger'";
                }
            }},
            {
                label: '上交新办证件',
                align: 'center',
                width: 120,
                formatter: function (cellvalue, options, rowObject) {

                    if($.trim(rowObject.passportCode)!='' ||
                            rowObject.handleType!='${ABROAD_TAIWAN_RECORD_HANDLE_TYPE_OFFICE}'
                    || $.trim(rowObject.expectDate)=='') return '-'

                    if($.trim(rowObject.handleDate)!='') return '已交证件'
                    return '<button data-url="${ctx}/abroad/passport_au?taiwanRecordId={0}" class="popupBtn btn btn-success btn-xs">'
                                    .format(rowObject.id)
                            + '<i class="fa fa-hand-paper-o"></i> 上交证件</button>'
                }
            },
            { label: '新证件实交组织部日期',name: '_handleDate', width: 160, formatter: function (cellvalue, options, rowObject) {
                if($.trim(rowObject.passportCode)!='' ||
                        rowObject.handleType!='${ABROAD_TAIWAN_RECORD_HANDLE_TYPE_OFFICE}'
                        || $.trim(rowObject.expectDate)=='') return '-'
                if(rowObject.handleDate==undefined) return '';
                return rowObject.handleDate.substr(0,10)
            }},{hidden:true, name:'handleDate'},{hidden:true, name:'expectDate'}
        ],
        onSelectRow: function(id,status){
            saveJqgridSelected("#"+this.id);
            var rowData = $(this).getRowData(id);
            $("#handleBtn").prop("disabled",($.trim(rowData.expectDate)=='' || $.trim(rowData.handleDate)!=''));
            $("#editBtn").prop("disabled", $.trim(rowData.handleDate)!='');
        }
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid');
    })
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>