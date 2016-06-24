<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h4>
<c:if test="${param.type==APPROVER_TYPE_UNIT}">"本单位正职"</c:if>
<c:if test="${param.type==APPROVER_TYPE_LEADER}">"分管校领导"</c:if>
<c:if test="${param.type==APPROVER_TYPE_OTHER}">"${approverType.name}"</c:if>
		所包含的干部
    </h4>
  </div>
  <div class="modal-body">
  <form class="form-horizontal"  action="${ctx}/approverType/selectCadres" id="modalFrom" method="post">
  <div id="tree3" style="min-height: 400px"></div>
  </form>
  </div>
  <div class="modal-footer">
	<c:if test="${param.type==APPROVER_TYPE_OTHER}">
  <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
  <a id="add_entity" class="btn btn-primary">更新</a></div>
	</c:if>
<c:if test="${param.type!=APPROVER_TYPE_OTHER}">
	<a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
	</c:if>
  <script>

	$(function(){
		$.getJSON("${ctx}/approverType/selectCadres_tree",{id:"${param.id}", type:"${param.type}"},function(data){
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
						if(!node.data.isFolder)
						return node.data.key;
					});

					$(form).ajaxSubmit({
						data:{cadreIds:cadreIds, id:"${param.id}"},
						success:function(data){
							if(data.success){
								$("#modal").modal('hide');
								SysMsg.success('操作成功。', '成功');
							}
						}
					});
				}
			});
	 })
</script>