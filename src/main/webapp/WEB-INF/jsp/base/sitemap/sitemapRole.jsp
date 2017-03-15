<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h4>分配角色</h4>
  </div>
  <div class="modal-body">
  <form class="form-horizontal"  action="${ctx}/sitemapRole" method="post">
  	<input type="hidden" name="id" value="${sitemap.id}">
  	<div id="tree3"></div>
  </form>
  </div>
  <div class="modal-footer">
  <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
  <input type="submit" class="btn btn-primary" value="修改"/></div>
  <style>
	ul.dynatree-container{border: none;}
</style>
  <script>
	  jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
	  jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();
	$(function(){
		var treeData = ${tree};
		treeData.title = "选择角色";
		$(function(){
			$("#tree3").dynatree({
				checkbox: true,
				//selectMode: 1,
				children: treeData,
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
					
					var roleIds = $.map($("#tree3").dynatree("getSelectedNodes"), function(node){
						if(!node.data.isFolder)
						return node.data.key;
					});
					
					$(form).ajaxSubmit({
						data:{roleIds:roleIds},
						success:function(data){
							if(data.success){
								$("#modal").modal('hide');
								_reload();
								//SysMsg.success('操作成功。', '成功');
							}
						}
					});
				}
			});
	 })
</script>