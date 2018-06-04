<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/crs/constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h4>设置专家组成员</h4>
</div>
<div class="modal-body">
    <form class="form-horizontal"  action="${ctx}/crsPostExperts" id="modalForm" method="post">
        <c:forEach items="${CRS_POST_EXPERT_ROLE_MAP}" var="role">
            <div id="tree${role.key}" style="min-height: 200px; width: 200px;float: left;margin-right: 5px"></div>
        </c:forEach>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <a id="add_entity" class="btn btn-primary">更新</a></div>
<script>

    $(function(){
        <c:forEach items="${CRS_POST_EXPERT_ROLE_MAP}" var="role">
        $.getJSON("${ctx}/crsPostExperts_tree",{postId:"${param.postId}",
            role:"${role.key}"},function(data){
            var treeData = data.tree;
            treeData.title="${role.value}";
            $("#tree${role.key}").dynatree({
                checkbox: true,
                selectMode: 3,
                children: treeData,
                onSelect: function(select, node) {
                    node.expand(node.data.isFolder && node.isSelected());
                },
                cookieId: "dynatree-Cb3",
                idPrefix: "dynatree-Cb3-"
            });
        });
        </c:forEach>

        $("#add_entity").click(function(){

            $("#modal form").submit();return false;
        });

        $("#modal form").validate({
            submitHandler: function (form) {
                var headUserIds = $.map($("#tree${CRS_POST_EXPERT_ROLE_HEAD}").dynatree("getSelectedNodes"), function(node){
                    if(!node.data.isFolder && !node.data.hideCheckbox)
                        return node.data.key;
                });
                var leaderUserIds = $.map($("#tree${CRS_POST_EXPERT_ROLE_LEADER}").dynatree("getSelectedNodes"), function(node){
                    if(!node.data.isFolder && !node.data.hideCheckbox)
                        return node.data.key;
                });
                var memberUserIds = $.map($("#tree${CRS_POST_EXPERT_ROLE_MEMBER}").dynatree("getSelectedNodes"), function(node){
                    if(!node.data.isFolder && !node.data.hideCheckbox)
                        return node.data.key;
                });
                $(form).ajaxSubmit({
                    data:{headUserIds:headUserIds, leaderUserIds:leaderUserIds,
                        memberUserIds:memberUserIds, postId:"${param.postId}"},
                    success:function(data){
                        if(data.success){
                            $("#modal").modal('hide');
                            $("#jqGrid2").trigger("reloadGrid");
                        }
                    }
                });
            }
        });
    })
</script>