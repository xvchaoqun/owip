<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/shortMsg_au"
             data-url-page="${ctx}/shortMsg_page"
             data-url-export="${ctx}/shortMsg_data"
             data-url-co="${ctx}/shortMsg_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.receiverId ||not empty param.mobile || not empty param.code || not empty param.sort}"/>
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
                                            <label class="col-xs-3 control-label">账号</label>
                                            <div class="col-xs-6">
                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                        name="receiverId" data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${sysUser.id}">${sysUser.realname}</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <shiro:hasRole name="admin">
                                        <div class="col-xs-4">
                                            <div class="form-group">
                                                <label class="col-xs-3 control-label">手机</label>
                                                <div class="col-xs-6">
                                                    <input class="form-control search-query" name="mobile" type="text" value="${param.mobile}"
                                                           placeholder="请输入手机号码">
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
                <%--<div class="space-4"></div>--%>
                <table id="jqGrid" class="jqGrid table-striped"> </table>
                <div id="jqGridPager"> </div>
            </div>
        </div><div id="item-content"></div>
    </div>
</div>

<script>
    $("#jqGrid").jqGrid({
        multiselect:false,
        url: '${ctx}/shortMsg_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '接收方', name: 'receiverId',resizable:false, width: 100, formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/sysUser_view?userId={0}">{1}</a>'
                        .format(rowObject.user.id, rowObject.user.realname);
            } ,frozen:true},
            { label: '类别',  name: 'type', width: 200 ,frozen:true},
            { label: '手机号码',  name: 'mobile', width: 100 ,frozen:true},
            { label: '短信内容',  name: 'content', width: 350},
            { label: '发送时间',  name: 'createTime', width: 200},
            { label: 'IP',  name: 'ip', width: 150},
            { label: '是否成功',  name: 'status', width: 100, formatter:function(cellvalue, options, rowObject){
                return cellvalue?'<span class="label label-success">是</span>':'<span class="label label-danger">否</span>';
            }},
            { label: '返回结果',  name: 'ret', width: 200, formatter:function(cellvalue, options, rowObject){
                return $.trim(cellvalue).NoSpace();
            }},
            { label: '备注',  name: 'remark', width: 200}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    register_user_select($('#searchForm select[name=receiverId]'));
</script>