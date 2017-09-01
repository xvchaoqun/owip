<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>选择建议人选</h3>
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
            {label: '工作证号', name: 'code', width: 120, frozen:true},
            {label: '姓名', name: 'realname', width: 150, frozen:true},
            {
                label: '性别', name: 'gender', width: 50, formatter: $.jgrid.formatter.GENDER
            },
            {label: '民族', name: 'nation', width: 60},
            {label: '学历学位', name: '_learn'},
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
            }/*,{
                label: '参加工作时间',
                name: 'workTime',
                width: 120,
                sortable: true,
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            }*/,{
                label: '所在单位及职务',
                name: '_title',
                width: 350,
                align: 'left',
                formatter: function (cellvalue, options, rowObject) {
                    return ($.trim(rowObject.title) == '') ? $.trim(rowObject.extUnit) : $.trim(rowObject.title);
                }
            }, {hidden: true, key: true, name: 'userId'}
        ]
    });

    $("#modal #selectBtn").click(function(){

        var userIds = $("#jqGridPopup").getGridParam("selarrrow");
        if(userIds.length==0) return;

        var $jqGrid = $("#jqGrid${param.type==PCS_USER_TYPE_DW?"1":"2"}");
        $.post("${ctx}/pcsRecommend_selectUser", {userIds: userIds}, function (ret) {
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