<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="CET_UPPER_TRAIN_UNIT" value="<%=CetConstants.CET_UPPER_TRAIN_UNIT%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>添加参训人员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetUnitTrain_batchAdd" id="modalForm" method="post">
        <input type="hidden" name="projectId" value="${cetUnitProject.id}">
        <input type="hidden" name="addType" value="${addType}">
        <div class="form-group">
            <div class="col-xs-12">
                <div id="tree3" style="height: 550px"></div>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> 确定</button>
</div>
<script>
    $.getJSON("${ctx}/cet/cetUpperTrain_selectCadres_tree", {
        addType:${addType},
        upperType:${CET_UPPER_TRAIN_UNIT}
    }, function (data) {
        var treeData = data.tree;
        treeData.title = "选择参训人员"
        $("#tree3").dynatree({
            checkbox: true,
            selectMode: 3,
            children: treeData,
            onSelect: function (select, node) {

                node.expand(node.data.isFolder && node.isSelected());
            },
            cookieId: "dynatree-Cb3",
            idPrefix: "dynatree-Cb3-"
        });
    });
    var userIds = [];
    $("#submitBtn").click(function () {
        userIds = $.map($("#tree3").dynatree("getSelectedNodes"), function (node) {
            if (!node.data.isFolder && !node.data.hideCheckbox)
                return node.data.key;
        });
        if (userIds.length == 0) {
            $.tip({
                $target: $("#tree3"),
                at: 'bottom center', my: 'top center',
                msg: "请选择参训人员"
            });
        }

        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {

            if (userIds.length == 0) {
                return;
            }
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                data: {userIds: userIds},
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });

</script>