<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/analysis/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
            <div class="myTableDiv"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <%--<c:set var="_query" value="${not empty param.userId || not empty param.gender || not empty param.stage || not empty param.userType || not empty param.partyId}"/>--%>
                <div class="tabbable">

                    <div class="tab-content multi-row-head-table">
                        <div class="tab-pane in active rownumbers">
                            <div class="jqgrid-vertical-offset buttons">
                                <div class="btn-group">
                                    <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                            data-url="${ctx}/commonSheet/downloadPartyInfo?cls=1" <%--${ctx}/commonSheet/downloadPartyInfo?cls=1--%>
                                            data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                        <i class="fa fa-download"></i> 导出</button>
                                </div>
                            </div>
                            <%--搜索--%>
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
                                                <label>姓名/学工号 <span class="prompt" data-title="查询说明"
                                                                    data-prompt="按姓名或学工号进行模糊查询"><i class="fa fa-question-circle-o"></i></span></label>
                                                <div class="input-group">
                                                    <input class="form-control search-query" name="realname" type="text"
                                                           value="${param.realname}"
                                                           placeholder="请输入">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>性别</label>
                                                <div class="input-group">
                                                    <select name="gender" data-width="100" data-rel="select2"
                                                            data-placeholder="请选择">
                                                        <option></option>
                                                        <c:forEach items="${GENDER_MAP}" var="entity">
                                                            <option value="${entity.key}">${entity.value}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <script>
                                                        $("#searchForm select[name=gender]").val('${sysUser.gender}');
                                                    </script>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>民族:</label>
                                                <div class="input-group">
                                                    <select data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/csMember_selects"
                                                            name="nation" data-placeholder="请输入民族">
                                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>籍贯:</label>
                                                <div class="input-group">
                                                    <select data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/csMember_selects"
                                                            name="nactionPlace" data-placeholder="请输入籍贯">
                                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                    </select>
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
                                            <div class="form-group">
                                                <label>成熟度:</label>
                                                <div class="input-group">
                                                    <select data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/sysUser_selects"
                                                            name="politicalStatus" data-placeholder="请输入成熟度">
                                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>入党申请时间</label>
                                                <div class="input-group tooltip-success" data-rel="tooltip" title="入党申请时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                    <input placeholder="请选择入党申请时间范围" data-rel="date-range-picker"
                                                           class="form-control date-range-picker"
                                                           type="text" name="_applyTime" value="${param._growTime}"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>确定为入党积极分子时间</label>
                                                <div class="input-group tooltip-success" data-rel="tooltip" title="确定为入党积极分子时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                    <input placeholder="请选择确定为入党积极分子时间范围" data-rel="date-range-picker"
                                                           class="form-control date-range-picker"
                                                           type="text" name="_activeTime" value="${param._growTime}"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>确定为发展对象时间</label>
                                                <div class="input-group tooltip-success" data-rel="tooltip" title="确定为发展对象时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                    <input placeholder="请选择确定为入党积极分子时间范围" data-rel="date-range-picker"
                                                           class="form-control date-range-picker"
                                                           type="text" name="_candidateTime" value="${param._growTime}"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>入党时间</label>
                                                <div class="input-group tooltip-success" data-rel="tooltip" title="入党时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                    <input placeholder="请选择入党时间范围" data-rel="date-range-picker"
                                                           class="form-control date-range-picker"
                                                           type="text" name="_growTime" value="${param._growTime}"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>转正时间</label>
                                                <div class="input-group tooltip-success" data-rel="tooltip" title="转正时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                    <input placeholder="请选择转正时间范围" data-rel="date-range-picker"
                                                           class="form-control date-range-picker"
                                                           type="text" name="_positiveTime" value="${param._positiveTime}"/>
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
        ondblClickRow: function () {
        },
        rownumbers: true,
        mtype:'POST',
        postData:${cm:toJSONObject(pageContext.request.parameterMap)},
        url: '${ctx}/csMember_data?cls=1&callback=?',
        colModel: [

            {label: '学号/职工号', name: 'code', width: 120, frozen: true},
            {
                label: '姓名', name: 'realname', width: 75, formatter: function (cellvalue, options, rowObject) {
                    return $.member(rowObject.userId, cellvalue);
                }, frozen: true
            },
            {label: '性别', name: 'gender', width: 55, formatter: $.jgrid.formatter.GENDER},
            {label: '身份证号', name: 'idcard', width: 200},
            {
                label: '出生日期',
                name: 'birth',
                width: 100,
                sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {label: '民族', name: 'nation',width:70},
            {label: '籍贯', name: 'nativePlace', width: 120},
            {
                label: '所在党组织', name: 'party', width: 550, formatter: function (cellvalue, options, rowObject) {
                    return $.party(rowObject.partyId, rowObject.branchId);
                }, sortable: true
            },
            {
                label: '入党成熟度', name: 'politicalStatus', formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue)
                        return _cMap.MEMBER_POLITICAL_STATUS_MAP[cellvalue];
                    return "-";
                }
            },
            {
                label: '入党申请时间',
                name: 'applyTime',
                width: 120,
                sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {
                label: '确定为入党积极分子时间',
                name: 'activeTime',
                width: 150,
                sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {
                label: '确定为发展对象时间',
                name: 'candidateTime',
                width: 150,
                sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {
                label: '入党时间',
                name: 'growTime',
                width: 120,
                sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {
                label: '转正时间',
                name: 'positiveTime',
                sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {label: '人员类别', name: 'userType', formatter : function (cellvalue, options, rowObject) {
                    return _cMap.USER_TYPE_MAP[cellvalue];
                }},
            {label: '从事专业技术职务', name: 'proPost', width: 150},
            {label: '从事专业技术职务级别', name: 'proPostLevel', formatter : function (cellvalue, options, rowObject) {
                    return _cMap.PRO_POST_LEVEL_MAP[cellvalue];
                }},
            {label: '党政职务', name: 'partyPost', width: 150},
            // {label: '党政职务级别', name: 'postLevel', width: 150},
            {label: '党政职务级别', name: 'postLevel', formatter : function (cellvalue, options, rowObject) {
                    return _cMap.PRO_POST_LEVEL_MAP[cellvalue];
                }},
            {label: '党支部任职', name: '', width: 150},
            {label: '人员状态', name: 'xjStatus', width: 120},
            {label: '联系地址', name: 'fromAddress', width: 120},
            {label: '移动电话', name: 'mobile', width: 110},
            {label: '毕业院校', name: 'schoolName', width: 110},
            {label: '专业', name: 'major', width: 110},
            {label: '最高学历', name: 'education', width: 120},
            {label: '学位', name: 'degree', width: 110},
            {label: '所在单位', name: 'unit', width: 180},
            {label: '党支部类型', name: 'branchName', width: 200},
            {label: '停止党籍或出国关系保留', name: 'status', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue)
                    return _cMap.MEMBER_STATUS_MAP[cellvalue];
                return "-";
            }},

            {hidden: true, key: true, name: 'userId'}, {hidden: true, name: 'partyId'}, {hidden: true, name: 'source'}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $('[data-rel="tooltip"]').tooltip();
    });
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[name=userId]'));
    $('[data-rel="select2"]').select2();
</script>