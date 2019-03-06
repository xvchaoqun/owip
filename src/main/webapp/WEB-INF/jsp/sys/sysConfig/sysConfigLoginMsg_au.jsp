<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>编辑常用公告
    <c:if test="${not empty sysConfigLoginMsg}">
        <a class="btn btn-success btn-sm" href="javascript:;" onclick="_copyLoginMsg()">
            <i class="fa fa-copy"></i> 使用此公告
        </a>
    </c:if>
    </h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sysConfigLoginMsg_au" id="modalForm" method="post">
            <input type="hidden" name="id" value="${sysConfigLoginMsg.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>名称</label>
				<div class="col-xs-6">
                    <input required class="form-control width400" maxlength="50"
                           type="text" name="name" value="${sysConfigLoginMsg.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                   <textarea id="modal-loginMsg">
                       ${sysConfigLoginMsg.loginMsg}
                   </textarea>
                    <input type="hidden" name="loginMsg">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control width400 limited" maxlength="100" name="remark">${sysConfigLoginMsg.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="保存"/>
</div>
<script>
    var modalKE = KindEditor.create('#modal-loginMsg', {
        filterMode: false,
        allowFileManager: true,
        items: [
            'source', '|', 'undo', 'redo', '|', 'preview', 'cut', 'copy', 'paste',
            'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
            'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
            'superscript', 'clearhtml', 'quickformat', 'selectall',
            'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
            'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
            'flash', 'media', 'insertfile', 'table', 'hr',
            'anchor', 'link', 'unlink', '|', 'fullscreen'
        ],
        uploadJson: '${ctx}/ke/upload_json',
        fileManagerJson: '${ctx}/ke/file_manager_json',
        height: '450px',
        width: '400px',
        minWidth: 400,
        cssPath: '${ctx}/assets/css/font-awesome.css'
    });
    <c:if test="${empty sysConfigLoginMsg}">
    modalKE.html(ke.html());
    </c:if>
    function _copyLoginMsg(){
        ke.html(modalKE.html());
        $("#modal").modal("hide");
    }
    $('textarea.limited').inputlimiter();
    $("#modal form").validate({
        submitHandler: function (form) {

            $("input[name=loginMsg]", form).val(modalKE.html());
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        _reloadLoginMsg();
                    }
                }
            });
        }
    });
</script>