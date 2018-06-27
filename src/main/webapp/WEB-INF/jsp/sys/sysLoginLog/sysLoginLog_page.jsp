<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/sysLoginLog"
             data-url-export="${ctx}/sysLoginLog_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.type || not empty param.username || not empty param.userId|| not empty param.ip }"/>

            <div class="col-sm-12">
                <div class="jqgrid-vertical-offset buttons">
                    <shiro:hasRole name="${ROLE_ADMIN}">
                        <button type="button"
                                data-url="${ctx}/sysLogin_switch"
                                class="popupBtn btn btn-info btn-sm">
                            <i class="fa fa-refresh"></i> 切换账号
                        </button>
                    </shiro:hasRole>
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
                                    <label>登录账号</label>
                                    <input class="form-control search-query" name="username" type="text"
                                           value="${param.username}"
                                           placeholder="请输入账号">
                                </div>
                                <div class="form-group">
                                    <label>系统用户</label>
                                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>类别</label>
                                    <select name="type" data-rel="select2" data-placeholder="请选择">
                                        <option></option>
                                        <c:forEach items="<%=SystemConstants.LOGIN_TYPE_MAP%>" var="entity">
                                            <option value="${entity.key}">${entity.value}</option>
                                        </c:forEach>
                                    </select>
                                    <script>
                                        $("#searchForm select[name=type]").val('${param.type}');
                                    </script>
                                </div>
                                <div class="form-group">
                                    <label>登录IP</label>
                                    <input class="form-control search-query search-input"
                                           name="ip" type="text" value="${param.ip}" placeholder="请输入IP地址">
                                </div>

                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="resetBtn btn btn-warning btn-sm">
                                            <i class="fa fa-reply"></i> 重置
                                        </button>
                                    </c:if>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <%--<div class="space-4"></div>--%>
                <table id="jqGrid" class="jqGrid table-striped"></table>
                <div id="jqGridPager"></div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>

<script>
    $("#jqGrid").jqGrid({
        multiselect: false,
        url: '${ctx}/sysLoginLog_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '登录账号', name: 'username', width: 120, frozen: true},
            {
                label: '系统用户', name: 'user.realname', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.user == undefined) return '-'
                return $.user(rowObject.userId, rowObject.user.realname)
            }, frozen: true
            },
            {label: '登录时间', name: 'loginTime', width: 160},
            {label: '登录IP', name: 'loginIp', width: 150},
            {label: '登录地点', name: 'country', width: 120},
            {
                label: '地区',
                name: 'area',
                align: 'left',
                width: 250,
                formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.country == '局域网') return '校内';
                    return $.trim(cellvalue);
                }
            },
            {label: '上次登录时间', name: 'lastLoginTime', width: 160},
            {label: '上次登录IP', name: 'lastLoginIp', width: 150},
            {label: '上次登录地点', name: 'lastCountry', width: 120},
            {
                label: '上次登录地区',
                name: 'lastArea',
                align: 'left',
                width: 250,
                formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.lastCountry == '局域网') return '校内';
                    return $.trim(cellvalue);
                }
            },
            {
                label: '类别', name: 'type', formatter: function (cellvalue, options, rowObject) {
                return _cMap.LOGIN_TYPE_MAP[cellvalue];
            }
            },
            {
                label: '结果', name: 'success', formatter: function (cellvalue, options, rowObject) {
                return cellvalue ? "登录成功" : "登录失败";
            }
            },
            {label: '备注', name: 'remark', width: 150},
            {label: '客户端', name: 'agent', width: 550}
        ],
        rowattr: function (rowData, currentObj, rowId) {
            if (!rowData.success) {
                //console.log(rowData)
                return {'class': 'warning'}
            }
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('[data-rel="select2"]').select2();
    $.register.user_select($('#searchForm select[name=userId]'));
</script>