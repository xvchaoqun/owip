<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="myTableDiv"
                 data-url-au="${ctx}/dispatchCadre_au"
                 data-url-page="${ctx}/dispatchCadre_page"
                 data-url-del="${ctx}/dispatchCadre_del"
                 data-url-bd="${ctx}/dispatchCadre_batchDel"
                 data-url-co="${ctx}/dispatchCadre_changeOrder"
                 data-url-export="${ctx}/dispatchCadre_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.dispatchId ||not empty param.typeId
            ||not empty param.wayId ||not empty param.procedureId ||not empty param.cadreId
            ||not empty param.adminLevelId ||not empty param.unitId }"/>
                <div class="jqgrid-vertical-offset buttons">
                    <a class="openView btn btn-info btn-sm" data-url="${ctx}/dispatch_cadres"><i class="fa fa-plus"></i> 添加干部任免</a>
                    <a href="javascript:;" class="jqEditBtn btn btn-primary btn-sm" data-width="700">
                        <i class="fa fa-edit"></i> 修改信息</a>
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="dispatchCadre:del">
                        <a class="jqDelBtn btn btn-danger btn-sm"><i class="fa fa-trash"></i> 删除</a>
                    </shiro:hasPermission>
                </div>
                <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs" style="margin-right: 20px">
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
                                            <%--<div class="form-group">
                                            <label>发文</label>
                                            <div class="col-xs-6">
                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/dispatch_selects"
                                                        name="dispatchId" data-placeholder="请选择">
                                                    <option value="${dispatch.id}">${dispatch.code}</option>
                                                </select>
                                            </div>
                                        </div>--%>
                                        <div class="form-group">
                                            <label>干部</label>
                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                                                        name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${cadre.id}">${sysUser.realname}</option>
                                                </select>
                                        </div>
                                        <div class="form-group">
                                            <label>任免方式</label>
                                                <select data-rel="select2" name="wayId" data-placeholder="请选择">
                                                    <option></option>
                                                    <c:forEach var="way" items="${wayMap}">
                                                        <option value="${way.value.id}">${way.value.name}</option>
                                                    </c:forEach>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#searchForm select[name=wayId]").val('${param.wayId}');
                                                </script>
                                        </div>
                                        <div class="form-group">
                                            <label>任免程序</label>
                                                <select data-rel="select2" name="procedureId" data-placeholder="请选择">
                                                    <option></option>
                                                    <c:forEach var="procedure" items="${procedureMap}">
                                                        <option value="${procedure.value.id}">${procedure.value.name}</option>
                                                    </c:forEach>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#searchForm select[name=procedureId]").val('${param.procedureId}');
                                                </script>
                                        </div>
                                        <div class="form-group">
                                            <label>行政级别</label>
                                                <select data-rel="select2" name="adminLevelId" data-placeholder="请选择">
                                                    <option></option>
                                                    <c:forEach var="adminLevel" items="${adminLevelMap}">
                                                        <option value="${adminLevel.value.id}">${adminLevel.value.name}</option>
                                                    </c:forEach>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#searchForm select[name=adminLevelId]").val('${param.adminLevelId}');
                                                </script>
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

                                    <a class="searchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="resetBtn btn btn-warning btn-sm">
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
        <div id="item-content"> </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<link rel="stylesheet" href="${ctx}/extend/css/jquery.webui-popover.min.css" type="text/css" />
<script src="${ctx}/extend/js/jquery.webui-popover.min.js"></script>
<script type="text/template" id="dispatch_del_file_tpl">
    <a class="btn btn-success btn-sm" onclick="dispatch_del_file({{=id}}, '{{=type}}')">
        <i class="fa fa-check"></i> 确定</a>&nbsp;
    <a class="btn btn-default btn-sm" onclick="hideDel()"><i class="fa fa-trash"></i> 取消</a>
</script>


<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/dispatchCadre_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '年份', name: 'dispatch.year',resizable:false, width: 75, frozen:true },
            { label:'发文号',  name: 'dispatch.dispatchCode', width: 180,formatter:function(cellvalue, options, rowObject){
                if(rowObject.dispatch.fileName && rowObject.dispatch.fileName!='')
                    return '<a href="javascript:void(0)" onclick="swf_preview({0}, \'file\')">{1}</a>'.format(rowObject.id, cellvalue);
                else return cellvalue;
            }, frozen:true },
            { label: '任免日期',  name: 'dispatch.workTime', width: 100 , frozen:true },
            { label:'类别', name: 'type', width: 80, formatter:function(cellvalue, options, rowObject){
                return _cMap.DISPATCH_CADRE_TYPE_MAP[cellvalue];
            }, frozen:true },
            { label:'任免方式', name: 'wayId', formatter:function(cellvalue, options, rowObject){
                return _metaMap[cellvalue];
            }},
            { label:'任免程序', name: 'procedureId', formatter:function(cellvalue, options, rowObject){
                return _metaMap[cellvalue];
            }},
            { label:'干部类型', name: 'cadreTypeId', formatter:function(cellvalue, options, rowObject){
                return _metaMap[cellvalue];
            } },
            { label:'工作证号', name: 'user.code'},
            { label:'姓名', name: 'user.realname'},
            { label:'职务', name: 'post', width: 150 },
            { label:'职务属性', name: 'postId', width: 120 , formatter:function(cellvalue, options, rowObject){
                return _metaMap[cellvalue];
            } },
            { label:'行政级别', name: 'adminLevelId', formatter:function(cellvalue, options, rowObject){
               return _metaMap[cellvalue];
            } },
            { label:'所属单位', name: 'unit.name', width: 150 },
            { label:'单位类型', name: 'unit.typeId', width: 120  , formatter:function(cellvalue, options, rowObject){
                return _metaMap[cellvalue];
            }},
            { label:'发文类型', name: 'dispatch.dispatchType.name'},
            { label:'党委常委会日期', name: 'dispatch.meetingTime'},
            { label:'发文日期', name: 'dispatch.pubTime'},
            { label:'任免文件', name: 'fileName', formatter:function(cellvalue, options, rowObject){
                if(rowObject.dispatch.fileName && rowObject.dispatch.fileName!='')
                    return '<a href="javascript:void(0)" onclick="swf_preview({0}, \'file\')">查看</a>'
                                    .format(rowObject.id);
                else return '';
            }},
            { label:'上会ppt', name: 'pptName', formatter:function(cellvalue, options, rowObject){
                if(rowObject.pptName && rowObject.pptName!='')
                    return '<a href="javascript:void(0)" onclick="swf_preview({0}, \'ppt\')">查看</a>'
                                    .format(rowObject.id);
                else return '';
            }}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_user_select($('#searchForm select[name=cadreId]'));
</script>