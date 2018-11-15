<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
            <div class="myTableDiv multi-row-head-table"
                 data-url-page="${ctx}/dispatch"
                 data-url-export="${ctx}/dispatch_data"
                 data-url-co="${ctx}/dispatch_changeOrder"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.year ||not empty param.dispatchTypeId ||not empty param.code
            ||not empty param._pubTime ||not empty param._workTime ||not empty param._meetingTime || not empty param.code}"/>
                <div class="tabbable">
                    <jsp:include page="/WEB-INF/jsp/dispatch/dispatch_menu.jsp"/>
                    <div class="tab-content">
                        <div class="tab-pane in active">
                            <div class="jqgrid-vertical-offset buttons">
                                <shiro:hasPermission name="dispatch:edit">
                                    <a class="openView btn btn-info btn-sm"
                                       data-url="${ctx}/dispatch_au_page"><i class="fa fa-plus"></i> 添加</a>
                                </shiro:hasPermission>
                                <button id="editBtn" class="jqEditBtn btn btn-primary btn-sm"
                                   data-open-by="page" data-url="${ctx}/dispatch_au_page">
                                    <i class="fa fa-edit"></i> 修改信息</button>
                                <button id="addDipatchCadreBtn" class="jqOpenViewBtn btn btn-success btn-sm"
                                   data-open-by="page" data-id-name="dispatchId" data-url="${ctx}/dispatch_cadres">
                                    <i class="fa fa-plus"></i> 添加干部任免
                                </button>
                                <shiro:hasPermission name="dispatch:check">
                                <button id="checkBtn" class="jqOpenViewBtn btn btn-warning btn-sm"
                                   data-open-by="page" data-id-name="dispatchId" data-url="${ctx}/dispatch_cadres"
                                   data-querystr="&check=1">
                                    <i class="fa fa-check"></i> 复核
                                </button>
                                <button id="reCheckBtn" class="jqItemBtn btn btn-warning btn-sm"
                                        data-url="${ctx}/dispatch_reset_check"
                                        data-title="重新复核"
                                        data-msg="确定重新复核吗？">
                                    <i class="fa fa-reply"></i> 重新复核
                                </button>
                                </shiro:hasPermission>
                                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                   data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i
                                        class="fa fa-download"></i> 导出</a>
                                <shiro:hasPermission name="dispatch:del">
                                    <a class="jqBatchBtn btn btn-danger btn-sm"
                                       data-url="${ctx}/dispatch_batchDel" data-title="删除"
                                       data-msg="确定删除这{0}个任免文件吗？"><i class="fa fa-trash"></i> 删除</a>
                                </shiro:hasPermission>

                            </div>
                            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs"
                                 style="margin-right: 20px">
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
                                            <input type="hidden" name="cls" value="${cls}">
                                            <div class="form-group">
                                                <label>年份</label>

                                                <div class="input-group">
                                                    <span class="input-group-addon"> <i
                                                            class="fa fa-calendar bigger-110"></i></span>
                                                    <input class="form-control date-picker" placeholder="请选择年份"
                                                           name="year" type="text"
                                                           data-date-format="yyyy" data-date-min-view-mode="2"
                                                           value="${param.year}"/>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label>任免日期</label>

                                                <div class="input-group tooltip-success" data-rel="tooltip"
                                                     title="任免日期范围">
                                                    <span class="input-group-addon">
                                                        <i class="fa fa-calendar bigger-110"></i>
                                                    </span>
                                                    <input placeholder="请选择任免日期范围" data-rel="date-range-picker"
                                                           class="form-control date-range-picker" type="text"
                                                           name="_workTime" value="${param._workTime}"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>发文类型</label>
                                                <select data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/dispatchType_selects"
                                                        name="dispatchTypeId" data-placeholder="请选择发文类型">
                                                    <option value="${dispatchType.id}">${dispatchType.name}</option>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label>发文日期</label>

                                                <div class="input-group tooltip-success" data-rel="tooltip"
                                                     title="发文日期范围">
                                                    <span class="input-group-addon"><i
                                                            class="fa fa-calendar bigger-110"></i></span>
                                                    <input placeholder="请选择发文日期范围" data-rel="date-range-picker"
                                                           class="form-control date-range-picker" type="text"
                                                           name="_pubTime" value="${param._pubTime}"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>发文号</label>
                                                <input class="form-control search-query" name="code" type="text"
                                                       value="${param.code}"
                                                       placeholder="请输入发文号">
                                            </div>
                                            <div class="form-group">
                                                <label>常委会</label>

                                                <div class="input-group tooltip-success" data-rel="tooltip"
                                                     title="党委常委会日期范围">
                                            <span class="input-group-addon">
                                                <i class="fa fa-calendar bigger-110"></i>
                                            </span>
                                                    <input placeholder="请选择党委常委会日期范围" data-rel="date-range-picker"
                                                           class="form-control date-range-picker" type="text"
                                                           name="_meetingTime" value="${param._meetingTime}"/>
                                                </div>
                                            </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"><i
                                                        class="fa fa-search"></i> 查找</a>
                                                <c:if test="${_query || not empty param.sort}">&nbsp;
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
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<link rel="stylesheet" href="${ctx}/extend/css/jquery.webui-popover.min.css" type="text/css"/>
<script src="${ctx}/extend/js/jquery.webui-popover.min.js"></script>
<script type="text/template" id="dispatch_del_file_tpl">
    <a class="btn btn-success btn-sm" onclick="dispatch_del_file({{=id}}, '{{=type}}')">
        <i class="fa fa-check"></i> 确定</a>&nbsp;
    <a class="btn btn-default btn-sm" onclick="hideDel()"><i class="fa fa-trash"></i> 取消</a>
