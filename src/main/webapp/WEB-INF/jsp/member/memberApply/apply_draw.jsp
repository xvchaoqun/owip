<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>领取志愿书</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/apply_draw" autocomplete="off" disableautocomplete id="applyForm" method="post">
        <input type="hidden" name="ids[]" value="${param['ids[]']}">
        <c:set var="count" value="${fn:length(fn:split(param['ids[]'],\",\"))}"/>
        <c:if test="${count>1}">
            <div class="form-group">
                <label class="col-xs-5 control-label">处理记录</label>
                <div class="col-xs-4 label-text">
                        ${count} 条
                </div>
            </div>
        </c:if>
			<div class="form-group">
				<label class="col-xs-5 control-label"><span class="star">*</span>领取志愿书时间</label>
				<div class="col-xs-4">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_drawTime" type="text"
                               data-date-format="yyyy-mm-dd"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">

    <button id="applySubmitBtn" type="button" class="btn btn-primary"
			 data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"><i class="fa fa-check"></i> 确定</button>
</div>

<script>
    $.register.date($('.date-picker'), {endDate:"${_today}"});
    $("#applySubmitBtn").click(function(){$("#applyForm").submit();return false;});
    $("#applyForm").validate({
        submitHandler: function (form) {
            var $btn = $("#applySubmitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        goto_next("${param.gotoNext}");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>