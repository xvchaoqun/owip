<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>新证件应交组织部日期</h3>
</div>
<div class="modal-body">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>确定日期</label>
			<div class="col-xs-6">
				<div class="input-group" style="width: 200px">
					<input required class="form-control date-picker" name="expectDate" type="text"
						   data-date-format="yyyy-mm-dd" value="${cm:formatDate(taiwanRecord.expectDate, "yyyy-MM-dd")}"/>
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>
<script>
	$.register.date($('#modal .date-picker'))
    $("#modal input[type=submit]").click(function(){
		var expectDate = $("#modal input[name=expectDate]").val();
		if(expectDate==''){
			$("#modal input[name=expectDate]").focus();
			return;
		}
		$.post("${ctx}/abroad/taiwanRecord_expectDate",{id:'${taiwanRecord.id}', expectDate:expectDate},function(ret){
			if(ret.success){
				$("#modal").modal('hide');
				$(window).resize();
			}
		});
	})
</script>