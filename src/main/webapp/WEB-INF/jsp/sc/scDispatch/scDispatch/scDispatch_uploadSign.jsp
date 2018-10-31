<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>正式签发</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scDispatch_uploadSign" id="uploadSignForm" method="post">
        <input type="hidden" name="id" value="${param.id}">
        <div class="form-group">
            <label class=" col-xs-3 control-label">标题</label>
            <div class="col-xs-6 label-text">
                ${scDispatch.title}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">签批文件</label>
            <div class="col-xs-6">
                <input required class="form-control" type="file" name="_signFilePath"/>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="uploadSignBtn" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
        <i class="fa fa-check"></i> 确定</button>
</div>

<script>
    $.fileInput($("#uploadSignForm input[name=_signFilePath]"),{
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
    });

    $("#uploadSignBtn").click(function(){$("#uploadSignForm").submit();return false;});
    $("#uploadSignForm").validate({
        submitHandler: function (form) {
            var $btn = $("#uploadSignBtn").button('loading');
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