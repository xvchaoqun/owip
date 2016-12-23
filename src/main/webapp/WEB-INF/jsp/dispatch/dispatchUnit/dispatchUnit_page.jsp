<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="myTableDiv"
                 data-url-au="${ctx}/dispatchUnit_au"
                 data-url-page="${ctx}/dispatchUnit_page"
                 data-url-del="${ctx}/dispatchUnit_del"
                 data-url-bd="${ctx}/dispatchUnit_batchDel"
                 data-url-co="${ctx}/dispatchUnit_changeOrder"
                 data-url-export="${ctx}/dispatchUnit_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.year ||not empty param.unitId ||not empty param.typeId || not empty param.code || not empty param.sort}"/>
                <div class="jqgrid-vertical-offset buttons">
                    <shiro:hasPermission name="dispatchUnit:edit">
                        <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <a href="javascript:;" class="jqEditBtn btn btn-primary btn-sm">
                        <i class="fa fa-edit"></i> 修改信息</a>
                    <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="dispatchUnit:del">
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
                                        <div class="form-group">
                                            <label>年份</label>
                                                <div class="input-group">
                                                    <input class="form-control date-picker" placeholder="请选择年份" name="year" type="text"
                                                           data-date-format="yyyy" data-date-min-view-mode="2" value="${param.year}" />
                                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                                </div>
                                        </div>
                                        <div class="form-group">
                                            <label>所属单位</label>
                                                <select name="unitId" data-rel="select2" data-placeholder="请选择所属单位">
                                                    <option></option>
                                                    <c:forEach items="${unitMap}" var="unit">
                                                        <option value="${unit.key}">${unit.value.name}</option>
                                                    </c:forEach>
                                                </select>
                                                <script>
                                                    $("#searchForm select[name=unitId]").val('${param.unitId}');
                                                </script>
                                        </div>
                                        <div class="form-group">
                                            <label>类型</label>
                                                <select data-rel="select2" name="typeId" data-placeholder="请选择单位发文类型">
                                                    <option></option>
                                                    <c:forEach var="dispatchUnitType" items="${dispatchUnitTypeMap}">
                                                        <option value="${dispatchUnitType.value.id}">${dispatchUnitType.value.name}</option>
                                                    </c:forEach>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#searchForm select[name=typeId]").val('${param.typeId}');
                                                </script>
                                        </div>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
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
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/dispatchUnit_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label:'所属发文',  name: 'dispatch.dispatchCode', width: 180,formatter:function(cellvalue, options, rowObject){
                if(rowObject.dispatch.fileName && rowObject.dispatch.fileName!='')
                    return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/swf/preview?path={0}&filename={1}">{2}</a>'
                            .format(encodeURI(rowObject.dispatch.file), rowObject.dispatch.fileName, cellvalue);
                else return cellvalue;
            },frozen:true },

            { label:'所属单位', name: 'unit.name', width: 150 },
            { label:'类型', name: 'typeId', width: 120  , formatter:function(cellvalue, options, rowObject){
                return _cMap.metaTypeMap[cellvalue].name;
            }},
            { label:'年份', name: 'year'},
            { label:'备注', name: 'remark'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    _initNavGrid("jqGrid", "jqGridPager");
    register_date($('.date-picker'));
    $('[data-rel="select2"]').select2();
</script>