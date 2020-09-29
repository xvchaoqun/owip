<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/metaClass_au"
             data-url-page="${ctx}/metaClass"
             data-url-export="${ctx}/metaClass_data"
             data-url-co="${ctx}/metaClass_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <jsp:include page="menu.jsp"/>
            <div class="space-4"></div>
            <c:set var="_query" value="${not empty param.name || not empty param.code
                || (not empty param.sort&&param.sort!='sort_order')}"/>

                <div class="jqgrid-vertical-offset  buttons">
                    <c:if test="${cls==1}">
                        <shiro:hasPermission name="metaClass:edit">
                            <a class="editBtn btn btn-info btn-sm">
                                <i class="fa fa-plus"></i> 添加
                            </a>
                            <button class="jqEditBtn btn btn-primary btn-sm">
                                <i class="fa fa-edit"></i> 修改信息
                            </button>
                            <button class="jqOpenViewBtn btn btn-success btn-sm"
                                    data-url="${ctx}/metaClass_type"
                                    data-width="<shiro:hasPermission name="metaClass:viewAll">800</shiro:hasPermission><shiro:lacksPermission name="metaClass:viewAll">600</shiro:lacksPermission>">
                                <i class="fa fa-bars"></i> 编辑属性
                            </button>
                        </shiro:hasPermission>

                        <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                           data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    </c:if>
                        <shiro:hasPermission name="metaClass:del">
                            <c:if test="${cls==1}">
                                <a class="jqBatchBtn btn btn-danger btn-sm"
                                   data-url="${ctx}/metaClass_batchDel" data-title="删除"
                                   data-msg="确定删除这{0}个元数据分类吗？"
                                   data-querystr="&isDeleted=1"><i class="fa fa-trash"></i> 删除</a>
                            </c:if>
                            <c:if test="${cls==3}">
                            <a class="jqBatchBtn btn btn-warning btn-sm"
                               data-url="${ctx}/metaClass_batchDel" data-title="恢复"
                               data-msg="确定恢复这{0}个元数据分类吗？"
                               data-querystr="&isDeleted=0"><i class="fa fa-reply"></i> 恢复</a>
                            </c:if>
                        </shiro:hasPermission>
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
                                <input type="hidden" name="metaClassId" value="${metaClassId}">
                                        <div class="form-group">
                                            <label>名称</label>
                                                <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                                       placeholder="请输入名称">
                                        </div>
                                    <shiro:hasPermission name="metaClass:viewAll">
                                        <div class="form-group">
                                            <label>代码</label>
                                                <input class="form-control search-query" name="code" type="text" value="${param.code}"
                                                       placeholder="请输入代码">
                                        </div>
                                    </shiro:hasPermission>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"
                                       data-url="${ctx}/metaClass?cls=${cls}&isDeleted=${isDeleted}"
                                       data-target="#page-content"
                                       data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                data-querystr="cls=${cls}&isDeleted=${isDeleted}">
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
        url: '${ctx}/metaClass_data?callback=?&isDeleted=${isDeleted}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '名称', name: 'name', width: 250,frozen:true,align:'left' },
            { label: '所属一级目录', name: 'firstLevel', width: 200,frozen:true },
            { label: '所属二级目录', name: 'secondLevel', width: 200,frozen:true },
            <shiro:hasPermission name="metaClass:viewAll">
            <c:if test="${!_query}">
            { label:'排序', formatter: $.jgrid.formatter.sortOrder,frozen:true },
            </c:if>
            </shiro:hasPermission>
            { label: '布尔属性名称',  name: 'boolAttr', width: 150 },
            <shiro:hasPermission name="metaClass:viewAll">
            { label: '代码', name: 'code', width: 300, align:'left' },

            { label: '附加属性名称',  name: 'extraAttr', width: 150 }
            </shiro:hasPermission>
        ]
    }).jqGrid("setFrozenColumns");
  /*  $("#jqGrid").jqGrid('setSelection',${metaClassId});*/
    $(window).triggerHandler('resize.jqGrid');
    $.register.date($('.date-picker'));
    function openView(){
        $.loadModal( "${ctx}/metaClass_type?id="+${metaClassId} + "&pageNo=1",
            '<shiro:hasPermission name="metaClass:viewAll">800</shiro:hasPermission><shiro:lacksPermission name="metaClass:viewAll">600</shiro:lacksPermission>');
    }

    if(${metaClassId!=null}){
       openView();
    }
</script>