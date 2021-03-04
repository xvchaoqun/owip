<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
            <div class="myTableDiv"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <div class="tabbable">

                    <div class="tab-content multi-row-head-table">
                        <div class="tab-pane in active rownumbers">
                            <div class="jqgrid-vertical-offset buttons">
                                <div class="btn-group">
                                    <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                            data-url="${ctx}/stat/statCod_data?cls=1"
                                            data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                        <i class="fa fa-download"></i> 导出</button>
                                </div>
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
                                            <input type="hidden" name="cols">
                                            <div class="form-group">
                                                <label>成员姓名</label>
                                                <div class="input-group">
                                                    <select data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/sysUser_selects"
                                                            name="userId" data-placeholder="请输入账号或姓名或工作证号">
                                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"
                                                   data-url="${ctx}/stat/statCodAppply?cls=1"
                                                   data-target="#page-content"
                                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                                <c:if test="${_query}">&nbsp;
                                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                            data-url="${ctx}/stat/statCod_data?cls=1"
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
    <a href="javascript:;" class="jqOrderBtn" data-id="{{=id}}" data-direction="-1" title="下降"><i
            class="fa fa-arrow-down"></i></a>
</script>
<script>
    $("ul.dropdown-menu").on("click", "[data-stopPropagation]", function (e) {
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
        rownumbers: true,
        mtype:'POST',
        postData:${cm:toJSONObject(pageContext.request.parameterMap)},
        url: '${ctx}/stat/statCod_data?cls=1&callback=?',
        colModel: [
            {label: '姓名', name: 'user.realname', width: 110, frozen: true},
            {label: '公民身份证号码', name: 'user.idcard', width: 150},
            {label: '性别', name: 'user.gender', width: 100, formatter:$.jgrid.formatter.GENDER},
            {label: '民族', name: 'user.nation', width: 100},
            {label: '籍贯', name: 'user.nativePlace',  width: 150},
            {label: '出生日期', name: 'user.birth', width: 100,formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}},
            {label: '学历', name: 'education',  width: 100},
            {label: '人员类别', name: 'stage',  width: 100,formatter: function (cellvalue, options, rowObject) {
                    var str = "";
                    if (cellvalue == undefined) return '--';
                    if (cellvalue == -1) {
                        str = "不通过";
                    }else if (cellvalue == 0){
                        str = "申请";
                    }else if (cellvalue == 1){
                        str = "通过";
                    }else if (cellvalue == 2){
                        str = "入党积极分子";
                    }else if (cellvalue == 3){
                        str = "发展对象";
                    }else if (cellvalue == 4){
                        str = "列入发展计划";
                    }else if (cellvalue == 5){
                        str = "领取志愿书";
                    }else if (cellvalue == 6){
                        str = "预备党员";
                    }else if (cellvalue == 7){
                        str = "正式党员";
                    }
                    return str;
                }

            },
            {label: '入党申请时间', name: 'applyTime',  width: 100,formatter: $.jgrid.formatter.date},
            {label: '确定为积极分子时间', name: 'activeTime', width: 150,formatter: $.jgrid.formatter.date},
            {label: '确定为发展对象时间', name: 'candidateTime',  width: 150,formatter: $.jgrid.formatter.date},
            {label: '所在党组织', name: 'partyId',  width: 250,
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    return  _cMap.partyMap[cellvalue].name
                }
            },
            {label: '入党时间', name: 'passTime',  width: 150,formatter: $.jgrid.formatter.date},
            {label: '转正时间', name: 'positiveTime',  width: 200,formatter: $.jgrid.formatter.date},
            {label: '工作岗位', name: 'user.post',  width: 200},
            {label: '现居住地', name: '',  width: 200},
            {label: '移动电话', name: 'user.phone',  width: 100},
            {label: '联系电话', name: '',  width: 100},
            {label: '党员档案所在单位', name: '',  width: 150},
            {label: '从事专业技术职务', name: '', width: 150},
            {label: '新社会阶层类型', name: '',  width: 350},
            {label: '一线情况', name: '',  width: 200},
            {hidden: true, key: true, name: 'user.userId'}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $('[data-rel="tooltip"]').tooltip();
    });
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('[data-rel="select2"]').select2();
</script>