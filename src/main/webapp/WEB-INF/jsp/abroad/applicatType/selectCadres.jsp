<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h4>"${applicatType.name}"所包含的干部
    </h4>
  </div>
  <div class="modal-body">
  <form class="form-horizontal"  action="${ctx}/abroad/applicatType/selectCadres" id="modalForm" method="post">
  <div id="tree3" style="min-height: 400px"></div>
  </form>
  </div>
  <div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
  <a id="add_entity" class="btn btn-primary">更新</a></div>
  <script>

	$(function(){
		$.getJSON("${ctx}/abroad/applicatType/selectCadres_tree",{id:"${param.id}"},function(data){
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
					var cadreIds = $.map($("#tree3").dynatree("getSelectedNodes"), function(node){
						if(!node.data.isFolder && !node.data.hideCheckbox)
						return node.data.key;
					});
					
					$(form).ajaxSubmit({
						data:{cadreIds:cadreIds, id:"${param.id}"},
						success:function(data){
							if(data.success){
								$("#modal").modal('hide');
								//SysMsg.success('操作成功。', '成功',function(){
									page_reload()
								//});

							}
						}
					});
				}
			});
	 })
</script>