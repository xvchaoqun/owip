<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h4>选择任务对象</h4>
</div>
<div class="modal-body">
    <div id="tree3" style="min-height: 400px"></div>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="selectCadresBtn" type="button" class="btn btn-primary">确定</button></div>
<script>
    var $jqGrid = $("#jqGrid2");
    $.getJSON("${ctx}/oa/oaTask_selectCadres_tree", {userIds: "${param.userIds}"}, function (data) {
        var treeData = data.tree.children;
        $("#tree3").dynatree({
            checkbox: true,
            selectMode: 3,
            children: treeData,
            onSelect: function (select, node) {

                node.expand(node.data.isFolder && node.isSelected());
            },
            onPostInit:function(isReloading, isError){
                var $this = this;
                var selectedUserIds = $jqGrid.jqGrid("getDataIDs");
                //console.log(selectedUserIds)
                $.each(selectedUserIds, function(i, userId){
                    $this.selectKey(userId,true);
                })
            },
            cookieId: "dynatree-Cb3",
            idPrefix: "dynatree-Cb3-"
        });
    });

    $("#selectCadresBtn").click(function () {

        var userIds = $.map($("#tree3").dynatree("getSelectedNodes"), function (node) {
            if (!node.data.isFolder && !node.data.hideCheckbox)
                return node.data.key;
        });

        $.post("${ctx}/oa/oaTask_selectCadres", {userIds: userIds}, function (ret) {
            if (ret.success) {
                $("#modal").modal('hide');
                $.each(ret.cadres, function(i, cadre){

                    var user = {userId:cadre.userId,
                        realname:cadre.realname, code:cadre.code,
                        mobile:cadre.mobile, title:cadre.title}
                    var rowData =$jqGrid.getRowData(user.userId);

                    if (rowData.userId == undefined) {
                        //console.log(vote)
                        $jqGrid.jqGrid("addRowData", user.userId, user, "last");
                    }
                })

                $jqGrid.closest(".widget-box").find(".tip .count").html($jqGrid.jqGrid("getDataIDs").length);
                clearJqgridSelected();
            }
        })
    });
</script>