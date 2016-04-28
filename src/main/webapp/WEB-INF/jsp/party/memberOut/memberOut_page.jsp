<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="myTableDiv"
                 data-url-au="${ctx}/memberOut_au"
                 data-url-page="${ctx}/memberOut_page"
                 data-url-export="${ctx}/memberOut_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId ||not empty param.type
                || not empty param.status ||not empty param.isBack
                ||not empty param.partyId ||not empty param.branchId || not empty param.code || not empty param.sort}"/>
                <div class="tabbable">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                        <li class="dropdown <c:if test="${cls==1||cls==4||cls==5}">active</c:if>" >
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <i class="fa fa-circle-o"></i> 分党委审核${cls==1?"(新申请)":(cls==4)?"(返回修改)":(cls==5)?"(已审核)":""}
                                <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-info" style="min-width: 100px">
                                <li>
                                    <a href="?cls=1">新申请</a>
                                </li>
                                <li>
                                    <a href="?cls=4">返回修改</a>
                                </li>
                                <li>
                                    <a href="?cls=5">已审核</a>
                                </li>
                            </ul>
                        </li>
                        <shiro:hasRole name="odAdmin">
                            <li class="dropdown <c:if test="${cls==6||cls==7}">active</c:if>" >
                                <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                    <i class="fa fa-circle-o"></i> 组织部审核${cls==6?"(新申请)":(cls==7)?"(返回修改)":""}
                                    <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
                                </a>
                                <ul class="dropdown-menu dropdown-info" style="min-width: 100px">
                                    <li>
                                        <a href="?cls=6">新申请</a>
                                    </li>
                                    <li>
                                        <a href="?cls=7">返回修改</a>
                                    </li>
                                </ul>
                            </li>

                        </shiro:hasRole>
                        <li class="${cls==2?'active':''}">
                            <a ${cls!=2?'href="?cls=2"':''}><i class="fa fa-times"></i> 未通过</a>
                        </li>
                        <li class="${cls==3?'active':''}">
                            <a ${cls!=3?'href="?cls=3"':''}><i class="fa fa-check"></i> 已审核</a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div id="home4" class="tab-pane in active">
                            <div class="jqgrid-vertical-offset buttons">
                                <shiro:hasPermission name="memberOut:edit">
                                    <c:if test="${cls==1}">
                                    <a href="javascript:;" class="openView btn btn-info btn-sm" data-url="${ctx}/memberOut_au">
                                        <i class="fa fa-plus"></i> 添加</a>
                                    </c:if>
                                    <c:if test="${cls!=3}">
                                    <button id="editBtn" class="jqEditBtn btn btn-primary btn-sm"
                                            data-open-by="page">
                                        <i class="fa fa-edit"></i> 修改信息
                                    </button>
                                    </c:if>
                                </shiro:hasPermission>
                                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                   data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i
                                        class="fa fa-download"></i> 导出</a>
                                <c:if test="${cls==1||cls==4}">
                                    <button id="partyApprovalBtn" ${partyApprovalCount>0?'':'disabled'}
                                            class="jqOpenViewBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/memberOut_approval"
                                            data-open-by="page"
                                            data-querystr="&type=1&cls=${cls}"
                                            data-need-id="false"
                                            data-count="${partyApprovalCount}">
                                        <i class="fa fa-sign-in"></i> 分党委审核（${partyApprovalCount}）
                                    </button>
                                </c:if>
                                <c:if test="${cls==6||cls==7}">
                                    <button id="odApprovalBtn" ${odApprovalCount>0?'':'disabled'}
                                            class="jqOpenViewBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/memberOut_approval"
                                            data-open-by="page"
                                            data-querystr="&type=2"
                                            data-need-id="false"
                                            data-count="${odApprovalCount}">
                                        <i class="fa fa-sign-in"></i> 组织部审核（${odApprovalCount}）
                                    </button>
                                </c:if>
                                <button class="jqOpenViewBtn btn btn-info btn-sm"
                                        data-url="${ctx}/applyApprovalLog_page"
                                        data-querystr="&type=${APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT}"
                                        data-open-by="page">
                                    <i class="fa fa-check-circle-o"></i> 查看审批记录
                                </button>
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
                                        <form class="form-horizontal " id="searchForm">
                                            <input type="hidden" name="cls" value="${cls}">
                                            <div class="row">
                                                <div class="col-xs-4">
                                                    <div class="form-group">
                                                        <label class="col-xs-3 control-label">当前状态</label>
                                                        <div class="col-xs-6">
                                                            <div class="input-group">
                                                                <select name="status" data-rel="select2" data-placeholder="请选择">
                                                                    <option></option>
                                                                    <c:forEach var="_status" items="${MEMBER_OUT_STATUS_MAP}">
                                                                        <c:if test="${_status.key>MEMBER_OUT_STATUS_BACK && _status.key<MEMBER_OUT_STATUS_OW_VERIFY}">
                                                                            <option value="${_status.key}">${_status.value}</option>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </select>
                                                                <script>
                                                                    $("#searchForm select[name=status]").val("${param.status}");
                                                                </script>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-xs-3 control-label">审核类别</label>
                                                        <div class="col-xs-6">
                                                            <div class="input-group">
                                                                <select name="isBack" data-rel="select2" data-placeholder="请选择">
                                                                    <option></option>
                                                                    <option value="0">新申请</option>
                                                                    <option value="1">返回修改</option>
                                                                </select>
                                                                <script>
                                                                    $("#searchForm select[name=isBack]").val("${param.isBack}");
                                                                </script>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label class="col-xs-3 control-label">姓名</label>

                                                        <div class="col-xs-6">
                                                            <div class="input-group">
                                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                                    <option value="${sysUser.id}">${sysUser.realname}</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label class="col-xs-3 control-label">类别</label>
                                                        <div class="col-xs-6">
                                                            <select required data-rel="select2" name="type" data-placeholder="请选择">
                                                                <option></option>
                                                                <c:forEach items="${MEMBER_INOUT_TYPE_MAP}" var="_type">
                                                                    <option value="${_type.key}">${_type.value}</option>
                                                                </c:forEach>
                                                            </select>
                                                            <script>
                                                                $("#searchForm select[name=type]").val(${param.type});
                                                            </script>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-xs-4">
                                                    <div class="form-group">
                                                        <label class="col-xs-3 control-label">分党委</label>

                                                        <div class="col-xs-6">
                                                            <select class="form-control" data-rel="select2-ajax"
                                                                    data-ajax-url="${ctx}/party_selects"
                                                                    name="partyId" data-placeholder="请选择分党委">
                                                                <option value="${party.id}">${party.name}</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                                        <label class="col-xs-4 control-label">党支部</label>

                                                        <div class="col-xs-6">
                                                            <select class="form-control" data-rel="select2-ajax"
                                                                    data-ajax-url="${ctx}/branch_selects"
                                                                    name="branchId" data-placeholder="请选择党支部">
                                                                <option value="${branch.id}">${branch.name}</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <script>
                                                    register_party_branch_select($("#searchForm"), "branchDiv",
                                                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                                </script>
                                            </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                                <c:if test="${_query || not empty param.sort}">&nbsp;
                                                    <button type="button" class="resetBtn btn btn-warning btn-sm"
                                                            data-querystr="cls=${cls}">
                                                        <i class="fa fa-reply"></i> 重置
                                                    </button>
                                                </c:if>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <div class="space-4"></div>
                            <table id="jqGrid" class="jqGrid table-striped"></table>
                            <div id="jqGridPager"></div>
                        </div></div></div>
            </div>
        </div>
        <div id="item-content">

        </div>
    </div>
</div>
<script>
    function goto_next(goToNext) {
        if (goToNext) {
            if ($("#next").hasClass("disabled") && $("#last").hasClass("disabled"))
                $(".closeView").click();
            else if (!$("#next").hasClass("disabled"))
                $("#next").click();
            else
                $("#last").click();
        }
    }
    function apply_deny(id, type, goToNext) {

        loadModal("${ctx}/memberOut_deny?id=" + id + "&type="+type +"&goToNext="+((goToNext!=undefined&&goToNext)?"1":"0"));
    }
    function apply_pass(id, type, goToNext) {
        bootbox.confirm("确定通过该申请？", function (result) {
            if (result) {
                $.post("${ctx}/memberOut_check", {ids: [id], type: type}, function (ret) {
                    if (ret.success) {
                        SysMsg.success('操作成功。', '成功', function () {
                            //page_reload();
                            goto_next(goToNext);
                        });
                    }
                });
            }
        });
    }

    $("#jqGrid").jqGrid({
        multiboxonly:false,
        ondblClickRow:function(){},
        url: '${ctx}/memberOut_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '学工号', name: 'user.code', width: 120,frozen: true},
            { label: '姓名', name: 'user.realname',resizable:false, width: 75, formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/member_view?userId={0}">{1}</a>'
                        .format(rowObject.userId, cellvalue);
            } ,frozen:true },
            {
                label: '所属组织机构', name: 'party', resizable: false, width: 450,
                formatter: function (cellvalue, options, rowObject) {
                    var party = rowObject.party;
                    var branch = rowObject.branch;
                    return party + (($.trim(branch) == '') ? '' : '-' + branch);
                }, frozen: true
            },
            {label: '类别', name: 'type', width: 50, formatter: function (cellvalue, options, rowObject) {
                return _cMap.MEMBER_INOUT_TYPE_MAP[cellvalue];
            }, frozen: true},
            {label: '状态', name: 'statusName', width: 100, formatter: function (cellvalue, options, rowObject) {
                return _cMap.MEMBER_OUT_STATUS_MAP[rowObject.status];
            }, frozen: true}<c:if test="${cls==4||cls==7}">
            ,{label: '返回修改原因', name: 'reason', width: 180}</c:if>,
             <shiro:hasAnyRoles name="admin,odAdmin,partyAdmin">
            { label: '打印', align:'center', width: 100, formatter:function(cellvalue, options, rowObject){

                if(rowObject.type=="${MEMBER_INOUT_TYPE_INSIDE}"){
                    var html = '<button class="openView btn btn-primary btn-xs"'
                            +' data-url="${ctx}/memberOut/printPreview?userId={0}"><i class="fa fa-print"></i> 打印介绍信</button>'
                                    .format(rowObject.userId);
                    return html;
                }
                if(rowObject.type=="${MEMBER_INOUT_TYPE_OUTSIDE}"){
                    var html = '<button class="openView btn btn-primary btn-xs"'
                            +' data-url="${ctx}/memberOut/printPreview?userId={0}"><i class="fa fa-print"></i> 介绍信套打</button>'
                                    .format(rowObject.userId);
                    return html;
                }
            }, frozen: true},
            </shiro:hasAnyRoles>
            {label: '转入单位', name: 'toUnit', width: 250},
            {label: '转入单位抬头', name: 'toTitle', width: 150},
            {label: '转出单位', name: 'fromUnit', width: 250},
            {label: '介绍信有效期天数', name: 'validDays', width: 150},
            {label: '办理时间', name: 'handleTime'},
             {hidden: true, name: 'status'}
        ],
        onSelectRow: function (id, status) {
            jgrid_sid = id;
            //console.log(id)
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#partyApprovalBtn,#odApprovalBtn").prop("disabled", true);
            } else if (ids.length==1) {
                jgrid_sid = ids[0];
                var rowData = $(this).getRowData(ids[0]);
                $("#partyApprovalBtn").prop("disabled", rowData.status != "${MEMBER_OUT_STATUS_APPLY}");
                $("#odApprovalBtn").prop("disabled", rowData.status != "${MEMBER_OUT_STATUS_PARTY_VERIFY}");
            } else {
                $("*[data-count]").each(function(){
                    $(this).prop("disabled", $(this).data("count") == 0);
                })
            }
        },
        onSelectAll: function (aRowids, status) {
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#partyApprovalBtn,#odApprovalBtn").prop("disabled", true);
            }else {
                $("*[data-count]").each(function () {
                    $(this).prop("disabled", $(this).data("count") == 0);
                })
            }
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    $("#jqGrid").navGrid('#jqGridPager',{refresh: false, edit:false,add:false,del:false,search:false});
    <c:if test="${cls==1||cls==4}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"分党委审核",
        btnbase:"jqBatchBtn btn btn-primary btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/memberOut_check" data-querystr="&type=1" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-page-reload="true"'
    });
    </c:if>
    <shiro:hasRole name="odAdmin">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"组织部审核",
        btnbase:"jqBatchBtn btn btn-warning btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/memberOut_check" data-querystr="&type=2" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-page-reload="true"'
    });
    </shiro:hasRole>
    <c:if test="${cls==1||cls==4||cls==6||cls==7}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"打回申请",
        btnbase:"jqOpenViewBatchBtn btn btn-danger btn-xs",
        buttonicon:"fa fa-reply-all",
        onClickButton: function(){
            var ids  = $(this).getGridParam("selarrrow");
            if(ids.length==0){
                SysMsg.warning("请选择行", "提示");
                return ;
            }
            var minStatus;
            for(var key in ids){
                var rowData = $(this).getRowData(ids[key]);
                if(minStatus==undefined || minStatus>rowData.status) minStatus = rowData.status;
            }

            loadModal("${ctx}/memberOut_back?ids[]={0}&status={1}".format(ids, minStatus))
        }
    });
    </c:if>

    $('[data-rel="select2"]').select2();
    register_user_select($('#searchForm select[name=userId]'));
</script>
