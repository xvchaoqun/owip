<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>选择任免对象<span class="red bolder">（如信息有误， 请到“常委会讨论议题”中修改）</span></h3>
</div>
<div class="modal-body rownumbers">
    <select data-rel="select2"
            name="userId" data-placeholder="请输入姓名">
        <option></option>
        <c:forEach items="${scCommitteeVotes}" var="v">
            <option value="${v.user.id}">${v.user.realname}</option>
        </c:forEach>
    </select>
    <input type="checkbox" id="hideCheck" class="big" style="margin-left: 30px;"> 隐藏不可选人员
    <div class="space-4"></div>
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
    var $selectUser =$('#modal [data-rel="select2"]').select2();
    $selectUser.on("change",function(e){
        var userId = $(this).select2("data")[0]['id']||'';
        //console.log(userId)

        $.each(scCommitteeVotes, function(i, v){

            if($("#hideCheck").prop("checked") && v.dispatchUserId>0 && $.inArray(v.id, voteIds)==-1){
                $("#modal [role=row][id="+ v.id + "]").hide();
                return false;
            }

            if($.trim(userId)!='' && v.user.userId != userId) {
                $("#modal [role=row][id="+ v.id + "]").hide();
                //$("#jqGridPopup").setRowData(c.userId, null, {display: 'none'});
            }else {
                $("#modal [role=row][id="+ v.id + "]").show();
                //$("#jqGridPopup").setRowData(c.userId, null, {display: 'block'});
            }
        })
    })
    $("#hideCheck").click(function(){
        //$selectUser.val(null).trigger("change")
        if($(this).prop("checked")){
            $.each(scCommitteeVotes, function(i, v){
                //console.log("v.id=" + v.id + " voteIds=" + voteIds + " " + $.inArray(v.id, voteIds))
                if (v.dispatchUserId>0 && $.inArray(v.id, voteIds)==-1) {
                    $("#modal [role=row][id="+ v.id + "]").hide();
                }
            })
        }else{
            $selectUser.trigger("change")
        }
    })
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
            {label: '原任职务', name: 'originalPost', width:300,align:'left'},
            { label:'任免职务', name: 'post', width: 380,align:'left' },
            {hidden: true, name: 'dispatchUserId'}, {hidden: true, key: true, name: 'id'}
        ],
        loadComplete:function(){
            $.each(scCommitteeVotes, function(i, v){
                if (v.dispatchUserId>0 && $.inArray(v.id, voteIds)==-1) {
                    $("#jqg_jqGridPopup_"+ v.id).prop("disabled", true);
                    $("tr#"+ v.id).addClass("danger");
                }
            })
        },
        onSelectAll:function(rowids,status) {
            if (status == true) {
                var rowIds = $("#jqGridPopup").jqGrid('getDataIDs');
                for (var i = 0; i < rowIds.length; i++) {
                    rowData = $("#jqGridPopup").jqGrid("getRowData", rowIds[i]);
                    if (rowData.dispatchUserId>0 && $.inArray(parseInt(rowIds[i]), voteIds)==-1) {
                        $("#jqGridPopup").jqGrid("setSelection", rowIds[i], false);
                    }
                }
            }
        },
        onSelectRow:function(rowid,status) {
            if (status == true) {
                //console.log("rowid=" + rowid + " voteIds=" + voteIds +  " " + $.inArray(parseInt(rowid), voteIds))
                var rowData = $("#jqGridPopup").jqGrid('getRowData', rowid);
                if (rowData.dispatchUserId>0 && $.inArray(parseInt(rowid), voteIds)==-1) {
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

                    //console.log("vote.dispatchUserId=" + vote.dispatchUserId)
                    var $jqGrid = $("#jqGrid"+vote.type);

                    //console.log(vote.type + " " + vote.id)
                    //console.log($jqGrid.getRowData(vote.id))

                    var rowData =$jqGrid.getRowData(vote.id);
                    if (rowData.id == undefined) {
                        //console.log(vote)
                        $jqGrid.jqGrid("addRowData", vote.id, vote, "last");
                    }
                })
                if(ret.votes.length>0){
                    $("#dispatch-widget-box").addClass("collapsed");
                    $("#users-widget-box").removeClass("collapsed");
                }
                //$jqGrid.closest(".panel").find(".tip .count").html($jqGrid.jqGrid("getDataIDs").length);
                clearJqgridSelected();
                //$("#jqGridPopup").resetSelection();

                $("#selectedCount").html( $("#jqGrid1").jqGrid("getDataIDs").length
                    + $("#jqGrid2").jqGrid("getDataIDs").length)
            }
        })
    });
</script>