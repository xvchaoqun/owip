<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <script src="${ctx}/assets/js/jquery.js"></script>
    <jsp:include page="../WEB-INF/jsp/common/m_head.jsp"></jsp:include>
    <style>
        #preview {
            width: 100%;
            padding: 20px;
            box-sizing: border-box;
        }

        #preview img {
            width: 100%;
            height: auto;
        }
    </style>
</head>
<body>
    <div>
        <form id="form" method="post" action="${ctx}/m/test/takePhoto" enctype="multipart/form-data">
            <div>
                <label>
                    <span>拍照</span>
                </label>
                <input name="_photo" type="file" accept="image/*" id="myFile"onchange="changImg(event)" style="position:absolute;opacity:0;filter:alpha(opacity=0);width:55px;height:25px;"<%--控制兼容性--%>/>
                <div class="photo" id="preview"><img id="myImg" src="${ctx}/img/photo.jpg" onclick="takePhoto()"></div>
            </div>
        </form>

        <br/>
        <br/>
        <button onclick="_submit()"  type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中"> 提交
        </button>
    </div>
</body>
    <script type="text/javascript">

        function changImg(e){
            for (var i = 0; i < e.target.files.length; i++) {
                var file = e.target.files.item(i);
                if (!(/^image\/.*$/i.test(file.type))) {
                    continue; //不是图片 就跳出这一次循环
                }
                //实例化FileReader API
                var freader = new FileReader();
                freader.readAsDataURL(file);
                freader.onload = function(e) {
                    $("#myImg").attr("src",e.target.result);
                };
            }
        }

        function takePhoto(){
            $('#form #myFile').click();
        }
        $.fileInput($('#form input[name=_photo]'),{
            allowExt: ['pdf', 'jpg', 'jpeg', 'png', 'gif'],
            allowMime: ['application/pdf', 'image/jpg', 'image/jpeg', 'image/png', 'image/gif']
        })

        function _submit(){
            $("#form").submit();return false;
        }
        $("#form").validate({
            submitHandler: function (form) {
                var $btn = $("#submitBtn").button('loading');
                $(form).ajaxSubmit({
                    success: function (ret) {
                        if (ret.success) {
                            SysMsg.success('提交成功。', '成功');
                        }
                        $btn.button('reset');
                    }
                });
            }
        });
    </script>
</html>