<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>换领领取志愿书</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/applySn_change" autocomplete="off"
          disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${param.id}">

        <div class="form-group">
            <label class="col-xs-4 control-label">原志愿书编码</label>
            <div class="col-xs-7 label-text">
                ${applySn.displaySn}
                <span class="help-block">注：原志愿书编码将作废，不可再使用</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">新志愿书编码</label>
            <div class="col-xs-7 label-text">
                ${empty newApplySn?"无可用编码":newApplySn.displaySn}
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <button id="submitBtn" type="button" ${empty newApplySn?'disabled':''}
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
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>