</script>
<script>

    function hideDel() {
        $(".dispatch_del_file").webuiPopover("hide")
    }
    function dispatch_del_file(id, type) {
        $.post("${ctx}/dispatch_del_file", {id: id, type: type}, function (data) {
            if (data.success) {
                hideDel();
                $("#jqGrid").trigger("reloadGrid");
                //SysMsg.success('操作成功。', '成功');
            }
        });
    }
    $.register.date($('.date-picker'));
    $('[data-rel="select2"]').select2();
    $.register.dispatchType_select($('#searchForm select[name=dispatchTypeId]'), $("#searchForm input[name=year]"));

    $("#jqGrid").jqGrid({
        url: '${ctx}/dispatch_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年份', name: 'year', width: 60, frozen: true},
            {
                label: '发文类型', name: 'dispatchType', width: 80, formatter: function (cellvalue, options, rowObject) {
                return cellvalue.name;
            }, frozen: true
            },
            {
                label: '发文号', name: 'dispatchCode', width: 140, align:'left', formatter: function (cellvalue, options, rowObject) {

                return $.swfPreview(rowObject.file, rowObject.fileName, cellvalue, cellvalue);
            }, frozen: true
            },
            {
                label: '党委常委会', name: 'scDispatch.scCommittees', width:200, formatter: function (cellvalue, options, rowObject) {

                if(cellvalue==undefined || cellvalue.length==0) return '--'

                var scCommittee = cellvalue[0];
                var str = scCommittee.code
                if(cellvalue.length>1){
                    str += "，..."
                }else{

                    return ('<a href="javascript:;" class="linkBtn"'
                    +'data-url="${ctx}#/sc/scCommittee?year={0}&holdDate={1}"'
                    +'data-target="_blank">{2}</a>')
                            .format(scCommittee.year, $.date(scCommittee.holdDate,'yyyyMMdd'),str)
                }

                return ('<a href="javascript:;" class="popupBtn" ' +
                'data-url="${ctx}/sc/scDispatchCommittee?dispatchId={0}">{1}</a>')
                        .format(rowObject.scDispatch.id, str);
            }
            },
            {label: '党委常委会<br/>日期', name: 'meetingTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '发文日期', name: 'pubTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '任免日期', name: 'workTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '任命人数', name: 'appointCount', width: 80},
            {label: '录入<br/>任命人数', width: 80, name: 'realAppointCount', formatter:$.jgrid.formatter.defaultString},
            {label: '免职人数', name: 'dismissCount', width: 80},
            {label: '录入<br/>免职人数', width: 80, name: 'realDismissCount', formatter:$.jgrid.formatter.defaultString},
            {
                label: '是否<br/>全部录入', width: 80, formatter: function (cellvalue, options, rowObject) {
                //console.log((rowObject.realAppointCount+rowObject.realDismissCount)>0)

                return isFinished(rowObject.appointCount, rowObject.dismissCount,
                        rowObject.realAppointCount, rowObject.realDismissCount)? "是" : "否";
            }
            },
            {
                label: '是否<br/>复核', name: 'hasChecked', width: 65, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return cellvalue ? "已复核" : "否";
            }
            },
            /*{
                label: '任免文件', formatter: function (cellvalue, options, rowObject) {

                if (rowObject.fileName && rowObject.fileName != '')
                    return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/swf/preview?path={0}&filename={1}">查看</a>'
                        .format(encodeURI(rowObject.file), encodeURI(rowObject.fileName))
                            + '&nbsp;<a href="javascript:void(0)" class="dispatch_del_file"'
                            + 'data-id="{0}" data-type="file">删除</a>'.format(rowObject.id);
                else return '';
            }
            },*/
            {
                label: '上会ppt', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.pptName && rowObject.pptName != '')
                    return ('<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/swf/preview?path={0}&filename={1}">查看</a>'
                        .format(encodeURI(rowObject.ppt), encodeURI(rowObject.pptName))
                + '&nbsp;<a href="javascript:void(0)" class="dispatch_del_file"'
                            + 'data-id="{0}" data-type="ppt">删除</a>'.format(rowObject.id));
                return '--';
            }, width:85
            },
            {
                label: '任免信息', width:80, formatter: function (cellvalue, options, rowObject) {

                return '<button class="linkBtn btn btn-xs btn-primary" data-url="#/dispatch?cls=2&year={0}&dispatchTypeId={1}&code={2}" data-target="_blank"><i class="fa fa-search"></i> 查看</button>'
                        .format(rowObject.year, rowObject.dispatchTypeId, rowObject.code);
            }},
            {
                label: '文件签发信息', name: 'scDispatch', width:140, formatter: function (cellvalue, options, rowObject) {

                if(cellvalue==undefined) return '--'

                return ('<a href="javascript:;" class="linkBtn"'
                +'data-url="${ctx}#/dispatch?cls=3&year={0}&dispatchTypeId={1}&code={2}"'
                +'data-target="_blank">{3}</a>')
                        .format(cellvalue.year,cellvalue.dispatchTypeId, cellvalue.code, cellvalue.dispatchCode)
            }
            },
            {label: '备注', name: 'remark', width: 550}
                ,{hidden:true, name:'_hasChecked', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.hasChecked==undefined) return 0;
                return rowObject.hasChecked?1:0;
            }}
        ], onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id, id, status);
            //console.log(id)
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#checkBtn,#reCheckBtn, #addDipatchCadreBtn, #editBtn").prop("disabled", true);
            } else if (ids.length == 1) {

                var rowData = $(this).getRowData(ids[0]);
                $("#checkBtn").prop("disabled", !isFinished(parseInt(rowData.appointCount), parseInt(rowData.dismissCount),
                        parseInt(rowData.realAppointCount), parseInt(rowData.realDismissCount)) || rowData._hasChecked==1);
                $("#reCheckBtn").prop("disabled", rowData._hasChecked!=1);
                //console.log(rowData._hasChecked==1)
                $("#addDipatchCadreBtn, #editBtn").prop("disabled", rowData._hasChecked==1)
            } else {
                $("#checkBtn, #reCheckBtn, #addDipatchCadreBtn, #editBtn").prop("disabled", false);
            }
        }
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $(".dispatch_del_file").each(function () {
            var id = $(this).data('id');
            var type = $(this).data('type');
            $(this).webuiPopover({
                width: '180px', animation: 'pop',
                content: function () {
                    return _.template($("#dispatch_del_file_tpl").html())({id: id, type: type})
                }
            });
        });
        $('[data-rel="tooltip"]').tooltip();
    });

    function isFinished(appointCount, dismissCount, realAppointCount, realDismissCount){

        return ((realAppointCount + realDismissCount) > 0
        && appointCount == realAppointCount
        && dismissCount == realDismissCount);
    }
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
</script>