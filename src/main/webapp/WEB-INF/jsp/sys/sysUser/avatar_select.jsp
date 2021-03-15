<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>修改照片</h3>
</div>
<div class="modal-body">
    <div style="width: 480px;height: 600px;float: left">
        <div class="note" style="margin-bottom: 10px">注：选择照片后，点击“确定”按钮后请再点击“${not empty param.op?param.op:'提交申请'}”按钮，否则不生效。</div>
        <img id="image" src="${ctx}/avatar?path=${param.path}&t=<%=new Date().getTime()%>">
    </div>
    <div style="width: 520px;float: left;">
        <div style="float: left;font-size: larger;font-weight: bolder;padding-left: 50px">效果预览：</div>
        <div class="img-preview"></div>
        <div style="float: left;padding-left: 20px;padding-top: 10px">
            ${cm:getHtmlFragment('hf_avatar_info').content}
        </div>

    </div>
</div>
<div class="modal-footer" style="clear: both">

    <label class="btn btn-warning btn-upload" for="inputImage" title="选择一张照片">
        <input type="file" class="sr-only" id="inputImage" name="file" accept=".jpg,.jpeg,.png,.gif,.bmp,.tiff">
        <span class="docs-tooltip" data-toggle="tooltip" data-animation="false" title="选择一张照片">
          <span class="fa fa-upload"></span> 选择照片
        </span>
    </label>
     <button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中">
        <i class="ace-icon fa fa-check-circle-o bigger-110"></i> 确定
    </button>
</div>
<style>
    #modal .img-preview {
        background-color: #f7f7f7;
        text-align: center;
        width: 100%;
        float: left;
        overflow: hidden;
        height: 16rem;
        width: 20rem;
    }
    #modal img, #modal .img-preview > img {
        max-width: 100%;
    }
</style>
<script src="${ctx}/extend/js/cropper/cropper.min.js"></script>
<link href="${ctx}/extend/js/cropper/cropper.min.css" rel="stylesheet">
<script src="${ctx}/extend/js/cropper/jquery-cropper.min.js"></script>
<script>
    var $image = $('#modal #image');
    var options = {
        viewMode: 1,
        dragMode:'move',
        responsive: true,
        minContainerWidth: 480,
        minContainerHeight: 600,
        aspectRatio: 4 / 5,
        autoCropArea: 1,
        preview: '.img-preview',
        crop: function (event) {
        }
    };
    $('#modal').on('shown.bs.modal', function (e) {//赋值
        $image.cropper(options);
    });
    var $inputImage = $('#modal #inputImage');
    var uploadedImageURL;
    $inputImage.change(function () {
        var files = this.files;
        var file;
        if (!$image.data('cropper')) {
            return;
        }
        if (files && files.length) {
            file = files[0];
            if (/^image\/\w+$/.test(file.type)) {
                if (uploadedImageURL) {
                    URL.revokeObjectURL(uploadedImageURL);
                }
                uploadedImageURL = URL.createObjectURL(file);
                $image.cropper('destroy').attr('src', uploadedImageURL).cropper(options);
                $inputImage.val('');
            } else {
                SysMsg.warning("请选择一张照片")
            }
        }
    });
    $("#submitBtn").click(function () {
        var base64Img = $image.cropper("getCroppedCanvas").toDataURL();
        //console.log(base64Img)

        var $btn = $("#submitBtn").button('loading');
        <c:if test="${empty param.modifyItemId}">
            $("#avatarDiv img").attr("src", base64Img);
            $("#updateForm input[name=base64Avatar]").val(base64Img.replace(/^data:image\/(.*);base64,/g, ""));
            $("#modal").modal('hide');
            $btn.button('reset');
        </c:if>
        <c:if test="${not empty param.modifyItemId}">
        $.post("${ctx}/user/modifyBaseItem_au",
            {id:'${param.modifyItemId}', modifyValue:base64Img.replace(/^data:image\/(.*);base64,/g, "")}, function(ret){
                $("#jqGrid2").trigger("reloadGrid");
                $("#modal").modal('hide');
                $btn.button('reset');
            });
        </c:if>
    });
</script>