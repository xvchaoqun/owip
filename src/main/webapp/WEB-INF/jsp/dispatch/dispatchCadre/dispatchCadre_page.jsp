<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="myTableDiv"
                 data-url-au="${ctx}/dispatchCadre_au"
                 data-url-page="${ctx}/dispatchCadre"
                 data-url-co="${ctx}/dispatchCadre_changeOrder"
                 data-url-export="${ctx}/dispatchCadre_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.startYear ||not empty param.endYear||not empty param.year||not empty param.year ||not empty param.dispatchTypeId ||not empty param.code
                || not empty param.type|| not empty param.dispatchId
            ||not empty param.wayId ||not empty param.procedureId ||not empty param.cadreId
            ||not empty param.adminLevelId ||not empty param.unitId }"/>
                <div class="tabbable">
                    <jsp:include page="/WEB-INF/jsp/dispatch/dispatch_menu.jsp"/>
                    <div class="tab-content">
                        <div id="home4" class="tab-pane in active">
                <div class="jqgrid-vertical-offset buttons">
                    <a class="openView btn btn-info btn-sm" data-url="${ctx}/dispatch_cadres"><i class="fa fa-plus"></i> 添加干部任免</a>
                    <button id="editBtn" class="jqEditBtn btn btn-primary btn-sm" data-width="700">
                        <i class="fa fa-edit"></i> 修改信息</button>
                    <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="dispatchCadre:del">
                        <a class="jqBatchBtn btn btn-danger btn-sm"
                           data-url="${ctx}/dispatchCadre_batchDel" data-title="删除"
                           data-msg="确定删除这{0}条数据吗？"><i class="fa fa-trash"></i> 删除</a>
                    </shiro:hasPermission>
                </div>
                <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs" style="margin-right: 20px">
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
                                <input type="hidden" name="cls" value="${cls}">
                                <div class="form-group">
                                    <label>起止年份：</label>
                                        <input class="form-control date-picker" style="width: 50px" name="startYear" type="text"
                                               data-date-format="yyyy" data-date-min-view-mode="2" value="${param.startYear}" />
                                    -
                                        <input class="form-control date-picker" style="width: 50px" name="endYear" type="text"
                                               data-date-format="yyyy" data-date-min-view-mode="2" value="${param.endYear}" />
                                </div>
                                <div class="form-group">
                                    <label>发文类型</label>
                                    <input class="form-control date-picker" style="width: 80px" name="year" placeholder="选择年份" type="text"
                                           data-date-format="yyyy" data-date-min-view-mode="2" value="${param.year}" />
                                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/dispatchType_selects"
                                            name="dispatchTypeId" data-placeholder="请选择发文类型" data-no-results="ss">
                                        <option value="${dispatchType.id}">${dispatchType.name}</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>发文号</label>
                                    <input class="form-control search-query" name="code" type="text" value="${param.code}"
                                           placeholder="请输入发文号">
                                </div>
                                        <div class="form-group">
                                            <label>干部</label>
                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                                                        name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
                                                </select>
                                        </div>
                                        <div class="form-group">
                                            <label>类别</label>
                                            <select data-rel="select2" name="type" data-placeholder="请选择">
                                                <option></option>
                                                <c:forEach var="entity" items="${DISPATCH_CADRE_TYPE_MAP}">
                                                    <option value="${entity.key}">${entity.value}</option>
                                                </c:forEach>
                                            </select>
                                            <script type="text/javascript">
                                                $("#searchForm select[name=type]").val('${param.type}');
                                            </script>
                                        </div>
                                        <div class="form-group">
                                            <label>任免方式</label>
                                                <select class="multiselect" multiple="" name="wayId" data-placeholder="请选择">
                                                    <c:forEach var="way" items="${wayMap}">
                                                        <option value="${way.value.id}">${way.value.name}</option>
                                                    </c:forEach>
                                                </select>
                                        </div>
                                        <div class="form-group">
                                            <label>任免程序</label>
                                                <select class="multiselect" multiple="" name="procedureId" data-placeholder="请选择">
                                                    <c:forEach var="procedure" items="${procedureMap}">
                                                        <option value="${procedure.value.id}">${procedure.value.name}</option>
                                                    </c:forEach>
                                                </select>
                                        </div>
                                        <div class="form-group">
                                            <label>行政级别</label>
                                                <select class="multiselect" multiple="" name="adminLevelId" data-placeholder="请选择">
                                                    <c:forEach var="adminLevel" items="${adminLevelMap}">
                                                        <option value="${adminLevel.value.id}">${adminLevel.value.name}</option>
                                                    </c:forEach>
                                                </select>
                                        </div>
                                        <div class="form-group">
                                            <label>所属单位</label>
                                                <select name="unitId" data-rel="select2" data-placeholder="请选择">
                                                    <option></option>
                                                    <c:forEach items="${unitMap}" var="unit">
                                                        <option value="${unit.key}">${unit.value.name}</option>
                                                    </c:forEach>
                                                </select>
                                                <script>
                                                    $("#searchForm select[name=unitId]").val('${param.unitId}');
                                                </script>
                                        </div>
                                <div class="clearfix form-actions center">

                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="resetBtn btn btn-warning btn-sm" data-querystr="cls=${cls}">
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
            </div>
        </div>
                </div></div></div>
        <div id="item-content"> </div>
    </div>
