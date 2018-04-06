<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetProject!=null}">编辑</c:if><c:if test="${cetProject==null}">添加</c:if>专题培训</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetProject_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetProject.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">年度</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" placeholder="请选择年份"
							   name="year"
							   type="text"
							   data-date-format="yyyy" data-date-min-view-mode="2"
							   value="${empty cetProject.year?_thisYear:cetProject.year}"/>
                                            <span class="input-group-addon"> <i
													class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">培训时间</label>
				<div class="col-xs-6">
					<input required class="form-control date-picker" name="startDate"
						   type="text" style="width: 120px;float: left"
						   data-date-format="yyyy-mm-dd"
						   value="${cm:formatDate(cetProject.startDate,'yyyy-MM-dd')}"/>
					<div style="float: left;margin: 5px 5px 0 5px;"> 至 </div>
					<input required class="form-control date-picker" name="endDate"
						   type="text" style="width: 120px;float: left"
						   data-date-format="yyyy-mm-dd"
						   value="${cm:formatDate(cetProject.endDate,'yyyy-MM-dd')}"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">培训班名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${cetProject.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">参训人员类型</label>
				<div class="col-xs-9 label-text" id="traineeTypeDiv">
					<c:forEach items="${traineeTypeMap}" var="entity">
						<label>
							<input name="traineeTypeIds[]" type="checkbox" value="${entity.key}"> ${entity.value.name}&nbsp;
							<span class="lbl"></span>
						</label>
					</c:forEach>
				</div>
			</div>
			<%--<div class="form-group">
				<label class="col-xs-3 control-label">文件名</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="fileName" value="${cetProject.fileName}">
				</div>
			</div>--%>
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
				<label class="col-xs-3 control-label">总学时</label>
				<div class="col-xs-6">
                        <input required class="form-control period" type="text" name="period" value="${cetProject.period}">
				</div>
			</div>
			<%--<div class="form-group">
				<label class="col-xs-3 control-label">达到结业要求的学时数</label>
				<div class="col-xs-6">
                        <input required class="form-control period" type="text" name="requirePeriod" value="${cetProject.requirePeriod}">
				</div>
			</div>--%>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
					<textarea class="form-control limited"
							  name="remark">${cetProject.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary" data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
		<i class="fa fa-check"></i> <c:if test="${cetProject!=null}">确定</c:if><c:if test="${cetProject==null}">添加</c:if></button>
</div>

<script>
	var traineeTypeIds = ${cm:toJSONArray(traineeTypeIds)};
	for(i in traineeTypeIds){
		$('#modalForm input[name="traineeTypeIds[]"][value="'+ traineeTypeIds[i] +'"]').prop("checked", true);
	}
    $("#submitBtn").click(function(){
		if($('#modalForm input[name="traineeTypeIds[]"]:checked').length==0){
			$.tip({
				$target: $("#traineeTypeDiv"),
				at: 'top center', my: 'bottom center',
				msg: "请选择参训人员类型。"
			});
		}
		$("#modalForm").submit();return false;
	});
    $("#modalForm").validate({
        submitHandler: function (form) {

			if($('#modalForm input[name="traineeTypeIds[]"]:checked').length==0){
				return false;
			}

			var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){

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
	$('textarea.limited').inputlimiter();
	$.fileInput($("#modalForm input[name=_pdfFilePath]"),{
		allowExt: ['pdf'],
		allowMime: ['application/pdf']
	});
	$.fileInput($("#modalForm input[name=_wordFilePath]"),{
		allowExt: ['doc', 'docx'],
		allowMime: ['application/msword','application/vnd.openxmlformats-officedocument.wordprocessingml.document']
	});
</script>