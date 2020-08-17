<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>选择党代表</h3>
</div>
<div class="modal-body rownumbers">
    <table id="jqGridPopup" class="table-striped"></table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="button" id="selectBtn" class="btn btn-primary" value="选择"/>
</div>
<style>
    .rownumbers #jqGridPopup_cb {
        text-align: left !important;
    }
</style>
<script>
    var candidates = ${cm:toJSONArray(candidates)};
    $("#jqGridPopup").jqGrid({
        pager: null,
        rownumbers: true,
        multiboxonly: false,
        height: 400,
        width:865,
        datatype: "local",
        rowNum: candidates.length,
        data: candidates,
        colModel: [
            {
                label: '党代表类型', name: 'type', width: 150, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--';
                return _cMap.PCS_PR_TYPE_MAP[cellvalue]
            }
            },
            {label: '工作证号', name: 'code', width: 120, frozen: true},
            {label: '被推荐人姓名', name: 'realname', width: 120, frozen: true},
            {label: '票数', name: 'vote3', width: 80},
            {
                label: '性别', name: 'gender', width: 50, formatter: $.jgrid.formatter.GENDER
            },
            {label: '出生年月', name: 'birth', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE, formatoptions: {newformat: 'Y.m'}},
            {label: '民族', name: 'nation', width: 60},
            {
                label: '学历', name: '_learn', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.userType == '${PCS_PR_USER_TYPE_CADRE}') {
                    return $.jgrid.formatter.MetaType(rowObject.eduId);
                } else if (rowObject.userType == '${PCS_PR_USER_TYPE_TEACHER}') {
                    return $.trim(rowObject.education);
                }
                return "-"
            }
            },/*
            {
                label: '参加工作时间',
                name: 'workTime',
                width: 120,
                sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m'}
            },*/
            {
                label: '入党时间',
                name: 'growTime',
                width: 120,
                sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {
                label: '职别', name: 'proPost', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.userType == '${PCS_PR_USER_TYPE_CADRE}') {
                    return '干部';
                } else if (rowObject.userType == '${PCS_PR_USER_TYPE_TEACHER}') {
                    return (rowObject.isRetire) ? "离退休" : $.trim(cellvalue);
                }
                return $.trim(rowObject.eduLevel);
            }
            },
            {
                label: '职务',
                name: 'post',
                width: 150,
                align: 'left', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.userType == '${PCS_PR_USER_TYPE_CADRE}') {
                    return $.trim(cellvalue);
                }
                return "-"
            }
            }, {hidden: true, key: true, name: 'userId'}
        ]
    });

    $("#modal #selectBtn").click(function(){

        var userIds = $("#jqGridPopup").getGridParam("selarrrow");
        if(userIds.length==0) return;

        var $jqGrid = $("#jqGrid2");
        $.post("${ctx}/pcs/pcsPrList_selectUser", {userIds: userIds}, function (ret) {
            if (ret.success) {
                $("#modal").modal('hide');
                $.each(ret.candidates, function(i, candidate){
                    var rowData =$jqGrid.getRowData(candidate.userId);
                    if (rowData.userId == undefined) {
                        //console.log(candidate)
                        $jqGrid.jqGrid("addRowData", candidate.userId, candidate, "last");
                    }
                })
                $jqGrid.closest(".panel").find(".tip .count").html($jqGrid.jqGrid("getDataIDs").length);
                clearJqgridSelected();
                //$("#jqGridPopup").resetSelection();
            }
        })
    });
</script>