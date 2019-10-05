<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/shortMsgTpl"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.relateId ||not empty param.receiverId ||not empty param.mobile
            || not empty param.senderId|| not empty param.relateSn|| not empty param.content || not empty param._sendTime
            || not empty param.code || not empty param.sort}"/>

            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                                <button data-url="${ctx}/shortMsg_repeat"
                                        data-title="重复发送一次"
                                        data-msg="确定重复发送这{0}条消息？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-warning btn-sm">
                                    <i class="fa fa-send-o"></i> 重复发送
                                </button>
                        </div>
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
                                            <input type="hidden" name="cls" value="${cls}">
                                            <div class="form-group">
                                                <label>所属模板</label>
                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/shortMsgTpl_selects"
                                                        name="relateId" data-placeholder="请输入模板名称">
                                                    <option value="${relateTpl.id}">${relateTpl.name}</option>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label>接收人</label>
                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                        name="receiverId" data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${receiver.id}">${receiver.realname}-${receiver.code}</option>
                                                </select>
                                            </div>
                                            <shiro:hasRole name="${ROLE_ADMIN}">
                                                <div class="form-group">
                                                    <label>手机</label>
                                                    <input class="form-control search-query" name="mobile" type="text"
                                                           value="${param.mobile}"
                                                           placeholder="请输入手机号码">
                                                </div>
                                            </shiro:hasRole>
                                            <div class="form-group">
                                                <label>发送内容</label>
                                                <input class="form-control search-query" name="content" type="text"
                                                       value="${param.content}"
                                                       placeholder="请输入">
                                            </div>
                                            <div class="form-group">
                                                <label>发送时间</label>

                                                <div class="input-group tooltip-success" data-rel="tooltip"
                                                     title="发送时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                    <input placeholder="请选择发送时间范围" data-rel="date-range-picker"
                                                           class="form-control date-range-picker"
                                                           type="text" name="_sendTime" value="${param._sendTime}"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>发送批次</label>
                                                <input class="form-control search-query" name="relateSn" type="text"
                                                       value="${param.relateSn}" style="width: 300px"
                                                       placeholder="请输入">
                                            </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"><i
                                                        class="fa fa-search"></i> 查找</a>
                                                <c:if test="${_query}">&nbsp;
                                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"  data-querystr="cls=${cls}">
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
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script type="text/template" id="itemListTpl">
    <div class="modal-header">
        <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
        <h3>重复发送记录</h3>
    </div>
    <div class="modal-body">
        <table class="table table-striped table-bordered table-condensed table-unhover2 table-center">
            <thead>
            <tr>
                <td width="150">重复发送时间</td>
            </tr>
            </thead>
            <tbody>
            {{_.each(repeatTimes, function(repeatTime, idx){ }}
            <tr>
                <td>{{=repeatTime}}</td>
            </tr>
            {{});}}
            </tbody>
        </table>
    </div>
    <div class="modal-footer">
        <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
    </div>
</script>
<script>
    function _showModal(_repeatTimes){
        var repeatTimes = $.map(_repeatTimes.split(","), function (repeatTime) {
            return $.date(parseInt(repeatTime), 'yyyy-MM-dd HH:mm:ss')
        })
        //console.log(repeatTimes)
        $.showModal(_.template($("#itemListTpl").html())({repeatTimes: repeatTimes}), 300)
    }
    $("#jqGrid").jqGrid({
        url: '${ctx}/shortMsgTpl_sendList_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '重复发送', name: 'repeatTimes', width: 90, formatter:function (cellvalue) {
                if($.isBlank(cellvalue)) return '--'
                return ('<button class="btn btn-xs btn-primary" ' +
                    'onclick="_showModal(\'{0}\')"><i class="fa fa-search"></i> 查看({1})</button>')
                        .format(cellvalue, cellvalue.split(",").length);
            },frozen:true},
            { label: '类型', name: 'type', width: 80,  formatter: function (cellvalue, options, rowObject) {
                return _cMap.CONTENT_TPL_TYPE_MAP[cellvalue];
            }},
            {label: '接收人', name: 'user.realname', width: 120, frozen: true},
            {label: '学工号', name: 'user.code', width: 120, frozen: true},
            {label: '所属模块', name: 'typeStr', width: 250},
            {label: '手机号码', name: 'mobile', width: 120},
            { label: '标题（微信）', name: 'wxTitle', width: 150, align:'left', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.type=='<%=ContentTplConstants.CONTENT_TPL_TYPE_MSG%>') return '--'
                var str = "";
                if(rowObject.wxPic!=undefined){
                    str += '<a href="{0}" target="_blank"><img src="{0}" width="40"/></a> '.format(rowObject.wxPic)
                }
                str += $.trim(cellvalue) + "，" + $.trim(rowObject.wxUrl);
                return str;
            }},
            {label: '发送内容', name: 'content', width: 350, formatter: $.jgrid.formatter.NoMultiSpace},
            {label: '发送人', name: 'sender.realname', width: 120},
            {label: '发送时间', name: 'createTime', width: 200},

            {label: 'IP', name: 'ip', width: 150},
            {
                label: '是否成功', name: 'status', width: 80, formatter: function (cellvalue, options, rowObject) {
                return cellvalue ? '<span class="label label-success">是</span>' : '<span class="label label-danger">否</span>';
            }
            },
            {label: '发送批次', name: 'relateSn', width: 350},
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.ajax_select($('#searchForm select[name=relateId]'));
    $.register.user_select($('#searchForm select[name=receiverId]'));
</script>