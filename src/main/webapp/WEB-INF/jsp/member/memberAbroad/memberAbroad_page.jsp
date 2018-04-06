<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="myTableDiv"
                 data-url-page="${ctx}/memberAbroad"
                 data-url-export="${ctx}/memberAbroad_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId ||not empty param._abroadTime
                 ||not empty param.partyId ||not empty param.branchId || not empty param.code || not empty param.sort}"/>

                <div class="jqgrid-vertical-offset buttons">
                    <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i
                            class="fa fa-download"></i> 导出</a>
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
                            <form class="form-inline search-form " id="searchForm">
                                <input type="hidden" name="cls" value="${cls}">
                                        <div class="form-group">
                                            <label>用户</label>
                                                <div class="input-group">
                                                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects?type=${MEMBER_TYPE_TEACHER}"
                                                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                    </select>
                                                </div>
                                        </div>
                                        <div class="form-group">
                                            <label>出国时间</label>
                                                <div class="input-group tooltip-success" data-rel="tooltip" title="出国时间范围">
                                                    <span class="input-group-addon">
                                                        <i class="fa fa-calendar bigger-110"></i>
                                                    </span>
                                                    <input placeholder="请选择出国时间范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_abroadTime" value="${param._abroadTime}"/>
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
                                        $.register.party_branch_select($("#searchForm"), "branchDiv",
                                                '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                    </script>
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
            </div>
        </div>
        <div id="body-content-view">

        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/memberAbroad_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '教工号', name: 'user.code', frozen:true},
            { label: '姓名', name: 'user.realname', width: 75, formatter:function(cellvalue, options, rowObject){
                return $.member(rowObject.userId, cellvalue);
            }, frozen:true  },
            {label: '性别', name: 'user.gender',width: 50, frozen:true, formatter:$.jgrid.formatter.GENDER},
            {label: '年龄', name: 'user.birth', width: 50, formatter: $.jgrid.formatter.AGE},
            {
                label: '所属组织机构', name: 'party', align:'left',  width: 450,
                formatter: function (cellvalue, options, rowObject) {
                    return $.party(rowObject.partyId, rowObject.branchId);
                }, frozen:true
            },
            {label: '国家', name: 'gj'},
            {label: '经费来源', name: 'jfly'},
            {label: '出国境类别', name: 'cgjlb',width: 150},
            {label: '出国境方式', name: 'cgjfs'},
            {label: '邀请单位', name: 'yqdw',width: 150},
            {label: '邀请单位地址', name: 'yqdwdz'},
            {label: '邀请人', name: 'yqr'},
            {label: '申请人职称', name: 'sqrzc'},
            {label: '申请人手机号', name: 'sqrsjh'},
            {label: '申请人邮箱', name: 'sqryx'},
            {label: '预计出发时间', name: 'yjcfsj', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '应归时间', name: 'ygsj', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '预计停留天数', name: 'yjtlts'},
            {label: '实际出发时间', name: 'sjcfsj', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '实归时间', name: 'sgsj', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '实际停留天数', name: 'sjtlts'},
            {label: '延期1始', name: 'yq1s', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '延期1止', name: 'yq1z', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '延期2始', name: 'yq2s', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '延期2止', name: 'yq2z', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '批准文号', name: 'pzwh', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '出国境状态', name: 'cgjzt'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('[data-rel="select2"]').select2();
    $.register.user_select($('#searchForm select[name=userId]'));
</script>
