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
        <div class="form-group">
            <label class="col-xs-3 control-label">所属一级目录</label>
            <div class="col-xs-6">
                <input class="form-control" type="text" name="firstLevel" value="${metaClass.firstLevel}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">所属二级目录</label>
            <div class="col-xs-6">
                <input class="form-control" type="text" name="secondLevel" value="${metaClass.secondLevel}">
            </div>
        </div>
        <shiro:hasRole name="${ROLE_ADMIN}">
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
    	<div class="form-group">
          <label class="col-xs-3 control-label">附加属性选项</label>
          <div class="col-xs-6">
              <textarea class="form-control" name="extraOptions">${metaClass.extraOptions}</textarea>
              <span class="help-block red">格式一： jg|机关,xy|学院<br/>格式二：正高,副高,中级,初级</span>
          </div>
        </div>
        </shiro:hasRole>
        </form>
  </div>
  <div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
  <input type="submit" class="btn btn-primary" value="<c:if test="${metaClass!=null}">确定</c:if><c:if test="${metaClass==null}">添加</c:if>"/>
  </div>

  <script>
      jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
      jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();
	$(function(){
		$("#modal form").validate({
				submitHandler: function (form) {
					$(form).ajaxSubmit({
						success:function(ret){
							if(ret.success){
                                $("#modal").modal('hide');
                                //SysMsg.success('提交成功。', '成功',function(){
                                    $("#jqGrid").trigger("reloadGrid");
                                //});
							}
						}
					});
				}
			});
	 })
</script>