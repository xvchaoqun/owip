<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="DP_MEMBER_SOURCE_MAP" value="<%=DpConstants.DP_MEMBER_SOURCE_MAP%>"/>
<c:set var="DP_MEMBER_TYPE_TEACHER" value="<%=DpConstants.DP_MEMBER_TYPE_TEACHER%>"/>
<c:set var="DP_MEMBER_TYPE_STUDENT" value="<%=DpConstants.DP_MEMBER_TYPE_STUDENT%>"/>
<c:set var="DP_MEMBER_STATUS_NORMAL" value="<%=DpConstants.DP_MEMBER_STATUS_NORMAL%>"/>
<c:set var="DP_MEMBER_STATUS_TRANSFER" value="<%=DpConstants.DP_MEMBER_STATUS_TRANSFER%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers " data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId ||not empty param.partyId ||not empty param.politicalStatus ||not empty param.type ||not empty param.status ||not empty param.source ||not empty param.partyPost || not empty param.code || not empty param.sort}"/>
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
                   <c:if test="${cls!=10}">
                       <div class="btn-group">
                           <button data-toggle="dropdown"
                                   data-rel="tooltip" data-placement="top" data-html="true"
                                   title="<div style='width:180px'>导出选中记录或所有搜索结果</div>"
                                   class="btn btn-success btn-sm dropdown-toggle tooltip-success">
                               <i class="fa fa-download"></i> 导出 <span class="caret"></span>
                           </button>
                           <ul class="dropdown-menu dropdown-success" role="menu" style="z-index: 1031">
                               <li class="dropdown-hover" data-stopPropagation="true">
                                   <a href="javascript:;">
                                       <i class="fa fa-file-excel-o"></i> 导出党员信息
                                       <i class="ace-icon fa fa-caret-right pull-right"></i>
                                   </a>
                                   <div class="dropdown-menu" style="width: 675px;top:-220px;">
                                       <form class="form-horizontal" id="exportForm">
                                           <div style="padding: 7px 0 10px 10px">
                                               <c:forEach items="${titles}" var="title" varStatus="vs">
                                                   <div style="padding-left:5px;float: left;width:220px">
                                                       <input class="big" type="checkbox" value="${vs.index}"
                                                              checked>
                                                           ${fn:split(title, "|")[0]}</div>
                                               </c:forEach>
                                               <div style="clear: both"/>
                                           </div>
                                           <div class="form-actions center">
                                               <div style="position: absolute; float:left; left:10px;padding-top: 3px">
                                                   <input type="button" id="btnSelectAll"
                                                          class="btn btn-success btn-xs" value="全选"/>
                                                   <input type="button" id="btnDeselectAll"
                                                          class="btn btn-danger btn-xs" value="全不选"/>
                                               </div>
                                               <button type="button" class="jqExportBtn btn btn-success"
                                                       data-need-id="false" data-url="${ctx}/dp/dpMember_data?cls=${cls}"
                                                       data-querystr="format=1">
                                                   <i class="fa fa-file-excel-o"></i> 导出
                                               </button>
                                           </div>
                                       </form>
                                   </div>
                               </li>
                           </ul>
                       </div>
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
                                <c:if test="${cls==1 || cls==6}">
                                    <c:set var="_type" value="${DP_MEMBER_TYPE_STUDENT}"/>
                                </c:if>
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
                                <label>民主党派所在单位</label>
                                <select name="unitId" data-rel="select2" data-placeholder="请选择">
                                    <option></option>
                                    <c:forEach items="${unitMap}" var="unit">
                                        <option value="${unit.key}">${unit.value.name}</option>
                                    </c:forEach>
                                </select>
                                <script>
                                    $("#searchForm select[name=unitId]").val('${param.unitId}');
                                </script>
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
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/dp/dpMember"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/dp/dpMember"
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

    $.register.date($('.date-picker'));
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
            {label: '性别', name: 'user.gender', width: 55, formatter: $.jgrid.formatter.GENDER},
            {label: '民族', name: 'user.nation'},
            {label: '籍贯', name: 'user.nativePlace', width: 120},
            {label: '年龄', name: 'user.birth', width: 55, formatter: $.jgrid.formatter.AGE},
            {
                label: '所属组织机构', name: 'dpParty', width: 550, formatter: function (cellvalue, options, rowObject) {
                    return $.party(rowObject.partyId);
                }, sortable: true, align: 'left'
            },
            {
                label: '党籍状态', name: 'politicalStatus', formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue)
                        return _cMap.MEMBER_POLITICAL_STATUS_MAP[cellvalue];
                    return "-";
                }
            },
            {
                label: '入党时间',
                name: 'growTime',
                width: 120,
                sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y-m-d'}
            },
            {
                label: '转正时间',
                name: 'positiveTime',
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y-m-d'}
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
            {label: '人员类别', name: 'staffType'},
            {label: '岗位类别', name: 'postClass'},
            {label: '专业技术职务', name: 'proPost', width: 150},
            {label: '联系手机', name: 'mobile', width: 110},
            </c:if>
            <c:if test="${cls==6 || cls==7}">
            {
                label: '转出时间',
                name: 'outHandleTime',
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y-m-d'}
            },
            </c:if>

            <c:if test="${cls==3||cls==7}">
            {label: '退休时间', name: 'retireTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}},
            {label: '是否离休', name: 'isHonorRetire', formatter: $.jgrid.formatter.TRUEFALSE},
            </c:if>
            {label: '所在单位', name: 'unitId', width: 180, align: 'left', formatter: $.jgrid.formatter.unit},
            {label: '所在院系', name: 'user.unit', width: 180, align: 'left'},
            {hidden: true, key: true, name: 'userId'}, {hidden: true, name: 'partyId'}, {hidden: true, name: 'source'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    $.initNavGrid("jqGrid", "jqGridPager");
    <shiro:hasRole name="${ROLE_DPPARTYADMIN}">
    $("#jqGrid").navButtonAdd('#jqGridPager', {
        caption: "民主党派内部组织关系变动",
        btnbase: "branchChangeBtn btn btn-info btn-xs",
        buttonicon: "fa fa-random",
        onClickButton: function () {
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length == 0) {
                SysMsg.warning("请选择行", "提示");
                return;
            }
            //alert(ids)
            var rowData = $(this).getRowData(ids[0]);
            //console.log("ids[0]" + ids[0] +rowData)
            $.loadModal("${ctx}/member_changeBranch?ids[]={0}&partyId={1}".format(ids, rowData.partyId))
        }
    });
    </shiro:hasRole>
</script>