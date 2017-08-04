<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>岗位要求</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crsPost_detail/step1_require_au" id="modalForm" method="post">
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
						$("#modalForm select[name=type]").val('${crsPostRequire.id}');
					</script>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
						$("#step-content li.active .loadPage").click()
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>