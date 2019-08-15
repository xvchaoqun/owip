<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/modify/constants.jsp"%>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/modifyBaseApply"
             data-url-export="${ctx}/modifyBaseApply_data"
             data-url-bd="${ctx}/modifyBaseApply_batchDel"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId}"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${status==1}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/modifyBaseApply?type=${param.type}&status=1"><i
                                class="fa fa-edit"></i> 修改申请</a>
                    </li>
                    <li class="<c:if test="${status==2}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/modifyBaseApply?type=${param.type}&status=2"><i
                                class="fa fa-check"></i> 审核完成</a>
                    </li>
                    <shiro:hasRole name="${ROLE_CADREADMIN}">
                        <li class="<c:if test="${status==3}">active</c:if>">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/modifyBaseApply?type=${param.type}&status=3"><i
                                    class="fa fa-times"></i> 已删除</a>
                        </li>
                    </shiro:hasRole>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${status==1}">
                                <shiro:hasPermission name="modifyBaseItem:approval">
                                <button class="jqOpenViewBatchBtn btn btn-info btn-sm"
                                        data-url="${ctx}/modifyBaseApply_approval"
                                        data-querystr="">
                                    <i class="fa fa-check-square-o"></i> 批量审批
                                </button>
                                </shiro:hasPermission>
                                <c:if test="${param.type!='admin'}">
                                    <a class="openView btn btn-success btn-sm"
                                       data-url="${ctx}/user/modifyBaseApply_au"
                                       data-open-by="page"><i class="fa fa-edit"></i> 修改申请</a>
                                    <button id="backBtn" class="jqBatchBtn btn btn-danger btn-sm"
                                            data-url="${ctx}/user/modifyBaseApply_back" data-title="撤销申请记录"
                                            data-msg="确定撤销申请记录吗？"><i class="fa fa-times"></i> 撤销申请
                                    </button>
                                </c:if>
                                <shiro:hasPermission name="modifyBaseApply:fakeDel">
                                    <a class="jqBatchBtn btn btn-danger btn-sm"
                                       data-url="${ctx}/modifyBaseApply_fakeDel" data-title="删除申请记录"
                                       data-msg="确定将这{0}条申请记录移到“已删除”库吗？"><i class="fa fa-times"></i> 删除</a>
                                </shiro:hasPermission>
                            </c:if>
                            <c:if test="${status!=1}">
                                <shiro:hasPermission name="modifyBaseApply:del">
                                    <a class="jqBatchBtn btn btn-danger btn-sm"
                                       data-url="${ctx}/modifyBaseApply_batchDel" data-title="删除申请记录"
                                       data-msg="确定删除这{0}条申请记录吗（删除后不可恢复）？"><i class="fa fa-trash"></i> 彻底删除</a>
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
                                            <label>账号</label>

                                            <div class="input-group">
                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>
                                            <c:if test="${_query || not empty param.sort}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-querystr="status=${status}">
                                                    <i class="fa fa-reply"></i> 重置
                                                </button>
                                            </c:if>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <%--<div class="space-4"></div>--%>
                        <table id="jqGrid" class="jqGrid"></table>
                        <div id="jqGridPager"></div>
                    </div>
                </div>
            </div>
        </div>
        <div id="body-content-view">
        </div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        //forceFit:true,
        url: '${ctx}/modifyBaseApply_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '序号', name: 'id', width: 50, frozen: true},
            {label: '申请时间', width: 150, name: 'createTime'/*,formatter:$.jgrid.formatter.date,formatoptions: {newformat:'Y.m.d'}*/},
            {label: '工作证号', name: 'user.code', width: 120, frozen: true},
            {
                label: '姓名', name: 'user.realname', width: 120, formatter: function (cellvalue, options, rowObject) {

                return (rowObject.cadre != undefined) ? $.cadre(rowObject.cadre.id, cellvalue) : cellvalue;
            }, frozen: true
            },
            {label: '所在单位及职务', name: 'cadre.title', width: 250},
            {
                label: '申请内容', name: 'content', width: 80, formatter: function (cellvalue, options, rowObject) {

                return '<button href="javascript:;" class="openView btn btn-primary btn-xs" data-url="${ctx}/modifyBaseItem?applyId={0}">'.format(rowObject.id)
                        + '<i class="fa fa-search"></i> 详情</button>';
            }
            },
            {
                label: '审核状态', name: 'status', formatter: function (cellvalue, options, rowObject) {
                return _cMap.MODIFY_BASE_APPLY_STATUS_MAP[cellvalue]
            }
            },
            <c:if test="${status!=3}">
            <shiro:hasRole name="${ROLE_CADREADMIN}">
            {
                label: '组织部审核', name: '_approval', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.status == '${MODIFY_BASE_APPLY_STATUS_ALL_CHECK}') {
                    return '--';
                }
                return '<button data-url="${ctx}/modifyBaseItem?opType=check&applyId={0}" class="openView btn btn-warning btn-xs">'
                                .format(rowObject.id)
                        + '<i class="fa fa-sign-in"></i> 审核</button>'
            }
            },
            {label: '最后审核时间', name: 'checkTime', width: 150},
            {label: '最后审核IP', name: 'checkIp', width: 120},
            </shiro:hasRole>
            </c:if>
            {
                hidden: true, name: '_status', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.status == undefined) return -1;
                return rowObject.status;
            }
            }]
        , onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id, id, status);
            //console.log(id)
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#backBtn").prop("disabled", true);
            } else if (ids.length == 1) {
                var rowData = $(this).getRowData(ids[0]);
                $("#backBtn").prop("disabled", rowData._status != '${MODIFY_BASE_APPLY_STATUS_APPLY}')
            } else {
                $("#backBtn").prop("disabled", false);
            }
        }
    }).jqGrid("setFrozenColumns");

    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $.register.user_select($('#searchForm select[name=userId]'));
    $('[data-rel="tooltip"]').tooltip();

    $.register.fancybox();
</script>