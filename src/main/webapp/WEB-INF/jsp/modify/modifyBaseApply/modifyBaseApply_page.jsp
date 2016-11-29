<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/modifyBaseApply_page"
             data-url-export="${ctx}/modifyBaseApply_data"
             data-url-bd="${ctx}/modifyBaseApply_batchDel"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId}"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li  class="<c:if test="${status==1}">active</c:if>">
                        <a href="?status=1"><i class="fa fa-circle-o-notch fa-spin"></i> 修改申请</a>
                    </li>
                    <li  class="<c:if test="${status==2}">active</c:if>">
                        <a href="?status=2"><i class="fa fa-check"></i> 审核完成</a>
                    </li>
                    <li  class="<c:if test="${status==3}">active</c:if>">
                        <a href="?status=3"><i class="fa fa-trash"></i> 删除</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">

                            <a class="openView btn btn-success btn-sm"
                               data-url="${ctx}/user/modifyBaseApply_au"
                               data-open-by="page"><i class="fa fa-edit"></i> 修改申请</a>
                            <a class="jqBatchBtn btn btn-danger btn-sm"
                               data-url="${ctx}/user/modifyBaseApply_back" data-title="撤销申请记录"
                               data-msg="确定撤销申请记录吗？"><i class="fa fa-trash"></i> 撤销申请</a>

                                <a class="jqBatchBtn btn btn-danger btn-sm"
                                   data-url="${ctx}/modifyBaseApply_batchDel" data-title="删除申请记录"
                                   data-msg="确定删除这{0}条申请记录吗？"><i class="fa fa-trash"></i> 管理员删除</a>
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
                                            <label>账号</label>
                                            <div class="input-group">
                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                </select>
                                            </div>
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
                        <%--<div class="space-4"></div>--%>
                        <table id="jqGrid" class="jqGrid"> </table>
                        <div id="jqGridPager"> </div>
                    </div>
                </div></div></div>
        <div id="item-content">
        </div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        //forceFit:true,
        url: '${ctx}/modifyBaseApply_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '序号', name: 'id', width: 50,frozen:true },
            {label: '申请时间', width: 150, name: 'createTime'/*,formatter:'date',formatoptions: {newformat:'Y-m-d'}*/},
            { label: '工作证号', name: 'cadre.user.code', width: 80,frozen:true },
            { label: '姓名', name: 'cadre.user.realname', width: 80,frozen:true },
            { label: '所在单位及职务', name: 'cadre.title', width: 250},
            { label: '申请内容', name: 'content', width: 80,formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/modifyBaseItem?applyId={0}">详情</a>'
                        .format(rowObject.id);
            }},
            { label: '审核状态', name: 'status',formatter:function(cellvalue, options, rowObject){
                return _cMap.MODIFY_BASE_APPLY_STATUS_MAP[cellvalue]
            }},
            { label: '最后审核时间', name: 'checkTime', width: 150},
            { label: '最后审核IP', name: 'checkIp'}
        ]}).jqGrid("setFrozenColumns");

    $(window).triggerHandler('resize.jqGrid');
    _initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    register_user_select($('#searchForm select[name=userId]'));
    $('[data-rel="tooltip"]').tooltip();
</script>