<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers multi-row-head-table" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
                <li class="<c:if test="${cls==1}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/unitPost?cls=1"><i
                            class="fa fa-circle-o-notch"></i> 现有处级岗位</a>
                </li>
                <li class="<c:if test="${cls==2}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/unitPost?cls=2"><i class="fa fa-history"></i> 撤销处级岗位</a>
                </li>
                <li class="<c:if test="${cls==3}">active</c:if>">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/unitPost?cls=3"><i class="fa fa-trash"></i> 已删除</a>
                </li>
            </ul>
            <div class="space-4"></div>
            <c:set var="_query" value="${not empty param.unitId ||not empty param.name
            ||not empty param.isPrincipalPost||not empty param.isMainPost ||not empty param.isCpc  ||not empty param.cadreId
            ||not empty param.adminLevel ||not empty param.postType ||not empty param.postClass
            ||not empty param.unitTypes||not empty param.adminLevels
            ||not empty param.startNowPostAge||not empty param.endNowPostAge||not empty param.startNowLevelAge||not empty param.endNowLevelAge
            || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
               <%-- <shiro:hasPermission name="unitPost:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/unitPost_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/unitPost_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="unitPost:del">
                    <button data-url="${ctx}/unitPost_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>--%>

                   <div class="type-select">
<c:if test="${cls==1}">
                                <span class="typeCheckbox ${param.displayEmpty==1?"checked":""}">
                                <input ${param.displayEmpty==1?"checked":""} type="checkbox" class="big"
                                                                              value="1"> 只显示空缺岗位
                                </span>
</c:if>
                       <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-url="${ctx}/unitPost_data"
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
                                <label>岗位名称</label>
                                <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                       placeholder="请输入岗位名称">
                            </div>
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
                            <select required data-rel="select2" name="postClass" data-width="100" data-placeholder="请选择">
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
                                    $("#searchForm select[name=isCpc]").val('${param.isCpc}');
                                </script>
                            </div>
                            <div class="form-group">
                                <label>任职类型</label>
                                <select name="isMainPost" data-width="100"
                                        data-rel="select2" data-placeholder="请选择">
                                    <option></option>
                                    <option value="1">主职</option>
                                    <option value="0">兼职</option>
                                </select>
                                <script>
                                    $("#searchForm select[name=isMainPost]").val('${param.isMainPost}');
                                </script>
                            </div>
                            <div class="form-group">
                                <label>现任职干部</label>
                                <select data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/cadre_selects?status=${status}"
                                        name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>干部级别</label>
                                <select class="multiselect" multiple="" name="adminLevels">
                                    <c:import url="/metaTypes?__code=mc_admin_level"/>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>现职务始任年限</label>
                                <input class="num" type="text" name="startNowPostAge" style="width: 50px"
                                       value="${param.startNowPostAge}"> 至 <input class="num"
                                                                                  type="text" style="width: 50px"
                                                                                  name="endNowPostAge"
                                                                                  value="${param.endNowPostAge}">

                            </div>
                            <%--<div class="form-group">
                                <label>现职级始任年限</label>
                                <input class="num" type="text" name="startNowLevelAge" style="width: 50px"
                                       value="${param.startNowLevelAge}"> 至 <input class="num"
                                                                                   type="text" style="width: 50px"
                                                                                   name="endNowLevelAge"
                                                                                   value="${param.endNowLevelAge}">

                            </div>--%>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/unitPost?cls=${cls}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="resetBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/unitPost?cls=${cls}"
                                            data-target="#page-content">
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
<jsp:include page="colModel.jsp?list=0"/>
<script type="text/template" id="dispatch_select_tpl">
    <button class="popupBtn btn {{=(count>0)?'btn-warning':'btn-success'}} btn-xs"
            data-url="${ctx}/cadrePost_addDispatchs?id={{=id}}&cadreId={{=cadreId}}"
            data-width="1000"><i class="fa fa-link"></i>
        任免文件({{=count}})
    </button>
</script>
<script>
    $(":checkbox", ".typeCheckbox").click(function () {
        $("#searchForm input[name=displayEmpty]").val($(this).prop("checked")?1:0);
        $("#searchForm .jqSearchBtn").click();
    })

    $.register.multiselect($('#searchForm select[name=unitTypes]'), ${cm:toJSONArray(selectUnitTypes)});
    $.register.multiselect($('#searchForm select[name=adminLevels]'), ${cm:toJSONArray(selectAdminLevels)});
    $.register.user_select($('#searchForm select[name=cadreId]'));
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/unitPost_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>