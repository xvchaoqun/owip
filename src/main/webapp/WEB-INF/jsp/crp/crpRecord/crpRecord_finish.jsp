<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>挂职结束</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crpRecord_finish" id="modalForm" method="post">
        <input type="hidden" name="id" value="${crpRecord.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">姓名</label>
				<div class="col-xs-6 label-text">
					${crpRecord.user.realname}
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">挂职拟结束时间</label>
				<div class="col-xs-6 label-text">
					${cm:formatDate(crpRecord.endDate, "yyyy-MM")}
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>挂职实际结束时间</label>
				<div class="col-xs-6">
					<div class="input-group" style="width: 200px">
						<input class="form-control date-picker required" type="text"  name="realEndDate"
							   data-date-min-view-mode="1"
							   data-date-format="yyyy-mm" value="${cm:formatDate(crpRecord.realEndDate, "yyyy-MM")}">
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
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>

	$.register.date($('.date-picker'));
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
</script>