<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/oneSend_au"
             data-url-page="${ctx}/oneSend"
             data-url-export="${ctx}/oneSend_data"
             data-url-co="${ctx}/oneSend_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.receiver
            || not empty param.senderId|| not empty param.content || not empty param._sendTime}"/>

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
                                    <label>发起方</label>
                                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                            name="senderId" data-placeholder="请输入账号或姓名或学工号">
                                        <option value="${sender.id}">${sender.realname}-${sender.code}</option>
                                    </select>
                                </div>
                                    <div class="form-group">
                                    <label>接收人</label>
                                    <input class="form-control search-query" name="receiver" type="text" value="${param.receiver}"
                                           placeholder="请输入">
                                </div>
                                <div class="form-group">
                                    <label>发送内容</label>
                                    <input class="form-control search-query" name="content" type="text" value="${param.content}"
                                           placeholder="请输入">
                                </div>
                                <div class="form-group">
                                    <label>发送时间</label>
                                    <div class="input-group tooltip-success" data-rel="tooltip" title="发送时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                        <input placeholder="请选择发送时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                               type="text" name="_sendTime" value="${param._sendTime}"/>
                                    </div>
                                </div>
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
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#jqGrid").jqGrid({
        multiselect:false,
        url: '${ctx}/oneSend_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '接收人',  name: 'recivers', align:'left', width: 220 },
            { label: '学工号',  name: 'codes', align:'left', width: 220 },
            { label: '发送内容',  name: 'content', width: 350, formatter: $.jgrid.formatter.NoMultiSpace},
            { label: '发送时间',  name: 'sendTime', width: 150},
            { label: '是否成功',  name: 'isSuccess', formatter:function(cellvalue, options, rowObject){
                return cellvalue?'<span class="label label-success">是</span>':'<span class="label label-danger">否</span>';
            }},
            { label: '返回结果',  name: 'ret', width: 200, formatter: $.jgrid.formatter.htmlencodeWithNoSpace},
            { label: '发起方', name: 'sender.realname'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
     $.register.user_select($(' #searchForm select[name=senderId]'));
</script>