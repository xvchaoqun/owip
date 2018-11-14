<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
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
            <c:set var="_query" value="${not empty param.unitId ||not empty param.name ||not empty param.adminLevel ||not empty param.postType ||not empty param.postClass || not empty param.code || not empty param.sort}"/>
            <%--<div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="unitPost:edit">
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
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/unitPost_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
            </div>--%>
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
<jsp:include page="colModel.jsp?list=0"/>
<script>
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