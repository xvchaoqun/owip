<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h4>"${dispatchWorkFile.fileName}"所包含的职务属性
    </h4>
  </div>
  <div class="modal-body">
  <form class="form-horizontal"  action="${ctx}/dispatchWorkFileAuth" id="modalForm" method="post">
  <div id="tree3" style="min-height: 400px"></div>
  </form>
  </div>
  <div class="modal-footer">
	  <div  style="position: absolute; float:left; left:10px;padding-top: 5px">
		  <input type="button" id="btnSelectAll" class="btn btn-success btn-xs" data-status="true" value="全选"/>
	  </div>
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
  <a id="add_entity" class="btn btn-primary">更新</a></div>
  <script>
	  $("#btnSelectAll").click(function(){

		  var status = $(this).data("status");
		  $(this).data("status", !status);
		  if(status){
			  $(this).removeClass("btn-success").addClass("btn-danger").val("全不选");
		  }else{
			  $(this).removeClass("btn-danger").addClass("btn-success").val("全选");
		  }

		  $("#tree3").dynatree("getRoot").visit(function(node){
			  node.select(status);
			  node.activate()
		  });
		  return false;
	  });

	$(function(){
		$.getJSON("${ctx}/dispatchWorkFile_selectPosts_tree",{id:"${param.id}"},function(data){
			var treeData = data.tree;
			$("#tree3").dynatree({
				children: treeData,
				onSelect: function(select, node) {

					node.expand(node.data.isFolder && node.isSelected());
				}
			});
		});

		$("#add_entity").click(function(){
			
			$("#modal form").submit();return false;
		});

		$("#modal form").validate({
				submitHandler: function (form) {
					var postTypes = $.map($("#tree3").dynatree("getSelectedNodes"), function(node){
						if(!node.data.isFolder && !node.data.hideCheckbox)
						return node.data.key;
					});
					
					$(form).ajaxSubmit({
						data:{postTypes:postTypes, id:"${param.id}"},
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