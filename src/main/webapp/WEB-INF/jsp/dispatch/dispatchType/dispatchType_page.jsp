<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/dispatchType_au"
             data-url-page="${ctx}/dispatchType"
             data-url-export="${ctx}/dispatchType_data"
             data-url-co="${ctx}/dispatchType_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.name ||not empty param.attr||not empty param.year  || (not empty param.sort&&param.sort!='sort_order')}"/>
                <div class="jqgrid-vertical-offset buttons">
                    <shiro:hasPermission name="dispatchType:edit">
                        <a class="editBtn btn btn-info btn-sm">
                            <i class="fa fa-plus"></i> 添加
                        </a>
                        <button class="jqEditBtn btn btn-primary btn-sm">
                            <i class="fa fa-edit"></i> 修改信息
                        </button>
                    </shiro:hasPermission>
                    <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="dispatchType:del">
                        <a class="jqBatchBtn btn btn-danger btn-sm"
                           data-url="${ctx}/dispatchType_batchDel" data-title="删除发文类型"
                           data-msg="确定删除这{0}个发文类型吗？"><i class="fa fa-trash"></i> 删除</a>
                    </shiro:hasPermission>
                </div>
                <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                    <div class="widget-header">
                        <h4 class="widget-title">搜索</h4>
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
                                            <label>年份</label>
                                                <div class="input-group">
                                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                                    <input class="form-control date-picker" placeholder="请选择年份" name="year" type="text"
                                                           data-date-format="yyyy" data-date-min-view-mode="2" value="${param.year}" />
                                                </div>
                                        </div>
                                        <div class="form-group">
                                            <label>名称</label>
                                                <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                                       placeholder="请输入名称">
                                        </div>
                                        <div class="form-group">
                                            <label>发文属性</label>
                                                <input class="form-control search-query" name="attr" type="text" value="${param.attr}"
                                                       placeholder="请输入发文属性">
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
                <table id="jqGrid" class="jqGrid table-striped"> </table>
                <div id="jqGridPager"> </div>
        </div><div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        //forceFit:true,
        url: '${ctx}/dispatchType_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '名称', name: 'name', width: 250,frozen:true },
            { label: '发文属性', name: 'attr', width: 150,frozen:true },
            { label: '所属年份', name: 'year' },
            { label: '添加时间',  name: 'createTime', width: 180 }
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    $.register.date($('.date-picker'));
</script>