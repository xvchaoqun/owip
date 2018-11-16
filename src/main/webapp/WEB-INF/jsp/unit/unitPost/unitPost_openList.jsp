<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:set var="_query" value="${not empty param.unitId ||not empty param.name
            ||not empty param.isPrincipalPost ||not empty param.isCpc  ||not empty param.cadreId
            ||not empty param.adminLevel ||not empty param.postType ||not empty param.postClass
            || not empty param.code || not empty param.sort}"/>
<jsp:include page="unitPostAllocation_menu.jsp"/>
<div class="space-4"></div>
<div class="jqgrid-vertical-offset buttons">
    <div class="type-select">
        <span class="typeCheckbox ${param.displayEmpty==1?"checked":""}">
        <input ${param.displayEmpty==1?"checked":""} type="checkbox" class="big"
                                                     value="1"> 只显示空缺岗位
        </span>
        <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                data-url="${ctx}/unitPost_data"
                data-querystr="exportType=1"
                data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
            <i class="fa fa-download"></i> 导出</button>

    </div>
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
                <input type="hidden" name="displayEmpty" value="${param.displayEmpty}">
                <div class="form-group">
                    <label>所属单位</label>
                    <select name="unitId" data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                            data-placeholder="请选择所属内设机构">
                        <option value="${unit.id}" title="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                    </select>
                    <script>
                        $.register.del_select($("#searchForm select[name=unitId]"), 250)
                    </script>
                </div>
                <div class="form-group">
                    <label>单位类型</label>
                    <select class="multiselect" multiple="" name="unitTypes">
                        <c:import url="/metaTypes?__code=mc_unit_type"/>
                    </select>
                </div>
                <div class="form-group">
                    <label>是否正职</label>
                    <select name="isPrincipalPost" data-width="100"
                            data-rel="select2" data-placeholder="请选择">
                        <option></option>
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                    <script>
                        $("#searchForm select[name=isPrincipalPost]").val('${param.isPrincipalPost}');
                    </script>
                </div>
                <div class="form-group">
                    <label>岗位级别</label>
                    <select required class="form-control" data-rel="select2" name="adminLevel"
                            data-placeholder="请选择行政级别">
                        <option></option>
                        <option value="${cm:getMetaTypeByCode('mt_admin_level_main').id}">正处级</option>
                        <option value="${cm:getMetaTypeByCode('mt_admin_level_vice').id}">副处级</option>
                        <option value="${cm:getMetaTypeByCode('mt_admin_level_none').id}">无行政级别</option>
                    </select>
                    <script type="text/javascript">
                        $("#searchForm select[name=adminLevel]").val('${param.adminLevel}');
                    </script>
                </div>
                <div class="form-group">
                    <label>职务属性</label>
                    <select name="postType" data-rel="select2" data-placeholder="请选择职务属性">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_post"/>
                    </select>
                    <script>
                        $("#searchForm select[name=postType]").val('${param.postType}');
                    </script>
                </div>
                <div class="form-group">
                    <label>职务类别</label>
                    <select required data-rel="select2" name="postClass" data-placeholder="请选择">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_post_class"/>
                    </select>
                    <script type="text/javascript">
                        $("#searchForm select[name=postClass]").val(${param.postClass});
                    </script>
                </div>
                <div class="form-group">
                    <label>是否占干部职数</label>
                    <select name="isCpc" data-width="100"
                            data-rel="select2" data-placeholder="请选择">
                        <option></option>
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                    <script>
                        $("#searchForm select[name=isPrincipalPost]").val('${param.isPrincipalPost}');
                    </script>
                </div>
                <div class="clearfix form-actions center">
                    <a class="jqSearchBtn btn btn-default btn-sm"
                       data-url="${ctx}/unitPostAllocation?module=${module}"

                       data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                    <c:if test="${_query}">&nbsp;
                        <button type="button" class="resetBtn btn btn-warning btn-sm"
                                data-url="${ctx}/unitPostAllocation"
                                data-querystr="module=${module}&unitId=${param.unitId}"
                              >
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
    <table id="jqGrid" class="jqGrid table-striped" data-height-reduce="80"></table>
    <div id="jqGridPager"></div>
</div>
<style>
    .type-select {
        padding: 10px 0 0 5px;
    }

    .type-select a {
        padding-left: 20px;
    }

    .type-select .typeCheckbox {
        padding: 10px;
        font-size: 16px;
    }

    .type-select .typeCheckbox.checked {
        color: darkred;
        font-weight: bolder;
    }
</style>
<script type="text/template" id="dispatch_select_tpl">
    <button class="popupBtn btn {{=(count>0)?'btn-warning':'btn-success'}} btn-xs"
            data-url="${ctx}/cadrePost_addDispatchs?id={{=id}}&cadreId={{=cadreId}}"
            data-width="1000"><i class="fa fa-link"></i>
        任免文件({{=count}})
    </button>
</script>
<jsp:include page="/WEB-INF/jsp/unit/unitPost/colModel.jsp?list=0"/>
<script>
    $(":checkbox", ".typeCheckbox").click(function () {

        $("#searchForm input[name=displayEmpty]").val($(this).prop("checked")?1:0);
        $("#searchForm .jqSearchBtn").click();
    })

    function _reload(){
        $("#modal").modal('hide');
        $("#jqGrid").trigger("reloadGrid");
    }
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/unitPost_data?callback=?&displayOpen=1&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>