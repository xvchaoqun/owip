<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/analysis/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
            <div class="myTableDiv"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId || not empty param.gender || not empty param.stage || not empty param.userType || not empty param.partyId}"/>
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
                                                <label>成员姓名:</label>
                                                <div class="input-group">
                                                    <select data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/sysUser_selects"
                                                            name="userId" data-placeholder="请输入账号或姓名或工作证号">
                                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>人员状态:</label>
                                                <div class="input-group">
                                                    <select name="stage" data-width="200" data-rel="select2"
                                                            data-placeholder="请选择">
                                                        <option></option>
                                                        <c:forEach items="${OW_APPLY_STAGE_MAP}" var="entity" begin="${OW_APPLY_STAGE_PASS}"
                                                                   end="${_p_ignore_plan_and_draw?OW_APPLY_STAGE_CANDIDATE:OW_APPLY_STAGE_DRAW}">
                                                            <option value="${entity.key}">${entity.value}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <script>
                                                        $("#searchForm select[name=stage]").val('${param.stage}');
                                                    </script>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>人员类别:</label>
                                                <div class="input-group">
                                                    <select name="userType" data-width="100" data-rel="select2"
                                                            data-placeholder="请选择">
                                                        <option></option>
                                                        <c:forEach items="${USER_TYPE_MAP}" var="entity">
                                                            <option value="${entity.key}">${entity.value}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <script>
                                                        $("#searchForm select[name=userType]").val('${param.userType}');
                                                    </script>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>所在党组织: <span class="prompt" data-title="查询说明"
                                                                               data-prompt="选择${_p_partyName}后，会出现党支部的选择（二级联动）"><i class="fa fa-question-circle-o"></i></span></label>
                                                <select class="form-control" data-width="250" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/party_selects?auth=1"
                                                        name="partyId" data-placeholder="请选择">
                                                    <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                                </select>
                                            </div>
                                            <div class="form-group" style="${(empty branch)?'display: none':''}"
                                                 id="branchDiv">
                                                <label>所在党支部:</label>
                                                <select class="form-control" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/branch_selects?auth=1"
                                                        name="branchId" data-placeholder="请选择党支部">
                                                    <option value="${branch.id}" delete="${branch.isDeleted}">${branch.name}</option>
                                                </select>
                                            </div>
                                            <script>
                                                $.register.party_branch_select($("#searchForm"), "branchDiv",
                                                    '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                            </script>

                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"
                                                   data-url="${ctx}/stat/statCod?cls=1"
                                                   data-target="#page-content"
                                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                                <c:if test="${_query}">&nbsp;
                                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                            data-url="${ctx}/stat/statCod?cls=1"
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
            {label: '学工号', name: 'user.code', width: 115, frozen: true},
            {label: '姓名', name: 'user.realname', width: 90, frozen: true},
            {label: '身份证号码', name: 'user.idcard', width: 170},
            {label: '性别', name: 'user.gender', width: 50, formatter:$.jgrid.formatter.GENDER},
            {label: '民族', name: 'user.nation', width: 60},
            {label: '籍贯', name: 'user.nativePlace',  width: 110},
            {label: '出生日期', name: 'user.birth', width: 100,formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}},
            {label: '学历', name: 'edu',  width: 100},
            {label: '发展阶段', name: 'stage',  width: 100,formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    if (cellvalue == ${OW_APPLY_STAGE_PASS}){
                        cellvalue = ${OW_APPLY_STAGE_INIT}
                    }
                    return _cMap.OW_APPLY_STAGE_MAP[cellvalue];
                }
            },
            {label: '入党申请时间', name: 'applyTime',  width: 100,formatter: $.jgrid.formatter.date},
            {label: '确定为积极分子时间', name: 'activeTime', width: 150,formatter: $.jgrid.formatter.date},
            {label: '确定为发展对象时间', name: 'candidateTime',  width: 150,formatter: $.jgrid.formatter.date},
            {label: '所在党组织', name: 'partyId',  width: 450,
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    return $.party(rowObject.partyId, rowObject.branchId);
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
    $.register.user_select($('[name=userId]'));
    $('[data-rel="select2"]').select2();
</script>