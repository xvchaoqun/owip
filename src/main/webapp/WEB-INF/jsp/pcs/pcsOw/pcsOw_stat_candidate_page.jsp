<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/pcsOw"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.userId|| not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="candidate-table tab-content">
                    <div class="tab-pane in active rownumbers">
                        <div class="jqgrid-vertical-offset buttons" style="padding: 10px">
                            <div class="type-select">
                                <span class="typeCheckbox ${type==PCS_USER_TYPE_DW?"checked":""}">
                                <input ${type==PCS_USER_TYPE_DW?"checked":""} type="checkbox" value="${PCS_USER_TYPE_DW}"> 党委委员
                                </span>
                                <span class="typeCheckbox ${type==PCS_USER_TYPE_JW?"checked":""}">
                                <input class="typeCheckbox" ${type==PCS_USER_TYPE_JW?"checked":""} type="checkbox" value="${PCS_USER_TYPE_JW}"> 纪委委员
                                </span>
                            </div>
                            <c:if test="${cls==2}">
                            <button data-url="${ctx}/pcsOw_choose"
                                    data-title="入选“二下”名单"
                                    data-msg="确定将这{0}位被推荐人列入“二下”名单吗？"
                                    data-grid-id="#jqGrid"
                                    data-querystr="stage=${param.stage}&type=${type}&isChosen=1" ${hasIssue?"disabled":""}
                                    class="jqBatchBtn btn btn-warning">
                                <i class="fa fa-level-down"></i> 入选“二下”名单
                            </button>

                                <a style="margin-left: 20px" href="${ctx}/pcsOw_export?file=4-1&partyId=${param.partyId}&stage=${param.stage}&type=${type}" >
                                    <i class="fa fa-download"></i> ${PCS_USER_TYPE_MAP.get(type)}（组织部用）</a>

                                <a style="margin-left: 20px" href="${ctx}/pcsOw_export?file=5-1&partyId=${param.partyId}&stage=${param.stage}&type=${type}" >
                                    <i class="fa fa-download"></i> ${PCS_USER_TYPE_MAP.get(type)}（报上级用）</a>
                            </c:if>
                            <c:if test="${cls==4}">
                                <button data-url="${ctx}/pcsOw_choose"
                                        data-title="删除名单"
                                        data-msg="确定将这{0}位被推荐人从名单中去除吗？"
                                        data-grid-id="#jqGrid"
                                        data-querystr="stage=${param.stage}&type=${type}&isChosen=0" ${hasIssue?"disabled":""}
                                        class="jqBatchBtn btn btn-danger">
                                    <i class="fa fa-times"></i> 删除
                                </button>
                                    &nbsp;&nbsp;
                                <button data-url="${ctx}/pcsOw_issue" id="issueBtn"
                                        class="jqBatchBtn btn btn-success" ${hasIssue?"disabled":""}>
                                    <i class="fa fa-level-down"></i>
                                        ${hasIssue?"已下发名单":"下发名单"}
                                </button>

                                <a style="margin-left: 20px" href="${ctx}/pcsOw_export?file=7-1&partyId=${param.partyId}&stage=${param.stage}&type=${type}" >
                                    <i class="fa fa-download"></i> ${PCS_USER_TYPE_MAP.get(type)}（组织部用）</a>
                            </c:if>
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
                                                <button type="button" class="resetBtn btn btn-warning btn-sm"
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
        <div id="item-content"></div>
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
        top: 44px !important;
    }
    #jqGrid_actualMemberCount{
        padding: 0;
    }

    .modal .tip ul{
        margin-left: 150px;
    }
    .modal .tip ul li{
        font-size: 25px;
        text-align: left;
    }
</style>
<script type="text/template" id="sort_tpl">
    <a href="javascript:;" class="jqOrderBtn" data-url="{{=url}}" data-id="{{=id}}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
    <input type="text" value="1" class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
           title="修改操作步长">
    <a href="javascript:;" class="jqOrderBtn" data-url="{{=url}}" data-id="{{=id}}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>
</script>
<script>

    $("#issueBtn").click(function () {
        bootbox.confirm({
            className: "confirm-modal",
            buttons: {
                confirm: {
                    label: '<i class="fa fa-check"></i> 确认下发',
                    className: 'btn-success'
                },
                cancel: {
                    label: '<i class="fa fa-reply"></i> 返回',
                    className: 'btn-default btn-show'
                }
            },
            message: "下发两委名单后不可以修改，请认真核实后下发。",
            callback: function (result) {
                if (result) {
                    $.post("${ctx}/pcsOw_issue", {stage:${param.stage}}, function (ret) {
                        if (ret.success) {
                            $.loadPage({url: "${ctx}/pcsOw?cls=${cls}&stage=${param.stage}&type=${type}"});
                        }
                    });
                }
            },
            title: '下发'
        });

        return false;
    })

    $(".typeCheckbox").click(function(){
        var $input = $("input", $(this));
        $("#searchForm input[name=type]").val($input.val());
        $("#searchForm .jqSearchBtn").click();
    })
    $("#jqGrid").jqGrid({
        rownumbers: true,
        multiboxonly: false,
        url: '${ctx}/pcsOw_stat_candidate_data?callback=?&stage=${param.stage}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'code', width: 120, frozen:true,cellattr:function(rowId, val, rowObject, cm, rdata) {
                <c:if test="${cls==2}">
                if(rowObject.chosenId>0) return "class='success'";
                </c:if>
            }},
            {label: '被推荐提名人姓名', name: 'realname', width: 150, frozen:true},
            {
                label: '排序', width: 80, index: 'sort', formatter: function (cellvalue, options, rowObject) {
                return _.template($("#sort_tpl").html().NoMultiSpace())({id: rowObject.chosenId, url:"${ctx}/pcsCandidateChosen_changeOrder"})
            }, frozen: true
            },
            {label: '推荐提名的党支部数', name: 'branchCount', width: 160},
            {label: '推荐党支部所含党员数', name: 'memberCount', width: 170},
            {label: '推荐党支部应参会党员数', name: 'expectMemberCount', width: 190},
            {label: '推荐党支部实参会党员数<div style="font-size: 8px">（推荐提名的党员数）</div>', name: 'actualMemberCount', width: 180},
            {
                label: '性别', name: 'gender', width: 50, formatter: $.jgrid.formatter.GENDER
            },
            {label: '民族', name: 'nation', width: 60},
            {label: '职称', name: 'proPost', width: 200},
            {label: '出生年月', name: 'birth', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE},
            {
                label: '入党时间',
                name: 'growTime',
                width: 120,
                sortable: true,
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },{
                label: '参加工作时间',
                name: 'workTime',
                width: 120,
                sortable: true,
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },{
                label: '所在单位及职务',
                name: '_title',
                width: 350,
                align: 'left',
                formatter: function (cellvalue, options, rowObject) {
                    return ($.trim(rowObject.title) == '') ? $.trim(rowObject.extUnit) : $.trim(rowObject.title);
                }
            }, {hidden: true, key: true, name: 'userId'}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $(window).triggerHandler('resize.jqGrid');
    })
    $.initNavGrid("jqGrid", "jqGridPager");
    register_user_select($('#searchForm select[name=userId]'));
</script>