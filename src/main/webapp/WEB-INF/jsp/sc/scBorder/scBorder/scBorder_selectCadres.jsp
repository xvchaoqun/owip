<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h4>添加报备干部</h4>
  </div>
  <div class="modal-body">
  <form class="form-horizontal"  action="${ctx}/sc/scBorder_selectCadres" id="modalForm" method="post">
      <input type="hidden" name="borderId" value="${param.borderId}">
      <div class="form-group">
          <label class="col-xs-3 control-label">报备类别</label>
          <div class="col-xs-6">
              <c:forEach var="_type" items="<%=ScConstants.SC_BORDER_ITEM_TYPE_MAP%>">
                  <label class="label-text" style="padding-right: 15px;">
                      <input required name="type" type="radio" class="ace"
                             value="${_type.key}"/>
                      <span class="lbl"> ${_type.value}</span>
                  </label>
              </c:forEach>

          </div>
      </div>
      <div id="tree3" style="min-height: 400px"></div>
  </form>
  </div>
  <div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
  <a id="add_entity" class="btn btn-primary">添加</a></div>
  <script>

	$(function(){
		$.getJSON("${ctx}/sc/scBorder_selectCadres_tree",{},function(data){
			var treeData = data.tree.children;
			$("#tree3").dynatree({
				checkbox: true,
				selectMode: 3,
				children: treeData,
				onSelect: function(select, node) {
					//node.expand(node.data.isFolder && node.isSelected());
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
						data:{cadreIds:cadreIds},
						success:function(data){
							if(data.success){
								$("#modal").modal('hide');
								$("#jqGrid2").trigger("reloadGrid");
							}
						}
					});
				}
			});
	 })
    $('#modalForm [data-rel="select2"]').select2();
</script>