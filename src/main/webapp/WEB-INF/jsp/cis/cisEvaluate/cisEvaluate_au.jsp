<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cisEvaluate!=null}">编辑</c:if><c:if test="${cisEvaluate==null}">添加</c:if>现实表现材料和评价</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cisEvaluate_au" id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${cisEvaluate.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">形成日期</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input  class="form-control date-picker required" name="_createDate"
								type="text" data-date-format="yyyy-mm-dd"
								value="${cm:formatDate(cisEvaluate.createDate, "yyyy-MM-dd")}"/>
                        <span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">考察对象</label>
				<div class="col-xs-6">
					<select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects?type=0"
							name="cadreId" data-placeholder="请输入账号或姓名或学工号"  data-width="270">
						<option value="${cadre.id}">${cadre.user.realname}-${cadre.user.code}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">材料类型</label>
				<div class="col-xs-6">
						<select required data-rel="select2" name="type" data-placeholder="请选择"  data-width="270">
							<option></option>
							<c:forEach items="${CIS_EVALUATE_TYPE_MAP}" var="type">
								<option value="${type.key}">${type.value}</option>
							</c:forEach>
						</select>
					<script type="text/javascript">
						$("#modal form select[name=type]").val(${cisEvaluate.type});
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">材料内容</label>
				<div class="col-xs-6">
					<input ${cisEvaluate==null?'required':''} class="form-control" type="file" name="_file" />
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
					<textarea class="form-control" name="remark">${cisEvaluate.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cisEvaluate!=null}">确定</c:if><c:if test="${cisEvaluate==null}">添加</c:if>"/>
</div>

<script>
	register_date($('.date-picker'));
	$('#modalForm input[type=file]').ace_file_input({
		no_file:'请上传pdf文件 ...',
		btn_choose:'选择',
		btn_change:'更改',
		droppable:false,
		onchange:null,
		thumbnail:false, //| true | large
		allowExt: ['pdf']
	}).off('file.error.ace').on("file.error.ace",function(e, info){
		var size = info.error_list['size'];
		if(size!=undefined) alert("文件{0}超过${_uploadMaxSize/(1024*1024)}M大小".format(size));
		var ext = info.error_count['ext'];
		var mime = info.error_count['mime'];
		if(ext!=undefined||mime!=undefined) alert("请上传pdf文件".format(ext));
		e.preventDefault();
	});
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
    $('#modalForm [data-rel="select2"]').select2();
	register_user_select($('#modalForm select[name=cadreId]'));
</script>