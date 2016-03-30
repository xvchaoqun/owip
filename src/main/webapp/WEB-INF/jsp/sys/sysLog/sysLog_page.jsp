<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/sysLog_au"
             data-url-page="${ctx}/sysLog_page"
             data-url-export="${ctx}/sysLog_data"
             data-url-co="${ctx}/sysLog_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.typeId || not empty param.content}"/>
            <!-- PAGE CONTENT BEGINS -->
            <div class="col-sm-12">
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
                            <mytag:sort-form css="form-horizontal " id="searchForm">
                                <div class="row">

                                    <div class="col-xs-4">
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">类别</label>
                                            <div class="col-xs-6">
                                                <select name="typeId" data-placeholder="请选择类别">
                                                    <option></option>
                                                    <c:forEach var="metaType" items="${metaTypeMap}">
                                                        <option value="${metaType.value.id}">${metaType.value.name}</option>
                                                    </c:forEach>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#searchForm select[name=typeId]").val('${param.typeId}');
                                                    $("#searchForm select[name=typeId]").select2();
                                                </script>
                                            </div>
                                        </div>
                                    </div>
                                    <shiro:hasRole name="admin">
                                        <div class="col-xs-4">
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">内容</label>
                                                <div class="col-xs-6">
                                                    <input class="form-control search-query search-input"
                                                           name="content" type="text" value="${param.content}" placeholder="请输入日志内容">
                                                </div>
                                            </div>
                                        </div>
                                    </shiro:hasRole>
                                </div>

                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="resetBtn btn btn-warning btn-sm">
                                            <i class="fa fa-reply"></i> 重置
                                        </button>
                                    </c:if>
                                </div>
                            </mytag:sort-form>
                        </div>
                    </div>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid" class="jqGrid table-striped"> </table>
                <div id="jqGridPager"> </div>
            </div>
        </div><div id="item-content"></div>
    </div>
</div>

<script>
    $("#jqGrid").jqGrid({
        multiselect:false,
        url: '${ctx}/sysLog_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '模块',align:'center', name: 'logType.name',resizable:false, width: 120,frozen:true},
            { label: '账号ID', align:'center', name: 'userId', width: 50 ,frozen:true},
            { label: '账号', align:'center', name: 'operator', width: 100 ,frozen:true},
            { label: '请求', align:'center', name: 'api', width: 250},
            { label: '客户端', align:'center', name: 'agent', width: 350},
            { label: '内容', align:'center', name: 'content', width: 300},
            { label: '时间', align:'center', name: 'createTime', width: 150},
            { label: 'IP', align:'center', name: 'ip', width: 150}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    register_user_select($('#searchForm select[name=receiverId]'));
</script>