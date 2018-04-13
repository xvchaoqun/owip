<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${partySchool!=null}">编辑</c:if><c:if test="${partySchool==null}">添加</c:if>二级党校</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/partySchool_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${partySchool.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">二级党校名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${partySchool.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">设立日期</label>
				<div class="col-xs-6">
                    <input  class="form-control date-picker required" name="foundDate"
                            type="text" data-date-format="yyyy-mm-dd"
                            value="${cm:formatDate(partySchool.foundDate, "yyyy-MM-dd")}"/>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control limited" name="remark">${partySchool.remark}</textarea>
				</div>
			</div>

    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${partySchool!=null}">确定</c:if><c:if test="${partySchool==null}">添加</c:if></button>
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
    $.register.date($('.date-picker'));
    $('textarea.limited').inputlimiter();
</script>