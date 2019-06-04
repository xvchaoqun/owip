<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/modify/constants.jsp"%>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/modifyTableApply"
             data-url-export="${ctx}/modifyTableApply_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${cls==1}">
                                <shiro:hasPermission name="modifyTableApply:fakeDel">
                                    <a class="jqBatchBtn btn btn-danger btn-sm"
                                       data-url="${ctx}/modifyTableApply_fakeDel" data-title="删除申请记录"
                                       data-msg="确定将这{0}条申请记录移到“已删除”库吗？"><i class="fa fa-times"></i> 删除</a>
                                </shiro:hasPermission>
                            </c:if>
                            <c:if test="${cls!=1}">
                                <shiro:hasPermission name="modifyTableApply:del">
                                    <a class="jqBatchBtn btn btn-danger btn-sm"
                                       data-url="${ctx}/modifyTableApply_batchDel" data-title="删除申请记录"
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
                                        <input type="hidden" name="cls" value="${cls}">
                                        <input type="hidden" name="module" value="${module}">

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
                                                        data-querystr="module=${module}&cls=${cls}">
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
    //序号、申请时间、工作证号、姓名、所在单位及职务、修改方式、申请内容、组织部审核
    $("#jqGrid").jqGrid({
        //forceFit:true,
        url: '${ctx}/modifyTableApply_data?callback=?&module=${module}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '序号', name: 'id', width: 50, frozen: true},
            {label: '申请时间', width: 150, name: 'createTime'/*,formatter:$.jgrid.formatter.date,formatoptions: {newformat:'Y-m-d'}*/},
            {label: '工作证号', name: 'user.code', width: 120, frozen: true},
            {
                label: '姓名', name: 'user.realname', width: 120, formatter: function (cellvalue, options, rowObject) {
                return (rowObject.cadre != undefined) ? $.cadre(rowObject.cadre.id, cellvalue) : cellvalue;
            }, frozen: true
            },
            {label: '所在单位及职务', name: 'cadre.title', width: 250},
            {
                label: '修改方式', name: 'type', formatter: function (cellvalue, options, rowObject) {

                if (cellvalue == undefined) return '--'
                return _cMap.MODIFY_TABLE_APPLY_TYPE_MAP[cellvalue];
            }
            },
            {
                label: '申请内容', name: 'content', width: 80, formatter: function (cellvalue, options, rowObject) {
                return '<button href="javascript:;" class="openView btn btn-primary btn-xs" data-url="${ctx}/modifyTableApply_detail?applyId={0}">'.format(rowObject.id)
                        + '<i class="fa fa-search"></i> 详情</button>';
            }
            },
            <shiro:hasRole name="${ROLE_CADREADMIN}">
            <c:if test="${cls==1}">
            {
                label: '组织部审核', name: '_check', formatter: function (cellvalue, options, rowObject) {

                return '<button data-url="${ctx}/modifyTableApply_detail?opType=check&applyId={0}" class="openView btn btn-success btn-xs">'
                                .format(rowObject.id)
                        + '<i class="fa fa-check"></i> 审核</button>'
            }
            },
            </c:if>
            </shiro:hasRole>

            {
                label: '审核状态', name: 'status', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--'
                return _cMap.MODIFY_TABLE_APPLY_STATUS_MAP[cellvalue];
            }
            },
            <c:if test="${cls==1}">
            {
                label: '操作', name: '_check', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.userId != '${_user.id}') return '--'
                return '<button data-url="${ctx}/user/modifyTableApply_back?id={0}" data-msg="确定撤销申请？" data-callback="_reload" class="confirm btn btn-danger btn-xs">'
                                .format(rowObject.id)
                        + '<i class="fa fa-times"></i> 撤销</button>'
            }
            },
            </c:if>
            <c:if test="${cls==2}">
            {label: '审核人', name: 'checkUser.realname'},
            {label: '审核时间', name: 'checkTime', width: 150},
            {label: '依据', name: 'checkReason', width: 250},
            {label: '备注', name: 'checkRemark', width: 250},
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

    function _reload() {
        $("#modal").modal('hide');
        $("#jqGrid").trigger("reloadGrid");
    }
</script>
<jsp:include page="/WEB-INF/jsp/cadre/colModels.jsp?_sort=no"/>