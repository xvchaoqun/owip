<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h4>编辑推荐组成员
  </h4>
</div>
<div class="modal-body">
  <form class="form-horizontal"  action="${ctx}/drOffline_selectMembers" id="modalForm" method="post">
    <div id="tree3" style="min-height: 400px"></div>
  </form>
</div>
<div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
  <a id="add_entity" class="btn btn-primary">更新</a></div>
<script>

  $(function(){
    $.getJSON("${ctx}/drOffline_selectMembers_tree",{offlineId:"${param.offlineId}"},function(data){
      var treeData = data.tree;
      $("#tree3").dynatree({
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

    $("#add_entity").click(function(){

      $("#modal form").submit();return false;
    });

    $("#modal form").validate({
      submitHandler: function (form) {
        var memberIds = $.map($("#tree3").dynatree("getSelectedNodes"), function(node){
          if(!node.data.isFolder && !node.data.hideCheckbox)
            return node.data.key;
        });

        $(form).ajaxSubmit({
          data:{memberIds:memberIds, offlineId:"${param.offlineId}"},
          success:function(data){
            if(data.success){
              $("#modal").modal('hide');
              $("#jqGrid").trigger("reloadGrid");
            }
          }
        });
      }
    });
  })
</script>