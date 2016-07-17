<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/sysConfig_au"
             data-url-page="${ctx}/sysConfig_page"
             data-url-export="${ctx}/sysConfig_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.name || not empty param.code
                || (not empty param.sort&&param.sort!='sort_order')}"/>
            <!-- PAGE CONTENT BEGINS -->
                <div class="jqgrid-vertical-offset  buttons">
                        <shiro:hasPermission name="sysConfig:edit">
                            <a class="editBtn btn btn-info btn-sm" data-width="700">
                                <i class="fa fa-plus"></i> 添加
                            </a>
                            <button class="jqEditBtn btn btn-primary btn-sm" data-width="700">
                                <i class="fa fa-edit"></i> 修改信息
                            </button>

                            <button class="jqOpenViewBtn btn btn-success btn-sm"
                                    data-url="${ctx}/sc_content">
                                <i class="fa fa-search"></i> 查看内容
                            </button>

                        </shiro:hasPermission>
                        <shiro:hasRole name="admin">
                            <button class="jqOpenViewBtn btn btn-warning btn-sm"
                                    data-url="${ctx}/sysConfigRole">
                                <i class="fa fa-pencil"></i> 修改角色
                            </button>
                        </shiro:hasRole>

                        <shiro:hasPermission name="sysConfig:del">
                            <a class="jqBatchBtn btn btn-danger btn-sm"
                               data-url="${ctx}/sysConfig_batchDel" data-title="删除"
                               data-msg="确定删除这{0}项配置吗？"><i class="fa fa-trash"></i> 删除</a>
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
                                            <label>名称</label>
                                                <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                                       placeholder="请输入名称">
                                        </div>
                                    <shiro:hasRole name="admin">
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
        url: '${ctx}/sysConfig_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '名称', name: 'name', width: 250,frozen:true, align:'left' },
            <shiro:hasRole name="admin">
            { label: '代码', name: 'code', width: 250, align:'left' },
            </shiro:hasRole>
            { label: '备注', name: 'remark', width: 250}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
</script>