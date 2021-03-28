<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/feedback"
                 data-url-export="${ctx}/feedback_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId || not empty param.code || not empty param.sort}"/>
            <shiro:hasPermission name="feedback:del">
                    <button data-url="${ctx}/feedback_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

                    <div class="widget-toolbar">
                        <a href="javascript:;" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm">
                        <div class="form-group">
                            <label>用户</label>
                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                    name="userId" data-placeholder="请输入账号或姓名或学工号">
                                <option value="${user.id}">${user.realname}-${user.code}</option>
                            </select>

                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm">
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

    $.register.user_select($('#searchForm select[name=userId]'));
    $("#jqGrid").jqGrid({
        url: '${ctx}/feedback_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '用户', name: '_user', formatter:function(cellvalue, options, rowObject){
                if(rowObject.user==undefined) return '--'
                return $.user(rowObject.user.id, rowObject.user.realname)
            },frozen:true },
            { label: '学工号',  name: 'user.code', width: 120,frozen:true },
            { label: '标题',name: 'title', width: 450, align:'left', formatter: $.jgrid.formatter.NoMultiSpace},
            { label: '详情和回复', name: '_reply', formatter:function(cellvalue, options, rowObject){
                return ('<button class="openView btn btn-xs btn-primary" ' +
                    'data-url="${ctx}/feedback_detail?id={1}">' +
                    '<i class="fa fa-search"></i> 查看({0})</button>')
                    .format(Math.trimToZero(rowObject.replyCount), rowObject.id)
            }},
            { label: '提交时间',name: 'createTime', width: 180},
            { label: 'IP',name: 'ip', width: 120}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>