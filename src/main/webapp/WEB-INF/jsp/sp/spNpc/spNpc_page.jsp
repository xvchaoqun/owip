<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${!isHistory}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/sp/spNpc?isHistory=0">
                            <i class="fa fa-circle-o-notch fa-spin"></i>
                        现任人大代表和政协委员</a>
                    </li>
                    <li class="<c:if test="${isHistory}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/sp/spNpc?isHistory=1">
                            <i class="fa fa-history"></i>
                        离任人大代表和政协委员</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <c:set var="_query" value="${not empty param.type ||not empty param.th ||not empty param.userId}"/>
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="sp:edit">
                                <c:if test="${!isHistory}">
                                    <button class="popupBtn btn btn-info btn-sm"
                                            data-url="${ctx}/sp/spNpc_au"><i class="fa fa-plus"></i>
                                        添加</button>
                                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                            data-url="${ctx}/sp/spNpc_au"
                                            data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                        修改</button>
                                    <button data-url="${ctx}/sp/spNpc_history"
                                            data-title="离任"
                                            data-grid-id="#jqGrid"
                                            class="jqOpenViewBtn btn btn-warning btn-sm"><i class="fa fa-sign-out"></i>
                                        离任</button>
                                    <button class="popupBtn btn btn-info btn-sm tooltip-info"
                                            data-url="${ctx}/sp/spNpc_import"
                                            data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                                        批量导入</button>
                                </c:if>
                                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                        data-url="${ctx}/sp/spNpc_data?isHistory=${isHistory}"
                                        data-rel="tooltip" data-placement="top"
                                        title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i>
                                    导出</button>
                                <button data-url="${ctx}/sp/spNpc_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm"><i class="fa fa-trash"></i>
                                    删除</button>
                            </shiro:hasPermission>
                        </div>
                    </div>
                </div>
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
                                <label>类别</label>
                                <select class="col-xs-6" required name="type"
                                        data-rel="select2" data-placeholder="请选择">
                                    <option></option>
                                    <c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_sp_npc_type').id}"/>
                                </select>
                                <script type="text/javascript">
                                    $("#searchForm select[name=type]").val('${param.type}')
                                </script>
                            </div>
                            <div class="form-group">
                                <label>届次</label>
                                <input class="form-control search-query" name="th" type="text"
                                       value="${param.th}" placeholder="请输入届次">
                            </div>
                            <div class="form-group">
                                <label>姓名</label>
                                <select data-rel="select2-ajax" name="userId"
                                        data-ajax-url="${ctx}/sysUser_selects"
                                        data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                </select>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                        data-url="${ctx}/sp/spNpc"
                                        data-target="#page-content"
                                        data-form="#searchForm"><i class="fa fa-search"></i>
                                    查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/sp/spNpc"
                                            data-target="#page-content"><i class="fa fa-reply"></i>
                                        重置</button>
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
        url: '${ctx}/sp/spNpc_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '类别',name: 'type', width: 200, formatter: $.jgrid.formatter.MetaType, frozen:true},
                { label: '届次',name: 'th', frozen:true},
                { label: '姓名',name: 'user.realname',formatter: function (cellvalue, options, rowObject) {
                        if (rowObject.isCadre) {
                            return $.cadre(rowObject.cadre.id, cellvalue);
                        }else {
                            return cellvalue;
                        }
                    },frozen:true,
                },
                <shiro:hasPermission name="sp:edit">
                    { label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                    formatoptions:{url: "${ctx}/sp/spNpc_changeOrder"}, frozen: true},
                </shiro:hasPermission>
                { label: '性别',name: 'user.gender',formatter: $.jgrid.formatter.GENDER,width: 80},
                { label: '出生时间',name: 'user.birth',formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
                { label: '年龄', name: 'user.birth', width: 80, formatter: $.jgrid.formatter.AGE},
                { label: '政治面貌',name: 'politicsStatus',width: 200, formatter: $.jgrid.formatter.MetaType},
                <c:if test="${!isHistory}">
                    { label: '人大/政协职务',name: 'npcPost',width: 200},
                </c:if>
                { label: '所在单位',name: 'unitId',width: 200,align: 'left', formatter: $.jgrid.formatter.unit},
                { label: '当选时职务',name: 'electedPost',width: 300, align: 'left'},
                { label: '${isHistory?'离任时职务':'现任职务'}',name: 'post',width: 300,align: 'left'},
                <c:if test="${!isHistory}">
                    { label: '是否现任领导干部',name: 'isCadre',width: 130,formatter: $.jgrid.formatter.TRUEFALSE},
                </c:if>
                { label: '联系方式',name: 'phone',width: 120},
                { label: '备注',name: 'remark',width: 300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
</script>