<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/htmlFragment_au"
             data-url-page="${ctx}/htmlFragment?isDeleted=${isDeleted}"
             data-url-export="${ctx}/htmlFragment_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.title || not empty param.code|| not empty param.content
                || (not empty param.sort&&param.sort!='sort_order')}"/>

            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <li class="<c:if test="${!isDeleted}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/htmlFragment?isDeleted=0"><i
                            class="fa fa-circle-o-notch"></i> 系统说明</a>
                </li>
                <li class="<c:if test="${isDeleted}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/htmlFragment?isDeleted=1"><i class="fa fa-times"></i> 已删除</a>
                </li>
            </ul>
            <div class="space-4"></div>
                <div class="jqgrid-vertical-offset  buttons">
                    <shiro:hasPermission name="htmlFragment:edit">
                        <c:if test="${!isDeleted}">
                            <a class="openView btn btn-info btn-sm" data-url="${ctx}/htmlFragment_au">
                                <i class="fa fa-plus"></i> 添加
                            </a>
                            <button class="jqEditBtn btn btn-primary btn-sm" data-open-by="page">
                                <i class="fa fa-edit"></i> 修改信息
                            </button>
                        </c:if>
                            <button class="jqOpenViewBtn btn btn-success btn-sm"
                                    data-url="${ctx}/hf_content">
                                <i class="fa fa-search"></i> 查看内容
                            </button>

                        </shiro:hasPermission>
                    <c:if test="${!isDeleted}">
                        <shiro:hasRole name="${ROLE_ADMIN}">
                            <button class="jqOpenViewBtn btn btn-warning btn-sm"
                                    data-url="${ctx}/htmlFragmentRole">
                                <i class="fa fa-pencil"></i> 修改角色
                            </button>
                        </shiro:hasRole>

                        <shiro:hasPermission name="htmlFragment:del">
                            <a class="jqBatchBtn btn btn-danger btn-sm"
                               data-url="${ctx}/htmlFragment_batchDel" data-title="删除"
                               data-msg="确定删除这{0}项配置吗？"><i class="fa fa-trash"></i> 删除</a>
                        </shiro:hasPermission>
                    </c:if>
                    <c:if test="${isDeleted}">
                    <shiro:hasPermission name="htmlFragment:del">
                        <a class="jqBatchBtn btn btn-danger btn-sm"
                           data-url="${ctx}/htmlFragment_doBatchDel" data-title="彻底删除"
                           data-msg="确定彻底删除这{0}项配置吗？删除后数据将无法恢复。"><i class="fa fa-trash"></i> 彻底删除</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="htmlFragment:edit">
                        <a class="jqBatchBtn btn btn-warning btn-sm"
                           data-url="${ctx}/htmlFragment_batchUnDel" data-title="返回列表"
                           data-msg="确定返回这{0}项配置吗？"><i class="fa fa-reply"></i> 返回列表</a>
                    </shiro:hasPermission>
                    </c:if>
                </div>
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
                                                <input class="form-control search-query" name="title" type="text" value="${param.title}"
                                                       placeholder="请输入名称">
                                        </div>
                                    <shiro:hasRole name="${ROLE_ADMIN}">
                                        <div class="form-group">
                                            <label>代码</label>
                                                <input class="form-control search-query" name="code" type="text" value="${param.code}"
                                                       placeholder="请输入代码">
                                        </div>
                                        <div class="form-group">
                                            <label>内容</label>
                                            <input class="form-control search-query" name="content" type="text" value="${param.content}"
                                                   placeholder="请输入内容">
                                        </div>
                                    </shiro:hasRole>
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
        url: '${ctx}/htmlFragment_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: 'ID', name: 'id', frozen:true, width:50 },
            { label: '名称', name: 'title', width: 450,frozen:true, align:'left' },
            <c:if test="${!_query}">
            { label:'排序', formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{url:'${ctx}/htmlFragment_changeOrder'},frozen:true },
            </c:if>
            { label: '上级说明', name: 'fid', width: 250,  align:'left', formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '--';
                return "[{0}]".format(rowObject.parent.id) + rowObject.parent.title ;
            }  },
            <shiro:hasRole name="${ROLE_ADMIN}">
            { label: '代码', name: 'code', width: 250, align:'left' },
            </shiro:hasRole>
            { label: '备注', name: 'remark', width: 250}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
</script>