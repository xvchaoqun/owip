<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=CmConstants.CM_MEMBER_TYPE_MAP%>" var="CM_MEMBER_TYPE_MAP"/>
<c:set value="<%=CmConstants.CM_MEMBER_TYPE_JW%>" var="CM_MEMBER_TYPE_JW"/>
<c:set value="<%=CmConstants.CM_MEMBER_TYPE_CW%>" var="CM_MEMBER_TYPE_CW"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <li class="<c:if test="${!isQuit}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/cmMember?type=${type}&isQuit=0">
                        <i class="fa fa-circle-o-notch"></i> 现任${CM_MEMBER_TYPE_MAP.get(type)}</a>
                </li>
                <li class="<c:if test="${isQuit}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/cmMember?type=${type}&isQuit=1">
                        <i class="fa fa-history"></i> 离任${CM_MEMBER_TYPE_MAP.get(type)}</a>
                </li>
            </ul>
            <div class="space-4"></div>
            <c:set var="_query"
                   value="${not empty param.userId ||not empty param.gender
                    ||not empty param.nation || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cmMember:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/cmMember_au?type=${type}&isQuit=${isQuit?1:0}">
                        <i class="fa fa-plus"></i> 添加
                    </button>
                    <shiro:hasPermission name="pcs:menu">
                    <c:if test="${!isQuit && type==CM_MEMBER_TYPE_CW}">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/cmMember_draw" data-width="800">
                        <i class="fa fa-search-plus"></i> 从本届党委委员库中提取
                    </button>
                    </c:if>
                    </shiro:hasPermission>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/cmMember_au?type=${type}&isQuit=${isQuit?1:0}"
                            data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadre:leave">
                    <c:if test="${!isQuit}">
                    <button class="jqOpenViewBtn btn btn-warning btn-sm"
                            data-url="${ctx}/cmMember_leave?type=${type}">
                        <i class="fa fa-sign-out"></i> 离任
                    </button>
                    </c:if>
                    <c:if test="${isQuit}">
                    <button class="jqItemBtn btn btn-success btn-sm"
                            data-msg="确认返回现任库？"
                            data-querystr="type=${type}&isQuit=0"
                            data-url="${ctx}/cmMember_leave">
                        <i class="fa fa-reply"></i> 返回现任库
                    </button>
                    </c:if>
                </shiro:hasPermission>
                <c:if test="${!isQuit}">
                    <shiro:hasPermission name="cmMember:export">
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                        data-url="${ctx}/cmMember_data?type=${type}&isQuit=0"
                        data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出
                </button>
                    </shiro:hasPermission>
                    </c:if>
                <shiro:hasPermission name="cmMember:del">
                    <button data-url="${ctx}/cmMember_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
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
                            <div class="form-group">
                                <label>职务</label>
                                <select data-rel="select2" name="post"
                                        data-width="272" data-placeholder="请选择职务">
                                    <option></option>
                                    <jsp:include
                                            page="/metaTypes?__code=${type==CM_MEMBER_TYPE_JW?'mc_committee_member_jw'
                                            :'mc_committee_member_dw'}"/>
                                </select>
                                <script>
                                    $("#searchForm select[name=post]").val('${param.post}');
                                </script>
                            </div>
                            <div class="form-group">
                                <label>姓名</label>
                                <select data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                                        data-width="220"
                                        name="userId" data-placeholder="请输入账号或姓名或工作证号">
                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>性别</label>
                                <select name="gender" data-width="100" data-rel="select2"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <option value="<%=SystemConstants.GENDER_MALE%>">男</option>
                                    <option value="<%=SystemConstants.GENDER_FEMALE%>">女</option>
                                </select>
                                <script>
                                    $("#searchForm select[name=gender]").val('${param.gender}');
                                </script>
                            </div>
                            <div class="form-group">
                                <label>民族</label>

                                <div class="input-group">
                                    <select class="multiselect" multiple="" name="nation">
                                        <c:forEach var="nation" items="${cm:getMetaTypes('mc_nation').values()}">
                                            <option value="${nation.name}">${nation.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/cmMember?type=${type}&isQuit=${isQuit?1:0}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cmMember?type=${type}&isQuit=${isQuit?1:0}"
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
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $.register.multiselect($('#searchForm select[name=nation]'), ${cm:toJSONArray(selectNations)});

    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/cmMember_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '届数', name: 'pcsName', align: 'left', width: 220, frozen:true},
            {label: '职务', name: 'post', width: 120, formatter: $.jgrid.formatter.MetaType, frozen:true},
            {label: '任职日期', name: 'postDate', width: 90, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}, frozen:true},
            <c:if test="${isQuit}">
            {label: '离职日期', name: 'quitDate', width: 90, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}, frozen:true},
            </c:if>
            {
                label: '姓名', name: 'realname', width: 80, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.cadreId==undefined) return cellvalue;
                return $.cadre(rowObject.cadreId, cellvalue);
            }, frozen: true
            },
            <shiro:hasPermission name="cmMember:changeOrder">
            <c:if test="${!_query}">
            {
                label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                formatoptions: {url: '${ctx}/cmMember_changeOrder'}, frozen: true
            },
            </c:if>
            </shiro:hasPermission>
            {label: '性别', name: 'gender', width: 50, formatter: $.jgrid.formatter.GENDER},
            {label: '民族', name: 'nation', width: 60},
            {label: '职称', name: 'proPost'},
            {label: '出生年月', name: 'birth', width: 90, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE, formatoptions: {newformat: 'Y.m'}},
            {label: '入党时间', name: 'growTime', width: 90, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '所在单位及职务', name: 'title', align: 'left', width: 350},
            {label: '任命文件', name: 'postFilePath',formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--';
                return $.pdfPreview(cellvalue, '任命文件', '<button class="btn btn-xs btn-primary"><i class="fa fa-search"></i> 查看</button>');
            }},
            <c:if test="${isQuit}">
            {label: '离任文件', name: 'quitFilePath',formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    return $.pdfPreview(cellvalue, '离任文件', '<button class="btn btn-xs btn-primary"><i class="fa fa-search"></i> 查看</button>');
                }},
            {label: '离职原因', name: 'quitReason', align: 'left', width: 200},
            </c:if>
            {label: '备注', name: 'remark', align: 'left', width: 350}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>