<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>生成干部任免审批表</h3>
</div>
<div class="modal-body rownumbers">
    <table id="jqGridPopup" class="table-striped"></table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="button" id="selectBtn" class="btn btn-primary" value="生成"/>
</div>
<style>
    .rownumbers #jqGridPopup_cb {
        text-align: left !important;
    }
</style>
<script>
    var selectedVotes = ${cm:toJSONArray(selectedVotes)};
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
            {label: '原任职务', name: 'originalPost', width: 300,align:'left'},
            { label:'职务', name: 'post', width: 300,align:'left' }, {hidden: true, key: true, name: 'id'}
        ],
        gridComplete:function(){
            selectedVotes.forEach(function (item, i) {
                $("#jqGridPopup").jqGrid("setSelection", item.voteId);
            });
        }
    });

    //clearJqgridSelected();
    $("#modal #selectBtn").click(function(){

        var voteIds = $("#jqGridPopup").getGridParam("selarrrow");
        if(voteIds.length==0) return;
        $.post("${ctx}/sc/scAdArchive_checkVotes", {archiveId:'${param.archiveId}', voteIds: voteIds}, function (ret) {
            if (ret.success) {
                $("#modal").modal('hide');
                $.loadView("${ctx}/sc/scAdArchive_preview?archiveId=${param.archiveId}&voteIds[]=" + voteIds);
            }
        })
    });
</script>