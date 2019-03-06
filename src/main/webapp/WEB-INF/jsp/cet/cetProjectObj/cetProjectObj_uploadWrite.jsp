<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>上传心得体会</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetProjectObj_uploadWrite" id="modalForm" method="post">
        <input type="hidden" name="id" value="${param.id}">
        <div class="form-group">
            <label class=" col-xs-3 control-label">学员姓名</label>
            <div class="col-xs-6 label-text">
                ${sysUser.realname}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>心得体会</label>
            <div class="col-xs-6">
                <input required class="form-control" type="file" name="_writeFilePath"/>
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
    $.fileInput($("#modalForm input[name=_writeFilePath]"),{
        allowExt: ['pdf','doc', 'docx'],
        allowMime: ['application/pdf','application/msword','application/vnd.openxmlformats-officedocument.wordprocessingml.document']
    });

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>