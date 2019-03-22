<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/cla/constants.jsp" %>
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h4>
<c:if test="${param.type==CLA_APPROVER_TYPE_UNIT}">"本单位正职"</c:if>
<c:if test="${param.type==CLA_APPROVER_TYPE_LEADER}">"分管校领导"</c:if>
<c:if test="${param.type!=CLA_APPROVER_TYPE_UNIT && param.type!=CLA_APPROVER_TYPE_LEADER}">"${approverType.name}"</c:if>
		所包含的干部
    </h4>
  </div>
  <div class="modal-body">
  <form class="form-horizontal"  action="${ctx}/cla/claApproverType/selectCadres" autocomplete="off" disableautocomplete id="modalForm" method="post">
  <div id="tree3" style="min-height: 400px"></div>
  </form>
  </div>
  <div class="modal-footer">
	  <div  style="position: absolute; float:left; left:10px;padding-top: 5px">
		  <input type="button" id="btnSelectAll" class="btn btn-success btn-xs" value="全选"/>
		  <input type="button" id="btnDeselectAll" class="btn btn-danger btn-xs" value="全不选"/>
	  </div>
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
  <a id="add_entity" class="btn btn-primary">更新</a></div>
  <script>
	  $("#btnDeselectAll").click(function(){
		  $("#tree3").dynatree("getRoot").visit(function(node){
			  node.select(false);
		  });
		  return false;
	  });
	  $("#btnSelectAll").click(function(){
		  $("#tree3").dynatree("getRoot").visit(function(node){
			  node.select(true);
		  });
		  return false;
	  });
	$(function(){
		$.getJSON("${ctx}/cla/claApproverType/selectCadres_tree",{id:"${param.id}", type:"${param.type}"},function(data){
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
					<c:if test="${param.type!=CLA_APPROVER_TYPE_UNIT && param.type!=CLA_APPROVER_TYPE_LEADER}">
					var cadreIds = $.map($("#tree3").dynatree("getSelectedNodes"), function(node){
						if(!node.data.isFolder)
						return node.data.key;
					});
					</c:if>
					<c:if test="${param.type==CLA_APPROVER_TYPE_UNIT || param.type==CLA_APPROVER_TYPE_LEADER}">
					var cadreIds = [];
					$("#tree3").dynatree("getRoot").visit(function(node){
						if(!node.data.isFolder && !node.data.unselectable && !node.isSelected()) {
							cadreIds.push(node.data.key);
						}
					});
					</c:if>
					$(form).ajaxSubmit({
						data:{cadreIds:cadreIds, id:"${param.id}", type: "${param.type}"},
						success:function(data){
							if(data.success){
								$("#modal").modal('hide');
								//SysMsg.success('操作成功。', '成功');
							}
						}
					});
				}
			});
	 })
</script>