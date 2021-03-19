<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3>${cadreEvaResult!=null?'编辑':'添加'}${type==0?'干部':'班子'}年度测评结果</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreEvaResult_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreEvaResult.id}">
		<input type="hidden" name="cadreId" value="${param.cadreId}">
		<input type="hidden" name="type" value="${type}">
		<input type="hidden" name="unitId" value="${cadreEvaResult.unitId}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 年份</label>
				<div class="col-xs-6">
					<div class="input-group" style="width: 120px">
					<input required class="form-control date-picker" placeholder="请选择年份" name="year" type="text"
						   data-date-format="yyyy" data-date-min-view-mode="2" value="${cadreEvaResult.year}" />
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 测评类别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="groupName" value="${cadreEvaResult.groupName}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 排名</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="number" style="width: 80px;" min="1" name="sortOrder" value="${cadreEvaResult.sortOrder}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> ${type==0?'总人数':'班子总人数'}</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="number" style="width: 80px;" min="1" name="num" value="${cadreEvaResult.num}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 备注</label>
				<div class="col-xs-6">
					<textarea class="form-control limited" type="text" name="remark" >${cadreEvaResult.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty cadreEvaResult?'确定':'添加'}</button>
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
                        $("#jqGrid_evaResult").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>