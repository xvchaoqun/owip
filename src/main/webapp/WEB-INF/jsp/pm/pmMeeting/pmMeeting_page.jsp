<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/pmMeeting_au"
             data-url-page="${ctx}/pmMeeting"
             data-url-export="${ctx}/pmMeeting_data?type=${type}&cls=${cls}"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="tabbable">
                <shiro:hasPermission name="pmMeeting:list">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="${cls==1?'active':''}">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/pmMeeting?cls=1&type=${type}"}><i class="fa fa-circle-o"></i> 待审核(${cm:trimToZero(pm_initCount)})</a>
                    </li>
                    </li>
                    <li class="${cls==2?'active':''}">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/pmMeeting?cls=2&type=${type}"}><i class="fa fa-reply"></i> 退回(${cm:trimToZero(pm_backCount)})</a>
                    </li>
                    <li class="${cls==3?'active':''}">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/pmMeeting?cls=3&type=${type}"}><i class="fa fa-check"></i> 已通过(${cm:trimToZero(pm_passCount)})</a>
                    </li>
                    <li class="${cls==4?'active':''}">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/pmMeeting?cls=4&type=${type}"}><i class="fa fa-times"></i> 未通过(${cm:trimToZero(pm_denyCount)})</a>
                    </li>
                    <div class="buttons pull-left hidden-sm hidden-xs" style="left:20px; position: relative">
                        <shiro:hasPermission name="pmMeeting:edit">
                            <button class="openView btn btn-info btn-sm"
                                    data-url="${ctx}/pmMeeting_au?type=${type}&edit=true">
                                <i class="fa fa-plus"></i> 添加
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="pmMeeting:approve">
                            <button class="popupBtn btn btn-success btn-sm tooltip-info"
                                    data-url="${ctx}/pmMeeting_import?type=${type}"
                                    data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                                批量导入
                            </button>
                        </shiro:hasPermission>
                    </div>
                </ul>
                </shiro:hasPermission>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <c:set var="_query"
                               value="${not empty param.partyId||not empty param.branchId||not empty param.name||not empty param.issue||not empty param.year||not empty param.quarter||not empty param._meetingDate}"/>
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="pmMeeting:approve">
                                <c:if test="${cls==1}">
                                    <button id="checkBtn" class="jqOpenViewBatchBtn btn btn-success btn-sm"
                                            data-url="${ctx}/pmMeeting_check?check=true"
                                            data-grid-id="#jqGrid">
                                        <i class="fa fa-check"></i> 审核</button>
                                    <button id="backBtn" class="jqOpenViewBatchBtn btn btn-danger btn-sm"
                                            data-url="${ctx}/pmMeeting_check"
                                            data-grid-id="#jqGrid">
                                        <i class="fa fa-reply"></i> 退回</button>
                                </c:if>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="pmMeeting:edit">
                                <c:if test="${cls==2||cls==4}">
                                    <a class="jqOpenViewBtn btn btn-info btn-sm"
                                       data-url="${ctx}/pmMeeting_au?edit=true&reedit=1&type=${type}"
                                       data-grid-id="#jqGrid"
                                       data-open-by="page"><i class="fa fa-edit"></i>
                                        重新提交</a>
                                </c:if>
                                <c:if test="${cls==1||(cls==3 && cm:isPermitted('pmMeeting:approve'))}">
                                      <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                                   data-url="${ctx}/pmMeeting_au?edit=true&type=${type}"
                                                   data-grid-id="#jqGrid"
                                                   data-open-by="page"><i class="fa fa-edit"></i>
                                            修改</a>
                                </c:if>

                                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                   data-rel="tooltip" data-placement="top"
                                   title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i> 导出</a>
                              <%--  <a class="jqLinkItemBtn btn btn-warning btn-sm"
                                   data-url="${ctx}/pmMeeting_exportWord"
                                   data-grid-id="#jqGrid"
                                   data-open-by="page"><i class="fa fa-download"></i>
                                    导出工作记录</a>--%>

                                <c:if test="${cm:isPermitted(PERMISSION_OWADMIN) || cm:hasRole(ROLE_PARTYADMIN)}">
                                <button data-url="${ctx}/pmMeeting_del"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                                </c:if>
                            </shiro:hasPermission>
                            <button class="jqOpenViewBtn btn btn-info btn-sm"
                                    data-url="${ctx}/sysApprovalLog"
                                    data-querystr="&type=<%=SystemConstants.SYS_APPROVAL_LOG_PM%>"
                                    data-open-by="page">
                                <i class="fa fa-search"></i> 操作记录
                            </button>
                        </div>
                        <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                            <div class="widget-header">
                                <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

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

                                            <div class="form-group">
                                                <label>年度</label>
                                                <select class="form-control" data-rel="select2" data-width="110" id="year" name="year" data-placeholder="请选择">
                                                    <option></option>
                                                </select>
                                            </div>
                                            <div class="form-group"id="quarterDiv" style="${(empty param.year)?'display: none':''}">
                                                <label>季度</label>
                                                <select class="form-control"
                                                        data-width="110" data-rel="select2"
                                                        id="quarter"  name="quarter" data-placeholder="请选择">
                                                    <option></option>
                                                    <option value="1">第1季度</option>
                                                    <option value="2">第2季度</option>
                                                    <option value="3">第3季度</option>
                                                    <option value="4">第4季度</option>

                                                </select>
                                            </div>

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

                                            <label>会议名称</label>
                                            <input class="form-control search-query" name="name" type="text"
                                                   value="${param.name}"
                                                   placeholder="请输入会议名称">
                                            <label>会议议题</label>
                                            <input class="form-control search-query" name="issue" type="text"
                                                   value="${param.issue}"
                                                   placeholder="请输入会议议题">
                                        </div>
                                        <script>
                                            $.register.party_branch_select($("#searchForm"), "branchDiv",
                                                '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                        </script>
                                        <div class="form-group">
                                            <label>会议时间</label>
                                            <div class="input-group tooltip-success" data-rel="tooltip" title="会议时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                <input placeholder="请选择会议时间范围" data-rel="date-range-picker"
                                                       class="form-control date-range-picker"
                                                       type="text" name="_meetingDate" value="${param._meetingDate}"/>
                                            </div>
                                        </div>

                                        <div class="clearfix form-actions center">

                                                <a class="jqSearchBtn btn btn-default btn-sm"
                                                   data-url="${ctx}/pmMeeting?type=${type}&cls=${cls}"
                                                   data-target="#page-content"
                                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
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
        url: '${ctx}/pmMeeting_data?branch=${param.branchId}&type=${type}&cls=${cls}&callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '年份', name: 'year', width:80, frozen:true
            },
            {
                label: '季度', name: 'quarter',width:80,frozen:true, formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined)
                        return '--'
                    return '第'+cellvalue+'季度'
                }
            },
            { label: '工作记录',name: '_export', formatter: function (cellvalue, options, rowObject) {

                    return ('<button class="downloadBtn btn btn-primary btn-xs" ' +
                        'data-url="${ctx}/pmMeeting_exportWord?id={0}"><i class="fa fa-download"></i> 导出</button>').format(rowObject.id);
                    return '--'
                }
            },
            { label: '所属${_p_partyName}', name: 'partyId',align:'left', width: 300 ,  formatter:function(cellvalue, options, rowObject){
                    return $.party(rowObject.partyId);
                }},
            { label: '所属党支部',  name: 'branchId',align:'left', width: 400,formatter:function(cellvalue, options, rowObject){

                    return $.party(null, rowObject.branchId);
                }, frozen:true },
            {label: '计划时间', name: 'planDate', width:150,
                formatter: $.jgrid.formatter.date,
                formatoptions: {srcformat: 'Y.m.d H:i', newformat: 'Y-m-d H:i'}},
            {label: '实际时间', name: 'date', width:150,
                formatter: $.jgrid.formatter.date,
                formatoptions: {srcformat: 'Y.m.d H:i', newformat: 'Y-m-d H:i'}},
            {label: '${param.type!=5?"会议":"主题党日活动"}名称', name: 'name', width:350,align:'left',frozen:true},
            {label: '${param.type!=5?"会议议题":"活动主题"}', name: 'issue', width:350, align:'left', formatter:function(cellvalue, options, rowObject){
                    if(cellvalue==undefined) return '--';
                    return '<a href="javascript:;" class="openView" data-url="${ctx}/pmMeeting_au?edit=false&id={0}&type={2}">{1}</a>'.format( rowObject.id,cellvalue,rowObject.type);
                }
            },

            {label: '应到人数', name: 'dueNum'},
            {label: '实到人数', name: 'attendNum',formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==0) return '--'
                    return ('<a href="javascript:;" class="popupBtn bolder" ' +
                        'data-url="${ctx}/pmMeeting_user?id={0}&type=1"><u>{1}</u></a>')
                        .format(rowObject.id, cellvalue);
                }
            },
            {label: '请假人数', name: 'absentNum',formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==0||cellvalue==undefined) return '--'
                    return ('<a href="javascript:;" class="popupBtn bolder" ' +
                        'data-url="${ctx}/pmMeeting_user?id={0}&type=2"><u>{1}</u></a>')
                        .format(rowObject.id, cellvalue);
                }
            },
            <c:if test="${param.type!=5}">
               {label: '主持人', name: 'presenterName.realname', align:'left'},
            </c:if>
            {label: '记录人', name: 'recorderName.realname', align:'left'},
            {label: '${param.type!=5?"会议":"活动"}地点', name: 'address', align:'left'},
            {label: '参会人员', name: 'attendList', align:'left', width: 280,formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined) return '--'
                    return $.map(cellvalue, function(u){
                        return u.realname;
                    })
                }},
            {label: '附件', name: 'file', formatter: function (cellvalue, options, rowObject) {

                    return '<button class="popupBtn btn btn-info btn-xs" data-width="700" data-callback="_reload"' +
                        'data-url="${ctx}/pmMeetingFile?id={0}"><i class="fa fa-search"></i> 附件{1}</button>'
                            .format(rowObject.id, rowObject.fileCount>=0?"("+rowObject.fileCount+")":"")
                }},
            { label: '审核情况', name: 'status', width: 80, formatter: function (cellvalue, options, rowObject) {
                var adminParty=$.inArray(rowObject.partyId,${cm:toJSONArray(adminPartyIdList)});

                    if(rowObject.isBack) return '已退回'

                    return _cMap.PM_MEETING_STATUS_MAP[cellvalue];
                },frozen:true },

            <c:if test="${cls==2||cls==4}">
            {label: '原因', name: 'reason', align:'left'},
            </c:if>
            {hidden: true, name: 'partyId'}
        ],
        onSelectRow: function(id,status){
            _selectRows(this,status)
        },
        onSelectAll: function(aRowids,status){
            _selectRows(this,status)
        }
    }).jqGrid("setFrozenColumns");

    function _selectRows(grid,status){
        if(status){
            var ids = $(grid).getGridParam("selarrrow");
            var canCheck = false;
            for(var i=0;i<ids.length;i++){

                var rowData = $(grid).getRowData(ids[i]);
                var partyId = parseInt(rowData.partyId);

                var arr= ${cm:toJSONArray(adminPartyIdList)};
                var adminParty=$.inArray(partyId,arr);
/*
                console.log(partyId);
                console.log(arr);
                console.log(adminParty);*/
                if(adminParty>=0){
                    canCheck = true;
                    break;
                }
            }

            if(!canCheck&&${addPermits==true}){
                $("#checkBtn").prop("disabled",true);
                $("#backBtn").prop("disabled",true);
            }
        }
        else{

            $("#checkBtn").removeAttr("disabled");
            $("#backBtn").removeAttr("disabled");
        }
    }
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();

    //$('[data-rel="tooltip"]').tooltip();
    //设置年份的选择
    var myDate= new Date();
    var startYear=myDate.getFullYear();
    for (var i=0;i<10;i++) {
        var yearNumber = startYear-i;
        $("#year").append("<option value="+yearNumber+">"+yearNumber+"</option>");
    }

    $("#year").on('change',function(){
        console.log($("#year").val());
        if($("#year").val()=='') {
            $("#quarterDiv").hide();
           return;
        }
        $("#quarterDiv").show();
    });
    if(${not empty param.year}){
        $("#year").val(${param.year}).trigger("change");
    }
    if(${not empty param.quarter}){
      $("#quarter").val(${param.quarter}).trigger("change");
    }
</script>