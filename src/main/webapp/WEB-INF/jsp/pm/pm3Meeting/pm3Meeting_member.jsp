<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>选择请假人员</h3>
</div>
<div class="modal-body rownumbers">
    <select data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects?partyId=${param.partyId}&branchId=${param.branchId}&status=${MEMBER_STATUS_NORMAL}"
            name="userId" data-placeholder="请输入账号或姓名或工号">
        <option></option>
    </select>
    <span class="pull-right" style="padding: 10px 20px">已选 <span id="selectCount" style="font-size: 16px;font-weight: bolder">0</span> 人</span>
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

   var membersViews = ${cm:toJSONArray(membersViews)};
    var $selectPr = $.register.user_select($("#modal select[name=userId]"));

    $selectPr.on("change",function(e){

        //console.log($(this).select2("data")[0])
        var userId = $(this).select2("data")[0]['id']||'';
        //console.log(userId)
        $.each(membersViews, function(i, c){
            if($.trim(userId)!='' && c.userId != userId) {
                $("#modal [role=row][id="+ c.userId + "]").hide();
                //$("#jqGridPopup").setRowData(c.userId, null, {display: 'none'});
            }else {
                $("#modal [role=row][id="+ c.userId + "]").show();
                //$("#jqGridPopup").setRowData(c.userId, null, {display: 'block'});
            }
        })
    })

    $("#jqGridPopup").jqGrid({
        pager: null,
        rownumbers: true,
        multiboxonly: false,
        height: 400,
        width:865,
       datatype: "local",
        rowNum: membersViews.length,
        data: membersViews,
        colModel: [
            {label: '工作证号', name: 'code', width: 200, frozen: true},
            {label: '党员姓名', name: 'realname', width: 150, frozen: true},
            {label: '党内职务', name: 'partyPost', width: 200, align: 'left'
                // ,formatter:function(cellvalue, options, rowObject){
                //    return _cMap.MEMBER_TYPE_MAP[cellvalue];
                // },frozen:true
            },
            {label: '手机号', name: 'mobile', width: 200},
            {hidden: true, key: true, name: 'userId'}
        ],
        onSelectRow: function(id,status){
            var key=false;
            var userIds = $("#jqGridPopup").getGridParam("selarrrow");
            $("#selectCount").html(userIds==undefined?0:userIds.length);
        },
        onSelectAll: function(aRowids,status){

            var userIds = $("#jqGridPopup").getGridParam("selarrrow");
            $("#selectCount").html(userIds==undefined?0:userIds.length);
        }
    });
    var table=$("#absentTable tbody tr");
    table.each(function(){
        $("#jqGridPopup").jqGrid('setSelection', $(this).data("user-id"), true);
    });

    $("#modal #selectBtn").click(function(){

        var userIds = $("#jqGridPopup").getGridParam("selarrrow");
        if(userIds.length==0) return;

        var users = [];
        $.each(userIds, function(i, userId){
            if($("#absentTable tbody tr[data-user-id="+userId+"]").length>0&&${param.type==2}) return true;
            var user = $("#jqGridPopup").getRowData(userId);
            users.push(user);
        })

        $("#absentTable tbody").append(_.template($("#seconder_tpl").html())({users: users}));
        users.forEach(function(user){

            $("#attendTable tbody tr").each(function(){
                if(user.userId==$(this).data("user-id")){
                    $(this).remove();
                }
            });
        });
        $("#modalForm input[name=absentNum]").val($("#absentTable tbody tr").length);

        $("#modal").modal('hide');
    });
</script>