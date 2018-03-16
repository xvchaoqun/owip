<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>短信通知所有分党委管理员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmd/pmdSendMsg_notifyPartyAdmins" id="modalForm" method="post">

        <div class="form-group">
            <label class="col-xs-3 control-label">短信内容：</label>
            <div class="col-xs-8 label-text">
                ${msg}
            </div>
        </div>

    </form>
</div>
<div class="modal-footer">
    <div style="color: red; font-size: 16px; font-weight: bolder;text-align: left;padding-bottom: 20px;">
        注：
        <div>
            此短信会通知所有的分党委管理员，发送之后可能有延迟，请勿重复发送。
        </div>
    </div>
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