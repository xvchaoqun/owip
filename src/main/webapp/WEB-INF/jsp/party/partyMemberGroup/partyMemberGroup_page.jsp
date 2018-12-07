<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
            <div class="myTableDiv"
                 data-url-au="${ctx}/partyMemberGroup_au"
                 data-url-page="${ctx}/partyMemberGroup"
                 data-url-export="${ctx}/partyMemberGroup_data"
                 data-url-co="${ctx}/partyMemberGroup_changeOrder"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.partyId ||not empty param.name || not empty param.code || not empty param.sort}"/>

                <div class="tabbable">
                   <jsp:include page="menu.jsp"/>
                    <div class="tab-content">
                        <div class="tab-pane in active">
                <div class="jqgrid-vertical-offset buttons">

                    <a href="javascript:;" class="jqEditBtn btn btn-primary btn-sm" >
                        <i class="fa fa-edit"></i> 修改信息</a>

                    <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i> 导出</a>
                    <c:if test="${status>=0}">
                        <shiro:hasPermission name="partyMemberGroup:del">
                            <a class="jqBatchBtn btn btn-danger btn-sm"
                               data-url="${ctx}/partyMemberGroup_batchDel" data-title="删除领导班子"
                               data-msg="确定删除这{0}个领导班子吗？"><i class="fa fa-trash"></i> 删除</a>
                            【注：删除操作将同时删除相关管理员，请谨慎操作！】
                        </shiro:hasPermission>
                    </c:if>
                    <c:if test="${status==-1}">
                        <shiro:hasPermission name="partyMemberGroup:del">
                            <a class="jqBatchBtn btn btn-success btn-sm"
                               data-url="${ctx}/partyMemberGroup_batchDel"
                               data-querystr="isDeleted=0"
                               data-title="恢复已删除领导班子"
                               data-msg="确定恢复这{0}个领导班子吗？"><i class="fa fa-reply"></i> 恢复</a>
                            【注：恢复操作之后需要重新设置相关管理员！】
                        </shiro:hasPermission>
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
                                <input type="hidden" name="status" value="${status}">
                                    <div class="form-group">
                                        <label>名称</label>
                                        <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                               placeholder="请输入名称">
                                    </div>

                                <div class="form-group">
                                    <label>所属分党委</label>
                                    <select name="partyId" data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                                            data-placeholder="请选择所属分党委">
                                        <option value="${party.id}" title="${party.isDeleted}">${party.name}</option>
                                    </select>
                                    <script>
                                        $.register.del_select($("#searchForm select[name=partyId]"), 350)
                                    </script>
                                </div>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                    <c:if test="${_query || not empty param.sort}">&nbsp;
                                        <button type="button" class="resetBtn btn btn-warning btn-sm" data-querystr="status=${status}">
                                            <i class="fa fa-reply"></i> 重置
                                        </button>
                                    </c:if>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid" class="jqGrid table-striped"> </table>
                <div id="jqGridPager"> </div>
            </div>
        </div>
                </div></div></div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/partyMemberGroup_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '名称',  name: 'name', align:'left', width: 400,formatter:function(cellvalue, options, rowObject){
                var str = '<span class="label label-sm label-primary" style="display: inline!important;"> 现任班子</span>&nbsp;';
                return (rowObject.isPresent)?str+cellvalue:cellvalue;
            },frozen:true},
            {label: '查看委员', name: 'courseNum', formatter: function (cellvalue, options, rowObject) {
                return '<a href="javascript:void(0)" class="openView" data-url="${ctx}/partyMember?groupId={0}">查看委员</a>'
                        .format(rowObject.id);
            }},
            {label: '导出委员', name: 'courseNum', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.isPresent)
                return '<a href="${ctx}/partyMember?export=1&groupId={0}" target="_blank"><i class="fa fa-file-excel-o"></i> 导出委员</a>'
                        .format(rowObject.id);
                return '-'
            }},
            { label:'所属分党委', name: 'partyId', align:'left', width: 380, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '-'
                return _cMap.partyMap[cellvalue].name
            }},
            { label: '应换届时间', name: 'tranTime', width: 130, formatter: 'date', formatoptions: {newformat: 'Y-m-d'} },
            { label: '实际换届时间', name: 'actualTranTime', width: 130, formatter: 'date', formatoptions: {newformat: 'Y-m-d'} },
            { label: '任命时间', name: 'appointTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'} },
            {  hidden:true, name: 'isPresent',formatter:function(cellvalue, options, rowObject){
                return (rowObject.isPresent)?1:0;
            }}
        ]/*,
        rowattr: function(rowData, currentObj, rowId)
        {
            if(rowData.isPresent) {
                //console.log(rowData)
                return {'class':'success'}
            }
        }*/
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>