<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="multi-row-head-table myTableDiv"
     data-url-page="${ctx}/pcsVoteGroup_data"
     data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">

    <div class="space-4"></div>
    本次大会实到代表${currentPcsConfig.committeeJoinCount}名，
    发出选票${type==PCS_USER_TYPE_DW?currentPcsConfig.dwSendVote:currentPcsConfig.jwSendVote}张，
    收回选票${type==PCS_USER_TYPE_DW?currentPcsConfig.dwBackVote:currentPcsConfig.jwBackVote}张，
    其中有效票${pcsVoteGroup.valid}张，无效票${pcsVoteGroup.invalid}张。
    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
        <a class="downloadBtn btn btn-info btn-sm"
           data-url="${ctx}/pcsVoteCandidate_export?cls=1&type=${type}"><i class="fa fa-download"></i>
            导出汇总单</a>
        <a class="downloadBtn btn btn-primary btn-sm"
           data-url="${ctx}/pcsVoteCandidate_export?cls=2&type=${type}"
           ><i class="fa fa-download"></i>
            导出汇总单（报总监票人）</a>

        <button data-url="${ctx}/pcsVoteCandidate_choose"
                data-title="当选${PCS_USER_TYPE_MAP.get(type)}"
                data-msg="确定将这{0}位候选人列入当选${PCS_USER_TYPE_MAP.get(type)}名单吗？"
                data-grid-id="#jqGrid2"
                data-querystr="type=${type}&isChosen=1"
                data-callback="_chooseCallback"
                class="jqBatchBtn btn btn-success btn-sm">
            <i class="fa fa-check"></i> 当选${PCS_USER_TYPE_MAP.get(type)}
        </button>
    </div>
    <div class="rownumbers">
        <div class="space-4"></div>
        <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="120"></table>
        <div id="jqGridPager2"></div>
    </div>
</div>
<script>
    function _chooseCallback(){
        $("#chooseAhref").click()
    }
    var candidates = ${cm:toJSONArray(candidates)};
    var joinCount = ${currentPcsConfig.committeeJoinCount==null?0:currentPcsConfig.committeeJoinCount};
    $("#jqGrid2").jqGrid({
        pager: null,
        datatype: "local",
        rowNum: candidates.length,
        data: candidates,
        rownumbers: true,
        multiboxonly: false,
        colModel: [
            {label: '候选人姓名', name: 'realname', width: 120},
            {label: '赞成票数', name: 'agree'},
            {label: '赞成票数比率', name: '_agree', formatter: function (cellvalue, options, rowObject) {
                return  parseFloat(rowObject.agree*100/joinCount).toFixed(2) + "%"
            },cellattr:function(rowId, val, rowObject, cm, rdata) {
                if(rowObject.agree*2>joinCount)
                    return "class='danger'";
            }, width: 130},
            {label: '不赞成票数', name: 'degree', width: 120, formatter: function (cellvalue, options, rowObject) {
                return rowObject.isFromStage ? cellvalue : "-";
            }},
            {label: '弃权票数', name: 'abstain', formatter: function (cellvalue, options, rowObject) {
                return rowObject.isFromStage ? cellvalue : "-";
            }},
            {label: '部分无效票数<br/>(模糊无法辨认)', name: 'invalid', width: 120, formatter: function (cellvalue, options, rowObject) {
                return rowObject.isFromStage ? cellvalue : "-";
            }},
            {label: '备注', name: '_remark', formatter: function (cellvalue, options, rowObject) {
                return rowObject.isFromStage ? "预备人选" : "另选他人";
            }}, {hidden: true, key: true, name: 'userId'}
        ]
    });
    $(window).triggerHandler('resize.jqGrid2');
</script>