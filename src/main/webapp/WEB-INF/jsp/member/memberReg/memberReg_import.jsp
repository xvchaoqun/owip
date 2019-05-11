<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>批量生成账号</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" autocomplete="off" disableautocomplete id="modalForm"
          enctype="multipart/form-data" action="${ctx}/memberReg_import" method="post">
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>Excel文件</label>
            <div class="col-xs-6">
                <input class="form-control" type="file" name="xlsx" required extension="xlsx"/>
            </div>
        </div>
    </form>
    <div class="well">
        <span class="help-inline">导入的文件请严格按照
            <a href="${ctx}/attach?code=sample_memberReg">
                系统注册账号录入样表.xlsx</a>（点击下载）的数据格式</span>
    </div>
</div>
<div class="modal-footer">
	<div class="note">注：导入成功后，将保存至“审批通过”列表。</div>
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">确定
    </button>
</div>

<script>
    $.fileInput($('#modalForm input[type=file]'))
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        messages: {
            "xlsx": {
                required: "请选择文件",
                extension: "请上传 xlsx格式的文件"
            }
        },
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                dataType: "json",
                success: function (ret) {
                    if (ret && ret.addCount >= 0) {
                        var result = '操作成功，总共{0}条记录，其中成功生成{1}个账号，' +
                            '<font color="red">{2}个身份证号重复</font><br>此次生成批次为[{3}]';
                        SysMsg.success(result.format(ret.total, ret.addCount,
                            ret.total - ret.addCount, $.trim(ret.importSeq)), '成功', function () {
                            page_reload();
                        });
                    }
                    $btn.button('reset');
                }
            });
        }
    });

</script>