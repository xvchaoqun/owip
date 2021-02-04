<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="UNIT_POST_STATUS_NORMAL" value="<%=SystemConstants.UNIT_POST_STATUS_NORMAL%>"/>
<c:set var="UNIT_POST_STATUS_ABOLISH" value="<%=SystemConstants.UNIT_POST_STATUS_ABOLISH%>"/>
<c:set var="_query"
       value="${not empty param.name ||not empty param.adminLevel ||not empty param.postType
       ||not empty param.postClass || not empty param.code || not empty param.sort}"/>
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${cls==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-load-el="#unit-content"
           data-url="${ctx}/unitPostList?cls=1&unitId=${param.unitId}"><i
                class="fa fa-circle-o-notch"></i> 现有岗位</a>
    </li>
    <li class="<c:if test="${cls==2}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-load-el="#unit-content"
           data-url="${ctx}/unitPostList?cls=2&unitId=${param.unitId}"><i class="fa fa-history"></i> 撤销岗位</a>
    </li>
</ul>
<div class="space-4"></div>
<div class="jqgrid-vertical-offset buttons">
    <c:if test="${cls==1||cls==2}">
    <shiro:hasPermission name="unitPost:edit">
        <button class="popupBtn btn btn-info btn-sm"
                data-width="900"
                ${cls==1 && cm:getUnitById(param.unitId).status==UNIT_STATUS_HISTORY ? "disabled":""}
                data-url="${ctx}/unitPost_au?unitId=${param.unitId}&status=${cls==1?UNIT_POST_STATUS_NORMAL:UNIT_POST_STATUS_ABOLISH}&jqGrid=jqGrid2">
            <i class="fa fa-plus"></i> 添加
        </button>
    </shiro:hasPermission>
    </c:if>
    <shiro:hasPermission name="unitPost:edit">
        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                data-width="900"
                data-url="${ctx}/unitPost_au?unitId=${param.unitId}&jqGrid=jqGrid2"
                data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
            修改
        </button>
    </shiro:hasPermission>
    <c:if test="${cls==1}">
    <shiro:hasPermission name="unitPost:edit">
        <button class="jqOpenViewBatchBtn btn btn-warning btn-sm"
                data-url="${ctx}/unitPost_abolish" data-width="400"
                data-grid-id="#jqGrid2"><i class="fa fa-history"></i>
            撤销
        </button>
        <div class="btn-group">
            <button data-toggle="dropdown"
                    data-rel="tooltip" data-placement="top" data-html="true"
                    title="<div style='width:180px'>按指定条件进行批量排序</div>"
                    class="btn btn-success btn-sm dropdown-toggle tooltip-success">
                <i class="fa fa-sort"></i> 批量排序 <span class="caret"></span>
            </button>
            <ul class="dropdown-menu dropdown-success" role="menu">
                <li>
                    <a href="javascript:;" class="runBtn"
                            data-url="${ctx}/unitPost_sortByCode?unitId=${param.unitId}&asc=1"
                            data-callback="_callback_sortByCode">
                        <i class="fa fa-sort-alpha-asc"></i> 按岗位编号排序（升序）
                    </a>
                </li>
                <li role="separator" class="divider"></li>
                <li>
                    <a href="javascript:;" class="runBtn"
                            data-url="${ctx}/unitPost_sortByCode?unitId=${param.unitId}&asc=0"
                            data-callback="_callback_sortByCode">
                        <i class="fa fa-sort-alpha-desc"></i> 按岗位编号排序（降序）
                    </a>
                </li>
            </ul>
        </div>
    </shiro:hasPermission>
    </c:if>
    <c:if test="${cls==2}">
    <shiro:hasPermission name="unitPost:edit">
        <button class="jqBatchBtn btn btn-warning btn-sm"
                data-url="${ctx}/unitPost_unabolish"
                data-title="返回现有岗位"
                data-msg="确定返回现有岗位？（已选{0}个岗位）"
                data-grid-id="#jqGrid2"
                data-callback="_reload"><i class="fa fa-reply"></i>
            返回现有岗位
        </button>
    </shiro:hasPermission>
    </c:if>
    <shiro:hasPermission name="unitPost:del">
        <button data-url="${ctx}/unitPost_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}个岗位？<br/>（删除后不可恢复，请谨慎操作！）"
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
        <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

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
                        <option value="${unit.id}" delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
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
                    <select  data-rel="select2" name="adminLevel" data-width="272" data-placeholder="请选择">
						<option></option>
						<jsp:include page="/metaTypes?__code=mc_admin_level"/>
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
                    <select data-rel="select2" name="postClass" data-width="100" data-placeholder="请选择">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_post_class"/>
                    </select>
                    <script type="text/javascript">
                        $("#searchForm2 select[name=postClass]").val(${param.postClass});
                    </script>
                </div>
                <div class="clearfix form-actions center">
                    <a class="jqSearchBtn btn btn-default btn-sm"
                       data-url="${ctx}/unitPostList?cls=${cls}&unitId=${param.unitId}"
                       data-target="#unit-content"
                       data-form="#searchForm2"><i class="fa fa-search"></i> 查找</a>
                    <c:if test="${_query}">&nbsp;
                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                data-url="${ctx}/unitPostList"
                                data-querystr="cls=${cls}&unitId=${param.unitId}"
                                data-target="#unit-content">
                            <i class="fa fa-reply"></i> 重置
                        </button>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="multi-row-head-table">
<div class="space-4"></div>
<table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="30" data-width-reduce="20"></table>
<div id="jqGridPager2"></div>
</div>
<script type="text/template" id="dispatch_select_tpl">
    <button class="popupBtn btn {{=(count>0)?'btn-warning':'btn-success'}} btn-xs"
            data-url="${ctx}/cadrePost_addDispatchs?id={{=id}}&cadreId={{=cadreId}}"
            data-width="1000"><i class="fa fa-link"></i>
        任免文件({{=count}})
    </button>
</script>
<jsp:include page="colModel.jsp?list=1"/>
<script>
    function _callback_sortByCode(){
        $("#jqGrid2").trigger("reloadGrid");
    }
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