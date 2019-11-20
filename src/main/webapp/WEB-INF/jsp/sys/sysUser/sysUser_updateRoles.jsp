<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h4>修改账号角色</h4>
  </div>
  <div class="modal-body">
  <form class="form-horizontal"  action="${ctx}/sysUser_updateRoles" method="post">
  	<input type="hidden" name="id" value="${sysUser.id}">
  	<div id="tree3"></div>
  </form>
  </div>
  <div class="modal-footer">
    <c:if test="${!cm:isSuperAccount(_user.username)}">
    <div class="note">注：系统自动赋予的角色不在此显示</div>
      </c:if>
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
  <input type="submit" class="btn btn-primary" value="修改"/></div>
  <style>
	ul.dynatree-container{border: none;}
	  .unselectable .dynatree-title{
		  background-color: lightgoldenrodyellow;
	  }
  </style>
  <script>

	$(function(){
		var treeData = ${tree};
		treeData.title = "选择角色";
		$(function(){
			$("#tree3").dynatree({
				checkbox: true,
				selectMode: 3,
				children: treeData,
				onClick:function(node, event){
					if(node.data.unselectable){
						$.tip({
							$target: $(event.target).closest(".dynatree-node"), $container: $("#modal"),
							msg: "该角色由系统自动维护，不可以手动修改", my: 'bottom left', at: 'top center'
						})
						//$(event.target).qtip({content:"该角色由系统自动维护，不可以手动修改",show: true,container:$("#modal")});
					}
				},
				onSelect: function(select, node) {
					node.expand(node.data.isFolder && node.isSelected());
				},
				cookieId: "dynatree-Cb3",
				idPrefix: "dynatree-Cb3-"
			});
		});

		$("#modal input[type=submit]").click(function(){$("#modal form").submit();return false;});
		$("#modal form").validate({
				submitHandler: function (form) {
					
					var rIds = $.map($("#tree3").dynatree("getSelectedNodes"), function(node){
						if(!node.data.isFolder)
						return node.data.key;
					});
					
					$(form).ajaxSubmit({
						data:{rIds:rIds},
						success:function(data){
							if(data.success){
								/*$("#modal").modal('hide');
								_reload();
								SysMsg.success('操作成功。', '成功');*/
								$("#modal").modal('hide');
								//SysMsg.success('操作成功。', '成功',function(){
									$("#jqGrid").trigger("reloadGrid");
								//});
							}
						}
					});
				}
			});
	 })
</script>