<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>调换学工号</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sysUser_exchangeCode" autocomplete="off" disableautocomplete id="modalForm"
          method="post">
        <input type="hidden" name="oldUserId" value="${param.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">学工号</label>
            <div class="col-xs-8 label-text">
                ${oldUser.code}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-8 label-text">
                ${oldUser.realname}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">调换学工号</label>
            <div class="col-xs-8">
                 <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects" data-width="290"
                            name="newUserId" data-placeholder="请输入账号或姓名或学工号">
                        <option></option>
                 </select>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <div class="note">注：仅调换两个账号对应的账号、学工号</div>
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确认"/>
</div>

<script>
    $("#modal input[type=submit]").click(function () {
        $("#modal form").submit();
        return false;
    });
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                        SysMsg.success("操作成功");
                    }
                }
            });
        }
    });
    $.register.user_select($('[data-rel="select2-ajax"]'));
</script>