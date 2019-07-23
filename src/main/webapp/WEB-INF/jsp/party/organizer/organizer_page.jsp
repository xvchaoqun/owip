<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="OW_ORGANIZER_TYPE_SCHOOL" value="<%=OwConstants.OW_ORGANIZER_TYPE_SCHOOL%>"/>
<c:set var="OW_ORGANIZER_TYPE_UNIT" value="<%=OwConstants.OW_ORGANIZER_TYPE_UNIT%>"/>
<c:set var="OW_ORGANIZER_STATUS_NOW" value="<%=OwConstants.OW_ORGANIZER_STATUS_NOW%>"/>
<c:set var="OW_ORGANIZER_STATUS_LEAVE" value="<%=OwConstants.OW_ORGANIZER_STATUS_LEAVE%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year
            ||not empty param.userId ||not empty param.partyId
            || not empty param.unitId || not empty param.unit}"/>

            <jsp:include page="menu.jsp"/>
            <div class="space-4"></div>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="organizer:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/organizer_au?cls=${cls}&type=${type}">
                        <i class="fa fa-plus"></i> 添加
                    </button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/organizer_au"
                            data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改
                    </button>
                    <c:if test="${cls==1}">
                    <button class="jqOpenViewBtn btn btn-warning btn-sm"
                            data-url="${ctx}/organizer_leave?status=${OW_ORGANIZER_STATUS_LEAVE}"
                            data-grid-id="#jqGrid"><i class="fa fa-sign-out"></i>
                        离任
                    </button>
                    </c:if>
                    <c:if test="${cls==2}">
                    <button class="jqOpenViewBtn btn btn-warning btn-sm"
                            data-msg="确定将该组织员从离任库返回至现任库？"
                            data-title="返回现任"
                            data-url="${ctx}/organizer_leave?status=${OW_ORGANIZER_STATUS_NOW}"
                            data-grid-id="#jqGrid"><i class="fa fa-reply"></i>
                        重新任用
                    </button>
                    </c:if>
                </shiro:hasPermission>
                <shiro:hasPermission name="organizer:del">
                    <button data-url="${ctx}/organizer_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？<br/>（删除后不可恢复，请谨慎操作！）<c:if test="${cls==3}">
                    <div style='color:red;'><br/>注：历史任职情况包含现任情况，如在此删除的记录中包含现任情况，则系统将同时删除现任库中对应的记录。</div>
                </c:if>"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                        data-url="${ctx}/organizer_data?cls=${cls}&type=${type}"
                        data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出
                </button>--%>
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
                            <div class="form-group" >
                                <label>年度</label>
                                <div class="input-group">
                                    <div class="input-group date" data-date-format="yyyy" data-date-min-view-mode="2" style="width: 110px;">
                                        <input required class="form-control" placeholder="请选择" name="year"
                                               value="${param.year}" type="text"/>
                                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>姓名</label>
                                <select required data-rel="select2-ajax" data-width="272"
                                          data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                                    name="userId" data-placeholder="请输入账号或姓名或工号">
                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.username}</option>
                                 </select>
                            </div>
                            <div class="form-group">
                                <label>组别</label>
                                <input class="form-control search-query" name="partyId" type="text"
                                       value="${param.partyId}"
                                       placeholder="请输入联系党委">
                            </div>
                            <c:if test="${type==OW_ORGANIZER_TYPE_SCHOOL}">
                            <div class="form-group">
                                <label>联系单位</label>
                                <input class="form-control search-query" name="unit" type="text"
                                       value="${param.unit}"
                                       placeholder="请输入">
                            </div>
                            </c:if>
                            <c:if test="${type==OW_ORGANIZER_TYPE_UNIT}">
                            <div class="form-group">
                                <label>联系${_p_partyName}</label>
                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                                        data-width="350" name="partyId" data-placeholder="请选择">
                                     <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                 </select>
                            </div>
                            </c:if>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/organizer?cls=${cls}&type=${type}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/organizer?cls=${cls}&type=${type}"
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
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/organizer_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            <c:if test="${cls==1 || cls==2}">
            {label: '历史任职情况', name: '_history', width: 120, formatter: function (cellvalue, options, rowObject) {
                     return ('<button class="loadPage btn btn-primary btn-xs" ' +
                        'data-url="${ctx}/organizer?type=${type}&cls=3&userId={0}">'
                        + '<i class="fa fa-history"></i> 查看</button>')
                        .format(rowObject.userId);
            }, frozen: true},
            </c:if>
            {label: '年度', name: 'year', width: 80, frozen: true},
            {label: '工作证号', name: 'user.code', width: 110, frozen: true},
            {
                label: '姓名', name: 'user.realname', formatter: function (cellvalue, options, rowObject) {
                    return $.user(rowObject.userId, cellvalue);
                }, frozen: true
            },
            {
                label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                formatoptions: {grid: '#jqGrid', url: "${ctx}/organizer_changeOrder"}
            },
            {label: '所在单位', name: 'unit', width: 180, align: 'left'},
            {label: '行政职务', name: 'post', width: 300, align: 'left'},
            {label: '性别', name: 'user.gender', width: 50, formatter: $.jgrid.formatter.GENDER},
            {label: '出生日期', name: 'user.birth', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            /*{label: '身份证号码', name: 'user.idcard', width: 170},*/
            {label: '入党时间', name: 'growTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '编制类别', name: 'authorizedType'},
            {label: '人员类别', name: 'staffType'},
            {label: '岗位类别', name: 'postClass'},
            {label: '主岗等级', name: 'mainPostLevel'},
            {label: '专业技术职务', name: 'proPost', width: 150},
            {label: '是否离休', name: 'isRetire', formatter: $.jgrid.formatter.TRUEFALSE},
            {label: '手机号码', name: 'user.mobile', width: 120},
            <c:if test="${type==OW_ORGANIZER_TYPE_SCHOOL && cls==1}">
            {label: '所在小组', name: '_history', width: 90, formatter: function (cellvalue, options, rowObject) {
                    return ('<button class="loadPage btn btn-success btn-xs" ' +
                        'data-url="${ctx}/organizer?type=${type}&cls=10&userId={0}">'
                        + '<i class="fa fa-search"></i> 查看</button>')
                        .format(rowObject.userId);
            }, frozen: true},
            </c:if>
            <c:if test="${type==OW_ORGANIZER_TYPE_UNIT}">
            { label: '联系${_p_partyName}', name: 'partyId',align:'left', width: 350, formatter:function(cellvalue, options, rowObject){
                return $.party(rowObject.partyId);
            }},
            </c:if>
            {
                label: '任职时间',
                name: 'appointDate',
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            <c:if test="${cls==2||cls==3}">
            {
                label: '离任时间',
                name: 'dismissDate',
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            </c:if>
            {label: '备注', name: 'remark', width: 200}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.del_select($('#searchForm select[name=partyId]'));
    $.register.user_select($('#searchForm select[name=userId]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $.register.date($('.input-group.date'));
</script>