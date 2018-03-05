<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/cet/cetCourse"
                 data-url-export="${ctx}/cet/cetCourse_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cetCourse:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/cet/cetCourse_au"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cet/cetCourse_au"
                       data-grid-id="#jqGrid"
                       data-querystr="&"><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="cetCourse:del">
                    <button data-url="${ctx}/cet/cetCourse_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <%--<a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</a>--%>
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
                        <div class="form-group">
                            <label>课程名称</label>
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入课程名称">
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
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="item-content"></div>
    </div>
</div>
<script>
    var courseTypeMap = ${cm:toJSONObject(courseTypeMap)};
    $("#jqGrid").jqGrid({
        url: '${ctx}/cet/cetCourse_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '课程编号',name: 'sn'},
            { label: '设立时间',name: 'foundDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            { label: '课程名称',name: 'name', width: 200, align:'left'},
            { label:'排序',align:'center',index:'sort', formatter:function(cellvalue, options, rowObject){
                return _.template($("#common_sort_tpl").html().NoMultiSpace())({id:rowObject.id,
                    url:"${ctx}/cet/cetCourse_changeOrder"})
            }},
            { label: '主讲人',name: 'cetExpert.realname'},
            { label: '所在单位',name: 'cetExpert.unit', width:200, align:'left'},
            { label: '职务和职称',name: 'cetExpert.post', width:300, align:'left'},
            { label: '学时',name: 'period'},
            { label: '专题分类',name: 'courseTypeId', formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return ''
                return courseTypeMap[cellvalue].name
            }},
            { label: '详情',name: '_detail'},
            { label: '备注',name: 'remark', width:400}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>