<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>修改管理员信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcsAdmin_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${pcsAdmin.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">手机号码</label>

            <div class="col-xs-6">
                <input class="form-control" type="text" name="mobile" value="${sysUser.mobile}">
                <span class="help-block">* 同系统用户的手机号码</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>

            <div class="col-xs-6">
                <textarea class="form-control limited" type="text"
                          name="remark" maxlength="200">${pcsAdmin.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $('textarea.limited').inputlimiter();
</script>