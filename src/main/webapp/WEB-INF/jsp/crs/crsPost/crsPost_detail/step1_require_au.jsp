<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crsPost_detail/step1_require_au" id="requireForm" method="post">
        <input type="hidden" name="id" value="${crsPost.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">岗位要求</label>
				<div class="col-xs-6">
					<select  class="form-control" name="postRequireId" data-rel="select2" data-placeholder="请选择岗位要求">
						<option></option>
						<c:forEach items="${crsPostRequires}" var="entry">
							<option value="${entry.id}">${entry.name}</option>
						</c:forEach>
					</select>
					<script>
						$("#requireForm select[name=postRequireId]").val('${crsPost.postRequireId}');
					</script>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer center">
<c:if test="${not empty crsPostRequire}">
    <a href="javascript:;"  onclick="_stepContentReload()" class="btn btn-default">取消</a>
    </c:if>
    <input type="button" id="requireFormSubmitBtn" class="btn btn-primary" value="确定"/>
</div>

<script>
    $("#requireFormSubmitBtn").click(function(){$("#requireForm").submit(); return false;})
    $("#requireForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        //$("#modal").modal('hide');
                        _stepContentReload()
                    }
                }
            });
        }
    });
    $("#requireForm :checkbox").bootstrapSwitch();
    $('#requireForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>