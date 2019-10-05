<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${isCurrent}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/cg/cgCount?isCurrent=1">
                            <i class="fa fa-circle-o-notch fa-spin"></i>
                            现任成员</a>
                    </li>
                    <li class="<c:if test="${!isCurrent}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/cg/cgCount?isCurrent=0">
                            <i class="fa fa-history"></i>
                            离任成员</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <c:set var="_query" value="${not empty param.userId ||not empty param.type ||not empty param.teamId || not empty param.code || not empty param.sort}"/>

                        <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                            <div class="widget-header">
                                <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>
                                <div class="widget-toolbar">
                                    <a href="#" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main no-padding">
                                    <form class="form-inline search-form" id="searchForm">
                                        <input class="hidden" name="isCurrent" value="${param.isCurrent}">
                                        <div class="form-group">
                                            <label>姓名</label>
                                            <select name="userId"
                                                    class="form-control"
                                                    data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/sysUser_selects"
                                                    data-placeholder="请输入账号或姓名或学工号">
                                                <option value="${param.userId}">${sysUser.realname}</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>人员类型</label>
                                            <select id="typeSelect1" name="type" data-placeholder="请选择人员类型"
                                                    data-rel="select2">
                                                <option></option>
                                                <c:forEach items="<%=CgConstants.CG_MEMBER_TYPE_MAY%>" var="cgMemberType">
                                                    <option value="${cgMemberType.key}">${cgMemberType.value}</option>
                                                </c:forEach>
                                            </select>
                                            <script>
                                                $("#typeSelect1").val('${param.type}');
                                            </script>
                                        </div>
                                        <div class="form-group">
                                            <label>委员会和领导小组名称</label>
                                            <select name="teamId"
                                                    class="form-control"
                                                    data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/cg/cgTeam_selects"
                                                    data-placeholder="请输入名称">
                                                <option value="${param.teamId}">${cgTeam.name}</option>
                                            </select>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"
                                               data-url="${ctx}/cg/cgCount"
                                               data-target="#page-content"
                                               data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-url="${ctx}/cg/cgCount"
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
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers:true,
        pager:"jqGridPager",
        url: '${ctx}/cg/cgCount_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '姓名',name: 'user.realname'},
                { label: '学工号',name: 'user.code', width: 150},
                { label: '职务',name: 'post',width:150, formatter: $.jgrid.formatter.MetaType},
                { label: '人员类型',name: 'type',formatter: function (cellvalue, options, rowObject)
                    {return cellvalue == <%=CgConstants.CG_MEMBER_TYPE_CADRE%>?'现任干部':'各类代表'}
                },
                { label: '委员会和领导小组名称',name: 'cgTeam.name',width:300,align:'left'},
                { label: '所在单位', name: 'user.unit',width: 250}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $.register.user_select($('[data-rel="select2-ajax"]'));
</script>