<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${pm3Guide!=null?'编辑':'上传'}组织生活指南</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pm/pm3Guide_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${pm3Guide.id}">
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span> 活动年月</label>
			<div class="col-xs-6">
				<input required name="_meetingMonth" class="form-control date-picker" data-date-min-view-mode="1"
					   data-date-format="yyyy-mm" type="text"
						value="${cm:formatDate(pm3Guide.meetingMonth, "yyyy-MM")}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span> 报送时间</label>
			<div class="col-xs-6">
				<input required name="_reportTime"  class="form-control datetime-picker " type="text"
						value="${cm:formatDate(pm3Guide.reportTime, "yyyy-MM-dd HH:mm")}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label"><c:if test="${empty pm3Guide.guideFiles}"><span class="star">*</span></c:if>上传组织生活指南(批量)</label>
			<div class="col-xs-6">
				<div class="files">
					<input ${empty pm3Guide.guideFiles?'required':''} class="form-control" multiple="multiple"
																	   type="file" name="_guideFile"/>
				</div>
			</div>

			<div id="fileButton" style="padding-left: 50px;padding-top: 5px">
				<button type="button" onclick="addFile()"
						class="addFileBtn btn btn-default btn-xs"><i class="fa fa-plus"></i></button>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label"> 备注</label>
			<div class="col-xs-6">
				<textarea class="form-control" type="text" name="remark">${pm3Guide.remark}</textarea>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty pm3Guide?'确定':'添加'}</button>
</div>
<script>

	var i = 1;

	function addFile() {
		i++;
		var _file = $('<div id="file' + i + '"><input  class="form-control" type="file" name="_guideFile" /></div>');
		$(".files").append(_file);
		var _fileButton = $('<div id="btn' + i + '" style="padding-top: 13px"><button type="button" data-i="' + i + '" onclick="delfileInput(this)"class="addFileBtn btn btn-default btn-xs"><i class="fa fa-trash"></i></button></div>');
		$("#fileButton").append(_fileButton);
		$.fileInput($('input[type=file]', $(_file)), {
			no_file: '请上传文件...',
		});
		return false;
	}

	function delfileInput(btn) {
		var i = $(btn).data("i");
		$("#file" + i).remove();
		$("#btn" + i).remove();
	}

	$.fileInput($('#modalForm input[name=_guideFile]'), {
		no_file: '请上传文件...',
	});

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
	$('textarea.limited').inputlimiter();
	$.register.datetime($('.datetime-picker'));
	$.register.date($('.date-picker'));
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
</script>