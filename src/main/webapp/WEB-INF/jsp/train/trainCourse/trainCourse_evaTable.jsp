<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>设置评估表</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/trainCourse_evaTable" id="modalForm" method="post">
        <input type="hidden" name="id" value="${trainCourse.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">培训班次</label>
				<div class="col-xs-6 label-text">
					${train.name}
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">培训课程</label>
				<div class="col-xs-6 label-text">
					${trainCourse.name}
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">评估表</label>
				<div class="col-xs-6">
					<div class="input-group">
						<select required data-rel="select2" name="evaTableId" data-placeholder="请选择"  data-width="270">
							<option></option>
							<c:forEach items="${trainEvaTableMap}" var="entity">
								<option value="${entity.key}">${entity.value.name}</option>
							</c:forEach>
						</select>
						<script type="text/javascript">
							$("#modal form select[name=evaTableId]").val(${trainCourse.evaTableId});
						</script>
					</div>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${trainCourse!=null}">确定</c:if><c:if test="${trainCourse==null}">添加</c:if>"/>
</div>

<script>
	register_datetime($('.datetime-picker'));
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>