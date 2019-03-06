<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${metaType!=null}">编辑</c:if><c:if test="${metaType==null}">添加</c:if></h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scMatterSetting_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${metaType.id}">
		<input type="hidden" name="classId" value="${param.classId}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>名称</label>
				<div class="col-xs-6">
                   <input required class="form-control" type="text" name="name" value="${metaType.name}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
	<a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${metaType!=null}">确定</c:if><c:if test="${metaType==null}">添加</c:if>"/>
</div>
<script>

        $("#modalForm").validate({
            submitHandler: function (form) {
                $(form).ajaxSubmit({
                    success:function(ret){
                        if(ret.success){
							_reload()
                        }
                    }
                });
            }
        });
</script>