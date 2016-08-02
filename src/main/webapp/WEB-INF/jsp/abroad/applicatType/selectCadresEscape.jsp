<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h4>还没有分配申请人身份的干部
    </h4>
</div>
<div class="modal-body">
    <div id="tree3"></div>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
    </div>
<script>
    $.getJSON("${ctx}/applicatType/selectCadresEscape_tree", {}, function (data) {
        var treeData = data.tree.children;
        console.log(treeData)
        if(treeData.length==0){
            $("#tree3").html('<div class="well">所有干部都已分配申请人身份</div>');
        }else {
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
        }
    });
</script>