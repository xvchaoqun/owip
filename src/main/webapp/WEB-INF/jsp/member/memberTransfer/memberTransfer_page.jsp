<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="myTableDiv"
                 data-url-au="${ctx}/memberTransfer_au"
                 data-url-page="${ctx}/memberTransfer"
                 data-url-export="${ctx}/memberTransfer_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId
                || not empty param.status ||not empty param.isBack||not empty param.toPartyId ||not empty param.toBranchId
                ||not empty param._fromHandleTime
                ||not empty param.partyId ||not empty param.branchId || not empty param.code || not empty param.sort}"/>
                <div class="tabbable">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                        <li class="${cls==1?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/memberTransfer?cls=1"}><i class="fa fa-circle-o"></i> 待审核</a>
                        </li>
                        <li class="${cls==2?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/memberTransfer?cls=2"}><i class="fa fa-times"></i> 未通过</a>
                        </li>
                        <li class="${cls==3?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/memberTransfer?cls=3"}><i class="fa fa-check"></i> 已完成审批</a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div id="home4" class="tab-pane in active">
                            <div class="jqgrid-vertical-offset buttons">
                                <shiro:hasPermission name="memberTransfer:edit">
                                    <c:if test="${cls==1}">
                                    <a href="javascript:" class="openView btn btn-info btn-sm" data-url="${ctx}/memberTransfer_au">
                                        <i class="fa fa-plus"></i> 添加</a>
                                    </c:if>
                                    <c:if test="${cls==1||cls==2}">
                                    <button id="editBtn" class="jqEditBtn btn btn-primary btn-sm"
                                            data-open-by="page">
                                        <i class="fa fa-edit"></i> 修改信息
                                    </button>
                                    </c:if>
                                </shiro:hasPermission>
                                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i
                                        class="fa fa-download"></i> 导出</a>
                                <c:if test="${cls==1}">
                                    <button id="partyApprovalBtn" ${partyApprovalCount>0?'':'disabled'}
                                            class="jqOpenViewBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/memberTransfer_approval"
                                            data-open-by="page"
                                            data-querystr="&type=1"
                                            data-need-id="false"
                                            data-count="${partyApprovalCount}">
                                        <i class="fa fa-check-circle-o"></i> 转出分党委审核（${partyApprovalCount}）
                                    </button>
                                        <button id="toPartyApprovalBtn" ${toPartyApprovalCount>0?'':'disabled'}
                                                class="jqOpenViewBtn btn btn-warning btn-sm"
                                                data-url="${ctx}/memberTransfer_approval"
                                                data-open-by="page"
                                                data-querystr="&type=2"
                                                data-need-id="false"
                                                data-count="${toPartyApprovalCount}">
                                            <i class="fa fa-check-circle-o"></i> 转入分党委审核（${toPartyApprovalCount}）
                                        </button>
                                </c:if>
                                <button class="jqOpenViewBtn btn btn-info btn-sm"
                                        data-url="${ctx}/applyApprovalLog"
                                        data-querystr="&type=${APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER}"
                                        data-open-by="page">
                                    <i class="fa fa-check-circle-o"></i> 查看审批记录
                                </button>
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
                                                                    data-ajax-url="${ctx}/party_selects"
                                                                    name="partyId" data-placeholder="请选择分党委">
                                                                <option value="${party.id}" title="${party.isDeleted}">${party.name}</option>
                                                            </select>
                                                    </div>
                                                    <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                                        <label>党支部</label>
                                                            <select class="form-control" data-rel="select2-ajax"
                                                                    data-ajax-url="${ctx}/branch_selects"
                                                                    name="branchId" data-placeholder="请选择党支部">
                                                                <option value="${branch.id}" title="${branch.isDeleted}">${branch.name}</option>
                                                            </select>
                                                    </div>
                                                <script>
                                                    $.register.party_branch_select($("#searchForm"), "branchDiv",
                                                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                                </script>

                                            <div class="form-group">
                                                <label>转入分党委</label>
                                                <select class="form-control" data-width="350" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/party_selects"
                                                        name="toPartyId" data-placeholder="请选择分党委">
                                                    <option value="${toParty.id}" title="${toParty.isDeleted}">${toParty.name}</option>
                                                </select>
                                            </div>
                                            <div class="form-group" style="${(empty toBranch)?'display: none':''}" id="toBranchDiv">
                                                <label>转入党支部</label>
                                                <select class="form-control" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/branch_selects"
                                                        name="toBranchId" data-placeholder="请选择党支部">
                                                    <option value="${toBranch.id}" title="${toBranch.isDeleted}">${toBranch.name}</option>
                                                </select>
                                            </div>
                                            <script>
                                                $.register.party_branch_select($("#searchForm"), "toBranchDiv",
                                                        '${cm:getMetaTypeByCode("mt_direct_branch").id}',
                                                        "${toParty.id}", "${toParty.classId}", "toPartyId", "toBranchId");
                                            </script>
                                            <div class="form-group">
                                                <label>转出办理时间</label>
                                                <div class="input-group tooltip-success" data-rel="tooltip" title="选择时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                    <input placeholder="请选择时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                                           type="text" name="_fromHandleTime" value="${param._fromHandleTime}"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>当前状态</label>
                                                <div class="input-group">
                                                    <select name="status" data-rel="select2" data-placeholder="请选择">
                                                        <option></option>
                                                        <c:forEach var="_status" items="${MEMBER_TRANSFER_STATUS_MAP}">
                                                            <c:if test="${_status.key>MEMBER_TRANSFER_STATUS_BACK && _status.key<MEMBER_TRANSFER_STATUS_TO_VERIFY}">
                                                                <option value="${_status.key}">${_status.value}</option>
                                                            </c:if>
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
                $.hashchange();
            else if (!$("#next").hasClass("disabled"))
                $("#next").click();
            else
                $("#last").click();
        }
    }
    function apply_deny(id, type, goToNext) {

        $.loadModal("${ctx}/memberTransfer_deny?id=" + id + "&type="+type +"&goToNext="+((goToNext!=undefined&&goToNext)?"1":"0"));
    }
    function apply_pass(id, type, goToNext) {
        bootbox.confirm("确定通过该申请？", function (result) {
            if (result) {
                $.post("${ctx}/memberTransfer_check", {ids: [id], type: type}, function (ret) {
                    if (ret.success) {
                        //SysMsg.success('操作成功。', '成功', function () {
                            //page_reload();
                            goto_next(goToNext);
                        //});
                    }
                });
            }
        });
    }
    $("#jqGrid").jqGrid({
        multiboxonly:false,
        ondblClickRow:function(){},
        url: '${ctx}/memberTransfer_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '学工号', name: 'user.code', width: 120,frozen:true},
            {label: '类别', name: 'user.type',frozen:true, formatter: function (cellvalue, options, rowObject) {
                return _cMap.USER_TYPE_MAP[cellvalue];
            }},
            { label: '姓名', name: 'user.realname', width: 75, formatter:function(cellvalue, options, rowObject){
                return $.member(rowObject.userId, cellvalue);
            },frozen:true  },
            {
                label: '所属组织机构', name: 'from',  width: 450, align:'left',
                formatter: function (cellvalue, options, rowObject) {
                    return $.party(rowObject.partyId, rowObject.branchId);
                },frozen:true
            },
            {label: '转入组织机构', name: 'to', width: 450, align:'left',
                formatter: function (cellvalue, options, rowObject) {
                    return $.party(rowObject.toPartyId, rowObject.toBranchId);
                }
            },
            {label: '转出办理时间', name: 'fromHandleTime', width: 150, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '状态', name: 'statusName', width: 200, formatter: function (cellvalue, options, rowObject) {
                return _cMap.MEMBER_TRANSFER_STATUS_MAP[rowObject.status];
            }}<c:if test="${cls==1}">
            ,{label: '审核类别', name: 'isBackName', width: 200, formatter: function (cellvalue, options, rowObject) {
                return rowObject.isBack?"返回修改":"新申请";
            }}</c:if>, {hidden: true, name: 'status'}
        ],
        onSelectRow: function (id, status) {
            saveJqgridSelected("#"+this.id);
            //console.log(id)
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#partyApprovalBtn,#toPartyApprovalBtn").prop("disabled", true);
            } else if (ids.length==1) {

                var rowData = $(this).getRowData(ids[0]);
                $("#partyApprovalBtn").prop("disabled", rowData.status != "${MEMBER_TRANSFER_STATUS_APPLY}");
                $("#toPartyApprovalBtn").prop("disabled", rowData.status != "${MEMBER_TRANSFER_STATUS_FROM_VERIFY}");
            } else {
                $("*[data-count]").each(function(){
                    $(this).prop("disabled", $(this).data("count") == 0);
                })
            }
        },
        onSelectAll: function (aRowids, status) {
            saveJqgridSelected("#" + this.id);
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#partyApprovalBtn,#toPartyApprovalBtn").prop("disabled", true);
            }else {
                $("*[data-count]").each(function () {
                    $(this).prop("disabled", $(this).data("count") == 0);
                })
            }
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');


    $.initNavGrid("jqGrid", "jqGridPager");
    <c:if test="${cls==1}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"转出分党委批量审核",
        btnbase:"jqBatchBtn btn btn-primary btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/memberTransfer_check" data-querystr="&type=1" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });

    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"转入分党委批量审核",
        btnbase:"jqBatchBtn btn btn-warning btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/memberTransfer_check" data-querystr="&type=2" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });

    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"批量打回申请",
        btnbase:"btn btn-danger btn-xs",
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

            $.loadModal("${ctx}/memberTransfer_back?ids[]={0}&status={1}".format(ids, minStatus))
        }
    });
    </c:if>

    $('[data-rel="select2"]').select2();
    $.register.user_select($('#searchForm select[name=userId]'));
</script>
