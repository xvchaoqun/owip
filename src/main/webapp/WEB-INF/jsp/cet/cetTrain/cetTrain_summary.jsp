<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>编辑内容简介</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetTrain_summary" id="summaryForm" method="post">
        <input type="hidden" name="id" value="${cetTrain.id}">
        <div class="form-group">
            <div class="col-xs-6">
                <input type="hidden" name="summary">
                <textarea id="contentId">${cetTrain.summary}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer center">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="button" id="summarySubmitBtn" class="btn btn-primary" value="确定"/>
</div>
<script type="text/javascript" src="${ctx}/extend/ke4/kindeditor-all-min.js"></script>
<script>
    var ke = KindEditor.create('#contentId', {
        allowFileManager: true,
        uploadJson: '${ctx}/ke/upload_json',
        fileManagerJson: '${ctx}/ke/file_manager_json',
        cssPath:"${ctx}/css/ke.css",
        height: '400px',
        width: '720px',
        minWidth: 400
    });

    $("#summarySubmitBtn").click(function(){$("#summaryForm").submit(); return false;})
    $("#summaryForm").validate({
        submitHandler: function (form) {
            $("#summaryForm input[name=summary]").val(ke.html());
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