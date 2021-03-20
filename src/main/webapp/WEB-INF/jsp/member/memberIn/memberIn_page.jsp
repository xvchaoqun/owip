<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_IN_STATUS_BACK" value="<%=MemberConstants.MEMBER_IN_STATUS_BACK%>"/>
<c:set var="MEMBER_IN_STATUS_OW_VERIFY" value="<%=MemberConstants.MEMBER_IN_STATUS_OW_VERIFY%>"/>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
            <div class="myTableDiv"
                 data-url-au="${ctx}/memberIn_au"
                 data-url-page="${ctx}/memberIn"
                 data-url-export="${ctx}/memberIn_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId ||not empty param.type
                || not empty param.status ||not empty param.isBack||not empty param.isModify
                || not empty param.fromUnit ||not empty param.fromTitle||not empty param.fromHandleTime||not empty param.handleTime
                ||not empty param.partyId ||not empty param.branchId || not empty param.code || not empty param.sort}"/>
                <div class="tabbable">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                        <li class="${cls==1?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/memberIn?cls=1"}><i class="fa fa-circle-o"></i> ${_p_partyName}审核</a>
                        </li>
                        <li class="${cls==4?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/memberIn?cls=4"}><i class="fa fa-circle-o"></i> 组织部审核</a>
                        </li>
                        <li class="${cls==2?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/memberIn?cls=2"}><i class="fa fa-times"></i> 未通过/已撤销</a>
                        </li>
                        <li class="${cls==3?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/memberIn?cls=3"}><i class="fa fa-check"></i> 已完成审批</a>
                        </li>
                        <div class="buttons pull-left" style="margin-left: 25px">
                            <shiro:hasPermission name="memberIn:edit">
                                <a href="javascript:;" class="openView btn btn-info btn-sm" data-url="${ctx}/memberIn_au">
                                    <i class="fa fa-plus"></i> 添加</a>
                                <button class="popupBtn btn btn-success btn-sm tooltip-primary" data-url="/memberIn_import" data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                                    批量导入</button>
                            </shiro:hasPermission>
                        </div>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane in active">
                <div class="jqgrid-vertical-offset buttons">
                    <shiro:hasPermission name="memberIn:edit">
                        <button id="editBtn" class="jqEditBtn btn btn-primary btn-sm"
                                data-open-by="page">
                            <i class="fa fa-edit"></i> ${cls==2?"重新申请":"修改信息"}
                        </button>
                    </shiro:hasPermission>
                    <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i
                            class="fa fa-download"></i> 导出</a>
                    <c:if test="${cls==1}">
                        <c:if test="${cm:isPermitted(PERMISSION_PARTYVIEWALL) || cm:hasRole(ROLE_PARTYADMIN)}">
                    <button id="partyApprovalBtn" ${partyApprovalCount>0?'':'disabled'}
                            class="jqOpenViewBtn btn btn-warning btn-sm"
                            data-url="${ctx}/memberIn_approval"
                            data-open-by="page"
                            data-querystr="&type=1"
                            data-need-id="false"
                            data-count="${partyApprovalCount}">
                        <i class="fa fa-sign-in"></i> ${_p_partyName}审核（${partyApprovalCount}）
                    </button>
                        </c:if>
                    </c:if>
                        <c:if test="${cls==4}">
                        <shiro:hasRole name="${ROLE_ODADMIN}">
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
                            data-url="${ctx}/applyApprovalLog"
                            data-querystr="&type=<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_IN%>"
                            data-open-by="page">
                        <i class="fa fa-sign-in"></i> 查看审批记录
                    </button>
                    <c:if test="${cls==3}">
                        <button class="jqOpenViewBtn btn btn-danger btn-sm"
                                data-url="${ctx}/memberInModify"
                                data-id-name="inId"
                                data-open-by="page">
                            <i class="fa fa-search"></i> 查看修改记录
                        </button>
                    </c:if>
                </div>
                <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                    <div class="widget-header">
                        <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

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
                                    <label>${_p_partyName}</label>
                                    <select class="form-control" data-width="350" data-rel="select2-ajax"
                                            data-ajax-url="${ctx}/party_selects?auth=1"
                                            name="partyId" data-placeholder="请选择">
                                        <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                    </select>
                                </div>
                                <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                    <label>党支部</label>
                                    <select class="form-control" data-rel="select2-ajax"
                                            data-ajax-url="${ctx}/branch_selects?auth=1"
                                            name="branchId" data-placeholder="请选择党支部">
                                        <option value="${branch.id}" delete="${branch.isDeleted}">${branch.name}</option>
                                    </select>
                                </div>
                                <script>
                                    $.register.party_branch_select($("#searchForm"), "branchDiv",
                                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                </script>
                                        <div class="form-group">
                                            <label>类别</label>
                                                <select data-rel="select2" name="type" data-width="120" data-placeholder="请选择">
                                                    <option></option>
                                                    <c:import url="/metaTypes?__code=mc_member_in_out_type"/>
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
                                               type="text" name="fromHandleTime" value="${param.fromHandleTime}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>转入办理时间</label>
                                    <div class="input-group tooltip-success" data-rel="tooltip" title="选择时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                        <input placeholder="请选择时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                               type="text" name="handleTime" value="${param.handleTime}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>当前状态</label>
                                    <div class="input-group">
                                        <select name="status" data-rel="select2" data-placeholder="请选择">
                                            <option></option>
                                            <c:forEach var="_status" items="<%=MemberConstants.MEMBER_IN_STATUS_MAP%>">
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
                                    <c:if test="${cls==3}">
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
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                    <c:if test="${_query || not empty param.sort}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
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
        <div id="body-content-view">

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
        }else{
            page_reload();
        }
    }
    function apply_deny(id, type, goToNext) {

        $.loadModal("${ctx}/memberIn_deny?id=" + id + "&type="+type +"&goToNext="+((goToNext!=undefined&&goToNext)?"1":"0"));
    }
    function apply_pass(id, type, goToNext) {
        if(type==1){
            $.loadModal("${ctx}/memberIn_party_check?ids="+id + "&type=" + type +"&goToNext="+((goToNext!=undefined&&goToNext)?"1":"0"))
        } else if(type==2){ // 组织部审核通过
            $.post("${ctx}/memberIn_check", {ids: [id]}, function (ret) {
                if (ret.success) {
                    goto_next(goToNext);
                }
            });
        }
    }

    $("#jqGrid").jqGrid({
        /*multiboxonly:false,*/
        ondblClickRow:function(){},
        url: '${ctx}/memberIn_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '介绍信抬头', name: 'fromTitle', width: 250, align:'left', frozen:true},

            {label: '学工号', name: 'user.code', width: 120, frozen:true},
                <c:if test="${cls==3}">
            { label: '姓名', name: 'user.realname', width: 75, formatter:function(cellvalue, options, rowObject){
                return $.member(rowObject.userId, cellvalue);
            }, frozen:true  },
            </c:if>
        <c:if test="${cls!=3}">
            {label: '姓名', name: 'user.realname', width: 75, frozen:true},
        </c:if>
            {label: '介绍信有效期天数', name: 'validDays', width: 140},
    <c:if test="${cls==3}">
            {label: '是否有回执', name: 'hasReceipt', formatter:$.jgrid.formatter.TRUEFALSE},
            </c:if>
            {
                label: '转入组织机构', name: 'party',  width: 450, align:'left',
                formatter: function (cellvalue, options, rowObject) {
                    return $.party(rowObject.partyId, rowObject.branchId);
                }
            },
            {label: '类别', name: 'type', width: 80, formatter: $.jgrid.formatter.MetaType},
            {label: '转出单位', name: 'fromUnit', width: 200, align:'left'},
            {label: '转出单位地址', name: 'fromAddress', width: 350, align:'left'},
            {label: '转出单位联系电话', name: 'fromPhone', width: 150},
            {label: '转出单位传真', name: 'fromFax', width: 120},
            {label: '转出单位邮编', name: 'fromPostCode', width: 120},
            {label: '转出办理时间', name: 'fromHandleTime', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '转入办理时间', name: 'handleTime', width: 120,formatter:$.jgrid.formatter.date,formatoptions: {newformat:'Y.m.d'}},
            {label: '提交书面申请书时间', name: 'applyTime', width: 160,formatter:$.jgrid.formatter.date,formatoptions: {newformat:'Y.m.d'}},
            {label: '确定为入党积极分子时间', name: 'activeTime', width: 180,formatter:$.jgrid.formatter.date,formatoptions: {newformat:'Y.m.d'}},
            {label: '确定为发展对象时间', name: 'candidateTime', width: 160,formatter:$.jgrid.formatter.date,formatoptions: {newformat:'Y.m.d'}},
            {label: '入党时间', name: 'growTime',formatter:$.jgrid.formatter.date,formatoptions: {newformat:'Y.m.d'}},
            {label: '转正时间', name: 'positiveTime',formatter:$.jgrid.formatter.date,formatoptions: {newformat:'Y.m.d'}},
            {label: '状态', name: 'statusName', width: 150, formatter: function (cellvalue, options, rowObject) {
                return _cMap.MEMBER_IN_STATUS_MAP[rowObject.status];
            }}<c:if test="${cls==1}">
            ,{label: '审核类别', name: 'isBackName', width: 200, formatter: function (cellvalue, options, rowObject) {
                return rowObject.isBack?"返回修改":"新申请";
            }}</c:if>
            , {hidden: true, name: 'status'}
        ],
        onSelectRow: function (id, status) {
            saveJqgridSelected("#"+this.id);
            //console.log(id)
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#partyApprovalBtn,#odApprovalBtn").prop("disabled", true);
            } else if (ids.length==1) {
                var rowData = $(this).getRowData(ids[0]);
                $("#partyApprovalBtn").prop("disabled", rowData.status != "<%=MemberConstants.MEMBER_IN_STATUS_APPLY%>");
                $("#odApprovalBtn").prop("disabled", rowData.status != "<%=MemberConstants.MEMBER_IN_STATUS_PARTY_VERIFY%>");
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
                $("#partyApprovalBtn,#odApprovalBtn").prop("disabled", true);
            }else {
                $("*[data-count]").each(function () {
                    $(this).prop("disabled", $(this).data("count") == 0);
                })
            }
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    $.initNavGrid("jqGrid", "jqGridPager");

    <c:if test="${cm:isPermitted(PERMISSION_PARTYVIEWALL) || cm:hasRole(ROLE_PARTYADMIN)}">
        <c:if test="${cls==1}">
        $("#jqGrid").navButtonAdd('#jqGridPager',{
            caption:"${_p_partyName}批量审核",
            btnbase:"jqBatchBtn btn btn-primary btn-xs",
            buttonicon:"fa fa-check-circle-o",
            onClickButton: function(){
                var ids  = $(this).getGridParam("selarrrow");
                if(ids.length==0){
                    SysMsg.warning("请选择行", "提示");
                    return ;
                }

                $.loadModal("${ctx}/memberIn_party_check?ids={0}".format(ids))
            }
        });
        </c:if>
        <c:if test="${cls==4}">
        <shiro:hasRole name="${ROLE_ODADMIN}">
        $("#jqGrid").navButtonAdd('#jqGridPager',{
            caption:"组织部批量审核",
            btnbase:"jqBatchBtn btn btn-warning btn-xs",
            buttonicon:"fa fa-check-circle-o",
            props:'data-url="${ctx}/memberIn_check" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
        });
        </shiro:hasRole>
        </c:if>
        <c:if test="${cls==1}">
        $("#jqGrid").navButtonAdd('#jqGridPager',{
            caption:"批量退回申请",
            btnbase:"btn btn-danger btn-xs",
            buttonicon:"fa fa-reply-all",
            onClickButton: function(){

                var $this = $(this);
                var ids  = $this.getGridParam("selarrrow");
                if(ids.length==0){
                    SysMsg.warning("请选择行", "提示");
                    return ;
                }
                var minStatus;
                $.each(ids, function (i, id) {
                    var rowData = $this.getRowData(id);
                    if(minStatus==undefined || minStatus>rowData.status) minStatus = rowData.status;
                })

                $.loadModal("${ctx}/memberIn_back?ids={0}&status={1}".format(ids, minStatus))
            }
        });
        </c:if>
        <c:if test="${cls==4}">
        <shiro:hasRole name="${ROLE_ODADMIN}">
        $("#jqGrid").navButtonAdd('#jqGridPager',{
            caption:"批量退回申请",
            btnbase:"btn btn-danger btn-xs",
            buttonicon:"fa fa-reply-all",
            onClickButton: function(){

                var $this = $(this);
                var ids  = $this.getGridParam("selarrrow");
                if(ids.length==0){
                    SysMsg.warning("请选择行", "提示");
                    return ;
                }
                var minStatus;
                $.each(ids, function (i, id) {
                    var rowData = $this.getRowData(id);
                    if(minStatus==undefined || minStatus>rowData.status) minStatus = rowData.status;
                })

                $.loadModal("${ctx}/memberIn_back?ids={0}&status={1}".format(ids, minStatus))
            }
        });
        </shiro:hasRole>
        </c:if>
    </c:if>
    $('[data-rel="select2"]').select2();
    $.register.user_select($('#searchForm select[name=userId]'));
</script>
