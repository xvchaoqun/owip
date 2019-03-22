<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cadreCompanyFile!=null?'编辑':'添加'}干部兼职管理文件</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreCompanyFile_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreCompanyFile.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">类型</label>
				<div class="col-xs-6">
					<select name="type" data-width="272"
							data-rel="select2" data-placeholder="请选择">
						<option></option>
						<option value="1">校领导</option>
						<option value="0">干部</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>文件</label>
				<div class="col-xs-6">
					<select required data-rel="select2-ajax" data-ajax-url="${ctx}/dispatchWorkFile_selects"
							name="dispatchWorkFileId" data-placeholder="请选择"  data-width="272">
						<option></option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
					  <textarea class="form-control limited"
								name="remark">${cadreCompanyFile.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cadreCompanyFile!=null}">确定</c:if><c:if test="${cadreCompanyFile==null}">添加</c:if></button>
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
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    $.register.ajax_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>