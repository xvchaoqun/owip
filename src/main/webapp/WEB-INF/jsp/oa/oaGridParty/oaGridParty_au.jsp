<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<h3>上传文件</h3>
<hr/>
    <form class="form-horizontal" action="${ctx}/oa/oaGridParty_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${oaGridParty.id}">
		<div class="row">
			<div class="col-xs-4">
				<div class="form-group">
					<label class="col-xs-4 control-label"><c:if test="${empty oaGridParty.excelFilePath}"><span class="star">*</span></c:if> 上传Excel文件</label>
					<div class="col-xs-6">
						<input ${empty oaGridParty.excelFilePath?'required':''} class="form-control" type="file" name="_excelFilePath"/>
						<span class="help-inline">上传的文件请严格按照<a href="${ctx}/attach_download?path=${cm:sign(oaGrid.templateFilePath)}&filename=${oaGrid.name}">数据模板</a>（点击下载）的格式</span>
					</div>
				</div>
					<div class="form-group">
						<label class="col-xs-4 control-label"><c:if test="${empty oaGridParty.filePath}"><span class="star">*</span></c:if>上传签字文件(批量)</label>
						<div class="col-xs-6">
							<div class="files">
								<input ${empty oaGridParty.filePath?'required':''} class="form-control" multiple="multiple" type="file" name="_filePath"/>
							</div>
						</div>

						<div id="fileButton" style="padding-left: 50px;padding-top: 5px">
							<button type="button" onclick="addFile()"
									class="addFileBtn btn btn-default btn-xs"><i class="fa fa-plus"></i></button>
						</div>
					</div>
			</div>
		</div>
    </form>
</div>
<div class="clearfix form-actions">
	<div class="col-md-offset-3 col-md-9">
		<button class="btn btn-info" id="submitBtn">
			<i class="ace-icon fa fa-check bigger-110"></i>
			提交
		</button>

		&nbsp; &nbsp; &nbsp;
		<button class="hideView btn btn-default" type="button">
			<i class="ace-icon fa fa-reply bigger-110"></i>
			返回
		</button>
	</div>
</div>
<script>

	var i = 1;
	function addFile() {
		i++;
		var _file = $('<div id="file'+i+'"><input  class="form-control" type="file" name="_filePath" /></div>');
		$(".files").append(_file);
		var _fileButton = $('<div id="btn'+i+'" style="padding-top: 13px"><button type="button" data-i="'+i+'" onclick="delfileInput(this)"class="addFileBtn btn btn-default btn-xs"><i class="fa fa-trash"></i></button></div>');
		$("#fileButton").append(_fileButton);
		$.fileInput($('input[type=file]', $(_file)), {
			no_file: '请上传pdf或图片...',
			allowExt: ['pdf', 'jpg', 'jpeg', 'png', 'gif'],
			allowMime: ['application/pdf', 'image/jpg', 'image/jpeg', 'image/png', 'image/gif']
		});
		return false;
	}

	function delfileInput(btn) {
		var i = $(btn).data("i");
		$("#file"+i).remove();
		$("#btn"+i).remove();
	}

	$.fileInput($('#modalForm input[name=_filePath]'), {
		no_file: '请上传pdf或图片...',
		allowExt: ['pdf', 'jpg', 'jpeg', 'png', 'gif'],
		allowMime: ['application/pdf', 'image/jpg', 'image/jpeg', 'image/png', 'image/gif']
	});

	$.fileInput($('#modalForm input[name=_excelFilePath]'), {
		no_file: '请上传xlsx,xls文件...',
		allowExt: ['xlsx','xls']
	})

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#jqGrid").trigger("reloadGrid");
                        $.hideView();
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.ajax_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
	//$.register.date($('.date-picker'));
</script>