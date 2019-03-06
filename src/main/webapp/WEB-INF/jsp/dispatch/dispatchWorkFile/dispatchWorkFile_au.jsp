<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="DISPATCH_WORK_FILE_TYPE_MAP" value="<%=DispatchConstants.DISPATCH_WORK_FILE_TYPE_MAP%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${dispatchWorkFile!=null}">编辑</c:if><c:if test="${dispatchWorkFile==null}">添加</c:if>干部工作文件
	（${DISPATCH_WORK_FILE_TYPE_MAP.get(type)}）
	</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dispatchWorkFile_au" id="modalForm"
		  method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${dispatchWorkFile.id}">
		<input name="type" type="hidden" value="${type}">
			<div class="form-group">
				<label class="col-xs-3 control-label">发文单位</label>
				<div class="col-xs-6">
					<select data-rel="select2" name="unitType" data-placeholder="请选择" data-width="270">
						<option></option>
						<c:import url="/metaTypes?__code=mc_dwf_unit_type"/>
					</select>
					<script type="text/javascript">
						$("#modal form select[name=unitType]").val('${dispatchWorkFile.unitType}');
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">年度</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input class="form-control date-picker" placeholder="请选择年份" name="year"
							   type="text"
							   data-date-format="yyyy" data-date-min-view-mode="2" value="${dispatchWorkFile.year}"/>
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所属专项工作</label>
				<div class="col-xs-6">
					<select data-rel="select2" name="workType" data-placeholder="请选择" data-width="270">
						<option></option>
						<c:import url="/metaTypes?__code=mc_dwf_work_type${type>10?'_ow':''}"/>
					</select>
					<script type="text/javascript">
						$("#modal form select[name=workType]").val('${dispatchWorkFile.workType}');
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发文号</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="code" value="${dispatchWorkFile.code}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发文日期</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input class="form-control date-picker" name="pubDate" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(dispatchWorkFile.pubDate,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">文件名</label>
				<div class="col-xs-6">
					<input class="form-control" type="text" name="fileName" value="${dispatchWorkFile.fileName}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>保密级别</label>
				<div class="col-xs-6">
					<select required name="privacyType" data-placeholder="请选择" data-width="270">
						<option></option>
						<c:import url="/metaTypes?__code=mc_dwf_privacy_type"/>
					</select>
					<script type="text/javascript">
						$("#modal form select[name=privacyType]").val('${dispatchWorkFile.privacyType}');
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">PDF文件</label>
				<div class="col-xs-6">
					<input class="form-control" type="file" name="_pdfFilePath"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">WORD文件</label>
				<div class="col-xs-6">
					<input class="form-control" type="file" name="_wordFilePath"/>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
					<textarea class="form-control limited" name="remark">${dispatchWorkFile.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button type="button" id="submitBtn" class="btn btn-primary"
		   data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中，请不要关闭此窗口"
		   data-success-text="上传成功" autocomplete="off">
		<c:if test="${dispatchWorkFile!=null}">确定</c:if><c:if test="${dispatchWorkFile==null}">添加</c:if>
	</button>
</div>

<script>
	$("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {

			var $btn = $("#submitBtn").button('loading');
			/*setTimeout(function(){
				$btn.button('reset');
			}, 3000);
			return;*/
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
						$btn.button("success").addClass("btn-success");

                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }else{
						$btn.button('reset');
					}
                }
            });
        }
    });

	$.register.date($('.date-picker'));

	$.fileInput($("#modalForm input[name=_pdfFilePath]"),{
		allowExt: ['pdf'],
		allowMime: ['application/pdf']
	});
	$.fileInput($("#modalForm input[name=_wordFilePath]"),{
		allowExt: ['doc', 'docx'],
		allowMime: ['application/msword','application/vnd.openxmlformats-officedocument.wordprocessingml.document']
	});

    $('#modalForm [data-rel="select2"]').select2();
    $('#modalForm select[name="privacyType"]').select2({templateSelection: function format(state) {

		var originalOption = state.element;
		var boolAttr = $(originalOption).data("bool-attr");
		//console.log(boolAttr)
		$("input[type=file]").prop("disabled", !boolAttr);

		return state.text;
	}})
	$('textarea.limited').inputlimiter();
</script>