<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>挂职结束</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cesTempPost_finish" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cesTempPost.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">姓名</label>
				<div class="col-xs-6 label-text">
					${cesTempPost.cadre.realname}
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">挂职拟结束时间</label>
				<div class="col-xs-6 label-text">
					${cm:formatDate(cesTempPost.endDate, "yyyy-MM-dd")}
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">挂职实际结束时间</label>
				<div class="col-xs-6">
					<div class="input-group" style="width: 200px">
						<input class="form-control date-picker required" type="text"  name="realEndDate"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(cesTempPost.realEndDate, "yyyy-MM-dd")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
					</div>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>

	register_date($('.date-picker'));
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