<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.dp ||not empty param.userId ||not empty param.isCadre || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="sp:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/sp/spDp_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/sp/spDp_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                    <button class="popupBtn btn btn-info btn-sm tooltip-info"
                            data-url="${ctx}/sp/spDp_import"
                            data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                        批量导入</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="sp:edit">
                    <button data-url="${ctx}/sp/spDp_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/sp/spDp_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
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
                        <div class="form-group" id="search_first">
                            <label>民主党派机构</label>
                            <select data-rel="select2" name="dp"
                                    data-placeholder="请选择">
                                <option></option>
                            </select>
                        </div>
                            <script>
                                $.register.layer_type_select("search_first", "secondDiv",
                                    ${cm:toJSONArrayWithFilter(cm:getLayerTypes("lt_spDp"), "id,name,children,children.id,children.name")}
                                    , '${spDp.dp}', '${spDp.dpPost}');
                            </script>
                            <script type="text/javascript">
                                $("#searchForm select[name=dp]").val(${param.dp});
                            </script>
                        <div class="form-group">
                            <label>姓名</label>
                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                    name="userId" data-placeholder="请输入账号或姓名或学工号">
                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>是否领导干部</label>
                            <select name="isCadre" data-width="100" data-rel="select2"
                                    data-placeholder="请选择">
                                <option></option>
                                <option value="1">是</option>
                                <option value="0">否</option>
                            </select>
                            <script>
                                $("#searchForm select[name=isCadre]").val('${param.isCadre}');
                            </script>
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/sp/spDp"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/sp/spDp"
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
<script>
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/sp/spDp_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '民主党派机构',name: 'dp',width: 200,formatter: $.jgrid.formatter.MAP,
                    formatoptions:{mapKey:'layerTypeMap', filed:'name'}},
                { label: '职务',name: 'dpPost',formatter: $.jgrid.formatter.MAP,
                    formatoptions:{mapKey:'layerTypeMap', filed:'name'}},
                { label: '工作证号',name: 'user.code',width: 150},
                { label: '姓名',name: 'user.realname'},
                { label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                    formatoptions:{url: "${ctx}/sp/spDp_changeOrder"}, frozen: true},
                { label: '性别',name: 'user.gender',width: 80,formatter: $.jgrid.formatter.GENDER},
                { label: '所在单位',name: 'unitId',width: 200,align: 'lift',formatter: $.jgrid.formatter.unit},
                { label: '出生日期',name: 'user.birth',formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
                { label: '民族',name: 'user.nation'},
                { label: '专业技术职务',name: 'proPost',width: 200},
                { label: '是否领导干部',name: 'isCadre',formatter: $.jgrid.formatter.TRUEFALSE},
                { label: '所担任行政职务',name: 'adminPost',width: 300,align: 'lift'},
                { label: '联系方式',name: 'phone',width: 150},
                { label: '备注',name: 'remark',width: 200}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>