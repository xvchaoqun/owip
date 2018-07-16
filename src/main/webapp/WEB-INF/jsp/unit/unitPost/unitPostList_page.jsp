<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="_query"
       value="${not empty param.name ||not empty param.adminLevel ||not empty param.postType ||not empty param.postClass || not empty param.code || not empty param.sort}"/>
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${cls==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-load-el="#tab-content"
           data-url="${ctx}/unitPostList?cls=1&unitId=${param.unitId}"><i
                class="fa fa-circle-o-notch"></i> 现有处级岗位</a>
    </li>
    <li class="<c:if test="${cls==2}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-load-el="#tab-content"
           data-url="${ctx}/unitPostList?cls=2&unitId=${param.unitId}"><i class="fa fa-history"></i> 撤销处级岗位</a>
    </li>
</ul>
<div class="space-4"></div>
<div class="jqgrid-vertical-offset buttons">
    <c:if test="${cls==1}">
    <shiro:hasPermission name="unitPost:edit">
        <button class="popupBtn btn btn-info btn-sm"
                data-url="${ctx}/unitPost_au?unitId=${param.unitId}">
            <i class="fa fa-plus"></i> 添加
        </button>
        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                data-url="${ctx}/unitPost_au?unitId=${param.unitId}"
                data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
            修改
        </button>
        <button class="jqOpenViewBtn btn btn-warning btn-sm"
                data-url="${ctx}/unitPost_abolish" data-width="400"
                data-grid-id="#jqGrid2"><i class="fa fa-history"></i>
            撤销
        </button>
    </shiro:hasPermission>
    </c:if>
    <c:if test="${cls==2}">
    <shiro:hasPermission name="unitPost:edit">
        <button class="jqItemBtn btn btn-warning btn-sm"
                data-url="${ctx}/unitPost_unabolish"
                data-title="返回现有岗位"
                data-msg="确定返回现有岗位？"
                data-grid-id="#jqGrid2"
                data-callback="_reload"><i class="fa fa-reply"></i>
            返回现有岗位
        </button>
    </shiro:hasPermission>
    </c:if>
    <shiro:hasPermission name="unitPost:del">
        <button data-url="${ctx}/unitPost_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}条数据？"
                data-grid-id="#jqGrid2"
                class="jqBatchBtn btn btn-danger btn-sm">
            <i class="fa fa-trash"></i> 删除
        </button>
    </shiro:hasPermission>
   <%-- <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
            data-url="${ctx}/unitPost_data"
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
            <form class="form-inline search-form" id="searchForm2">
                <div class="form-group">
                    <label>所属单位</label>
                    <select name="unitId" data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                            data-placeholder="请选择所属内设机构">
                        <option value="${unit.id}" title="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                    </select>
                    <script>
                        $.register.del_select($("#searchForm2 select[name=unitId]"), 250)
                    </script>
                </div>
                <div class="form-group">
                    <label>岗位名称</label>
                    <input class="form-control search-query" name="name" type="text" value="${param.name}"
                           placeholder="请输入岗位名称">
                </div>
                <div class="form-group">
                    <label>行政级别</label>
                    <select required class="form-control" data-rel="select2" name="adminLevel"
                            data-placeholder="请选择行政级别">
                        <option></option>
                        <option value="${cm:getMetaTypeByCode('mt_admin_level_main').id}">正处级</option>
                        <option value="${cm:getMetaTypeByCode('mt_admin_level_vice').id}">副处级</option>
                        <option value="${cm:getMetaTypeByCode('mt_admin_level_none').id}">无行政级别</option>
                    </select>
                    <script type="text/javascript">
                        $("#searchForm2 select[name=adminLevel]").val('${param.adminLevel}');
                    </script>
                </div>
                <div class="form-group">
                    <label>职务属性</label>
                    <select name="postType" data-rel="select2" data-placeholder="请选择职务属性">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_post"/>
                    </select>
                    <script>
                        $("#searchForm2 select[name=postType]").val('${param.postType}');
                    </script>
                </div>
                <div class="form-group">
                    <label>职务类别</label>
                    <select required data-rel="select2" name="postClass" data-placeholder="请选择">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_post_class"/>
                    </select>
                    <script type="text/javascript">
                        $("#searchForm2 select[name=postClass]").val(${param.postClass});
                    </script>
                </div>
                <div class="clearfix form-actions center">
                    <a class="jqSearchBtn btn btn-default btn-sm"
                       data-url="${ctx}/unitPostList?cls=${cls}"
                       data-target="#tab-content"
                       data-form="#searchForm2"><i class="fa fa-search"></i> 查找</a>
                    <c:if test="${_query}">&nbsp;
                        <button type="button" class="resetBtn btn btn-warning btn-sm"
                                data-url="${ctx}/unitPostList"
                                data-querystr="cls=${cls}&unitId=${param.unitId}"
                                data-target="#tab-content">
                            <i class="fa fa-reply"></i> 重置
                        </button>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="space-4"></div>
<table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="50"></table>
<div id="jqGridPager2"></div>

<jsp:include page="colModel.jsp?list=1"/>
<script>
    function _reload(){
        $("#modal").modal('hide');
        $("#jqGrid2").trigger("reloadGrid");
    }
    $("#jqGrid2").jqGrid({
        rownumbers: true,
        pager: "jqGridPager2",
        url: '${ctx}/unitPost_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm2 [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>