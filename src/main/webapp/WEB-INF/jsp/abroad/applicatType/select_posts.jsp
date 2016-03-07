<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h4>"${applicatType.name}"所包含的职务
    </h4>
  </div>
  <div class="modal-body">
  <form class="form-horizontal"  action="${ctx}/applicatType/select_posts" id="modalFrom" method="post">
  <div id="tree3"></div>
  </form>
  </div>
  <div class="modal-footer">
  <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
  <a id="add_entity" class="btn btn-primary">更新</a></div>
  <script>

	$(function(){
		$.getJSON("${ctx}/applicatType/selectPosts_tree",{id:"${param.id}"},function(data){
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
				errorElement: 'span',
				errorClass: 'help-inline',
				focusInvalid: true,
				rules: {
					annualSurveyId: {
						required: true
					}
					,
					objId: {
						required: true
					}
					
				},
		
				invalidHandler: function (event, validator) { //display error alert on form submit   
					//alert(2)	
					//$('.alert-error', $('.login-form')).show();
				},
		
				highlight: function (e) {
					//alert(3)
					$(e).closest('.form-group').removeClass('info').addClass('error');
				},
		
				success: function (e) {
					$(e).closest('.form-group').removeClass('error').addClass('info');
					$(e).remove();
				},
		
				errorPlacement: function (error, element) {
	
					if(element.is(':checkbox') || element.is(':radio')) {
						var controls = element.closest('.controls');
						if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
						else error.insertAfter(element.nextAll('.lbl').eq(0));
					} 
					else if(element.is('.chzn-select')) {
						error.insertAfter(element.nextAll('[class*="chzn-container"]').eq(0));
					}
					else error.insertAfter(element);
				},
		
				submitHandler: function (form) {
					
					var postIds = $.map($("#tree3").dynatree("getSelectedNodes"), function(node){
						if(!node.data.isFolder)
						return node.data.key;
					});
					
					$(form).ajaxSubmit({
						data:{postIds:postIds, id:"${param.id}"},
						success:function(data){
							if(data.success){
								$("#modal").modal('hide');
								SysMsg.success('操作成功。', '成功');
							}
						}
					});
				},
				invalidHandler: function (form) {
				}
			});
	 })
</script>