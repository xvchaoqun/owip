<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>修改密码</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dr/inspector_changePasswd" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span> 原密码</label>
            <div class="col-xs-5">
                <input required type="password" name="oldPasswd">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span> 新密码</label>
            <div class="col-xs-5">
                <input required type="password" name="passwd" id="passwd">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label"><span class="star">*</span> 新密码确认</label>

            <div class="col-sm-5">
                <input required type="password" name="repasswd">
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> 确认</button>
</div>
<script type="text/javascript">

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
            rules: {
                repasswd:{
                    equalTo:'#passwd'
                }
            },
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        SysMsg.success('修改密码成功,请重新登录。', '成功',function () {
                                _logout();
                        });
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>