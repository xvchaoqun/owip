<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>组织处理</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scMatterCheckItem_ow" id="owForm" method="post">
        <input type="hidden" name="id" value="${scMatterCheckItem.id}">

			<div class="form-group">
				<label class="col-xs-3 control-label">组织处理方式</label>
				<div class="col-xs-6">
					<select data-rel="select2" id="_owHandleType" data-placeholder="请选择" data-width="240">
						<option></option>
						<c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_sc_matter_check_ow_handle_type').id}"/>
					</select>
					<div class="space-4"></div>
                        <input required class="form-control" type="text"
							   name="owHandleType" value="${scMatterCheckItem.owHandleType}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">组织处理日期</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="owHandleDate" type="text"
							   data-date-format="yyyy-mm-dd"
							   value="${cm:formatDate(scMatterCheckItem.owHandleDate,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
													class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">组织处理记录</label>
				<div class="col-xs-6">
					<input class="form-control" type="file" name="_owHandleFile"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">组织处理影响期</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="owAffectDate" type="text"
							   data-date-format="yyyy-mm-dd"
							   value="${cm:formatDate(scMatterCheckItem.owAffectDate,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
													class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
					<textarea class="form-control limited" name="remark">${scMatterCheckItem.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="owSubmitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scMatterCheckItem!=null}">确定</c:if><c:if test="${scMatterCheckItem==null}">添加</c:if></button>
</div>

<script>
	$("#_owHandleType").change(function(){
		//console.log($(this).select2("data"))
		$("#owForm input[name=owHandleType]").val($(this).select2("data")[0].text);
	});

    $("#owSubmitBtn").click(function(){$("#owForm").submit();return false;});
    $("#owForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
	$.fileInput($("#owForm input[name=_owHandleFile]"),{
		allowExt: ['pdf'],
		allowMime: ['application/pdf']
	});
	$.register.date($('.date-picker'));
	$('textarea.limited').inputlimiter();
    $('#owForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>