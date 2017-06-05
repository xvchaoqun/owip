<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${dispatchWorkFile!=null}">编辑</c:if><c:if test="${dispatchWorkFile==null}">添加</c:if>干部工作文件
	（${DISPATCH_WORK_FILE_TYPE_MAP.get(type)}）
	</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dispatchWorkFile_au" id="modalForm" method="post">
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
						<c:import url="/metaTypes?__code=mc_dwf_work_type"/>
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
				<label class="col-xs-3 control-label">保密级别</label>
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
    <input type="submit" class="btn btn-primary" value="<c:if test="${dispatchWorkFile!=null}">确定</c:if><c:if test="${dispatchWorkFile==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm").validate({
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

	register_date($('.date-picker'));

	register_fileupload($("#modalForm input[type=file]"));

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