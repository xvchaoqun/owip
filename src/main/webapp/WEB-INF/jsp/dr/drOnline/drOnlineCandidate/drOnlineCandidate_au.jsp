<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" onclick="openView(${drOnlineCandidate.postId})" aria-hidden="true" class="close">&times;</button>
    <h3>编辑候选人姓名</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dr/drOnlineCandidate_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${drOnlineCandidate.id}">

		<div class="form-group">
			<label class="col-xs-3 control-label">工号</label>
			<div class="col-xs-6 label-text">
				${drOnlineCandidate.user.code}
			</div>
		</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="realname" value="${drOnlineCandidate.realname}">
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-3 control-label">备注</label>
                <div class="col-xs-6">
                    <textarea class="form-control limited" name="remark">${drOnlineCandidate.remark}</textarea>
                </div>
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" onclick="openView(${drOnlineCandidate.postId})" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="修改"/>
</div>

<script>
	$('[data-rel="select2"]').select2();

	$("#modalForm :checkbox").bootstrapSwitch();
	$('textarea.limited').inputlimiter();
        $("#modal form").validate({
            submitHandler: function (form) {
                $(form).ajaxSubmit({
                    success:function(ret){
                        if(ret.success){
							$.reloadMetaData(function(){
								openView("${drOnlineCandidate.postId}", "${param.pageNo}");
							});
                        }
                    }
                });
            }
        });
</script>