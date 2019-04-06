<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${empty feedback?'添加':'修改'}${empty param.fid?'意见和建议':'回复'}</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" autocomplete="off" disableautocomplete id="modalForm" method="post"
          action="${ctx}/feedback_au">
        <input type="hidden" name="id" value="${param.id}">
        <input type="hidden" name="fid" value="${param.fid}">
        <c:if test="${(not empty feedback && empty feedback.fid) || (empty feedback && empty param.fid)}">
        <div class="form-group">
            <div class="col-xs-12">
                <label class="col-xs-3 control-label"><span class="star">*</span>标题：</label>
                <div class="col-xs-6">
                    <input type="text" class="form-control"  name="title" maxlength="80" value="${feedback.title}"
                           required data-placeholder="请输入标题">
                </div>
            </div>
        </div>
            </c:if>
        <div class="form-group">
            <div class="col-xs-12">
                <label class="col-xs-3 control-label"><span class="star">*</span>${empty param.fid?'意见和建议':'回复'}：</label>
                <div class="col-xs-6">
                    <textarea class="form-control" required data-placeholder="请在此输入"
                              name="content" rows="4">${feedback.content}</textarea>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-xs-12">
                <label class="col-xs-3 control-label">上传相关截图：<br/>（可传多张）</label>
                <div class="col-xs-6">
                    <input class="form-control" type="file" name="_pics" multiple="multiple"/>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"> 确定
    </button>
</div>
<script>
    $.fileInput($('input[type=file]'), {
        no_file: '请上传图片文件...',
        allowExt: ['jpg', 'jpeg', 'png', 'gif'],
        allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
    });
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modal form").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        if (typeof _reloadList != 'undefined' && _reloadList instanceof Function) {
                            _reloadList();
                        }
                        /*if(_reloadList != undefined) {
                            _reloadList();
                        }*/
                        <c:if test="${param.isNotReply!=1}">
                        _reloadDetail();
                        </c:if>
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>