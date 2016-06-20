<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${op}角色</h3>
  </div>
  <div class="modal-body">
  <form class="form-horizontal"  action="${ctx}/sysRole_au" method="post">
  <input type="hidden" name="id" value="${sysRole.id}">
  	<div class="form-group">
		<label class="col-xs-3 control-label">代码</label>
		<div class="col-xs-4">
			<input class="form-control" <c:if test="${sysRole.role eq 'admin'}"> disabled </c:if> type="text" name="role" value="${sysRole.role}">
			<span class="help-block">* 不可修改；如需修改，请联系系统开发人员</span>
		</div>
	</div>
  	<div class="form-group">
		<label class="col-xs-3 control-label">名称</label>
		<div class="col-xs-4">
			<input class="form-control" type="text" name="description" value="${sysRole.description}">
		</div>
	</div>
	  <div class="form-group">
		  <label class="col-xs-3 control-label">备注</label>
		  <div class="col-xs-8">
			  <textarea class="form-control" name="remark">${sysRole.remark}</textarea>
		  </div>
	  </div>
	<div class="form-group">
		<label class="col-xs-3 control-label">拥有的资源</label>
		<div class="col-xs-8">
			<div id="tree3"></div>
		</div>
	</div>
  
  </form>
  </div>
  <div class="modal-footer">
  <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
  <input type="submit" class="btn btn-primary" value="${op}"/></div>
  <style>
	ul.dynatree-container{border: none;}
</style>
  <script>

		var treeData = ${tree};
		treeData.title = "选择资源";

        $("#tree3").dynatree({
            checkbox: true,
            selectMode: 2,
            children: treeData,
            onSelect: function(select, node) {

                //node.expand(node.data.isFolder && node.isSelected());
            },
            cookieId: "dynatree-Cb3",
            idPrefix: "dynatree-Cb3-"
        });

		$("#modal input[type=submit]").click(function(){$("#modal form").submit();return false;});
		$("#modal form").validate({
				rules: {
					role: {
						required: true
					},
					description: {
						required: true
					}
				},
				submitHandler: function (form) {
					
					var resIds = $.map($("#tree3").dynatree("getSelectedNodes"), function(node){
						//if(!node.data.isFolder)
						return node.data.key;
					});
					
					$(form).ajaxSubmit({
						data:{resIds:resIds},
						success:function(data){
							if(data.success){

								_reload();
								SysMsg.success('操作成功。', '成功');
							}
						}
					});
				}
			});
</script>