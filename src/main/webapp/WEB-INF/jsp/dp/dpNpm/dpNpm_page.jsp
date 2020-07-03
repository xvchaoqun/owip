<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="DP_MEMBER_TYPE_NPM" value="<%=DpConstants.DP_MEMBER_TYPE_NPM%>"/>
<c:set var="DP_NPM_NORMAL" value="<%=DpConstants.DP_NPM_NORMAL%>"/>
<c:set var="DP_NPM_OUT" value="<%=DpConstants.DP_NPM_OUT%>"/>
<c:set var="DP_NPM_TRANSFER" value="<%=DpConstants.DP_NPM_TRANSFER%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId ||not empty param.post ||not empty param.education
            ||not empty param.degree ||not empty param.unit ||not empty param._addAime
            || not empty param.sort ||not empty param.gender ||not empty selectNations|| not empty selectNativePlaces}"/>
            <div class="tabbale">
            <jsp:include page="menu.jsp"/>
                <div class="tab-content">
                    <div class="tab-pane in active">
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="dpNpm:edit">
                    <c:if test="${cls==1}">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/dp/dpNpm_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    </c:if>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/dp/dpNpm_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="dpNpm:del">
                    <c:if test="${cls==1}">
                        <button data-url="${ctx}/dp/dpNpm_out"
                                data-title="移除"
                                data-msg="确定移除这{0}条数据无党派人士？"
                                data-grid-id="#jqGrid"
                                class="jqOpenViewBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-minus-square"></i> 移除
                        </button>
                    </c:if>
                </shiro:hasPermission>
                <c:if test="${cls==1}">
                <button class="popupBtn btn btn-info btn-sm tooltip-info"
                        data-url="${ctx}/dp/dpNpm_import"
                        data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                    批量导入
                </button>
                </c:if>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/dp/dpNpm_data?cls=${cls}"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
                <%--<c:if test="${cls==1}">
                    <button data-url="${ctx}/dp/dpNpm_transfer"
                            data-title="转出"
                            data-msg="确定转出这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqOpenViewBatchBtn btn btn-primary btn-sm">
                        <i class="fa fa-random"></i> 转出
                    </button>
                </c:if>--%>
                <c:if test="${cls!=1}">
                    <shiro:hasPermission name="dpNpm:edit">
                        <a class="jqBatchBtn btn btn-success btn-sm"
                           data-url="${ctx}/dp/dpNpm_recover" data-title="恢复无党派成员身份"
                           data-msg="确定恢复这{0}个无党派成员身份吗？"><i class="fa fa-reply"></i> 恢复</a>
                    </shiro:hasPermission>
                </c:if>
                <c:if test="${cls==1}">
                    <a data-type="${DP_MEMBER_TYPE_NPM}" class="syncBtn btn btn-success btn-sm"
                       data-loading-text="<i class='fa fa-refresh fa-spin'></i> 干部档案表信息同步中..."
                       autocomplete="off"><i class="fa fa-refresh"></i> 干部档案表信息同步</a>
                </c:if>
                    <shiro:hasPermission name="dpNpm:del">
                        <button data-url="${ctx}/dp/dpNpm_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> 删除
                        </button>
                    </shiro:hasPermission>
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
                            <input type="hidden" name="cls" value="${cls}"/>
                            <div class="form-group">
                                <label>成员姓名</label>
                                <div class="input-group">
                                    <c:if test="${cls==1}">
                                        <c:set var="_status" value="${DP_NPM_NORMAL}"/>
                                    </c:if>
                                    <c:if test="${cls==2}">
                                        <c:set var="_status" value="${DP_NPM_OUT}"/>
                                    </c:if>
                                    <c:if test="${cls==3}">
                                        <c:set var="_status" value="${DP_NPM_TRANSFER}"/>
                                    </c:if>
                                    <select data-rel="select2-ajax"
                                            data-ajax-url="${ctx}/dp/dpNpm_selects?status=${_status}"
                                            name="userId" data-placeholder="请输入账号或姓名或工作证号">
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
                                <label>认定时间</label>
                                <div class="input-group tooltip-success" data-rel="tooltip" title="认定时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                    <input placeholder="请选择认定时间范围" data-rel="date-range-picker"
                                           class="form-control date-range-picker"
                                           type="text" name="_addAime" value="${param._addAime}"/>
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
                                <label>籍贯</label>
                                <div class="input-group">
                                    <select class="multiselect" name="nativePlace" multiple="">
                                        <c:forEach items="${nativePlaces}" var="nativePlace">
                                            <option value="${nativePlace}">${nativePlace}</option>
                                        </c:forEach>
                                    </select>

                                </div>
                            </div>
                            <div class="form-group">
                                <label>部门名称</label>
                                <input class="form-control search-query" name="unit" type="text" value="${param.unit}"
                                       placeholder="请输入部门名称">
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/dp/dpNpm?cls=${cls}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/dp/dpNpm?cls=${cls}"
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

    //同步干部档案表信息至统战模块
    var interval = null;
    clearInterval(interval);
    $(".syncBtn").click(function(){
        var $this = $(this);
        bootbox.confirm("确认" + $.trim($this.text()) + "（会用干部档案表的信息覆盖属于干部身份的统战人员的档案表信息，确认继续同步）？", function (result) {
            if (result) {
                var $btn = $this.button('loading')
                $.post("${ctx}/dp/dpSyncCadreInfo",{cls:$this.data("type")},function(ret){
                    if(ret.success){
                        SysMsg.success('干部档案表信息同步完成！');
                        $.reloadMetaData(function () {
                            $btn.button('reset');
                        });
                        //clearTimeout(t);
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                });
                clearInterval(interval);
            }
        });
    });

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
        url: '${ctx}/dp/dpNpm_data?callback=?&cls=${cls}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'user.code', width: 120,frozen: true,sortable:true},
        {
            label: '姓名', name: 'user.realname', width: 75, formatter: function (cellvalue, options, rowObject) {
                if (rowObject.userId > 0 && $.trim(cellvalue) != '')
                    return '<a href="javascript:;" class="openView" data-url="{2}/dp/dpMember_view?userId={0}">{1}</a>'
                        .format(rowObject.userId, cellvalue, ctx);
                return $.trim(cellvalue);
            }, frozen: true
        },
            <c:if test="${cls==2}">
            { label: '移除时间',name: 'outTime', width: 120, formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}},
            </c:if>
            {label: '性别', name: 'gender', formatter:$.jgrid.formatter.GENDER},
            {label: '民族', name: 'nation'},
            {label: '籍贯', name: 'nativePlace', width: 120},
            {label: '出生时间', name :'birth', width: 120,sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}},
            {label: '年龄', name: 'birth', width: 55, formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    return $.yearOffNow(cellvalue);
                },},
                { label: '认定时间',name: 'addTime', width: 120, formatter: $.jgrid.formatter.date,
                    formatoptions: {newformat: 'Y.m.d'}},
            { label: '部门',name: 'unit',width:200},
                { label: '现任职务',name: 'post',width:180},
            {
                label: '人大代表、政协委员', name: 'types', width: 270, formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    var typeIdStrs = [];
                    var typeIds = cellvalue.split(",");
                    $.each(typeIds, function (i, typeId) {
                        //console.log(typeId)
                        typeIdStrs.push($.jgrid.formatter.MetaType(typeId));
                    })
                    //console.log(typeIdStrs)
                    return typeIdStrs.join(",");
                }
            },
                { label: '最高学历',name: 'education', width: 120},
                { label: '最高学位',name: 'degree', width: 120},
                { label: '办公电话',name: 'phone',width:120},
                { label: '手机号',name: 'mobile',width:120},
                { label: '备注',name: 'remark',width:200},
                {hidden: true, key: true, name: 'id'},{hidden:true, name: 'userId'}]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>