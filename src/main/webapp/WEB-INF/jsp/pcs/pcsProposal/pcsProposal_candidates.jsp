<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>邀请附议人</h3>
</div>
<div class="modal-body rownumbers">
    <select data-rel="select2-ajax" data-ajax-url="${ctx}/pcsProposal_pr_selects"
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
    var candidates = ${cm:toJSONArray(candidates)};

    var $selectPr = register_user_select($("#modal select[name=userId]"))
    $selectPr.on("change",function(e){

        //console.log($(this).select2("data")[0])
        var userId = $(this).select2("data")[0]['id']||'';
        //console.log(userId)
        $.each(candidates, function(i, c){
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
        rowNum: candidates.length,
        data: candidates,
        colModel: [
            {label: '工作证号', name: 'code', width: 120, frozen: true},
            {label: '代表姓名', name: 'realname', width: 150, frozen: true},
            {label: '所在单位', name: 'unitName', width: 160, align: 'left'},
            {label: '手机号', name: 'mobile', width: 120},
            {label: '邮箱', name: 'email', width: 200, align: "left"},
            {hidden: true, key: true, name: 'userId'}
        ],
        onSelectRow: function(id,status){

            var userIds = $("#jqGridPopup").getGridParam("selarrrow");
            $("#selectCount").html(userIds==undefined?0:userIds.length);
        }
    });

    $("#modal #selectBtn").click(function(){

        var userIds = $("#jqGridPopup").getGridParam("selarrrow");
        if(userIds.length==0) return;

        var users = [];
        $.each(userIds, function(i, userId){
            if($("#seconders tbody tr[data-user-id="+userId+"]").length>0) return true;

            var user = $("#jqGridPopup").getRowData(userId);
            users.push(user);
        })

        $("#seconders tbody").append(_.template($("#seconder_tpl").html())({users: users}));

        $("#modal").modal('hide');
    });
</script>