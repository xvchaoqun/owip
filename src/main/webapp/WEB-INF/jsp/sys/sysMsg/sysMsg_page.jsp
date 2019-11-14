<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=SystemConstants.SYS_MSG_STATUS_UNREAD%>" var="SYS_MSG_STATUS_UNREAD"/>
<c:set value="<%=SystemConstants.SYS_MSG_STATUS_MAP%>" var="SYS_MSG_STATUS_MAP"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId}"/>
            <c:if test="${type == 1}">
                <div class="jqgrid-vertical-offset buttons">
                    <shiro:hasPermission name="sysMsg:edit">
                        <button class="popupBtn btn btn-info btn-sm"
                                data-url="${ctx}/sysMsg_au"><i class="fa fa-plus"></i>
                            添加</button>
                        <button data-url="${ctx}/sysMsg_batchDel"
                                data-title="删除"
                                data-msg="确定删除这{0}条数据？"
                                data-grid-id="#jqGrid"
                                class="jqBatchBtn btn btn-danger btn-sm"><i class="fa fa-trash"></i>
                            删除</button>
                    </shiro:hasPermission>
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
                                    <label>接收人</label>
                                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                    </select>
                                </div>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"
                                       data-url="${ctx}/sysMsg?type=${type}"
                                       data-target="#page-content"
                                       data-form="#searchForm"><i class="fa fa-search"></i>
                                        查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                data-url="${ctx}/sysMsg?type=${type}"
                                                data-target="#page-content">
                                            <i class="fa fa-reply"></i>
                                            重置</button>
                                    </c:if>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </c:if>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>

    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/sysMsg_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            <c:if test="${type == 1}">
                { label: '接收人',name: 'user.realname'},
                { label: '学工号',name: 'user.code',width:120},
            </c:if>
                { label: '标题', name: 'title',width: 250,align: 'left'},
                { label: '内容',name: 'content',formatter: function (cellvalue, options, rowObject) {

                    return ('<button class="popupBtn btn btn-primary btn-xs"' +
                        'data-url="${ctx}/sysMsg_view?id={0}&type=${type}">'+
                        '<i class="fa fa-search"></i> 查看</button>').format(rowObject.id)
    }},
                { label: '通知发送时间',name: 'createTime', width:150, formatter: $.jgrid.formatter.date,
                    formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'}},
            <c:if test="${type == 1}">
                { label: 'ip',name: 'ip'},
            </c:if>
                { label: '状态', name: 'status',formatter: function (cellvalue, options, rowObject) {

                   return ${cm:toJSONObject(SYS_MSG_STATUS_MAP)}[cellvalue];
                    }, cellattr: function (rowId, val, rowObject) {

                        if (rowObject.status == ${SYS_MSG_STATUS_UNREAD})
                            return "class='danger'";
                    }}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
</script>