</div>
<script src="${ctx}/assets/js/bootstrap-multiselect.js"></script>
<link rel="stylesheet" href="${ctx}/assets/css/bootstrap-multiselect.css" />

<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<link rel="stylesheet" href="${ctx}/extend/css/jquery.webui-popover.min.css" type="text/css" />
<script src="${ctx}/extend/js/jquery.webui-popover.min.js"></script>
<script type="text/template" id="dispatch_del_file_tpl">
    <a class="btn btn-success btn-sm" onclick="dispatch_del_file({{=id}}, '{{=type}}')">
        <i class="fa fa-check"></i> 确定</a>&nbsp;
    <a class="btn btn-default btn-sm" onclick="hideDel()"><i class="fa fa-trash"></i> 取消</a>
</script>
<script>
    register_multiselect($('#searchForm select[name=wayId]'), ${cm:toJSONArray(selectedWayIds)});
    register_multiselect($('#searchForm select[name=procedureId]'), ${cm:toJSONArray(selectedProcedureIds)});
    register_multiselect($('#searchForm select[name=adminLevelId]'), ${cm:toJSONArray(selectedAdminLevelIds)});

    register_date($('.date-picker'));
    register_dispatchType_select($('#searchForm select[name=dispatchTypeId]'), $("#searchForm input[name=year]"));

    $("#jqGrid").jqGrid({
        url: '${ctx}/dispatchCadre_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '年份', name: 'dispatch.year', width: 75,frozen:true },
            { label:'发文号',  name: 'dispatch.dispatchCode', width: 180,formatter:function(cellvalue, options, rowObject){

                return $.swfPreview(rowObject.dispatch.file, rowObject.dispatch.fileName, cellvalue, cellvalue);
            },frozen:true },
            { label: '任免日期',  name: 'dispatch.workTime',frozen:true , formatter: 'date', formatoptions: {newformat: 'Y-m-d'} },
            { label:'类别', name: 'type', width: 80, formatter:function(cellvalue, options, rowObject){
                return _cMap.DISPATCH_CADRE_TYPE_MAP[cellvalue];
            },frozen:true },
            { label:'任免方式', name: 'wayId', formatter: $.jgrid.formatter.MetaType},
            { label:'任免程序', name: 'procedureId', formatter: $.jgrid.formatter.MetaType},
            { label:'干部类型', name: 'cadreTypeId', formatter: $.jgrid.formatter.MetaType},
            { label:'工作证号', name: 'user.code'},
            { label:'姓名', name: 'user.realname', formatter: function (cellvalue, options, rowObject) {
                return $.cadre(rowObject.cadre.id, cellvalue);
            }},
            { label:'职务', name: 'post', width: 150 },
            { label:'职务属性', name: 'postId', width: 120 , formatter: $.jgrid.formatter.MetaType},
            { label:'行政级别', name: 'adminLevelId', formatter: $.jgrid.formatter.MetaType},
            { label:'所属单位', name: 'unit.name', width: 150 },
            { label:'单位类型', name: 'unit.typeId', width: 120, formatter: $.jgrid.formatter.MetaType},
            { label:'发文类型', name: 'dispatch.dispatchType.name'},
            { label:'党委常委会日期', name: 'dispatch.meetingTime', width: 130, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            { label:'发文日期', name: 'dispatch.pubTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            { label:'任免文件', name: 'fileName', formatter:function(cellvalue, options, rowObject){

                return $.swfPreview(rowObject.dispatch.file, rowObject.dispatch.fileName, '查看');
            }},
            { label:'上会ppt', name: 'pptName', formatter:function(cellvalue, options, rowObject){

                return $.swfPreview(rowObject.dispatch.ppt, rowObject.dispatch.pptName, '查看');
            }},
            { label: '是否复核', name: 'hasChecked', formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '';
                return cellvalue?"已复核":"否";
            }},{hidden:true, name:'_hasChecked', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.hasChecked==undefined) return 0;
                return rowObject.hasChecked?1:0;
            }},
            { label:'备注', width: 250, name: 'remark'}
        ], onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id, id, status);
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#editBtn").prop("disabled", true);
            } else if (ids.length == 1) {
                var rowData = $(this).getRowData(ids[0]);
                $("#editBtn").prop("disabled", rowData._hasChecked==1)
            } else {
                $("#editBtn").prop("disabled", false);
            }
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_user_select($('#searchForm select[name=cadreId]'));
</script>