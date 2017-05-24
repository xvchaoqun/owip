<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="myTableDiv"
                 data-url-au="${ctx}/branchMemberGroup_au"
                 data-url-page="${ctx}/branchMemberGroup_page"
                 data-url-export="${ctx}/branchMemberGroup_data"
                 data-url-co="${ctx}/branchMemberGroup_changeOrder"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.name ||not empty param.partyId
            ||not empty param.branchId}"/>

                <div class="tabbable">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                        <li  class="<c:if test="${status==1}">active</c:if>">
                            <a href="?status=1"><i class="fa fa-circle-o-notch fa-spin"></i> 支部委员会</a>
                        </li>
                        <li  class="<c:if test="${status==-1}">active</c:if>">
                            <a href="?status=-1"><i class="fa fa-trash"></i> 已删除支部委员会</a>
                        </li>
                    </ul>

                    <div class="tab-content">
                        <div id="home4" class="tab-pane in active">
                <div class="jqgrid-vertical-offset buttons">

                    <a href="javascript:;" class="jqEditBtn btn btn-primary btn-sm">
                        <i class="fa fa-edit"></i> 修改信息</a>

                    <button data-url="${ctx}/branch_member" class="jqOpenViewBtn btn btn-warning btn-sm">
                        <i class="fa fa-user"></i> 编辑委员
                    </button>
                    <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i> 导出</a>
                    <c:if test="${status>=0}">
                        <shiro:hasPermission name="branchMemberGroup:del">
                            <a class="jqBatchBtn btn btn-danger btn-sm"
                               data-url="${ctx}/branchMemberGroup_batchDel" data-title="删除支部委员会"
                               data-msg="确定删除这{0}个支部委员会吗？"><i class="fa fa-trash"></i> 删除</a>
                            【注：删除操作将同时删除相关管理员权限，请谨慎操作！】
                        </shiro:hasPermission>
                    </c:if>
                    <c:if test="${status==-1}">
                        <shiro:hasPermission name="branchMemberGroup:del">
                            <a class="jqBatchBtn btn btn-success btn-sm"
                               data-url="${ctx}/branchMemberGroup_batchDel"
                               data-querystr="isDeleted=0"
                               data-title="恢复已删除支部委员会"
                               data-msg="确定恢复这{0}个支部委员会吗？"><i class="fa fa-reply"></i> 恢复</a>
                            【注：恢复操作之后需要重新设置相关管理员权限！】
                        </shiro:hasPermission>
                    </c:if>
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
                                <input type="hidden" name="status" value="${status}">
                                    <div class="form-group">
                                        <label>名称</label>
                                        <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                               placeholder="请输入名称">
                                    </div>
                                    <div class="form-group">
                                        <label>分党委</label>
                                        <select class="form-control" data-rel="select2-ajax"
                                                data-ajax-url="${ctx}/party_selects?auth=1&notDirect=1" data-width="350"
                                                name="partyId" data-placeholder="请选择">
                                            <option value="${party.id}">${party.name}</option>
                                        </select>
                                    </div>
                                    <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                        <label>党支部</label>
                                        <select class="form-control" data-rel="select2-ajax"
                                                data-ajax-url="${ctx}/branch_selects"
                                                name="branchId" data-placeholder="请选择党支部">
                                            <option value="${branch.id}">${branch.name}</option>
                                        </select>
                                    </div>
                                    <script>
                                        register_party_branch_select($("#searchForm"), "branchDiv",
                                                '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                    </script>

                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                    <c:if test="${_query || not empty param.sort}">&nbsp;
                                        <button type="button" class="resetBtn btn btn-warning btn-sm">
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
        <div id="item-content"></div>
    </div>
</div>
<script type="text/template" id="sort_tpl">
    <a href="#" class="jqOrderBtn" data-id="{{=id}}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
    <input type="text" value="1" class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
    <a href="#" class="jqOrderBtn" data-id="{{=id}}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>
</script>
<script>

    $("#jqGrid").jqGrid({
        url: '${ctx}/branchMemberGroup_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '名称',  name: 'name',align:'left',width: 400,formatter:function(cellvalue, options, rowObject){
                var str = '<span class="label label-sm label-primary" style="display: inline!important;"> 现任委员会</span>&nbsp;';
                return (rowObject.isPresent)?str+cellvalue:cellvalue;
            }, frozen:true},
            {
                label: '所属组织机构', name: 'party', align:'left',  width: 550,
                formatter: function (cellvalue, options, rowObject) {
                    return displayParty(rowObject.partyId, rowObject.branchId);
                }, frozen:true
            },
            { label: '应换届时间', name: 'tranTime', width: 130, formatter: 'date', formatoptions: {newformat: 'Y-m-d'} },
            { label: '实际换届时间', name: 'actualTranTime', width: 130, formatter: 'date', formatoptions: {newformat: 'Y-m-d'} },
            { label: '任命时间', name: 'appointTime', width: 100, formatter: 'date', formatoptions: {newformat: 'Y-m-d'} },
            { label:'发文号',  name: 'dispatchCode', width: 180},
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
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid');
    })
    _initNavGrid("jqGrid", "jqGridPager");

    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>