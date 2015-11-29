<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${metaClass!=null}">编辑</c:if><c:if test="${metaClass==null}">添加</c:if></h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" action="${ctx}/metaClass_au" id="modalForm" method="post">
    	<input type="hidden" name="id" value="${metaClass.id}">
    	<div class="form-group">
          <label class="col-xs-3 control-label">名称</label>
          <div class="col-xs-6">
			   <input required class="form-control" type="text" name="name" value="${metaClass.name}">
          </div>
        </div>
        <shiro:hasRole name="admin">
    	<div class="form-group">
          <label class="col-xs-3 control-label">代码</label>
          <div class="col-xs-6">
			   <input class="form-control" type="text" name="code" value="${metaClass.code}">
          </div>
        </div>
    	<div class="form-group">
          <label class="col-xs-3 control-label">布尔属性名称</label>
          <div class="col-xs-6">
			  <input class="form-control" type="text" name="boolAttr" value="${metaClass.boolAttr}">
          </div>
        </div>
    	<div class="form-group">
          <label class="col-xs-3 control-label">附加属性名称</label>
          <div class="col-xs-6">
			  <input class="form-control" type="text" name="extraAttr" value="${metaClass.extraAttr}">
          </div>
        </div>
        </shiro:hasRole>
        </form>
  </div>
  <div class="modal-footer">
  <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
  <input type="submit" class="btn btn-primary" value="<c:if test="${metaClass!=null}">确定</c:if><c:if test="${metaClass==null}">添加</c:if>"/>
  </div>

  <script>
	$(function(){
		$("#modal form").validate({
				submitHandler: function (form) {
					$(form).ajaxSubmit({
						success:function(ret){
							if(ret.success){
                                page_reload();
								toastr.success('操作成功。', '成功');
							}
						}
					});
				}
			});
	 })
</script>