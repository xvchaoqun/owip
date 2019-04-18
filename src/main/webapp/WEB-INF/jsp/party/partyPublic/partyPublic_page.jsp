<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="widget-box transparent">
                <div class="widget-header">
                    <jsp:include page="/WEB-INF/jsp/member/memberApply/menu.jsp"/>
                </div>
                <div class="widget-body">
                    <div class="widget-main padding-12 no-padding-left no-padding-right" style="padding-top: 5px;">
                        <div class="tab-content padding-4">
                            <div class="tab-pane in active">
                                <c:set var="_query"
                                       value="${not empty param.type ||not empty param.partyId ||not empty param.pubDate
                                       || not empty param.isPublish || not empty param.userId || not empty param.pubUserId}"/>
                                <div class="jqgrid-vertical-offset buttons">
                                    <shiro:hasPermission name="partyPublic:edit">
                                        <button class="openView btn btn-success btn-sm"
                                                data-url="${ctx}/partyPublic_au">
                                            <i class="fa fa-plus"></i> 生成公示文件
                                        </button>
                                        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                                data-open-by="page"
                                                data-url="${ctx}/partyPublic_au"
                                                data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                            修改
                                        </button>
                                        <button id="pubBtn" class="jqBatchBtn btn btn-success btn-sm"
                                                data-url="${ctx}/partyPublic_pub"
                                                data-title="发布"
                                                data-msg="确定发布？（已选{0}条数据）"
                                                data-callback="_reload"
                                                data-grid-id="#jqGrid"
                                                data-querystr="publish=1"><i class="fa fa-check-circle-o"></i>
                                            发布
                                        </button>
                                        <button id="unPubBtn" class="jqBatchBtn btn btn-warning btn-sm"
                                                data-url="${ctx}/partyPublic_pub"
                                                data-title="取消发布"
                                                data-msg="确定取消发布？（已选{0}条数据）"
                                                data-callback="_reload"
                                                data-grid-id="#jqGrid"
                                                data-querystr="publish=0"><i class="fa fa-times-circle-o"></i>
                                            取消发布
                                        </button>
                                    </shiro:hasPermission>
                                    <button class="popupBtn btn btn-info btn-sm"
                                                data-url="${ctx}/partyPublics_preview">
                                            <i class="fa fa-search"></i> 预览已发布公示列表
                                    </button>
                                    <shiro:hasPermission name="partyPublic:del">
                                        <button data-url="${ctx}/partyPublic_batchDel"
                                                data-title="删除"
                                                data-msg="确定删除这{0}条数据？（不可恢复，请谨慎操作）"
                                                data-grid-id="#jqGrid"
                                                class="jqBatchBtn btn btn-danger btn-sm">
                                            <i class="fa fa-times"></i> 删除
                                        </button>
                                    </shiro:hasPermission>
                                    <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                            data-url="${ctx}/partyPublic_data"
                                            data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                        <i class="fa fa-download"></i> 导出
                                    </button>--%>
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
                                                    <label>类型</label>
                                                     <select class="form-control" data-rel="select2" data-width="180"
                                                                name="type" data-placeholder="请选择">
                                                            <option></option>
                                                             <c:forEach items="<%=OwConstants.OW_PARTY_PUBLIC_TYPE_MAP%>" var="_type">
                                                                <option value="${_type.key}">${_type.value}</option>
                                                            </c:forEach>
                                                     </select>
                                                    <script>
                                                        $("#searchForm select[name=type]").val('${param.type}')
                                                    </script>
                                                </div>
                                                <div class="form-group">
                                                    <label>是否发布</label>
                                                    <select name="isPublish" data-rel="select2"
                                                            data-width="100"
                                                            data-placeholder="请选择">
                                                        <option></option>
                                                        <option value="1">是</option>
                                                        <option value="0">否</option>
                                                    </select>
                                                    <script>
                                                        $("#searchForm select[name=isPublish]").val("${param.isPublish}");
                                                    </script>
                                                </div>
                                                <div class="form-group">
                                                    <label>公示对象</label>
                                                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                            name="pubUserId" data-placeholder="请输入账号或姓名或学工号">
                                                        <option value="${pubUser.id}">${pubUser.realname}-${pubUser.code}</option>
                                                    </select>
                                                </div>
                                                <div class="form-group">
                                                    <label>所属党委</label>
                                                    <select class="form-control" data-rel="select2-ajax"
                                                                data-ajax-url="${ctx}/party_selects?auth=1" data-width="290"
                                                                name="partyId" data-placeholder="请选择">
                                                            <option value="${party.id}" title="${party.isDeleted}">${party.name}</option>
                                                     </select>
                                                </div>
                                                <div class="form-group">
                                                    <label>公示日期</label>
                                                    <div class="input-group tooltip-success" data-rel="tooltip" title="公示日期范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                        <input placeholder="请选择公示日期范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                                               type="text" name="pubDate" value="${param.pubDate}"/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label>创建人</label>
                                                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                    </select>
                                                </div>
                                                <div class="clearfix form-actions center">
                                                    <a class="jqSearchBtn btn btn-default btn-sm"
                                                       data-url="${ctx}/partyPublic?cls=${cls}"
                                                       data-target="#page-content"
                                                       data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                                    <c:if test="${_query}">&nbsp;
                                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                                data-url="${ctx}/partyPublic?cls=${cls}"
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
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    function _reload() {
        $("#jqGrid").trigger("reloadGrid");
    }
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/partyPublic_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label:'类别', name: 'type', width: 130, formatter:function(cellvalue, options, rowObject){
                //console.log(_cMap.OW_PARTY_PUBLIC_TYPE_MAP)
                return _cMap.OW_PARTY_PUBLIC_TYPE_MAP[cellvalue];
            },frozen:true},
            {label: '公示日期', name: 'pubDate',formatter: 'date', formatoptions: {newformat: 'Y.m.d'},frozen:true},
            {label: '公示人数', name: 'num', width: 80},
            {label: '是否发布', name: 'isPublish', formatter:$.jgrid.formatter.TRUEFALSE},
            { label: '预览',name: '_preview', width: 80, formatter: function (cellvalue, options, rowObject) {
                return '<button class="linkBtn btn {1} btn-xs" data-url="${ctx}/public/partyPublic?id={0}" data-target="_blank"><i class="fa fa-external-link"></i> 预览</button>'
                        .format($.base64.encode(rowObject.id), rowObject.isPublish?'btn-success':'btn-warning');
            }},
            {label: '所属党委', name: 'partyName', width: 320, align:'left'},
            {label: '邮箱', name: 'email', width: 160},
            {label: '联系电话', name: 'phone', width: 110},
            {label: '信箱', name: 'mailbox'},
            {label: '创建人', name: 'user.realname'},
            {label: '创建人工号', name: 'user.code', width: 120},
            {label: '创建时间', name: 'createTime', width: 160},
            {label: 'IP', name: 'ip', width: 120},
            {label: '最后更新时间', name: 'updateTime', width: 160},
            {label: '备注', name: 'remark', width: 260},
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.del_select($('#searchForm select[name=partyId]'));
    $.register.user_select($('#searchForm select[name=pubUserId]'));
    $.register.user_select($('#searchForm select[name=userId]'));
    $('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $.register.date($('.input-group.date'));
</script>