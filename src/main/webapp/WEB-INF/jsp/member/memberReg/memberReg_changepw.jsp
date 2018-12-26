<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>修改登录密码</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberReg_changepw" id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberReg.id}">

        <div class="form-group">
            <label class="col-xs-3 control-label">用户名</label>

            <div class="col-xs-6 label-text">
                ${memberReg.username}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">新密码</label>

            <div class="col-xs-6">
                <input required class="form-control" autocomplete="off" type="text" onfocus="this.type='password'" name="password"  style="width: 150px">
                密码由6-16位的字母、下划线和数字组成
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">密码确认</label>
            <div class="col-xs-6">
                <input required class="form-control" autocomplete="off" type="text" onfocus="this.type='password'" name="repassword"  style="width: 150px">
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>
<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $form = $("#modalForm");
            var $passwd = $("input[name=password]", $form);
            var $repasswd = $("input[name=repassword]", $form);

            if ($.trim($passwd.val()) == "") {
                $passwd.val('').focus();
                return;
            }
            if ($.trim($repasswd.val()) == "") {
                $repasswd.val('').focus();
                return;
            }
            if ($.trim($repasswd.val()) != $.trim($passwd.val())) {
                $repasswd.focus();
                SysMsg.info("两次输入的密码不相同，请重新输入。");
                return;
            }

            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal("hide");
                        //SysMsg.success('修改成功。', '成功', function () {
                            $("#jqGrid").trigger("reloadGrid");
                        //});
                    }
                }
            });
        }
    });
</script>