<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div style="padding-top: 20px;"></div>

<div class="tabbable">
    <div class="tab-content no-border no-padding">
        <div id="feedbacks" class="tab-pane in active">
        <c:import url="/user/feedback_list"></c:import>
        </div>
    </div>
    <!-- /.tab-content -->
</div>
<div class="page-header" style="margin-top: 30px">
    <h1>
        *提交意见与建议
    </h1>
</div>
<form class="form-horizontal" id="modalForm" method="post" action="${ctx}/user/feedback_au">

    <div class="form-group">

        <div class="col-sm-12">
            <textarea data-my="center" data-at="center center" required placeholder="请在此输入您的意见或建议" name="content"
                      class="col-xs-6 col-sm-3" rows="10" style="width:100%;"></textarea>
        </div>
    </div>

    <div class="clearfix form-actions center">
            <button class="btn btn-info" type="submit" id="submitBtn" data-loading-text="提交中..."
                    data-success-text="提交成功" autocomplete="off">
                <i class="ace-icon fa fa-check bigger-110"></i>
                提交
            </button>
    </div>
</form>
<script>
    $("form").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        bootbox.alert("提交成功。", function () {
                            $btn.button("success").addClass("btn-success");
                            $.hashchange();
                        });
                    } else {
                        $btn.button('reset');
                    }
                }
            });
        }
    });
</script>
