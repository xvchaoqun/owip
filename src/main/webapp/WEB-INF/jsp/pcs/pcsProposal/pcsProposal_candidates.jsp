<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>邀请附议人</h3>
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
            {label: '工作证号', name: 'code', width: 120, frozen: true},
            {label: '代表姓名', name: 'realname', width: 150, frozen: true},
            {label: '所在单位', name: 'unitName', width: 160, align: 'left'},
            {label: '手机号', name: 'mobile', width: 120},
            {label: '邮箱', name: 'email', width: 200, align: "left"},
            {hidden: true, key: true, name: 'userId'}
        ]
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