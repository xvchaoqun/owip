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
				<label class="col-xs-3 control-label"><span class="star">*</span>形成日期</label>
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
						<option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>材料类型</label>
				<div class="col-xs-6">
						<select required data-rel="select2" name="type" data-placeholder="请选择"  data-width="270">
							<option></option>
							<c:forEach items="<%=CisConstants.CIS_EVALUATE_TYPE_MAP%>" var="type">
								<option value="${type.key}">${type.value}</option>
							</c:forEach>
						</select>
					<script type="text/javascript">
						$("#modal form select[name=type]").val(${cisEvaluate.type});
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">${cisEvaluate==null?'*':''}材料内容</label>
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
	$.register.date($('.date-picker'));
	$.fileInput($('#modalForm input[type=file]'),{
		no_file:'请上传pdf文件',
		allowExt: ['pdf']
	})
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
	$.register.user_select($('#modalForm select[name=cadreId]'));
</script>