<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=SystemConstants.SYS_MSG_STATUS_UNCONFIRM%>" var="SYS_MSG_STATUS_UNCONFIRM"/>
<c:set value="<%=SystemConstants.SYS_MSG_STATUS_MAP%>" var="SYS_MSG_STATUS_MAP"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId||not empty param.sendUserId||not empty param.title||not empty param.content
            ||not empty param.status}"/>
            <shiro:hasAnyRoles name="${ROLE_ODADMIN},${ROLE_PARTYADMIN},${ROLE_BRANCHADMIN}">
                <c:if test="${cls==2}">
                    <div class="tabbable">
                        <jsp:include page="menu.jsp"/>
                        <div class="tab-content multi-row-head-table">
                            <div class="tab-pane in active">
                </c:if>
            </shiro:hasAnyRoles>
                            <c:if test="${cls==2&&page==1}">
                                <div class="jqgrid-vertical-offset buttons">
                                    <shiro:hasPermission name="sysMsg:list">
                                        <button class="jqBatchBtn btn btn-success btn-sm"  data-title="批量确认" data-msg="确认已完成这{0}条提醒？"
                                        data-url="${ctx}/sys/sysMsg_confirm"><i class="fa fa-check"></i> 批量确认</button>
                                    </shiro:hasPermission>
                                </div>
                            </c:if>
                            <div class="jqgrid-vertical-offset buttons">
                                <c:if test="${cls==1}">
                                    <shiro:hasPermission name="sysMsg:edit">
                                        <button class="popupBtn btn btn-info btn-sm"
                                                data-url="${ctx}/sys/sysMsg_au"><i class="fa fa-plus"></i>
                                            添加</button>
                                        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                                data-url="${ctx}/sys/sysMsg_au"
                                                data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                            修改</button>
                                    </shiro:hasPermission>
                                </c:if>
                                <c:if test="${!(cls==2&&page==1)}">
                                    <shiro:hasPermission name="sysMsg:list">
                                        <button data-url="${ctx}/sys/sysMsg_batchDel"
                                                data-title="删除"
                                                data-msg="确定删除这{0}条数据？"
                                                data-grid-id="#jqGrid"
                                                class="jqBatchBtn btn btn-danger btn-sm"><i class="fa fa-trash"></i>
                                            删除</button>
                                    </shiro:hasPermission>
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
                                            <c:if test="${cls==1||(cls==2&&page==2)}">
                                                <div class="form-group">
                                                    <label>接收人</label>
                                                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                    </select>
                                                </div>
                                            </c:if>
                                            <shiro:hasPermission name="sysMsg:edit">
                                                <div class="form-group">
                                                    <label>发送人</label>
                                                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                            name="sendUserId" data-placeholder="请输入账号或姓名或学工号">
                                                        <option value="${sendUser.id}">${sendUser.realname}-${sendUser.code}</option>
                                                    </select>
                                                </div>
                                            </shiro:hasPermission>
                                            <div class="form-group">
                                                <label>标题</label>
                                                <input class="form-control search-query" name="title" type="text" value="${param.title}"
                                                       placeholder="请输入">
                                            </div>
                                            <div class="form-group">
                                                <label>内容</label>
                                                <input class="form-control search-query" name="content" type="text" value="${param.content}"
                                                       placeholder="请输入">
                                            </div>
                                            <div class="form-group">
                                                <label>是否确认</label>
                                                <select data-rel="select2" name="status" data-placeholder="请选择" data-width="120">
                                                    <option></option>
                                                    <c:forEach items="${SYS_MSG_STATUS_MAP}" var="entry">
                                                        <option value="${entry.key}">${entry.value}</option>
                                                    </c:forEach>
                                                </select>
                                                <script> $("#searchForm select[name=status]").val(${param.status}) </script>
                                            </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"
                                                   data-url="${ctx}/sys/sysMsg?cls=${cls}&page=${page}"
                                                   data-target="#page-content"
                                                   data-form="#searchForm"><i class="fa fa-search"></i>
                                                    查找</a>
                                                <c:if test="${_query}">&nbsp;
                                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                            data-url="${ctx}/sys/sysMsg?cls=${cls}&page=${page}"
                                                            data-target="#page-content">
                                                        <i class="fa fa-reply"></i>
                                                        重置</button>
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
        <shiro:hasAnyRoles name="${ROLE_ODADMIN},${ROLE_PARTYADMIN},${ROLE_BRANCHADMIN}">
            <c:if test="${cls==2}">
                            </div>
                        </div>
                </div>
            </c:if>
        </shiro:hasAnyRoles>
    </div>
</div>
<script>

    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/sys/sysMsg_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            <c:if test="${cls==1||(cls==2&&page==2)}">
                { label: '学工号',name: 'user.code',width:120},
                { label: '接收人',name: 'user.realname'},
            </c:if>
            { label: '标题', name: 'title',width: 250,align: 'left'},
            { label: '内容',name: 'content',width:548,align:"left"/*,formatter: function (cellvalue, options, rowObject) {

                return ('<button class="popupBtn btn btn-primary btn-xs"' +
                    'data-url="${ctx}/sysMsg_view?id={0}&cls=${cls}">'+
                    '<i class="fa fa-search"></i> 查看</button>').format(rowObject.id)
                }*/},
            { label: '发送人',name: 'sendUser.realname',width:90},
            { label: '发送时间',name: 'sendTime', width:130, formatter: $.jgrid.formatter.date,
                formatoptions: {srcformat: 'Y.m.d H:i', newformat: 'Y.m.d H:i'}},
            { label: '状态', name: 'status',width:80,formatter: function (cellvalue, options, rowObject) {

                    return ${cm:toJSONObject(SYS_MSG_STATUS_MAP)}[cellvalue];
                }, cellattr: function (rowId, val, rowObject) {

                    if (rowObject.status == ${SYS_MSG_STATUS_UNCONFIRM})
                        return "class='danger'";
                }},
            <c:if test="${page==1&&cls==2}">
                { label: '确认',name: '_confirm',width:70,formatter:function (cellvalue, options, rowObject){
                    var ids = new Array();
                    ids.push(rowObject.id);
                        return ('<button class="jqBatchBtn btn btn-success btn-xs" {1} data-title="确认" data-msg="确定已完成该提醒？" '+
                            'data-url="${ctx}/sys/sysMsg_confirm?ids={0}"><i class="fa fa-hand-paper-o"></i> 确认</button>')
                            .format(ids, rowObject.status==${SYS_MSG_STATUS_UNCONFIRM}? '' : 'disabled');
                    }},
            </c:if>
            { label: '确认时间',name: 'confirmTime', width:130, formatter: $.jgrid.formatter.date,
                formatoptions: {srcformat: 'Y.m.d H:i', newformat: 'Y.m.d H:i'}},

            <c:if test="${cls==1}">
                { label: 'IP',name: 'ip'},
            </c:if>
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
</script>