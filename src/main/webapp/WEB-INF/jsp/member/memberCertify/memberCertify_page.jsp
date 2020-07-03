<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_POLITICAL_STATUS_MAP" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_MAP%>"/>
<c:set var="MEMBER_POLITICAL_STATUS_GROW" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_GROW%>"/>
<c:set var="MEMBER_POLITICAL_STATUS_POSITIVE" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE%>"/>
<c:set var="JASPER_PRINT_TYPE_MEMBER_CERTIFY" value="<%=SystemConstants.JASPER_PRINT_TYPE_MEMBER_CERTIFY%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.fromUnit ||not empty param.toTitle||not empty param.toUnit ||not empty param.userId ||not empty param.sn ||not empty param.politicalStatus || not empty param.code || not empty param.year}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="memberCertify:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/member/memberCertify_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/member/memberCertify_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="memberCertify:del">
                    <button data-url="${ctx}/member/memberCertify_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/member/memberCertify_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>
                    <span class="widget-note">${note_searchbar}</span>
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
                                <label>姓名</label>
                                <select data-ajax-url="${ctx}/sysUser_selects" data-rel="select2-ajax"
                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>年度</label>
                                <input class="form-control date-picker" placeholder="请选择年份"
                                       name="year" type="text" style="width: 100px;"
                                       data-date-format="yyyy" data-date-min-view-mode="2"
                                       value="${param.year}"/>
                            </div>
                            <div class="form-group">
                                <label>介绍信编号</label>
                                <input class="form-control search-query" name="sn" type="text" value="${param.sn}"
                                       placeholder="请输入">
                            </div>
                            <div class="form-group">
                                <label>政治面貌</label>
                                <select required data-rel="select2" name="politicalStatus"
                                        data-placeholder="请选择" data-width="120">
                                    <option></option>
                                    <c:forEach items="${MEMBER_POLITICAL_STATUS_MAP}" var="entity">
                                        <option value="${entity.key}">${entity.value}</option>
                                    </c:forEach>
                                </select>
                                <script>
                                    $("#searchForm select[name=politicalStatus]").val(${param.politicalStatus});
                                </script>
                            </div>
                            <div class="form-group">
                                <label>转出单位</label>
                                <input class="form-control search-query" name="fromUnit" type="text" value="${param.fromUnit}"
                                       placeholder="请输入">
                            </div>
                            <div class="form-group">
                                <label>转入单位抬头</label>
                                <input class="form-control search-query" name="toTitle" type="text" value="${param.toTitle}"
                                       placeholder="请输入">
                            </div>
                            <div class="form-group">
                                <label>转入单位</label>
                                <input class="form-control search-query" name="toUnit" type="text" value="${param.toUnit}"
                                       placeholder="请输入">
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/member/memberCertify"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/member/memberCertify"
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
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/member/memberCertify_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '年份',name: 'year', width:70},
            { label: '学工号',name: 'user.code', width:120},
            { label: '姓名',name: 'user.realname'},
            { label: '介绍信编号',name: 'sn'},
            { label: '介绍信打印', width: 110, formatter:function(cellvalue, options, rowObject){

                var html = '<button class="openView btn btn-primary btn-xs"'
                    +' data-url="${ctx}/report/printPreview?type=${JASPER_PRINT_TYPE_MEMBER_CERTIFY}&ids[]={0}"><i class="fa fa-print"></i> 打印</button>'
                        .format(rowObject.id);
                return html;
                }},
            { label: '政治面貌',name: 'politicalStatus', formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue==null || cellvalue=='') return '--';
                    if (cellvalue==${MEMBER_POLITICAL_STATUS_POSITIVE}) {
                        return '${MEMBER_POLITICAL_STATUS_MAP.get(MEMBER_POLITICAL_STATUS_POSITIVE)}';
                    }else {
                        return '${MEMBER_POLITICAL_STATUS_MAP.get(MEMBER_POLITICAL_STATUS_GROW)}';
                    }
                }},
            { label: '转出单位',name: 'fromUnit', width:200, align:'left'},
            { label: '转入单位抬头',name: 'toTitle', width:200, align:'left'},
            { label: '转入单位',name: 'toUnit', width:200, align:'left'},
            { label: '介绍信日期',name: 'certifyDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},

            { label: '创建时间',name: 'createTime', width: 150}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>