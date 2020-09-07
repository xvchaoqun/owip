<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pmd/constants.jsp"%>
<div class="modal-header">
    <button id="closeBtn" type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>席位制更新</h3>
</div>
<div class="modal-body">
    <table class="jqGrid2 table-striped" id="jqGrid_cgMemberUpdate"></table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="updateBtn" class="btn btn-primary">
        <i class="fa fa-check"></i> 更新</button>
</div>
<script>
    $("#jqGrid_cgMemberUpdate").jqGrid({
        pager: null,
        ondblClickRow: function () {
        },
        height: 300,
        datatype: "local",
        data:${cm:toJSONArray(userList)},
        colModel: [
            {label: '委员会和领导小组名称', name: 'cgTeamName',width: 250,align: 'left'},
            {label: '席位', name: 'seat',width: 350,align: 'left'},
            {label: '关联岗位名称',name: 'unitPostName', width: 250,align: 'left'},
            {label: '原任职干部姓名', name: 'oldRealname',width: 120},
            {label: '现任职干部姓名', name: 'newRealname',width: 120},
        ]}).jqGrid("setFrozenColumns");

    $("#updateBtn").click(function(){

        var ids = $("#jqGrid_cgMemberUpdate").getGridParam("selarrrow");
        $.post("${ctx}/cg/cgMember_updateUser", {ids:ids}, function (ret) {
            if (ret.success) {
                $("#modal").modal('hide');
                $("#jqGrid").trigger("reloadGrid");
                $("#jqGrid_cgMember").trigger("reloadGrid");
                $("#jqGrid2").trigger("reloadGrid");
            }
    })
    });
</script>