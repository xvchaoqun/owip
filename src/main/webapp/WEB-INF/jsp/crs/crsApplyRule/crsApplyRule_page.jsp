<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/crsApplyRule"
             data-url-export="${ctx}/crsApplyRule_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${ not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="${cls==1?'active':''}">
                        <a href="javascript:;" class="loadPage"
                           data-url="${ctx}/crsApplyRule?cls=1"><i
                                class="fa fa-th${cls==1?'-large':''}"></i> 有效规则</a>
                    </li>
                    <li class="${cls==2?'active':''}">
                        <a href="javascript:;" class="loadPage"
                           data-url="${ctx}/crsApplyRule?cls=2"><i
                                class="fa fa-th${cls==2?'-large':''}"></i> 过期规则</a>
                    </li>
                    <li class="${cls==3?'active':''}">
                        <a href="javascript:;" class="loadPage"
                           data-url="${ctx}/crsApplyRule?cls=3"><i
                                class="fa fa-th${cls==3?'-large':''}"></i> 已删除</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="crsApplyRule:edit">
                                <a class="popupBtn btn btn-info btn-sm" data-url="${ctx}/crsApplyRule_au"><i
                                        class="fa fa-plus"></i> 添加</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/crsApplyRule_au"
                                   data-grid-id="#jqGrid"
                                   ><i class="fa fa-edit"></i>
                                    修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="crsApplyRule:del">
                                <button data-url="${ctx}/crsApplyRule_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
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
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/crsApplyRule_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '所含招聘岗位', name: 'postIds', width: 500, formatter: function (cellvalue, options, rowObject) {
                var posts = [];
                $.each(rowObject.crsPosts, function(i, post){
                    posts.push(post.name);
                });
                return posts.join("，")
            }},
            {label: '最多同时报名个数', name: 'num', width: 200},
            {label: '有效期起始时间', name: 'startTime', width: 150, formatter: 'date', formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}},
            {label: '有效期截止时间', name: 'endTime', width: 150, formatter: 'date', formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}},
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>