<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>添加参训人员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetUnitTrain_batchAdd" autocomplete="off" disableautocomplete
          id="modalForm" method="post">
        <input type="hidden" name="projectId" value="${cetUnitProject.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span> 参训人员类型</label>
            <div class="col-xs-6">
                <select required data-rel="select2" name="traineeTypeId" data-placeholder="请选择" data-width="272">
                    <option></option>
                    <c:forEach items="${traineeTypeMap}" var="entity">
                        <c:if test="${entity.value.code!='t_reserve' && entity.value.code!='t_candidate'}">
                            <option value="${entity.value.id}">${entity.value.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
                <script type="text/javascript">
                    $("#modalForm select[name=traineeTypeId]").val(${cetUnitTrain.traineeTypeId});
                </script>
            </div>
        </div>
        <div class="form-group">
            <div class="col-xs-12">
                <div id="tree3" style="max-height: 500px"></div>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> 确定
    </button>
</div>
<script>
    var traineeTypeMap = ${cm:toJSONObject(traineeTypeMap)};
    $('#modalForm [data-rel="select2"]').select2();

    $("#modalForm select[name=traineeTypeId]").change(function () {

        var selectTreeURL;
        var traineeTypeId = $(this).val();
        if (traineeTypeId > 0) {
            var traineeType = traineeTypeMap[traineeTypeId];
            switch (traineeType.code) {
                case 't_cadre':
                    selectTreeURL = "${ctx}/cet/selectCadres_tree";
                    break;
                case 't_party_member':
                    selectTreeURL = "${ctx}/cet/selectPartyMembers_tree";
                    break;
                case 't_branch_member':
                    selectTreeURL = "${ctx}/cet/selectBranchMembers_tree";
                    break;
                case 't_organizer':
                    selectTreeURL = "${ctx}/cet/selectOrganizers_tree";
                    break;
            }
        }
        _loadTree(selectTreeURL);
    });

    function _loadTree(selectTreeURL) {

        $("#tree3").html('')
        if ($.isBlank(selectTreeURL)) {
            return;
        }
        $.getJSON(selectTreeURL, function (data) {
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
            $("#tree3").dynatree("getTree").reload();
        });
    }

    var userIds = [];
    $("#submitBtn").click(function () {
        userIds = $.map($("#tree3").dynatree("getSelectedNodes"), function (node) {
            if (!node.data.isFolder && !node.data.hideCheckbox)
                return node.data.key;
        });
        if (userIds.length == 0) {
            $.tip({
                $target: $("#tree3"),
                at: 'left center', my: 'right center',
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