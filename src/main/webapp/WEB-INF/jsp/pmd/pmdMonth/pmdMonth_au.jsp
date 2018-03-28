<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>修改缴费月份</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmd/pmdMonth_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${pmdMonth.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">缴费月份</label>
				<div class="col-xs-6">
                    <div class="input-group" style="width: 150px;">
                        <input  class="form-control date-picker required" name="month"
                               type="text" data-date-format="yyyy-mm"
                                data-date-min-view-mode="1"
                               value="${cm:formatDate(pmdMonth.payMonth, "yyyy-MM")}"/>
                        <span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                    </div>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${unit!=null}">确定</c:if><c:if test="${unit==null}">添加</c:if>"/>
</div>
<script>

    $("#modal form").validate({
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
</script>