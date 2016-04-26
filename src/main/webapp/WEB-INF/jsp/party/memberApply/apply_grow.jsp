<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>发展为预备党员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/apply_grow" id="modalForm" method="post">
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
				<label class="col-xs-5 control-label">发展时间</label>
				<div class="col-xs-4">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_growTime" type="text"
                               data-date-format="yyyy-mm-dd"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
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
    register_date($('.date-picker'), {endDate:"${today}"});
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        goto_next("${param.type}");
                    }
                }
            });
        }
    });
</script>