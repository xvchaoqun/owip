<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="myTableDiv"
                 data-url-au="${ctx}/memberIn_au"
                 data-url-page="${ctx}/memberIn_page"
                 data-url-export="${ctx}/memberIn_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId ||not empty param.type
                || not empty param.status ||not empty param.isBack
                || not empty param.fromUnit ||not empty param.fromTitle||not empty param._fromHandleTime
                ||not empty param.partyId ||not empty param.branchId || not empty param.code || not empty param.sort}"/>
                <div class="tabbable">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                        <li class="${cls==1?'active':''}">
                            <a ${cls!=1?'href="?cls=1"':''}><i class="fa fa-circle-o"></i> 待审核</a>
                        </li>
                        <li class="${cls==2?'active':''}">
                            <a ${cls!=2?'href="?cls=2"':''}><i class="fa fa-times"></i> 未通过</a>
                        </li>
                        <li class="${cls==3?'active':''}">
                            <a ${cls!=3?'href="?cls=3"':''}><i class="fa fa-check"></i> 已完成审批</a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div id="home4" class="tab-pane in active">
                <div class="jqgrid-vertical-offset buttons">
                    <shiro:hasPermission name="memberIn:edit">
                        <c:if test="${cls==1}">
                            <a href="javascript:;" class="openView btn btn-info btn-sm" data-url="${ctx}/memberIn_au">
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
                    <c:if test="${cls==1}">
                    <button id="partyApprovalBtn" ${partyApprovalCount>0?'':'disabled'}
                            class="jqOpenViewBtn btn btn-warning btn-sm"
                            data-url="${ctx}/memberIn_approval"
                            data-open-by="page"
                            data-querystr="&type=1"
                            data-need-id="false"
                            data-count="${partyApprovalCount}">
                        <i class="fa fa-sign-in"></i> 分党委审核（${partyApprovalCount}）
                    </button>
                        <shiro:hasRole name="odAdmin">
                    <button id="odApprovalBtn" ${odApprovalCount>0?'':'disabled'}
                            class="jqOpenViewBtn btn btn-danger btn-sm"
                            data-url="${ctx}/memberIn_approval"
                            data-open-by="page"
                            data-querystr="&type=2"
                            data-need-id="false"
                            data-count="${odApprovalCount}">
                        <i class="fa fa-sign-in"></i> 组织部审核（${odApprovalCount}）
                    </button>
                        </shiro:hasRole>
                        </c:if>
                    <button class="jqOpenViewBtn btn btn-info btn-sm"
                            data-url="${ctx}/applyApprovalLog_page"
                            data-querystr="&type=${APPLY_APPROVAL_LOG_TYPE_MEMBER_IN}"
                            data-open-by="page">
                        <i class="fa fa-sign-in"></i> 查看审批记录
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
                                        <option value="${party.id}">${party.name}</option>
                                    </select>
                                </div>
                                <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                    <label>党支部</label>
                                    <select class="form-control" data-rel="select2-ajax"
                                            data-ajax-url="${ctx}/branch_selects?auth=1"
                                            name="branchId" data-placeholder="请选择党支部">
                                        <option value="${branch.id}">${branch.name}</option>
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
                                    <label>转出单位</label>
                                    <input type="text" name="fromUnit" value="${param.fromUnit}">
                                </div>
                                <div class="form-group">
                                    <label>转出单位抬头</label>
                                    <input type="text" name="fromTitle" value="${param.fromTitle}">
                                </div>
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
                                            <c:forEach var="_status" items="${MEMBER_IN_STATUS_MAP}">
                                                <c:if test="${_status.key>MEMBER_IN_STATUS_BACK && _status.key<MEMBER_IN_STATUS_OW_VERIFY}">
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
                $(".closeView").click();
            else if (!$("#next").hasClass("disabled"))
                $("#next").click();
            else
                $("#last").click();
        }
    }
    function apply_deny(id, type, goToNext) {

        loadModal("${ctx}/memberIn_deny?id=" + id + "&type="+type +"&goToNext="+((goToNext!=undefined&&goToNext)?"1":"0"));
    }
    function apply_pass(id, type, goToNext) {
        bootbox.confirm("确定通过该申请？", function (result) {
            if (result) {
                $.post("${ctx}/memberIn_check", {ids: [id], type: type}, function (ret) {
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
        url: '${ctx}/memberIn_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '学工号', name: 'user.code', width: 120, frozen:true},
                <c:if test="${cls==3}">
            { label: '姓名', name: 'user.realname', width: 75, formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/member_view?userId={0}">{1}</a>'
                        .format(rowObject.userId, cellvalue);
            }, frozen:true  },
            </c:if>
<c:if test="${cls!=3}">
            {label: '姓名', name: 'user.realname', width: 75, frozen:true},
    </c:if>
            {
                label: '所属组织机构', name: 'party',  width: 450,
                formatter: function (cellvalue, options, rowObject) {
                    var party = rowObject.party;
                    var branch = rowObject.branch;
                    return party + (($.trim(branch) == '') ? '' : '-' + branch);
                }, frozen:true
            },
            {label: '类别', name: 'type', width: 50, formatter: function (cellvalue, options, rowObject) {
                return _cMap.MEMBER_INOUT_TYPE_MAP[cellvalue];
            }, frozen:true},
            {label: '转出单位', name: 'fromUnit', width: 150},
            {label: '转出单位抬头', name: 'fromTitle', width: 150},
            {label: '介绍信有效期天数', name: 'validDays', width: 150},
            {label: '转出办理时间', name: 'fromHandleTime', width: 150},
            {label: '状态', name: 'statusName', width: 150, formatter: function (cellvalue, options, rowObject) {
                return _cMap.MEMBER_IN_STATUS_MAP[rowObject.status];
            }}<c:if test="${cls==1}">
            ,{label: '审核类别', name: 'isBackName', width: 200, formatter: function (cellvalue, options, rowObject) {
                return rowObject.isBack?"返回修改":"新申请";
            }}</c:if>
            , {hidden: true, name: 'status'}
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
                $("#partyApprovalBtn").prop("disabled", rowData.status != "${MEMBER_IN_STATUS_APPLY}");
                $("#odApprovalBtn").prop("disabled", rowData.status != "${MEMBER_IN_STATUS_PARTY_VERIFY}");
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
    <c:if test="${cls==1}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"分党委批量审核",
        btnbase:"jqBatchBtn btn btn-primary btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/memberIn_check" data-querystr="&type=1" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-page-reload="true"'
    });
    <shiro:hasRole name="odAdmin">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"组织部批量审核",
        btnbase:"jqBatchBtn btn btn-warning btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/memberIn_check" data-querystr="&type=2" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-page-reload="true"'
    });
    </shiro:hasRole>

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

            loadModal("${ctx}/memberIn_back?ids[]={0}&status={1}".format(ids, minStatus))
        }
    });
    </c:if>

    $('[data-rel="select2"]').select2();
    register_user_select($('#searchForm select[name=userId]'));
</script>
