<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="myTableDiv"
                 data-url-au="${ctx}/memberOut_au"
                 data-url-page="${ctx}/memberOut"
                 data-url-export="${ctx}/memberOut_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId ||not empty param.type
                || not empty param.status ||not empty param.isBack||not empty param.isModify||not empty param.hasReceipt || not empty param.isPrint
                || not empty param.toUnit ||not empty param.toTitle||not empty param.fromUnit||not empty param._handleTime
                ||not empty param.partyId ||not empty param.branchId || not empty param.code || not empty param.sort}"/>
                <div class="tabbable">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                        <li class="dropdown <c:if test="${cls==1||cls==4||cls==5}">active</c:if>" >
                            <a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
                                <i class="fa fa-circle-o"></i> 分党委审核${cls==1?"(新申请)":(cls==4)?"(返回修改)":(cls==5)?"(已审核)":""}
                                <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-info" style="min-width: 100px">
                                <li>
                                    <a href="javascript:;" class="loadPage" data-url="${ctx}/memberOut?cls=1">新申请</a>
                                </li>
                                <li>
                                    <a href="javascript:;" class="loadPage" data-url="${ctx}/memberOut?cls=4">返回修改</a>
                                </li>
                                <li>
                                    <a href="javascript:;" class="loadPage" data-url="${ctx}/memberOut?cls=5">已审核</a>
                                </li>
                            </ul>
                        </li>
                        <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN}">
                            <li class="dropdown <c:if test="${cls==6||cls==7}">active</c:if>" >
                                <a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
                                    <i class="fa fa-circle-o"></i> 组织部审核${cls==6?"(新申请)":(cls==7)?"(返回修改)":""}
                                    <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
                                </a>
                                <ul class="dropdown-menu dropdown-info" style="min-width: 100px">
                                    <li>
                                        <a href="javascript:;" class="loadPage" data-url="${ctx}/memberOut?cls=6">新申请</a>
                                    </li>
                                    <li>
                                        <a href="javascript:;" class="loadPage" data-url="${ctx}/memberOut?cls=7">返回修改</a>
                                    </li>
                                </ul>
                            </li>

                        </shiro:hasAnyRoles>
                        <li class="${cls==2?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/memberOut?cls=2"}><i class="fa fa-times"></i> 未通过</a>
                        </li>
                        <li class="${cls==3?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/memberOut?cls=3"}><i class="fa fa-check"></i> 已完成审批</a>
                        </li>
                        <c:if test="${(cls==1 || cls==4||cls==6||cls==7) && (approvalCountNew+approvalCountBack)>0}">
                        <div class="pull-right"  style="top: 3px; right:10px; position: relative; color: red;  font-weight: bolder">
                            有${approvalCountNew+approvalCountBack}条待审核记录（其中新申请：共${approvalCountNew}条，返回修改：共${approvalCountBack}条）
                        </div>
                        </c:if>
                    </ul>
                    <div class="tab-content">
                        <div id="home4" class="tab-pane in active">
                            <div class="jqgrid-vertical-offset buttons">
                                <shiro:hasPermission name="memberOut:edit">
                                    <c:if test="${cls==1}">
                                    <a href="javascript:" class="openView btn btn-info btn-sm" data-url="${ctx}/memberOut_au">
                                        <i class="fa fa-plus"></i> 添加</a>
                                    </c:if>
                                    <c:if test="${cls!=2 &&cls!=5}">
                                    <button class="jqEditBtn btn btn-primary btn-sm"
                                            data-open-by="page">
                                        <i class="fa fa-edit"></i> 修改信息
                                    </button>
                                    </c:if>
                                    <c:if test="${cls==2}">
                                        <button class="jqEditBtn btn btn-primary btn-sm"
                                                data-querystr="&reapply=1"
                                                data-open-by="page">
                                            <i class="fa fa-edit"></i> 重新申请
                                        </button>
                                    </c:if>
                                </shiro:hasPermission>
                                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i
                                        class="fa fa-download"></i> 导出</a>
                                <c:if test="${cls==1||cls==4}">
                                    <button id="partyApprovalBtn" ${approvalCount>0?'':'disabled'}
                                            class="jqOpenViewBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/memberOut_approval"
                                            data-open-by="page"
                                            data-querystr="&type=1&cls=${cls}"
                                            data-need-id="false"
                                            data-count="${approvalCount}">
                                        <i class="fa fa-sign-in"></i> 分党委审核（${approvalCount}）
                                    </button>
                                </c:if>
                                <c:if test="${cls==6||cls==7}">
                                    <button id="odApprovalBtn" ${approvalCount>0?'':'disabled'}
                                            class="jqOpenViewBtn btn btn-danger btn-sm"
                                            data-url="${ctx}/memberOut_approval"
                                            data-open-by="page"
                                            data-querystr="&type=2&cls=${cls}"
                                            data-need-id="false"
                                            data-count="${approvalCount}">
                                        <i class="fa fa-sign-in"></i> 组织部审核（${approvalCount}）
                                    </button>
                                </c:if>
                                <button class="jqOpenViewBtn btn btn-info btn-sm"
                                        data-url="${ctx}/applyApprovalLog"
                                        data-querystr="&type=${APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT}"
                                        data-open-by="page">
                                    <i class="fa fa-check-circle-o"></i> 查看审批记录
                                </button>
                                    <c:if test="${cls==3}">
                                <button class="jqOpenViewBtn btn btn-danger btn-sm"
                                        data-url="${ctx}/memberOutModify"
                                        data-id-name="outId"
                                        data-open-by="page">
                                    <i class="fa fa-search"></i> 查看修改记录
                                </button>
                                </c:if>
                                <c:if test="${cls==3}">
                                <button class="jqOpenViewBatchBtn btn btn-primary btn-sm"
                                        data-url="${ctx}/memberOut/printPreview"
                                        data-querystr="&type=${MEMBER_INOUT_TYPE_INSIDE}"
                                        data-open-by="page">
                                    <i class="fa fa-print"></i> 批量打印介绍信
                                </button>
                                <button class="jqOpenViewBatchBtn btn btn-warning btn-sm"
                                        data-url="${ctx}/memberOut/printPreview"
                                        data-querystr="&type=${MEMBER_INOUT_TYPE_OUTSIDE}"
                                        data-open-by="page">
                                    <i class="fa fa-print"></i> 批量介绍信套打
                                </button>
                                <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN}">
                                    <button class="jqOpenViewBtn btn btn-danger btn-sm"
                                            data-url="${ctx}/memberOut_abolish">
                                        <i class="fa fa-reply"></i> 撤销
                                    </button>
                                </shiro:hasAnyRoles>
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

                                                    <div class="form-group">
                                                        <label>用户</label>
                                                            <div class="input-group">
                                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                                </select>
                                                            </div>
                                                    </div>
                                            <div class="form-group">
                                                <label>分党委</label>
                                                <select class="form-control" data-width="350" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/party_selects?auth=1"
                                                        name="partyId" data-placeholder="请选择分党委">
                                                    <option value="${party.id}" title="${party.isDeleted}">${party.name}</option>
                                                </select>
                                            </div>
                                            <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                                <label>党支部</label>
                                                <select class="form-control" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/branch_selects?auth=1"
                                                        name="branchId" data-placeholder="请选择党支部">
                                                    <option value="${branch.id}" title="${branch.isDeleted}">${branch.name}</option>
                                                </select>
                                            </div>
                                            <script>
                                                register_party_branch_select($("#searchForm"), "branchDiv",
                                                        '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                            </script>
                                                    <div class="form-group">
                                                        <label>类别</label>
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
                                            <div class="form-group">
                                                <label>转入单位</label>
                                                <input type="text" name="toUnit" value="${param.toUnit}">
                                            </div>
                                            <div class="form-group">
                                                <label>转入单位抬头</label>
                                                <input type="text" name="toTitle" value="${param.toTitle}">
                                            </div>
                                            <div class="form-group">
                                                <label>转出单位</label>
                                                <input type="text" name="fromUnit" value="${param.fromUnit}">
                                            </div>
                                            <div class="form-group">
                                                <label>办理时间</label>
                                                <div class="input-group tooltip-success" data-rel="tooltip" title="选择时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                    <input placeholder="请选择时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                                           type="text" name="_handleTime" value="${param._handleTime}"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>当前状态</label>
                                                <div class="input-group">
                                                    <select name="status" data-rel="select2" data-placeholder="请选择">
                                                        <option></option>
                                                        <c:forEach var="_status" items="${MEMBER_OUT_STATUS_MAP}">
                                                                <option value="${_status.key}">${_status.value}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <script>
                                                        $("#searchForm select[name=status]").val("${param.status}");
                                                    </script>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>审核类别</label>
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
                                            <c:if test="${cls==3}">
                                            <div class="form-group">
                                                <label>是否有回执</label>
                                                <div class="input-group">
                                                    <select name="hasReceipt" data-rel="select2" data-placeholder="请选择">
                                                        <option></option>
                                                        <option value="0">否</option>
                                                        <option value="1">是</option>
                                                    </select>
                                                    <script>
                                                        $("#searchForm select[name=hasReceipt]").val("${param.hasReceipt}");
                                                    </script>
                                                </div>
                                            </div>
                                                <div class="form-group">
                                                <label>是否修改</label>
                                                <div class="input-group">
                                                    <select name="isModify" data-rel="select2" data-placeholder="请选择">
                                                        <option></option>
                                                        <option value="0">否</option>
                                                        <option value="1">是</option>
                                                    </select>
                                                    <script>
                                                        $("#searchForm select[name=isModify]").val("${param.isModify}");
                                                    </script>
                                                </div>
                                            </div>
                                            </c:if>
                                            <c:if test="${cls==3}">
                                                <div class="form-group">
                                                    <label>是否已打印</label>
                                                    <div class="input-group">
                                                        <select name="isPrint" data-rel="select2" data-placeholder="请选择">
                                                            <option></option>
                                                            <option value="0">否</option>
                                                            <option value="1">是</option>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=isPrint]").val("${param.isPrint}");
                                                        </script>
                                                    </div>
                                                </div>
                                            </c:if>
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
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
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
    function apply_pass(btn, id, type, goToNext) {
        $(btn).attr("disabled", "disabled");
        $.post("${ctx}/memberOut_check", {ids: [id], type: type}, function (ret) {
            if (ret.success) {
                goto_next(goToNext);
                $(btn).removeAttr("disabled");
            }
        });
        /*bootbox.confirm("确定通过该申请？", function (result) {
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
        });*/
    }

    $("#jqGrid").jqGrid({
        multiboxonly:false,
        ondblClickRow:function(){},
        url: '${ctx}/memberOut_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '学工号', name: 'user.code', width: 120, frozen:true},
            { label: '姓名', name: 'user.realname',width: 75, formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/member_view?userId={0}">{1}</a>'
                        .format(rowObject.userId, cellvalue);
            }, frozen:true  },
            {
                label: '所属组织机构', name: 'party',  width: 450, align:'left',
                formatter: function (cellvalue, options, rowObject) {
                    return displayParty(rowObject.partyId, rowObject.branchId);
                }, frozen:true
            },
            {label: '类别', name: 'type', width: 50, formatter: function (cellvalue, options, rowObject) {
                return _cMap.MEMBER_INOUT_TYPE_MAP[cellvalue];
            }, frozen:true},
            {label: '状态', name: 'statusName', width: 120, formatter: function (cellvalue, options, rowObject) {
                return _cMap.MEMBER_OUT_STATUS_MAP[rowObject.status];
            }, frozen:true}<c:if test="${cls==4||cls==7}">
            ,{label: '返回修改原因', name: 'reason', width: 180}</c:if>,
            <c:if test="${cls==3}">
             <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN},${ROLE_PARTYADMIN}">
            { label: '打印', width: 100, formatter:function(cellvalue, options, rowObject){

                if(rowObject.type=="${MEMBER_INOUT_TYPE_INSIDE}"){
                    var html = '<button class="openView btn btn-primary btn-xs"'
                            +' data-url="${ctx}/memberOut/printPreview?type=${MEMBER_INOUT_TYPE_INSIDE}&ids[]={0}"><i class="fa fa-print"></i> 打印介绍信</button>'
                                    .format(rowObject.id);
                    return html;
                }
                if(rowObject.type=="${MEMBER_INOUT_TYPE_OUTSIDE}"){
                    var html = '<button class="openView btn btn-warning btn-xs"'
                            +' data-url="${ctx}/memberOut/printPreview?type=${MEMBER_INOUT_TYPE_OUTSIDE}&ids[]={0}"><i class="fa fa-print"></i> 介绍信套打</button>'
                                    .format(rowObject.id);
                    return html;
                }
            }},
            {label: '打印次数', name: 'printCount'},
            {label: '最近打印时间', width: 150, name: 'lastPrintTime'},
            {label: '最近打印人', name: 'lastPrintUser.realname'},
            </shiro:hasAnyRoles>
            </c:if>
            {label: '党员本人联系电话', name: 'phone', width: 180},
            {label: '转入单位', name: 'toUnit', width: 150},
            {label: '转入单位抬头', name: 'toTitle', width: 200},
            {label: '转出单位', name: 'fromUnit', width: 200},
            {label: '转出单位地址', name: 'fromAddress', width: 120},
            {label: '转出单位联系电话', name: 'fromPhone', width: 150},
            {label: '转出单位传真', name: 'fromFax', width: 120},
            {label: '转出单位邮编', name: 'fromPostCode', width: 120},
            {label: '党费缴纳至年月', name: 'payTime', width: 150,formatter: 'date', formatoptions: {newformat: 'Y-m'}},
            {label: '介绍信有效期天数', name: 'validDays', width: 150},
            {label: '办理时间', name: 'handleTime',formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '是否有回执', name: 'hasReceipt', formatter: function (cellvalue, options, rowObject) {
                return cellvalue?"是":"否"
            }},
            {label: '是否修改', name: 'isModify', formatter: function (cellvalue, options, rowObject) {
                return cellvalue?"是":"否"
            }},
            {label: '申请时间', name: 'applyTime', width: 150},
             {hidden: true, name: 'status'}
        ],
        onSelectRow: function (id, status) {
            saveJqgridSelected("#"+this.id, id, status);
            //console.log(id)
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#partyApprovalBtn,#odApprovalBtn").prop("disabled", true);
            } else if (ids.length==1) {

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

    _initNavGrid("jqGrid", "jqGridPager");
    <c:if test="${cls==1||cls==4}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"分党委批量审核",
        btnbase:"jqBatchBtn btn btn-primary btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/memberOut_check" data-querystr="&type=1" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });
    </c:if>
<c:if test="${cls==6||cls==7}">
    <shiro:hasRole name="${ROLE_ODADMIN}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"组织部批量审核",
        btnbase:"jqBatchBtn btn btn-warning btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/memberOut_check" data-querystr="&type=2" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });
    </shiro:hasRole>
    </c:if>
    <c:if test="${cls==1||cls==4||cls==6||cls==7}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"批量打回申请",
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
