<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>修改归还日期（D${passportDraw.id}-${passportDraw.user.realname}）</h3>
</div>
<div class="modal-body">
		<div class="form-group">
			<label class="col-xs-3 control-label">归还日期</label>
			<div class="col-xs-6">
				<div class="input-group" style="width: 200px">
					<input required class="form-control date-picker" name="_returnDate" type="text"
						   data-date-format="yyyy-mm-dd" value="${cm:formatDate(passportDraw.returnDate, "yyyy-MM-dd")}"/>
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
	register_date($('#modal .date-picker'))
    $("#modal input[type=submit]").click(function(){
		var _returnDate = $("#modal input[name=_returnDate]").val();
		if(_returnDate==''){
			$("#modal input[name=_returnDate]").focus();
			return;
		}
		$.post("${ctx}/reset_passportDraw_returnDate",{id:'${passportDraw.id}', _returnDate:_returnDate},function(ret){
			if(ret.success){
				$("#modal").modal('hide');
				$(window).resize();
			}
		});
	})
</script>