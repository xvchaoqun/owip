<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${crPost!=null?'编辑':'添加'}岗位</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crPost_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${crPost.id}">
        <input type="hidden" name="infoId" value="${infoId}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 岗位名称</label>
				<div class="col-xs-8">
					<textarea required class="form-control" type="text" name="name" rows="3">${crPost.name}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 招聘人数</label>
				<div class="col-xs-8">
                        <input required class="form-control digits" type="text" name="num" value="${crPost.num}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">岗位职责</label>
				<div class="col-xs-8">
                        <textarea class="form-control" name="duty" rows="8">${crPost.duty}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 资格审核模板</label>
				<div class="col-xs-6">
					<select  class="form-control" name="requireId" data-rel="select2"
							 data-width="273"
							 data-placeholder="请选择">
						<option></option>
						<c:forEach items="${crRequires}" var="entry">
							<option value="${entry.id}">${entry.name}</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=requireId]").val('${crPost.requireId}');
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-8">
					<textarea class="form-control" name="remark" rows="2">${crPost.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty crPost?'确定':'添加'}</button>
</div>
<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>