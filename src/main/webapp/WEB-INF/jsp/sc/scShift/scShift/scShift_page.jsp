<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="UNIT_POST_STATUS_NORMAL" value="<%=SystemConstants.UNIT_POST_STATUS_NORMAL%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.userId ||not empty param.postId ||not empty param.assignPostId || not empty param.code || not empty param.sort}"/>

            <div class="tabbable">
                <div class="multi-row-head-table tab-pane in active">
                    <div class="jqgrid-vertical-offset buttons">
                        <shiro:hasPermission name="scShift:edit">
                            <button class="popupBtn btn btn-info btn-sm"
                                    data-url="${ctx}/sc/scShift_au">
                                <i class="fa fa-plus"></i> 添加
                            </button>
                            <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                    data-url="${ctx}/sc/scShift_au"
                                    data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                修改
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="scShift:del">
                            <button data-url="${ctx}/sc/scShift_batchDel"
                                    data-title="删除"
                                    data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                                    data-grid-id="#jqGrid"
                                    class="jqBatchBtn btn btn-danger btn-sm">
                                <i class="fa fa-trash"></i> 删除
                            </button>
                        </shiro:hasPermission>
                        <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                data-url="${ctx}/sc/scShift_data"
                                data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                            <i class="fa fa-download"></i> 导出
                        </button>
                    </div>
                    <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                        <div class="widget-header">
                            <h4 class="widget-title">搜索</h4>
                            <span class="widget-note">${note_searchbar}</span>
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
                                        <label>干部</label>
                                        <c:set var="user" value="${cm:getUserById(param.userId)}"/>
                                        <select required data-rel="select2-ajax"
                                                data-ajax-url="${ctx}/cadre_selects?key=1"
                                                name="userId" data-placeholder="请选择干部">
                                            <option value="${param.userId}">${user.realname}-${user.code}</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>拟调整岗位</label>
                                        <select name="postId" data-rel="select2-ajax"
                                                data-ajax-url="${ctx}/unitPost_selects"
                                                data-width="273" data-placeholder="请选择">
                                            <option value="${post.id}" delete="${unitPost.status!=UNIT_POST_STATUS_NORMAL}">${post.code}-${post.name}</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>拟任职岗位</label>
                                        <select required name="assignPostId" data-rel="select2-ajax"
                                                data-ajax-url="${ctx}/unitPost_selects"
                                                data-width="273" data-placeholder="请选择">
                                            <option value="${assignPost.id}" delete="${unitPost.status!=UNIT_POST_STATUS_NORMAL}">${assignPost.code}-${assignPost.name}</option>
                                        </select>
                                    </div>
                                    <div class="clearfix form-actions center">
                                        <a class="jqSearchBtn btn btn-default btn-sm"
                                           data-url="${ctx}/sc/scShift"
                                           data-target="#page-content"
                                           data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                        <c:if test="${_query}">&nbsp;
                                            <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                    data-url="${ctx}/sc/scShift"
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
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        /*rownumbers:true,*/
        url: '${ctx}/sc/scShift_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'user.code'},
            {label: '姓名', name: 'user.realname'},
            {label: '拟调整岗位', name: 'unitPost.name', width: 250, align: 'left'},
            {label: '拟任职岗位', name: 'assignPost.name', width: 250, align: 'left'},
            {label: '单位名称', name: 'unitName', width: 200, align: 'left'},
            {label: '单位类型', name: 'unitTypeId', width: 200, formatter: $.jgrid.formatter.MetaType},
            {label: '是否正职', name: 'isPrincipal', width: 80, formatter: $.jgrid.formatter.TRUEFALSE},
            {label: '是否班子<br/>负责人', name: 'leaderType', width: 80, formatter: $.jgrid.formatter.TRUEFALSE},
            {label: '岗位级别', name: 'adminLevel', formatter: $.jgrid.formatter.MetaType},
            {label: '职务属性', name: 'postType', width: 200, formatter: $.jgrid.formatter.MetaType}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>