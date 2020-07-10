<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="PARTY_MEETING_MAP" value="<%=sys.constants.PmConstants.PARTY_MEETING_MAP%>"/>
<c:set var="PARTY_MEETING_BRANCH_GROUP" value="<%=sys.constants.PmConstants.PARTY_MEETING_BRANCH_GROUP%>"/>
<c:set var="PARTY_MEETING_BRANCH_ACTIVITY" value="<%=sys.constants.PmConstants.PARTY_MEETING_BRANCH_ACTIVITY%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/pmMeeting2_au"
             data-url-page="${ctx}/pmMeeting2"
             data-url-export="${ctx}/pmMeeting2_data?cls=${cls}"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">

            <div class="tabbable">
                <shiro:hasPermission name="pmMeeting2:list">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                        <li class="${cls==1?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/pmMeeting2?cls=1"}><i class="fa fa-list"></i> 活动列表</a>
                        </li>
                        <li class="${cls==2?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/pmMeeting2?cls=2"}><i class="fa fa-circle-o"></i> 待审核</a>
                        </li>
                        <li class="${cls==3?'active':''}">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/pmMeeting2?cls=3"}><i class="fa fa-times"></i> 审批未通过</a>
                        </li>
                        <div class="buttons pull-left hidden-sm hidden-xs" style="left:20px; position: relative">
                            <shiro:hasPermission name="pmMeeting2:edit">
                                <button class="openView btn btn-info btn-sm"
                                        data-url="${ctx}/pmMeeting2_au?edit=true">
                                    <i class="fa fa-plus"></i> 添加
                                </button>
                            </shiro:hasPermission>
                           <%-- <shiro:hasPermission name="pmMeeting2:approve">
                                <button class="popupBtn btn btn-success btn-sm tooltip-info"
                                        data-url="${ctx}/pmMeeting2_import"
                                        data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                                    批量导入
                                </button>
                            </shiro:hasPermission>--%>
                        </div>
                    </ul>
                </shiro:hasPermission>
                <div class="tab-content">
                    <div class="tab-pane in active">
                    <c:set var="_query" value="${not empty param.year||not empty param.quarter||not empty param.partyId||not empty param.branchId}"/>
                    <div class="jqgrid-vertical-offset buttons">
                        <shiro:hasPermission name="pmMeeting2:approve">
                            <c:if test="${cls==2}">
                                <button id="checkBtn" class="jqOpenViewBatchBtn btn btn-success btn-sm"
                                        data-url="${ctx}/pmMeeting2_check?check=true"
                                        data-grid-id="#jqGrid">
                                    <i class="fa fa-check"></i> 审核</button>
                            </c:if>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="pmMeeting2:edit">
                            <c:if test="${cls==3}">
                                <a class="jqOpenViewBtn btn btn-info btn-sm"
                                   data-url="${ctx}/pmMeeting2_au?edit=true&reedit=1"
                                   data-grid-id="#jqGrid"
                                   data-open-by="page"><i class="fa fa-edit"></i>
                                    重新提交</a>
                            </c:if>

                            <c:if test="${(cls==1 && cm:isPermitted('pmMeeting2:approve'))||cls==2}">
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/pmMeeting2_au?edit=true"
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

                            <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN},${ROLE_PARTYADMIN}">
                                <button data-url="${ctx}/pmMeeting2_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasAnyRoles>
                        </shiro:hasPermission>
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
                                    <script>
                                        //设置年份的选择
                                        var myDate= new Date();
                                        var startYear=myDate.getFullYear();
                                        for (var i=0;i<10;i++) {
                                            var yearNumber = startYear-i;
                                            $("#year").append("<option value="+yearNumber+">"+yearNumber+"</option>");
                                        }

                                        $("#year").on('change',function(){
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
                                    <div class="clearfix form-actions center">
                                        <a class="jqSearchBtn btn btn-default btn-sm"
                                           data-url="${ctx}/pmMeeting2?cls=${cls}"
                                           data-target="#page-content"
                                           data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                        <c:if test="${_query}">&nbsp;
                                            <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                    data-url="${ctx}/pmMeeting2?cls=${cls}"
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
<script>
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/pmMeeting2_data?callback=?&cls=${cls}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '年份', name: 'year', width:80, frozen:true},
            { label: '季度', name: 'quarter',width:80,frozen:true, formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined)
                        return '--'
                    return '第'+cellvalue+'季度'
                }
            },
            { label: '所属${_p_partyName}', name: 'partyId',align:'left', width: 300 ,  formatter:function(cellvalue, options, rowObject){
                    return $.party(rowObject.partyId);
                }},
            { label: '所属党支部',  name: 'branchId',align:'left', width: 300,formatter:function(cellvalue, options, rowObject){

                    return $.party(null, rowObject.branchId);
                }, frozen:true
            },
            {label: '实际时间', name: 'date', width:150,
                formatter: $.jgrid.formatter.date,
                formatoptions: {srcformat: 'Y.m.d H:i', newformat: 'Y-m-d H:i'}
            },
            { label: '地点',name: 'address'},
            { label: '活动名称',name: 'type', width:180,align:'left', formatter:function(cellvalue, options, rowObject){
                    var type1=rowObject.type1;
                    var type2=rowObject.type2;
                    if(type1==undefined) return '--';
                    return '<a href="javascript:;" class="openView" data-url="${ctx}/pmMeeting2_au?edit=false&id={0}">{1}</a>'.format( rowObject.id,type2==null?_cMap.PARTY_MEETING_MAP[type1]:_cMap.PARTY_MEETING_MAP[type1]+","+_cMap.PARTY_MEETING_MAP[type2]);
                  }
             },
            { label: '次数',name: 'number', formatter: function (cellvalue, options, rowObject) {
                    var number1=rowObject.number1;
                    var number2=rowObject.number2;
                    if(number1==undefined)
                        return '--'
                    return number2==undefined?'第'+number1+'次':'第'+number1+'/'+number2+'次';
                }},
            { label: '时长',name: 'time',width:120, formatter: function (cellvalue, options, rowObject) {
                    var time1=rowObject.time1;
                    var time2=rowObject.time2;
                    if(time1==undefined)
                        return '--'
                    return time2==undefined?time1+'分钟':time1+'/'+time2+'分钟';
                }},
            { label: '主要内容',name: 'shortContent',width:200},
            { label: '应到人数',name: 'dueNum'},
            { label: '实到人数',name: 'attendNum'},
            { label: '主持人', name: 'presenterName.realname', align:'left'},
            { label: '记录人', name: 'recorderName.realname', align:'left'},
            { label: '附件', name: 'filePath',formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined) return '--';
                    var fileName = rowObject.fileName;
                   if(fileName.endsWith(".pdf")){
                       return ' <a href="${ctx}/pdf?path={0}" target="_blank"> 预览</a>'
                               .format(encodeURI(cellvalue))
                           + '<a href="javascript:;" data-type="download" data-url="${ctx}/attach_download?path={0}&filename={1}" class="downloadBtn"> 下载</a>'
                               .format(encodeURI(cellvalue), encodeURI(rowObject.fileName));
                   }else {
                       return ' <a href="${ctx}/pic?path={0}" target="_blank"> 预览</a>'
                           .format(encodeURI(cellvalue))
                           + '<a href="javascript:;" data-type="download" data-url="${ctx}/attach_download?path={0}&filename={1}" class="downloadBtn"> 下载</a>'
                               .format(encodeURI(cellvalue), encodeURI(rowObject.fileName));
                   }
                }
            },
            <c:if test="${cls==3}">
            {label: '未通过原因', name: 'reason',width:200, align:'left'},
            </c:if>
            {hidden: true, name: 'partyId'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
</script>