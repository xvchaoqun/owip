<%@ page language="java" contentType="text/html; charset=UTF-8"
                  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:if test="${param.detail!=1}">
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>评课说明</h3>
</div>
<div class="modal-body">
    <div style="margin-left: auto; margin-right: auto;margin-top: 10px;">
        <textarea id="noteId">${cetTrain.evaNote}</textarea>
    </div>
</div>
<div class="modal-footer"><a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <a class="btn btn-primary" onclick="updateNote()">确定</a></div>
</c:if>
<c:if test="${param.detail==1}">
    <div class="space-4"></div>
<div style="width: 715px;">
    <textarea id="noteId">${cetTrain.evaNote}</textarea>
<div class="modal-footer center"><a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <a class="btn btn-primary" onclick="updateNote()">确定</a></div>
</div>
</c:if>
<script>
    var ke = KindEditor.create('#noteId', {
        allowFileManager: true,
        uploadJson: '${ctx}/ke/upload_json',
        fileManagerJson: '${ctx}/ke/file_manager_json',
        height: '550px',
        width: '715px'
    });

    function updateNote() {

        $.post('${ctx}/cet/cetTrain_evaNote', {trainId: '${cetTrain.id}', evaNote: ke.html()}, function (data) {
            if (data.success) {
                <c:if test="${param.detail!=1}">
                $(".modal").modal("hide");
                </c:if>
                <c:if test="${param.detail==1}">
                    SysMsg.success('保存成功。', '成功');
                </c:if>
            }
        });
    }
</script>