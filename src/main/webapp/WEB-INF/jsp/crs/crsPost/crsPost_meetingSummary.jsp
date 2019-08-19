<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

    <form class="form-horizontal" action="${ctx}/crsPost_meetingSummary" id="meetingSummaryForm" method="post">
        <input type="hidden" name="id" value="${crsPost.id}">
        <div class="form-group">
            <div class="col-xs-6">
                <input type="hidden" name="meetingSummary">
                <textarea id="contentId">${crsPost.meetingSummary}</textarea>
            </div>
        </div>
    </form>

<div class="modal-footer center">
    <c:if test="${not empty crsPost.meetingSummary}">
    <a href="javascript:;" onclick="_stepContentReload()" class="btn btn-default">取消</a>
    </c:if>
    <input type="button" id="meetingSummarySubmitBtn" class="btn btn-primary" value="确定"/>
</div>
<script>
    var ke = KindEditor.create('#contentId', {
        allowFileManager: true,
        uploadJson: '${ctx}/ke/upload_json',
        fileManagerJson: '${ctx}/ke/file_manager_json',
        height: '400px',
        width: '450px',
        minWidth: 400
    });

    $("#meetingSummarySubmitBtn").click(function(){$("#meetingSummaryForm").submit(); return false;})
    $("#meetingSummaryForm").validate({
        submitHandler: function (form) {
            $("#meetingSummaryForm input[name=meetingSummary]").val(ke.html());
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        _stepContentReload()
                    }
                }
            });
        }
    });
    $('#meetingSummaryForm [data-rel="select2"]').select2();
</script>