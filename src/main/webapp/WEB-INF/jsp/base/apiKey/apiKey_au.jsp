<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${apiKey!=null?'修改':'添加'}API接口</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/apiKey_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${apiKey.id}">
            <div class ="form-group">
                <label class="col-xs-3 control-label"> Api名称</label>
                <div class="col-xs-6">
                    <input required class="form-control" type="text" name="name" value="${apiKey.name}">
                </div>
            </div>
            <div class ="form-group">
                <label class="col-xs-3 control-label">键值</label>
                <div class="col-xs-6">
                    <input required class="form-control" type="text" name="apikey" value="${apiKey.apiKey}">
                </div>
                <a id="createApiKey" style="text-decoration:none;">生成键值</a>
            </div>
            <div class ="form-group">
                <label class="col-xs-3 control-label">备注</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="remark" value="${apiKey.remark}">
                </div>
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty apiKey?'确定':'添加'}</button>
</div>
<script src="${ctx}/js/jquery.md5.js"></script>
<script>
    $("#createApiKey").click(function (){
        function _getRandomString(len) {
            len = len || 32;
            var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
            var maxPos = $chars.length;
            var pwd = '';
            for (i = 0; i < len; i++) {
                pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
            }
            return pwd;
        };
        var newApiKey = $.md5(_getRandomString(6));
        $("[name='apikey']").val(newApiKey);
    });


    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>