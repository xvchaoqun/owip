<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${apiKey!=null?'修改':'添加'}API秘钥</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/apiKey_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${apiKey.id}">
            <div class ="form-group">
                <label class="col-xs-3 control-label"> 应用名称（app）</label>
                <div class="col-xs-6">
                    <input required class="form-control" type="text" name="name" value="${apiKey.name}">
                </div>
            </div>
            <div class ="form-group">
                <label class="col-xs-3 control-label">秘钥（key）</label>
                <div class="col-xs-6">
                    <input required class="form-control" type="text" name="apikey" value="${apiKey.apiKey}">
                </div>
                <a id="createApiKey" class="btn btn-xs btn-primary" style="text-decoration:none;"><i class="fa fa-random"></i>
                    随机生成</a>
            </div>
            <div class ="form-group">
                <label class="col-xs-3 control-label">备注</label>
                <div class="col-xs-6">
                    <textarea class="form-control" type="text" name="remark">${apiKey.remark}</textarea>
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

        var newApiKey = $.md5($.getRandomString(6));
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
</script>