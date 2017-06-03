<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" onclick="openView(${metaType.classId})" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${metaType!=null}">编辑</c:if><c:if test="${metaType==null}">添加</c:if></h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/metaType_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${metaType.id}">

		<div class="form-group">
			<label class="col-xs-3 control-label">所属分类</label>
			<div class="col-xs-6">
				<input type="hidden" name="classId" value="${metaType.classId}">
				<c:set value="${metaClassMap.get(metaType.classId)}" var="metaClass"/>
				<input type="text" readonly value="${metaClass.name}">

			</div>
		</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${metaType.name}">
				</div>
			</div>
<shiro:hasRole name="${ROLE_ADMIN}">
			<div class="form-group">
				<label class="col-xs-3 control-label">代码</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="code" value="${metaType.code}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">${empty metaClass.boolAttr?'布尔属性':metaClass.boolAttr}</label>
				<div class="col-xs-6">
					<label>
						<input name="boolAttr"  type="checkbox" ${metaType.boolAttr?"checked":""}/>
						<span class="lbl"></span>
					</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">附加属性</label>
				<div class="col-xs-6">
						<textarea class="form-control" name="extraAttr">${metaType.extraAttr}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
						<textarea class="form-control limited" name="remark">${metaType.remark}</textarea>
				</div>
			</div>
	</shiro:hasRole>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:" onclick="openView(${metaType.classId})" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${metaType!=null}">确定</c:if><c:if test="${metaType==null}">添加</c:if>"/>
</div>

<script>
	$("#modalForm :checkbox").bootstrapSwitch();
	$('textarea.limited').inputlimiter();
        $("#modal form").validate({
            submitHandler: function (form) {
                $(form).ajaxSubmit({
                    success:function(ret){
                        if(ret.success){
							openView("${param.classId}", "${param.pageNo}");
                            //SysMsg.success('操作成功。', '成功');
                        }
                    }
                });
            }
        });
</script>