<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>选择任免对象</h3>
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
    var scCommitteeVotes = ${cm:toJSONArray(scCommitteeVotes)};
    $("#jqGridPopup").jqGrid({
        pager: null,
        rownumbers: true,
        multiboxonly: false,
        height: 400,
        width:1170,
        datatype: "local",
        rowNum: scCommitteeVotes.length,
        data: scCommitteeVotes,
        colModel: [
            {label: '党委常委会日期', name: 'holdDate', width: 120, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            { label:'类别', name: 'type', width: 80, formatter:function(cellvalue, options, rowObject){
                return _cMap.DISPATCH_CADRE_TYPE_MAP[cellvalue];
            },frozen:true },
            { label:'工作证号', name: 'user.code'},
            { label:'姓名', name: 'user.realname'},
            {label: '原任职务', name: 'originalPost', width: 240,align:'left'},
            { label:'职务', name: 'post', width: 240,align:'left' }, {hidden: true, name: 'dispatchUserId'}
        ],loadComplete:function(){
            $.each(scCommitteeVotes, function(i, v){
                if (v.dispatchUserId>0) {
                    $("#jqg_jqGridPopup_"+ v.id).prop("disabled", true);
                }
            })
        },

        onSelectAll:function(rowids,status) {
            if (status == true) {
                var rowIds = $("#jqGridPopup").jqGrid('getDataIDs');
                for (var i = 0; i < rowIds.length; i++) {
                    rowData = $("#jqGridPopup").jqGrid("getRowData", rowIds[i]);
                    if (rowData.dispatchUserId>0) {
                        $("#jqGridPopup").jqGrid("setSelection", rowIds[i], false);
                    }
                }
            }
        },
        onSelectRow:function(rowid,status) {
            if (status == true) {
                var rowData = $("#jqGridPopup").jqGrid('getRowData', rowid);
                if (rowData.dispatchUserId>0) {
                    $("#jqGridPopup").jqGrid("setSelection", rowid, false);
                }
            }
        }
    });





    $("#modal #selectBtn").click(function(){

        var voteIds = $("#jqGridPopup").getGridParam("selarrrow");
        if(voteIds.length==0) return;

        $.post("${ctx}/sc/scDispatch_selectUser", {voteIds: voteIds}, function (ret) {
            if (ret.success) {
                $("#modal").modal('hide');
                $.each(ret.votes, function(i, vote){

                    console.log("vote.dispatchUserId=" + vote.dispatchUserId)
                    var $jqGrid = $("#jqGrid"+vote.type);

                    //console.log(vote.type + " " + vote.id)
                    //console.log($jqGrid.getRowData(vote.id))

                    var rowData =$jqGrid.getRowData(vote.id);
                    if (rowData.id == undefined) {
                        //console.log(vote)
                        $jqGrid.jqGrid("addRowData", vote.id, vote, "last");
                    }
                })
                //$jqGrid.closest(".panel").find(".tip .count").html($jqGrid.jqGrid("getDataIDs").length);
                clearJqgridSelected();
                //$("#jqGridPopup").resetSelection();
            }
        })
    });
</script>