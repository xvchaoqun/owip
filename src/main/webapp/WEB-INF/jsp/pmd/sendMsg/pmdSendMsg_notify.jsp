<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>短信催交</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmd/pmdSendMsg_notify" id="modalForm" method="post">
        <input type="hidden" name="id" value="${param.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">手机号码</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="mobile" value="${shortMsgBean.mobile}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">短信内容</label>
            <div class="col-xs-8 label-text">
                ${shortMsgBean.content}
            </div>
        </div>

    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> 确定发送</button>
</div>

<script>
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        SysMsg.info("发送成功。")
                    }
                }
            });
        }
    });
</script>