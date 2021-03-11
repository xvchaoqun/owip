<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/analysis/constants.jsp" %>
<c:set var="OW_APPLY_STAGE_MAP" value="<%=OwConstants.OW_APPLY_STAGE_MAP%>"/>
<c:set var="OW_APPLY_TYPE_MAP" value="<%=OwConstants.OW_APPLY_TYPE_MAP%>"/>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
            <div class="myTableDiv"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId || not empty param.gender || not empty param.type || not empty param.branchId || not empty partyId}"/>

                <div class="tabbable">

                    <div class="tab-content multi-row-head-table">
                        <div class="tab-pane in active rownumbers">
                            <div class="jqgrid-vertical-offset buttons">
                                <div class="btn-group">
                                    <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                            data-url="${ctx}/stat/statCod_data?cls=2"
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
                                            <div class="form-group">
                                                <label>性别:</label>
                                                <div class="input-group">
                                                    <select name="gender" data-width="100" data-rel="select2"
                                                            data-placeholder="请选择">
                                                        <option></option>
                                                        <c:forEach items="${GENDER_MAP}" var="entity">
                                                            <option value="${entity.key}">${entity.value}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <script>
                                                        $("#searchForm select[name=gender]").val('${param.gender}');
                                                    </script>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>类别:</label>
                                                <div class="input-group">
                                                    <select name="type" data-width="100" data-rel="select2"
                                                            data-placeholder="请选择">
                                                        <option></option>
                                                        <c:forEach items="${OW_APPLY_TYPE_MAP}" var="entity">
                                                            <option value="${entity.key}">${entity.value}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <script>
                                                        $("#searchForm select[name=type]").val('${param.type}');
                                                    </script>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>所在${_p_partyName} <span class="prompt" data-title="查询说明"
                                                                               data-prompt="选择${_p_partyName}后，会出现党支部的选择（二级联动）"><i class="fa fa-question-circle-o"></i></span></label>
                                                <select class="form-control" data-width="250" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/party_selects?auth=1"
                                                        name="partyId" data-placeholder="请选择">
                                                    <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                                </select>
                                            </div>
                                            <div class="form-group" style="${(empty branch)?'display: none':''}"
                                                 id="branchDiv">
                                                <label>所在党支部</label>
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
                                                   data-url="${ctx}/stat/statCod?cls=2"
                                                   data-target="#page-content"
                                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                                <c:if test="${_query}">&nbsp;
                                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                            data-url="${ctx}/stat/statCod?cls=2"
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
        url: '${ctx}/stat/statCod_data?cls=2&callback=?',
        colModel: [
            {label: '姓名', name: 'realname', width: 110, frozen: true},
            {label: '身份证号码', name: 'idcard', width: 160},
            {label: '性别', name: 'gender', width: 100, formatter:$.jgrid.formatter.GENDER},
            {label: '出生日期', name: 'birth', width: 100,formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}},
            {label: '民族', name: 'nation', width: 100},
            {label: '籍贯', name: 'nativePlace',  width: 150},
            {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE},
            {label: '学历', name: 'userType',  width: 100,formatter: function (cellvalue, options, rowObject) {
                    var str = "--";
                    var edu = rowObject.education;
                    if (cellvalue == ${USER_TYPE_JZG} && edu != undefined && $.trim(edu)) {
                        str = edu;
                    }else if (cellvalue != ${USER_TYPE_JZG}){
                        str = _cMap.USER_TYPE_MAP[cellvalue];
                    }
                    return str;
                }},
            {label: '学位', name: 'degree',  width: 100},
            {label: '毕业院校', name: 'school', width: 150},
            {label: '专业', name: 'major',  width: 150},
            {label: '入党日期', name: 'growTime',  width: 150,formatter: $.jgrid.formatter.date},
            {label: '转正日期', name: 'positiveTime',  width: 150,formatter: $.jgrid.formatter.date},
            {label: '工作岗位', name: 'postClass',  width: 200},
            {label: '从事专业技术职务', name: 'proPost',  width: 200},
            {label: '新的社会阶层', name: '',  width: 200},
            {label: '人员类别', name: 'politicalStatus',  width: 100,formatter: function (cellvalue, options, rowObject) {
                    var str = "";
                    if (cellvalue == undefined) return '--';
                    if (cellvalue == 1) {
                        str = "预备党员";
                    }else if (cellvalue == 0){
                        str = "正式党员";
                    }
                    return str;
                }},
            {label: '是否农民工', name: '',  width: 100},
            {label: '手机号码', name: 'mobile',  width: 150},
            {label: '联系电话', name: '', width: 150},
            {label: '所在党组织', name: 'partyId',  width: 450,
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';

                    return $.party(rowObject.partyId, rowObject.branchId);
                }
            },
            {label: '联合支部单位名称', name: '',  width: 200},
            {label: '档案所在单位', name: '',  width: 200},
            {label: '户籍所在地', name: '',  width: 200},
            {label: '现居住地', name: 'address',  width: 350},
            {label: '失去联系情形', name: '',  width: 100},
            {label: '失去联系日期', name: '',  width: 100},
            {label: '信息完整度', name: '',  width: 100},
            {hidden: true, key: true, name: 'userId'}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $('[data-rel="tooltip"]').tooltip();
    });
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[name=userId]'));
    $('[data-rel="select2"]').select2();
</script>