<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="OW_PARTY_REPU_PARTY" value="<%=OwConstants.OW_PARTY_REPU_PARTY%>"/>
<c:set var="OW_PARTY_REPU_BRANCH" value="<%=OwConstants.OW_PARTY_REPU_BRANCH%>"/>
<c:set var="OW_PARTY_REPU_MEMBER" value="<%=OwConstants.OW_PARTY_REPU_MEMBER%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:if test="${clss==1}">
            <c:set var="_query"
                   value="${not empty param.id ||not empty param.partyId ||not empty param.branchId ||not empty param.userId ||not empty param.rewardTime ||not empty param.rewardType ||not empty param.unit || not empty param.code || not empty param.sort|| not empty param.userPartyId || not empty param.name}"/>
            </c:if>
            <c:if test="${clss==2}">
                <c:set var="_query" value="${not empty param.id ||not empty param.partyId ||not empty param.branchId ||not empty param.userId ||not empty param.punishTime ||not empty param.endTime ||not empty param.unit || not empty param.code || not empty param.sort || not empty userPartyId}"/>
            </c:if>
            <c:if test="${clss==3}">
                <c:set var="_query" value="${not empty param.userPartyId || not empty param.userId || not empty param.unit}"/>
            </c:if>
                <div class="tabble">
                <jsp:include page="menu.jsp"/>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${clss==2}">
                                <c:if test="${cls==OW_PARTY_REPU_PARTY}">
                                    <button class="popupBtn btn btn-info btn-sm"
                                            data-url="${ctx}/party/partyPunish_au?partyId=${param.partyId}&list=1">
                                        <i class="fa fa-plus"></i> 添加</button>
                                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                            data-url="${ctx}/party/partyPunish_au?partyId=${param.partyId}&list=1"
                                            data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                        修改</button>
                                </c:if>
                                <c:if test="${cls==OW_PARTY_REPU_BRANCH}">
                                    <button class="popupBtn btn btn-info btn-sm"
                                            data-url="${ctx}/party/partyPunish_au?branchId=${param.branchId}&cls=2&list=1">
                                        <i class="fa fa-plus"></i> 添加</button>
                                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                            data-url="${ctx}/party/partyPunish_au?branchId=${param.branchId}&cls=2&list=1"
                                            data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                        修改</button>
                                </c:if>
                                <c:if test="${cls==OW_PARTY_REPU_MEMBER}">
                                    <button class="popupBtn btn btn-info btn-sm"
                                            data-url="${ctx}/party/partyPunish_au?userId=${param.userId}&cls=3&list=1">
                                        <i class="fa fa-plus"></i> 添加</button>
                                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                            data-url="${ctx}/party/partyPunish_au?userId=${param.userId}&cls=3&list=1"
                                            data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                        修改</button>
                                </c:if>
                                <button data-url="${ctx}/party/partyPunish_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                   data-url="${ctx}/partyPunish_data"
                                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                    <i class="fa fa-download"></i> 导出</button>--%>
                            </c:if>
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
                                        <c:if test="${clss==1}">
                                            <c:if test="${cls==OW_PARTY_REPU_PARTY}">
                                            <div class="form-group">
                                                <label>${_p_partyName}</label>
                                                <select name="partyId" data-rel="select2-ajax" data-width="350"
                                                        data-ajax-url="${ctx}/party_selects"
                                                        data-placeholder="请选择奖励的${_p_partyName}">
                                                    <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                                </select>
                                                <script>
                                                    $.register.del_select($("#searchForm select[name=partyId]"))
                                                </script>
                                            </div>
                                            <div class="form-group">
                                                <label>获奖类型</label>
                                                <select name="rewardType" data-rel="select2" data-placeholder="请选择获奖类型">
                                                    <option></option>
                                                    <c:import url="/metaTypes?__code=mt_party_reward"/>
                                                </select>
                                                <script>         $("#searchForm select[name=rewardType]").val('${param.rewardType}');     </script>

                                            </div>
                                            </c:if>
                                            <c:if test="${cls==OW_PARTY_REPU_BRANCH}">
                                            <div class="form-group">
                                                <label>${_p_partyName}</label>
                                                <select class="form-control" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/party_selects?auth=1&notDirect=1"
                                                        data-width="350"
                                                        name="partyId" data-placeholder="请选择">
                                                    <option value="${party.id}">${party.name}</option>
                                                </select>
                                            </div>
                                            <div class="form-group" style="${(empty branch)?'display: none':''}"
                                                 id="branchDiv">
                                                <label>党支部</label>
                                                <select class="form-control" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/branch_selects"
                                                        name="branchId" data-placeholder="请选择党支部">
                                                    <option value="${branch.id}">${branch.name}</option>
                                                </select>
                                            </div>
                                            <script>
                                                $.register.party_branch_select($("#searchForm"), "branchDiv",
                                                    '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                            </script>
                                            </c:if>
                                            <c:if test="${cls==OW_PARTY_REPU_MEMBER}">
                                            <div class="form-group">
                                                <label>${_p_partyName}</label>
                                                <select name="userPartyId" data-rel="select2-ajax" data-width="350"
                                                        data-ajax-url="${ctx}/party_selects"
                                                        data-placeholder="请选择奖励的${_p_partyName}">
                                                    <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                                </select>
                                                <script>
                                                    $.register.del_select($("#searchForm select[name=userPartyId]"))
                                                </script>
                                            </div>
                                            <div class="form-group">
                                                <label>党员</label>
                                                <select data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/member_selects"
                                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                </select>
                                                <script>
                                                    $.register.user_select($('[data-rel="select2-ajax"]'));
                                                </script>
                                            </div>
                                            </c:if>
                                            <div class="form-group">
                                                <label>获得奖项</label>
                                                <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                                       placeholder="请输入获得奖项">
                                            </div>
                                        <div class="form-group">
                                            <label>颁奖单位</label>
                                            <input class="form-control search-query" name="unit" type="text"
                                                   value="${param.unit}"
                                                   placeholder="请输入颁奖单位">
                                        </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"
                                                   data-url="${ctx}/party/partyRePu_page?type=${param.type}&clss=1"
                                                   data-target="#page-content"
                                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                                <c:if test="${_query}">&nbsp;
                                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                            data-url="${ctx}/party/partyRePu_page?type=${param.type}&clss=1"
                                                            data-target="#page-content">
                                                        <i class="fa fa-reply"></i> 重置
                                                    </button>
                                                </c:if>
                                            </div>
                                        </c:if>
                                        <c:if test="${clss==2}">
                                            <c:if test="${cls==OW_PARTY_REPU_PARTY}">
                                                <div class="form-group">
                                                    <label>${_p_partyName}</label>
                                                    <select name="partyId" data-rel="select2-ajax" data-width="350"
                                                            data-ajax-url="${ctx}/party_selects"
                                                            data-placeholder="请选择${_p_partyName}">
                                                        <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                                    </select>
                                                    <script>
                                                        $.register.del_select($("#searchForm select[name=partyId]"))
                                                    </script>
                                                </div>
                                            </c:if>
                                            <c:if test="${cls==OW_PARTY_REPU_BRANCH}">
                                            <div class="form-group">
                                                <label>${_p_partyName}</label>
                                                <select class="form-control" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/party_selects?auth=1&notDirect=1"
                                                        data-width="350"
                                                        name="partyId" data-placeholder="请选择">
                                                    <option value="${party.id}">${party.name}</option>
                                                </select>
                                            </div>
                                            <div class="form-group" style="${(empty branch)?'display: none':''}"
                                                 id="branchDiv">
                                                <label>党支部</label>
                                                <select class="form-control" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/branch_selects"
                                                        name="branchId" data-placeholder="请选择党支部">
                                                    <option value="${branch.id}">${branch.name}</option>
                                                </select>
                                            </div>
                                            <script>
                                                $.register.party_branch_select($("#searchForm"), "branchDiv",
                                                    '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                            </script>
                                            </c:if>
                                            <c:if test="${cls==OW_PARTY_REPU_MEMBER}">
                                            <div class="form-group">
                                                <label>${_p_partyName}</label>
                                                <select name="userPartyId" data-rel="select2-ajax" data-width="350"
                                                        data-ajax-url="${ctx}/party_selects"
                                                        data-placeholder="请选择奖励的${_p_partyName}">
                                                    <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                                </select>
                                                <script>
                                                    $.register.del_select($("#searchForm select[name=userPartyId]"))
                                                </script>
                                            </div>
                                            <div class="form-group">
                                                <label>党员</label>
                                                <select data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/member_selects"
                                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                </select>
                                                <script>
                                                    $.register.user_select($('[data-rel="select2-ajax"]'));
                                                </script>
                                            </div>
                                            </c:if>
                                            <div class="form-group">
                                                <label>处分单位</label>
                                                <input class="form-control search-query" name="unit" type="text" value="${param.unit}"
                                                       placeholder="请输入处分单位">
                                            </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"
                                                   data-url="${ctx}/party/partyPunishList_page?type=${param.type}&clss=2"
                                                   data-target="#page-content"
                                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                                <c:if test="${_query}">&nbsp;
                                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                            data-url="${ctx}/party/partyPunishList_page?type=${param.type}&clss=2"
                                                            data-target="#page-content">
                                                        <i class="fa fa-reply"></i> 重置
                                                    </button>
                                                </c:if>
                                            </div>
                                        </c:if>
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
<jsp:include page="/WEB-INF/jsp/party/partyPunish/colModels.jsp?list=1"/>
<script>
    $("#jqGrid").jqGrid({
        ondblClickRow: function () {
        },
        pager: "jqGridPager",
        url: '${ctx}/party/partyPunish_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.partyPunish,
        rowattr: function (rowData, currentObj, rowId) {
            if (rowData.isPresent) {
                //console.log(rowData)
                return {'class': 'success'}
            }
        }
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $('[data-rel="tooltip"]').tooltip();
    });
    $(window).triggerHandler('resize.jqGrid');
    $('#searchForm [data-rel="select2"]').select2();
    $.register.fancybox();
    $.initNavGrid("jqGrid", "jqGridPager");
    function _reload() {
        $("#modal").modal('hide');
        $("#view-box .tab-content").loadPage("${ctx}/party/partyPunishList_page?type=${type}&clss=${clss}&${cm:encodeQueryString(pageContext.request.queryString)}");
    }

</script>