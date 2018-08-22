<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>上传培训总结</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetUpperTrain_uploadNote" id="modalForm" method="post">
        <input type="hidden" name="id" value="${param.id}">
        <div class="form-group">
            <label class=" col-xs-4 control-label">参训人</label>
            <div class="col-xs-6 label-text">
                ${sysUser.realname}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">培训总结</label>
            <div class="col-xs-6">
                <input class="form-control" type="file" name="_word"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-xs-offset-4 col-xs-6">
                <input class="form-control" type="file" name="_pdf"/>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
        <i class="fa fa-check"></i> 确定</button>
</div>

<script>
    $.fileInput($("#modalForm input[name=_word]"),{
        no_file: '请上传word文件...',
        allowExt: ['doc', 'docx'],
        allowMime: ['application/msword','application/vnd.openxmlformats-officedocument.wordprocessingml.document']
    });
    $.fileInput($("#modalForm input[name=_pdf]"),{
        no_file: '请上传pdf文件...',
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
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