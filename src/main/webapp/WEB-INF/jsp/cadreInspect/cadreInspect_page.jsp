<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=CadreConstants.CADRE_INSPECT_STATUS_NORMAL%>" var="CADRE_INSPECT_STATUS_NORMAL"/>
<c:set value="<%=CadreConstants.CADRE_INSPECT_STATUS_ASSIGN%>" var="CADRE_INSPECT_STATUS_ASSIGN"/>
<c:set value="<%=CadreConstants.CADRE_INSPECT_STATUS_ABOLISH%>" var="CADRE_INSPECT_STATUS_ABOLISH"/>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
            <div  class="myTableDiv"
                  data-url-page="${ctx}/cadreInspect"
                  data-url-bd="${ctx}/cadreInspect_batchDel"
                  data-url-co="${ctx}/cadreInspect_changeOrder"
                  data-url-export="${ctx}/cadreInspect_data"
                  data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId ||not empty param.adminLevel
            ||not empty param.postType ||not empty param.title || not empty param.code }"/>

        <div class="tabbable">
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <c:forEach var="_status" items="<%=CadreConstants.CADRE_INSPECT_STATUS_MAP%>">
                    <li class="<c:if test="${status==_status.key}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/cadreInspect?status=${_status.key}">
                            <c:if test="${_status.key==CADRE_INSPECT_STATUS_ABOLISH}">
                                <i class="fa fa-times"></i>
                            </c:if>
                            <c:if test="${_status.key==CADRE_INSPECT_STATUS_ASSIGN}">
                                <i class="fa fa-check"></i>
                            </c:if>
                            <c:if test="${_status.key==CADRE_INSPECT_STATUS_NORMAL}">
                                <i class="fa fa-circle-o-notch fa-spin"></i>
                            </c:if>
                            ${_status.value}</a>
                    </li>
                </c:forEach>
            </ul>

            <div class="tab-content">
                <div class="tab-pane in active rownumbers">
                    <div class="jqgrid-vertical-offset buttons">
                        <c:if test="${status==CADRE_INSPECT_STATUS_NORMAL}">
                        <shiro:hasPermission name="cadreInspect:edit">
                            <a class="popupBtn btn btn-info btn-sm btn-success"
                               data-url="${ctx}/cadreInspect_au"><i class="fa fa-plus"></i>
                                添加考察对象
                            </a>
                        </shiro:hasPermission>
                        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                data-url="${ctx}/cadreInspect_au">
                            <i class="fa fa-edit"></i> 修改信息
                        </button>
                        <button class="jqOpenViewBtn btn btn-success btn-sm"
                                data-url="${ctx}/cadreInspect_pass">
                            <i class="fa fa-check"></i> 通过常委会任命
                        </button>
                        <button data-url="${ctx}/cadreInspect_abolish"
                                data-title="撤销考察对象"
                                data-msg="确认撤销该考察对象？"
                                class="jqItemBtn btn btn-danger btn-sm">
                            <i class="fa fa-reply"></i> 撤销
                        </button>
                        <shiro:hasPermission name="cadreInspect:edit">
                        <a class="popupBtn btn btn-info btn-sm tooltip-info"
                           data-url="${ctx}/cadreInspect_import"
                           data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i> 批量导入</a>
                        </shiro:hasPermission>
                        </c:if>
                        <c:if test="${status==CADRE_INSPECT_STATUS_ASSIGN}">
                            <shiro:hasPermission name="cadreInspect:edit">
                                <button class="jqOpenViewBtn btn btn-info btn-sm"
                                        data-url="${ctx}/cadreInspect_rollback">
                                    <i class="fa fa-backward"></i> 返回考察对象
                                </button>
                            </shiro:hasPermission>
                        </c:if>
                        <c:if test="${status==CADRE_INSPECT_STATUS_ABOLISH}">
                            <shiro:hasPermission name="cadreInspect:abolish">
                            <button data-url="${ctx}/cadreInspect_batchDel"
                                    data-title="删除"
                                    data-msg="确定删除这{0}条数据？（删除后无法恢复，请谨慎操作！）"
                                    class="jqBatchBtn btn btn-danger btn-sm"><i class="fa fa-trash"></i>
                                删除</button>
                        </shiro:hasPermission>
                            </c:if>
                        <button class="jqOpenViewBtn btn btn-warning btn-sm"
                                data-url="${ctx}/cadreAdLog"
                                data-id-name="inspectId"
                                data-open-by="page">
                            <i class="fa fa-search"></i> 任免操作记录
                        </button>
                        <a class="jqExportBtn btn btn-success btn-sm"
                           data-rel="tooltip" data-placement="bottom"
                           title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i> 导出</a>
                    </div>
                    <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                        <div class="widget-header">
                            <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>
                            <div class="widget-toolbar">
                                <a href="javascript:;" data-action="collapse">
                                    <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                                </a>
                            </div>
                        </div>
                        <div class="widget-body">
                            <div class="widget-main no-padding">
                                <form class="form-inline search-form" id="searchForm">
                                    <input name="status" type="hidden" value="${status}">
                                            <div class="form-group">
                                                <label>姓名</label>
                                                    <div class="input-group">
                                                        <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                                name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                            <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                        </select>
                                                    </div>
                                            </div>
                                            <div class="form-group">
                                                <label>行政级别</label>
                                                    <select data-rel="select2" name="adminLevel" data-placeholder="请选择行政级别">
                                                        <option></option>
                                                        <jsp:include page="/metaTypes?__code=mc_admin_level"/>
                                                    </select>
                                                    <script type="text/javascript">
                                                        $("#searchForm select[name=adminLevel]").val(${param.adminLevel});
                                                    </script>
                                            </div>
                                            <div class="form-group">
                                                <label>职务属性</label>
                                                    <select data-rel="select2" name="postType" data-placeholder="请选择职务属性">
                                                        <option></option>
                                                        <jsp:include page="/metaTypes?__code=mc_post"/>
                                                    </select>
                                                    <script type="text/javascript">
                                                        $("#searchForm select[name=postType]").val(${param.postType});
                                                    </script>
                                            </div>
                                            <div class="form-group">
                                                <label>单位及职务</label>
                                                    <input class="form-control search-query" name="title" type="text" value="${param.title}"
                                                           placeholder="请输入单位及职务">
                                            </div>
                                    <div class="clearfix form-actions center">
                                        <a class="jqSearchBtn btn btn-default btn-sm" ><i class="fa fa-search"></i> 查找</a>

                                        <c:if test="${_query || not empty param.sort}">&nbsp;
                                            <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                    data-querystr="status=${status}">
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

                </div></div></div>
                </div>
        </div>
        <div id="body-content-view">

        </div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        //forceFit:true,
        rownumbers: true,
        url: '${ctx}/cadreInspect_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'user.code', width: 110, frozen: true},
            {
                label: '姓名', name: 'user.realname', width: 120, formatter: function (cellvalue, options, rowObject) {
                return $.cadre(rowObject.id, cellvalue);
            }, frozen: true
            },
            <c:if test="${status==CADRE_INSPECT_STATUS_NORMAL}">
            {label: '排序', width: 80, formatter: function (cellvalue, options, rowObject) {

                    var op = {grid: ''}
                    if (options != undefined && options.colModel != undefined) {
                        op = $.extend(op, options.colModel.formatoptions);
                    }

                    return _.template($("#common_sort_tpl").html().NoMultiSpace())({
                        grid: op.grid,
                        id: rowObject.inspectId,
                        url: op.url
                    })
                }, frozen: true},
            </c:if>
            {label: '现所在单位', name: 'unit.name', width: 200, align:'left'},
            {label: '现任职务', name: 'post', width: 200, align:'left'},
            {label: '现所在单位及职务', name: 'title', width: 300, align:'left'},
            {label: '现行政级别', name: 'adminLevel', formatter:$.jgrid.formatter.MetaType},
            {label: '现职务属性', name: 'postType', formatter:$.jgrid.formatter.MetaType, width: 150},
            {label: '拟任职务', name: 'unitPost.name', width: 200, align:'left'},
            <shiro:hasPermission name="scRecord:list">
            {label: '对应的选任纪实', name: 'scRecord.code', width: 200},
            </shiro:hasPermission>
            {label: '手机号', name: 'mobile', width: 110},
            {label: '办公电话', name: 'phone'},
            {label: '家庭电话', name: 'homePhone'},
            {label: '电子邮箱', name: 'email', width: 200, align:'left'},
            <shiro:hasPermission name="scRecord:list">
            {label: '纪实人员', name: 'recordUser.realname'},
            </shiro:hasPermission>
            {label: '备注', name: 'inspectRemark', width: 350, align:'left'}, {hidden: true, key: true, name: 'inspectId'}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $('[data-rel="tooltip"]').tooltip();
    });

    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('[data-rel="select2"]').select2();
    $.register.user_select($('#searchForm select[name=userId]'));
</script>