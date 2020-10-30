<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="PARTY_MEETING2_BRANCH" value="<%=sys.constants.PmConstants.PARTY_MEETING2_BRANCH%>"/>
<c:set var="PARTY_MEETING2_BRANCH_COMMITTEE" value="<%=sys.constants.PmConstants.PARTY_MEETING2_BRANCH_COMMITTEE%>"/>
<c:set var="PARTY_MEETING2_BRANCH_GROUP" value="<%=sys.constants.PmConstants.PARTY_MEETING2_BRANCH_GROUP%>"/>
<c:set var="PARTY_MEETING2_BRANCH_CLASS" value="<%=sys.constants.PmConstants.PARTY_MEETING2_BRANCH_CLASS%>"/>
<c:set var="PARTY_MEETING2_BRANCH_ACTIVITY" value="<%=sys.constants.PmConstants.PARTY_MEETING2_BRANCH_ACTIVITY%>"/>

<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">

            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <li class="${cls==1?'active':''}">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/pmMeeting2Stat?cls=1"}><i class="fa fa-circle-o"></i> 按季度统计</a>
                </li>
                <li class="${cls==2?'active':''}">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/pmMeeting2Stat?cls=2"}><i class="fa fa-calendar"></i> 按月份统计</a>
                </li>
            </ul>
            <div class="space-4"></div>
            <c:set var="_query" value="${not empty param.year||not empty param.quarter||not empty param.partyId||not empty param.branchId }"/>
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
                                   data-url="${ctx}/pmMeeting2Stat?cls=${cls}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/pmMeeting2Stat?cls=${cls}"
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
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/pmMeeting2_stat?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [

            { label: '年份', name: 'year', width:80, frozen:true},
            { label: '季度', name: 'quarter',width:80,frozen:true, formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined)
                        return '--'
                    return '第'+cellvalue+'季度'
                }
            },

            <c:if test="${cls==2}">
            { label: '月份', name: 'month', width:80, frozen:true, formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined)
                        return '--'
                    return cellvalue+'月'
                }
            },
            </c:if>

            { label: '所属${_p_partyName}', name: 'partyId',align:'left', width: 300 ,  formatter:function(cellvalue, options, rowObject){
                    return $.party(rowObject.partyId);
                }},
            { label: '所属党支部',  name: 'branchId',align:'left', width: 300,formatter:function(cellvalue, options, rowObject){

                    return $.party(null, rowObject.branchId);
                }, frozen:true
            },
            { label: '合计数量',name: 'count',formatter: function (cellvalue, options, rowObject) {
                    var count=rowObject.count1+rowObject.count2+rowObject.count3+rowObject.count4+rowObject.count5;
                    if(count==0) return '--'

                    return ('<a href="javascript:;" class="popupBtn bolder" data-width="1000"' +
                            'data-url="${ctx}/pmMeeting2_count?year={0}&quarter={1}&month={2}&partyId={3}&branchId={4}&cls={5}"><u>{6}</u></a>')
                            .format(rowObject.year, rowObject.quarter, rowObject.month, rowObject.partyId, $.trim(rowObject.branchId),${cls},count);
                }
            },
            { label: '支部党员大会',name: 'count1',formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined||cellvalue==0) return '0'

                    return ('<a href="javascript:;" class="popupBtn bolder" data-width="1000"' +
                            'data-url="${ctx}/pmMeeting2_count?year={0}&quarter={1}&month={2}&partyId={3}&branchId={4}&type={5}&cls={6}"><u>{7}</u></a>')
                            .format(rowObject.year, rowObject.quarter, rowObject.month, rowObject.partyId, $.trim(rowObject.branchId),${PARTY_MEETING2_BRANCH},${cls},cellvalue);
                }
            },
            { label: '支部委员会',name: 'count2',formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined||cellvalue==0) return '0'

                    return ('<a href="javascript:;" class="popupBtn bolder" data-width="1000"' +
                            'data-url="${ctx}/pmMeeting2_count?year={0}&quarter={1}&month={2}&partyId={3}&branchId={4}&type={5}&cls={6}"><u>{7}</u></a>')
                            .format(rowObject.year, rowObject.quarter, rowObject.month, rowObject.partyId, $.trim(rowObject.branchId),${PARTY_MEETING2_BRANCH_COMMITTEE},${cls},cellvalue);
                }
            },
            { label: '党小组会',name: 'count3',formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined||cellvalue==0) return '0'
                    return ('<a href="javascript:;" class="popupBtn bolder" data-width="1000"' +
                            'data-url="${ctx}/pmMeeting2_count?year={0}&quarter={1}&month={2}&partyId={3}&branchId={4}&type={5}&cls={6}"><u>{7}</u></a>')
                            .format(rowObject.year, rowObject.quarter, rowObject.month, rowObject.partyId, $.trim(rowObject.branchId),${PARTY_MEETING2_BRANCH_GROUP},${cls},cellvalue);
                }
            },
            { label: '党课',name: 'count4',formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined||cellvalue==0) return '0'

                    return ('<a href="javascript:;" class="popupBtn bolder" data-width="1000"' +
                            'data-url="${ctx}/pmMeeting2_count?year={0}&quarter={1}&month={2}&partyId={3}&branchId={4}&type={5}&cls={6}"><u>{7}</u></a>')
                            .format(rowObject.year, rowObject.quarter, rowObject.month, rowObject.partyId, $.trim(rowObject.branchId),${PARTY_MEETING2_BRANCH_CLASS},${cls},cellvalue);
                }
            },
            { label: '主题党日活动',name: 'count5',formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined||cellvalue==0) return '0'

                    return ('<a href="javascript:;" class="popupBtn bolder" data-width="1000"' +
                            'data-url="${ctx}/pmMeeting2_count?year={0}&quarter={1}&month={2}&partyId={3}&branchId={4}&type={5}&cls={6}"><u>{7}</u></a>')
                            .format(rowObject.year, rowObject.quarter, rowObject.month, rowObject.partyId, $.trim(rowObject.branchId),${PARTY_MEETING2_BRANCH_ACTIVITY},${cls},cellvalue);
                }
            },
    ]
}).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('#searchForm [data-rel="select2"]').select2();

    //$('[data-rel="tooltip"]').tooltip();
</script>