<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" onclick="openView(${drVoterType.tplId})" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${drVoterType!=null}">编辑</c:if><c:if test="${drVoterType==null}">添加</c:if></h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/drVoterType_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${drVoterType.id}">

		<div class="form-group">
			<label class="col-xs-3 control-label">所属模板</label>
			<div class="col-xs-6 label-text">
				<input type="hidden" name="tplId" value="${drVoterType.tplId}">
				<c:set value="${drVoterTypeTplMap.get(drVoterType.tplId)}" var="drVoterTypeTpl"/>
				${drVoterTypeTpl.name}
			</div>
		</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${drVoterType.name}">
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-3 control-label">备注</label>
                <div class="col-xs-6">
                    <textarea class="form-control limited" name="remark">${drVoterType.remark}</textarea>
                </div>
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" onclick="openView(${drVoterType.tplId})" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${drVoterType!=null}">确定</c:if><c:if test="${drVoterType==null}">添加</c:if>"/>
</div>

<script>
	$('[data-rel="select2"]').select2();

	$("#modalForm :checkbox").bootstrapSwitch();
	$('textarea.limited').inputlimiter();
        $("#modal form").validate({
            submitHandler: function (form) {
                $(form).ajaxSubmit({
                    success:function(ret){
                        if(ret.success){
							$.reloadMetaData(function(){
								openView("${param.tplId}", "${param.pageNo}");
							});
                        }
                    }
                });
            }
        });
</script>