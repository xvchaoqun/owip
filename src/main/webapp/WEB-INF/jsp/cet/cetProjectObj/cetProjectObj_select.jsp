<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h4>选择培训对象（${cetTraineeType.name}）</h4>
  </div>
  <div class="modal-body">
  <form class="form-horizontal"  action="${ctx}/cet/cetProjectObj_add" id="modalForm" method="post">
  <div id="tree3" style="min-height: 400px"></div>
  </form>
  </div>
  <div class="modal-footer">
	  <div  style="position: absolute; float:left; left:10px;padding-top: 5px">
		  <input type="button" id="btnSelectAll" class="btn btn-success btn-xs" value="全选"/>
		  <input type="button" id="btnDeselectAll" class="btn btn-danger btn-xs" value="全不选"/>
	  </div>
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
  <button id="submitBtn" class="btn btn-primary"
		  data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">确定</button></div>
  <c:choose>
      <c:when test="${cetTraineeType.code=='t_cadre'}">
          <c:set var="selectTreeURL" value="${ctx}/cet/cetProjectObj_selectCadres_tree"/>
      </c:when>
      <c:when test="${cetTraineeType.code=='t_reserve'}">
          <c:set var="selectTreeURL" value="${ctx}/cet/cetProjectObj_selectCadreReserves_tree"/>
      </c:when>
      <c:when test="${cetTraineeType.code=='t_party_member'}">
          <c:set var="selectTreeURL" value="${ctx}/cet/cetProjectObj_selectPartyMembers_tree"/>
      </c:when>
      <c:when test="${cetTraineeType.code=='t_branch_member'}">
          <c:set var="selectTreeURL" value="${ctx}/cet/cetProjectObj_selectBranchMembers_tree"/>
      </c:when>
      <c:when test="${cetTraineeType.code=='t_organizer'}">
          <c:set var="selectTreeURL" value=""/>
      </c:when>
      <c:when test="${cetTraineeType.code=='t_activist'}">
          <c:set var="selectTreeURL" value="${ctx}/cet/cetProjectObj_selectActivists_tree"/>
      </c:when>
  </c:choose>
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
		$.getJSON("${selectTreeURL}",
            {projectId:"${param.projectId}", traineeTypeId:"${param.traineeTypeId}"},function(data){
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

		$("#submitBtn").click(function(){

			$("#modal form").submit();return false;
		});

		$("#modal form").validate({

				submitHandler: function (form) {

					var $btn = $("#submitBtn").button('loading');
					var userIds = $.map($("#tree3").dynatree("getSelectedNodes"), function(node){
						if(!node.data.isFolder)
							return node.data.key;
					});
					$(form).ajaxSubmit({
						data:{userIds:userIds, projectId:"${param.projectId}", traineeTypeId: "${param.traineeTypeId}"},
						success:function(data){
							if(data.success){
								$("#modal").modal('hide');
								$("#jqGrid2").trigger("reloadGrid");
								//SysMsg.success('操作成功。', '成功');
							}
							$btn.button('reset');
						}
					});
				}
			});
	 })
</script>