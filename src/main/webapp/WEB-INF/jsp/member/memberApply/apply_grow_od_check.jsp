<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>审核领取志愿书</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/apply_grow_od_check" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="ids[]" value="${param['ids[]']}">
        <c:set var="count" value="${fn:length(fn:split(param['ids[]'],\",\"))}"/>
        <c:if test="${count>1}">
            <div class="form-group">
                <label class="col-xs-4 control-label">处理记录</label>
                <div class="col-xs-7 label-text">
                        ${count} 条 （其中${totalCount}条记录需要分配志愿书编码）
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-4 control-label">系统将分配志愿书编码</label>
            <div class="col-xs-7 label-text">
                ${empty startSn?'<span class="text-danger">当无可用编码</span>':startSn.displaySn}<c:if test="${not empty endSn}"> ~ ${endSn.displaySn}</c:if>
                    <div>（共${assignCount}个编码<c:if test="${assignCount<totalCount}"><span class="text-danger">，该数量少于已选记录总数，将有部分记录无法分配志愿书编码</span></c:if> ）</div>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <button id="submitBtn" type="button" ${totalCount==0?'disabled':''}
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> 确定</button>
</div>

<script>
    $.register.date($('.date-picker'), {endDate:"${_today}"});
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modal form").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
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