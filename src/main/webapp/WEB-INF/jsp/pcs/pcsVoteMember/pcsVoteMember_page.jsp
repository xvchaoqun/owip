<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="multi-row-head-table myTableDiv"
             data-url-page="${ctx}/pcs/pcsVoteMember_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="space-4"></div>
            <button data-url="${ctx}/pcs/pcsVoteCandidate_choose"
                    data-title="删除名单"
                    data-msg="确定将这{0}位候选人从名单中去除吗？"
                    data-grid-id="#jqGrid2"
                    data-querystr="type=${param.type}&isChosen=0"
                    class="jqBatchBtn btn btn-danger">
                <i class="fa fa-times"></i> 删除
            </button>

                <div style="line-height: 30px;" class="pull-right">
                    <input class="orderCheckbox" ${param.orderType!=1?"checked":""} type="checkbox" value="0"> 按姓氏笔画排序
                    <input class="orderCheckbox" ${param.orderType==1?"checked":""} type="checkbox" value="1"> 按得票多少排序
                </div>

                <div class="rownumbers">
                <div class="space-4"></div>

                <table id="jqGrid2" class="jqGrid2 table-striped"  data-height-reduce="100"></table>
                </div>
        </div>
    </div>
</div>
<style>
    .type-select{
        padding: 5px 0 0 5px;
        float: left;
        margin-right: 50px;
    }
</style>
<script>
    $(".orderCheckbox").click(function(){
        $("#step-body-content-view").loadPage({url:"${ctx}/pcs/pcsVoteMember?type=${param.type}&orderType="+$(this).val()})
    })

    $("#jqGrid2").jqGrid({
        pager: "#jqGridPager2",
        rownumbers: true,
        url: '${ctx}/pcs/pcsVoteMember_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '候选人姓名', name: 'realname', width: 120, frozen: true},
            {label: '赞成票数', name: 'agree', width: 80},
            {label: '不赞成票数', name: 'degree', width: 110},
            {label: '弃权票数', name: 'abstain', width: 80},
            {label: '部分无效票数<br/>(模糊无法辨认)', name: 'invalid', width: 120},
            {
                label: '性别', name: 'gender', width: 50, formatter: $.jgrid.formatter.GENDER
            },
            {label: '民族', name: 'nation', width: 80},
            {label: '职称', name: 'proPost', width: 200},
            {label: '出生年月', name: 'birth', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '年龄', name: 'birth', width: 80, formatter: $.jgrid.formatter.AGE, formatoptions: {newformat: 'Y.m',baseDate: '${_finishDate}'}},
            {
                label: '入党时间',
                name: 'growTime',
                width: 120,
                sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {label: '所在单位及职务', name: 'title', width: 350, align:'left'}, {hidden: true, key: true, name: 'userId'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
</script>