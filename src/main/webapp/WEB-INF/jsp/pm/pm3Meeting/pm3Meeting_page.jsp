<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="tabbable">
                <shiro:hasPermission name="pm3Meeting:list">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                        <li class="${cls==0?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/pm/pm3Meeting?cls=0"}><i class="fa fa-list"></i> 待报送(${cm:trimToZero(pm_initCount)})</a>
                        </li>
                        </li>
                        <li class="${cls==1?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/pm/pm3Meeting?cls=1"}><i class="fa fa-circle-o"></i> 待分党委审核(${cm:trimToZero(pm_partyCount)})</a>
                        </li>
                        <li class="${cls==4?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/pm/pm3Meeting?cls=4"}><i class="fa fa-circle-o"></i> 待学工部审核(${cm:trimToZero(pm_stuCount)})</a>
                        </li>
                        <li class="${cls==2?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/pm/pm3Meeting?cls=2"}><i class="fa fa-circle-o"></i> 待组织部审核(${cm:trimToZero(pm_owCount)})</a>
                        </li>
                        <li class="${cls==3?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/pm/pm3Meeting?cls=3"}><i class="fa fa-check"></i> 审核通过(${cm:trimToZero(pm_passCount)})</a>
                        </li>
                        <div class="buttons pull-left hidden-sm hidden-xs" style="left:20px; position: relative">
                            <c:if test="${cls==PM_3_STATUS_SAVE||(cls==PM_3_STATUS_PARTY&&(isOw||isPa))||(cls==PM_3_STATUS_OW&&isOw)}">
                                <shiro:hasPermission name="pm3Meeting:edit">
                                    <button class="openView btn btn-info btn-sm"
                                            data-url="${ctx}/pm/pm3Meeting_au?cls=${cls}&edit=true">
                                        <i class="fa fa-plus"></i> 添加</button>
                                </shiro:hasPermission>
                            </c:if>
                        </div>
                    </ul>
                </shiro:hasPermission>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <c:set var="_query" value="${not empty param._startTime || not empty param._endTime || not empty param.partyId
                        ||not empty param.branchId ||not empty param.year ||not empty param.name ||not empty param.type}"/>
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${cls==PM_3_STATUS_SAVE||(cls==PM_3_STATUS_PARTY&&(isOw||isPa))||((cls==PM_3_STATUS_OW||cls==PM_3_STATUS_STU)&&isOw)}">
                                <shiro:hasPermission name="pm3Meeting:edit">
                                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                       data-url="${ctx}/pm/pm3Meeting_au?cls=${cls}&edit=true"
                                            data-open-by="page"
                                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                        修改</button>
                                </shiro:hasPermission>
                            </c:if>
                            <c:if test="${cls==PM_3_STATUS_SAVE||(cls==PM_3_STATUS_PARTY&&(isOw||isPa))||((cls==PM_3_STATUS_OW||cls==PM_3_STATUS_STU)&&isOw)}">
                                <shiro:hasPermission name="pm3Meeting:del">
                                    <button data-url="${ctx}/pm/pm3Meeting_batchDel"
                                            data-title="删除"
                                            data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                                            data-grid-id="#jqGrid"
                                            class="jqBatchBtn btn btn-danger btn-sm">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                </shiro:hasPermission>
                            </c:if>
                            <c:if test="${cls==PM_3_STATUS_PASS}">
                                <shiro:hasPermission name="${PERMISSION_OWADMIN}">
                                    <button data-url="${ctx}/pm/pm3Meeting_back"
                                            data-title="退回"
                                            data-msg="确定退回这{0}条数据？"
                                            data-grid-id="#jqGrid"
                                            class="jqBatchBtn btn btn-warning btn-sm">
                                        <i class="fa fa-reply"></i> 退回
                                    </button>
                                </shiro:hasPermission>
                            </c:if>
                            <c:if test="${(cls==PM_3_STATUS_PARTY&&(isOw||isPa))||((cls==PM_3_STATUS_OW||cls==PM_3_STATUS_STU)&&isOw)}">
                                <shiro:hasPermission name="pm3Meeting:check">
                                    <button data-url="${ctx}/pm/pm3Meeting_check"
                                            data-title="审核"
                                            data-grid-id="#jqGrid"
                                            class="jqOpenViewBatchBtn btn btn-warning btn-sm">
                                        <i class="fa fa-circle-o"></i> 审核
                                    </button>
                                </shiro:hasPermission>
                            </c:if>
                            <button class="jqOpenViewBtn btn btn-info btn-sm"
                                    data-url="${ctx}/sysApprovalLog"
                                    data-querystr="&type=<%=SystemConstants.SYS_APPROVAL_LOG_PM%>"
                                    data-open-by="page">
                                <i class="fa fa-search"></i> 操作记录
                            </button>
                        </div>
                        <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                            <div class="widget-header">
                                <h4 class="widget-title">搜索</h4>
                                <span class="widget-note">${note_searchbar}</span>
                                <div class="widget-toolbar">
                                    <a href="#" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main no-padding">
                                    <form class="form-inline search-form" id="searchForm">
                                        <div class="form-group">
                                            <label>年份</label>
                                            <select class="form-control" data-rel="select2" data-width="110" id="year" name="year" data-placeholder="请选择">
                                                <option></option>
                                            </select>
                                        </div>
                                        <script>
                                            //设置年份的选择
                                            var myDate= new Date();
                                            var startYear=myDate.getFullYear();
                                            for (var i=0;i<10;i++) {
                                                var yearNumber = startYear-i;
                                                $("#year").append("<option value="+yearNumber+">"+yearNumber+"</option>");
                                            }

                                            if(${not empty param.year}){
                                                $("#year").val(${param.year}).trigger("change");
                                            }
                                        </script>
                                        <div class="form-group">
                                            <label>${_p_partyName}</label>
                                            <select class="form-control" data-width="250" data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/party_selects?auth=1"
                                                    name="partyId" data-placeholder="请选择">
                                                <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                            </select>
                                        </div>
                                        <div class="form-group" style="${(empty branch)?'display: none':''}"
                                             id="branchDiv">
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
                                            <label>会议名称</label>
                                            <input class="form-control search-query" name="name" type="text"
                                                   value="${param.name}"
                                                   placeholder="请输入会议名称">
                                        </div>
                                        <div class="form-group">
                                            <label>会议类型</label>
                                            <select class="form-control" name="type"
                                                    data-rel="select2"
                                                    data-width="150"
                                                    data-placeholder="请选择">
                                                <option></option>
                                                <c:forEach items="${PM_3_BRANCH_MAP}" var="_type">
                                                    <option value="${_type.key}">${_type.value}</option>
                                                </c:forEach>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=type]").val('${param.type}');
                                            </script>
                                        </div>

                                        <div class="form-group">
                                            <label>开始时间</label>
                                            <div class="input-group tooltip-success" data-rel="tooltip" title="会议时间范围">
                                                                    <span class="input-group-addon">
                                                                        <i class="fa fa-calendar bigger-110"></i>
                                                                    </span>
                                                <input placeholder="请选择会议时间范围" data-rel="date-range-picker"
                                                       class="form-control date-range-picker"
                                                       type="text" name="_startTime" value="${param._startTime}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>结束时间</label>
                                            <div class="input-group tooltip-success" data-rel="tooltip" title="会议时间范围">
                                                                    <span class="input-group-addon">
                                                                        <i class="fa fa-calendar bigger-110"></i>
                                                                    </span>
                                                <input placeholder="请选择会议时间范围" data-rel="date-range-picker"
                                                       class="form-control date-range-picker"
                                                       type="text" name="_endTime" value="${param._endTime}"/>
                                            </div>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"
                                               data-url="${ctx}/pm/pm3Meeting?cls=${cls}"
                                               data-target="#page-content"
                                               data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-url="${ctx}/pm/pm3Meeting?cls=${cls}"
                                                        data-target="#page-content">
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
                    </div>
                </div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>

    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/pm/pm3Meeting_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            <c:if test="${cls==PM_3_STATUS_SAVE}">
                <shiro:hasPermission name="pm3Meeting:edit">
                { label: '报送', name: 'submit',width:70,formatter:function (cellvalue,optins,rowObject) {
                        return ('<button class="jqBatchBtn btn btn-success btn-xs" data-title="报送" data-msg="确定报送这条数据？" ' +
                            'data-url="${ctx}/pm/pm3Meeting_submit?id={0}"><i class="fa fa-hand-paper-o"></i> 报送</button>')
                            .format(rowObject.id);
                    },frozen: true},
                </shiro:hasPermission>
            </c:if>
            { label: '审核意见',name: 'checkOpinion',width:175},
            {
                label: '年份', name: 'year', width:60, frozen:true
            },
            {
                label: '月份', name: 'month', width:50, frozen:true
            },
            { label: '所属${_p_partyName}', name: 'partyId',align:'left', width: 300 ,  formatter:function(cellvalue, options, rowObject){
                    return $.party(rowObject.partyId);
                },frozen: true},
            { label: '所属党支部',  name: 'branchId',align:'left', width: 315,formatter:function(cellvalue, options, rowObject){
                    if ($.trim(cellvalue)==null)
                        return '--';
                    return $.party(null, rowObject.branchId);
                }, frozen:true },
            { label: '会议类型',name: 'type', width:175, formatter:function (cellvalue, options, rowObject){
                    if (cellvalue == undefined) return '--';
                    return _cMap.PM_3_BRANCH_MAP[cellvalue];
                }},
            { label: '主题',name: 'name',align:'left', width: 200, formatter:function(cellvalue, options, rowObject){
                    if(rowObject.type==undefined) return cellvalue;
                    return '<a href="javascript:;" class="openView" data-url="${ctx}/pm/pm3Meeting_au?edit=false&id={0}">{1}</a>'.format( rowObject.id,cellvalue);
                }
            },
            <c:if test="${cls==0}">
            { label: '状态',name: 'status',width:70, formatter:function (cellvalue, options, rowObject){
                    if (cellvalue == undefined) return '--';
                    if (rowObject.isBack==1)
                        return "已退回";
                    else
                        return _cMap.PM_3_STATUS_MAP[cellvalue];
                }},
            </c:if>
            { label: '下载',name: 'operation',width:70,formatter:function (cellvalue,options,rowObject){
                    var str = '<button href="javascript:void(0)" data-url="${ctx}/pm/pm3Meeting_download?id={0}"  title="下载" class="downloadBtn btn btn-xs btn-primary"><i class="fa fa-download"></i> 下载</button>'
                            .format(rowObject.id);
                    return str;
                }},
            { label: '开始时间',name: 'startTime', width:150,formatter: $.jgrid.formatter.date, formatoptions: {srcformat: 'Y.m.d H:i', newformat: 'Y-m-d H:i'}},
            { label: '结束时间',name: 'endTime', width:150,formatter: $.jgrid.formatter.date, formatoptions: {srcformat: 'Y.m.d H:i', newformat: 'Y-m-d H:i'}},
            { label: '地点',name: 'address',align:'left', width: 200},
            { label: '主持人',name: 'presenterUser.realname',width: 80},
            { label: '记录人',name: 'recorderUser.realname',width: 80},
            { label: '应到人数',name: 'dueNum',width:70},
            { label: '实到人数',name: 'attendNum',width:70},
            { label: '缺席人数',name: 'absentNum',width:70},
            { label: '主要内容',name: 'content',align:'left', width: 200},
            { label: '缺席人员',name: 'absentList',width:175,formatter:function (cellValue,options,rowObject){
                if (cellValue==undefined) return "--";
                return ($.map(cellValue, function(e){
                    //console.log(e.realname)
                    return e.realname;
                })).join(",")
                }},
            { label: '缺席原因',name: 'absentReason',align:'left', width: 200},

            { label: '备注',name: 'remark'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>