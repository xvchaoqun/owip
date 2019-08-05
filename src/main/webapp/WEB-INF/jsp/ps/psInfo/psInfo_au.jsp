<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${psInfo!=null}">编辑</c:if><c:if test="${psInfo==null}">添加</c:if>二级党校</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/ps/psInfo_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${psInfo.id}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>二级党校名称</label>
			<div class="col-xs-6">
				<input required class="form-control" type="text" name="name" value="${psInfo.name}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>成立时间</label>
			<div class="col-xs-6">
				<div class="input-group date" data-date-format="yyyy.mm.dd" <%--style="width: 130px"--%>>
					<input required class="form-control" name="foundDate" type="text"
						   value="${cm:formatDate(psInfo.foundDate,'yyyy.MM.dd')}" />
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>批次</label>
			<div class="col-xs-6">
				<input required class="form-control" type="text" name="seq" value="${psInfo.seq}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">备注</label>
			<div class="col-xs-6">
				<textarea class="form-control limited" name="remark">${psInfo.remark}</textarea>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${psInfo!=null}">确定</c:if><c:if test="${psInfo==null}">添加</c:if></button>
</div>
<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
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
    $.register.date($('.input-group.date'));
    $('textarea.limited').inputlimiter();
</script>