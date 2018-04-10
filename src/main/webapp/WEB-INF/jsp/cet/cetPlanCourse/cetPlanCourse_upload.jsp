<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>上传附件</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetPlanCourse_upload" id="modalForm" method="post">
        <input type="hidden" name="planCourseId" value="${param.planCourseId}">
        <div class="form-group">
            <label class=" col-xs-4 control-label">附件</label>
            <div class="col-xs-6">
                <input type="file" name="_file" required/>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> 确定</button>
</div>

<script>
    $.fileInput($('#modalForm input[type=file]'))
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                }
            });
        }
    });
</script>