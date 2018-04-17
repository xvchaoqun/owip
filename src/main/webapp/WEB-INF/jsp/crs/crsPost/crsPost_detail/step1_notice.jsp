<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>

<div class="widget-box" style="width: 750px">
    <div class="widget-header">
        <h4 class="smaller">
            <div class="pull-right" style="margin-right: 10px">
                <form action="${ctx}/crsPost_uploadNotice"
                      enctype="multipart/form-data" method="post"
                      class="btn-upload-form">
                    <button type="button"
                            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中..."
                            class="btn btn-xs btn-success">
                        <i class="ace-icon fa fa-upload"></i>
                        上传（PDF文件）
                    </button>
                    <input type="hidden" name="id" value="${crsPost.id}">
                    <input type="file" name="file" id="upload-file">
                </form>
            </div>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <div class="swf-file-view">
                <c:import url="${ctx}/swf/preview?type=html&path=${crsPost.notice}"/>
            </div>
        </div>
    </div>
</div>
<script>
    $("#upload-file").change(function () {
        //console.log($(this).val())
        if ($(this).val() != "") {
            var $this = $(this);
            var $form = $this.closest("form");
            var $btn = $("button", $form).button('loading');
            var preHtml = $(".swf-file-view").html();
            $(".swf-file-view").html('<img src="${ctx}/img/loading.gif"/>')
            $form.ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        //console.log(ret)
                        $(".swf-file-view").load("${ctx}/swf/preview?type=html&path=" + encodeURI(ret.file));

                        $("#modalForm input[name=file]").val(ret.file);
                        $("#modalForm input[name=fileName]").val(ret.fileName);
                    }else{
                        $(".swf-file-view").html(preHtml);
                    }
                    $btn.button('reset');
                    $this.attr("disabled", false);
                }
            });
            $this.attr("disabled", true);
        }
    });
</script>
