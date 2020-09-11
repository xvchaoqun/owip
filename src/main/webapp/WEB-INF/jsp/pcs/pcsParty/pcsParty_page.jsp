<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.partyId || not empty param.code || not empty param.sort}"/>
            <jsp:include page="menu_pb.jsp"/>
            <div class="space-4"></div>
            <%--<div class="jqgrid-vertical-offset buttons">
                <button data-url="${ctx}/pcs/pcsParty_batchSync"
                        data-title="同步信息"
                        data-msg="确定同步这{0}条数据？"
                        data-grid-id="#jqGrid"
                        class="jqBatchBtn btn btn-info btn-sm">
                    <i class="fa fa-trash"></i> 同步信息
                </button>
            </div>--%>
             <%--<shiro:hasPermission name="pcsPartyList:edit">
                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                        data-url="${ctx}/pcs/pcsParty_au"
                        data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                    修改</button>
             </shiro:hasPermission>--%>
            <shiro:hasRole name="${ROLE_SUPER}">
                <button data-url="${ctx}/pcs/pcsParty_batchDel"
                        data-title="删除"
                        data-msg="确定删除这{0}条数据？"
                        data-grid-id="#jqGrid"
                        class="jqBatchBtn btn btn-danger btn-sm">
                    <i class="fa fa-trash"></i> 删除
                </button>
            </shiro:hasRole>
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
                                <label>${_p_partyName}</label>
                                <select name="partyId" data-rel="select2-ajax" data-width="272"
                                        data-ajax-url="${ctx}/party_selects"
                                        data-placeholder="请选择${_p_partyName}">
                                    <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                </select>
                                <script>
                                    $.register.del_select($("#searchForm select[name=partyId]"))
                                </script>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/pcs/pcsPartyList"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/pcs/pcsPartyList"
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
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    function _reload(){
        $("#jqGrid").trigger("reloadGrid");
    }
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/pcs/pcsPartyList_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '名称',name: 'name', width: 500, align: 'left'},
                <shiro:hasPermission name="pcsPoll:list">
                { label: '当前投票阶段',name: 'currentStage', width: 120, formatter: function (cellvalue, options, rowobject) {
                    if(cellvalue==undefined) return '--'
                    return _cMap.PCS_POLL_STAGE_MAP[cellvalue];
                }, frozen: true},
                </shiro:hasPermission>
                { label: '支部数量',name: 'branchCount'},
                { label: '党员数量',name: 'memberCount'},
                { label: '正式党员数量',name: 'positiveCount'},
                { label: '学生党员数量',name: 'studentMemberCount'},
                { label: '教师党员数量',name: 'teacherMemberCount'},
                { label: '离退休党员数量',name: 'retireMemberCount', width: 120},
                /*{ label: '班子数量',name: 'groupCount'},
                { label: '现任班子数量',name: 'presentGroupCount'}*/
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>