<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>退出报名</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/user/crsPost_quit" autocomplete="off" disableautocomplete id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="postId" value="${param.postId}">
        <input type="hidden" name="applicantId" value="${param.applicantId}">
        <div class="form-group">
            <label class="col-xs-3 control-label">退出申请</label>
            <div class="col-xs-6">
                <input class="form-control" type="file" name="_quitProof" />
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
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        SysMsg.success('退出成功。', '成功',function(){
                            $("#jqGrid2").trigger("reloadGrid");
                        });
                    }
                }
            });
        }
    });
    $.fileInput($('input[name=_quitProof]'), {
        no_file: '请上传pdf文件...',
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
    });
</script>