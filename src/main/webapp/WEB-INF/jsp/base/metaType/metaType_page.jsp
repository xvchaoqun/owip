<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/metaType"
             data-url-co="${ctx}/metaType_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <jsp:include page="../metaClass/menu.jsp"/>
            <div class="space-4"></div>
            <c:set var="_query" value="${not empty param.name || not empty param.code
                ||not empty param.className || not empty param.classCode
                || (not empty param.sort&&param.sort!='sort_order')}"/>
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
                                <label>名称</label>
                                <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                       placeholder="请输入名称">
                            </div>
                            <shiro:hasPermission name="metaClass:viewAll">
                                <div class="form-group">
                                    <label>代码</label>
                                    <input class="form-control search-query" name="code" type="text"
                                           value="${param.code}"
                                           placeholder="请输入代码">
                                </div>
                            </shiro:hasPermission>
                            <div class="form-group">
                                <label>所属分类名称</label>
                                <input class="form-control search-query" name="className" type="text"
                                       value="${param.className}"
                                       placeholder="请输入分类名称">
                            </div>
                            <shiro:hasPermission name="metaClass:viewAll">
                                <div class="form-group">
                                    <label>所属分类代码</label>
                                    <input class="form-control search-query" name="classCode" type="text"
                                           value="${param.classCode}"
                                           placeholder="请输入分类代码">
                                </div>
                            </shiro:hasPermission>

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
    $("#jqGrid").jqGrid({
        //forceFit:true,
        url: '${ctx}/metaType_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: 'ID', name: 'id', width: 50, frozen: true},
            {label: '名称', name: 'name', width: 250, frozen: true, align: 'left'},
            {label: '布尔属性', name: 'boolAttr', width: 120},
            <shiro:hasPermission name="metaClass:viewAll">
            {label: '属性代码', name: 'code', width: 180, align: 'left'},
            {label: '附加属性名称', name: 'extraAttr', width: 150},
            </shiro:hasPermission>
            {label: '所属分类', name: 'className', width: 250, frozen: true, align: 'left'},
            {label: '所属分类代码', name: 'classCode', width: 180, align: 'left'},
            {label: '所属一级目录', name: 'firstLevel', width: 200, frozen: true},
            {label: '所属二级目录', name: 'secondLevel', width: 200, frozen: true}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
</script>