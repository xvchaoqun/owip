<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp"%>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers multi-row-head-table" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <li class="${cls==1?'active':''}">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/pm/pm3MeetingStat?cls=1"}><i class="fa fa-calendar-o "></i> 月度汇总</a>
                </li>
                <li class="${cls==2?'active':''}">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/pm/pm3MeetingStat?cls=2"}><i class="fa fa-calendar"></i> 年度汇总</a>
                </li>
            </ul>
            <div class="space-4"></div>
            <c:set var="_query" value="${not empty param.year||not empty param.month||not empty param.partyId||not empty param.branchId }"/>
            <div class="jqgrid-vertical-offset buttons">
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                        data-url="${ctx}/pm/pm3MeetingStat_data?cls=${cls}"
                        data-rel="tooltip" data-placement="top" title="导出所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
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
                            <input type="hidden" name="cols">
                            <input type="hidden" name="cols1">
                            <input type="hidden" name="cols2">
                            <div class="form-group">
                                <label>年份</label>
                                <select class="form-control" data-rel="select2" data-width="110" id="year" name="year" data-placeholder="请选择">
                                    <option></option>
                                </select>
                            </div>
                            <c:if test="${cls==1}">
                                <div class="form-group"id="monthDiv" style="${(empty param.year)?'display: none':''}">
                                    <label>月份</label>
                                    <select class="form-control"
                                            data-width="110" data-rel="select2"
                                            id="month"  name="month" data-placeholder="请选择">
                                        <option></option>
                                        <option value="1">1月</option>
                                        <option value="2">2月</option>
                                        <option value="3">3月</option>
                                        <option value="4">4月</option>
                                        <option value="5">5月</option>
                                        <option value="6">6月</option>
                                        <option value="7">7月</option>
                                        <option value="8">8月</option>
                                        <option value="9">9月</option>
                                        <option value="10">10月</option>
                                        <option value="11">11月</option>
                                        <option value="12">12月</option>
                                    </select>
                                </div>
                            </c:if>
                            <script>
                                var myDate= new Date();
                                var startYear=myDate.getFullYear();
                                for (var i=0;i<10;i++) {
                                    var yearNumber = startYear-i;
                                    $("#year").append("<option value="+yearNumber+">"+yearNumber+"</option>");
                                }

                                $("#year").on('change',function(){
                                    if($("#year").val()=='') {
                                        $("#monthDiv").hide();
                                        return;
                                    }
                                    $("#monthDiv").show();
                                });

                                if(${not empty param.year}){
                                    $("#year").val(${param.year}).trigger("change");
                                }
                                if(${not empty param.month}){
                                    $("#month").val(${param.month}).trigger("change");
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
                                   data-url="${ctx}/pm/pm3MeetingStat?cls=${cls}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/pm/pm3MeetingStat?cls=${cls}"
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
        url: '${ctx}/pm/pm3MeetingStat_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [

            { label: '年份', name: 'year', width:80, frozen:true},
            <c:if test="${cls==1}">
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
            { label: '支部委员会',name: 'count1',formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined||cellvalue==0) return '0'

                    return ('<a href="javascript:;" class="popupBtn bolder" data-width="1000"' +
                        'data-url="${ctx}/pm/pm3Meeting_count?year={0}&quarter={1}&month={2}&partyId={3}&branchId={4}&type={5}&cls={6}"><u>{7}</u></a>')
                        .format(rowObject.year, rowObject.quarter, rowObject.month, rowObject.partyId, $.trim(rowObject.branchId),${PM_3_BRANCH_COMMITTEE},${cls},cellvalue);
                }
            },
            { label: '党员集体活动总数',name: 'count',formatter: function (cellvalue, options, rowObject) {
                    var count=rowObject.count2+rowObject.count3+rowObject.count4+rowObject.count5+rowObject.count6;
                    if(count==0) return '--'

                    return ('<a href="javascript:;" class="popupBtn bolder" data-width="1000"' +
                        'data-url="${ctx}/pm/pm3Meeting_count?year={0}&quarter={1}&month={2}&partyId={3}&branchId={4}&cls={5}&isSum=1"><u>{6}</u></a>')
                        .format(rowObject.year, rowObject.quarter, rowObject.month, rowObject.partyId, $.trim(rowObject.branchId),${cls},count);
                }
            },
            { label: '党员大会',name: 'count2',formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined||cellvalue==0) return '0'

                    return ('<a href="javascript:;" class="popupBtn bolder" data-width="1000"' +
                        'data-url="${ctx}/pm/pm3Meeting_count?year={0}&quarter={1}&month={2}&partyId={3}&branchId={4}&type={5}&cls={6}"><u>{7}</u></a>')
                        .format(rowObject.year, rowObject.quarter, rowObject.month, rowObject.partyId, $.trim(rowObject.branchId),${PM_3_BRANCH_MEMBER},${cls},cellvalue);
                }
            },
            { label: '党小组会',name: 'count3',formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined||cellvalue==0) return '0'
                    return ('<a href="javascript:;" class="popupBtn bolder" data-width="1000"' +
                        'data-url="${ctx}/pm/pm3Meeting_count?year={0}&quarter={1}&month={2}&partyId={3}&branchId={4}&type={5}&cls={6}"><u>{7}</u></a>')
                        .format(rowObject.year, rowObject.quarter, rowObject.month, rowObject.partyId, $.trim(rowObject.branchId),${PM_3_BRANCH_GROUP},${cls},cellvalue);
                }
            },
            { label: '党课',name: 'count4',formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined||cellvalue==0) return '0'

                    return ('<a href="javascript:;" class="popupBtn bolder" data-width="1000"' +
                        'data-url="${ctx}/pm/pm3Meeting_count?year={0}&quarter={1}&month={2}&partyId={3}&branchId={4}&type={5}&cls={6}"><u>{7}</u></a>')
                        .format(rowObject.year, rowObject.quarter, rowObject.month, rowObject.partyId, $.trim(rowObject.branchId),${PM_3_BRANCH_COURSE},${cls},cellvalue);
                }
            },
            { label: '组织生活会<br>民主评议党员',name: 'count5',formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined||cellvalue==0) return '0'

                    return ('<a href="javascript:;" class="popupBtn bolder" data-width="1000"' +
                        'data-url="${ctx}/pm/pm3Meeting_count?year={0}&quarter={1}&month={2}&partyId={3}&branchId={4}&type={5}&cls={6}"><u>{7}</u></a>')
                        .format(rowObject.year, rowObject.quarter, rowObject.month, rowObject.partyId, $.trim(rowObject.branchId),${PM_3_BRANCH_LIVE},${cls},cellvalue);
                }
            },
            { label: '主题党日',name: 'count6',formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined||cellvalue==0) return '0'

                    return ('<a href="javascript:;" class="popupBtn bolder" data-width="1000"' +
                            'data-url="${ctx}/pm/pm3Meeting_count?year={0}&quarter={1}&month={2}&partyId={3}&branchId={4}&type={5}&cls={6}"><u>{7}</u></a>')
                            .format(rowObject.year, rowObject.quarter, rowObject.month, rowObject.partyId, $.trim(rowObject.branchId),${PM_3_BRANCH_THEME},${cls},cellvalue);

                }
            }
    ]
}).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('#searchForm [data-rel="select2"]').select2();

    $("ul.dropdown-menu").on("click", "[data-stopPropagation]", function (e) {
        //console.log($(e.target).hasClass("jqExportBtn"))
        if (!$(e.target).hasClass("jqExportBtn")) {
            e.stopPropagation();
        }
    });
    $("#btnSelectAll").click(function () {
        $("#exportForm :checkbox").prop("checked", true);
        _updateCols()
    });
    $("#btnDeselectAll").click(function () {
        $("#exportForm :checkbox").prop("checked", false);
        _updateCols()
    });
    $("#exportForm :checkbox").click(function () {
        _updateCols()
    });

    function _updateCols() {
        var cols = $.map($("#exportForm :checkbox:checked"), function (chk) {
            return $(chk).val();
        });
        $("#searchForm input[name=cols]").val(cols.join(','));
    }

    $("#btnSelectAll1").click(function () {
        $("#exportForm1 :checkbox").prop("checked", true);
        _updateCols1()
    });
    $("#btnDeselectAll1").click(function () {
        $("#exportForm1 :checkbox").prop("checked", false);
        _updateCols1()
    });
    $("#exportForm1 :checkbox").click(function () {
        _updateCols1()
    });
    function _updateCols1() {
        var cols = $.map($("#exportForm1 :checkbox:checked"), function (chk) {
            return $(chk).val();
        });
        $("#searchForm input[name=cols1]").val(cols.join(','));
    }

    $("#btnSelectAll2").click(function () {
        $("#exportForm2 :checkbox").prop("checked", true);
        _updateCols2()
    });
    $("#btnDeselectAll2").click(function () {
        $("#exportForm2 :checkbox").prop("checked", false);
        _updateCols2()
    });
    $("#exportForm2 :checkbox").click(function () {
        _updateCols2()
    });
    function _updateCols2() {
        var cols = $.map($("#exportForm2 :checkbox:checked"), function (chk) {
            return $(chk).val();
        });
        $("#searchForm input[name=cols2]").val(cols.join(','));
    }
</script>