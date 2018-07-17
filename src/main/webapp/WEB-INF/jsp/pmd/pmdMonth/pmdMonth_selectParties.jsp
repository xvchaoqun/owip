<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h4>缴费月份"${cm:formatDate(pmdMonth.payMonth, "yyyy年MM月")}"所包含的缴费党委
    </h4>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmd/pmdMonth_selectParties" id="modalForm" method="post">
        <div id="tree3" style="min-height: 400px"></div>
    </form>
</div>
<div class="modal-footer">
    <div style="position: absolute; float:left; left:10px;padding-top: 5px">
        <input type="button" id="btnSelectAll3" class="btn btn-success btn-xs" value="全选"/>
        <input type="button" id="btnDeselectAll3" class="btn btn-danger btn-xs" value="全不选"/>
    </div>
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 同步数据中，请稍后"
            data-success-text="设置成功" autocomplete="off"> 确定
    </button>
    <div style="position: absolute;font-size: 16pt;bottom: 15px;right: 20px;">
        已选<span id="partyCount" style="color: darkred;font-weight: bolder">0</span>个党委</div>
</div>
<link href="${ctx}/extend/js/fancytree/skin-win8/ui.fancytree.css" rel="stylesheet"/>
<script src="${ctx}/extend/js/fancytree/jquery.fancytree-all.min.js"></script>
<script>

    $("#tree3").fancytree({
        checkbox: true,
        selectMode: 3,
        icon: false,
        source: {
            url: "${ctx}/pmd/pmdMonth_selectParties_tree?monthId=${param.monthId}",
            cache: false
        },
        init: function(event, data, flag) {
            $("#partyCount").html(data.tree.getSelectedNodes().length)
        },
        select: function(event, data) {
            $("#partyCount").html(data.tree.getSelectedNodes().length)
        },
        cookieId: "fancytree-Cb3",
        idPrefix: "fancytree-Cb3-"
    });

    var _tree = $("#tree3").fancytree("getTree");
    $("#btnDeselectAll3").click(function () {
        _tree.visit(function (node) {
            node.setSelected(false);
        });
        return false;
    });
    $("#btnSelectAll3").click(function () {
        _tree.visit(function (node) {
            node.setSelected(true);
        });
        return false;
    });

    $("#submitBtn").click(function () {$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {

            var partyIds = $.map(_tree.getSelectedNodes(true), function (node) {
                return node.key;
            });
            /*console.log(partyIds);
             return;*/
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                data: {partyIds: partyIds, monthId: "${param.monthId}"},
                success: function (data) {
                    if (data.success) {
                        $btn.button("success").addClass("btn-success");
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>