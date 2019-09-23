<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="DP_MEMBER_SOURCE_MAP" value="<%=DpConstants.DP_MEMBER_SOURCE_MAP%>"/>
<c:set var="DP_MEMBER_TYPE_TEACHER" value="<%=DpConstants.DP_MEMBER_TYPE_TEACHER%>"/>
<c:set var="DP_MEMBER_TYPE_STUDENT" value="<%=DpConstants.DP_MEMBER_TYPE_STUDENT%>"/>
<c:set var="DP_MEMBER_STATUS_NORMAL" value="<%=DpConstants.DP_MEMBER_STATUS_NORMAL%>"/>
<c:set var="DP_MEMBER_STATUS_TRANSFER" value="<%=DpConstants.DP_MEMBER_STATUS_TRANSFER%>"/>
<c:set var="DP_MEMBER_POLITICAL_STATUS_MAP" value="<%=DpConstants.DP_MEMBER_POLITICAL_STATUS_MAP%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv " data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId ||not empty param.unitId ||not empty param.gender
            ||not empty param.partyId ||not empty param.politicalStatus ||not empty param.type || not empty selectNations ||not empty selectNativePlaces
            ||not empty param.status ||not empty param.source ||not empty param.partyPost
            || not empty param.code || not empty param.sort ||not empty param._outHandleTime ||not empty param._positiveTime ||not empty param._growTime
            ||not empty param.isHonorRetire ||not empty param._retireTime ||not empty param.education}"/>
            <div class="tabbable">
                <jsp:include page="/WEB-INF/jsp/dp/dpMember/dpMember_menu.jsp"/>
                <div class="tab-content">
                    <div class="tab-pane in active">
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="dpMember:edit">
                    <button class="jqEditBtn btn btn-primary btn-sm"
                       data-url="${ctx}/dp/dpMember_au"
                            data-open-by="page"
                            data-id-name="userId"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改党籍信息</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="dpMember:del">
                    <button data-url="${ctx}/dp/dpMember_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                        data-url="${ctx}/dp/dpMember_data?cls=${cls}"
                        data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
                <c:if test="${cls!=7}">
                    <shiro:hasPermission name="dpMember:del">
                        <a class="jqOpenViewBatchBtn btn btn-danger btn-sm"
                           data-url="${ctx}/dp/dpMember_transfer" data-title="转出民主党派"
                           data-msg="确定转出这{0}个党派成员吗？"><i class="fa fa-history"></i> 转出</a>
                    </shiro:hasPermission>
                </c:if>
                <c:if test="${cls==7}">
                    <shiro:hasPermission name="dpMember:del">
                        <a class="jqBatchBtn btn btn-success btn-sm"
                           data-url="${ctx}/dp/dpMember_recover" data-title="恢复党派成员身份"
                           data-msg="确定恢复这{0}个党派成员身份吗？"><i class="fa fa-reply"></i> 恢复</a>
                    </shiro:hasPermission>
                </c:if>
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
                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                </select>
                            </div>
                        </div>
                            <div class="form-group">
                                <label>所在民主党派</label>
                                <select class="form-control" data-width="300" data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/dp/dpParty_selects?auth=1"
                                        name="partyId" data-placeholder="请选择">
                                    <option value="${dpParty.id}" delete="${dpParty.isDeleted}">${dpParty.name}</option>
                                </select>
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
                                    <select class="multiselect" name="nativePlace" multiple="">
                                        <c:forEach items="${nativePlaces}" var="nativePlace">
                                            <option value="${nativePlace}">${nativePlace}</option>
                                        </c:forEach>
                                    </select>

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
                                <label>党籍状态</label>
                                <select required data-rel="select2" name="politicalStatus"
                                        data-placeholder="请选择" data-width="120">
                                    <option></option>
                                    <c:forEach items="${DP_MEMBER_POLITICAL_STATUS_MAP}" var="_status">
                                        <option value="${_status.key}">${_status.value}</option>
                                    </c:forEach>
                                </select>
                                <script>
                                    $("#searchForm select[name=politicalStatus]").val(${param.politicalStatus});
                                </script>
                            </div>
                            <c:if test="${cls==2 || cls==3 || cls==7}">
                                <div class="form-group">
                                    <label>最高学历</label>
                                    <div class="input-group">
                                        <select name="education" data-rel="select2" data-placeholder="请选择">
                                            <option></option>
                                            <c:forEach items="${teacherEducationTypes}" var="education">
                                                <option value="${education}">${education}</option>
                                            </c:forEach>
                                        </select>
                                        <script>
                                            $("#searchForm select[name=education]").val('${param.education}');
                                        </script>
                                    </div>
                                </div>

                                <c:if test="${cls==3 || cls==7}">
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
                                    <div class="form-group">
                                        <label>是否离休</label>
                                        <select name="isHonorRetire" data-width="100" data-rel="select2"
                                                data-placeholder="请选择">
                                            <option></option>
                                            <option value="1">是</option>
                                            <option value="0">否</option>
                                        </select>
                                        <script>
                                            $("#searchForm select[name=isHonorRetire]").val('${param.isHonorRetire}');
                                        </script>
                                    </div>
                                </c:if>
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
            {
                label: '姓名', name: 'user.realname', width: 75, formatter: function (cellvalue, options, rowObject) {
                        if (rowObject.userId > 0 && $.trim(cellvalue) != '')
                            return '<a href="javascript:;" class="openView" data-url="{2}/dp/dpMember_view?userId={0}">{1}</a>'
                                .format(rowObject.userId, cellvalue, ctx);
                        return $.trim(cellvalue);
                }, frozen: true
            },
            {label: '学工号', name: 'user.code', width: 120, frozen: true},
            <c:if test="${cls==7}">
            {
                label: '转出时间',
                name: 'transferTime',
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            </c:if>
            {label: '性别', name: 'gender', width: 55, formatter:$.jgrid.formatter.GENDER},
            {label: '民族', name: 'nation'},
            {label: '籍贯', name: 'nativePlace', width: 140},
            {label: '出生时间', name :'growTime', width: 120,sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}},
            {label: '年龄', name: 'birth', width: 55, formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    return $.yearOffNow(cellvalue);
                },},
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
            {
                label: '党籍状态', name: 'politicalStatus', formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue)
                        return _cMap.MEMBER_POLITICAL_STATUS_MAP[cellvalue];
                    return "-";
                }
            },
            {
                label: '党派加入时间',
                name: 'growTime',
                width: 120,
                sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {   label: '入党介绍人', name: 'sponsor'},
            {
                label: '转正时间',
                name: 'positiveTime',
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            <c:if test="${cls==1 || cls==6}">
            {label: '学生类别', name: 'studentType', width: 150},
            {label: '年级', name: 'grade', width: 90},
            {label: '培养层次', name: 'eduLevel'},
            {label: '培养类型', name: 'eduType'},
            </c:if>
            <c:if test="${cls==2 || cls==7}">
            {label: '最高学历学位', name: 'education', width: 120},
            {label: '编制类别', name: 'authorizedType'},
            /*{label: '人员类别', name: 'staffType'},
            {label: '岗位类别', name: 'postClass'},*/
            {label: '专业技术职务', name: 'proPost', width: 150},
            {label: '联系手机', name: 'mobile', width: 120},
            </c:if>
            <c:if test="${cls==3||cls==7}">
            {label: '退休时间', name: 'retireTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '是否离休', name: 'isHonorRetire', formatter: $.jgrid.formatter.TRUEFALSE},
            </c:if>
            {label: '所在单位', name: 'unitId', width: 180, formatter: $.jgrid.formatter.unit},
            {label: '所在院系', name: 'user.unit', width: 250},
            {hidden: true, key: true, name: 'userId'}, {hidden: true, name: 'partyId'}, {hidden: true, name: 'source'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $.initNavGrid("jqGrid", "jqGridPager");
</script>