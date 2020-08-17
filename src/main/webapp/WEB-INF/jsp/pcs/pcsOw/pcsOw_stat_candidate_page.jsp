<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/pcs/pcsOw"
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

                                <button class="jqOpenViewBtn btn btn-success" data-url="${ctx}/pcs/pcsOw_recommend_detail"
                                        data-open-by="page"
                                        data-id-name="userId"
                                        data-querystr="&type=${type}&stage=${param.stage}">
                                    <i class="fa fa-info-circle"></i> 推荐详情
                                </button>
                                <shiro:hasPermission name="pcsOw:admin">
                                <c:set var="_stage" value="${param.stage==PCS_STAGE_FIRST?'二下':''}
                                ${param.stage==PCS_STAGE_SECOND?'三下':''}"/>
                            <button data-url="${ctx}/pcs/pcsOw_choose"
                                    data-title="入选“${_stage}”名单"
                                    data-msg="确定将这{0}位被推荐人列入“${_stage}”名单吗？"
                                    data-grid-id="#jqGrid"
                                    data-querystr="stage=${param.stage}&type=${type}&isChosen=1" ${hasIssue?"disabled":""}
                                    class="jqBatchBtn btn btn-warning">
                                <i class="fa fa-level-down"></i>
                                <c:if test="${param.stage!=PCS_STAGE_THIRD}">
                                入选“${_stage}”名单
                                </c:if>
                                <c:if test="${param.stage==PCS_STAGE_THIRD}">
                                    预备人选
                                </c:if>
                            </button>


                                <a style="margin-left: 20px" href="${ctx}/pcs/pcsOw_export?file=4-1&partyId=${param.partyId}&stage=${param.stage}&type=${type}" >
                                    <i class="fa fa-download"></i> ${PCS_USER_TYPE_MAP.get(type)}候选人初步人选推荐提名汇总表（“${PCS_STAGE_MAP.get(cm:toByte(param.stage))}”阶段）</a>

                                <a style="margin-left: 20px" href="${ctx}/pcs/pcsOw_export?file=5-1&partyId=${param.partyId}&stage=${param.stage}&type=${type}" >
                                    <i class="fa fa-download"></i> 附表5-${type}. ${PCS_USER_TYPE_MAP.get(type)}候选人推荐提名汇总表（报上级用）</a>

                                </shiro:hasPermission>

                            </c:if>
                            <c:if test="${cls==4}">
                                <shiro:hasPermission name="pcsOw:admin">
                                <button data-url="${ctx}/pcs/pcsOw_choose"
                                        data-title="删除名单"
                                        data-msg="确定将这{0}位被推荐人从名单中去除吗？"
                                        data-grid-id="#jqGrid"
                                        data-querystr="stage=${param.stage}&type=${type}&isChosen=0" ${hasIssue?"disabled":""}
                                        class="jqBatchBtn btn btn-danger">
                                    <i class="fa fa-times"></i> 删除
                                </button>
                                <c:if test="${param.stage!=PCS_STAGE_THIRD}">
                                    &nbsp;&nbsp;
                                <button data-url="${ctx}/pcs/pcsOw_issue" id="issueBtn"
                                        class="jqBatchBtn btn btn-success" ${hasIssue?"disabled":""}>
                                    <i class="fa fa-level-down"></i>
                                        ${hasIssue?"已下发名单":"下发名单"}
                                </button>
                                </c:if>
                                <a class="popupBtn btn btn-warning btn-sm ${hasIssue?"":"disabled"}"
                                   data-url="${ctx}/pcs/pcsAdmin_msg?cls=2"><i class="fa fa-send"></i> 短信提醒</a>

                                <a style="margin-left: 20px" href="${ctx}/pcs/pcsOw_export?file=7-1&partyId=${param.partyId}&stage=${param.stage}&type=${type}" >
                                    <i class="fa fa-download"></i> ${PCS_USER_TYPE_MAP.get(type)}候选人初步人选名册（“${param.stage==PCS_STAGE_FIRST?"二下":"三下"}”名单）</a>
                                </shiro:hasPermission>
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
    #jqGrid_actualMemberCount, #jqGrid_branchCount, #jqGrid_memberCount, #jqGrid_expectMemberCount{
        padding: 0;
        font-size: 11px;
    }

    .modal .tip ul{
        margin-left: 50px;
    }
    .modal .tip ul li{
        /*font-size: 13px;*/
        text-align: left;
    }
</style>
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
                    $.post("${ctx}/pcs/pcsOw_issue", {stage:${param.stage}}, function (ret) {
                        if (ret.success) {
                            $.loadPage({url: "${ctx}/pcs/pcsOw?cls=${cls}&stage=${param.stage}&type=${type}"});
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
        //multiboxonly: false,
        url: '${ctx}/pcs/pcsOw_stat_candidate_data?callback=?&stage=${param.stage}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'code', width: 120, frozen:true,cellattr:function(rowId, val, rowObject, cm, rdata) {
                <c:if test="${cls==2}">
                if(rowObject.chosenId>0) return "class='success'";
                </c:if>
            }},
            {label: '被推荐提名人姓名', name: 'realname', width: 150, frozen:true},
            <c:if test="${cls==4}">
           <shiro:hasPermission name="pcsOw:admin">
            {
                label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{url: "${ctx}/pcs/pcsCandidateChosen_changeOrder"}, frozen: true
            },
            </shiro:hasPermission>
            </c:if>
            {label: '推荐提名<div>的党支部数</div>', name: 'branchCount'},
  /*          {label: '支部列表', name: '_branchCount', width: 120,formatter: function (cellvalue, options, rowObject) {

               return  ('<a href="javascript:;" class="popupBtn" data-width="750" ' +
                'data-url="${ctx}/pcs/pcsOw_branchs?userId={0}&type=${type}&stage=${param.stage}&recommend={1}">已推荐</a>')
                        .format(rowObject.userId, 1)

                +  ('&nbsp;&nbsp;<a href="javascript:;" class="popupBtn" data-width="750" ' +
                        'data-url="${ctx}/pcs/pcsOw_branchs?userId={0}&type=${type}&stage=${param.stage}&recommend={1}">未推荐</a>')
                                .format(rowObject.userId, 0);
            }},*/
            {label: '推荐党支部<div>所含党员数</div>', name: 'memberCount'},
            {label: '推荐党支部<div>应参会党员数</div>', name: 'expectMemberCount'},
            {label: '推荐党支部实参会党员数<div style="font-size: 8px">（推荐提名的党员数）</div>', name: 'actualMemberCount', width: 180},
            {
                label: '性别', name: 'gender', width: 50, formatter: $.jgrid.formatter.GENDER
            },
            {label: '民族', name: 'nation', width: 60},
            {label: '职称', name: 'proPost', width: 200},
            {label: '出生年月', name: 'birth', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE, formatoptions: {newformat: 'Y.m'}},
            {
                label: '入党时间',
                name: 'growTime',
                width: 120,
                sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
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
            }<c:if test="${param.stage == PCS_STAGE_SECOND || param.stage == PCS_STAGE_THIRD}">
            ,{
                label: '备注',
                name: '_remark',
                width: 300,
                align: 'left',
                formatter: function (cellvalue, options, rowObject) {
                    return rowObject.isFromStage ? "“${param.stage == PCS_STAGE_SECOND?"二下":"三下"}”名单成员" : "另选他人";
                }
            }
            </c:if>, {hidden: true, key: true, name: 'userId'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('#searchForm select[name=userId]'));
</script>