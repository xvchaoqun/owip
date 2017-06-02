<%@ page language="java" contentType="text/html; charset=UTF-8"
                  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>评课说明</h3>
</div>
<div class="modal-body">
    <div style="margin-left: auto; margin-right: auto;margin-top: 10px;">
        <textarea id="noteId">${train.note}</textarea>
    </div>
</div>
<div class="modal-footer"><a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <a id="add_entity" class="btn btn-primary" onclick="updateNote()">确定</a></div>
<script type="text/javascript" src="${ctx}/extend/ke4/kindeditor-all-min.js"></script>
<script>
    var ke = KindEditor.create('#noteId', {
        allowFileManager: true,
        uploadJson: '${ctx}/ke/upload_json',
        fileManagerJson: '${ctx}/ke/file_manager_json',
        height: '550px',
        width: '715px'
    });

    function updateNote() {

        $.post('${ctx}/train_note', {id: '${train.id}', note: ke.html()}, function (data) {
            if (data.success) {
                $(".modal").modal("hide");
               // toastr.success('操作成功。', '成功');
            }
        });
    }
</script>