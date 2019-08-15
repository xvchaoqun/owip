<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=OwConstants.OW_ORG_ADMIN_DPPARTY%>" var="OW_ORG_ADMIN_DPPARTY"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
            <div class="myTableDiv"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query"
                       value="${not empty param.userId||not empty param.partyId||not empty param.branchId}"/>
                <div class="tabbable">
                    <c:if test="${type==OW_ORG_ADMIN_DPPARTY}">
                        <jsp:include page="/WEB-INF/jsp/dp/dpParty/menu.jsp"/>
                    </c:if>
                    <div class="tab-content">
                        <div class="tab-pane in active">

                            <div class="jqgrid-vertical-offset buttons">
                                <c:if test="${type==OW_ORG_ADMIN_DPPARTY}">
                                    【注：民主党派成员如果没有在正在运转的民族党派中设置为管理员，不在此显示】
                                </c:if>
                            </div>
                            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                                <div class="widget-header">
                                    <h4 class="widget-title">搜索</h4>
                                    <div class="widget-toolbar">
                                        <a href="javascript:;" data-action="collapse">
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
                                                    <select data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/sysUser_selects"
                                                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>所在民主党派</label>
                                                <div class="input-group">
                                                <select  data-width="350" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/dp/dpParty_selects?auth=1"
                                                        name="partyId" data-placeholder="请选择">
                                                    <option value="${dpParty.id}"
                                                            title="${dpParty.isDeleted}">${dpParty.name}</option>
                                                </select>
                                                </div>
                                            </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"
                                                   data-url="${ctx}/dp/dpOrgAdmin?type=${type}&cls=${cls}"
                                                   data-target="#page-content"
                                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                                <c:if test="${_query}">&nbsp;
                                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                            data-url="${ctx}/dp/dpOrgAdmin?type=${type}&cls=${cls}"
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
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>

    $("#jqGrid").jqGrid({
        url: '${ctx}/dp/dpOrgAdmin_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '学工号', name: 'user.code', width: 110, frozen: true},
            {label: '姓名', name: 'user.realname', width: 90, frozen: true},
            {label: '所在民主党派', name: 'dpPartyName', width: 450, align: 'left', formatter:function(cellvalue, options, rowObject){
                    return $.party(rowObject.partyId);
                },}
        ]
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.del_select($('#searchForm select[name=partyId]'));
    $.register.user_select($('#searchForm select[name=userId]'));

    $.dpParty
</script>