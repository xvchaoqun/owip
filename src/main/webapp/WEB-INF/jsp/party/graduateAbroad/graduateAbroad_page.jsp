<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="myTableDiv"
                 data-url-au="${ctx}/graduateAbroad_au"
                 data-url-page="${ctx}/graduateAbroad"
                 data-url-export="${ctx}/graduateAbroad_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId ||not empty param.partyId 
                || not empty param.status ||not empty param.isBack
                || not empty param.country ||not empty param._abroadTime|| not empty param._returnTime
                 ||not empty param._payTime||not empty param.mobile
                ||not empty param.branchId || not empty param.code || not empty param.sort}"/>
                <div class="tabbable">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                        <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN},${ROLE_PARTYADMIN},${ROLE_BRANCHADMIN}">
                        <li class="dropdown <c:if test="${cls==1||cls==11||cls==12}">active</c:if>" >
                            <a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
                                <i class="fa fa-circle-o"></i> 支部审核${cls==1?"(新申请)":(cls==11)?"(返回修改)":(cls==12)?"(已审核)":""}
                                <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-info" style="min-width: 100px">
                                <li>
                                    <a href="javascript:;" class="loadPage" data-url="${ctx}/graduateAbroad?cls=1">新申请</a>
                                </li>
                                <li>
                                    <a href="javascript:;" class="loadPage" data-url="${ctx}/graduateAbroad?cls=11">返回修改</a>
                                </li>
                                <li>
                                    <a href="javascript:;" class="loadPage" data-url="${ctx}/graduateAbroad?cls=12">已审核</a>
                                </li>
                            </ul>
                        </li>
                        </shiro:hasAnyRoles>
                        <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN},${ROLE_PARTYADMIN}">
                        <li class="dropdown <c:if test="${cls==2||cls==21||cls==22}">active</c:if>" >
                            <a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
                                <i class="fa fa-circle-o"></i> 分党委审核${cls==2?"(新申请)":(cls==21)?"(返回修改)":(cls==22)?"(已审核)":""}
                                <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-info" style="min-width: 100px">
                                <li>
                                    <a href="javascript:;" class="loadPage" data-url="${ctx}/graduateAbroad?cls=2">新申请</a>
                                </li>
                                <li>
                                    <a href="javascript:;" class="loadPage" data-url="${ctx}/graduateAbroad?cls=21">返回修改</a>
                                </li>
                                <li>
                                    <a href="javascript:;" class="loadPage" data-url="${ctx}/graduateAbroad?cls=22">已审核</a>
                                </li>
                            </ul>
                        </li>
                        </shiro:hasAnyRoles>
                        <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN}">
                        <li class="dropdown <c:if test="${cls==3||cls==31}">active</c:if>" >
                            <a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
                                <i class="fa fa-circle-o"></i> 组织部审核${cls==3?"(新申请)":(cls==31)?"(返回修改)":""}
                                <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-info" style="min-width: 100px">
                                <li>
                                    <a href="javascript:;" class="loadPage" data-url="${ctx}/graduateAbroad?cls=3">新申请</a>
                                </li>
                                <li>
                                    <a href="javascript:;" class="loadPage" data-url="${ctx}/graduateAbroad?cls=31">返回修改</a>
                                </li>
                            </ul>
                        </li>
                        </shiro:hasAnyRoles>
                        <%--<li class="${cls==1?'active':''}">
                            <a ${cls!=1?'href="?cls=1"':''}><i class="fa fa-circle-o"></i> 支部审核（${branchApprovalCount}）</a>
                        </li>
                        <li class="${cls==11?'active':''}">
                            <a ${cls!=11?'href="?cls=11"':''}><i class="fa fa-circle-o"></i> 分党委审核（${partyApprovalCount}）</a>
                        </li>
                            <li class="${cls==12?'active':''}">
                                <a ${cls!=12?'href="?cls=12"':''}><i class="fa fa-circle-o"></i> 组织部审核（${odApprovalCount}）</a>
                            </li>--%>
                        <li class="${cls==4?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/graduateAbroad?cls=4"}><i class="fa fa-times"></i> 未通过</a>
                        </li>

                        <li class="dropdown <c:if test="${cls==5||cls==6}">active</c:if>" >
                            <a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
                                <i class="fa fa-sign-in"></i> 已完成审批${cls==5?"(未转出)":(cls==6)?"(已转出)":""}
                                <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-info" style="min-width: 100px">
                                <li>
                                    <a href="javascript:;" class="loadPage" data-url="${ctx}/graduateAbroad?cls=5">现有暂留的党员</a>
                                </li>
                                <li>
                                    <a href="javascript:;" class="loadPage" data-url="${ctx}/graduateAbroad?cls=6">已转出的暂留党员</a>
                                </li>
                            </ul>
                        </li>

                        <c:if test="${(cls==1||cls==11||cls==2||cls==21||cls==3||cls==31) && (approvalCountNew+approvalCountBack)>0}">
                            <div class="pull-right"  style="top: 3px; right:10px; position: relative; color: red;  font-weight: bolder">
                                有${approvalCountNew+approvalCountBack}条待审核记录（其中新申请：共${approvalCountNew}条，返回修改：共${approvalCountBack}条）
                            </div>
                        </c:if>
                    </ul>
                    <div class="tab-content">
                        <div id="home4" class="tab-pane in active">
                            <div class="jqgrid-vertical-offset buttons">
                                <shiro:hasPermission name="graduateAbroad:edit">
                                    <c:if test="${cls==1}">
                                    <a href="javascript:" class="openView btn btn-info btn-sm" data-url="${ctx}/graduateAbroad_au">
                                        <i class="fa fa-plus"></i> 添加</a>
                                    </c:if>
                                    <c:if test="${cls==1||cls==4}">
                                    <button id="editBtn" class="jqEditBtn btn btn-primary btn-sm"
                                            data-open-by="page">
                                        <i class="fa fa-edit"></i> 修改信息
                                    </button>
                                    </c:if>
                                </shiro:hasPermission>
                                <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN},${ROLE_PARTYADMIN}">
                                <c:if test="${cls==5||cls==6}">
                                    <button class="jqOpenViewBtn btn btn-danger btn-sm"
                                            data-url="${ctx}/graduateAbroad_transfer_au">
                                        <i class="fa fa-edit"></i> 修改暂留党支部
                                    </button>
                                </c:if>
                                </shiro:hasAnyRoles>
                                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i
                                        class="fa fa-download"></i> 导出</a>

                                <c:if test="${cls==1||cls==11}">
                                    <button id="branchApprovalBtn" ${approvalCount>0?'':'disabled'}
                                            class="jqOpenViewBtn btn btn-success btn-sm"
                                            data-url="${ctx}/graduateAbroad_approval"
                                            data-open-by="page"
                                            data-querystr="&type=1&cls=${cls}"
                                            data-need-id="false"
                                            data-count="${approvalCount}">
                                        <i class="fa fa-sign-in"></i> 党支部审核（${approvalCount}）
                                    </button>
                                </c:if>

                                <c:if test="${cls==2||cls==21}">
                                    <button id="partyApprovalBtn" ${approvalCount>0?'':'disabled'}
                                            class="jqOpenViewBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/graduateAbroad_approval"
                                            data-open-by="page"
                                            data-querystr="&type=2&cls=${cls}"
                                            data-need-id="false"
                                            data-count="${approvalCount}">
                                        <i class="fa fa-sign-in"></i> 分党委审核（${approvalCount}）
                                    </button>
                                </c:if>

                                <c:if test="${cls==3||cls==31}">
                                    <button id="odApprovalBtn" ${approvalCount>0?'':'disabled'}
                                            class="jqOpenViewBtn btn btn-danger btn-sm"
                                            data-url="${ctx}/graduateAbroad_approval"
                                            data-open-by="page"
                                            data-querystr="&type=3&cls=${cls}"
                                            data-need-id="false"
                                            data-count="${approvalCount}">
                                        <i class="fa fa-sign-in"></i> 组织部审核（${approvalCount}）
                                    </button>
                                </c:if>

                                <button class="jqOpenViewBtn btn btn-info btn-sm"
                                        data-url="${ctx}/applyApprovalLog"
                                        data-querystr="&type=${APPLY_APPROVAL_LOG_TYPE_GRADUATE_ABROAD}"
                                        data-open-by="page">
                                    <i class="fa fa-check-circle-o"></i> 查看审批记录
                                </button>

                                <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN}">
                                    <button class="jqBatchBtn btn btn-danger btn-sm"
                                       data-title="删除"
                                       data-msg="确定删除这{0}条记录吗？"
                                       data-url="${ctx}/graduateAbroad_batchDel">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                </shiro:hasAnyRoles>
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
                                                <label>去往国家</label>
                                                <input type="text" name="country" value="${param.country}">
                                            </div>
                                            <div class="form-group">
                                                <label>出国时间</label>
                                                <div class="input-group tooltip-success" data-rel="tooltip" title="选择时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                    <input placeholder="请选择时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                                           type="text" name="_abroadTime" value="${param._abroadTime}"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>预计回国时间</label>
                                                <div class="input-group tooltip-success" data-rel="tooltip" title="选择时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                    <input placeholder="请选择时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                                           type="text" name="_returnTime" value="${param._returnTime}"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>党费缴纳至年月</label>
                                                <div class="input-group tooltip-success" data-rel="tooltip" title="选择时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                    <input placeholder="请选择时间范围" data-rel="date-range-picker"
                                                           class="form-control date-range-picker"
                                                           type="text" name="_payTime" value="${param._payTime}"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>手机号码</label>
                                                <input type="text" name="mobile" value="${param.mobile}">
                                            </div>

                                            <div class="form-group">
                                                <label>当前状态</label>
                                                <div class="input-group">
                                                    <select name="status" data-rel="select2" data-placeholder="请选择">
                                                        <option></option>
                                                        <c:forEach var="_status" items="${GRADUATE_ABROAD_STATUS_MAP}">
                                                            <c:if test="${_status.key>GRADUATE_ABROAD_STATUS_BACK && _status.key<GRADUATE_ABROAD_STATUS_OW_VERIFY}">
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
        }else{
            page_reload();
        }
    }
    function apply_deny(id, type, goToNext) {

        loadModal("${ctx}/graduateAbroad_deny?id=" + id + "&type="+type +"&goToNext="+((goToNext!=undefined&&goToNext)?"1":"0"));
    }
    function apply_pass(id, type, goToNext) {
        if(type==2){
            loadModal("${ctx}/graduateAbroad_transfer?ids[]={0}".format(id) +"&goToNext="+((goToNext!=undefined&&goToNext)?"1":"0"))
        }else{
           // bootbox.confirm("确定通过该申请？", function (result) {
             //   if (result) {
                    $.post("${ctx}/graduateAbroad_check", {ids: [id], type: type}, function (ret) {
                        if (ret.success) {
                            //SysMsg.success('操作成功。', '成功', function () {
                                //page_reload();
                                goto_next(goToNext);
                           // });
                        }
                    });
                //}
            //});
        }
    }

    $("#jqGrid").jqGrid({
        multiboxonly:false,
        ondblClickRow:function(){},
        url: '${ctx}/graduateAbroad_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '学工号', name: 'user.code', width: 120, frozen:true},
            { label: '姓名', name: 'user.realname', width: 75, formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/member_view?userId={0}">{1}</a>'
                        .format(rowObject.userId, cellvalue);
            }, frozen:true  },
            {
                label: '所属组织机构', name: 'party',  width: 450, align:'left',
                formatter: function (cellvalue, options, rowObject) {
                    return $.displayParty(rowObject.partyId, rowObject.branchId);
                }, frozen:true
            },
            <c:if test="${cls==22||cls==3||cls==31||cls==5||cls==6}">
            {label: '暂留后所在党支部', name: 'toBranch', width: 250,formatter:function(cellvalue, options, rowObject){
                return ($.trim(rowObject.branch) == '')? '-':$.trim(cellvalue);
            }},
            {label: '原党支部负责人', name: 'orgBranchAdmin.realname', width: 250,formatter:function(cellvalue, options, rowObject){
                return ($.trim(rowObject.branch) == '')? '-':$.trim(cellvalue);
            }},
            {label: '原党支部负责人联系电话', name: 'orgBranchAdminPhone', width: 180,formatter:function(cellvalue, options, rowObject){
                return ($.trim(rowObject.branch) == '')? '-': $.trim(cellvalue);
            }},
            </c:if>
            {label: '人员类别', name: 'userType', formatter: $.jgrid.formatter.MetaType},
            {label: '出国原因', name: 'abroadReason', width: 250, formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '';
                return cellvalue.replace(/\+\+\+/g, ',');
            }},
            {label: '手机号码', name: 'mobile'},
            {label: '家庭电话', name: 'phone'},
            {label: '微信', name: 'weixin'},
            {label: '电子邮箱', name: 'email', width: 200},
            {label: 'QQ号', name: 'qq'},
            {label: '国内通讯地址', name: 'inAddress', width: 200},
            {label: '国外通讯地址', name: 'outAddress', width: 200},
            {label: '国内第一联系人', name: 'name1', width: 150},
            {label: '与本人关系', name: 'relate1'},
            {label: '单位', name: 'unit1', width: 200},
            {label: '职务', name: 'post1'},
            {label: '办公电话', name: 'phone1'},
            {label: '手机号', name: 'mobile1'},
            {label: '电子邮箱', name: 'email1', width: 200},
            {label: '国内第二联系人', name: 'name2', width: 150},
            {label: '与本人关系', name: 'relate2'},
            {label: '单位', name: 'unit2', width: 200},
            {label: '职务', name: 'post2'},
            {label: '办公电话', name: 'phone2'},
            {label: '手机号', name: 'mobile2'},
            {label: '电子邮箱', name: 'email2', width: 200},
            {label: '去往国家', name: 'country', width: 150},
            {label: '留学学校或工作单位', name: 'school', width: 200},
            {label: '出国起止时间', name: 'abroadTime', width: 200,formatter: function (cellvalue, options, rowObject) {
                return new Date(rowObject.startTime).format('yyyy-MM') + "至" + new Date(rowObject.endTime).format('yyyy-MM');
            }},
            {label: '留学方式', name: 'type', width: 200, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '';
                return _cMap.GRADUATE_ABROAD_TYPE_MAP[cellvalue];
            }},
            {label: '申请保留组织关系起止时间', name: 'mobile', width: 200,formatter: function (cellvalue, options, rowObject) {
                return new Date(rowObject.saveStartTime).format('yyyy-MM') + "至" + new Date(rowObject.saveEndTime).format('yyyy-MM');
            }},
            {label: '党费交纳截止时间', name: 'payTime', width: 200, formatter: 'date', formatoptions: {newformat: 'Y-m'}},
            {label: '状态', name: 'statusName', width: 150, formatter: function (cellvalue, options, rowObject) {
                return _cMap.GRADUATE_ABROAD_STATUS_MAP[rowObject.status];
            }}<c:if test="${cls==1}">
            ,{label: '审核类别', name: 'isBackName', width: 150, formatter: function (cellvalue, options, rowObject) {
                return rowObject.isBack?"返回修改":"新申请";
            }}</c:if>, {hidden: true, name: 'status'}
        ],
        onSelectRow: function (id, status) {
            saveJqgridSelected("#"+this.id, id, status);
            //console.log(id)
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#branchApprovalBtn,#partyApprovalBtn,#odApprovalBtn").prop("disabled", true);
            } else if (ids.length==1) {

                var rowData = $(this).getRowData(ids[0]);
                $("#branchApprovalBtn").prop("disabled", rowData.status != "${GRADUATE_ABROAD_STATUS_APPLY}");
                $("#partyApprovalBtn").prop("disabled", rowData.status != "${GRADUATE_ABROAD_STATUS_BRANCH_VERIFY}");
                $("#odApprovalBtn").prop("disabled", rowData.status != "${GRADUATE_ABROAD_STATUS_PARTY_VERIFY}");
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

    $.initNavGrid("jqGrid", "jqGridPager");
    <c:if test="${cls==1||cls==11}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"支部批量审核",
        btnbase:"jqBatchBtn btn btn-success btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/graduateAbroad_check" data-querystr="&type=1" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });
    </c:if>
    <c:if test="${cls==2||cls==21}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"分党委批量审核",
        btnbase:"jqBatchBtn btn btn-primary btn-xs",
        buttonicon:"fa fa-check-circle-o",
        onClickButton: function(){
            var ids  = $(this).getGridParam("selarrrow");
            if(ids.length==0){
                SysMsg.warning("请选择行", "提示");
                return ;
            }

            loadModal("${ctx}/graduateAbroad_transfer?ids[]={0}".format(ids))
        }
    });
    </c:if>
    <c:if test="${cls==3||cls==31}">
    $("#jqGrid").navButtonAdd('#jqGridPager',{
        caption:"组织部批量审核",
        btnbase:"jqBatchBtn btn btn-warning btn-xs",
        buttonicon:"fa fa-check-circle-o",
        props:'data-url="${ctx}/graduateAbroad_check" data-querystr="&type=3" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });
    </c:if>
    <c:if test="${cls==1||cls==11||cls==2||cls==21||cls==3||cls==31}">
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

            loadModal("${ctx}/graduateAbroad_back?ids[]={0}&status={1}".format(ids, minStatus))
        }
    });
    </c:if>
    
    $('[data-rel="select2"]').select2();
    register_user_select($('#searchForm select[name=userId]'));
</script>
