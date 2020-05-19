<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div id="body-content">
    <div style="width: 800px">
        <%--<div class="page-header">
            <h1>
                提交意见与建议
            </h1>
        </div>--%>
        <form class="form-horizontal" autocomplete="off" disableautocomplete id="feedbackForm" method="post"
              action="${ctx}/user/feedback_au">
            <div class="form-group">
                <div class="col-sm-12">
                    <label><span class="star">*</span>标题：</label>
                    <input type="text" name="title" maxlength="80" data-my="center"
                           data-at="center center" required data-placeholder="请输入标题" style="width:100%;">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-12">
                    <label><span class="star">*</span>意见或建议：</label>
                    <textarea data-my="center" data-at="center center" required data-placeholder="请在此输入您的意见或建议"
                              name="content"
                              class="col-xs-6 col-sm-3" rows="8" style="width:100%;"></textarea>
                </div>
            </div>
            <div class="form-group">
                <div class="col-xs-12">
                    <label class="col-xs-4 control-label">上传相关图片（可传多张）：</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="file" name="_pics" multiple="multiple"/>
                    </div>
                </div>
            </div>
            <div class="clearfix form-actions center">
                <button class="btn btn-info" type="button" id="feedbackSubmitBtn" data-loading-text="提交中..."
                        data-success-text="提交成功" autocomplete="off">
                    <i class="ace-icon fa fa-check bigger-110"></i>
                    提交
                </button>
            </div>
        </form>
        <div class="tabbable" style="margin-top: 20px">
            <div class="tab-content no-border no-padding">
                <div id="feedbacks" class="tab-pane in active">
                    <c:import url="/user/feedback_list"></c:import>
                </div>
            </div>
            <!-- /.tab-content -->
        </div>
    </div>
</div>
<div id="body-content-view"></div>
<script>
    $.fileInput($('input[type=file]'), {
        no_file: '请上传图片文件...',
        allowExt: ['jpg', 'jpeg', 'png', 'gif'],
        allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
    });

    $("#feedbackSubmitBtn").click(function () {
        $("#feedbackForm").submit();
        return false;
    });
    $("#feedbackForm").validate({
        submitHandler: function (form) {
            var $btn = $("#feedbackSubmitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        SysMsg.success("提交成功", "提交成功，感谢您的支持。", function () {
                            $btn.button("success").addClass("btn-success");
                            _reloadList();
                        });
                        $("#feedbackForm")[0].reset();
                        $('input[type=file]').ace_file_input('reset_input');
                    }

                    $btn.button('reset');
                }
            });
        }
    });
</script>
