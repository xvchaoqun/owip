<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h4>批量添加
    </h4>
  </div>
  <div class="modal-body">
  <form class="form-horizontal"  action="${ctx}/crsExpert/selectCadres" id="modalFrom" method="post">
  <div id="tree3" style="min-height: 400px"></div>
  </form>
  </div>
  <div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
  <a id="add_entity" class="btn btn-primary">更新</a></div>
  <script>

	$(function(){
		$.getJSON("${ctx}/crsExpert/selectCadres_tree",{},function(data){
			var treeData = data.tree.children;
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
					var userIds = $.map($("#tree3").dynatree("getSelectedNodes"), function(node){
						if(!node.data.isFolder && !node.data.hideCheckbox)
						return node.data.key;
					});
					
					$(form).ajaxSubmit({
						data:{userIds:userIds, id:"${param.id}"},
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