<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="DP_MEMBER_TYPE_MEMBER" value="<%=DpConstants.DP_MEMBER_TYPE_MEMBER%>"/>
<c:set var="DP_MEMBER_SOURCE_MAP" value="<%=DpConstants.DP_MEMBER_SOURCE_MAP%>"/>
<c:set var="DP_MEMBER_TYPE_TEACHER" value="<%=DpConstants.DP_MEMBER_TYPE_TEACHER%>"/>
<c:set var="DP_MEMBER_STATUS_NORMAL" value="<%=DpConstants.DP_MEMBER_STATUS_NORMAL%>"/>
<c:set var="DP_MEMBER_STATUS_TRANSFER" value="<%=DpConstants.DP_MEMBER_STATUS_TRANSFER%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv " data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId ||not empty param.unit ||not empty param.gender||not empty param.age
            ||not empty param.partyId ||not empty param.type ||not empty selectNations ||not empty selectNativePlaces ||not empty selectAdminLevels||not empty selectPosts
            ||not empty param.status ||not empty param.source ||not empty param.partyPost ||not empty param.proPost
            ||not empty param._dpGrowTime ||not empty param.isPartyMember
            ||not empty param._retireTime ||not empty param.edu ||not empty param.degree ||not empty param.politicalAct }"/>
            <div class="tabbable">
                <jsp:include page="/WEB-INF/jsp/dp/dpMember/dpMember_menu.jsp"/>
                <div class="tab-content">
                    <div class="tab-pane in active">
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="dpMember:edit">
                    <c:if test="${cls!=7}">
                    <a href="javascript:;" class="openView btn btn-info btn-sm" data-url="${ctx}/dp/dpMember_au">
                        <i class="fa fa-plus"></i> 添加成员</a>
                    </c:if>
                    <button class="jqEditBtn btn btn-primary btn-sm"
                       data-url="${ctx}/dp/dpMember_au"
                            data-open-by="page"
                            data-id-name="userId"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改成员信息</button>
                </shiro:hasPermission>
                <c:if test="${cls!=7}">
                    <shiro:hasPermission name="dpMember:del">
                        <a class="jqOpenViewBatchBtn btn btn-danger btn-sm"
                           data-url="${ctx}/dp/dpMember_out" data-title="移除民主党派"
                           data-msg="确定移除这{0}个党派成员吗？"><i class="fa fa-minus-square"></i> 移除</a>
                    </shiro:hasPermission>
                <shiro:hasPermission name="dpMember:edit">
                    <button class="popupBtn btn btn-info btn-sm tooltip-info"
                    data-url="${ctx}/dp/dpMember_import"
                    data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                    批量导入
                    </button>
                </shiro:hasPermission>
                </c:if>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                        data-url="${ctx}/dp/dpMember_data?cls=${cls}"
                        data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
                <c:if test="${cls!=7}">
                    <a data-type="${DP_MEMBER_TYPE_MEMBER}" class="syncBtn btn btn-success btn-sm"
                       data-loading-text="<i class='fa fa-refresh fa-spin'></i> 干部档案表信息同步中..."
                       autocomplete="off"><i class="fa fa-refresh"></i> 干部档案表信息同步</a>
                </c:if>
                <c:if test="${cls==7}">
                    <shiro:hasPermission name="dpMember:del">
                        <a class="jqBatchBtn btn btn-success btn-sm"
                           data-url="${ctx}/dp/dpMember_recover" data-title="恢复党派成员身份"
                           data-msg="确定恢复这{0}个党派成员身份吗？"><i class="fa fa-reply"></i> 恢复</a>
                    </shiro:hasPermission>
                </c:if>
                <shiro:hasPermission name="dpMember:del">
                    <button data-url="${ctx}/dp/dpMember_batchDel"
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
                            <input type="hidden" name="cols">
                            <input type="hidden" name="cls" value="${cls}">
                        <div class="form-group">
                            <label>成员姓名</label>
                            <div class="input-group">
                                <c:if test="${cls==2 || cls==7}">
                                    <c:set var="_type" value="${DP_MEMBER_TYPE_TEACHER}"/>
                                </c:if>
                                <c:if test="${cls==1 || cls==2}">
                                    <c:set var="_status" value="${DP_MEMBER_STATUS_NORMAL}"/>
                                </c:if>
                                <c:if test="${cls==6 || cls==7}">
                                    <c:set var="_status" value="${DP_MEMBER_STATUS_TRANSFER}"/>
                                </c:if>
                                <select data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/dp/dpMember_selects?type=${_type}&status=${_status}"
                                        name="userId" data-placeholder="请输入账号或姓名或工作证号">
                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                </select>
                            </div>
                        </div>
                            <div class="form-group">
                                <label>所在民主党派</label>
                                <select class="form-control" data-width="230" data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/dp/dpParty_selects?auth=1"
                                        name="partyId" data-placeholder="请选择">
                                    <option value="${dpParty.id}" delete="${dpParty.isDeleted}">${dpParty.name}</option>
                                </select>
                            </div>
                            <%--<div class="form-group">
                                <label>部门名称</label>
                                <input class="form-control search-query" name="unit" type="text" value="${param.unit}"
                                       placeholder="请输入部门名称">
                            </div>--%>
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
                                <label>年龄</label>
                                <select name="age" data-width="150" data-rel="select2"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <c:forEach items="<%=DpConstants.DP_MEMBER_AGE_MAP%>" var="age">
                                        <option value="${age.key}">${age.value}</option>
                                    </c:forEach>
                                </select>
                                <script>
                                    $("#searchForm select[name=age]").val('${param.age}');
                                </script>
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
                                    <select class="multiselect" multiple="" name="nativePlace">
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
                            <div class="form-group">
                                <label>行政级别</label>
                                <div class="input-group">
                                    <select class="multiselect" name="adminLevel" multiple="">
                                        <c:forEach items="${metaAdminLevel}" var="metaType">
                                        <c:forEach items="${adminLevels}" var="adminLevel">
                                            <c:if test="${metaType.id==adminLevel}">
                                            <option value="${adminLevel}">${metaType.name}</option>
                                            </c:if>
                                        </c:forEach>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>行政职务</label>
                                <div class="input-group">
                                    <select class="multiselect" name="post" multiple="">
                                        <c:forEach items="${posts}" var="post">
                                            <option value="${post}">${post}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>加入党派时间</label>
                                <div class="input-group tooltip-success" data-rel="tooltip" title="加入党派时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                    <input placeholder="请选择加入党派时间范围" data-rel="date-range-picker"
                                           class="form-control date-range-picker"
                                           type="text" name="_dpGrowTime" value="${param._dpGrowTime}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>是否是共产党员</label>
                                <select name="isPartyMember" data-width="100" data-rel="select2"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <option value="1">是</option>
                                    <option value="0">否</option>
                                </select>
                                <script>
                                    $("#searchForm select[name=isPartyMember]").val('${param.isPartyMember}');
                                </script>
                            </div>
                                <c:if test="${cls==3 || cls==7 || cls==10}">
                                    <div class="form-group">
                                        <label>退休时间</label>
                                        <div class="input-group tooltip-success" data-rel="tooltip"
                                             title="退休时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                            <input placeholder="请选择退休时间范围" data-rel="date-range-picker"
                                                   class="form-control date-range-picker"
                                                   type="text" name="_retireTime"
                                                   value="${param._retireTime}"/>
                                        </div>
                                    </div>
                                </c:if>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/dp/dpMember?cls=${cls}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/dp/dpMember?cls=${cls}"
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
                </div></div></div>
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

    $('#searchForm [data-rel="select2"]').select2();
    $.register.multiselect($('#searchForm select[name=post]'), ${cm:toJSONArray(selectPosts)});
    $.register.multiselect($('#searchForm select[name=adminLevel]'), ${cm:toJSONArray(selectAdminLevels)});
    $.register.multiselect($('#searchForm select[name=nation]'), ${cm:toJSONArray(selectNations)});
    $.register.multiselect($('#searchForm select[name=nativePlace]'), ${cm:toJSONArray(selectNativePlaces)});

    $('[data-rel="tooltip"]').tooltip();
    $.register.user_select($('#searchForm select[name=userId]'));

    $("#jqGrid").jqGrid({
        /*multiboxonly:false,*/
        ondblClickRow: function () {
        },
        url: '${ctx}/dp/dpMember_data?callback=?&cls=${cls}&${cm:encodeQueryString(pageContext.request.queryString)}',
        sortname: 'dpParty',
        colModel: [
            {label: '工作证号', name: 'user.code', width: 120, frozen: true},
            {
                label: '姓名', name: 'user.realname', width: 75, formatter: function (cellvalue, options, rowObject) {
                        if (rowObject.userId > 0 && $.trim(cellvalue) != '')
                            return '<a href="javascript:;" class="openView" data-url="{2}/dp/dpMember_view?userId={0}">{1}</a>'
                                .format(rowObject.userId, cellvalue, ctx);
                        return $.trim(cellvalue);
                }, frozen: true
            },
            <c:if test="${cls==7}">
            {
                label: '转出时间',
                name: 'outTime',
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            </c:if>
            {label: '性别', name: 'gender', width: 55, formatter:$.jgrid.formatter.GENDER},
            {label: '民族', name: 'nation'},
            {label: '籍贯', name: 'nativePlace', width: 140},
            {label: '出生时间', name :'birth', width: 120,sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}},
            {label: '年龄', name: 'birth', width: 55, formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    return $.yearOffNow(cellvalue);
                },},
            {
                label: '所属党派', name: 'dpParty.name', width: 200, formatter: function (cellvalue, options, rowObject) {
                    var _dpPartyView = null;
                    if ($.inArray("dpParty:list", _permissions) >= 0 || $.inArray("dpParty:*", _permissions) >= 0)
                        _dpPartyView = '<a href="javascript:;" class="openView" data-url="{2}/dp/dpParty_view?id={0}">{1}</a>'
                            .format(rowObject.partyId, cellvalue, ctx);
                    if (cellvalue != undefined){
                        return '<span class="{0}">{1}</span>'.format(rowObject.dpParty.isDeleted ? "delete" : "", _dpPartyView);
                    }
                    return "--";
                }, sortable: true
            },
            {label: '部门', name: 'unit', width: 200},
            {label: '党派内职务', name: 'dpPost', width: 180},
            {label: '兼职（其他校外职务）', name: 'partTimeJob', width: 180},
            {label: '行政职务', name: 'post', width: 180},
            {label: '行政级别', name: 'adminLevel', formatter:$.jgrid.formatter.MetaType},
            {label: '职称', name: 'proPost'},
            {label: '是否是共产党员', name: 'isPartyMember', width: 120, formatter:$.jgrid.formatter.TRUEFALSE},
            {
                label: '加入党派时间',
                name: 'dpGrowTime',
                width: 120,
                sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
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
            {label: '培训情况', name: 'trainState', width: 200},
            {label: '政治表现', name: 'politicalAct', width: 200},
            {label: '党内奖励', name: 'partyReward', width: 200},
            {label: '其他奖励', name: 'otherReward', width: 200},
            {label: '通讯地址', name: 'address', width: 200},
            <c:if test="${cls==2 || cls==7}">
            {label: '学历', name: 'edu', width: 120},
            {label: '学位', name: 'degree', width: 120},
            {label: '联系手机', name: 'mobile', width: 120},
            </c:if>
            {label: '邮箱', name: 'email'},
            <c:if test="${cls==3||cls==7}">
            {label: '退休时间', name: 'retireTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            /*{label: '是否离休', name: 'isHonorRetire', formatter: $.jgrid.formatter.TRUEFALSE},*/
            </c:if>
            {label: '备注', name: 'remark', width: 200},
            {hidden: true, key: true, name: 'userId'}, {hidden: true, name: 'partyId'}, {hidden: true, name: 'source'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $.initNavGrid("jqGrid", "jqGridPager");
</script>