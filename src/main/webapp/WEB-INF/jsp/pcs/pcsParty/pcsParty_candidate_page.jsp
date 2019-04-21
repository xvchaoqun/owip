<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/pcsParty"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.userId|| not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="candidate-table tab-content">
                    <div class="tab-pane in active rownumbers">
                        <div class="jqgrid-vertical-offset buttons" style="height: 40px">
                            <div class="type-select">
                                <span class="typeCheckbox ${type==PCS_USER_TYPE_DW?"checked":""}">
                                <input ${type==PCS_USER_TYPE_DW?"checked":""} type="checkbox" value="${PCS_USER_TYPE_DW}"> 党委委员
                                </span>
                                <span class="typeCheckbox ${type==PCS_USER_TYPE_JW?"checked":""}">
                                <input class="typeCheckbox" ${type==PCS_USER_TYPE_JW?"checked":""} type="checkbox" value="${PCS_USER_TYPE_JW}"> 纪委委员
                                </span>
                            </div>

                            <a style="line-height: 40px" href="${ctx}/pcsParty_export?file=2-1&stage=${param.stage}&type=${type}" >
                                <i class="fa fa-download"></i> ${PCS_USER_TYPE_MAP.get(type)}候选人初步人选推荐提名汇总表（“${PCS_STAGE_MAP.get(cm:toByte(param.stage))}”阶段）</a>
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
                                        <input type="hidden" name="type" value="${type}">
                                        <input type="hidden" name="stage" value="${param.stage}">
                                        <div class="form-group">
                                            <label>被推荐人</label>
                                            <select name="userId" data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/member_selects?noAuth=1&type=${MEMBER_TYPE_TEACHER}&status=${MEMBER_STATUS_NORMAL}"
                                                    data-placeholder="请输入账号或姓名或学工号">
                                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                            </select>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>
                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-querystr="cls=${param.cls}&stage=${param.stage}&type=${type}">
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
<style>
    .type-select{
        padding: 10px 0 0 5px;
        float: left;
        margin-right: 50px;
    }
    .type-select .typeCheckbox{
        padding: 10px;
        cursor: pointer;
    }
    .type-select .typeCheckbox.checked{
        color: darkred;
        font-weight: bolder;
    }
    .candidate-table{
        padding-top: 0px !important;
    }
    .candidate-table th.ui-th-column div{
        white-space:normal !important;
        height:auto !important;
        padding:0px;
    }
    .candidate-table .frozen-bdiv.ui-jqgrid-bdiv {
        top: 43px !important;
    }
    #jqGrid_actualMemberCount, #jqGrid_branchCount, #jqGrid_memberCount, #jqGrid_expectMemberCount{
        padding: 0;
        font-size: 11px;
    }

    .modal .tip ul{
        margin-left: 50px;
    }
    .modal .tip ul li{
        font-size: 25px;
        text-align: left;
    }
</style>
<script>
    $(".typeCheckbox").click(function(){
        var $input = $("input", $(this));
        $("#searchForm input[name=type]").val($input.val());
        $("#searchForm .jqSearchBtn").click();
    })
    $("#jqGrid").jqGrid({
        rownumbers: true,
        multiselect:false,
        url: '${ctx}/pcsParty_candidate_data?callback=?&stage=${param.stage}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'code', width: 120, frozen:true},
            {label: '被推荐提名人姓名', name: 'realname', width: 150, frozen:true},
            {label: '推荐提名<div>的党支部数</div>', name: 'branchCount',formatter: function (cellvalue, options, rowObject) {
                return ('<a href="javascript:;" class="popupBtn" ' +
                'data-url="${ctx}/pcsParty_branchs?userId={3}&partyIds={1}&branchIds={2}">{0}</a>')
                        .format(cellvalue, rowObject.partyIds, rowObject.branchIds, rowObject.userId)
            }},
            {label: '推荐党支部<div>所含党员数</div>', name: 'memberCount'},
            {label: '推荐党支部<div>应参会党员数</div>', name: 'expectMemberCount'},
            {label: '推荐党支部实参会党员数<div style="font-size: 8px">（推荐提名的党员数）</div>', name: 'actualMemberCount', width: 180},
            {
                label: '性别', name: 'gender', width: 50, formatter: $.jgrid.formatter.GENDER
            },
            {label: '民族', name: 'nation', width: 60},
            /*{label: '学历学位', name: '_learn'},*/
            {label: '职称', name: 'proPost', width: 200},
            {label: '出生年月', name: 'birth', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}},
            {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE},
            {
                label: '入党时间',
                name: 'growTime',
                width: 120,
                sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y-m-d'}
            }/*,{
                label: '参加工作时间',
                name: 'workTime',
                width: 120,
                sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m'}
            }*/,{
                label: '所在单位及职务',
                name: '_title',
                width: 350,
                align: 'left',
                formatter: function (cellvalue, options, rowObject) {
                    return ($.trim(rowObject.title) == '') ? $.trim(rowObject.extUnit) : $.trim(rowObject.title);
                }
            }
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('#searchForm select[name=userId]'));
</script>