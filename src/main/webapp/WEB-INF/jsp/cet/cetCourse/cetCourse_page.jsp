<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/cet/cetCourse"
             data-url-export="${ctx}/cet/cetCourse_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${cls==1}">
                            <shiro:hasPermission name="cetCourse:edit">
                                <a class="popupBtn btn btn-info btn-sm"
                                   data-url="${ctx}/cet/cetCourse_au?type=${param.type}"><i
                                        class="fa fa-plus"></i> 添加</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/cet/cetCourse_au"
                                   data-grid-id="#jqGrid"
                                   ><i class="fa fa-edit"></i>
                                    修改</a>
                                <c:if test="${param.type==CET_COURSE_TYPE_OFFLINE}">
                                <a class="popupBtn btn btn-info btn-sm tooltip-info"
                               data-url="${ctx}/cet/cetCourse_import"
                               data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                                批量导入</a>
                                </c:if>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cetCourse:del">
                                <button data-url="${ctx}/cet/cetCourse_fakeDel?del=1"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
                                <c:if test="${param.type==CET_COURSE_TYPE_OFFLINE||param.type==CET_COURSE_TYPE_ONLINE}">
                                    <button class="jqOpenViewBatchBtn btn btn-info btn-sm"
                                            data-url="${ctx}/cet/cetCourse_addToTrain"
                                            data-grid-id="#jqGrid"><i class="fa fa-plus-circle"></i>
                                        添加到培训班</button>
                                </c:if>
                            </c:if>
                            <c:if test="${cls==4}">
                                <button data-url="${ctx}/cet/cetCourse_fakeDel?del=0"
                                        data-title="恢复使用"
                                        data-msg="确定恢复使用这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-success btn-sm">
                                    <i class="fa fa-reply"></i> 恢复使用
                                </button>
                                <button data-url="${ctx}/cet/cetCourse_batchDel"
                                        data-title="彻底删除"
                                        data-msg="确定彻底删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-times"></i> 彻底删除
                                </button>
                            </c:if>


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
                                        <input type="hidden" name="cls" value="${cls}">
                                        <input type="hidden" name="type" value="${param.type}">
                                        <div class="form-group">
                                            <label>名称</label>
                                            <input class="form-control search-query" name="name" type="text"
                                                   value="${param.name}" style="width: 200px;"
                                                   placeholder="请输入${CET_COURSE_TYPE_MAP.get(cm:toByte(param.type))}名称">
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-querystr="cls=${cls}&type=${param.type}">
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
                </div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="cetCourse_colModel.jsp?list=admin&type=${param.type}"/>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/cet/cetCourse_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    $.register.fancybox(function () {
        //console.log(this)
        this.title = '<div class="title">' + this.title + '</div>';
    });

</script>