<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/sysLog_au"
             data-url-page="${ctx}/sysLog"
             data-url-export="${ctx}/sysLog_data"
             data-url-co="${ctx}/sysLog_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.typeId || not empty param.content || not empty param.ip || not empty param.userId}"/>

            <div class="col-sm-12">
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
                                <div class="form-group">
                                    <label>操作人</label>
                                        <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                name="userId" data-placeholder="请输入账号或姓名或学工号">
                                            <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                        </select>
                                </div>
                                        <div class="form-group">
                                            <label>类别</label>
                                                <select name="typeId" data-placeholder="请选择类别">
                                                    <option></option>
                                                    <c:forEach var="logType" items="<%=LogConstants.LOG_MAP%>">
                                                        <option value="${logType.key}">${logType.value}</option>
                                                    </c:forEach>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#searchForm select[name=typeId]").val('${param.typeId}');
                                                    $("#searchForm select[name=typeId]").select2();
                                                </script>
                                        </div>
                                    <shiro:hasRole name="${ROLE_ADMIN}">
                                            <div class="form-group">
                                                <label>内容</label>
                                                    <input class="form-control search-query search-input"
                                                           name="content" type="text" value="${param.content}" placeholder="请输入日志内容">
                                            </div>
                                        <div class="form-group">
                                            <label>IP</label>
                                            <input class="form-control search-query search-input"
                                                   name="ip" type="text" value="${param.ip}" placeholder="请输入IP地址">
                                        </div>
                                    </shiro:hasRole>

                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm">
                                            <i class="fa fa-reply"></i> 重置
                                        </button>
                                    </c:if>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <%--<div class="space-4"></div>--%>
                <table id="jqGrid" class="jqGrid table-striped"> </table>
                <div id="jqGridPager"> </div>
            </div>
        </div><div id="body-content-view"></div>
    </div>
</div>

<script>
    $("#jqGrid").jqGrid({
        multiselect:false,
        url: '${ctx}/sysLog_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '模块', name: 'typeId', formatter: function (cellvalue, options, rowObject) {
                return $.trim(_cMap.LOG_MAP[cellvalue]);
            }},
            { label: '操作人',  name: 'user.realname', formatter:function(cellvalue, options, rowObject){
                if(rowObject.user==undefined) return $.trim(rowObject.operator)
                return $.user(rowObject.user.id, rowObject.user.realname);
            },frozen:true },
            { label: '操作人学工号',  name: 'user.code', width: 120,frozen:true },
            { label: '请求',  name: 'api', width: 250},
            { label: '客户端',  name: 'agent', width: 350},
            { label: '内容',  name: 'content', width: 300, formatter: $.jgrid.formatter.htmlencodeWithNoSpace},
            { label: '时间',  name: 'createTime', width: 150},
            { label: 'IP',  name: 'ip', width: 150}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('#searchForm select[name=userId]'));
</script>