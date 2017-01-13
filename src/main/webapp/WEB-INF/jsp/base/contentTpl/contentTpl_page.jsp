<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/contentTpl_page"
             data-url-export="${ctx}/contentTpl_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.name || not empty param.code || not empty param.content
                || (not empty param.sort&&param.sort!='sort_order')}"/>
            <!-- PAGE CONTENT BEGINS -->
            <div class="jqgrid-vertical-offset  buttons">
                <shiro:hasPermission name="contentTpl:edit">
                    <a class="popupBtn btn btn-success btn-sm"
                       data-url="${ctx}/contentTpl_au?contentType=${CONTENT_TPL_CONTENT_TYPE_STRING}">
                        <i class="fa fa-plus"></i> 添加普通文本
                    </a>
                    <a class="popupBtn btn btn-info btn-sm"
                       data-width="900"
                       data-url="${ctx}/contentTpl_au?contentType=${CONTENT_TPL_CONTENT_TYPE_HTML}">
                        <i class="fa fa-plus"></i> 添加HTML文本
                    </a>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/contentTpl_au">
                        <i class="fa fa-edit"></i> 修改
                    </button>
                    <button data-url="${ctx}/contentTpl_receivers" class="jqOpenViewBtn btn btn-warning btn-sm">
                        <i class="fa fa-user"></i> 设置短信接收人
                    </button>
                </shiro:hasPermission>
                <shiro:hasRole name="${ROLE_ADMIN}">
                    <button class="jqOpenViewBtn btn btn-warning btn-sm"
                            data-url="${ctx}/contentTplRole">
                        <i class="fa fa-pencil"></i> 修改角色
                    </button>
                </shiro:hasRole>
                <shiro:hasPermission name="contentTpl:del">
                    <a class="jqBatchBtn btn btn-danger btn-sm"
                       data-url="${ctx}/contentTpl_batchDel" data-title="删除"
                       data-msg="确定删除这{0}个模板吗？"><i class="fa fa-trash"></i> 删除</a>
                </shiro:hasPermission>

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
                                <label>模板名称</label>
                                <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                       placeholder="请输入名称">
                            </div>
                            <div class="form-group">
                                <label>内容</label>
                                <input class="form-control search-query" name="content" type="text" value="${param.content}"
                                       placeholder="请输入内容">
                            </div>
                            <shiro:hasRole name="${ROLE_ADMIN}">
                                <div class="form-group">
                                    <label>代码</label>
                                    <input class="form-control search-query" name="code" type="text" value="${param.code}"
                                           placeholder="请输入代码">
                                </div>
                            </shiro:hasRole>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"> </table>
            <div id="jqGridPager"> </div>
        </div><div id="item-content"></div>
    </div>
</div>

<script>
    $("#jqGrid").jqGrid({
        //forceFit:true,
        url: '${ctx}/contentTpl_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '模板名称', name: 'name', align:'left', width: 220,frozen:true },
            <shiro:hasRole name="${ROLE_ADMIN}">
            { label: '代码', name: 'code', align:'left', width: 250 },
            </shiro:hasRole>
            { label: '类型', name: 'type',  formatter: function (cellvalue, options, rowObject) {
                return _cMap.CONTENT_TPL_TYPE_MAP[cellvalue];
            }},
            { label: '内容', name: 'content', width: 450},
            { label: '模板类型', name: 'contentType', formatter: function (cellvalue, options, rowObject) {
                return _cMap.CONTENT_TPL_CONTENT_TYPE_MAP[cellvalue];
            }},
            { label: '模板引擎', name: 'engine', width: 150,formatter: function (cellvalue, options, rowObject) {
                return _cMap.CONTENT_TPL_ENGINE_MAP[cellvalue];
            }},
            { label: '备注', name: 'remark', width: 250},
            { label: '添加时间', name: 'createTime', width: 150}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
</script>