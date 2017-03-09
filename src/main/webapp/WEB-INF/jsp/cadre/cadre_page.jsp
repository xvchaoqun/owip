<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<jsp:include page="/WEB-INF/jsp/cadre/colModels.jsp"/>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="myTableDiv"
                 data-url-page="${ctx}/cadre_page"
                 data-url-co="${ctx}/cadre_changeOrder?status=${status}"
                 data-url-export="${ctx}/cadre_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.cadreId ||not empty param.typeId
            ||not empty param.postId ||not empty param.title || not empty param.code }"/>

                <div class="tabbable">
<shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                        <li class="<c:if test="${status==CADRE_STATUS_MIDDLE}">active</c:if>">
                            <a href="?status=${CADRE_STATUS_MIDDLE}"><i
                                    class="fa fa-flag"></i> ${CADRE_STATUS_MAP.get(CADRE_STATUS_MIDDLE)}</a>
                        </li>

                        <li class="<c:if test="${status==CADRE_STATUS_MIDDLE_LEAVE}">active</c:if>">
                            <a href="?status=${CADRE_STATUS_MIDDLE_LEAVE}"><i
                                    class="fa fa-flag"></i> ${CADRE_STATUS_MAP.get(CADRE_STATUS_MIDDLE_LEAVE)}</a>
                        </li>
                        <div class="buttons pull-left hidden-sm hidden-xs" style="left:50px; position: relative">
                            <a class="popupBtn btn btn-danger btn-sm"
                               data-url="${ctx}/cadre/search"><i class="fa fa-search"></i> 查询账号所属干部库</a>
                        </div>

                    </ul>
</shiro:lacksRole>
                    <div class="tab-content">
                        <div id="home4" class="tab-pane in active rownumbers">
                            <div class="jqgrid-vertical-offset buttons">
                                <c:if test="${status==CADRE_STATUS_MIDDLE_LEAVE}">
                                    <button class="jqBatchBtn btn btn-warning btn-sm"
                                            data-title="重新任用"
                                            data-msg="确定重新任用这{0}个干部吗？（添加到考察对象中）"
                                            data-url="${ctx}/cadre_re_assign" data-callback="_reAssignCallback">
                                        <i class="fa fa-reply"></i> 重新任用
                                    </button>
                                </c:if>
                                <shiro:hasPermission name="cadre:edit">
                                    <a class="popupBtn btn btn-info btn-sm btn-success"
                                       data-url="${ctx}/cadre_au?status=${status}"><i class="fa fa-plus"></i>
                                        <c:if test="${status==CADRE_STATUS_MIDDLE}">添加现任中层干部</c:if>
                                        <c:if test="${status==CADRE_STATUS_MIDDLE_LEAVE}">添加离任中层干部</c:if>
                                    </a>
                                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                        data-url="${ctx}/cadre_au"
                                        data-querystr="&status=${status}">
                                    <i class="fa fa-edit"></i> 修改信息
                                </button>
                                </shiro:hasPermission>
                                <c:if test="${status==CADRE_STATUS_MIDDLE}">
                                    <shiro:hasPermission name="cadre:leave">
                                    <button class="jqOpenViewBtn btn btn-success btn-sm"
                                            data-width="700"
                                            data-url="${ctx}/cadre_leave" data-querystr="&status=${CADRE_STATUS_MIDDLE_LEAVE}">
                                        <i class="fa fa-edit"></i> 离任
                                    </button>
                                    </shiro:hasPermission>
                                </c:if>
                                <c:if test="${status==CADRE_STATUS_MIDDLE}">
                                    <shiro:hasPermission name="cadreAdditionalPost:edit">
                                    <button class="jqOpenViewBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cadre_additional_post" data-rel="tooltip"
                                            data-placement="bottom"
                                            title="添加职务——仅用于因私出国（境）审批人身份设定">
                                        <i class="fa fa-plus"></i> 因私出国境兼审单位
                                    </button>
                                    </shiro:hasPermission>
                                </c:if>
                                <shiro:hasPermission name="cadre:import">
                                <a class="popupBtn btn btn-primary btn-sm tooltip-success"
                                   data-url="${ctx}/cadre_import?status=${status}"
                                   data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i> 导入</a>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="cadre:export">
                                <a class="jqExportBtn btn btn-success btn-sm"
                                   data-rel="tooltip" data-placement="bottom" title="导出选中记录或所有搜索结果"><i
                                        class="fa fa-download"></i> 导出</a>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="cadre:del">
                                    <button data-url="${ctx}/cadre_batchDel"
                                            data-title="删除"
                                            data-msg="确定删除这{0}条数据？"
                                            data-grid-id="#jqGrid"
                                            class="jqBatchBtn btn btn-danger btn-sm">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                </shiro:hasPermission>
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
                                                <label>姓名</label>

                                                <div class="input-group">
                                                    <input type="hidden" name="status" value="${status}">
                                                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects?status=${status}"
                                                            name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                        <option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>行政级别</label>
                                                <select data-rel="select2" name="typeId" data-placeholder="请选择行政级别">
                                                    <option></option>
                                                    <jsp:include page="/metaTypes?__code=mc_admin_level"/>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#searchForm select[name=typeId]").val(${param.typeId});
                                                </script>
                                            </div>
                                            <div class="form-group">
                                                <label>职务属性</label>
                                                <select data-rel="select2" name="postId" data-placeholder="请选择职务属性">
                                                    <option></option>
                                                    <jsp:include page="/metaTypes?__code=mc_post"/>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#searchForm select[name=postId]").val(${param.postId});
                                                </script>
                                            </div>
                                            <div class="form-group">
                                                <label>单位及职务</label>
                                                <input class="form-control search-query" name="title" type="text"
                                                       value="${param.title}"
                                                       placeholder="请输入单位及职务">
                                            </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"><i
                                                        class="fa fa-search"></i> 查找</a>

                                                <c:if test="${_query || not empty param.sort}">&nbsp;
                                                    <button type="button" class="resetBtn btn btn-warning btn-sm"
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

                            <table id="jqGrid" class="jqGrid table-striped"></table>
                            <div id="jqGridPager"></div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="item-content">

        </div>
    </div>
</div>
<script type="text/template" id="sort_tpl">
<a href="#" class="jqOrderBtn" data-id="{{=id}}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
<input type="text" value="1" class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
           title="修改操作步长">
<a href="#" class="jqOrderBtn" data-id="{{=id}}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>
</script>
<script>
    function _reAssignCallback(){
        location.href='${ctx}/cadreInspect';
    }
    <c:if test="${status==CADRE_STATUS_MIDDLE}">
    $("#jqGrid").jqGrid({
        //forceFit:true,
        rownumbers: true,
        url: '${ctx}/cadre_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.cadre
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $('[data-rel="tooltip"]').tooltip();
    });
    </c:if>

    <c:if test="${status!=CADRE_STATUS_MIDDLE}">
    $("#jqGrid").jqGrid({
        //forceFit:true,
        rownumbers: true,
        url: '${ctx}/cadre_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.cadreLeave
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $('[data-rel="tooltip"]').tooltip();
    });
    </c:if>
    $(window).triggerHandler('resize.jqGrid');
    _initNavGrid("jqGrid", "jqGridPager");

    /*function openView(id){
     $("#body-content").hide();
     $("#item-content").load("${ctx}/cadre_view?cadreId="+id).show();
     }
     function closeView(){
     $("#body-content").show();
     $("#item-content").hide();
     }*/

    $('[data-rel="select2"]').select2();
    register_user_select($('#searchForm select[name=cadreId]'));
</script>