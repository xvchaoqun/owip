<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId ||not empty param.workTime ||not empty param.type ||not empty param.level ||not empty param.transferTime || not empty param.code || not empty param.sort
            ||not empty param.unitPost ||not empty param.gender ||not empty selectNations ||not empty param.unitId}"/>
            <div class="tabbale">
            <jsp:include page="menu.jsp"/>
                <div class="tab-content">
                    <div class="tab-pane in active">
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="dpNpr:edit">
                    <c:if test="${cls==1}">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/dp/dpNpr_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    </c:if>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/dp/dpNpr_au?gridId=jqGrid"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="dpNpr:del">
                    <c:if test="${cls==1}">
                    <button data-url="${ctx}/dp/dpNpr_cancel"
                            data-title="撤销"
                            data-msg="确定撤销这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqOpenViewBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-history"></i> 撤销
                    </button>
                    </c:if>
                    <c:if test="${cls==2}">
                        <button data-url="${ctx}/dp/dpNpr_batchDel"
                                data-title="删除"
                                data-msg="确定删除这{0}条数据？"
                                data-grid-id="#jqGrid"
                                class="jqBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> 删除
                        </button>
                    </c:if>
                </shiro:hasPermission>
                <button class="popupBtn btn btn-info btn-sm tooltip-info"
                        data-url="${ctx}/dp/dpNpr_import"
                        data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                    批量导入
                </button>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/dp/dpNpr_data?cls=${cls}"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
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
                                <label>成员姓名</label>
                                <div class="input-group">
                                    <select data-rel="select2-ajax"
                                            data-ajax-url="${ctx}/dp/dpNpr_selects?status=${_status}"
                                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                    </select>
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
                                        $("#searchForm select[name=gender]").val('${param.gender}');
                                    </script>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>民族</label>
                                <div class="input-group">
                                    <select class="multiselect" multiple="" name="nation">
                                        <c:forEach items="${nations}" var="nation">
                                            <option value="${nation}">${nation}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>所在单位</label>
                                <select name="unitId" data-rel="select2" data-placeholder="请选择">
                                    <option></option>
                                    <c:forEach items="${unitMap}" var="unit">
                                        <option value="${unit.key}">${unit.value.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>所属类别</label>
                                <select data-width="180" name="type" data-rel="select2" data-placeholder="请选择">
                                    <option></option>
                                    <c:import url="/metaTypes?__code=mc_dp_npr_type"/>
                                </select>
                                <script>         $("#searchForm select[name=type]").val('${param.type}');     </script>
                            </div>
                            <div class="form-group">
                                <label>所属级别</label>
                                <select data-width="180" name="level" data-rel="select2" data-placeholder="请选择">
                                    <option></option>
                                    <c:import url="/metaTypes?__code=mc_dp_npr_level"/>
                                </select>
                                <script>         $("#searchForm select[name=level]").val('${param.level}');     </script>
                            </div>
                            <div class="form-group">
                                <label>参加工作时间</label>
                                <div class="input-group tooltip-success" data-rel="tooltip" title="成立时间范围">
                                                    <span class="input-group-addon">
                                                        <i class="fa fa-calendar bigger-110"></i>
                                                    </span>
                                    <input placeholder="请选择成立时间范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="workTime" value="${param.workTime}"/>
                                </div>
                            </div>
                            <c:if test="${cls==2}">
                            <div class="form-group">
                                <label>撤销时间</label>
                                <div class="input-group tooltip-success" data-rel="tooltip" title="成立时间范围">
                                                    <span class="input-group-addon">
                                                        <i class="fa fa-calendar bigger-110"></i>
                                                    </span>
                                    <input placeholder="请选择成立时间范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="transferTime" value="${param.transferTime}"/>
                                </div>
                            </div>
                            </c:if>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/dp/dpNpr?cls=${cls}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/dp/dpNpr?cls=${cls}"
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
                </div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
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
    $.register.multiselect($('#searchForm select[name=nation]'), ${cm:toJSONArray(selectNations)});
    $.register.multiselect($('#searchForm select[name=nativePlace]'), ${cm:toJSONArray(selectNativePlaces)});

    $("#jqGrid").jqGrid({
        url: '${ctx}/dp/dpNpr_data?callback=?&cls=${cls}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '姓名', name: 'user.realname', width: 75, formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.userId > 0 && $.trim(cellvalue) != '')
                        return '<a href="javascript:;" class="openView" data-url="{2}/dp/dpMember_view?userId={0}">{1}</a>'
                            .format(rowObject.userId, cellvalue, ctx);
                    return $.trim(cellvalue);
                }, frozen: true
            },
                { label: '工作证号',name: 'user.code',width: 120,sortable:true,frozen:true},
                <c:if test="${cls==2}">
                { label: '离任时间',name: 'transferTime',width:120,sortable:true,
                    formatter: $.jgrid.formatter.date,
                    formatoptions: {newformat: 'Y.m.d'}},
                </c:if>
                { label: '性别', name: 'gender', formatter:$.jgrid.formatter.GENDER},
                { label: '民族', name: 'nation'},
                { label: '出生时间', name :'birth', width: 120,sortable: true,
                    formatter: $.jgrid.formatter.date,
                    formatoptions: {newformat: 'Y.m.d'}},
            {
                label: '所属党派', name: 'dpParty.name', width: 300, formatter: function (cellvalue, options, rowObject) {
                    var _dpPartyView = null;
                    if ($.inArray("dpParty:list", _permissions) >= 0 || $.inArray("dpParty:*", _permissions) >= 0)
                        _dpPartyView = '<a href="javascript:;" class="openView" data-url="{2}/dp/dpParty_view?id={0}">{1}</a>'
                            .format(rowObject.partyId, cellvalue, ctx);
                    if (cellvalue != undefined){
                        return '<span class="{0}">{1}</span>'.format(rowObject.isDeleted ? "delete" : "", _dpPartyView);
                    }
                    return "--";
                }, sortable: true
            },
                { label: '加入党派时间',name: 'growTime',width:120,sortable:true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}},
                { label: '参加工作时间',name: 'workTime',width:120,sortable:true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}},
                { label: '所属单位',name: 'unitId',width:180,sortable:true,formatter:$.jgrid.formatter.unit},
                { label: '所属单位及职务',name: 'unitPost',width:120,sortable:true},
                { label: '所属类别',name: 'type',width:280,sortable:true,formatter: $.jgrid.formatter.MetaType},
                { label: '所属级别',name: 'level',formatter: $.jgrid.formatter.MetaType},
                { label: '最高学历',name: 'education'},
                { label: '最高学位',name: 'degree'},
                { label: '毕业学校',name: 'school'},
                { label: '所学专业',name: 'major'},
                { label: '办公电话',name: 'phone',width:120},
                { label: '手机号',name: 'mobile',width:120},
                { label: '备注',name: 'remark',width:120,sortable:true},
                { hidden:true,key:true,name:'id'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>