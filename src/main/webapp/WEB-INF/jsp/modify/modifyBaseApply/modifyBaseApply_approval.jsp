<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>批量审批</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/modifyBaseApply_approval" autocomplete="off" disableautocomplete
          id="modalForm" method="post">

        <input type="hidden" name="ids[]" value="${param['ids[]']}">
        <div class="form-group">
            <label class="col-xs-3 control-label">已选记录</label>
            <div class="col-xs-8 label-text">
                ${fn:length(fn:split(param['ids[]'],","))} 条
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">审核意见</label>
            <div class="col-xs-8" style="font-size: 15px;">
                <div class="input-group">
                    <input required name="pass" type="checkbox" class="big" value="1"/> 通过审核
                    <input required name="pass" type="checkbox" class="big" value="2"/> 未通过审核
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">依据</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" type="text" name="checkReason" rows="2"></textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" type="text" name="checkRemark" rows="2"></textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"> 确定
    </button>
</div>

<script>
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {

            var pass = $('#modal input[name=pass]:checked').val();
            if (pass != 1 && pass != 2) {
                SysMsg.warning("请选择审核意见");
                return;
            }
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                data: {status: (pass == 1)},
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>